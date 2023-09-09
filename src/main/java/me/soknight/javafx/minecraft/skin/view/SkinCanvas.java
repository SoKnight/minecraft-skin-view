package me.soknight.javafx.minecraft.skin.view;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.part.player.ArmSkinPart;
import me.soknight.javafx.minecraft.skin.part.player.BodySkinPart;
import me.soknight.javafx.minecraft.skin.part.player.HeadSkinPart;
import me.soknight.javafx.minecraft.skin.part.player.LegSkinPart;
import me.soknight.javafx.minecraft.skin.render.SecondLayerGroup;
import me.soknight.javafx.minecraft.skin.render.SkinPartMeshView;
import me.soknight.javafx.minecraft.skin.util.FunctionUtils;
import me.soknight.javafx.minecraft.skin.util.ImageUtils;

import static javafx.scene.transform.Rotate.*;
import static me.soknight.javafx.minecraft.skin.part.SkinPartSide.LEFT;
import static me.soknight.javafx.minecraft.skin.part.SkinPartSide.RIGHT;

public class SkinCanvas extends Group {

    private final HeadSkinPart head;
    private final BodySkinPart body;
    private final ArmSkinPart leftArm, rightArm;
    private final LegSkinPart leftLeg, rightLeg;

    private final Group canvas;
    private final PerspectiveCamera camera;

    @Getter private final Rotate rotateX, rotateY, rotateZ;
    @Getter private final Translate translate;
    @Getter private final Scale scale;

    @Getter private SubScene subScene;

    private Image skinImage;
    private Image upscaledImage;
    private Boolean slim;

    public SkinCanvas(double width, double height, boolean msaa) {
        this.head = new HeadSkinPart();
        this.body = new BodySkinPart();
        this.leftArm = new ArmSkinPart(LEFT);
        this.rightArm = new ArmSkinPart(RIGHT);
        this.leftLeg = new LegSkinPart(LEFT);
        this.rightLeg = new LegSkinPart(RIGHT);

        this.camera = new PerspectiveCamera(true);
        this.rotateX = new Rotate(0, X_AXIS);
        this.rotateY = new Rotate(180, Y_AXIS);
        this.rotateZ = new Rotate(0, Z_AXIS);
        this.translate = new Translate(0, 0, -80);
        this.scale = new Scale(1, 1);

        this.canvas = new Group();
        getChildren().setAll(createSubScene(width, height, msaa));
    }

    public void updateSkin(Image skinImage, boolean slim) {
        if (skinImage == null) {
            unbindMaterial(canvas);
            return;
        }

        if (ImageUtils.hasNoRequestedSize(skinImage) && ImageUtils.isMinecraftSkin(skinImage)) {
            this.skinImage = skinImage;
//            this.skinImage = ImageUtils.x32ToX64(skinImage);

            int multiple = Math.max((int) (1024 / skinImage.getWidth()), 1);
            this.upscaledImage = multiple > 1 ? ImageUtils.upscale(skinImage, multiple) : skinImage;

            if (this.slim == null || this.slim != slim) {
                this.slim = slim;
                updateSkinMesh();
            }

            bindMaterial(canvas);
        }
    }

    protected void updateSkinMesh() {
        SkinPartMeshView leftArmMesh = leftArm.getInnerLayer();
        SkinPartMeshView rightArmMesh = rightArm.getInnerLayer();

//        FunctionUtils.forEachWithB(SkinOuterLayer::setWidth, slim ? 3 : 4, leftArm.getOuterMesh(), rightArm.getOuterMesh());
        FunctionUtils.forEachWithB(SkinPartMeshView::setWidth, slim ? 3D : 4D, leftArmMesh, rightArmMesh);

        double bodyMeshWidth = body.getInnerLayer().getWidth();
        FunctionUtils.forEachWithB(Node::setTranslateX, -(bodyMeshWidth + leftArmMesh.getWidth()) / 2D, leftArm);
        FunctionUtils.forEachWithB(Node::setTranslateX, (bodyMeshWidth + rightArmMesh.getWidth()) / 2D, rightArm);

        leftArm.setSlim(slim);
        rightArm.setSlim(slim);

        leftArm.getRotateZ().setPivotX(-leftArmMesh.getWidth() / 2D);
        rightArm.getRotateZ().setPivotX(rightArmMesh.getWidth() / 2D);
    }

    protected Material createMaterial() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(upscaledImage);
        return material;
    }

    protected void bindMaterial(Group group) {
        Material material = createMaterial();

        for (Node node : group.getChildren()) {
            if (node instanceof Shape3D shape) {
                shape.setMaterial(material);
            } else if (node instanceof SecondLayerGroup secondLayer) {
                secondLayer.updateSkin(skinImage);
            } else if (node instanceof Group childGroup) {
                bindMaterial(childGroup);
            }
        }
    }

    protected void unbindMaterial(Group group) {
        for (Node node : group.getChildren()) {
            if (node instanceof Shape3D shape) {
                shape.setMaterial(null);
            } else if (node instanceof SecondLayerGroup secondLayer) {
                secondLayer.updateSkin(null);
            } else if (node instanceof Group childGroup) {
                unbindMaterial(childGroup);
            }
        }
    }

    protected Group createPlayerModel() {
        head.setTranslateY(-body.getInnerLayer().getHeight() / 2D - head.getInnerLayer().getHeight() / 2D);

        leftArm.setTranslateX(-body.getInnerLayer().getWidth() / 2D - leftArm.getInnerLayer().getWidth() / 2D);
        rightArm.setTranslateX(body.getInnerLayer().getWidth() / 2D + rightArm.getInnerLayer().getWidth() / 2D);

        leftLeg.setTranslateX(-body.getInnerLayer().getWidth() / 2D + leftLeg.getInnerLayer().getWidth() / 2D);
        rightLeg.setTranslateX(body.getInnerLayer().getWidth() / 2D - rightLeg.getInnerLayer().getWidth() / 2D);

        leftLeg.setTranslateY(body.getInnerLayer().getHeight() / 2D + leftLeg.getInnerLayer().getHeight() / 2D);
        rightLeg.setTranslateY(body.getInnerLayer().getHeight() / 2D + rightLeg.getInnerLayer().getHeight() / 2D);

        canvas.getTransforms().addAll(rotateX);
        canvas.getChildren().addAll(head, body, leftArm, rightArm, leftLeg, rightLeg);
        updateSkin(skinImage, false);
        return canvas;
    }

    protected SubScene createSubScene(double width, double height, boolean msaa) {
        Group group = new Group();
        group.getChildren().add(createPlayerModel());
        group.getChildren().add(new AmbientLight());
        group.getTransforms().add(rotateZ);

        SceneAntialiasing antiAliasing = msaa ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED;
        this.subScene = new SubScene(group, width, height, true, antiAliasing);

        camera.getTransforms().addAll(rotateY, translate, scale);
        subScene.setCamera(camera);

        return subScene;
    }

}
