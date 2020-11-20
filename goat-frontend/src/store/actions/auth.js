import { CLEAR_USER, SET_USER } from './types'
import { apiCall, setAuthTokenHeader, clearAuthTokenHeader } from '../../utils/apicall'

export const login = data => {
    return dispatch => {
      loginAsync(dispatch, data)
    }
}

export const register = data => {
    return dispatch => {
      registerAsync(dispatch, data)
    }
}

const loginAsync = async (dispatch, data) => {
    const loginResponse = await apiCall("POST", "/user/login", data)
    const token = loginResponse.result
    setAuthTokenHeader(token)
    const profileResponse = await apiCall("GET", `/user/profile/get?username=${data.username}`)
    const user = profileResponse.result
    user.token = token
    dispatch({
        type: SET_USER,
        user
    })
}

const registerAsync = async (dispatch, data) => {
    const response = await apiCall("POST", "/user/register", data)
    const token = response.result
    setAuthTokenHeader(token)
    const user = data
    user.token = token
    dispatch({
        type: SET_USER,
        user
    })
}

export const logout = () => {
    clearAuthTokenHeader()
    return {
       type: CLEAR_USER
    }
}