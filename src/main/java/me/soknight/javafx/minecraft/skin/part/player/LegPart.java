package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.shape.Mesh;
import javafx.scene.transform.Rotate;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.part.AbstractDynamicPart;
import me.soknight.javafx.minecraft.skin.part.dependent.SideDependent.Side;

import static javafx.scene.transform.Rotate.X_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;

public final class LegPart extends AbstractDynamicPart {

    public static final Dimensions DIMENSIONS = new Dimensions(4, 12, 4);

    private final Side side;

    public LegPart(Side side) {
        this.side = side;
        initialize();
    }

    @Override
    protected Dimensions createDimensions(boolean slim) {
        return DIMENSIONS;
    }

    @Override
    protected Mesh createInnerLayer(boolean slim) {
        float startX = side.isLeft() ? 16 : 0;
        float startY = side.isLeft() ? 48 : 16;
        return createMesh(startX, startY, 16, 16, 0F, 1F, slim);
    }

    @Override
    protected Mesh createSecondLayer(boolean slim) {
        float startY = side.isLeft() ? 48 : 32;
        return createMesh(0, startY, 16, 16, 0F, 1.0625F, slim);
    }

    @Override
    protected Rotate createRotateX() {
        return new Rotate(0, 0, -6D, 0, X_AXIS);
    }

    @Override
    protected Rotate createRotateZ() {
        return new Rotate(0, 0, -6D, 0, Z_AXIS);
    }

}
