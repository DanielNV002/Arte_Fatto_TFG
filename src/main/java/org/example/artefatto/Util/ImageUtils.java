package org.example.artefatto.Util;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static byte[] imageToBytes(Image image) throws IOException {
        URL url = new URL(image.getUrl());
        InputStream inputStream = url.openStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    public static Image bytesToImage(byte[] bytes) {
        return new Image(new ByteArrayInputStream(bytes));
    }

}
