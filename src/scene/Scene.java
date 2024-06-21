package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

//================== Scene class (PDS - Plain Data Structure) ==================//
public class Scene {

    List<LightSource> lights = new LinkedList<LightSource>();

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public List<LightSource> getLights() {
        return this.lights;
    }

    //==== we use with design pattern called "builder pattern" ====//

    private final String name; // the scene's name
    private final Color background; // the background's color (black is the default)
    private final AmbientLight ambientLight; //the ambientLight - תאורה סביבתית
    private final Geometries geometries; // the 3D model

    /**
     * Constructs a new Scene object with the specified name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK; // Default background color is black
        this.ambientLight = new AmbientLight(); // Default ambient light
        this.geometries = new Geometries(); // Initialize empty geometries collection
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        if (geometries == null) return new Geometries();
        return geometries;
    }

    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
    }
    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return This Scene object to allow for chaining of method calls.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        ambientLight = ambientLight;
        return this;
    }
    //================== SceneBuilder class ==================//
    public static class SceneBuilder {
        private final String name; // the scene's name
        private Color background = Color.BLACK; // the background's color (black is the default)
        private AmbientLight ambientLight = new AmbientLight(); //the ambientLight
        private Geometries geometries = new Geometries(); // the 3D model

        public SceneBuilder(String name) {
            this.name = name;
        }

        //========= chaining (שירשור) method =========//

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            return new Scene(this);
        }
    }
}