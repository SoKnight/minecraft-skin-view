package me.soknight.javafx.minecraft.skin.part.player;

import me.soknight.javafx.minecraft.skin.part.StaticSkinPart;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import static me.soknight.javafx.minecraft.skin.render.SecondLayerGroup.DEFAULT_THICK;

public class BodySkinPart extends StaticSkinPart {

    public BodySkinPart() {
        super(new Dimensions3D(8, 12, 4));
        initialize();
    }

    @Override
    protected InnerLayerMesh createInnerLayer() {
        return innerLayerMesh(24F / 64F, 16F / 64F, 16F / 64F, 16F / 64F, 0.05F);
    }

    @Override
    protected SecondLayerGroup createSecondLayer() {
        return secondLayerGroup(16F / 64F, 32F / 64F, 1.0625D, DEFAULT_THICK);
    }

}
