package dev.umbra.ac.checks.nofall;

import dev.umbra.ac.UmbraAC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallListener implements Listener {

    private final UmbraAC plugin;
    private final NoFallManager manager;

    public NoFallListener(UmbraAC plugin, NoFallManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!manager.isNoFallEnabled()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        double fallDistance = player.getFallDistance();

        if (fallDistance >= manager.getMinFallDistance() && event.getDamage() <= 0.5) {
            plugin.getAlertManager().sendAlert(player, "NoFall", "Dusus Mesafesi: " + String.format("%.2f", fallDistance) + " blok | Alinan Hasar: " + event.getDamage());
        }
    }
}