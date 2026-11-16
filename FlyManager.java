package dev.umbra.ac.checks.fly;

import dev.umbra.ac.UmbraAC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class FlyManager {

    private final UmbraAC plugin;

    public FlyManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public boolean isFlyEnabled() {
        return plugin.getConfig().getBoolean("fly.enabled", true);
    }

    // Oyuncunun uçmasına izin verilen durumlar (Yaratıcı mod, izleyici modu, uçma izni vb.)
    public boolean isExempt(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return true;
        }
        if (player.getAllowFlight()) {
            return true;
        }
        if (player.isGliding() || player.isSwimming() || player.isInsideVehicle()) {
            return true;
        }
        return false;
    }

    public void alert(String playerName, String reason) {
        plugin.getLogger().warning("[Fly Suphesi] " + playerName + " | Sebep: " + reason);
    }
}