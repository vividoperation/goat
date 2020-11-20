import React from 'react'

const Logout = ({onLogout}) => (
    <button className="rounded bg-primary-nobootstrap text-white border-0 px-2 py-2 fixed-top m-2" onClick={onLogout}>Logout</button>
)

export default Logout