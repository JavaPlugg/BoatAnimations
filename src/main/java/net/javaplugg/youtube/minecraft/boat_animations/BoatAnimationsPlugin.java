package net.javaplugg.youtube.minecraft.boat_animations;

import net.javaplugg.youtube.minecraft.boat_animations.command.AnimationCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class BoatAnimationsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginCommand command = Bukkit.getPluginCommand("animation");
        AnimationCommand executor = new AnimationCommand();
        assert command != null;
        command.setExecutor(executor);
    }
}