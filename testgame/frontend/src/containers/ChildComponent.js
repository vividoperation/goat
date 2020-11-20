import React from 'react'
import { w3cwebsocket as W3CWebSocket } from "websocket";

class ChildComponent extends Component {

    sendMessage=()=>{
        const {websocket} = this.props // websocket instance passed as props to the child component.

        try {
            websocket.send(data) //send data to the server
        } catch (error) {
            console.log(error) // catch error
        }
    }
    render() {
        return (
            <div>
                ........
            </div>
        );
    }
}

export default ChildComponent
