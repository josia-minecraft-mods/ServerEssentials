package com.jmteam.serveressentiials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jmteam.serveressentiials.commands.player.CommandNick;
import com.jmteam.serveressentiials.commands.player.CommandWarp;
import com.jmteam.serveressentiials.commands.staff.CommandFly;
import com.jmteam.serveressentiials.commands.util.CommandReloadRanks;
import com.jmteam.serveressentiials.commands.util.CommandSetRank;
import com.jmteam.serveressentiials.commands.util.CommandSetWarp;
import com.jmteam.serveressentiials.server.DedicatedPlayerListHook;
import com.jmteam.serveressentiials.util.PlayerUtils;
import com.jmteam.serveressentiials.util.RankUtils;
import com.jmteam.serveressentiials.util.ServerUtils;
import com.jmteam.serveressentiials.util.WarpUtils;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ServerEssentials.MODID, version = ServerEssentials.VERSION, acceptableRemoteVersions = "*", serverSideOnly = true)
public class ServerEssentials {

    private static Logger logger;
    public static final String MODID = "serveressentiials";
    public static final String VERSION = "1.0";
    public static Gson JSON = (new GsonBuilder()).enableComplexMapKeySerialization().setPrettyPrinting().create();

    @EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        ServerUtils.readServerData();
        RankUtils.loadRanks();
        WarpUtils.loadWarps();

        PlayerList plist = new DedicatedPlayerListHook((DedicatedServer) FMLCommonHandler.instance().getMinecraftServerInstance());
        plist.setPlayerManager(FMLCommonHandler.instance().getMinecraftServerInstance().worlds);
        FMLCommonHandler.instance().getMinecraftServerInstance().setPlayerList(plist);

        // Commands
        e.registerServerCommand(new CommandReloadRanks());
        e.registerServerCommand(new CommandSetRank());
        e.registerServerCommand(new CommandFly());
        e.registerServerCommand(new CommandSetWarp());
        e.registerServerCommand(new CommandWarp());
        e.registerServerCommand(new CommandNick());
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent e) {
        PlayerUtils.deopAll(FMLCommonHandler.instance().getMinecraftServerInstance());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
