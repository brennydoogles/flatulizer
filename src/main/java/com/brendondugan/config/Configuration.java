package com.brendondugan.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
	private static Properties properties;
	private static boolean initialized = false;


	public static String getValueOrDefault(String key, String defaultValue) {
		return properties.getOrDefault(key, defaultValue).toString();
	}

	public static void initialize() throws IOException {
		if (!initialized) {
			File propertiesFile = new File("./flatulizer.properties");
			boolean propertiesFileExists = propertiesFile.isFile();
			properties = new Properties();
			if (propertiesFileExists) {
				properties.load(new FileInputStream(propertiesFile));
			} else {
				propertiesFile.createNewFile();
			}
			getConfigDefaults().forEach((key, value) -> {
				if (!properties.containsKey(key)) {
					properties.setProperty(key, value);
				}
			});
			properties.store(new FileOutputStream(propertiesFile), null);
			initialized = true;
		}
	}

	private static Map<String, String> getConfigDefaults() {
		Map<String, String> defaults = new HashMap<>();
		defaults.put(ConfigurationKey.DEFAULT_SOUND_DELAY, "700");
		defaults.put(ConfigurationKey.SOUND_LIBRARY_PATH, buildDefaultFartDirectoryPath());
		return defaults;
	}

	private static String buildDefaultFartDirectoryPath() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.getProperty("user.home"))
				.append(File.separator)
				.append("flatulizer-library");
		return stringBuilder.toString();
	}

	public static File getDefaultFartDirectory() throws IOException {
		String pathname = properties.getProperty(ConfigurationKey.SOUND_LIBRARY_PATH);
		File file = new File(pathname);
		if (!file.exists()) {
			file.mkdirs();
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			InputStream resourceAsStream = contextClassLoader.getResourceAsStream("fart.wav");
			Path target = Paths.get(String.format("%s/fart.wav", pathname));
			Files.copy(resourceAsStream, target, StandardCopyOption.REPLACE_EXISTING);
		}

		return file;
	}

}
