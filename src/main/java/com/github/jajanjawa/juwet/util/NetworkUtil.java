package com.github.jajanjawa.juwet.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtil {
    /**
     * alamat ip yang digabung dengan : sebagai pemisah.
     * @return alamat ip
     */
    public static String getAddress() {
        StringBuilder builder = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        builder.append(hostAddress);
                        builder.append(':');
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        int index = builder.lastIndexOf(":");
        if (index != -1) {
            builder.deleteCharAt(index);
        }

        return builder.toString();
    }
}
