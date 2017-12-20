package com.github.jajanjawa.juwet.module;

import com.github.jajanjawa.juwet.data.Room;
import com.github.jajanjawa.juwet.data.ServerInfo;

import java.io.IOException;
import java.util.List;

public interface JuwetCoreApi {
    String NAME = "jajanjawa.juwet.core";

    ServerInfo serverInfo();

    /**
     * @return alamat ip server
     */
    String ipAddress();

    List<String> listModules();

}
