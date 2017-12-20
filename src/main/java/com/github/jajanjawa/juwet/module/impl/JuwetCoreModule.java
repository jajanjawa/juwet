package com.github.jajanjawa.juwet.module.impl;

import com.github.jajanjawa.juwet.JuwetModule;
import com.github.jajanjawa.juwet.JuwetService;
import com.github.jajanjawa.juwet.data.ServerInfo;
import com.github.jajanjawa.juwet.module.JuwetCoreApi;
import com.github.jajanjawa.juwet.util.NetworkUtil;

import java.util.List;

public class JuwetCoreModule implements JuwetModule, JuwetCoreApi {

    protected final JuwetService service;
    protected ServerInfo serverInfo;

    public JuwetCoreModule(JuwetService service, ServerInfo serverInfo) {
        this.service = service;
        this.serverInfo = serverInfo;

        serverInfo.start();
    }

    @Override
    public String name() {
        return JuwetCoreApi.NAME;
    }

    @Override
    public ServerInfo serverInfo() {
        serverInfo.setIpAddress(NetworkUtil.getAddress());
        serverInfo.setUptime(System.currentTimeMillis() - serverInfo.getStart());

        return serverInfo;
    }

    @Override
    public String ipAddress() {
        return NetworkUtil.getAddress();
    }

    @Override
    public List<String> listModules() {
        return service.getMessageProcessor().listModules();
    }


}
