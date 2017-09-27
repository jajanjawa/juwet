package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.event.MessageListener;
import com.github.jajanjawa.juwet.wire.Message;
import com.github.jajanjawa.juwet.wire.Packet;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JuwetConnection {
    private static final String SUBSCRIBE = "subscribe";
    private String id;
    private Socket socket;
    private Emitter.Listener messageListener;
    private List<MessageListener> messageListeners;

    public JuwetConnection(String id) {
        this.id = id;
        socket = createConnection();
        messageListeners = new ArrayList<>();
        initMessageListener();
    }

    public String getId() {
        return id;
    }

    private void initMessageListener() {
        messageListener = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Packet packet = Packet.from((JSONObject) args[0]);
                if (packet.getRoom().equals(id)) {
                    Message message = packet.getMessage();
                    for (MessageListener listener : messageListeners) {
                        listener.onSucces(message);
                    }
                }
            }
        };
    }

    public Socket getSocket() {
        return socket;
    }

    public JuwetConnection connect() {
        socket.connect();
        return this;
    }

    public JuwetConnection listen() {
        if (messageListener == null) {
            initMessageListener();
            socket.on(Socket.EVENT_MESSAGE, messageListener);
        }
        socket.emit(SUBSCRIBE, id);
        return this;
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    private Socket createConnection() {
        try {
            return IO.socket("http://relay.nowdb.net");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
