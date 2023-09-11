package me.soknight.javafx.minecraft.skin.part.dependent;

public interface SideDependent {

    Side getSide();

    enum Side {

        LEFT,
        RIGHT,
        ;

        public boolean isLeft() {
            return this == LEFT;
        }

        public boolean isRight() {
            return this == RIGHT;
        }

    }

}
