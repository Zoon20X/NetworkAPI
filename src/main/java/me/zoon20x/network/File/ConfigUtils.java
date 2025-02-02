package me.zoon20x.network.File;

import java.util.List;

public interface ConfigUtils{

    int getInteger(String value);
    String getString(String value);
    boolean getBoolean(String value);

    double getDouble(String value);

    void set(String value, Object data);

    List<String> getStringList(String value);

    ConfigSection getConfigurationSection(String value);

}
