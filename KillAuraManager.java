package dev.umbra.ac.checks.killaura;

import dev.umbra.ac.UmbraAC;

public class KillAuraManager {

    private final UmbraAC plugin;

    public KillAuraManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public boolean isKillAuraEnabled() {
        return plugin.getConfig().getBoolean("killaura.enabled", true);
    }

    // Maksimum tıklama hızı (saniyede)
    public int getMaxCps() {
        return plugin.getConfig().getInt("killaura.max-cps", 16);
    }

    // Maksimum vuruş açısı (derece cinsinden)
    public double getMaxAngle() {
        return plugin.getConfig().getDouble("killaura.max-angle", 90.0);
    }

    public void alert(String playerName, String reason) {
        plugin.getLogger().warning("[KillAura Suphesi] " + playerName + " | Sebep: " + reason);
    }
}