package com.jmteam.serveressentiials.server;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;

import java.net.SocketAddress;

public class DedicatedPlayerListHook extends DedicatedPlayerList {

    public DedicatedPlayerListHook(DedicatedServer server) {
        super(server);
    }

    @Override
    public String allowUserToConnect(SocketAddress address, GameProfile p) {

        String result = null;

        //result = "";

        if (result != null) return result;
        return super.allowUserToConnect(address, p);
    }
}
