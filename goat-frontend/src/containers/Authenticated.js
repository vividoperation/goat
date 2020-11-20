import React from 'react'
import { connect } from 'react-redux'
import { Redirect } from 'react-router-dom'

import { isNotNullOrEmpty } from '../utils/ensure'

const Authenticated = ({user, children}) => {
    try {
        isNotNullOrEmpty(user.token)
        return children
    } catch (err) {
        return <Redirect to="/login" />
    }
}

const mapStateToProps = ({user}) => ({
    user
})

export default connect(mapStateToProps)(Authenticated)