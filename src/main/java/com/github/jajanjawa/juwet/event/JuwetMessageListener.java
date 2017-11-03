package com.github.jajanjawa.juwet.event;

import com.github.jajanjawa.juwet.io.PacketReader;

public interface JuwetMessageListener {

    public void received(PacketReader reader);
}
