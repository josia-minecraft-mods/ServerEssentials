package com.jmteam.serveressentiials.data;

import com.jmteam.serveressentiials.util.ChatUtil;

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
