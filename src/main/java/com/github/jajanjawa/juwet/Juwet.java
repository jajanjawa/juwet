package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.event.JuwetStateListener;
import com.github.jajanjawa.juwet.util.ExceptionHandler;
import com.github.jajanjawa.juwet.util.IdGenerator;
import com.github.jajanjawa.juwet.util.JuwetClientMessageProcessor;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.lang.reflect.Proxy;
import java.util.HashMap;

public class Juwet {

    private final JuwetConnection outputConnection;
    private final String clientId;
    private final JuwetConnection inputConnection;
    private final Socket socket;
    private final JuwetClientMessageProcessor clientListener;
    private final HashMap<String, ExceptionHandler> exceptionHandlers;

    /**
     * @param server server room id
     * @param client klien room id
     */
    public Juwet(String server, String client) {
        socket = JuwetConnection.createSocket();
        outputConnection = new JuwetConnection(socket, server);
        inputConnection = new JuwetConnection(socket, client);

        clientId = IdGenerator.generate();

        clientListener = new JuwetClientMessageProcessor(this, clientId);

        exceptionHandlers = new HashMap<String, ExceptionHandler>();

        socket.connect();
    }


    public void setStateListener(final JuwetStateListener l) {
        socket.off(Socket.EVENT_CONNECT);
        Emitter.Listener connect = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                l.ready();
            }
        };
        socket.on(Socket.EVENT_CONNECT, connect);


        socket.off(Socket.EVENT_DISCONNECT);
        Emitter.Listener disconnect = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                l.disconnected();
            }
        };
        socket.on(Socket.EVENT_DISCONNECT, disconnect);
    }

    public void stop() {
        socket.disconnect();
    }

    public void start() {
        socket.connect();
    }

    public String getClientId() {
        return clientId;
    }

    /**
     * @return koneksi untuk kirim pesan ke server.
     */
    public JuwetConnection getOutputConnection() {
        return outputConnection;
    }

    /**
     * Koneksi milik klien
     *
     * @return koneksi untuk terima pesan dari server
     */
    public JuwetConnection getInputConnection() {
        return inputConnection;
    }


    /**
     * @param <T>      class dari api
     * @param name     nama modul
     * @param api      interface api
     * @param listener penerima pesan
     * @return proxy dari class api yang diberikan
     */
    @SuppressWarnings("unchecked")
    public <T> T create(String name, Class<T> api, Object listener) {
        Class[] interfaces = {api};

        JuwetProxy proxy = new JuwetProxy(this, name);
        clientListener.mapModule(name, listener);

        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, proxy);
    }

    /**
     * Pindah room
     *
     * @param room room baru untuk klien
     */
    public void migrate(String room) {
        inputConnection.setId(room);
        inputConnection.listen();
    }

    public void handleException(Class<? extends Exception> e, ExceptionHandler handler) {
        exceptionHandlers.put(e.getName(), handler);
    }

    public ExceptionHandler getExceptionHandler(Class<? extends Exception> e) {
        return exceptionHandlers.get(e.getName());
    }

    public ExceptionHandler getExceptionHandler(String className) {
        return exceptionHandlers.get(className);
    }

}
