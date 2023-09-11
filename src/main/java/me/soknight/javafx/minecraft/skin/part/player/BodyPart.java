package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.shape.Mesh;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.part.AbstractStaticPart;

public final class BodyPart extends AbstractStaticPart {

    public static final Dimensions DIMENSIONS = new Dimensions(8, 12, 4);

    public BodyPart() {
        this(DIMENSIONS);
    }

    public BodyPart(Dimensions dimensions) {
        super(dimensions);
        initialize();
    }

    @Override
    protected Mesh createInnerLayer() {
        return createMesh(16, 16, 24, 16, 0.03F, 1F);
    }

    @Override
    protected Mesh createSecondLayer() {
        return createMesh(16, 32, 24, 16, 0.03F, 1F);
    }

}
