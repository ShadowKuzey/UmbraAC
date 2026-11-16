package dev.umbra.ac.checks.reach;

import dev.umbra.ac.UmbraAC;

public class ReachManager {

    private final UmbraAC plugin;

    public ReachManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public boolean isReachEnabled() {
        return plugin.getConfig().getBoolean("reach.enabled", true);
    }

    public double getMaxReach() {
        return plugin.getConfig().getDouble("reach.max-distance", 3.5);
    }

    public int getAlertThreshold() {
        return plugin.getConfig().getInt("reach.alert-threshold", 3);
    }

    public void alert(String playerName, double distance, String targetName) {
        plugin.getLogger().warning("[Reach Suphesi] " + playerName
                + " | Mesafe: " + String.format("%.2f", distance) + " blok"
                + " | Hedef: " + targetName);
    }
}