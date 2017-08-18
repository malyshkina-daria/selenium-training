package utils;

import java.io.IOException;
import java.util.Properties;

class PropertyLoader {
    private static final String PROP_FILE = "/application.properties";

    static String loadProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(PropertyLoader.class.getResourceAsStream(PROP_FILE));
        } catch (IOException ignored) {
        }
        String value = "";
        if (name != null) {
            value = props.getProperty(name);
        }
        return value;
    }
}
