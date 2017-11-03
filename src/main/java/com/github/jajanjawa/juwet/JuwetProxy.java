package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.wire.Action;
import com.github.jajanjawa.juwet.wire.Message;
import com.github.jajanjawa.juwet.wire.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JuwetProxy implements InvocationHandler {

    private final Logger logger = LoggerFactory.getLogger(JuwetProxy.class);
    private Juwet juwet;
    private String module;

    /**
     * @param juwet juwet objek
     * @param module nama modul
     */
    JuwetProxy(Juwet juwet, String module) {
        this.juwet = juwet;
        this.module = module;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JuwetConnection outputConnection = juwet.getOutputConnection();
        Packet packet = new Packet(outputConnection.getId());

        Action action = new Action();
        action.setModule(module);
        action.from(method, args);

        Message message = packet.getMessage();
        message.setAction(action);
        message.setClient(juwet.getClientId());
        // server akan mengirim balasan kesini
        message.setRoom(juwet.getInputConnection().getId());

        outputConnection.send(packet);
        logger.debug("kirim paket: {}",packet.toString());

        return null;
    }

}
