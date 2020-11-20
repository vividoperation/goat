import React from 'react'
import { connect } from 'react-redux'
import { Redirect } from 'react-router-dom'

const Authenticated = ({user, children}) => {
    if (!user || user.token === null) {
        return children
    }
    return <Redirect to="/" />
}

const mapStateToProps = ({user}) => ({
    user
})

export default connect(mapStateToProps)(Authenticated)