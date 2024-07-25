package lighting;

import static java.awt.Color.BLUE;
import static java.awt.Color.CYAN;
import static java.awt.Color.GREEN;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;

import org.junit.jupiter.api.Test;
import renderer.Camera;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.SimpleRayTrancer;
import renderer.SimpleRayTrancer;
import scene.Scene;

public class LightTest {
	  private final Scene          scene1                  = new Scene("Test scene");
	   private final Scene          scene2                  = new Scene("Test scene")
	      .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

	   private final Camera         camera1                 = new Camera(new Point(0, 0, 1000),
	                                                                     new Vector(0, 0, -1), new Vector(0, 1, 0))
	      .setVPSize(150, 150).setVPDistance(1000);
	   private final Camera         camera2                 = new Camera(new Point(0, 0, 1000),
	                                                                     new Vector(0, 0, -1), new Vector(0, 1, 0))
	      .setVPSize(200, 200).setVPDistance(1000);

	   private static final int     SHININESS               = 301;
	   private static final double  KD                      = 0.5;
	   private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);

	   private static final double  KS                      = 0.5;
	   private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);

	   private final Material       material                = new Material().setkD(KD3).setkS(KS3).setShininess(SHININESS);
	   private final Color          trianglesLightColor     = new Color(800, 500, 250);
	   private final Color          sphereLightColor        = new Color(800, 500, 0);
	   private final Color          sphereColor             = new Color(BLUE).reduce(2);

	   private final Point          sphereCenter            = new Point(0, 0, -50);
	   private static final double  SPHERE_RADIUS           = 50d;

	   // The triangles' vertices:
	   private final Point[]        vertices                =
	      {
	        // the shared left-bottom:
	        new Point(-110, -110, -150),
	        // the shared right-top:
	        new Point(95, 100, -150),
	        // the right-bottom
	        new Point(110, -110, -150),
	        // the left-top
	        new Point(-75, 78, 100)
	      };
	   private final Point          sphereLightPosition     = new Point(-50, -50, 25);
	   private final Point          trianglesLightPosition  = new Point(30, 10, -100);
	   private final Vector         trianglesLightDirection = new Vector(-2, -2, -2);

	   private final Geometry       sphere                  = new Sphere(sphereCenter, SPHERE_RADIUS)
	      .setEmission(sphereColor).setMaterial(new Material().setkD(KD).setkS(KS).setShininess(SHININESS));
	   private final Geometry       triangle1               = new Triangle(vertices[0], vertices[1], vertices[2])
	      .setMaterial(material);
	   private final Geometry       triangle2               = new Triangle(vertices[0], vertices[1], vertices[3])
	      .setMaterial(material);

	   /** Produce a picture of a sphere lighted by a directional light */
	   @Test
	   public void sphereDirectional() {
	      scene1.geometries.add(sphere);
	      scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));

	      ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
	      camera1.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene1)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of a sphere lighted by a point light */
	   @Test
	   public void spherePoint() {
	      scene1.geometries.add(sphere);
	      scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
	         .setKl(0.001).setKq(0.0002));

	      ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
	      camera1.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene1)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of a sphere lighted by a spotlight */
	   @Test
	   public void sphereSpot() {
	      scene1.geometries.add(sphere);
	      scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
	         .setKl(0.001).setKq(0.0001));

	      ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
	      camera1.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene1)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of a sphere lighted by a spotlight */
	   @Test
	   public void spherePoint2() {
	      scene1.geometries.add(sphere);
	      scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition).setKl(0.001).setKq(0.0002));

	      ImageWriter imageWriter = new ImageWriter("lightSpherePoint2", 500, 500);
	      camera1.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene1))
	              .setAntiAliasing(2)
	              .renderImage()
	              .writeToImage();
	   }

	   /** Produce a picture of two triangles lighted by a directional light */
	   @Test
	   public void trianglesDirectional() {
	      scene2.geometries.add(triangle1, triangle2);
	      scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

	      ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
	      camera2.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene2)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of two triangles lighted by a point light */
	   @Test
	   public void trianglesPoint() {
	      scene2.geometries.add(triangle1, triangle2);
	      scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
	         .setKl(0.001).setKq(0.0002));

	      ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
	      camera2.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene2)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of two triangles lighted by a spotlight */
	   @Test
	   public void trianglesSpot() {
	      scene2.geometries.add(triangle1, triangle2);
	      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
	         .setKl(0.001).setKq(0.0001));

	      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
	      camera2.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene2)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of a sphere lighted by a narrow spotlight */
	   @Test
	   public void sphereSpotSharp() {
	      scene1.geometries.add(sphere);
	      scene1.lights
	         .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
	            .setNarrowBeam(10).setKl(0.001).setKq(0.00004));

	      ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
	      camera1.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene1)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /** Produce a picture of two triangles lighted by a narrow spotlight */
	   @Test
	   public void trianglesSpotSharp() {
	      scene2.geometries.add(triangle1, triangle2);
	      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
	         .setNarrowBeam(10).setKl(0.001).setKq(0.00004));

	      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
	      camera2.setImageWriter(imageWriter) //
	         .setRayTracer(new SimpleRayTrancer(scene2)) //
	         .renderImage() //
	         .writeToImage(); //
	   }

	   /**
	    * Produce a picture of a sphere lighted by 2 directional lights and 1 point light
	    */
	   @Test
	   public void sphereMultipleLights() {
	      scene1.geometries.add(sphere);
	      scene1.lights.add(
	              new DirectionalLight(
	                      new Color(RED).scale(2),
	                      new Vector(1, 1, -0.5)
	              )
	      );
	      scene1.lights.add(
	              new DirectionalLight(
	                      new Color(YELLOW).scale(1),
	                      new Vector(-1, -1, -0.5)
	              )
	      );
	      scene1.lights.add(
	              new PointLight(
	                      new Color(CYAN).scale(2),
	                      new Point(50, -40, 0)
	              )
	              .setKl(0.0008)
	              .setKq(0.0001)
	      );

	      ImageWriter imageWriter = new ImageWriter("multipleLightSphere", 500, 500);
	      camera1.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene1))
	              .renderImage()
	              .writeToImage();
	   }

	   /**
	    * Produce a picture of 2 triangles lighted by 2 point lights and 1 directional light
	    */
	   @Test
	   public void triangleMultipleLights() {
	      scene2.geometries.add(triangle1, triangle2);
	      scene2.lights.add(
	              new PointLight(
	                      new Color(MAGENTA),
	                      trianglesLightPosition
	              )
	              .setKl(0.0008)
	              .setKq(0.0003)
	      );
	      scene2.lights.add(
	              new PointLight(
	                      new Color(GREEN),
	                      new Point(30, -60, -100)
	              )
	              .setKl(0.0008)
	              .setKq(0.0001)
	      );
	      scene2.lights.add(
	              new DirectionalLight(
	                      new Color(BLUE),
	                      trianglesLightDirection
	              )
	      );

	      ImageWriter imageWriter = new ImageWriter("multipleLightTriangles", 500, 500);
	      camera2.setImageWriter(imageWriter)
	              .setRayTracer(new SimpleRayTrancer(scene2))
	              .renderImage()
	              .writeToImage();
	   }

}