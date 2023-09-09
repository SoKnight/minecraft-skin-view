package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.shape.Mesh;
import lombok.AccessLevel;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.part.dependent.SlimDependent;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.render.SkinPartMeshView;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

public abstract class DynamicSkinPart extends SkinPart implements SlimDependent {

    private SlimDependency<Dimensions3D> dimensions;

    @Getter private SkinPartMeshView innerLayer;
    @Getter private SecondLayerGroup secondLayer;

    private SlimDependency<Mesh> innerLayerMesh;

    @Getter(AccessLevel.NONE)
    private Boolean slim;

    @Override
    protected void initialize() {
        super.initialize();

        this.dimensions = new SlimDependency<>(createDimensions(false), createDimensions(true));

        this.innerLayer = SkinPartMeshView.ofMesh(null, Dimensions3D.POINT);
        this.secondLayer = createSecondLayer();

        this.innerLayerMesh = new SlimDependency<>(createInnerLayer(false), createInnerLayer(true));

        getChildren().addAll(innerLayer, secondLayer);
        updateMeshes(isSlim());
    }

    protected abstract Dimensions3D createDimensions(boolean slim);

    protected abstract InnerLayerMesh createInnerLayer(boolean slim);

    protected abstract SecondLayerGroup createSecondLayer();

    @Override
    public Dimensions3D getDimensions() {
        return dimensions.get(isSlim());
    }

    protected void updateMeshes(boolean slim) {
        Dimensions3D dimensions = this.dimensions.get(slim);
        innerLayer.updateDimensions(dimensions);
        secondLayer.updateDimensions(dimensions);

        innerLayer.setMesh(innerLayerMesh.get(slim));
    }

    @Override
    public final boolean isSlim() {
        return slim != null && slim;
    }

    @Override
    public final void setSlim(boolean slim) {
        if (this.slim != null && this.slim == slim)
            return;

        this.slim = slim;
        updateMeshes(slim);
    }

    protected final InnerLayerMesh innerLayerMesh(float scaleX, float scaleY, float startX, float startY, boolean slim) {
        return innerLayerMesh(scaleX, scaleY, startX, startY, 0F, slim);
    }

    protected final InnerLayerMesh innerLayerMesh(float scaleX, float scaleY, float startX, float startY, float enlarge, boolean slim) {
        Dimensions3D dimensions = this.dimensions.get(slim);
        float width = dimensions.width() + enlarge;
        float height = dimensions.height() + enlarge;
        float depth = dimensions.depth() + enlarge;
        return new InnerLayerMesh(width, height, depth, scaleX, scaleY, startX, startY, slim);
    }

    private record SlimDependency<T>(T thick, T slim) {

        private T get(boolean isSlim) {
            return isSlim ? slim : thick;
        }

    }

}
