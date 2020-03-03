package com.jmteam.serveressentiials.util;

import com.jmteam.serveressentiials.data.ServerData;

public class ServerUtils {

    public static ServerData serverData;
    public static String prefix;

    public static ServerData getServerData() {
        return serverData;
    }

    public static void saveServerData() {
        IOUtils.writeToFile("config/JMTeam/essentials", "serverData.json", (serverData  != null ? serverData : new ServerData()));
    }

    public static void readServerData() {
        serverData = (ServerData) IOUtils.readFromFile("config/JMTeam/essentials", "serverData.json", ServerData.class);
        prefix = serverData.getChatprefix().replaceAll("&", ChatUtil.makeColored()) + " ";
        saveServerData();
    }
}
