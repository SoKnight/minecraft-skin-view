package me.soknight.javafx.minecraft.skin.render;

import javafx.scene.shape.TriangleMesh;
import me.soknight.javafx.minecraft.skin.util.ArrayUtils;

public class InnerLayerMesh extends TriangleMesh {

    public InnerLayerMesh(
            float width, float height, float depth,
            float scaleX, float scaleY,
            float startX, float startY,
            boolean slim
    ) {
        getPoints().addAll(generatePoints(width, height, depth));
        getTexCoords().addAll(generateTexCoords(width, height, depth, scaleX, scaleY, startX, startY, slim));
        getFaces().addAll(generateFaces());
    }

    private static float[] generatePoints(float width, float height, float depth) {
        width /= 2F;
        height /= 2F;
        depth /= 2F;

        return new float[] {
                -width,   -height,    depth,    // P0
                 width,   -height,    depth,    // P1
                -width,    height,    depth,    // P2
                 width,    height,    depth,    // P3
                -width,   -height,   -depth,    // P4
                 width,   -height,   -depth,    // P5
                -width,    height,   -depth,    // P6
                 width,    height,   -depth     // P7
        };
    }

    private static float[] generateTexCoords(
            float width, float height, float depth,     // 8 8 8
            float scaleX, float scaleY,                 // 1/2 1/4
            float startX, float startY,                 // 0 0
            boolean slim                                // false
    ) {
        float x = (width + depth) * 2;                  // 32
        float y = height + depth;                       // 16
        float halfWidth = width / x * scaleX;           // 1/8
        float halfDepth = depth / x * scaleX;           // 1/8
        float topX = halfDepth + startX;                // 1/8
        float arm4 = slim ? halfDepth : halfWidth;      // 1/8
        float middleY = depth / y * scaleY + startY;    // 1/8
        float bottomY = scaleY + startY;                // 1/4

        return new float[]{
                topX,                               startY,     // T0  ---  // 1/8, 0   // 8,   0
                topX + halfWidth,                   startY,     // T1   |   // 2/8, 0   // 16,  0
                topX + halfWidth * 2,               startY,     // T2  ---  // 3/8, 0   // 24,  0
                startX,                             middleY,    // T3  ---  // 0,   1/8 // 0,   8
                startX + halfDepth,                 middleY,    // T4   |   // 1/8, 1/8 // 8,   8
                startX + halfDepth + halfWidth,     middleY,    // T5   |   // 2/8, 1/8 // 16,  8
                startX + scaleX - arm4,             middleY,    // T6   |   // 3/8, 1/8 // 24,  8
                startX + scaleX,                    middleY,    // T7  ---  // 4/8, 1/8 // 32,  8
                startX,                             bottomY,    // T8  ---  // 0,   2/8 // 0,   16
                startX + halfDepth,                 bottomY,    // T9   |   // 1/8, 2/8 // 8,   16
                startX + halfDepth + halfWidth,     bottomY,    // T10  |   // 2/8, 2/8 // 16,  16
                startX + scaleX - arm4,             bottomY,    // T11  |   // 3/8, 2/8 // 24,  16
                startX + scaleX,                    bottomY     // T12 ---  // 4/8, 2/8 // 32,  16
        };
    }

    private static int[] generateFaces() {
        int[] faces = new int[]{
                // TOP
                5,0, 4,1, 0,5,	// P5,T0, P4,T1, P0,T5
                5,0, 0,5, 1,4,	// P5,T0, P0,T5, P1,T4
                // RIGHT
                0,5, 4,6, 6,11,	// P0,T4 ,P4,T3, P6,T8
                0,5, 6,11,2,10,	// P0,T4 ,P6,T8, P2,T9
                // FRONT
                1,4, 0,5, 2,10,	// P1,T5, P0,T4, P2,T9
                1,4, 2,10,3,9,	// P1,T5, P2,T9, P3,T10
                // LEFT
                5,3, 1,4, 3,9,	// P5,T6, P1,T5, P3,T10
                5,3, 3,9, 7,8,	// P5,T6, P3,T10,P7,T11
                // BACK
                4,6, 5,7, 7,12,	// P4,T6, P5,T7, P7,T12
                4,6, 7,12,6,11,	// P4,T6, P7,T12,P6,T11
                // BOTTOM
                3,5, 2,6, 6,2,	// P3,T2, P2,T1, P6,T5
                3,5, 6,2, 7,1	// P3,T2, P6,T5, P7,T6
        };

        int[] copy = ArrayUtils.clone(faces, true);
        for (int i = 0; i < copy.length; i += 2) {
            int tmp = copy[i];
            copy[i] = copy[i + 1];
            copy[i + 1] = tmp;
        }

        return ArrayUtils.join(faces, copy);
    }

}
