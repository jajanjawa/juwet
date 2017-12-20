package com.github.jajanjawa.juwet;

import com.github.jajanjawa.juwet.event.JuwetMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class JuwetMessageProcessor implements JuwetMessageListener {
    protected final Logger logger = LoggerFactory.getLogger(JuwetMessageProcessor.class);
    protected Map<String, JuwetExecutor> executorMap;

    public JuwetMessageProcessor() {
        executorMap = new HashMap<String, JuwetExecutor>();
    }


    /**
     * Modul objek bisa didapatkan dari namanya.
     * @param name nama modul
     * @param module modul objek
     */
    public void mapModule(String name, Object module) {
        JuwetExecutor executor = new JuwetExecutor(module);
        executorMap.put(name, executor);
    }



}
