package com.github.jajanjawa.juwet.util;

import com.github.jajanjawa.juwet.JuwetConnection;
import com.github.jajanjawa.juwet.JuwetExecutor;
import com.github.jajanjawa.juwet.JuwetProcessor;
import com.github.jajanjawa.juwet.JuwetService;
import com.github.jajanjawa.juwet.io.PacketReader;
import com.github.jajanjawa.juwet.wire.Action;
import com.github.jajanjawa.juwet.wire.Exception;
import com.github.jajanjawa.juwet.wire.Message;
import com.github.jajanjawa.juwet.wire.Packet;

import java.util.ArrayList;
import java.util.List;

public class JuwetServerProcessor extends JuwetProcessor {

    private final JuwetConnection connection;

    public JuwetServerProcessor(JuwetService service, JuwetConnection connection) {
        this.connection = connection;
        connection.addMessageListener(this);
        connection.listen();
    }

    public List<String> listModules() {
        ArrayList<String> list = new ArrayList<String>();
        for (String module : executorMap.keySet()) {
            list.add(module);
        }
        return list;
    }

    private void sendActionResult(Object result, PacketReader reader) {
        Packet packet = reader.createPacket();

        Action action = reader.createAction();
        action.addParameter(result);

        packet.getMessage().setAction(action);

        sendResponse(packet);
    }

    private void sendException(java.lang.Exception ex, PacketReader reader) {
        Packet packet = reader.createPacket();
        Message message = packet.getMessage();

        Action action = reader.createAction();
        message.setAction(action);

        Exception exception = new Exception(ex);
        message.setException(exception);

        sendResponse(packet);
    }

    private void sendResponse(Packet packet) {
        connection.send(packet);
        logger.debug("kirim paket: {}", packet.toString());
    }

    private JuwetExecutor getExecutor(String module) throws JuwetModuleNotFoundException {
        JuwetExecutor executor = executorMap.get(module);
        if (executor == null) {
            String msg = "tidak menemukan modul: " + module;
            throw new JuwetModuleNotFoundException(msg);
        }
        return executor;
    }

    @Override
    public void received(PacketReader reader) {
        String client = reader.getClient();
        String module = reader.getModule();

        logger.debug("terima pesan dari: {}", client);

        try {
            JuwetExecutor executor = getExecutor(module);
            Object result = executor.run(reader.getMethod(), reader.getParameters());
            if (result != null) {
                sendActionResult(result, reader);
            }
        } catch (java.lang.Exception e) {
            logger.debug("terjadi kesalahan saat kirim balasan", e);
            sendException(e, reader);
        }
    }
}
