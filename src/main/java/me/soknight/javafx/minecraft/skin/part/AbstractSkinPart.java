package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.SkinMeshView;

import static javafx.scene.transform.Rotate.*;

@Getter
public abstract class AbstractSkinPart extends Group {

    protected Rotate rotateX, rotateY, rotateZ;

    protected AbstractSkinPart() {
    }

    protected void initialize() {
        this.rotateX = createRotateX();
        this.rotateY = createRotateY();
        this.rotateZ = createRotateZ();
    }

    public abstract Dimensions getDimensions();

    public abstract SkinMeshView getInnerLayer();

    public abstract SkinMeshView getSecondLayer();

    protected Rotate createRotateX() {
        return new Rotate(0, X_AXIS);
    }

    protected Rotate createRotateY() {
        return new Rotate(0, Y_AXIS);
    }

    protected Rotate createRotateZ() {
        return new Rotate(0, Z_AXIS);
    }

}
