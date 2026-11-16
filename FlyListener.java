package dev.umbra.ac.checks.fly;

import dev.umbra.ac.UmbraAC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlyListener implements Listener {

    private final UmbraAC plugin;
    private final FlyManager manager;
    private final Map<UUID, Integer> violationLevels = new HashMap<>();

    public FlyListener(UmbraAC plugin, FlyManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!manager.isFlyEnabled()) return;

        Player player = event.getPlayer();
        if (manager.isExempt(player)) return;

        if (player.isOnGround()) {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0);
            if (level > 0) violationLevels.put(uuid, level - 1);
            return;
        }

        double deltaY = event.getTo().getY() - event.getFrom().getY();

        if (deltaY > 0.5) {
            UUID uuid = player.getUniqueId();
            int level = violationLevels.getOrDefault(uuid, 0) + 1;
            violationLevels.put(uuid, level);

            if (level >= 3) {
                plugin.getAlertManager().sendAlert(player, "Fly", "Havada yukselme (DeltaY: " + String.format("%.2f", deltaY) + ")");
                violationLevels.put(uuid, 0);
            }
        }
    }
}