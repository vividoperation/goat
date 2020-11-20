import React from 'react'

const FormLabel = ({children, htmlFor}) => (
    <label className="px-2" htmlFor={htmlFor}>{children}</label>
)

export default FormLabel