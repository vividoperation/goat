import React from 'react'
import { Header } from './'

const TournamentHeader = ({text}) => (
    <>
        <div className="row">
            <div className="col-12">

            </div>
        </div>
        <div className="row">
            <div className="col-12">
                <Header className="text-center">{text}</Header>
            </div>
        </div>
    </>
)

export default TournamentHeader