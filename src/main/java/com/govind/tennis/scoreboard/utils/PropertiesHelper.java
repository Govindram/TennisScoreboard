package com.govind.tennis.scoreboard.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    public static final String PROVIDER = "provider";
    public static final String EXPORTER = "exporter";

    public static Properties loadProperties(){
        Properties props = new Properties();
        try (InputStream inputStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("runtime.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
