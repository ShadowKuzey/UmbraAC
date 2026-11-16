package dev.umbra.ac.checks.reach;

import dev.umbra.ac.UmbraAC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReachListener implements Listener {

    private final UmbraAC plugin;
    private final ReachManager manager;
    private final Map<UUID, Integer> violationLevels = new HashMap<>();

    public ReachListener(UmbraAC plugin, ReachManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!manager.isReachEnabled()) return;
        if (!(event.getDamager() instanceof Player player)) return;

        Entity target = event.getEntity();
        double distance = player.getEyeLocation().distance(target.getLocation().add(0, target.getHeight() / 2, 0));

        if (distance > manager.getMaxReach()) {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0) + 1;
            violationLevels.put(uuid, level);

            if (level >= manager.getAlertThreshold()) {
                plugin.getAlertManager().sendAlert(player, "Reach", "Mesafe: " + String.format("%.2f", distance) + " blok | Hedef: " + target.getType().name());
                violationLevels.put(uuid, 0);
            }
        } else {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0);
            if (level > 0) violationLevels.put(uuid, level - 1);
        }
    }
}