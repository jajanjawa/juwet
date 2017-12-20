package com.github.jajanjawa.juwet.data;

import com.github.jajanjawa.juwet.util.JsonUtil;
import com.github.jajanjawa.juwet.util.NetworkUtil;

public class ServerInfo {
    private String name;
    private String ipAddress;
    private String email;
    private String phone;
    private long start;
    private long uptime;

    public ServerInfo(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * catat kapan server mulai.
     */
    public void start() {
        start = System.currentTimeMillis();
    }

    /**
     * @return kapan server mulai
     */
    public long getStart() {
        return start;
    }

    /**
     * @return berapa lama server online
     */
    public long getUptime() {
        return uptime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return JsonUtil.gson.toJson(this);
    }
}
