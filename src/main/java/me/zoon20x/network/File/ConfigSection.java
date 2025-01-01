package me.zoon20x.network.File;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigSection {
    private Map<String, Object> configData = new HashMap<>();

    public ConfigSection(Map<String, Object> configData) {
        this.configData = configData;
    }

    public int getInteger(String value) {
        return (int) configData.get(value);

    }

    public String getString(String value) {
        return (String) configData.get(value);
    }

    public boolean getBoolean(String value) {
        return (boolean) configData.get(value);
    }

    public double getDouble(String value) {
        return (double) configData.get(value);
    }

    public void set(String value, Object data) {
        configData.put(value, data);
    }


    public Map<String, Object> getConfigSectionData() {
        return configData;
    }
}
