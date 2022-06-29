package mx.tc.j2se.evaluation;

/**
 * This class contain the main method where the classes are proved, also contain a class which detect the index of the
 * biggest circle in an array
 * @author Benjamin Sanchez Martinez
 */
public class Evaluation1 {
    /**
     * This method indicates the index of the biggest circle within an array
     * @param circles is an array that contains a certain number of circles
     * @return the index where is the largest circle in the array
     */
    public static int biggestCircle(Circle circles[]){
        int biggestIndex = 0;
        double biggestArea = 0;
        for (int i = 0; i<circles.length; i++){
            if(circles[i].getArea() > biggestArea){
                biggestArea = circles[i].getArea();
                biggestIndex = i;
            }
        }
        return biggestIndex;
    }

    /**
     * This is the main method, where the methods are proved
     */
    public static void main(String[] args) {
        // Catch the exception with a message
        try {
            Circle circle = new Circle(-1);
        } catch (IllegalArgumentException e){
            System.out.println("The radius is not valid, must be a positive value, try again please, don't give up!! :)");
        }

        // Create the array
        Circle circle1 = new Circle(55);
        Circle circle2 = new Circle(44);
        Circle circle3 = new Circle(50);

        Circle circles[] = {circle1, circle2, circle3};

        int index = biggestCircle(circles);

        //get the largest circle based in the index
        System.out.println("The largest circle has " + circles[index].getRadius() + " unities in its radius");
    }
}
