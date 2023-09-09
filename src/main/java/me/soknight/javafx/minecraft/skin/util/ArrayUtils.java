package me.soknight.javafx.minecraft.skin.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public final class ArrayUtils {

    public static int[] clone(int[] source, boolean reverse) {
        if (source == null || source.length == 0)
            return source;

        if (!reverse)
            return Arrays.copyOf(source, source.length);

        int offset = source.length - 1;
        int[] copy = new int[source.length];

        for (int i = 0; i < source.length; i++) {
            copy[i] = source[offset - i];
        }

        return copy;
    }

    public static int[] join(int[] first, int[] second) {
        boolean firstEmpty = first == null || first.length == 0;
        boolean secondEmpty = second == null || second.length == 0;

        if (firstEmpty && secondEmpty)
            return new int[0];

        if (firstEmpty)
            return second;

        if (secondEmpty)
            return first;

        int newLength = first.length + second.length;
        int[] joined = Arrays.copyOf(first, newLength);

        int realIndex = 0;
        for (int i = first.length; i < newLength; i++)
            joined[i] = second[realIndex++];

        return joined;
    }

}
