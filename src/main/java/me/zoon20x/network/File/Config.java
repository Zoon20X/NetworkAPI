package me.zoon20x.network.File;

import me.zoon20x.network.logging.Logging;
import me.zoon20x.network.logging.Severity;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config implements ConfigUtils {
    private File configFile;
    private Map<String, Object> configData = new HashMap<>();
    private Map<String, ConfigSection> configSection = new HashMap<>();

    public Config(String location) {
        this.configFile = new File(location);

    }

    public boolean exists() {
        return configFile.exists();
    }

    public void load() {
        try (FileInputStream fis = new FileInputStream(configFile)) {
            LoaderOptions loaderOptions = new LoaderOptions();
            Yaml yaml = new Yaml(new Constructor(Map.class, loaderOptions));
            configData = yaml.load(fis);
            Logging.log("Loaded server configuration", Severity.Debug);
            for (String s : configData.keySet()) {
                if (!(configData.get(s) instanceof String)) {
                    getConfigurationSection(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logging.log("Failed to read the configuration file", Severity.Critical);
        }
    }

    public void save() {
        for (String value : configSection.keySet()) {
            configData.put(value, configSection.get(value).getConfigSectionData());
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(configData, writer);
            Logging.log("Generated and saved new server configuration", Severity.Debug);

        } catch (IOException e) {
            e.printStackTrace();
            Logging.log("Failed to save the configuration file.", Severity.Critical);


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

    public File getConfigFile() {
        return configFile;
    }

    @Override
    public ConfigSection getConfigurationSection(String value) {
        if (!configData.containsKey(value)) {
            configData.put(value, new HashMap<String, Object>());
        }
        if (!configSection.containsKey(value)) {
            configSection.put(value, new ConfigSection((Map<String, Object>) configData.get(value)));
        }
        return configSection.get(value);
    }
}