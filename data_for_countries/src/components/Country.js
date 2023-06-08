import { useEffect, useState } from "react"
import countriesAPI from "../services/countriesAPI"
import weatherAPI from "../services/weatherAPI"

const Country = ({ countryName }) => {
    const [countryData, setCountryData] = useState(null)
    const [weatherData, setWeatherData] = useState(null)

    const hook = () => {
        countriesAPI.getCountry(countryName)
            .then(responseData => {
                console.log(responseData.name.common)
                setCountryData(responseData)
            })
            .catch(error => console.log(error))
    }

    useEffect(hook, [countryName])

    const weatherHook = () => {
        if (countryData) {
            weatherAPI.getWeather(countryData.capital)
                .then(responseData => setWeatherData(responseData))
                .catch(error => console.log(error))
        }
    }

    useEffect(weatherHook, [countryData])

    return (<div>
        {countryData ?
            <div>
                <h1>{countryData.name.common}</h1>
                <div>Capital: {countryData.capital} </div>
                <div>Area: {countryData.area}</div>
                <h2>Languages: </h2>
                {countryData.languages ? (<ul>{Object.entries(countryData.languages).map(([id, language]) => {
                    return <li>{language}</li>
                })}</ul>) : (<div>This country has no official languages</div>)}
                <img alt={countryData.alt} src={countryData.flags.png} />
                <h2>Weather in {countryData.capital}</h2>
                {weatherData ? 
                    (<div>Temperature: {(weatherData.main.temp - 273.15).toFixed(2)} Degrees Celsius</div>):
                    (<div>Fetching weather data...</div>)
                }
            </div>
            :
            <div>Loading country...</div>
        }
    </div>)
}


export default Country