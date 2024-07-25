package scence.parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class parses XML data to data structures relevant to a Scene object
 *
 * @author Shirel Cohen& Neomi Golkin
 */
public class SceneDescriptor {
	/*
	 * private final Map<String, String> sceneAttributes; private final Map<String,
	 * String> ambientLightAttributes; private List<Map<String, String>> planes;
	 * private List<Map<String, String>> spheres; private List<Map<String, String>>
	 * triangles; private final Element root; private Element geometries;
	 * 
	 *//**
		 * Constructor to initialize this class and start parsing the XML file
		 *
		 * @param filePath XML file path
		 * @throws ParserConfigurationException .
		 * @throws IOException                  .
		 * @throws SAXException                 .
		 */
	/*
	 * public SceneDescriptor(String filePath) throws ParserConfigurationException,
	 * IOException, SAXException { this.sceneAttributes = new HashMap<>();
	 * this.ambientLightAttributes = new HashMap<>();
	 * 
	 * File file = new File(filePath); DocumentBuilderFactory dbf =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder db =
	 * dbf.newDocumentBuilder(); Document doc = db.parse(file); this.root =
	 * doc.getDocumentElement(); this.planes = null; this.spheres = null;
	 * this.triangles = null; this.geometries = null;
	 * 
	 * this.parseSceneAttributes(); this.parseAmbientLightAttributes();
	 * 
	 * if (hasChild(this.root, "geometries")) { this.geometries = (Element)
	 * this.root.getElementsByTagName("geometries").item(0); this.planes =
	 * this.parseGeometry("plane"); this.spheres = this.parseGeometry("sphere");
	 * this.triangles = this.parseGeometry("triangle"); } }
	 * 
	 *//**
		 * Checks if an element has a child with the provided name
		 *
		 * @param parent   parent element
		 * @param nodeName child expected name
		 * @return true if child is present
		 */
	/*
	 * private static boolean hasChild(Element parent, String nodeName) { NodeList
	 * childNodes = parent.getChildNodes(); int length = childNodes.getLength(); for
	 * (int i = 0; i < length; i++) if
	 * (childNodes.item(i).getNodeName().equals(nodeName)) return true; return
	 * false; }
	 * 
	 *//**
		 * Parses the Scene attributes
		 */
	/*
	 * private void parseSceneAttributes() { NamedNodeMap attributes =
	 * this.root.getAttributes(); int length = attributes.getLength();
	 * 
	 * Node attribute; for (int i = 0; i < length; i++) { attribute =
	 * attributes.item(i); this.sceneAttributes.put(attribute.getNodeName(),
	 * attribute.getNodeValue()); }
	 * 
	 * this.sceneAttributes.putIfAbsent("background-color", "0 0 0"); }
	 * 
	 *//**
		 * Parses the Ambient Light attributes
		 */
	/*
	 * private void parseAmbientLightAttributes() { if (hasChild(this.root,
	 * "ambient-light")) { NamedNodeMap attributes =
	 * this.root.getElementsByTagName("ambient-light").item(0).getAttributes(); int
	 * length = attributes.getLength();
	 * 
	 * Node attribute; for (int i = 0; i < length; i++) { attribute =
	 * attributes.item(i); this.ambientLightAttributes.put(attribute.getNodeName(),
	 * attribute.getNodeValue()); } }
	 * 
	 * this.ambientLightAttributes.putIfAbsent("color", "0 0 0");
	 * this.ambientLightAttributes.putIfAbsent("k", "0"); }
	 * 
	 *//**
		 * Parses a specified geometry
		 *
		 * @param geometryName specified geometry name
		 * @return parsed data structure
		 */
	/*
	 * private List<Map<String, String>> parseGeometry(String geometryName) { if
	 * (!hasChild(this.geometries, geometryName)) return null;
	 * 
	 * List<Map<String, String>> result = new ArrayList<>(); NodeList requestedGeos
	 * = this.geometries.getElementsByTagName(geometryName); int length =
	 * requestedGeos.getLength();
	 * 
	 * NamedNodeMap attributes; for (int i = 0; i < length; i++) { attributes =
	 * requestedGeos.item(i).getAttributes(); int attrLength =
	 * attributes.getLength();
	 * 
	 * result.add(new HashMap<>());
	 * 
	 * Node attribute; for (int j = 0; j < attrLength; j++) { attribute =
	 * attributes.item(j); result.get(i).put(attribute.getNodeName(),
	 * attribute.getNodeValue()); } }
	 * 
	 * return result; }
	 * 
	 *//**
		 * Gets the Scene attributes data structure
		 *
		 * @return Scene attributes data structure
		 */
	/*
	 * public Map<String, String> getSceneAttributes() { return
	 * this.sceneAttributes; }
	 * 
	 *//**
		 * Gets the Ambient Light data structure
		 *
		 * @return Ambient Light data structure
		 */
	/*
	 * public Map<String, String> getAmbientLightAttributes() { return
	 * this.ambientLightAttributes; }
	 * 
	 *//**
		 * Gets the Planes data structure
		 *
		 * @return Planes data structure
		 */
	/*
	 * public List<Map<String, String>> getPlanes() { return this.planes; }
	 * 
	 *//**
		 * Gets the Spheres data structure
		 *
		 * @return Spheres data structure
		 */
	/*
	 * public List<Map<String, String>> getSpheres() { return this.spheres; }
	 * 
	 *//**
		 * Gets the Triangles data structure
		 *
		 * @return Triangles data structure
		 *//*
			 * public List<Map<String, String>> getTriangles() { return this.triangles; }
			 */
}
