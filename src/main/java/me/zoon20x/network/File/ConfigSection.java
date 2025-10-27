package me.zoon20x.network.File;

import me.zoon20x.network.logging.Logger;
import me.zoon20x.network.logging.Severity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigSection implements ConfigUtils{
    private final Map<String, Object> configData;
    private final Map<String, ConfigSection> configSection;

    public ConfigSection(Map<String, Object> configData) {
        this.configData = configData;
        this.configSection = new HashMap<>();

        for(String s : configData.keySet()){
            if(configData.get(s) instanceof Map){
                addConfigSection(s, (Map<String, Object>) configData.get(s));
            }
        }
    }

    @Override
    public int getInteger(String value) {
        return (int) configData.get(value);

    }
    @Override
    public String getString(String value) {
        return (String) configData.get(value);
    }
    @Override
    public boolean getBoolean(String value) {
        return (boolean) configData.get(value);
    }
    @Override
    public double getDouble(String value) {
        return (double) configData.get(value);
    }
    @Override
    public void set(String value, Object data) {
        configData.put(value, data);
    }

    @Override
    public List<String> getStringList(String value) {
        return (List<String>) configData.get(value);
    }


    private void addConfigSection(String value, Map<String, Object> map){
        configSection.put(value, new ConfigSection(map));
    }

    public ConfigSection getConfigurationSection(String value) {
        if(!configData.containsKey(value)){
            configData.put(value, new HashMap<String, Object>());
        }
        if(!configSection.containsKey(value)){
            configSection.put(value, new ConfigSection((Map<String, Object>) configData.get(value)));
        }
        return configSection.get(value);
    }

    public Map<String, Object> getConfigSectionData() {
        return configData;
    }
}
