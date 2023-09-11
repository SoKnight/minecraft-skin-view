package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.shape.Mesh;
import javafx.scene.transform.Rotate;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.part.AbstractStaticPart;

import static javafx.scene.transform.Rotate.X_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;

public final class HeadPart extends AbstractStaticPart {

    public static final Dimensions DIMENSIONS = new Dimensions(8, 8, 8);

    public HeadPart() {
        this(DIMENSIONS);
    }

    public HeadPart(Dimensions dimensions) {
        super(dimensions);
        initialize();
    }

    @Override
    protected Mesh createInnerLayer() {
        return createMesh(0, 0, 32, 16, 0F, 1F);
    }

    @Override
    protected Mesh createSecondLayer() {
        return createMesh(32, 0, 32, 16, 0F, 1.125F);
    }

    @Override
    protected Rotate createRotateX() {
        return new Rotate(0, 0, 4D, 0, X_AXIS);
    }

    @Override
    protected Rotate createRotateZ() {
        return new Rotate(0, 0, 4D, 0, Z_AXIS);
    }

}
