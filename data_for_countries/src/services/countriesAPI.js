import axios from 'axios'

const baseURL = 'https://studies.cs.helsinki.fi/restcountries/api/all'
const specificURL = 'https://studies.cs.helsinki.fi/restcountries/api/name'

const getCountry = (countryName) => {
    console.log(countryName)
    const request = axios.get(`${specificURL}/${countryName}`)
    return request.then(response => response.data)
}

const getAll = () => {
    const request = axios.get(baseURL)
    return request.then(response => response.data)
}


export default {getCountry, getAll}