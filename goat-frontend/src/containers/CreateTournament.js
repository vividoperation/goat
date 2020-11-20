import React, { Component} from 'react'
import { withRouter } from 'react-router-dom';
import { Formik } from 'formik';
import { LoginHeader, TournamentHeader } from '../components'
import { apiCall } from '../utils/apicall'

class CreateTournament extends Component {

    state = {
        players: "4",
        game: "testgame",
        tournament:"tournament"
    }

    handleChangePlayers = event => {
      this.setState({players: event.target.value});
    }

    handleChangeGame = event => {
      this.setState({game: event.target.value});
    }

    handleChangeName = event => {
      this.setState({tournament: event.target.value});
    }

    handleSubmit = async(event) => {
      event.preventDefault()
      const {game, players, tournament} = this.state

      const body = {
        maxplayers: parseInt(players),
        gamename: game,
        tournamentname: tournament
      }

      await apiCall("POST", "/tournaments/create", body)
      this.props.history.push("/tournaments/join")
    }

    render() {
         return (
            <div className="container">
                 <br/>
                 <br/>
                 <br/>
                 <br/>
                 <TournamentHeader text="Create Tournament"/>
                 <br/>
                 <div className="center">
                <Formik
                    initialValues={this.props.user}
                    onSubmit={this.onSubmit}
                >
                    {({ errors, touched }) => (
                     <form onSubmit={this.handleSubmit} onChange={this.handleChange}>
                             <div className="row">
                                   <div className="col-6 text-right mb-4">
                            <label>
                              Choose number of tournament players:
                              <select value={this.state.players} onChange={this.handleChangePlayers} >
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                              </select>
                            </label>
                               </div>
                                   <div className="col-6 text-left mb-4">
                            <label>
                              Choose a game name:
                              <select value={this.state.game} onChange={this.handleChangeGame}>
                                <option value="testgame">testgame</option>
                              </select>
                            </label>
                            </div>
                            </div>
                             <div className="row">
                               <div className="col-12 text-center mb-4">
                                <label>
                                  Choose a tournament name:
                                   <input type="text" value={this.state.name} onChange={this.handleChangeName} name="name" />
                                </label>
                        </div>
                        </div>
                        <div className="col-12 text-center mb-4">
                            <input type="submit" value="Submit" />
                            </div>
                          </form>

                    )}
                </Formik>
                </div>
            </div>
        )
    }
}

export default withRouter(CreateTournament)
