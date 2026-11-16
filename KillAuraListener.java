package dev.umbra.ac.checks.killaura;

import dev.umbra.ac.UmbraAC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillAuraListener implements Listener {

    private final UmbraAC plugin;
    private final KillAuraManager manager;
    private final Map<UUID, Deque<Long>> clickHistory = new HashMap<>();

    public KillAuraListener(UmbraAC plugin, KillAuraManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!manager.isKillAuraEnabled()) return;
        if (!(event.getDamager() instanceof Player player)) return;

        Entity target = event.getEntity();
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        Deque<Long> history = clickHistory.computeIfAbsent(uuid, k -> new ArrayDeque<>());
        history.addLast(now);

        while (!history.isEmpty() && now - history.peekFirst() > 1000) {
            history.pollFirst();
        }

        if (history.size() > manager.getMaxCps()) {
            plugin.getAlertManager().sendAlert(player, "KillAura", "Cok yuksek CPS (Saniyede " + history.size() + " vurus)");
            history.clear();
            return;
        }

        org.bukkit.util.Vector lookDir = player.getEyeLocation().getDirection().normalize();
        org.bukkit.util.Vector toTarget = target.getLocation().add(0, target.getHeight() / 2, 0)
                .subtract(player.getEyeLocation()).toVector().normalize();

        double dot = lookDir.dot(toTarget);
        dot = Math.max(-1.0, Math.min(1.0, dot));
        double angle = Math.toDegrees(Math.acos(dot));

        if (angle > manager.getMaxAngle()) {
            plugin.getAlertManager().sendAlert(player, "KillAura", "Arkasina bakarak vurdu (Aci: " + String.format("%.1f", angle) + " derece)");
        }
    }
}