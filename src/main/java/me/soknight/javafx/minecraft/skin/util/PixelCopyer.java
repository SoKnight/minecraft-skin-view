package me.soknight.javafx.minecraft.skin.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class PixelCopyer {

    private final Image sourceImage;
    private final WritableImage destImage;

    public PixelCopyer(Image sourceImage, WritableImage destImage) {
        this.sourceImage = sourceImage;
        this.destImage = destImage;
    }

    public void copy(int x, int y, int width, int height) {
        copy(x, y, x, y, width, height);
    }

    public void copy(int x1, int y1, int x2, int y2, int width, int height) {
        copy(x1, y1, x2, y2, width, height, false, false);
    }

    public void copy(int x1, int y1, int x2, int y2, int width, int height, boolean rX, boolean rY) {
        PixelReader reader = sourceImage.getPixelReader();
        PixelWriter writer = destImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tX = x1 + (rX ? width - x - 1 : x);
                int tY = y1 + (rY ? height - y - 1 : y);
                writer.setArgb(x2 + x, y2 + y, reader.getArgb(tX, tY));
            }
        }
    }

    public void copy(float x1, float y1, float x2, float y2, float width, float height) {
        copy(x1, y1, x2, y2, width, height, false, false);
    }

    public void copy(float x1, float y1, float x2, float y2, float width, float height, boolean rX, boolean rY) {
        PixelReader reader = sourceImage.getPixelReader();
        PixelWriter writer = destImage.getPixelWriter();

        int sourceScaleX = (int) sourceImage.getWidth();
        int sourceScaleY = (int) sourceImage.getHeight();
        int destScaleX = (int) destImage.getWidth();
        int destScaleY = (int) destImage.getHeight();

        int sourceWidth  = (int) (width * sourceScaleX);
        int sourceHeight = (int) (height * sourceScaleY);

        for (int x = 0; x < sourceWidth; x++) {
            for (int y = 0; y < sourceHeight; y++) {
                int tX = (int) (x1 * sourceScaleX + (rX ? sourceWidth - x - 1 : x));
                int tY = (int) (y1 * sourceScaleY + (rY ? sourceHeight - y - 1 : y));
                writer.setArgb((int) (x2 * destScaleX + x), (int) (y2 * destScaleY + y), reader.getArgb(tX, tY));
            }
        }
    }

}
