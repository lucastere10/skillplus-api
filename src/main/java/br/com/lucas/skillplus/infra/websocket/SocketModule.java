package br.com.lucas.skillplus.infra.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import br.com.lucas.skillplus.infra.websocket.model.Credentials;
import br.com.lucas.skillplus.infra.websocket.model.Message;
import br.com.lucas.skillplus.infra.websocket.service.SocketService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@CrossOrigin(origins = "http://localhost:3000/")
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_credentials", Credentials.class, onCredentialsReceived());
        server.addEventListener("send_message", Message.class, onChatReceived());
    }

    
    private DataListener<Credentials> onCredentialsReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.sendCredentials(data.getRoom(),"get_credentials", senderClient, data.getEmail(), data.getNome());
        };
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.sendMessage(data.getRoom(),"get_message", senderClient, data.getMessage());
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}