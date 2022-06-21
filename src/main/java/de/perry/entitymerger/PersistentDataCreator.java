package de.perry.entitymerger;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataCreator implements Listener {

    @EventHandler
    public void onSpawn (EntitySpawnEvent e){
        Entity entity = e.getEntity();

        entity.getPersistentDataContainer().set(new NamespacedKey(EntityMerger.getPlugin(), "isJockey"), PersistentDataType.BYTE, (byte) 0);
    }
}
