package br.com.lucas.skillplus.infra.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class SimpleSocketIOController {

    @Autowired
    private SocketIOServer socketServer;

    SimpleSocketIOController(SocketIOServer socketServer) {
        this.socketServer = socketServer;
        this.socketServer.addEventListener("showCode", String.class, onShowCode);
    }

    public DataListener<String> onShowCode = new DataListener<String>() {
        @Override
        public void onData(SocketIOClient client, String data, AckRequest acknowledge) throws Exception {

            // Log the received message
            log.info("Received showCode event with message: " + data);

            System.out.println("deu bom");

            // Broadcast the message to all connected clients
            socketServer.getBroadcastOperations().sendEvent("showCode", data);

            // Send an acknowledgement to the sender
            acknowledge.sendAckData("Message broadcasted successfully");
        }
    };
}
