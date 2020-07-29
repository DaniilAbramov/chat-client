package utils;

import java.io.IOException;
import java.util.Properties;

public class Props {

    private final static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Props.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
