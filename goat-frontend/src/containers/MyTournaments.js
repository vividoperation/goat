import React, { Component} from 'react'
import { withRouter } from 'react-router-dom';
import { JoinableTournament, LoginHeader, TournamentHeader } from '../components'
import { apiCall } from '../utils/apicall'

class MyTournaments extends Component {

    state = {
        tournaments: []
    }

    componentDidMount = () => {
        this.getGames()
    }

    onClick = async id => {
        this.props.history.push(`/play/tournaments/${id}`)
    }

   getGames = async () =>{
        const result = await apiCall("GET", "/tournaments/my");
        const tournaments = result.result
        this.setState({tournaments})
   }

    render() {
        const tournaments = this.state.tournaments.map((t, i) => <JoinableTournament key={i} onClick={this.onClick} tournament={t} />)
        return (
            <>
                <br/>
                <br/>
                <br/>
                <br/>
                <TournamentHeader text="My Tournaments"/>
                {tournaments}
            </>
            )
    }
}

export default withRouter(MyTournaments)
