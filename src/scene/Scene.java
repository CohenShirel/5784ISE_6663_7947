package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene class represents a scene in the 3D world.
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new ArrayList<>();

    /**
     * Constructor of the class
     * @param name the name
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param  background  the color to set as the background
     * @return             the Scene object for method chaining
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light for the scene.
     *
     * @param  ambientLight  the ambient light to set
     * @return               the updated Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries for the scene.
     *
     * @param  geometries   the geometries to be set
     * @return              the updated scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the lights for the scene.
     *
     * @param  lights  the list of light sources to set
     * @return        the updated Scene object
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}