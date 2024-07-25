package renderer;

import geometries.Box;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

/**
 * Test class to render additional geometries
 */
public class AdditionalGeometriesTest {
    private static final Camera camera = new Camera(
            new Point(0, 2, 3),
            new Vector(3, -2, -3),
            new Vector(3, -2, 4.33333333333333)
    ).setVPSize(200, 200).setVPDistance(200);

/**
 * Test method for the rendering of {@link geometries.Box}
 */
@Test
void box() {
    Scene scene = new Scene("Additional Geometries");

    scene.geometries.add(
            new Box(
                    new Point(3, 0, 1),
                    2,
                    2,
                    1,
                    new Vector(0, 0, 1),
                    new Vector(0, -1, 0),
                    new Color(BLUE).reduce(3),
                    new Material()
                            .setShininess(30)
                            .setkD(0.3)
                            .setkS(0.2)
            ).getBox()
    );

    scene.lights.add(
            new SpotLight(
                    new Color(WHITE),
                    new Point(0, 2, 2),
                    new Point(3, 0, 1).subtract(new Point(0, 2, 2))
            )
    );

    ImageWriter imageWriter = new ImageWriter("Box", 600, 600);
    camera.setImageWriter(imageWriter)
            .setRayTracer(new SimpleRayTrancer(scene))
            .renderImage()
            .writeToImage();
}
}

