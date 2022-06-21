package de.perry.entitymerger;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SkeletonJockeyMerger extends BukkitRunnable {
    public static boolean rapidhoppingskeleton = false;

    public static double distance = 3;

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            List<Spider> spiders = world.getLivingEntities().stream().filter(e -> e.getType() == EntityType.SPIDER).map(e -> (Spider) e).filter(e-> e.getPassengers().size()==0).toList();
            for (Spider spider : spiders) {
                for (Entity nearbyEntity : spider.getNearbyEntities(distance, distance, distance)) {


                    if (nearbyEntity.getType()!=EntityType.SKELETON)
                        continue;
                    if (nearbyEntity.getPassengers().size()>0)
                        continue;
                    if (nearbyEntity.getPersistentDataContainer().has(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE)){
                        if (!rapidhoppingskeleton && nearbyEntity.getPersistentDataContainer().get(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE) == 1)
                            continue;
                    }
                    if (nearbyEntity.getLocation().distance(spider.getLocation())>distance)
                        continue;

                    spider.addPassenger(nearbyEntity);
                    nearbyEntity.getPersistentDataContainer().set(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE, (byte) 1);
                    break;
                }
            }
        }
    }
}
