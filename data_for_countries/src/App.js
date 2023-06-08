import axios from 'axios';
import { useState, useEffect } from 'react';
import Country from './components/Country';
import './index.css'
import countriesAPI from './services/countriesAPI';


const App = () => {

  const [newValue, setNewValue] = useState('')
  const [countries, setCountries] = useState([])

  const hook = () => {
    console.log('Fetching all country names')
    countriesAPI
      .getAll()
      .then(responseData => {
        setCountries(responseData.map(country => country.name.common))
      })
  }

  useEffect(hook, [])
  const handleChange = (event) => {
    // event is automatically passed as an argument in the form element
    setNewValue(event.target.value)

  }

  const handleClick = (country) => {
    console.log(country, 'clicked!')
    setNewValue(country)
  }

  const FilterCountriesElement = ({ filterValue }) => {
    const filteredCountries = countries.filter(country => country.toLowerCase().includes(filterValue.toLowerCase()))
    console.log(filteredCountries.length)
    if (filterValue === '') {
      return <div></div>
    }
    else if (filteredCountries.length === 0) {
      return <div>No countries found</div>
    }
    else if (filteredCountries.length === 1) {
      console.log(filteredCountries[0])
      return <Country countryName={filteredCountries[0]} />
    }
    else if (filteredCountries.length >= 2 && filteredCountries.length <= 10) {
      return filteredCountries.map(country => (
        <div>{country}
          <button className='button' onClick={() => handleClick(country)}>show</button>
        </div>
      ))
    }
    
    return <div>Too many searches, narrow search</div>

  }

  return (
    <div>
      <h1>Data For Countries Web Application</h1>
      <form>
        find countries: <input value={newValue} onChange={handleChange} />
      </form>
      <div>
        <FilterCountriesElement filterValue={newValue} />
      </div>
    </div>
  )
}
export default App;
