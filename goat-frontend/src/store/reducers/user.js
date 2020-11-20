import { SET_USER, CLEAR_USER } from '../actions/types'
import { REHYDRATE } from 'redux-persist'
import { setAuthTokenHeader } from '../../utils/apicall'

export const userReducer = (user = null, action) => {
    // Add token to user if not included
    if (user && action.user && !action.user.token) {
        action.user.token = user.token
    }

    switch (action.type) {
        case SET_USER:
            return action.user
        case CLEAR_USER:
            return null
        case REHYDRATE:
            if (action.payload && action.payload.user) {
                if (action.payload.user.token) {
                    setAuthTokenHeader(action.payload.user.token)
                }
                return action.payload.user
            }
            return null
        default:
            return user
    }
}