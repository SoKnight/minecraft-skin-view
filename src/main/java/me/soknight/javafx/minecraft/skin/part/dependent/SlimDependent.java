package me.soknight.javafx.minecraft.skin.part.dependent;

import java.util.function.Function;

public interface SlimDependent {

    boolean isSlim();

    void setSlim(boolean value);

    record Pair<T>(T slim, T thick) {
        public Pair(Function<Boolean, T> constructor) {
            this(constructor.apply(true), constructor.apply(false));
        }

        public T get(boolean isSlim) {
            return isSlim ? slim : thick;
        }
    }

}
