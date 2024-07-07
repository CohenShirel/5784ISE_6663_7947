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
    @Test
    public void writeToImageTemp() {
        ImageWriter imageWriter = new ImageWriter("empty image grid", 800, 500);
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                imageWriter.writePixel(i, j, new Color(YELLOW));
            }
        }
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j , new Color(RED));
            }
        }
        imageWriter.writeToImage();
    }
}