package me.soknight.javafx.minecraft.skin;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import lombok.Getter;
import me.soknight.javafx.minecraft.skin.part.dependent.SlimDependent;
import me.soknight.javafx.minecraft.skin.part.player.ArmPart;
import me.soknight.javafx.minecraft.skin.part.player.BodyPart;
import me.soknight.javafx.minecraft.skin.part.player.HeadPart;
import me.soknight.javafx.minecraft.skin.part.player.LegPart;
import me.soknight.javafx.minecraft.skin.util.ImageUtils;

import java.util.function.Consumer;

import static javafx.scene.transform.Rotate.*;
import static me.soknight.javafx.minecraft.skin.part.dependent.SideDependent.Side.LEFT;
import static me.soknight.javafx.minecraft.skin.part.dependent.SideDependent.Side.RIGHT;

public class SkinCanvas extends Group {

    private final HeadPart head;
    private final BodyPart body;
    private final ArmPart leftArm, rightArm;
    private final LegPart leftLeg, rightLeg;

    private final PhongMaterial material;
    private final Group canvas;
    private final PerspectiveCamera camera;

    @Getter private final Rotate rotateX, rotateY, rotateZ;
    @Getter private final Translate translate;
    @Getter private final Scale scale;

    @Getter private SubScene subScene;

    private Image skinImage;
    private Image upscaledImage;
    private Boolean slim;

    public SkinCanvas(double width, double height, boolean msaa, boolean ambientLight) {
        this.head = new HeadPart();
        this.body = new BodyPart();
        this.leftArm = new ArmPart(LEFT);
        this.rightArm = new ArmPart(RIGHT);
        this.leftLeg = new LegPart(LEFT);
        this.rightLeg = new LegPart(RIGHT);

        this.camera = new PerspectiveCamera(true);
        this.rotateX = new Rotate(0, X_AXIS);
        this.rotateY = new Rotate(180, Y_AXIS);
        this.rotateZ = new Rotate(0, Z_AXIS);
        this.translate = new Translate(0, 0, -80);
        this.scale = new Scale(1, 1);

        this.material = new PhongMaterial();
        this.canvas = new Group(head, body, leftArm, rightArm, leftLeg, rightLeg);
//        this.canvas = new Group(prepareBox());
        this.canvas.getTransforms().addAll(rotateX);

        bindMaterial();
        getChildren().setAll(createSubScene(width, height, msaa, ambientLight));
    }

    private Box prepareBox() {
        Box box = new Box(8D, 8D, 8D);
        box.setMaterial(material);
        return box;
    }

    public void updateSkin(Image skinImage, boolean slim) {
        if (skinImage == null) {
            material.setDiffuseMap(null);
            return;
        }

        if (ImageUtils.hasNoRequestedSize(skinImage) && ImageUtils.isMinecraftSkin(skinImage)) {
            this.skinImage = skinImage;

            int multiple = Math.max((int) (1024 / skinImage.getWidth()), 1);
            this.upscaledImage = multiple > 1 ? ImageUtils.upscale(skinImage, multiple) : skinImage;

            if (this.slim == null || this.slim != slim) {
                this.slim = slim;
                updateSkinMesh();
            }

            material.setDiffuseMap(upscaledImage);
        }
    }

    private void updateSkinMesh() {
        walkChildRecursively(canvas, node -> {
            if (node instanceof SkinMeshView meshView && meshView.getParent() instanceof SlimDependent slimDependent) {
                slimDependent.setSlim(slim);
            }
        });

        leftArm.setTranslateX(-4D - (leftArm.getDimensions().width() / 2D));
        rightArm.setTranslateX(4D + (leftArm.getDimensions().width() / 2D));
    }

    private void bindMaterial() {
        walkChildRecursively(canvas, node -> {
            if (node instanceof Shape3D shape3D) {
                shape3D.setMaterial(material);
            }
        });
    }

    private void walkChildRecursively(Group group, Consumer<Node> nodeConsumer) {
        for (Node node : group.getChildren()) {
            if (node instanceof Group childGroup) {
                walkChildRecursively(childGroup, nodeConsumer);
            } else {
                nodeConsumer.accept(node);
            }
        }
    }

    private Group createPlayerModel() {
        head.setTranslateY(-10D);

        leftArm.setTranslateX(-6D);
        rightArm.setTranslateX(6D);

        leftLeg.setTranslateX(-2D);
        leftLeg.setTranslateY(12D);
        rightLeg.setTranslateX(2D);
        rightLeg.setTranslateY(12D);

        updateSkin(skinImage, false);
        return canvas;
    }

    private SubScene createSubScene(double width, double height, boolean msaa, boolean ambientLight) {
        Group group = new Group();
        group.getChildren().add(createPlayerModel());
        group.getTransforms().add(rotateZ);

        if (ambientLight) {
            group.getChildren().add(new AmbientLight());
        }

        SceneAntialiasing antiAliasing = msaa ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED;
        this.subScene = new SubScene(group, width, height, true, antiAliasing);

        camera.getTransforms().addAll(rotateY, translate, scale);
        subScene.setCamera(camera);

        return subScene;
    }

}
