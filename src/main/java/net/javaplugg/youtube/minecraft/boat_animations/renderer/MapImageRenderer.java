package net.javaplugg.youtube.minecraft.boat_animations.renderer;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Класс для рендера картинки на карте
 */
@RequiredArgsConstructor
public class MapImageRenderer extends MapRenderer {

    // Принимаем картинку в конструкторе (@RequiredArgsConstructor)
    private final BufferedImage bufferedImage;

    @Override
    @SuppressWarnings("NullableProblems")
    public void render(MapView map, MapCanvas canvas, Player player) {
        // Уменьшаем картинку до размеров 128 на 128 пикселей
        Image scaled = bufferedImage.getScaledInstance(128, 128, Image.SCALE_DEFAULT);

        // Рисуем картинку на карте
        canvas.drawImage(0, 0, scaled);
    }
}
