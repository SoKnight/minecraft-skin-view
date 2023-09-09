package me.soknight.javafx.minecraft.skin.part;

import javafx.scene.shape.Mesh;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.render.InnerLayerMesh;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.render.SkinPartMeshView;
import me.soknight.javafx.minecraft.skin.util.Dimensions3D;

import java.util.function.Consumer;

@Getter
public abstract class StaticSkinPart extends SkinPart {

    protected final Dimensions3D dimensions;
    private SkinPartMeshView innerLayer;
    private SecondLayerGroup secondLayer;

    protected StaticSkinPart(Dimensions3D dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.innerLayer = wrapMesh(createInnerLayer(), getChildren()::add);
        this.secondLayer = createSecondLayer();

        getChildren().add(secondLayer);
    }

    protected abstract InnerLayerMesh createInnerLayer();

    protected abstract SecondLayerGroup createSecondLayer();

    protected final InnerLayerMesh innerLayerMesh(float scaleX, float scaleY, float startX, float startY) {
        return innerLayerMesh(scaleX, scaleY, startX, startY, 0F);
    }

    protected final InnerLayerMesh innerLayerMesh(float scaleX, float scaleY, float startX, float startY, float enlarge) {
        float width = dimensions.width() + enlarge;
        float height = dimensions.height() + enlarge;
        float depth = dimensions.depth() + enlarge;
        return new InnerLayerMesh(width, height, depth, scaleX, scaleY, startX, startY, false);
    }

    protected final SkinPartMeshView wrapMesh(Mesh mesh) {
        return mesh != null ? SkinPartMeshView.ofMesh(mesh, getDimensions()) : null;
    }

    protected final SkinPartMeshView wrapMesh(Mesh mesh, Consumer<SkinPartMeshView> consumer) {
        SkinPartMeshView meshView = wrapMesh(mesh);

        if (meshView != null)
            consumer.accept(meshView);

        return meshView;
    }

}
