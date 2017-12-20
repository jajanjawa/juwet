package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.util.JuwetServerMessageProcessor;

public class JuwetService {

    private final JuwetServerMessageProcessor messageProcessor;
    private final JuwetConnection connection;


    /**
     * @param id room id untuk server
     */
    public JuwetService(String id) {
        connection = new JuwetConnection(id);
        messageProcessor = new JuwetServerMessageProcessor(this, connection);

        connection.connect();
    }

    public JuwetServerMessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void handle(JuwetModule module) {
        messageProcessor.mapModule(module.name(), module);
    }

}
