import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Explorer {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in); // creates the scanner object 'in'

        // prompts the user for the filename and inputs it
        System.out.print("Enter a map file name: ");
        String map_name = console.nextLine();

        // use a try-catch block to check if the file exists and load the data in
        // a 2D array
        try (BufferedReader reader = new BufferedReader(new FileReader(map_name))) {

            int dimension = reader.read(); // this reads the first line's character
            int numericDimension = Character.getNumericValue(dimension); // gets the int value of the dimension

            // create a new array using the dimension
            int[][] locations = new int[numericDimension][numericDimension];

            
        } catch (IOException e) {
            System.out.println("ERROR - Cannot load file " + map_name);
        }


    }
}
