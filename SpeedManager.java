package dev.umbra.ac.checks.speed;

import dev.umbra.ac.UmbraAC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpeedManager {

    private final UmbraAC plugin;

    public SpeedManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public boolean isSpeedEnabled() {
        return plugin.getConfig().getBoolean("speed.enabled", true);
    }

    // Oyuncunun hızlı olmasına izin verilen durumlar
    public boolean isExempt(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return true;
        if (player.isFlying() || player.isGliding() || player.isInsideVehicle()) return true;
        // Buz, ruh kumu gibi hızlandıran bloklarda veya suda yüzerken kontrolü esnet
        if (player.getLocation().getBlock().isLiquid()) return true;

        return false;
    }

    // Oyuncunun sahip olduğu hız iksiri seviyesine göre limiti ayarla
    public double getMaxSpeed(Player player) {
        double baseSpeed = plugin.getConfig().getDouble("speed.max-speed", 0.45); // Saniyedeki blok cinsinden limit

        // Hız iksiri varsa limiti arttır (Seviye 1 = %20, Seviye 2 = %40 vb.)
        if (player.hasPotionEffect(org.bukkit.potion.PotionEffectType.SPEED)) {
            int amplifier = player.getPotionEffect(org.bukkit.potion.PotionEffectType.SPEED).getAmplifier();
            baseSpeed += (baseSpeed * 0.2 * (amplifier + 1));
        }

        return baseSpeed;
    }

    public void alert(String playerName, double distance) {
        plugin.getLogger().warning("[Speed Suphesi] " + playerName
                + " | Hiz: " + String.format("%.2f", distance) + " blok/tick");
    }
}