module minecraft.skin.view {

    requires static lombok;

    requires transitive javafx.controls;

    exports me.soknight.javafx.minecraft.skin.behavior;
    exports me.soknight.javafx.minecraft.skin.part;
    exports me.soknight.javafx.minecraft.skin.part.dependent;
    exports me.soknight.javafx.minecraft.skin.part.player;
    exports me.soknight.javafx.minecraft.skin.render;
    exports me.soknight.javafx.minecraft.skin.util;
    exports me.soknight.javafx.minecraft.skin.view;

}