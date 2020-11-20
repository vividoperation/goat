import React, { Component } from 'react';
import SockJsClient from 'react-stomp';
import './App.css';
import Button from '@material-ui/core/Button';
import './MessageStyle.css';

class TestGameFront extends Component {
    state = {
        messages: [],
        typedMessage: "",
        name: "",
        foundWinner: false,
        weWon: false,
    }

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

    checkIfOver = () => {
        this.state.messages.map(msg => {
            if(msg.message === ("winner"))
            {
                if (msg.name === this.state.name) {
                    this.setState({weWon: true})
                } else {
                    this.setState({weWon: false})
                }
                this.setState({foundWinner: true})

            }
            if(msg.message === ("loser"))
            {
                if (msg.name === this.state.name) {
                    this.setState({weWon: false})
                } else {
                    this.setState({weWon: true})
                }
                this.setState({foundWinner: true})
            }
       })
    };

    displayMessages = () => {
        return (
            <div>
                {this.state.messages.map((msg, i) => {
                    return (
                        <div key={i}>
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

    displayResult = () => {
        const result = this.state.weWon ? "You won!" : "You lost :("
        return (
            <div>
                <p className="title2">{result}</p>
            </div>
        );
    }

    onMessage = (msg) => {
        var jobs = this.state.messages;
        jobs.push(msg);
        this.setState({messages: jobs});

        this.checkIfOver()
    }

    render() {
        const { foundWinner } = this.state

        const rockButton = !foundWinner ? <Button id="rock" variant="contained" color="primary" onClick={this.sendRock}>Rock</Button> : null
        const paperButton = !foundWinner ? <Button id ="paper" variant="contained" color="primary" onClick={this.sendPaper}>Paper</Button> : null
        const scissorsButton = !foundWinner ? <Button id = "scissors" variant="contained" color="primary" onClick={this.sendScissors}>Scissors</Button> : null

        const messages = foundWinner ? this.displayResult() : this.displayMessages()

        return (
            <div>
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
                                {rockButton}
                                {paperButton}
                                {scissorsButton}
                            </td>
                        </tr>
                    </table>
                </div>
                <br/><br/>
                <div className="align-center">
                    {messages}
                </div>
                <SockJsClient url={`${process.env.REACT_APP_BACKEND_ENDPOINT}/websocket-chat/`}
                              topics={['/topic/user']}
                              onConnect={() => {
                                    console.log("connected");
                                    var url = window.location.href;
                                    var split_url = url.split('?');
                                    var token1 = split_url[1];
                                    var split_token = token1.split('=');
                                    var token = split_token[1];
                                    this.setState({name: token})
                              }}
                              onDisconnect={() => {
                                  console.log("Disconnected");
                              }}
                              onMessage={this.onMessage}
                              ref={(client) => {
                                  this.clientRef = client
                              }}/>
            </div>
        )
    }
}

export default TestGameFront;

//import React, { Component } from 'react'
//import { Header } from '../components'
//import { ChildComponent } from '../containers'
//
//class TestGameFront extends Component {
//    constructor(props){
//        super(props);
//        this.state = {
//            message: 'Loading...',
//
//        };
//        this.ws = new WebSocket('ws://localhost:8081/testgameBack')
//        this.state.message = "ws created";
//        this.setUpEverything();
//
//    }
//
//    setUpEverything() {
//        this.state.message = "in setUpEverything()";
//        //debugger
//        this.ws.onopen = () => {
//            // on connecting, do nothing but log it to the console
//            this.state.message = 'connected'
//        }
//
//        this.ws.onmessage = evt => {
//            // listen to data sent from the websocket server
//            const message = JSON.parse(evt.data)
//            this.setState({dataFromServer: message})
//            this.state.message = message
//        }
//
//        this.ws.onclose = () => {
//            this.state.message = 'disconnected'
//            // automatically try to reconnect on connection loss
//        }
//
//    }
//
//    render() {
//        return (
//            <>
//                <div className="center">
//                <Header >Game Front End</Header>
//                </div>
//                <div><br></br></div>
//                <div className="center">
//                    <p>{this.state.message}</p>
//                </div>
//            </>
//        );
//    }
///*
//    constructor(props){
//        super(props);
//        this.state = {
//            message: 'Loading...',
//
//        };
//
//        var token = this.parseURL(window.location.href); // need to figure out exactly what is passing in as URL
//        console.log("TOKEN");
//        console.log(token);
//        this.state.message = token;
//        this.token = token;
//        this.gameId = 0
//        this.socket = null;
//        this.device = null;
////        this.open_websocket(token);
//
//    this.socket = new W3CWebSocket("ws://localhost:8081/testgameBack");
//    //this.state.message = socket.readyState;
//    this.open();
//
//  //socket.send("My name is Mike");
//  }
//
//open(){
//    this.socket.onopen = () => {
//            this.state.message = "Ready to Open";
//          }
//}
//
///*
//         waitForOpenConnection = (socket) => {
////                     this.state.message = "in wait message";
//
//            return new Promise((resolve, reject) => {
//                const maxNumberOfAttempts = 10
//                const intervalTime = 200 //ms
//
//                let currentAttempt = 0
//                this.state.message = currentAttempt
//                const interval = setInterval(() => {
//                    if (currentAttempt > maxNumberOfAttempts - 1) {
//                        clearInterval(interval)
//                        reject(new Error('Maximum number of attempts exceeded'))
//                    } else if (socket.readyState === socket.OPEN) {
//                        clearInterval(interval)
//                        resolve()
//                    }
//                    currentAttempt++
//                }, intervalTime)
//            })
//        }
//
//        sendMessage = async (socket, msg) => {
//            this.state.message = "in send message";
//            if (socket.readyState !== socket.OPEN) {
//                try {
//                    this.state.message = "waiting";
//                    //await this.waitForOpenConnection(socket)
//                    this.state.message = "waited"
//                    this.state.message = msg;
//                    socket.send(msg)
//
//                    this.state.message = "sent"
//                } catch (err) { console.error(err) }
//            } else {
//                socket.send(msg)
//            }
//        }
//        parseURL(url)
//        {
//            var split_url = url.split('?');
//            var token1 = split_url[1];
//            var split_token = token1.split('=');
//            var token = split_token[1];
//            this.token = token;
//            return token;
//        }
///*
//        open_websocket(token, state)
//        {
//            this.state.message = ("in open_websocket");
//             var socket = new WebSocket("ws://localhost:8081/testgameBack");
//
//             this.state.message = "after socket var";
//             this.socket = socket;
////             this.socket.open(this);
//    //             socket.open(this);
//
//             this.socket.onmessage=function(evt){
//                this.onMessage(evt)
//             };
//             this.state.message = "after on message";
//    //         this.socket = socket;
//             this.startGame(token);
////             this.state.message = "after start";
//        }
//
//         onMessage(event) {
//            this.state.message = "in onMessage"
//             var device = JSON.parse(event.data);
//             this.device = device;
//             //maybe need to change to started and removed
//             if (device.status === "start") {
//                this.state.message = "Status: both players present, game has started, waiting for winner";
//                this.playGame(device, this.token);
//
//
//             }
//             if (device.status === "wait") {
//                 this.state.message = "Status: waiting on other player in the game";
//                 this.startGame(device);
//
//                 //device.parentNode.removeChild(device);
//             }
//             if (device.status === "toggle") {
//
//                 if (device.winner== this.token) {
//                     this.state.message = "Status: you have won the game, congratulations!";
//                 } else if (device.loser == this.token) {
//                     this.state.message = "Status: you have lost the game. Better luck next time :(";
//                 }
//
//                 this.removeGame(device, this.token);
//             }
//             if (device.status === "remove") {
////                this.socket.close();
//             }
//         }
//
//         startGame(token) {
//         this.state.message = "start start";
//             var DeviceAction = {
//                 action: "start",
//                 token: this.token
//             };
//             this.state.message =" in start";
//             console.log("in start");
//             this.state.message = JSON.stringify(DeviceAction);
//             this.sendMessage(this.socket, JSON.stringify(DeviceAction));
////
//         }
//
//
//         removeGame(element, token) {
//                      this.state.message =" in remove";
//
//             var id = element;
//             var DeviceAction = {
//                 action: "remove",
//                 token: token,
//                 id: id
//             };
//             this.sendMessage(this.socket, JSON.stringify(DeviceAction));
////                           this.state.message = JSON.stringify(DeviceAction);
//
//         }
//    //
//         playGame(element, token) {
//                      this.state.message =" in play";
//
//             var id = this.state.device.getGameId();
//             this.state.gameId = id;
//             var DeviceAction = {
//                 action: "toggle",
//                 token: token,
//                 id: id
//             };
//             this.sendMessage(this.socket, JSON.stringify(DeviceAction));
////                          this.state.message = JSON.stringify(DeviceAction);
//
//         }
//*/
//
//
//
//
//}
//
//export default TestGameFront
//
