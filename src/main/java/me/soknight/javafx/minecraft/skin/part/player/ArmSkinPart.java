package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.transform.Rotate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.part.DynamicSkinPart;
import me.soknight.javafx.minecraft.skin.part.SkinPartSide;
import me.soknight.javafx.minecraft.skin.part.dependent.SideDependent;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import static javafx.scene.transform.Rotate.X_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;
import static me.soknight.javafx.minecraft.skin.render.SecondLayerGroup.DEFAULT_THICK;

@Getter
public class ArmSkinPart extends DynamicSkinPart implements SideDependent {

    private final SkinPartSide side;

    public ArmSkinPart(SkinPartSide side) {
        this.side = side;
        initialize();
    }

    @Override
    protected Dimensions3D createDimensions(boolean slim) {
        int width = slim ? 3 : 4;
        return new Dimensions3D(width, 12, 4);
    }

    @Override
    protected InnerLayerMesh createInnerLayer(boolean slim) {
        float scaleX = slim ? 14F / 64F : 16F / 64F;
        float startX = side.isLeft() ? 32F / 64F : 40F / 64F;
        float startY = side.isLeft() ? 48F / 64F : 16F / 64F;
        return innerLayerMesh(scaleX, 16F / 64F, startX, startY, slim);
    }

    @Override
    protected SecondLayerGroup createSecondLayer() {
        float startX = side.isLeft() ? 48F / 64F : 40F / 64F;
        float startY = side.isLeft() ? 48F / 64F : 32F / 64F;
        return secondLayerGroup(startX, startY, 1.0625D, DEFAULT_THICK);
    }

    @Override
    protected Rotate createRotateX() {
        return new Rotate(0, 0, -6, 0, X_AXIS);
    }

    @Override
    protected Rotate createRotateZ() {
        int pivotX = side.isLeft() ? 2 : -2;
        return new Rotate(0, pivotX, -6, 0, Z_AXIS);
    }

}
