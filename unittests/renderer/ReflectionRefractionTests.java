package renderer;
import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
* shadows
* (with transparency)
* @author dzilb, Shirel Cohen,Neomi Golkin*/
public class ReflectionRefractionTests {
	  private Scene scene = new Scene("Test scene");

	   /** Produce a picture of a sphere lighted by a spotlight */
	   @Test
	   public void twoSpheres() {
	      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	         .setVPSize(150, 150).setVPDistance(1000);

	      scene.geometries.add( //
	                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
	                              .setMaterial(new Material().setkD(0.4).setkS(0.3).setShininess(100).setKt(0.3)),
	                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
	                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)));
	      scene.lights.add( //
	                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
	                          .setKl(0.0004).setKq(0.0000006));

	      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
	         .setRayTracer(new SimpleRayTrancer(scene)) //
	         .renderImage() //
	         .writeToImage();
	   }

	   /** Produce a picture of a sphere lighted by a spotlight */
	   @Test
	   public void twoSpheresOnMirrors() {
	      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	         .setVPSize(2500, 2500).setVPDistance(10000); //

	      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

	      scene.geometries.add( //
	                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
	                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)
	                                 .setKt(new Double3(0.5, 0, 0))),
	                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
	                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)),
	                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
	                                        new Point(670, 670, 3000)) //
	                              .setEmission(new Color(20, 20, 20)) //
	                              .setMaterial(new Material().setKr(1)),
	                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
	                                        new Point(-1500, -1500, -2000)) //
	                              .setEmission(new Color(20, 20, 20)) //
	                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

	      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
	         .setKl(0.00001).setKq(0.000005));

	      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
	      camera.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene)) //
	         .renderImage() //
	         .writeToImage();
	   }

	   /** Produce a picture of two triangles lighted by a spotlight with a
	    * partially
	    * transparent Sphere producing partial shadow */
	   @Test
	   public void trianglesTransparentSphere() {
	      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
	         .setVPSize(200, 200).setVPDistance(1000);

	      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

	      scene.geometries.add( //
	                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
	                                        new Point(75, 75, -150)) //
	                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)), //
	                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
	                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)), //
	                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
	                              .setMaterial(new Material().setkD(0.2).setkS(0.2).setShininess(30).setKt(0.6)));

	      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
	         .setKl(4E-5).setKq(2E-7));

	      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
	      camera.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene)) //
	         .renderImage() //
	         .writeToImage();
	   }

	   @Test
	   public void customReflectionShadow() {
	      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15))
	              .setBackground(new Color(149, 175, 192));

	      Point p1 = new Point(5, -3, 0);
	      Point p2 = new Point(12, -3, 0);
	      Point p3 = new Point(8.5, 3, 0);
	      Point p4 = new Point(8.5, 0, 5.5);

	      scene.geometries.add(
	              new Triangle(p1, p2, p3)
	                      .setEmission(new Color(BLUE))
	                      .setMaterial(new Material()
	                      .setkD(0.5)
	                      .setkS(0.5)
	                      .setShininess(60)),
	              new Triangle(p1, p2, p4)
	                      .setEmission(new Color(BLUE))
	                      .setMaterial(new Material()
	                              .setkD(0.05)
	                              .setkS(0.5)
	                              .setKr(0.3)
	                              .setKt(0.1)
	                              .setShininess(10)),
	              new Triangle(p1, p3, p4)
	                      .setEmission(new Color(BLUE))
	                      .setMaterial(new Material()
	                              .setkD(0.05)
	                              .setkS(0.5)
	                              .setKt(0.5)
	                              .setShininess(10)),
	              new Triangle(p2, p3, p4)
	                      .setEmission(new Color(BLUE))
	                      .setMaterial(new Material()
	                              .setkD(0.05)
	                              .setkS(0.5)
	                              .setKr(0.3)
	                              .setShininess(10)),
	              new Sphere(new Point(8.5, 0, 1.1), 1)
	                      .setEmission(new Color(RED))
	                      .setMaterial(new Material()
	                              .setkD(0.01)
	                              .setkS(0.2)
	                              .setKt(0.35)
	                              .setShininess(30)),
	              new Sphere(new Point(8.5, 0, 1), 0.25)
	                      .setEmission(new Color(GREEN))
	                      .setMaterial(new Material()
	                              .setkD(0.01)
	                              .setkS(0.2)
	                              .setShininess(10))
	      );

	      scene.lights.add(
	              new SpotLight(
	                      new Color(700, 400, 400),
	                      new Point(0, 5, 10),
	                      new Vector(8.5, -5, -9)
	              )
	              .setKl(0.0001).setKq(0.0001)
	      );

	      // TC01
	      Camera camera = new Camera(
	              new Point(0, 0, 10),
	              new Vector(8.5, 0, -9),
	              new Vector(9, 0, 8.5))
	              .setVPSize(200, 200)
	              .setVPDistance(300)
	              .rotateX(10);
	              //.setAntiAliasing(20);

	      ImageWriter imageWriter = new ImageWriter("customReflectionShadow1", 600, 600);
	      camera.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene))
	              .renderImage()
	              .writeToImage();

	      // TC02
	      camera = new Camera(
	              new Point(1, -6, 10),
	              new Vector(7.5, 6, -9),
	              new Vector(9, 0, 7.5))
	              .setVPSize(200, 200)
	              .setVPDistance(300)
	              .setAntiAliasing(20);
	      imageWriter = new ImageWriter("customReflectionShadow2", 600, 600);
	      camera.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene))
	              .renderImage()
	              .writeToImage();

	      // TC03
	      camera = new Camera(
	              new Point(8, -8, 10),
	              new Vector(0.5, 8, -9),
	              new Vector(0, 9, 8))
	              .setVPSize(200, 200)
	              .setVPDistance(300)
	              .setAntiAliasing(20);
	      imageWriter = new ImageWriter("customReflectionShadow3", 600, 600);
	      camera.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene))
	              .renderImage()
	              .writeToImage();

	      // TC04
	      camera = new Camera(
	              new Point(6, 10, 8),
	              new Vector(2.5, -10, -7),
	              new Vector(2.5, -10, 15.178571428571429))
	              .setVPSize(200, 200)
	              .setVPDistance(300)
	              .setAntiAliasing(20);
	      imageWriter = new ImageWriter("customReflectionShadow4", 600, 600);
	      camera.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene))
	              .renderImage()
	              .writeToImage();

	      // TC05
	      camera = new Camera(
	              new Point(0, 6, 5),
	              new Vector(8.5, -6, -4),
	              new Vector(8.5, -6, 27.0625))
	              .setVPSize(200, 200)
	              .setVPDistance(300)
	              .rotateX(-3)
	              .rotateY(-7.5)
	              .rotateZ(-5)
	              .setAntiAliasing(100);
	      imageWriter = new ImageWriter("customReflectionShadow5", 600, 600);
	      camera.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene))
	              .renderImage()
	              .writeToImage();
	   }
}
