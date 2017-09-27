package com.github.jajanjawa.juwet.wire;

import com.github.jajanjawa.juwet.util.JsonUtil;
import org.json.JSONObject;

public class Packet {
    private String room;
    private Message message;

    public static Packet from(JSONObject object) {
        return JsonUtil.gson.fromJson(object.toString(), Packet.class);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
