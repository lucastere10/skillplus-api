package br.com.lucas.skillplus.infra.websocket.service;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;

import br.com.lucas.skillplus.infra.websocket.model.Credentials;
import br.com.lucas.skillplus.infra.websocket.model.Message;
import br.com.lucas.skillplus.infra.websocket.model.MessageType;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.CLIENT, message));
            }
        }
    }

    public void sendCredentials(String room, String eventName, SocketIOClient senderClient, String email, String nome) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Credentials(email, nome));
            }
        }
    }

}