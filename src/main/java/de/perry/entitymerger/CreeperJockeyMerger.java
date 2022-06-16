package de.perry.entitymerger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
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
                    if (!rapidhoppingcreeper && nearbyEntity.getNearbyEntities(1,1,1).stream().anyMatch(e->e.getPassengers().stream().anyMatch(p->p.getUniqueId().equals(nearbyEntity.getUniqueId()))))
                        continue;
                    if (nearbyEntity.getLocation().distance(zombie.getLocation())>distance)
                        continue;

                    zombie.addPassenger(nearbyEntity);
                    break;
                }
            }
        }
    }
}
