import React from 'react'
import GameThumbnail from '../components/GameThumbnail'

const Games = () => {
    const games = [
        {
            name: "testgame",
            displayName: "Rock, Paper, Scissors",
            iconUrl: "https://yt3.ggpht.com/a/AGF-l7_837fxti7sAA4P8Uz1FJKBnMbEqHSrhNjfYA=s900-mo-c-c0xffffffff-rj-k-no"
        }
    ]

    const gamesList =  games.map(({name, displayName, iconUrl}, i) => <GameThumbnail name={name} key={i} displayName={displayName} iconUrl={iconUrl} />)

    return (
        <div className="container">
            {gamesList}
        </div>
    )
}

export default Games