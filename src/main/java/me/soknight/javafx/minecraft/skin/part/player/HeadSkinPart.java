package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.transform.Rotate;
import me.soknight.javafx.minecraft.skin.part.StaticSkinPart;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import static javafx.scene.transform.Rotate.X_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;
import static me.soknight.javafx.minecraft.skin.render.SecondLayerGroup.DEFAULT_THICK;

public class HeadSkinPart extends StaticSkinPart {

    public HeadSkinPart() {
        super(new Dimensions3D(8, 8, 8));
        initialize();
    }

    @Override
    protected InnerLayerMesh createInnerLayer() {
        return innerLayerMesh(32F / 64F, 16F / 64F, 0F, 0F);
    }

    @Override
    protected SecondLayerGroup createSecondLayer() {
        return secondLayerGroup(32F / 64F, 0F, 1.125D, DEFAULT_THICK);
    }

    @Override
    protected Rotate createRotateX() {
        return new Rotate(0, 0, 4, 0, X_AXIS);
    }

    @Override
    protected Rotate createRotateZ() {
        return new Rotate(0, 0, 4, 0, Z_AXIS);
    }

}
