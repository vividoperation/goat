import React from "react";
import { connect } from 'react-redux'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect
} from "react-router-dom";
import { Games, Authenticated, Profile, Login, Register, Unauthenticated, CreateTournament, JoinTournament, MyTournaments } from "./"
import { BottomNavbar, TopNavbar, PlayGame, PlayTournament, TournamentNavbar } from '../components'

const MainRouter = ({user}) => {

  const username = user ? user.username : null

  return (
    <Router>
        <Switch>
          <Route exact path="/register">
            <Unauthenticated>
              <Register />
            </Unauthenticated>
          </Route>
          <Route exact path="/login">
            <Unauthenticated>
              <Login />
            </Unauthenticated>
          </Route>
          <Route exact path="/games">
            <Authenticated>
              <div className="row">
                  <div className="col-12">
                      <TopNavbar username={username} />
                  </div>
              </div>
              <div className="row">
                  <div className="col-12">
                      <Games />
                  </div>
              </div>
              <BottomNavbar />
            </Authenticated>
          </Route>
          <Route exact path="/play/tournaments/:tournamentId">
            <Authenticated>
              <div className="row">
                  <div className="col-12">
                      <TopNavbar username={username} />
                  </div>
              </div>
              <div className="row">
                  <div className="col-12">
                      <PlayTournament />
                  </div>
              </div>
              <BottomNavbar />
            </Authenticated>
          </Route>
          <Route exact path="/play/games/tournament/:tournamentId">
            <Authenticated>
              <div className="row">
                  <div className="col-12">
                      <TopNavbar username={username} />
                  </div>
              </div>
              <div className="row">
                  <div className="col-12">
                      <PlayGame />
                  </div>
              </div>
              <BottomNavbar />
            </Authenticated>
          </Route>
          <Route exact path="/play/games/:gameName">
            <Authenticated>
              <div className="row">
                  <div className="col-12">
                      <TopNavbar username={username} />
                  </div>
              </div>
              <div className="row">
                  <div className="col-12">
                      <PlayGame />
                  </div>
              </div>
              <BottomNavbar />
            </Authenticated>
          </Route>
          <Route exact path="/profile">
            <Authenticated>
              <Profile />
              <BottomNavbar />
            </Authenticated>
          </Route>
          <Route exact path="/">
            <Redirect to="/games" />
          </Route>
           <Route exact path="/tournaments/create">
              <Authenticated>
                <TournamentNavbar />
                <CreateTournament />
                <BottomNavbar />
              </Authenticated>
            </Route>
           <Route exact path="/tournaments/join">
              <Authenticated>
                <TournamentNavbar />
                <JoinTournament />
                <BottomNavbar />
              </Authenticated>
            </Route>
            <Route exact path="/tournaments/my">
              <Authenticated>
                <TournamentNavbar />
                <MyTournaments />
                <BottomNavbar />
              </Authenticated>
            </Route>
        </Switch>

    </Router>
  );
}

const mapStateToProps = ({user}) => ({
    user
})

export default connect(mapStateToProps, null)(MainRouter)