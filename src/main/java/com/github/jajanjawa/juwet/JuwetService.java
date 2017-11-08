package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.util.JuwetServerProcessor;

public class JuwetService {

    private final JuwetServerProcessor serverProcessor;
    private final JuwetConnection connection;


    /**
     * @param id room id untuk server
     */
    public JuwetService(String id) {
        connection = new JuwetConnection(id);
        serverProcessor = new JuwetServerProcessor(this, connection);

        connection.connect();
    }

    public JuwetServerProcessor getServerProcessor() {
        return serverProcessor;
    }

    public void handle(JuwetModule module) {
        serverProcessor.mapModule(module.name(), module);
    }
}
