package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.data.ServerInfo;
import com.github.jajanjawa.juwet.util.JuwetServerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JuwetService {

    private final Logger logger = LoggerFactory.getLogger(JuwetService.class);
    private final JuwetServerProcessor serverProcessor;
    private final JuwetConnection connection;
    private ServerInfo serverInfo;


    /**
     * @param id room id untuk server
     */
    public JuwetService(String id) {
        connection = new JuwetConnection(id);
        serverProcessor = new JuwetServerProcessor(this, connection);

        connection.connect();
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public JuwetServerProcessor getServerProcessor() {
        return serverProcessor;
    }

    public void handle(JuwetModule module) {
        serverProcessor.mapModule(module.name(), module);
    }
}
