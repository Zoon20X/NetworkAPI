package me.zoon20x.network.File;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigSection {
    private Map<String, Object> configData = new HashMap<>();

    public ConfigSection(Map<String, Object> configData){
        this.configData = configData;
    }
    public int getInteger(){
        return 0;
    }
    public String getString(String value){
        return configData.get(value).toString();
    }
    public boolean getBoolean(){
        return false;
    }
    public void set(String value, Object data){
        configData.put(value, data);
    }

    public Map<String, Object> getConfigSectionData() {
        return configData;
    }
}
