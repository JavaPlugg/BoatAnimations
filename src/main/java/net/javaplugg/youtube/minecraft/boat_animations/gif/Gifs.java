package net.javaplugg.youtube.minecraft.boat_animations.gif;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Gifs {

    /**
     * <p>Разделяет гифку на отдельные кадры</p>
     * <p>Гифка оптимизирована таким образом, что она не хранит все свои кадры полностью, а полностью хранит только
     * начальный кадр, а для всех последующих хранит только те пиксели, что изменились. Поэтому при чтении каждого кадра
     * необходимо накладывать его на предыдущий кадр</p>
     *
     * @param file Файл с гифкой
     * @return Кадры гифки
     */
    @SneakyThrows
    public static List<BufferedImage> splitGif(File file) {
        List<BufferedImage> gif = new ArrayList<>();

        // Получаем ридер для гифок
        ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
        // Ридер будет читать наш файл
        reader.setInput(ImageIO.createImageInputStream(new FileInputStream(file)), false);

        // Конвертируем кадр в формат поддерживающий прозрачность и добавляем в список
        BufferedImage lastImage = convertToARGB(reader.read(0));
        gif.add(clone(lastImage));

        for (int i = 1; i < reader.getNumImages(true); i++) {
            BufferedImage readImage = convertToARGB(reader.read(i));

            // Накладываем следующий кадр на предыдущий
            Graphics graphics = lastImage.getGraphics();
            graphics.drawImage(readImage, 0, 0, null);
            graphics.dispose();

            gif.add(clone(lastImage));
        }
        return gif;
    }

    /**
     * Конвертирует картинку в формат ARGB, поддерживающий прозрачность
     */
    private static BufferedImage convertToARGB(BufferedImage bufferedImage) {
        BufferedImage result = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        result.getGraphics().drawImage(bufferedImage, 0, 0, null);
        return result;
    }

    /**
     * Клонирует картинку
     */
    private static BufferedImage clone(BufferedImage bufferedImage) {
        BufferedImage result = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        result.getGraphics().drawImage(bufferedImage, 0, 0, null);
        return result;
    }
}
