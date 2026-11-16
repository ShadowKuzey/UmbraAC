package dev.umbra.ac.checks.nofall;

import dev.umbra.ac.UmbraAC;

public class NoFallManager {

    private final UmbraAC plugin;

    public NoFallManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public boolean isNoFallEnabled() {
        return plugin.getConfig().getBoolean("nofall.enabled", true);
    }

    // Hasar alınmamasına rağmen şüphe çekecek minimum düşüş mesafesi (blok)
    public double getMinFallDistance() {
        return plugin.getConfig().getDouble("nofall.min-fall-distance", 5.0);
    }

    public void alert(String playerName, double fallDistance) {
        plugin.getLogger().warning("[NoFall Suphesi] " + playerName
                + " | Dusus Mesafesi: " + String.format("%.2f", fallDistance) + " blok (Hasar Alinmadi)");
    }
}