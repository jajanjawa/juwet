package com.github.jajanjawa.juwet.io;

import com.github.jajanjawa.juwet.util.JsonUtil;
import com.github.jajanjawa.juwet.wire.Action;
import com.github.jajanjawa.juwet.wire.Exception;
import com.github.jajanjawa.juwet.wire.Packet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import static com.github.jajanjawa.juwet.util.PacketProperty.*;

public class PacketReader {
    private final String room;
    private final JsonObject message;
    private JsonObject action;
    private JsonArray parameters;

    public PacketReader(JSONObject packet) {
        JsonObject object = JsonUtil.gson.fromJson(packet.toString(), JsonObject.class);

        room = object.get(ROOM).getAsString();
        message = object.get(MESSAGE).getAsJsonObject();
        read();
    }

    private void read() {
        readAction();
    }

    /**
     * Hanya dipakai oleh server untuk kirim balasan
     * @return paket yang sudah punya alamat tujuan.
     */
    public Packet createPacket() {
        Packet packet = new Packet(getClientRoom());
        packet.getMessage().setClient(getClient());
        return packet;
    }

    public String getClient() {
        return message.get(CLIENT).getAsString();
    }

    public boolean hasException() {
        return message.has(EXCEPTION);
    }

    public Exception getException() {
        JsonObject exception = message.getAsJsonObject(EXCEPTION);
        return JsonUtil.gson.fromJson(exception, Exception.class);
    }

    public String getRoom() {
        return room;
    }

    public String getClientRoom() {
        return message.get(ROOM).getAsString();
    }

    public JsonObject getMessage() {
        return message;
    }

    private void readAction() {
        action = message.getAsJsonObject(ACTION);
        parameters = action.getAsJsonArray(PARAMETERS);
    }

    /**
     * @return parameters atau null
     */
    public JsonArray getParameters() {
        return parameters;
    }

    public String getModule() {
        return action.get(MODULE).getAsString();
    }

    public String getMethod() {
        return action.get(METHOD).getAsString();
    }

    /**
     *
     * @return Action objek yang tidak punya parameters
     */
    public Action createAction() {
        Action action = new Action();
        action.setModule(getModule());
        action.setName(getMethod());

        return action;
    }
}
