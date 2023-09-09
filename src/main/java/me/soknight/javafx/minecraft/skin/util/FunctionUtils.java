package me.soknight.javafx.minecraft.skin.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
public final class FunctionUtils {

    @SafeVarargs public static <T> void forEach(Consumer<T> consumer, T... ts) {
        Arrays.asList(ts).forEach(consumer);
    }

    @SafeVarargs public static <A, B> void forEachWithA(BiConsumer<A, B> consumer, A a, B... bs) {
        Arrays.asList(bs).forEach(b -> consumer.accept(a, b));
    }

    @SafeVarargs public static <A, B> void forEachWithB(BiConsumer<A, B> consumer, B b, A... as) {
        Arrays.asList(as).forEach(a -> consumer.accept(a, b));
    }

}
