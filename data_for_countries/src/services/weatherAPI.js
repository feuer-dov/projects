import axios from "axios";

const api_key = process.env.REACT_APP_API_KEY

const getWeather = (cityName) => {
    const baseURL = `https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=${api_key}`
    const request = axios.get(baseURL)
    return request.then(response => response.data)
}  


export default {getWeather}