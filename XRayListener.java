package dev.umbra.ac.checks.xray;

import dev.umbra.ac.UmbraAC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class XRayListener implements Listener {

    private final UmbraAC plugin;
    private final XRayManager manager;

    public XRayListener(UmbraAC plugin, XRayManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!manager.isXRayEnabled()) return;
        Player player = event.getPlayer();
        PlayerData data = manager.getPlayerData(player.getUniqueId());
        data.setLastYaw(player.getLocation().getYaw());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!manager.isXRayEnabled()) return;

        Player player = event.getPlayer();
        Material type = event.getBlock().getType();
        PlayerData data = manager.getPlayerData(player.getUniqueId());

        if (isStone(type)) {
            data.incrementStone();
            return;
        }

        if (!isOre(type)) return;

        String oreName = type.name();
        data.incrementOre(oreName);

        // 1. Temel Puan
        int basePoints = manager.getOrePoints(oreName);
        data.addSuspicionPoints(basePoints);

        // 2. Yön Sapması Puanı
        double angle = calculateAngle(player, event.getBlock().getLocation());
        if (angle > manager.getAngleThreshold()) {
            data.addAngleEvent(angle, oreName);
            data.addSuspicionPoints(manager.getAngleBonus());
        }

        // 3. Oran Kontrolü
        checkRatio(data);

        // 4. Alarm Kontrolü (Merkezi AlertManager kullanarak)
        if (data.getSuspicionPoints() >= manager.getAlertThreshold()) {
            plugin.getAlertManager().sendAlert(player, "X-Ray",
                    "Puan: " + data.getSuspicionPoints() +
                            " | Elmas: " + data.getOreMined().getOrDefault("DIAMOND_ORE", 0) +
                            " | Tas: " + data.getStoneMined());

            // Aynı oyuncu için sürekli spam atmaması için puanı sıfırlayalım
            data.clearSuspicionPoints(); // Bunu PlayerData'ya ekleyeceğiz
        }
    }

    private void checkRatio(PlayerData data) {
        int totalOre = 0;
        for (int count : data.getOreMined().values()) {
            totalOre += count;
        }

        int totalStone = data.getStoneMined();
        if (totalStone < 20) return;

        double ratio = (double) totalOre / totalStone;
        if (ratio > manager.getRatioThreshold()) {
            data.addSuspicionPoints(manager.getRatioBonus());
        }
    }

    private double calculateAngle(Player player, Location oreLocation) {
        Location eye = player.getEyeLocation();
        org.bukkit.util.Vector lookDir = eye.getDirection().normalize();
        org.bukkit.util.Vector toOre = oreLocation.clone().add(0.5, 0.5, 0.5)
                .subtract(eye).toVector().normalize();
        double dot = lookDir.dot(toOre);
        dot = Math.max(-1.0, Math.min(1.0, dot));
        return Math.toDegrees(Math.acos(dot));
    }

    private boolean isStone(Material type) {
        return type == Material.STONE || type == Material.DEEPSLATE
                || type == Material.NETHERRACK || type == Material.TUFF;
    }

    private boolean isOre(Material type) {
        return type.name().endsWith("_ORE") || type == Material.ANCIENT_DEBRIS;
    }
}