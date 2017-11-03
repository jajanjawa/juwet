package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.event.JuwetMessageListener;
import com.github.jajanjawa.juwet.io.PacketReader;
import com.github.jajanjawa.juwet.wire.Packet;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JuwetConnection {
    public static final String SUBSCRIBE = "subscribe";
    public static final String SEND = "send";
    private String id;
    private Socket socket;
    private Emitter.Listener messageListener;
    private List<JuwetMessageListener> messageListeners;

    /**
     * @param id room id
     */
    public JuwetConnection(String id) {
        this(createSocket(), id);
    }

    public JuwetConnection(Socket socket, String id) {
        this.id = id;
        this.socket = socket;
        messageListeners = new ArrayList<JuwetMessageListener>();
    }

    public String getId() {
        return id;
    }

    /**
     * @param id room id
     */
    public void setId(String id) {
        this.id = id;
    }

    public void send(Packet packet) {
        socket.emit(SEND, packet.toJson());
    }

    private void initMessageListener() {
        messageListener = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                PacketReader reader = new PacketReader((JSONObject) args[0]);
                if (reader.getRoom().equals(id)) {
                    for (JuwetMessageListener listener : messageListeners) {
                        listener.received(reader);
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

    /**
     * Menerima pesan
     *
     * @return JuwetConnection objek
     */
    public JuwetConnection listen() {
        if (messageListener == null) {
            initMessageListener();
            socket.on(Socket.EVENT_MESSAGE, messageListener);
        }
        socket.emit(SUBSCRIBE, id);
        return this;
    }

    public void addMessageListener(JuwetMessageListener listener) {
        messageListeners.add(listener);
    }

    public static Socket createSocket() {
        try {
            return IO.socket("http://relay.nowdb.net");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
