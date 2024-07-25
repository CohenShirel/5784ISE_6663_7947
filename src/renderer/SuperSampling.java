package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
/**
 * This class represents super sampling through a grid
 *
 * @author 
 */
public class SuperSampling {
    public double size = 1; // width / height ratio

    /**
     * Set the size of the grid
     *
     * @param size width / height ratio
     * @return the updated SuperSampling onject
     */
    public SuperSampling setSize(double size) {
        this.size = size;
        return this;
    }

    /**
     * Construct the rays through the grid
     *
     * @param width grid width
     * @param height grid height
     * @param source source point
     * @param gridCenter center of the grid
     * @param vUp up vector
     * @param vRight right vector
     * @return the rays
     */
    public List<Ray> constructRaysThroughGrid(
            double width,
            double height,
            Point source,
            Point gridCenter,//ציון המרכז של הרשת שדרכה ייבנו הקרניים
            Vector vUp,
            Vector vRight
    ) {
        List<Ray> rays = new LinkedList<>();

        double xJ;
        double yI = height / (2 * this.size) - height / 2;
        Point dest;

        for (int i = 0; i < this.size; i++) {
            xJ = width / (2 * this.size) - width / 2;

            for (int j = 0; j < this.size; j++) {
                dest = gridCenter;
                if (xJ != 0)
                    dest = dest.add(vRight.scale(xJ));
                if (yI != 0)
                    dest = dest.add(vUp.scale(yI));

                rays.add(new Ray(source, dest.subtract(source)));
                xJ = alignZero(xJ + width / this.size);

                if (xJ > width / 2)
                    xJ = -width / (2 * this.size);

            }

            yI = alignZero(yI + height / this.size);
            if (yI > height / 2)
                yI = -height / (2 * this.size);
        }
        return rays;
    }
}
