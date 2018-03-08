package config;


import java.util.Properties;

public class Config {

    Properties config;

    public Config(String path) {
        config = new Properties();

        try {
            config.load(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    public String getProperty(String key) {
        return this.config.getProperty(key);
    }
}