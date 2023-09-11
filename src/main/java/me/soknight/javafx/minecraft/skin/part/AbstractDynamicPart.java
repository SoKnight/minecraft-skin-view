package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.shape.Mesh;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.Dimensions;
import me.soknight.javafx.minecraft.skin.SkinMeshView;
import me.soknight.javafx.minecraft.skin.layer.SkinPartLayerMesh;
import me.soknight.javafx.minecraft.skin.part.dependent.SlimDependent;

public abstract class AbstractDynamicPart extends AbstractSkinPart implements SlimDependent {

    @Getter
    private SkinMeshView innerLayer, secondLayer;

    private Pair<Dimensions> dimensions;
    private Pair<Mesh> innerLayerMesh, secondLayerMesh;

    private Boolean slim;

    @Override
    protected void initialize() {
        this.innerLayer = new SkinMeshView();
        this.secondLayer = new SkinMeshView();

        this.dimensions = new Pair<>(this::createDimensions);

        super.initialize();

        this.innerLayerMesh = new Pair<>(this::createInnerLayer);
        this.secondLayerMesh = new Pair<>(this::createSecondLayer);

        getChildren().setAll(innerLayer, secondLayer);
    }

    @Override
    public Dimensions getDimensions() {
        return getDimensions(isSlim());
    }

    protected final Dimensions getDimensions(boolean isSlim) {
        return dimensions.get(isSlim);
    }

    protected abstract Dimensions createDimensions(boolean slim);

    protected abstract Mesh createInnerLayer(boolean slim);

    protected abstract Mesh createSecondLayer(boolean slim);

    protected void updateSlimDependent(boolean slim) {
        Dimensions dimensions = getDimensions();
        innerLayer.updateDimensions(dimensions);
        secondLayer.updateDimensions(dimensions);

        innerLayer.setMesh(innerLayerMesh.get(slim));
        secondLayer.setMesh(secondLayerMesh.get(slim));
    }

    @Override
    public boolean isSlim() {
        return slim != null && slim;
    }

    @Override
    public void setSlim(boolean value) {
        if (slim == null || slim != value) {
            this.slim = value;
            updateSlimDependent(value);
        }
    }

    protected final SkinPartLayerMesh createMesh(float startX, float startY, float texU, float texV, float enlarge, float modifier, boolean slim) {
        return new SkinPartLayerMesh(dimensions.get(slim), startX, startY, texU, texV, enlarge, modifier, slim);
    }

}
