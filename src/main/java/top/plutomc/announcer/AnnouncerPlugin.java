package top.plutomc.announcer;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AnnouncerPlugin extends JavaPlugin {
    private static JavaPlugin INSTANCE;
    private static ConfigManager configManager;

    public static JavaPlugin instance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
        configManager = new ConfigManager();
        configManager.load();
        getServer().getPluginCommand("announcer").setExecutor(new Command());
        getServer().getPluginCommand("announcer").setTabCompleter(new Command());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}