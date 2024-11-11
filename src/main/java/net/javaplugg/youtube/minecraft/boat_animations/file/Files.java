package net.javaplugg.youtube.minecraft.boat_animations.file;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Files {

    /**
     * Скачивает файл по указанному адресу url в указанное назначение destination
     */
    @SneakyThrows
    public static void downloadFile(String url, String destination) {
        URL url_ = new URL(url);
        InputStream in = url_.openStream();
        Path destinationPath = Paths.get(destination);
        java.nio.file.Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
