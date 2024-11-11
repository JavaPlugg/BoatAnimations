package net.javaplugg.youtube.minecraft.boat_animations.map;

import net.javaplugg.youtube.minecraft.boat_animations.renderer.MapImageRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class Maps {

    /**
     * @param image Картинка
     * @return Карта, изображающая эту картинку
     */
    public static ItemStack getMapItem(BufferedImage image) {
        MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
        mapView.addRenderer(new MapImageRenderer(image));

        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        assert mapMeta != null;

        mapMeta.setMapView(mapView);
        map.setItemMeta(mapMeta);
        return map;
    }
}
