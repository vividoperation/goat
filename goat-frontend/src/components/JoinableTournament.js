import React from 'react'
//import "joinable.css"

const JoinableTournament = ({tournament, onClick}) => (
    <div className="d-block mx-auto rounded bg-primary-nobootstrap text-white border-0 px-2 py-2 text-center w-50 mb-1" onClick={() => onClick(tournament.tournamentid)}>{tournament.tournamentname}</div>
)

export default JoinableTournament