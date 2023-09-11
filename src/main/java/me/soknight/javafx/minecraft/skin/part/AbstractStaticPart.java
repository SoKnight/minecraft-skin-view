package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.shape.Mesh;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.SkinMeshView;
import me.soknight.javafx.minecraft.skin.layer.SkinPartLayerMesh;

@Getter
public abstract class AbstractStaticPart extends AbstractSkinPart {

    protected final Dimensions dimensions;
    private SkinMeshView innerLayer, secondLayer;

    protected AbstractStaticPart(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.innerLayer = new SkinMeshView(dimensions, createInnerLayer());
        this.secondLayer = new SkinMeshView(dimensions, createSecondLayer());

        getChildren().addAll(secondLayer, innerLayer);
    }

    protected abstract Mesh createInnerLayer();

    protected abstract Mesh createSecondLayer();

    protected final SkinPartLayerMesh createMesh(float startX, float startY, float texU, float texV, float enlarge, float modifier) {
        return new SkinPartLayerMesh(dimensions, startX, startY, texU, texV, enlarge, modifier, false);
    }

}
