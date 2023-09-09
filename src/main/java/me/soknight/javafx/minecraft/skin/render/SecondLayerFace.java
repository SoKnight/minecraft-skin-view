package me.soknight.javafx.minecraft.skin.render;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class SecondLayerFace extends Group {

    public SecondLayerFace(
            Image image,
            int startX, int startY,
            int width, int height,
            double length, double thick,
            boolean reverseX,
            PixelConstructor constructor,
            PixelPositioner positioner
    ) {
        PixelReader reader = image.getPixelReader();
        int argb, tX, tY;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tX = startX + (reverseX ? width - x - 1 : x);
                tY = startY + y;

                if ((argb = reader.getArgb(tX, tY)) != 0) {
                    Box pixel = constructor.construct(length, thick);
                    pixel.setMaterial(createMaterial(argb));
                    positioner.applyTranslation(pixel, x, y);
                    getChildren().add(pixel);
                }
            }
        }
    }

    protected Material createMaterial(int argb) {
        return new PhongMaterial(Color.rgb((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF));
    }

    @FunctionalInterface
    public interface PixelConstructor {

        PixelConstructor FRONT_BACK = (length, thick) -> new Box(length, length, thick);
        PixelConstructor LEFT_RIGHT = (length, thick) -> new Box(thick, length, length);
        PixelConstructor TOP_BOTTOM = (length, thick) -> new Box(length, thick, length);

        Box construct(double length, double thick);

    }

    @FunctionalInterface
    public interface PixelPositioner {

        void applyTranslation(Box pixel, int x, int y);

    }

}
