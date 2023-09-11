package me.soknight.javafx.minecraft.skin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.soknight.javafx.minecraft.skin.behavior.SkinCanvasBehavior;

public final class TestApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SkinCanvas canvas = new SkinCanvas(0D, 0D, true, true);
        SkinCanvasBehavior behavior = new SkinCanvasBehavior(0.5D);
        behavior.register(canvas);

        StackPane root = new StackPane(canvas);
        canvas.getSubScene().widthProperty().bind(root.widthProperty());
        canvas.getSubScene().heightProperty().bind(root.heightProperty());

        Image image = new Image(getClass().getResourceAsStream("/skin.png"));
        canvas.updateSkin(image, false);

        Scene scene = new Scene(root, 600D, 800D, Color.DARKSLATEBLUE);
        stage.setScene(scene);
        stage.setTitle("Minecraft Skin View Test");
        stage.centerOnScreen();
        stage.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
