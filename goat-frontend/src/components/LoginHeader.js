import React from 'react'
import { Logo, Header } from './'

const LoginHeader = ({text}) => (
    <>
        <div className="row">
            <div className="col-12">
                <Logo className="mx-auto d-block m-3"/>
            </div>
        </div>
        <div className="row">
            <div className="col-12">
                <Header className="text-center">{text}</Header>
            </div>
        </div>
    </>
)

export default LoginHeader