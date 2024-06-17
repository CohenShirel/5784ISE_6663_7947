package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Scene class representing a 3D scene with a name, background color, ambient light, and geometries.
 */
public class Scene {

    //==== PDS - all fields are public ====//
    public final String name; // the scene's name
    public Color background; // the background's color (black is the default)
    public AmbientLight ambientLight; // the ambientLight - תאורה סביבתית
    public Geometries geometries; // the 3D model

    /**
     * Constructor that initializes the scene's name
     * @param name the scene's name
     */
    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
        this.geometries = new Geometries();
    }

    //================== SceneBuilder class ==================//
    public static class SceneBuilder {
        private final String name; // the scene's name
        private Color background = Color.BLACK; // the background's color (black is the default)
        private AmbientLight ambientLight = AmbientLight.NONE; // the ambientLight
        private Geometries geometries = new Geometries(); // the 3D model

        public SceneBuilder(String name){
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
            Scene scene = new Scene(this.name);
            scene.background = this.background;
            scene.ambientLight = this.ambientLight;
            scene.geometries = this.geometries;
            return scene;
        }
    }
}
