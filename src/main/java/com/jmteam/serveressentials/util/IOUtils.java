package com.jmteam.serveressentials.util;

import com.jmteam.serveressentials.ServerEssentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class IOUtils {

    public static void writeToFile(String dir, String name, Object object) {
        File direction = new File(dir);
        File f = new File(direction.getPath() + "/" + name);

        if (!direction.exists()) {
            direction.mkdirs();
        }

        try {
            FileWriter fileWriter = new FileWriter(f, false);
            fileWriter.write(ServerEssentials.JSON.toJson(object));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readFromFile(String dir, String name, Class object) {
        File direction = new File(dir);
        File f = new File(direction.getPath() + "/" + name);

        try {
            if (!f.exists()) f.createNewFile();

            FileReader reader = new FileReader(f);
            BufferedReader reader1 = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = "";

            while ((line = reader1.readLine()) != null) {
                builder.append(line);
            }

            reader1.close();
            return ServerEssentials.JSON.fromJson(builder.toString(), object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
