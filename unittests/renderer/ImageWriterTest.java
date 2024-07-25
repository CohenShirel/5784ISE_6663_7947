package renderer;

import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method to create a colored grid image
     */
    @Test
    void basicImageTest() {
        int nX = 800;
        int nY = 500;
        int vpWidth = 16;
        int vpHeight = 10;
        ImageWriter imageWriter = new ImageWriter("basic", nX, nY);
        Color blue = new Color(0, 0, 255);
        Color red = new Color(255, 0, 0);
        for (int i = 0; i < nY; i++)
            for (int j = 0; j < nX; j++)
                imageWriter.writePixel(j, i, i % (nX / vpWidth) == 0 || j % (nY / vpHeight) == 0 ? red : blue);
        imageWriter.writeToImage();
    }
}