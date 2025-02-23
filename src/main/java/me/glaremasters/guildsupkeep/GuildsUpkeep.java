package me.glaremasters.guildsupkeep;

import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.api.GuildsAPI;
import me.glaremasters.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class GuildsUpkeep extends JavaPlugin {
    private GuildsAPI api;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Save default config if not exists
        saveDefaultConfig();
        config = getConfig();

        // Plugin startup logic
        final Plugin guilds = Bukkit.getServer().getPluginManager().getPlugin("Guilds");
        if (guilds != null && guilds.isEnabled()) {
            api = Guilds.getApi();
            getLogger().info("Guilds plugin found and API set.");
            scheduleUpkeepTask();
        } else {
            Bukkit.getLogger().severe("Guilds is not enabled! Disabling GuildsUpkeep...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void scheduleUpkeepTask() {
        final long interval = config.getLong("upkeep.interval", TimeUnit.DAYS.toSeconds(1));
        final long lastUpkeep = config.getLong("upkeep.last_upkeep", 0);
        final long currentTime = System.currentTimeMillis() / 1000;

        if (currentTime - lastUpkeep >= interval) {
            performUpkeep();
        }

        Bukkit.getScheduler().runTaskTimer(this, this::performUpkeep, interval * 20, interval * 20);
    }

    private void performUpkeep() {
        final long currentTime = System.currentTimeMillis() / 1000;
        config.set("upkeep.last_upkeep", currentTime);
        saveConfig();

        final double amount = config.getDouble("upkeep.amount", 1000);

        for (final Guild guild : api.getGuildHandler().getGuilds().values()) {
            final double balance = guild.getBalance();
            if (balance >= amount) {
                guild.setBalance(balance - amount);
                Bukkit.getLogger().info("Pulled " + amount + " from guild " + guild.getName());
            } else {
                Bukkit.getLogger().warning("Guild " + guild.getName() + " has gone bankrupt and will be deleted.");
                api.getGuildHandler().removeGuild(guild);
                Bukkit.broadcastMessage("Guild " + guild.getName() + " has gone bankrupt and has been deleted.");
            }
        }
    }
}
