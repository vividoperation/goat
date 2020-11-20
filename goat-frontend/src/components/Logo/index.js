import React from 'react'
import logo from './logo.png'

const Logo = ({className}) => (
    <img src={logo} className={`${className}`} alt="GOAT Logo" />
)

export default Logo