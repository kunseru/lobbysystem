package com.github.decompilen.lobbysystem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class LobbyConfiguration {

    private File configFile;
    private final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().disableHtmlEscaping().create();
    private JsonObject rootObject = new JsonObject();

    public LobbyConfiguration(String fileName) {
        this.configFile = new File(fileName + ".json");
        if(!configFile.exists()) {
            save();
        }
    }

    public LobbyConfiguration(String path, String fileName) {
        this.configFile = new File(path, fileName + ".json");
        if(!configFile.exists()) {
            save();
        }
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(rootObject));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            this.rootObject = gson.fromJson(new FileReader(configFile), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasKey(String key) {
        return rootObject.has(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        return gson.fromJson(rootObject.get(key), clazz);
    }

    public <T> void append(String key, T value, boolean fullUpdate) {
        if(hasKey(key)) {
            if(fullUpdate) {
                rootObject.add(key, gson.toJsonTree(value));
            }
        } else {
            rootObject.add(key, gson.toJsonTree(value));
        }
    }

    public <T> List<T> getList(String key, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        for (Object o : gson.fromJson(rootObject.get(key), List.class)) {
            list.add(gson.fromJson(gson.toJson(o), (Type) clazz));
        }
        return list;
    }
}
