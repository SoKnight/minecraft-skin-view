package me.soknight.javafx.minecraft.skin.util;

public record Dimensions3D(int width, int height, int depth) {

    public static Dimensions3D POINT = new Dimensions3D(0, 0, 0);

}
