import axios from 'axios'
import { isNotNullOrEmpty } from "./ensure"

const apiCall = async (method, path, data) => {
    try {
        let pathWithEndpoint = path
        if (process && process.env) {
            if (process.env.BACKEND_ENDPOINT) {
                pathWithEndpoint = process.env.BACKEND_ENDPOINT + path
            } else if (process.env.REACT_APP_BACKEND_ENDPOINT) {
                pathWithEndpoint = process.env.REACT_APP_BACKEND_ENDPOINT + path
            }
        }

        const res = await axios[method.toLowerCase()](
            pathWithEndpoint,
            data
        )

        return res.data
    } catch (err) {
        if (err.response) {
            err.response.data.status = err.response.status
            throw err.response.data
        }

        throw err
    }
}

const setAuthTokenHeader = token => {
    token = isNotNullOrEmpty(token)
    axios.defaults.headers.common['token'] = token
}

const clearAuthTokenHeader = () => {
    delete axios.defaults.headers.common['token'];
}

export {
    apiCall,
    setAuthTokenHeader,
    clearAuthTokenHeader
}