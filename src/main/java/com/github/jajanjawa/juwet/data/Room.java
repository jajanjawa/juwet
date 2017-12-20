package com.github.jajanjawa.juwet.data;

import com.github.jajanjawa.juwet.util.JsonUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Room {
    public String room;
    @SerializedName("project_id")
    public String projectId;
    @SerializedName("project_name")
    public String projectName;
    public String secret;
    @SerializedName("room_name")
    public String roomName;
    public Date created;
    public String token;
    public String project;

    @Override
    public String toString() {
        return JsonUtil.gson.toJson(this);
    }
}
