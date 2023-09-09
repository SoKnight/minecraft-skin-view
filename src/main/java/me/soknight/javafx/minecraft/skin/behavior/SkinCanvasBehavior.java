package me.soknight.javafx.minecraft.skin.behavior;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import lombok.Getter;
import lombok.Setter;
import me.soknight.javafx.minecraft.skin.view.SkinCanvas;

public class SkinCanvasBehavior {

    @Getter @Setter
    private double sensitivity;
    private double lastX, lastY;

    public SkinCanvasBehavior(double mouseSensitivity) {
        this.sensitivity = mouseSensitivity;
    }

    public void register(SkinCanvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            this.lastX = -1;
            this.lastY = -1;
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (lastX != -1 && lastY != -1) {
                if (event.isAltDown() || event.isControlDown() || event.isShiftDown()) {
                    if (event.isShiftDown()) {
                        Rotate rotate = canvas.getRotateZ();
                        rotate.setAngle(rotate.getAngle() - (event.getSceneY() - lastY) * sensitivity);
                    }

                    if (event.isAltDown()) {
                        Rotate rotate = canvas.getRotateY();
                        rotate.setAngle(rotate.getAngle() + (event.getSceneX() - lastX) * sensitivity);
                    }

                    if (event.isControlDown()) {
                        Rotate rotate = canvas.getRotateZ();
                        rotate.setAngle(rotate.getAngle() + (event.getSceneY() - lastY) * sensitivity);
                    }
                } else {
                    double yaw = canvas.getRotateY().getAngle() + (event.getSceneX() - lastX) * sensitivity;
                    yaw %= 360;
                    if (yaw < 0)
                        yaw += 360;

                    int flagX = yaw < 90 || yaw > 270 ? 1 : -1;
                    int flagZ = yaw < 180 ? -1 : 1;
                    double kx = Math.abs(90 - yaw % 180) / 90 * flagX, kz = Math.abs(90 - (yaw + 90) % 180) / 90 * flagZ;

                    canvas.getRotateX().setAngle(canvas.getRotateX().getAngle() + (event.getSceneY() - lastY) * sensitivity * kx);
                    canvas.getRotateY().setAngle(yaw);
                    canvas.getRotateZ().setAngle(canvas.getRotateZ().getAngle() + (event.getSceneY() - lastY) * sensitivity * kz);
                }
            }

            lastX = event.getSceneX();
            lastY = event.getSceneY();
        });

        canvas.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();
            double delta = (deltaY > 0 ? 1 : deltaY == 0 ? 0 : -1) / 10D * sensitivity;

            canvas.getScale().setX(Math.min(Math.max(canvas.getScale().getX() - delta, 0.1), 10));
            canvas.getScale().setY(Math.min(Math.max(canvas.getScale().getY() - delta, 0.1), 10));
        });
    }

}
