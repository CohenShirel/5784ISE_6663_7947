package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    /** The name of the scene. */
    public String name;

    /** The background color of the scene. */
    public Color background = Color.BLACK;

    /** The ambient light in the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The geometries present in the scene. */
    public Geometries geometries = new Geometries();

    /** The light sources present in the scene. */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a new Scene with the specified name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background the background color to set
     * @return this Scene object for method chaining
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light to set
     * @return this Scene object for method chaining
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries present in the scene.
     *
     * @param geometries the geometries to set
     * @return this Scene object for method chaining
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the light sources present in the scene.
     *
     * @param lights the light sources to set
     * @return this Scene object for method chaining
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
