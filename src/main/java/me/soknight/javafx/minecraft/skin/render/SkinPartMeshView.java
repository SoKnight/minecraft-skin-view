package me.soknight.javafx.minecraft.skin.render;

import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import lombok.Getter;
import lombok.Setter;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

@Getter @Setter
public class SkinPartMeshView extends MeshView {

    private double width;
    private double height;
    private double depth;

    private SkinPartMeshView(Mesh mesh, float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        setMesh(mesh);
    }

    public static SkinPartMeshView ofMesh(Mesh mesh, Dimensions3D dimensions) {
        return ofMesh(mesh, dimensions.width(), dimensions.height(), dimensions.depth());
    }

    public static SkinPartMeshView ofMesh(Mesh mesh, float width, float height, float depth) {
        return new SkinPartMeshView(mesh, width, height, depth);
    }

    public void updateDimensions(Dimensions3D dimensions) {
        updateDimensions(dimensions.width(), dimensions.height(), dimensions.depth());
    }

    public void updateDimensions(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

}
