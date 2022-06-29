package mx.tc.j2se.evaluation;

import static java.lang.Math.PI;

/**
 * This Class create the circles that will be evaluated in the main class
 * @author  Benjamin Sanchez Martinez
 */
public class Circle {
    private  int radius;

    /**
     * This constructor set the circle radius in a value of 1
     */
    public Circle(){
        this.radius = 1;
    }

    /**
     * This constructor allows the user to set the value of the circle, also contains an exception if the circle radius
     * is zero or less
     * @param radius is the value of the radius
     */
    public Circle(int radius){
        if (radius <= 0){
            throw  new IllegalArgumentException("The radius needs to be positive");
        }
        this.radius = radius;
    }

    /**
     * This method allows the user to set the value of the circle, also contains an exception if the circle radius
     * is zero or less
     * @param radius is the value of the radius
     */
    public void setRadius(int radius) {
        if (radius <= 0){
            throw  new IllegalArgumentException("The radius needs to be positive");
        }
        this.radius = radius;
    }

    /**
     * This method throws the value of the radius
     * @return the value of the radius
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * This method returns the area of a circle
     * @return the circle area
     */
    public double getArea (){
        double area = (this.radius * this.radius) * (PI);
        return area;
    }
}
