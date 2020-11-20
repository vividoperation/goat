import React, { Component} from 'react'
import { withRouter } from 'react-router-dom';
import { JoinableTournament, LoginHeader, TournamentHeader } from '../components'
import { apiCall } from '../utils/apicall'

class JoinTournament extends Component {

    state = {
        tournaments: []
    }

    componentDidMount = () => {
        this.getGames()
    }

    onClick = async id => {
        await apiCall("POST", `/tournaments/join?id=${id}`)
        this.props.history.push(`/play/tournaments/${id}`)
    }

   getGames = async () =>{
        const result = await apiCall("GET", "/tournaments/joinable");
        const tournaments = result.result
        this.setState({tournaments})
   }

    render() {
        const tournaments = this.state.tournaments.map((t, i) => <JoinableTournament key={i} onClick={this.onClick} tournament={t} />)
        return (
            <>
                <div className="container">
                <br/>
                <br/>
                <br/>
                <br/>
                <TournamentHeader text="Join Available Tournaments"/>
                {tournaments}
                </div>
            </>
            )
    }
}

export default withRouter(JoinTournament)
