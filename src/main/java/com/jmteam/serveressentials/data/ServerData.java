package com.jmteam.serveressentials.data;

import com.jmteam.serveressentials.util.ChatUtil;

public class ServerData {

    private String chatprefix = "[Essentials]";

    public ServerData() {

    }

    public void setChatprefix(String chatprefix) {
        this.chatprefix = chatprefix;
    }

    public String getChatprefix() {
        return ChatUtil.getColored(chatprefix);
    }
}
