package me.soknight.javafx.minecraft.skin.layer;

import me.soknight.javafx.minecraft.skin.Dimensions;

public final class SkinPartLayerMesh extends AbstractLayerMesh {

    public SkinPartLayerMesh(Dimensions dimensions, float startX, float startY, float texU, float texV, float enlarge, float modifier, boolean slim) {
        super(dimensions, startX, startY, texU, texV, enlarge, modifier, slim);
    }

    public SkinPartLayerMesh(float width, float height, float depth, float startX, float startY, float texU, float texV, float enlarge, float modifier, boolean slim) {
        super(width, height, depth, startX, startY, texU, texV, enlarge, modifier, slim);
    }

}
