package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

public class MiniProject1Test {
    private static final Scene scene = new Scene("MiniProject1");

    private Geometries constructBoardSlots() {
        boolean is_white;
        Geometries result = new Geometries();

        for (int y = 0; y < 8; y++) {
            is_white = y % 2 != 0;
            for (int x = 0; x < 8; x++) {
                result.add(
                        new Box(
                                new Point(-3.5 + x, -3.5 + y, 1),
                                1,
                                1,
                                0.5,
                                new Vector(0, 0, 1),
                                new Vector(-1, 0, 0),
                                is_white ? new Color(WHITE) : Color.BLACK,
                                new Material().setShininess(80).setkD(0.05).setkS(0.1) // Play with these values
                        ).getBox()
                );

                is_white = !is_white;
            }
        }

        return result;
    }

    private Geometries constructSoldiers() {
        Geometries result = new Geometries();

        // First Player (White)
        for (int y = 0; y < 3; y++)
            for (int x = y % 2; x < 8; x += 2)
                result.add(
                        new Sphere(new Point(-3.5 + x, -3.5 + y, 1.25), 0.25)
                                .setEmission(new Color(247, 241, 227))
                                .setMaterial(
                                        new Material()
                                                .setkD(0.2)
                                                .setkS(0.5)
                                                .setShininess(60)
                                )
                );

        // Second Player (Red)
        for (int y = 7; y >= 5; y--)
            for (int x = y % 2; x < 8; x += 2)
                result.add(
                        new Sphere(new Point(-3.5 + x, -3.5 + y, 1.25), 0.25)
                                .setEmission(new Color(RED))
                                .setMaterial(
                                        new Material()
                                                .setkD(0.2)
                                                .setkS(0.5)
                                                .setShininess(60)
                                )
                );

        return result;
    }

    @Test
    void damka() {
        Camera camera = new Camera(
                new Point(-2, -6, 5),
                new Vector(2, 6, -5),
                new Vector(2, 6, 8)
        )
                .setVPSize(600, 600)
                .setVPDistance(300)
                .setAntiAliasing(10);

        scene.geometries.add(constructBoardSlots(), constructSoldiers());

        scene.lights.add(
                new SpotLight(new Color(WHITE), new Point(0, 0, 6), new Vector(0, 0, -1))
        );
        scene.lights.add(
                new DirectionalLight(new Color(WHITE), new Vector(-1, -1, 0))
        );
        scene.lights.add(
                new DirectionalLight(new Color(WHITE), new Vector(-1, -1, 0))
        );

        scene.setBackground(new Color(179, 57, 57).reduce(2));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0.15)); // Try rgb(132, 129, 122)

        ImageWriter imageWriter = new ImageWriter("damka_AA10", 900, 900);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new SimpleRayTrancer(scene))
                .renderImage()
                .writeToImage();
    }
}
