package de.perry.entitymerger;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CreeperJockeyMerger extends BukkitRunnable {
    public static boolean rapidhoppingcreeper = false;

    public static double distance = 3;

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            List<Zombie> zombies = world.getLivingEntities().stream().filter(e -> e.getType() == EntityType.ZOMBIE).map(e -> (Zombie) e).filter(e-> e.getPassengers().size()==0).toList();
            for (Zombie zombie : zombies) {
                for (Entity nearbyEntity : zombie.getNearbyEntities(distance, distance, distance)) {


                    if (nearbyEntity.getType()!=EntityType.CREEPER)
                        continue;
                    if (nearbyEntity.getPassengers().size()>0)
                        continue;
                    if (!nearbyEntity.getNearbyEntities(1,1,1).stream().anyMatch(e -> e.getPassengers().stream().anyMatch(p -> p.getUniqueId().equals(nearbyEntity.getUniqueId())))){
                        nearbyEntity.getPersistentDataContainer().set(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE, (byte) 0);
                    }
                    if (nearbyEntity.getPersistentDataContainer().has(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE)){
                        if (!rapidhoppingcreeper && nearbyEntity.getPersistentDataContainer().get(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE) == 1)
                            continue;
                    }
                    if (nearbyEntity.getLocation().distance(zombie.getLocation())>distance)
                        continue;

                    zombie.addPassenger(nearbyEntity);
                    break;
                }
            }
        }
    }
}
