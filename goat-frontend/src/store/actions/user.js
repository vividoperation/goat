import { SET_USER } from './types'
import { apiCall } from '../../utils/apicall'

export const getUser = () => {
    return (dispatch, getState) => {
      getUserAsync(dispatch, getState)
    }
}

export const updateUser = data => {
    return dispatch => {
      updateUserAsync(dispatch, data)
    }
}

const getUserAsync = async (dispatch, getState) => {
    const data = getState().user
    const response = await apiCall("GET", `/user/profile/get?username=${data.username}`)
    const user = response.result
    dispatch({
        type: SET_USER,
        user
    })
}

const updateUserAsync = async (dispatch, data) => {
    const updateData = {
        email: data.email,
        phone: data.phone
    }
    await apiCall("POST", `/user/profile/update?username=${data.username}`, updateData)
    dispatch({
        type: SET_USER,
        user: data
    })
}