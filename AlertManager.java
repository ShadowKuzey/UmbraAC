package dev.umbra.ac;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AlertManager {

    private final UmbraAC plugin;

    public AlertManager(UmbraAC plugin) {
        this.plugin = plugin;
    }

    public void sendAlert(Player suspect, String checkName, String details) {
        // Konsola da yazalım (loglar için önemli)
        plugin.getLogger().warning("[UmbraAC] " + suspect.getName() + " | " + checkName + " | " + details);

        // Sunucudaki tüm yetkililere (OP) mesaj gönder
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("umbra.admin")) {

                // Mesajı oluştur
                TextComponent mainMessage = new TextComponent("§8[§cUmbraAC§8] §e" + suspect.getName() + " §7adli oyuncuda §c" + checkName + " §7suphesi!");
                TextComponent detailMessage = new TextComponent("\n§8» §7Detay: §f" + details);

                // Ban komutu için tıklanabilir buton
                TextComponent banButton = new TextComponent("\n§8[§cBANLA§8] §7(Yetkiye sahipseniz banlamak icin tiklayin)");
                banButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ban " + suspect.getName() + " " + checkName + " hilesi"));
                banButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c" + suspect.getName() + " adli oyuncuyu banla").create()));

                // Mesajları birleştir ve gönder
                mainMessage.addExtra(detailMessage);
                mainMessage.addExtra(banButton);

                onlinePlayer.spigot().sendMessage(mainMessage);
            }
        }
    }
}