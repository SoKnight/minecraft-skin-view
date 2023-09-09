package me.soknight.javafx.minecraft.skin.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ImageUtils {

    public static final int BLACK_COLOR_ARGB = -16777216;

    public static boolean hasNoRequestedSize(Image image) {
        return image.getRequestedWidth() == 0 && image.getRequestedHeight() == 0;
    }

    public static boolean isMinecraftSkin(Image image) {
        double width = image.getWidth();
        double height = image.getHeight();
        return width % 64 == 0 && width / 64 > 0 && (height == width / 2 || height == width);
    }

    public static Image x32ToX64(Image source) {
        if (source.getHeight() == 64)
            return source;

        WritableImage dest = new WritableImage((int) source.getWidth(), (int) source.getHeight() * 2);
        PixelCopyer copyer = new PixelCopyer(source, dest);

        copyer.copy(0 / 64F, 0 / 32F, 0 / 64F, 0 / 64F, 64 / 64F, 16 / 32F);
        x32ToX64(copyer, 0 / 64F, 16 / 32F, 16 / 64F, 48 / 64F, 4 / 64F, 12 / 32F, 4 / 64F);
        copyer.copy(0 / 64F, 16 / 32F, 0 / 64F, 16 / 64F, 16 / 64F, 16 / 32F);
        copyer.copy(16 / 64F, 16 / 32F, 16 / 64F, 16 / 64F, 24 / 64F, 16 / 32F);
        x32ToX64(copyer, 40 / 64F, 16 / 32F, 32 / 64F, 48 / 64F, 4 / 64F, 12 / 32F, 4 / 64F);
        copyer.copy(40 / 64F, 16 / 32F, 40 / 64F, 16 / 64F, 16 / 64F, 16 / 32F);

        return dest;
    }

    public static void x32ToX64(PixelCopyer copyer, float x1, float y1, float x2, float y2, float width, float height, float depth) {
        copyer.copy(x1 + depth, y1, x2 + depth, y2, width, depth * 2, true, false);
        copyer.copy(x1 + depth + width, y1, x2 + depth + width, y2, width, depth * 2, true, false);
        copyer.copy(x1, y1 + depth * 2, x2 + width + depth, y2 + depth, depth, height, true, false);
        copyer.copy(x1 + width + depth, y1 + depth * 2, x2, y2 + depth, depth, height, true, false);
        copyer.copy(x1 + depth, y1 + depth * 2, x2 + depth, y2 + depth, width, height, true, false);
        copyer.copy(x1 + width + depth * 2, y1 + depth * 2, x2 + width + depth * 2, y2 + depth, width, height, true, false);
    }

    public static Image removeSemitransparentPixels(Image source) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();

        WritableImage clean = new WritableImage(width, height);
        PixelReader reader = source.getPixelReader();
        PixelWriter writer = clean.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int readValue = reader.getArgb(x, y);
                int alpha = 0xFF & (readValue >> 24);
                if (alpha == 0 || alpha == 255) {
                    writer.setArgb(x, y, readValue);
                } else {
                    writer.setArgb(x, y, BLACK_COLOR_ARGB);
                }
            }
        }

        return clean;
    }

    public static Image upscale(Image source, int multiple) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();

        WritableImage enlarged = new WritableImage(width * multiple, height * multiple);
        PixelReader reader = source.getPixelReader();
        PixelWriter writer = enlarged.getPixelWriter();

        for (int x = 0; x < width; x++) {
            int multipliedX = x * multiple;
            for (int y = 0; y < height; y++) {
                int multipliedY = y * multiple;
                int readValue = reader.getArgb(x, y);

                for (int mX = 0; mX < multiple; mX++) {
                    for (int mY = 0; mY < multiple; mY++) {
                        writer.setArgb(multipliedX + mX, multipliedY + mY, readValue);
                    }
                }
            }
        }

        return enlarged;
    }

}
