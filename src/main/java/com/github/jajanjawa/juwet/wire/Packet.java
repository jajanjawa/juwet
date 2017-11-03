package com.github.jajanjawa.juwet.wire;

import com.github.jajanjawa.juwet.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class Packet {
    private String room;
    private Message message;

    public Packet(String room) {
        this.room = room;
        message = new Message();
    }

    public static Packet from(JSONObject object) {
        return JsonUtil.gson.fromJson(object.toString(), Packet.class);
    }

    @Override
    public String toString() {
        return JsonUtil.gson.toJson(this);
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(toString());
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Sudah ada saat membuat paket.
     * @return pesan kosong
     */
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
