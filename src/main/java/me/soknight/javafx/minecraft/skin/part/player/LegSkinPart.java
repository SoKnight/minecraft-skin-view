package me.soknight.javafx.minecraft.skin.part.player;

import javafx.scene.transform.Rotate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.part.SkinPartSide;
import me.soknight.javafx.minecraft.skin.part.StaticSkinPart;
import me.soknight.javafx.minecraft.skin.part.dependent.SideDependent;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import static javafx.scene.transform.Rotate.X_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;
import static me.soknight.javafx.minecraft.skin.render.SecondLayerGroup.DEFAULT_THICK;

@Getter
public class LegSkinPart extends StaticSkinPart implements SideDependent {

    private final SkinPartSide side;

    public LegSkinPart(SkinPartSide side) {
        super(new Dimensions3D(4, 12, 4));
        this.side = side;
        initialize();
    }

    @Override
    protected InnerLayerMesh createInnerLayer() {
        float startX = side.isLeft() ? 16F / 64F : 0F;
        float startY = side.isLeft() ? 48F / 64F : 16F / 64F;
        return innerLayerMesh(16F / 64F, 16F / 64F, startX, startY);
    }

    @Override
    protected SecondLayerGroup createSecondLayer() {
        float startY = side.isLeft() ? 48F / 64F : 32F / 64F;
        return secondLayerGroup(0F, startY, 1.0625F, DEFAULT_THICK);
    }

    @Override
    protected Rotate createRotateX() {
        return new Rotate(0, 0, -6, 0, X_AXIS);
    }

    @Override
    protected Rotate createRotateY() {
        return new Rotate(0, 0, -6, 0, Z_AXIS);
    }

}
