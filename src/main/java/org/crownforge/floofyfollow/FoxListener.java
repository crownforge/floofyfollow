package org.crownforge.floofyfollow;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FoxListener implements Listener {

    private final FloofyFollow plugin;
    private final Map<UUID, Long> foxCooldowns = new HashMap<>();
    private final Map<UUID, BukkitRunnable> followTasks = new HashMap<>();
    
    // Cooldown time in milliseconds (5 minutes)
    private static final long COOLDOWN_TIME = 5 * 60 * 1000;
    // Follow duration in ticks (2 minutes = 120 seconds = 2400 ticks)
    private static final long FOLLOW_DURATION = 2 * 60 * 20;

    public FoxListener(FloofyFollow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        
        // Check if the entity is a fox
        if (entity.getType() != EntityType.FOX) {
            return;
        }
        
        Fox fox = (Fox) entity;
        UUID foxId = fox.getUniqueId();
        
        // Check if the player is holding sweet berries
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType() != Material.SWEET_BERRIES) {
            return;
        }
        
        // Check if the fox is on cooldown
        if (isOnCooldown(foxId)) {
            player.sendMessage(ChatColor.RED + "This fox is still full from the last berry! Try again later.");
            event.setCancelled(true);
            return;
        }
        
        // Cancel any existing follow task for this fox
        cancelExistingFollowTask(foxId);
        
        // Consume one sweet berry
        if (itemInHand.getAmount() > 1) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
        
        // Start the fox following the player
        startFollowing(fox, player);
        
        // Set cooldown for this fox
        setCooldown(foxId);
        
        // Cancel the original interaction to prevent vanilla taming behavior
        event.setCancelled(true);
    }
    
    private void startFollowing(Fox fox, Player player) {
        // Send message to player
        player.sendMessage(ChatColor.GREEN + "The fox is now following you for 2 minutes!");
        
        // Create particles around the fox
        fox.getWorld().spawnParticle(Particle.HEART, fox.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.1);
        
        // Create a task to make the fox follow the player
        BukkitRunnable followTask = new BukkitRunnable() {
            @Override
            public void run() {
                // Check if player and fox are still valid and in the same world
                if (!player.isValid() || !fox.isValid() || !player.getWorld().equals(fox.getWorld())) {
                    this.cancel();
                    return;
                }
                
                // Make the fox move toward the player if they're not too close
                double distance = fox.getLocation().distance(player.getLocation());
                if (distance > 2 && distance < 20) {
                    // Calculate direction vector
                    Vector direction = player.getLocation().toVector().subtract(fox.getLocation().toVector()).normalize();
                    
                    // Scale to appropriate speed
                    double speed = 0.3;
                    direction.multiply(speed);
                    
                    // Apply velocity to the fox
                    fox.setVelocity(direction);
                    
                    // Occasionally spawn particles to show the fox is following
                    if (Math.random() < 0.1) {
                        fox.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, fox.getLocation().add(0, 0.7, 0), 3, 0.2, 0.2, 0.2, 0.05);
                    }
                }
            }
        };
        
        // Run the task every 10 ticks (0.5 seconds)
        followTask.runTaskTimer(plugin, 0, 10);
        
        // Store the task
        followTasks.put(fox.getUniqueId(), followTask);
        
        // Schedule task to stop following after duration
        new BukkitRunnable() {
            @Override
            public void run() {
                stopFollowing(fox.getUniqueId(), player);
            }
        }.runTaskLater(plugin, FOLLOW_DURATION);
    }
    
    private void stopFollowing(UUID foxId, Player player) {
        cancelExistingFollowTask(foxId);
        
        // Notify player if they're still online
        if (player.isOnline()) {
            player.sendMessage(ChatColor.YELLOW + "The fox has stopped following you.");
        }
    }
    
    private void cancelExistingFollowTask(UUID foxId) {
        BukkitRunnable task = followTasks.remove(foxId);
        if (task != null) {
            task.cancel();
        }
    }
    
    private boolean isOnCooldown(UUID foxId) {
        Long cooldownUntil = foxCooldowns.get(foxId);
        return cooldownUntil != null && cooldownUntil > System.currentTimeMillis();
    }
    
    private void setCooldown(UUID foxId) {
        foxCooldowns.put(foxId, System.currentTimeMillis() + COOLDOWN_TIME);
    }
}
