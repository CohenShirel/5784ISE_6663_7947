package scene;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import scence.parser.SceneDescriptor;

import org.xml.sax.SAXException;

import primitives.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class builds a Scene object based on parsed XML data structures
 *
 * @author Shirel Cohen & Neomi Golkin
 */
public class SceneBuilder {
	/*
	 * private final SceneDescriptor sceneDesc; private final Scene scene;
	 * 
	 *//**
		 * Constructor to initialize this class and to start building the scene based on
		 * the parsed XML data
		 *
		 * @param filePath XML file path
		 * @param scene    scene object (can be null)
		 * @throws ParserConfigurationException .
		 * @throws IOException                  .
		 * @throws SAXException                 .
		 */
	/*
	 * public SceneBuilder(String filePath, Scene scene) throws
	 * ParserConfigurationException, IOException, SAXException { this.scene = scene
	 * != null ? scene : new Scene("XML Scene"); this.sceneDesc = new
	 * SceneDescriptor(filePath);
	 * 
	 * this.buildScene(); this.buildAmbientLight();
	 * 
	 * this.scene.setGeometries(new Geometries()); if (this.sceneDesc.getPlanes() !=
	 * null) this.buildPlanes(); if (this.sceneDesc.getSpheres() != null)
	 * this.buildSpheres(); if (this.sceneDesc.getTriangles() != null)
	 * this.buildTriangles(); }
	 * 
	 *//**
		 * Util method that converts a string of 3 doubles to Point. DRY (Do not Repeat
		 * Yourself)
		 *
		 * @param value provided string
		 * @return Point object
		 */
	/*
	 * private Point getPointFromValue(String value) { String[] point =
	 * value.split(" ");
	 * 
	 * if (point.length != 3) return null;
	 * 
	 * return new Point( Double.parseDouble(point[0]), Double.parseDouble(point[1]),
	 * Double.parseDouble(point[2]) ); }
	 * 
	 *//**
		 * Sets the Scene attributes
		 */
	/*
	 * private void buildScene() { Map<String, String> sceneAttributes =
	 * this.sceneDesc.getSceneAttributes();
	 * 
	 * for (String key : sceneAttributes.keySet()) { switch (key) { case
	 * "background-color": String[] rgb = sceneAttributes.get(key).split(" "); if
	 * (rgb.length == 3) try { this.scene.setBackground( new Color(
	 * Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])
	 * ) ); } catch (NumberFormatException e) {
	 * this.scene.setBackground(Color.BLACK); } else
	 * this.scene.setBackground(Color.BLACK); break; default: break; } } }
	 * 
	 *//**
		 * Sets the Ambient Light attributes
		 */
	/*
	 * private void buildAmbientLight() { Map<String, String> ambientLightAttributes
	 * = this.sceneDesc.getAmbientLightAttributes();
	 * 
	 * Color color = Color.BLACK; double k = 0;
	 * 
	 * for (String key : ambientLightAttributes.keySet()) { switch (key) { case
	 * "color": String[] rgb = ambientLightAttributes.get(key).split(" ");
	 * 
	 * if (rgb.length != 3) break;
	 * 
	 * try { color = new Color( Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]),
	 * Integer.parseInt(rgb[2])); } catch (NumberFormatException e) {} break; case
	 * "k": if (ambientLightAttributes.get(key).contains(" ")) break;
	 * 
	 * try { k = Double.parseDouble(ambientLightAttributes.get(key)); } catch
	 * (NumberFormatException e) {} break; default: break; } }
	 * 
	 * this.scene.setAmbientLight(new AmbientLight(color, k)); }
	 * 
	 *//**
		 * Adds the Planes as Geometries
		 */
	/*
	 * private void buildPlanes() { List<Map<String, String>> planes =
	 * this.sceneDesc.getPlanes();
	 * 
	 * for (Map<String, String> plane : planes) { if (Set.of("p1", "p2",
	 * "p3").equals(plane.keySet())) { Point p1, p2, p3;
	 * 
	 * try { p1 = getPointFromValue(plane.get("p1")); p2 =
	 * getPointFromValue(plane.get("p2")); p3 = getPointFromValue(plane.get("p3"));
	 * } catch (NumberFormatException e) { continue; }
	 * 
	 * if (p1 != null && p2 != null && p3 != null) this.scene.geometries.add(new
	 * Plane(p1, p2, p3)); } else if (Set.of("q0", "normal").equals(plane.keySet()))
	 * { Point q0; Vector normal;
	 * 
	 * try { q0 = getPointFromValue(plane.get("q0")); normal = (Vector)
	 * getPointFromValue(plane.get("normal")); } catch (NumberFormatException e) {
	 * continue; }
	 * 
	 * if (q0 != null && normal != null) this.scene.geometries.add(new Plane(q0,
	 * normal)); } } }
	 * 
	 *//**
		 * Adds the Spheres as Geometries
		 */
	/*
	 * private void buildSpheres() { List<Map<String, String>> spheres =
	 * this.sceneDesc.getSpheres();
	 * 
	 * for (Map<String, String> sphere : spheres) { if (Set.of("center",
	 * "radius").equals(sphere.keySet())) { Point center; double radius;
	 * 
	 * try { center = getPointFromValue(sphere.get("center")); radius =
	 * Double.parseDouble(sphere.get("radius")); } catch (NumberFormatException e) {
	 * continue; }
	 * 
	 * if (center != null) this.scene.geometries.add(new Sphere(center, radius)); }
	 * } }
	 * 
	 *//**
		 * Adds the Triangles as Geometries
		 */
	/*
	 * private void buildTriangles() { List<Map<String, String>> triangles =
	 * this.sceneDesc.getTriangles();
	 * 
	 * for (Map<String, String> triangle : triangles) { if (Set.of("p1", "p2",
	 * "p3").equals(triangle.keySet())) { Point p1, p2, p3;
	 * 
	 * try { p1 = getPointFromValue(triangle.get("p1")); p2 =
	 * getPointFromValue(triangle.get("p2")); p3 =
	 * getPointFromValue(triangle.get("p3")); } catch (NumberFormatException e) {
	 * continue; }
	 * 
	 * if (p1 != null && p2 != null && p3 != null) this.scene.geometries.add(new
	 * Triangle(p1, p2, p3)); } } }
	 * 
	 *//**
		 * Gets the Scene object
		 *
		 * @return the Scene object
		 *//*
			 * public Scene getScene() { return this.scene; }
			 */
}
