package dev.umbra.ac.checks.speed;

import dev.umbra.ac.UmbraAC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpeedListener implements Listener {

    private final UmbraAC plugin;
    private final SpeedManager manager;
    private final Map<UUID, Integer> violationLevels = new HashMap<>();

    public SpeedListener(UmbraAC plugin, SpeedManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!manager.isSpeedEnabled()) return;

        Player player = event.getPlayer();
        if (manager.isExempt(player)) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        double distance = Math.sqrt(Math.pow(to.getX() - from.getX(), 2) + Math.pow(to.getZ() - from.getZ(), 2));

        if (distance > 10.0) return;

        double maxSpeed = manager.getMaxSpeed(player);

        if (distance > maxSpeed) {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0) + 1;
            violationLevels.put(uuid, level);

            if (level >= 3) {
                plugin.getAlertManager().sendAlert(player, "Speed", "Hiz: " + String.format("%.2f", distance) + " blok/tick");
                violationLevels.put(uuid, 0);
            }
        } else {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0);
            if (level > 0) violationLevels.put(uuid, level - 1);
        }
    }
}