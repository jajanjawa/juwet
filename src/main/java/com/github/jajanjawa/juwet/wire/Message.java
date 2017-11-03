package com.github.jajanjawa.juwet.wire;

import com.github.jajanjawa.juwet.util.IdGenerator;
import com.github.jajanjawa.juwet.util.JsonUtil;

public class Message {
    private Action action;
    private String client;
    private String room;
    private Exception exception;

    public String getRoom() {
        return room;
    }

    /**
     * @param room room id untuk membalas pesan ini
     */
    public void setRoom(String room) {
        this.room = room;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean hasException() {
        return exception != null;
    }

    public String getClient() {
        return client;
    }

    /**
     * @param client client id dari {@link IdGenerator#generate()}
     */
    public void setClient(String client) {
        this.client = client;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return JsonUtil.gson.toJson(this);
    }
}
