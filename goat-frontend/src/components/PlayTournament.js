import React, { Component } from 'react'
import { withRouter } from 'react-router-dom';
import { Header, Loading } from '.';
import { Bracket } from 'react-tournament-bracket';
import { waitMillisecondsAsync } from '../utils/timer';
import { apiCall } from '../utils/apicall'

class PlayTournament extends Component {
    constructor(props) {
        super(props)

        this.state = {
            bracket: null,
            alive: false,
            winner: null,
            full: false,
            name: 'Tournament Bracket'
        }
    }

    componentDidMount = () => {
        this.interval = setInterval(() => {
            this.getTournamentInfo();
          }, 1000);
    }

    getTournamentInfo = async () => {
        await waitMillisecondsAsync(500)
        const response = await apiCall("GET", `/tournaments/bracket?id=${this.props.match.params.tournamentId}`)
        console.log(response.result)
        console.log(JSON.stringify(response.result))
        this.setState(response.result)
    }

    onClick = () => {
        console.log("Click")
        this.props.history.push(`/play/games/tournament/${this.props.match.params.tournamentId}`)
    }

    render() {
        const bracketComponent = this.state.bracket ? (
            <div className="container">
                <Bracket game={this.state.bracket} topText={() => null} />
            </div>
        ) : <Loading />

        const PlayGameComponent = ({alive,bracket,winner,full})=>{
            if(alive && bracket!=null&&winner==null&&full ){
                return(
                    <button
                        onClick={this.onClick}
                        className="rounded bg-primary-nobootstrap text-white border-0 px-2 py-2 m-2"
                    >
                    Play Game
                    </button>
                )
            }else if(alive ==false && bracket!=null ){
                return(
                    <h2>Sorry, you have been eliminated from this tournament</h2>
                )
            }else{
                return(<h2></h2>)
            }
        }

    

        const winnerComponent = this.state.bracket ? this.state.winner ?(
            <h2>Tournament Complete, winner is {this.state.winner}</h2>
        ) : (
            <h2></h2>
        ) : null

        return (
            <>
                <div className="row">
                    <div className="col-12">
                        <Header className="text-center">{this.state.name}</Header>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">{bracketComponent}</div>
                </div>
                <div className="row">
                    {/* <div className="col-12 text-center">
                        {playGameComponent}
                    </div> */}
                    <div className="col-12 text-center">
                          {/* playGameComponent part */}
                        <PlayGameComponent bracket={this.state.bracket} alive={this.state.alive}
                        full={this.state.full} winner={this.state.winner}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12 text-center">
                        {winnerComponent}
                    </div>
                </div>
            </>
        )
    }
}

export default withRouter(PlayTournament)