package dev.umbra.ac.checks.xray;

import dev.umbra.ac.UmbraAC;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class XRayManager {

    private final UmbraAC plugin;
    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public XRayManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, k -> new PlayerData());
    }

    public boolean isXRayEnabled() {
        return plugin.getConfig().getBoolean("xray.enabled", true);
    }

    public double getAngleThreshold() {
        return plugin.getConfig().getDouble("xray.angle-threshold", 47.0);
    }

    public int getAlertThreshold() {
        return plugin.getConfig().getInt("xray.alert-threshold", 50);
    }

    public int getAngleBonus() {
        return plugin.getConfig().getInt("xray.angle-bonus", 3);
    }

    public int getRatioBonus() {
        return plugin.getConfig().getInt("xray.ratio-bonus", 10);
    }

    public double getRatioThreshold() {
        return plugin.getConfig().getDouble("xray.ratio-threshold", 0.15);
    }

    public int getOrePoints(String materialName) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("xray.ore-points");
        if (section != null && section.contains(materialName)) {
            return section.getInt(materialName);
        }
        return 0;
    }

    public void saveData() {
        // İleride verileri diske kaydetmek için kullanılacak (şu an hafızada tutuyoruz)
    }
}