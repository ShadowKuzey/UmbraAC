package dev.umbra.ac;

import dev.umbra.ac.checks.xray.XRayListener;
import dev.umbra.ac.checks.xray.XRayManager;
import dev.umbra.ac.checks.reach.ReachListener;
import dev.umbra.ac.checks.reach.ReachManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import dev.umbra.ac.checks.fly.FlyListener;
import dev.umbra.ac.checks.fly.FlyManager;
import dev.umbra.ac.checks.killaura.KillAuraListener;
import dev.umbra.ac.checks.killaura.KillAuraManager;
import dev.umbra.ac.checks.nofall.NoFallListener;
import dev.umbra.ac.checks.nofall.NoFallManager;
import dev.umbra.ac.checks.speed.SpeedListener;
import dev.umbra.ac.checks.speed.SpeedManager;

public class UmbraAC extends JavaPlugin {

    private XRayManager xrayManager;
    private ReachManager reachManager;
    private FlyManager flyManager;
    private KillAuraManager killAuraManager;
    private NoFallManager noFallManager;
    private SpeedManager speedManager;
    private AlertManager alertManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Speed Modulu
        speedManager = new SpeedManager(this);
        getServer().getPluginManager().registerEvents(new SpeedListener(this, speedManager), this);
        // NoFall Modulu
        noFallManager = new NoFallManager(this);
        getServer().getPluginManager().registerEvents(new NoFallListener(this, noFallManager), this);
        // Kill Aura Modulu
        killAuraManager = new KillAuraManager(this);
        getServer().getPluginManager().registerEvents(new KillAuraListener(this, killAuraManager), this);
        // Fly Modulu
        flyManager = new FlyManager(this);
        getServer().getPluginManager().registerEvents(new FlyListener(this, flyManager), this);
        // X-Ray Modulu
        xrayManager = new XRayManager(this);
        getServer().getPluginManager().registerEvents(new XRayListener(this, xrayManager), this);
        // Reach Modulu
        reachManager = new ReachManager(this);
        getServer().getPluginManager().registerEvents(new ReachListener(this, reachManager), this);
        // Mesaj Modulu
        alertManager = new AlertManager(this);
        getLogger().info("UmbraAC basariyla aktif edildi!");
    }

    @Override
    public void onDisable() {
        if (xrayManager != null) {
            xrayManager.saveData();
        }
        getLogger().info("UmbraAC devre disi birakildi.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("umbra")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                sender.sendMessage("§a[UmbraAC] Konfigurasyon yeniden yuklendi.");
                return true;
            }
            sender.sendMessage("§6[UmbraAC] Kullanim: /umbra reload");
            return true;
        }
        return false;
    }
    public AlertManager getAlertManager() {
        return alertManager;
    }
    public XRayManager getXrayManager() {
        return xrayManager;
    }

    public ReachManager getReachManager() {
        return reachManager;
        }
    }


