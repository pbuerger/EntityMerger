package de.perry.entitymerger;

import me.unleqitq.commandframework.CommandManager;
import me.unleqitq.commandframework.building.command.FrameworkCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityMerger extends JavaPlugin {

    public static CommandManager commandManager;
    @Override
    public void onEnable() {
        commandManager = new CommandManager(this);
        registerCommands();
        new SkeletonJockeyMerger().runTaskTimer(this, 20L, 5L);
        new CreeperJockeyMerger().runTaskTimer(this, 20L, 5L);
    }

    private void registerCommands(){
        commandManager.register(FrameworkCommand.commandBuilder("skeletonhopping").permission("EntityMerger.skeletonhopping").handler(c->{
            SkeletonJockeyMerger.rapidhoppingskeleton = !SkeletonJockeyMerger.rapidhoppingskeleton;
            c.getSender().sendMessage("§e§l(!) Skeleton hopping is set to " + SkeletonJockeyMerger.rapidhoppingskeleton);
            return true;
        }));
        commandManager.register(FrameworkCommand.commandBuilder("creeperhopping").permission("EntityMerger.creeperhopping").handler(c->{
            CreeperJockeyMerger.rapidhoppingcreeper = !CreeperJockeyMerger.rapidhoppingcreeper;
            c.getSender().sendMessage("§e§l(!) Creeper hopping is set to " + SkeletonJockeyMerger.rapidhoppingskeleton);
            return true;
        }));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
