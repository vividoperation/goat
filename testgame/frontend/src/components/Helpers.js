    export function parseURL(url)
    {
        var split_url = url.split('?');
        token = split_url[1];
        return token;
    }
    export function open_websocket(token, state)
    {
         var socket = new WebSocket("ws://localhost:8081/testgameBack");
         socket.onmessage = onMessage;

         startGame(token);
    }


     export function onMessage(event) {
         var device = JSON.parse(event.data);
         //maybe need to change to started and removed
         if (device.status === "start") {
            state.message = "Status: both players present, game has started, waiting for winner";
            playGame(device)
         }
         if (device.status == "wait") {
             state.message = "Status: waiting on other player in the game";
             //device.parentNode.removeChild(device);
         }
         if (device.status === "toggle") {

             if (device.winner== token) {
                 state.message = "Status: you have won the game, congratulations!";
             } else if (device.loser == token) {
                 state.message = "Status: you have lost the game. Better luck next time :(";
             }

             removeGame(device, token);
         }
         if (device.status === "remove") {
            socket.close();
         }
     }

     export function startGame(token) {
         var DeviceAction = {
             action: "start",
             token: token
         };
         socket.send(JSON.stringify(DeviceAction));
     }

     export function removeGame(element, token) {
         var id = element;
         var DeviceAction = {
             action: "remove",
             token: token,
             id: id
         };
         socket.send(JSON.stringify(DeviceAction));
     }
//
     export function playGame(element, token) {
         var id = device.getGameId();
         var DeviceAction = {
             action: "toggle",
             token: token,
             id: id
         };
         socket.send(JSON.stringify(DeviceAction));
     }

