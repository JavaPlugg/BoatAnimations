package net.javaplugg.youtube.minecraft.boat_animations.command;

import net.javaplugg.youtube.minecraft.boat_animations.file.Files;
import net.javaplugg.youtube.minecraft.boat_animations.gif.Gifs;
import net.javaplugg.youtube.minecraft.boat_animations.map.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class AnimationCommand implements CommandExecutor {

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (args.length < 1) {
            return true;
        }
        try {
            String url = args[0];
            String destination = "animation.gif";
            Files.downloadFile(url, destination);
            List<BufferedImage> images = Gifs.splitGif(new File(destination));

            /*
                Ставим все кадры гифки. Если их меньше 1000, повторяем гифку, пока кадров не будет как минимум 1000
                Нужно, чтобы лодка успела разогнаться
             */
            int n = (int) Math.ceil(1000.0 / images.size());
            for (int i = 0; i < n; i++) {
                for (int f = 0; f < images.size(); f++) {
                    int distance = f + i * images.size();
                    Location location = player.getLocation().clone().add(distance, 0, 0);
                    placeFrame(location, images.get(f));
                }
            }

        } catch (Exception e) {
            player.sendMessage("An error occurred");
            e.printStackTrace(System.out);
        }
        return true;
    }

    /**
     * Ставит 1 кадр гифки
     * @param location локация
     * @param bufferedImage кадр
     */
    private void placeFrame(Location location, BufferedImage bufferedImage) {
        // Ставим блоки
        location.clone().add(0, -1, 0).getBlock().setType(Material.PACKED_ICE);
        placeStairs(location.clone().add(0, -1, 1), BlockFace.SOUTH, false);
        placeStairs(location.clone().add(0, -1, -1), BlockFace.NORTH, false);
        placeStairs(location.clone().add(0, 0, 2), BlockFace.SOUTH, false);
        placeStairs(location.clone().add(0, 0, -2), BlockFace.NORTH, false);
        location.clone().add(0, 1, 3).getBlock().setType(Material.SPRUCE_PLANKS);
        location.clone().add(0, 1, -3).getBlock().setType(Material.SPRUCE_PLANKS);
        placeStairs(location.clone().add(0, 2, 2), BlockFace.SOUTH, true);
        placeStairs(location.clone().add(0, 2, -2), BlockFace.NORTH, true);
        placeStairs(location.clone().add(0, 3, 1), BlockFace.SOUTH, true);
        placeStairs(location.clone().add(0, 3, -1), BlockFace.NORTH, true);
        location.clone().add(0, 4, 0).getBlock().setType(Material.SPRUCE_PLANKS);

        // Ставим рамки
        ItemStack map = Maps.getMapItem(bufferedImage);
        placeItemFrame(location.clone().add(0, 1, 2), BlockFace.SOUTH, map.clone());
        placeItemFrame(location.clone().add(0, 1, -2), BlockFace.NORTH, map.clone());
    }

    /**
     * Ставит ступеньки
     * @param location локация
     * @param blockFace направление
     * @param upsideDown перевернутая
     */
    private void placeStairs(Location location, BlockFace blockFace, boolean upsideDown) {
        Stairs stairs = (Stairs) Bukkit.createBlockData(Material.SPRUCE_STAIRS);
        stairs.setFacing(blockFace);
        stairs.setShape(Stairs.Shape.STRAIGHT);
        stairs.setHalf(upsideDown ? Bisected.Half.TOP : Bisected.Half.BOTTOM);
        location.getBlock().setBlockData(stairs);
    }

    /**
     * Ставит рамку
     * @param location локация
     * @param blockFace направление
     * @param itemStack предмет в рамке
     */
    private void placeItemFrame(Location location, BlockFace blockFace, ItemStack itemStack) {
        World world = location.getWorld();
        assert world != null;
        ItemFrame itemFrame = world.spawn(location, ItemFrame.class);
        itemFrame.setFacingDirection(blockFace);
        itemFrame.setItem(itemStack);
    }
}
