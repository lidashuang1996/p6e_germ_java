package com.dyy.p6e.germ.file.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class P6eGermImgUtils {
    private BufferedImage bufferedImage;

    public static P6eGermImgUtils create(InputStream inputStream) {
        return new P6eGermImgUtils(inputStream);
    }

    public P6eGermImgUtils(InputStream inputStream) {
        try {
            this.bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSquare() {
        return getWidth() == getHeight();
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    private File generationFile(String path) throws IOException {
        File file = new File(path);
        File fileParent = file.getParentFile();
        if (fileParent != null && !fileParent.exists() && !fileParent.mkdirs())
            throw new IOException(path + " generation folder error.");
        else return file;
    }

    public void toBigImg(String format, String path) {
        try {
            BufferedImage image = new BufferedImage(240, 240, 1);
            Graphics g = image.getGraphics();
            g.drawImage(bufferedImage.getScaledInstance(240, 240, 4), 0, 0, null);
            g.dispose();
            ImageIO.write(image, format, generationFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toSmallImg(String format, String path) {
        try {
            BufferedImage image = new BufferedImage(120, 120, 1);
            Graphics g = image.getGraphics();
            g.drawImage(bufferedImage.getScaledInstance(120, 120, 4), 0, 0, null);
            g.dispose();
            ImageIO.write(image, format, generationFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
