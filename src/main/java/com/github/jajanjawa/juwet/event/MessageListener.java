package com.github.jajanjawa.juwet.event;

import com.github.jajanjawa.juwet.wire.Message;

public interface MessageListener {
    public void onFailure(Throwable throwable);

    public void onSucces(Message message);
}
