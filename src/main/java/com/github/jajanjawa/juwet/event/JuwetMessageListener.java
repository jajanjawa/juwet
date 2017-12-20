package com.github.jajanjawa.juwet.event;

import com.github.jajanjawa.juwet.io.PacketReader;

public interface JuwetMessageListener {

    void received(PacketReader reader);
}
