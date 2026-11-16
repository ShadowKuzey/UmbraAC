# UmbraAC

**UmbraAC** is a lightweight, modern, and modular anti-cheat plugin for Minecraft servers. It focuses on detecting the most common cheats without causing unnecessary lag, making it perfect for small to medium-sized communities.

## ✨ Features
- **X-Ray Detection:** Uses statistical and behavioral analysis to find players mining ores abnormally.
- **Fly Check:** Detects hovering and unauthorized vertical movement.
- **Reach Check:** Prevents players from attacking entities from impossible distances.
- **Speed Check:** Catches players moving faster than vanilla limits.
- **KillAura Check:** Detects multi-aura (attacking multiple targets), high CPS (Clicks Per Second), and impossible attack angles.
- **NoFall Check:** Identifies players who take no fall damage from high altitudes.
- **Interactive Alerts:** Staff members (with OP/admin permissions) receive a clickable `[BAN]` button in chat when a player is flagged. No need to constantly watch the console!

## 📥 Installation
1. Download the latest `.jar` file from the [Releases](https://github.com/yourusername/UmbraAC/releases) page.
2. Drop the `.jar` file into your server's `plugins/` folder.
3. Start or restart your server.
4. Configure the checks and thresholds in `plugins/UmbraAC/config.yml`.

## ⚙️ Configuration
UmbraAC comes with a fully customizable `config.yml`. You can enable/disable specific checks and adjust the sensitivity for each one.

Thats All!
