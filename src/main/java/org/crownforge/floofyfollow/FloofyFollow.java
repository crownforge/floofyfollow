package org.crownforge.floofyfollow;

import org.bukkit.plugin.java.JavaPlugin;

public class FloofyFollow extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register the event listener
        getServer().getPluginManager().registerEvents(new FoxListener(this), this);
        
        // Log plugin startup
        getLogger().info("FloofyFollow has been enabled!");
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        getLogger().info("FloofyFollow has been disabled!");
    }
}
