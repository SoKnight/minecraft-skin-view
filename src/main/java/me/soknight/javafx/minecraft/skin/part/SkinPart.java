package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static javafx.scene.transform.Rotate.*;

@Getter
public abstract class SkinPart extends Group {

    protected Rotate rotateX, rotateY, rotateZ;

    public abstract Dimensions3D getDimensions();

    protected void initialize() {
        this.rotateX = createRotateX();
        this.rotateY = createRotateY();
        this.rotateZ = createRotateZ();

        List<Rotate> rotates = Arrays.asList(rotateX, rotateY, rotateZ);
        rotates.removeIf(Objects::isNull);

        if (!rotates.isEmpty()) {
            getTransforms().addAll(rotates);
        }
    }

    protected Rotate createRotateX() {
        return new Rotate(0, X_AXIS);
    }

    protected Rotate createRotateY() {
        return new Rotate(0, Y_AXIS);
    }

    protected Rotate createRotateZ() {
        return new Rotate(0, Z_AXIS);
    }

    protected final SecondLayerGroup secondLayerGroup(float startX, float startY, double length, double thick) {
        Dimensions3D dimensions = getDimensions();
        int width = dimensions.width();
        int height = dimensions.height();
        int depth = dimensions.depth();
        return new SecondLayerGroup(width, height, depth, startX, startY, length, thick);
    }

}
