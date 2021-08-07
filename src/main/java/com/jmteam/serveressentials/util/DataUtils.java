package com.jmteam.serveressentials.util;

import com.jmteam.serveressentials.ServerEssentials;
import com.jmteam.serveressentials.data.PlayerData;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.*;

public class DataUtils {

    public static void writeDataFileToJson(Object object, String path, String j) {
        File dir = new File(((File) FMLInjectionData.data()[6]) + "/mods/JMTeam/essentials/" + path + "/");
        File file = new File(dir.getPath() + "//" + j + ".json");

        if (!file.exists()) {
            dir.mkdirs();
        }
        String json = ServerEssentials.JSON.toJson(object);

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadDataFileFromJson(Class object, String path, String j) {
        File dir = new File(((File) FMLInjectionData.data()[6]) + "/mods/JMTeam/essentials/" + path + "/");
        File file = new File(dir.getPath() + "//" + j + ".json");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    return ServerEssentials.JSON.fromJson(br, object);
                } finally {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
