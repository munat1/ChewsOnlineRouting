import java.util.Vector;

/**
 * 2D vector class implementation.
 *
 * @author Johannes Diemke
 */
import java.util.HashSet;
import java.util.ArrayList;
public class Vector2D {
    public HashSet<Vector2D> nachbarMenge = new HashSet<>();
    public double x;
    public double y;
    public Boolean isHighway = false;

    /**
     * Constructor of the 2D vector class used to create new vector instances.
     *
     * @param x
     *            The x coordinate of the new vector
     * @param y
     *            The y coordinate of the new vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void addNeighbour(Vector2D v) {
        nachbarMenge.add(v);
    }
    /**
     * Subtracts the given vector from this.
     *
     * @param vector
     *            The vector to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    public Vector2D sub(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Adds the given vector to this.
     *
     * @param vector
     *            The vector to be added to this
     * @return A new instance holding the result of the vector addition
     */
    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Multiplies this by the given scalar.
     *
     * @param scalar
     *            The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public Vector2D mult(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Computes the magnitude or length of this.
     *
     * @return The magnitude of this
     */
    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Computes the dot product of this and the given vector.
     *
     * @param vector
     *            The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public double dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }
     public double distance(Vector2D v) {
         double dist = sub(v).mag();
         if(this.isHighway && v.isHighway && dist!= 0.0){
             return 0.000000001;
         }
         return dist;
     }

    /**
     * Computes the 2D pseudo cross product Dot(Perp(this), vector) of this and
     * the given vector.
     *
     * @param vector
     *            The vector to be multiplied to the perpendicular vector of
     *            this
     * @return A new instance holding the result of the pseudo cross product
     */
    public double cross(Vector2D vector) {
        return this.y * vector.x - this.x * vector.y;
    }

    @Override
    public String toString() {
        String neig = "";
        for(Vector2D v : nachbarMenge){
            neig += "(" + v.x + "," + v.y + ")-";
        }
        return "vertex[ x=" + x + ", y=" + y + " -> " + neig + "]";
    }
    public Boolean isHighway() {
        return this.isHighway;
    }

}
