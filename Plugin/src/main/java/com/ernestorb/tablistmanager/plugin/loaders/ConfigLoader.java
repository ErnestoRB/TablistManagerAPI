package com.ernestorb.tablistmanager.plugin.loaders;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ConfigLoader {

    private FileConfiguration configFile;
    private HashMap<String, List<String>> headers;

    private HashMap<String, List<String>> footers;
    private List<String> defaultFooter;
    private List<String> defaultHeader;
    private final File path;

    public ConfigLoader(File path) {
        this.path = path;
        this.newConfigFile();
        this.createIfNotExists();
        this.loadFields();
    }

    private void newConfigFile() {
        this.configFile = YamlConfiguration.loadConfiguration(path);
    }

    private void createIfNotExists() {
        if (!this.configFile.contains("headers")) {
            this.configFile.set("headers", new HashMap<String, List<String>>());
        }
        if (!this.configFile.contains("footers")) {
            this.configFile.set("footers", new HashMap<String, List<String>>());
        }
        if (!this.configFile.contains("defaultHeader")) {
            this.configFile.set("defaultHeader", List.of("&cTablist Manager"));
        }
        if (!this.configFile.contains("defaultFooter")) {
            this.configFile.set("defaultFooter", List.of("&aBy &lErnestoRB"));
        }
        this.save();
    }

    public void reloadFields() {
        this.newConfigFile();
        this.createIfNotExists();
        this.loadFields();
    }

    private void loadFields() {
        var defaultHeader = this.configFile.getStringList("defaultHeader");
        this.defaultHeader = defaultHeader == null ? new ArrayList<>() : defaultHeader;
        var defaultFooter = this.configFile.getStringList("defaultFooter");
        this.defaultFooter = defaultFooter == null ? new ArrayList<>() : defaultFooter;
        ConfigurationSection headerSection = this.configFile.getConfigurationSection("headers");
        if (headerSection != null) {
            var keys = headerSection.getKeys(false);
            headers = new HashMap<>();
            keys.forEach((key) -> headers.put(key, headerSection.getStringList(key)));
        } else {
            headers = null;
        }

        ConfigurationSection footerSection = this.configFile.getConfigurationSection("footers");
        if (footerSection != null) {
            var keys = footerSection.getKeys(false);
            footers = new HashMap<>();
            keys.forEach((key) -> footers.put(key, footerSection.getStringList(key)));
        } else {
            footers = null;
        }
    }

    private void save() {
        try {
            this.configFile.save(path);
        } catch (IOException e) {
            System.err.println("Wasn't possible to save configuration to" + path.getAbsolutePath());
        }
    }

    public List<String> getDefaultHeader() {
        return this.defaultHeader;
    }

    public Optional<List<String>> getWorldFooter(String worldName) {
        return getFooters().flatMap(stringListHashMap -> Optional.ofNullable(stringListHashMap.get(worldName)));
    }

    public Optional<List<String>> getWorldHeader(String worldName) {
        return getHeaders().flatMap(stringListHashMap -> Optional.ofNullable(stringListHashMap.get(worldName)));
    }

    public Optional<HashMap<String, List<String>>> getHeaders() {
        if (headers == null) {
            return Optional.empty();
        }
        return Optional.of(headers);
    }

    public Optional<HashMap<String, List<String>>> getFooters() {
        if (footers == null) {
            return Optional.empty();
        }
        return Optional.of(footers);
    }


    public List<String> getDefaultFooter() {
        return this.defaultFooter;
    }
}
