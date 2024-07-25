package parser;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import scence.parser.SceneDescriptor;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing SceneDescriptor
 *
 * @author 
 */
class SceneDescriptorTest {
  /*  private final static String FILEPATH = "xml/basicRenderTestTwoColors2.xml";
    private SceneDescriptor sceneDescriptor;

   // @BeforeEach
    void setUp() throws ParserConfigurationException, IOException, SAXException {
        sceneDescriptor = new SceneDescriptor(FILEPATH);
    }

    *
     * Test method for {@link SceneDescriptor#getSceneAttributes()}
     
    @Test
    void testGetSceneAttributes() {
        // TC01: xml/basicRenderTestTwoColors2.xml
        Map<String, String> expected = new HashMap<>() {
            {
                put("background-color", "75 127 190");
            }
        };

        assertEquals(expected, sceneDescriptor.getSceneAttributes());
    }

    *
     * Test method for {@link SceneDescriptor#getAmbientLightAttributes()}
     
    @Test
    void testGetAmbientLightAttributes() {
        // TC01: xml/basicRenderTestTwoColors2.xml
        Map<String, String> expected = new HashMap<>() {
            {
                put("color", "255 191 191");
                put("k", "1.0");
            }
        };

        assertEquals(expected, sceneDescriptor.getAmbientLightAttributes());
    }

    *
     * Test method for {@link SceneDescriptor#getPlanes()}
     
    @Test
    void testGetPlanes() {
        // TC01: xml/basicRenderTestTwoColors2.xml
        assertNull(sceneDescriptor.getPlanes());
    }

    *
     * Test method for {@link SceneDescriptor#getSpheres()}
     
    @Test
    void testGetSpheres() {
        // TC01: xml/basicRenderTestTwoColors2.xml
        List<Map<String, String>> expected = new ArrayList<>();
        expected.add(new HashMap<>() {
            {
                put("center", "0 0 -100");
                put("radius", "50");
            }
        });

        assertEquals(expected, sceneDescriptor.getSpheres());
    }

    *
     * Test method for {@link SceneDescriptor#getTriangles()}
     
    @Test
    void testGetTriangles() {
        // TC01: xml/basicRenderTestTwoColors2.xml
        List<Map<String, String>> expected = new ArrayList<>();
        expected.add(new HashMap<>() {
            {
                put("p1", "-100 0 -100");
                put("p2", "0 100 -100");
                put("p3", "-100 100 -100");
            }
        });
        expected.add(new HashMap<>() {
            {
                put("p1", "-100 0 -100");
                put("p2", "0 -100 -100");
                put("p3", "-100 -100 -100");
            }
        });
        expected.add(new HashMap<>() {
            {
                put("p1", "100 0 -100");
                put("p2", "0 -100 -100");
                put("p3", "100 -100 -100");
            }
        });

        assertEquals(expected, sceneDescriptor.getTriangles());
    }*/
}
