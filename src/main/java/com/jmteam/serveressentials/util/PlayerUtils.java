package com.jmteam.serveressentials.util;

import com.jmteam.serveressentials.ServerEssentials;
import com.jmteam.serveressentials.data.PlayerData;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerUtils {

    public static Map<EntityPlayer, PlayerData> players = new HashMap<>();

    public static void savePlayerData(EntityPlayer player, boolean logout) {
        savePlayerData(players.get(player));
    }

    public static void savePlayerData(PlayerData data) {
        File dir = new File(((File) ((File) FMLInjectionData.data()[6])) + "/config/JMTeam/essentials/players/");
        File file = new File(dir.getPath() + "//" + data.getUuid() + ".json");

        if (!file.exists()) {
            dir.mkdirs();
        }
        String json = ServerEssentials.JSON.toJson(data);

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayerData(EntityPlayer player) {
        File dir = new File(((File) ((File) FMLInjectionData.data()[6])) + "/config/JMTeam/essentials/players/");
        File file = new File(dir.getPath() + "//" + player.getUniqueID().toString() + ".json");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                PlayerData data = new PlayerData();
                data.setUuid(player.getUniqueID().toString());
                writer.write(ServerEssentials.JSON.toJson(data));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                return ServerEssentials.JSON.fromJson(br, PlayerData.class);
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void opUser(MinecraftServer server, GameProfile gameprofile) {
        server.getPlayerList().addOp(gameprofile);
    }

    public static void deopUser(MinecraftServer server, GameProfile gameProfile) {
        server.getPlayerList().removeOp(gameProfile);
    }

    public static void deopAll(MinecraftServer server) {
        if (!server.getPlayerList().getOppedPlayers().isEmpty()) {
            for (String ops : server.getPlayerList().getOppedPlayers().getKeys()) {
                GameProfile profile = server.getPlayerList().getOppedPlayers().getGameProfileFromName(ops);
                server.getPlayerList().getOppedPlayers().removeEntry(profile);
            }
        }
    }

    public static Map<EntityPlayer, PlayerData> getPlayers() {
        return players;
    }

    public static void refreshPlayers() {
        players.clear();
        for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
            players.put(player, loadPlayerData(player));
        }
    }

    public static void checkPlayerRanks() {
        for (Map.Entry<EntityPlayer, PlayerData> entry : players.entrySet()) {
            RankUtils.doesRankForPlayerExist(entry.getKey());
        }
    }

    public static PlayerData getPlayerData(EntityPlayerMP playerMP) {
        return players.get(playerMP);
    }
}
