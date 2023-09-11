package me.soknight.javafx.minecraft.skin;

import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import lombok.Getter;

@Getter
public final class SkinMeshView extends MeshView {

    private float width, height, depth;

    public SkinMeshView() {
        this(0F, 0F, 0F, null);
    }

    public SkinMeshView(Dimensions dimensions) {
        this(dimensions, null);
    }

    public SkinMeshView(Dimensions dimensions, Mesh mesh) {
        this(dimensions.width(), dimensions.height(), dimensions.depth(), mesh);
    }

    public SkinMeshView(float width, float height, float depth, Mesh mesh) {
        super(mesh);
        updateDimensions(width, height, depth);
    }

    public void updateDimensions(Dimensions dimensions) {
        updateDimensions(dimensions.width(), dimensions.height(), dimensions.depth());
    }

    public void updateDimensions(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

}
