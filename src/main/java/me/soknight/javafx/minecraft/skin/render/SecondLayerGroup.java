package me.soknight.javafx.minecraft.skin.render;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.shape.Box;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.soknight.javafx.minecraft.skin.render.SecondLayerFace.PixelConstructor;
import me.soknight.javafx.minecraft.skin.render.SecondLayerFace.PixelPositioner;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import static me.soknight.javafx.minecraft.skin.render.SecondLayerFace.PixelConstructor.*;

@Getter @Setter
@AllArgsConstructor
public class SecondLayerGroup extends Group {

    public static final double DEFAULT_THICK = 0.02D;

    private int width, height, depth;
    private float startX, startY;
    private double length, thick;

    public void updateSkin(Image skinImage) {
        getChildren().clear();

        if (skinImage == null)
            return;

        int startX = (int) (this.startX * skinImage.getWidth());
        int startY = (int) (this.startY * skinImage.getHeight());

        int scale = (int) Math.max(skinImage.getWidth() / 64, 1);
        int scaledWidth = width * scale;
        int scaledHeight = height * scale;
        int scaledDepth = depth * scale;

        addFace(skinImage, startX + scaledDepth, startY + scaledDepth, width, height, false, FRONT_BACK, this::positionFrontPixel);
        addFace(skinImage, startX + scaledWidth + scaledDepth * 2, startY + scaledDepth, width, height, true, FRONT_BACK, this::positionBackPixel);
        addFace(skinImage, startX + scaledWidth + scaledDepth, startY + scaledDepth, depth, height, false, LEFT_RIGHT, this::positionLeftPixel);
        addFace(skinImage, startX, startY + scaledDepth, depth, height, true, LEFT_RIGHT, this::positionRightPixel);
        addFace(skinImage, startX + scaledDepth, startY, width, depth, false, TOP_BOTTOM, this::positionTopPixel);
        addFace(skinImage, startX + scaledWidth + scaledDepth, startY, width, depth, false, TOP_BOTTOM, this::positionBottomPixel);
    }

    public void updateDimensions(Dimensions3D dimensions) {
        this.width = dimensions.width();
        this.height = dimensions.height();
        this.depth = dimensions.depth();
    }

    private void positionFrontPixel(Box pixel, int x, int y) {
        pixel.setTranslateX(((width - 1) / 2D - x) * pixel.getWidth());
        pixel.setTranslateY(-((height - 1) / 2D - y) * pixel.getHeight());
        pixel.setTranslateZ((depth * length + thick) / 2.0);
    }

    private void positionBackPixel(Box pixel, int x, int y) {
        pixel.setTranslateX(((width - 1) / 2.0 - x) * pixel.getWidth());
        pixel.setTranslateY(-((height - 1) / 2.0 - y) * pixel.getHeight());
        pixel.setTranslateZ(-(depth * length + thick) / 2.0);
    }

    private void positionLeftPixel(Box pixel, int x, int y) {
        pixel.setTranslateX((width * length + thick) / 2.0);
        pixel.setTranslateY(-((height - 1) / 2.0 - y) * pixel.getHeight());
        pixel.setTranslateZ(((depth - 1) / 2.0 - x) * pixel.getDepth());
    }

    private void positionRightPixel(Box pixel, int x, int y) {
        pixel.setTranslateX(-(width * length + thick) / 2.0);
        pixel.setTranslateY(-((height - 1) / 2.0 - y) * pixel.getHeight());
        pixel.setTranslateZ(((depth - 1) / 2.0 - x) * pixel.getDepth());
    }

    private void positionTopPixel(Box pixel, int x, int y) {
        pixel.setTranslateX(((width - 1) / 2.0 - x) * pixel.getWidth());
        pixel.setTranslateY(-(height * length + thick) / 2.0);
        pixel.setTranslateZ(-((depth - 1) / 2.0 - y) * pixel.getDepth());
    }

    private void positionBottomPixel(Box pixel, int x, int y) {
        pixel.setTranslateX(((width - 1) / 2.0 - x) * pixel.getWidth());
        pixel.setTranslateY((height * length + thick) / 2.0);
        pixel.setTranslateZ(-((depth - 1) / 2.0 - y) * pixel.getDepth());
    }

    private void addFace(
            Image skinImage,
            int startX, int startY,
            int width, int height,
            boolean reverseX,
            PixelConstructor constructor,
            PixelPositioner positioner
    ) {
        getChildren().add(new SecondLayerFace(
                skinImage,
                startX, startY,
                width, height,
                length, thick,
                reverseX,
                constructor, positioner
        ));
    }

}
