import React from 'react'
import { Link } from 'react-router-dom'

const GameThumbnail = ({name, displayName, iconUrl}) => (
    <Link className="col-lg-2 col-md-3 col-sm-4 text-white text-center d-inline-block" to={`/play/games/${name}`} >
        <img className="img-fluid img-thumbnail" src={iconUrl} alt="Game icon" />
        {displayName}
    </Link>
)

export default GameThumbnail