import React, { Component } from 'react'
import { withRouter } from 'react-router-dom';
import { Game, Loading } from '.';
import { apiCall } from '../utils/apicall';

class PlayGame extends Component {
    state = {
        url: null
    }

    componentDidMount = () => {
        this.getGameToken(this.props);
    }

    getGameToken = async (props) => {
        const routeParams = props.match.params
        console.log(routeParams)
        console.log(routeParams.tournamentId)

        let response = null

        if (routeParams.tournamentId) {
            response = await apiCall("GET", `/tournaments/play?id=${routeParams.tournamentId}`)
        } else {
            response = await apiCall("GET", `/games/newgame?gameName=${routeParams.gameName}`)
        }
        this.setState({url: response.result})
    }

    onClick = () => {
        const { history, match } = this.props
        const { tournamentId } = match.params
        if (tournamentId) {
            history.push(`/play/tournaments/${tournamentId}`)
        } else {
            history.push('/games')
        }
    }

    render() {
        const { url } = this.state
        console.log(url)
        const component = url ? <Game src={url} /> : <Loading />

        return (
            <>
                {component}
                <button className="rounded bg-primary-nobootstrap text-white d-block mx-auto border-0 px-2 py-2 my-2" onClick={this.onClick}>Game Over? Click Here</button>
            </>
        )
    }
}

export default withRouter(PlayGame)