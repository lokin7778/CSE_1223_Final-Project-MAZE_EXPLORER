import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Explorer {

    /**
     * This method is used to load the map layout into a 2D Array by taking in the file to be read in as a parameter
     *
     * @param fName (fileName for the file to be read in)
     * @return The 2D Array that the method created
     */

    public static int[][] initializeArray(String fName){

        // use a try-catch block to check if the file exists and load the data in
        // a 2D array
        int[][] map = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {

            // Read the dimension from first line
            String firstLine = reader.readLine();
            int dimension = Integer.parseInt(firstLine.trim());

            // Create 2D array
            map = new int[dimension][dimension];

            int location;
            int row = 0;
            int col = 0;

            while ((location = reader.read()) != -1) {
                char ch = (char) location;

                // Skip commas
                if (ch == ',') {
                    continue;
                }

                // Handle newlines - move to next row
                if (ch == '\n') {
                    row++;
                    col = 0;  // Reset column
                    continue;
                }

                // Process digits and add to array
                if (Character.isDigit(ch)) {
                    int digit = Character.getNumericValue(ch);
                    map[row][col] = digit;
                    col++;
                }
            }

            reader.close(); // closes the BufferedReader object

        } catch (IOException e) {
            System.out.println("ERROR - Cannot load file " + fName);
        }

        return map;
    }



    public static void main(String[] args) {

        Scanner console = new Scanner(System.in); // creates the scanner object 'in'

        // prompts the user for the filename and inputs it
        System.out.print("Enter a map file name: ");
        String map_name = console.nextLine();

        int[][] dimensions = initializeArray(map_name); // calls the initializeArray() function and stores the output 2D array in a variable called dimensions


    }
}
