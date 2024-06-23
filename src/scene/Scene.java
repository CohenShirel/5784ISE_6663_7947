package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    private List<LightSource> lights = new LinkedList<LightSource>();

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public List<LightSource> getLights() {
        return this.lights;
    }

    private final String name;
    private Color background; 
    private AmbientLight ambientLight; 
    private final Geometries geometries;

    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK; 
        this.ambientLight = new AmbientLight(); 
        this.geometries = new Geometries(); 
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
        return geometries;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    public Scene addGeometries(Intersectable... geometries) {
        this.geometries.add(geometries);
        return this;
    }

    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
    }

    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK; 
        private AmbientLight ambientLight = new AmbientLight(); 
        private Geometries geometries = new Geometries(); 

        public SceneBuilder(String name) {
            this.name = name;
        }

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
