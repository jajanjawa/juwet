package com.github.jajanjawa.juwet.util;

import com.github.jajanjawa.juwet.Juwet;
import com.github.jajanjawa.juwet.JuwetConnection;
import com.github.jajanjawa.juwet.JuwetExecutor;
import com.github.jajanjawa.juwet.JuwetMessageProcessor;
import com.github.jajanjawa.juwet.io.PacketReader;
import com.github.jajanjawa.juwet.wire.Exception;

/**
 * Mendengarkan pesan dari server.
 */
public class JuwetClientMessageProcessor extends JuwetMessageProcessor {
    private final Juwet juwet;
    private final String clientId;

    public JuwetClientMessageProcessor(Juwet juwet, String clientId) {
        this.juwet = juwet;
        this.clientId = clientId;

        JuwetConnection inputConnection = juwet.getInputConnection();
        inputConnection.addMessageListener(this);
        inputConnection.listen();
    }

    @Override
    public void received(PacketReader packet) {
        if (!clientId.equals(packet.getClient())) {
            return; // bukan untuk saya, klien id tidak sama
        }

        String method = packet.getMethod();
        String module = packet.getModule();

        if (packet.hasException()) {
            Exception exception = packet.getException();
            handleException(exception, method, module);
            return;
        }

        try {
            JuwetExecutor executor = getExecutor(module);
            executor.run(method, packet.getParameters());
        } catch (java.lang.Exception e) {
            logger.debug("", e);
            handleException(new Exception(e), method, module);
        }

    }

    private JuwetExecutor getExecutor(String module) {
        JuwetExecutor listener = executorMap.get(module);
        if (listener == null) {
            String msg = String.format("modul %s tidak punya listener");
            throw new UnsupportedOperationException(msg);
        }
        return listener;
    }

    private void handleException(Exception e, String method, String module) {
        ExceptionHandler handler = juwet.getExceptionHandler(e.getName());
        if (handler == null) {
            // fallback, barangkali ada
            handler = juwet.getExceptionHandler(java.lang.Exception.class);
        }

        if (handler != null) {
            handler.handle(e, method, module);
        }
    }

}
