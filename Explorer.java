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

    /**
     *
     * This method checks the row and column from the 2D array and then returns the available exits depending on where
     * the user currently is in the array
     *
     * @param row The row in which the user currently is in the 2D array
     * @param column The column in which the user currently is in the 2D array
     * @return returns the available exits to the user playing the game
     */

    public static String availableExits(int row, int column){

        // declare a string available exit to track the available exits
        String availableExit;

        // print out the current location to the user
        System.out.println("You are in location row:" + row + " col:" + column);

        // conditional statements to check where the user and change the available exits according to that
        if (row == 0 && column == 0) {
            availableExit = "ES";
        } else if (row == 0) {
            availableExit = "NES";
        } else if (column == 0) {
            availableExit = "ESW";
        } else {
            availableExit = "NESW";
        }

        // return the availableExit variable
        return availableExit;
    }

    /**
     * This method takes in the 2D array as an input and a scanner object too, it basically is the main interface of the
     * game that the user will interact with and if the player wins the game (i.e, reaches the number 3 on the layout),
     * the method breaks and returns true, or else it will return false
     *
     * @param mapLayout The 2D array which was initialized by the method
     * @return returns either true/false depending on weather the user has won the game
     */

    public static boolean mainGame(int[][] mapLayout){

        // declare all the necessary variables
        int noOfRounds = 1;
        boolean gameWon = false;
        int row = 0;
        int col = 0;

        Scanner in = new Scanner(System.in); // creates a new scanner object

        while (!gameWon) {



        }
        return gameWon;
    }

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in); // creates the scanner object 'in'

        // prompts the user for the filename and inputs it
        System.out.print("Enter a map file name: ");
        String map_name = console.nextLine();

        int[][] dimensions = initializeArray(map_name); // calls the initializeArray() function and stores the output 2D array in a variable called dimensions

        boolean gameWon = mainGame(dimensions);
    }
}
