import React, {Component} from 'react';
import SockJsClient from 'react-stomp';
import './App.css';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import './MessageStyle.css';
import NameComponent from "./components/NameComponent";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            messages: [],
            typedMessage: "",
            name: ""
        }
    }

    setName = (name) => {
        console.log(name);
        this.setState({name: name});
    };

    sendMessage = () => {
        this.clientRef.sendMessage('/app/user-all', JSON.stringify({
            name: this.state.name,
            message: this.state.typedMessage
        }));
    };

    sendRock = () => {
    this.setState({typedMessage: "rock"})
            this.clientRef.sendMessage('/app/user-all', JSON.stringify({
                name: this.state.name,
                message: "rock"
            }));
        };
    sendPaper = () => {
    this.setState({typedMessage: "paper"})
            this.clientRef.sendMessage('/app/user-all', JSON.stringify({
                name: this.state.name,
                message: "paper"
            }));
        };
    sendScissors = () => {
    this.setState({typedMessage: "scissors"})
            this.clientRef.sendMessage('/app/user-all', JSON.stringify({
                name: this.state.name,
                message: "scissors"
            }));
        };
    connectGame = () => {

    }

    displayMessages = () => {
        return (
            <div>
                {this.state.messages.map(msg => {
                    return (
                        <div>
                            {this.state.name === msg.name ?
                                <div>
                                    <p className="title1">{msg.name} : </p><br/>
                                    <p>{msg.message}</p>
                                </div> :
                                <div>
                                    <p className="title2">{msg.name} : </p><br/>
                                    <p>{msg.message}</p>
                                </div>
                            }
                        </div>)
                })}
            </div>
        );
    };

    render() {
        return (
            <div>
                <NameComponent setName={this.setName}/>
                <div className="align-center">
                    <h1>Rock Paper Scissors!</h1>
                    <br/><br/>
                </div>
                <div className="align-center">
                    User : <p className="title1"> {this.state.name}</p>
                </div>
                <div className="align-center">
                    <br/><br/>
                    <table>
                        <tr>

                            <td>
                                <Button variant="contained" color="primary"


                                        onClick={this.sendRock}>Rock</Button>
                                 <Button variant="contained" color="primary"
                                                                        onClick={this.sendPaper}>Paper</Button>
                                 <Button variant="contained" color="primary"
                                                                        onClick={this.sendScissors}>Scissors</Button>
                            </td>
                        </tr>
                    </table>
                </div>
                <br/><br/>
                <div className="align-center">
                    {this.displayMessages()}
                </div>
                <SockJsClient url={`${process.env.REACT_APP_BACKEND_ENDPOINT}/websocket-chat/`}
                              topics={['/topic/user']}
                              onConnect={() => {
                                  console.log("connected");
                                  this.connectGame;
                              }}
                              onDisconnect={() => {
                                  console.log("Disconnected");
                              }}
                              onMessage={(msg) => {
                                  var jobs = this.state.messages;
                                  jobs.push(msg);
                                  this.setState({messages: jobs});
                                  console.log(this.state);
                              }}
                              ref={(client) => {
                                  this.clientRef = client
                              }}/>
            </div>
        )
    }
}

export default App;