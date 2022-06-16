package de.perry.entitymerger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
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
                    if (!rapidhoppingskeleton && nearbyEntity.getNearbyEntities(1,1,1).stream().anyMatch(e->e.getPassengers().stream().anyMatch(p->p.getUniqueId().equals(nearbyEntity.getUniqueId()))))
                        continue;
                    if (nearbyEntity.getLocation().distance(spider.getLocation())>distance)
                        continue;

                    spider.addPassenger(nearbyEntity);
                    break;
                }
            }
        }
    }
}
