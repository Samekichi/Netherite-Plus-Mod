package com.oroarmor.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Config {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final List<ConfigItemGroup> configs;
	private final File configFile;

	public Config(List<ConfigItemGroup> configs, File configFile) {
		this.configs = configs;
		this.configFile = configFile;
	}

	public void readConfigFromFile() {
		try (FileInputStream stream = new FileInputStream(configFile)) {
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			String file = new String(bytes);
			JsonObject parsed = new JsonParser().parse(file).getAsJsonObject();
			configs.stream().forEachOrdered(cig -> cig.fromJson((JsonObject) parsed.get(cig.getName())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveConfigToFile() {
		JsonObject object = new JsonObject();
		configs.stream().forEachOrdered(c -> object.add(c.getName(), c.toJson()));

		try (FileOutputStream stream = new FileOutputStream(configFile)) {
			stream.write(GSON.toJson(object).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
