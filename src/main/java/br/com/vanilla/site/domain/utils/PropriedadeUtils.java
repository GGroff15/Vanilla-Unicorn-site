package br.com.vanilla.site.domain.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropriedadeUtils {

	private PropriedadeUtils() {
	}

	public static String get(String key) {
		Properties props = new Properties();
		try (BufferedReader fis = Files.newBufferedReader(Paths.get("/vanilla_config/props.properties"))) {
			props.load(fis);
		} catch (IOException e) {
		}
		return props.getProperty(key, "");
	}

}
