import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
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

        // conditional statements to check where the user and change the available exits according to that
        if (row == 0 && column == 0) {
            availableExit = "ES";
        } else if (row == 0) {
            availableExit = "ESW";
        } else if (column == 0) {
            availableExit = "NES";
        } else {
            availableExit = "NESW";
        }

        // return the availableExit variable
        return availableExit;
    }

    public static ArrayList<Integer> moveInLayout(int row, int column, String availableExits, int[][] mapLayout){

        ArrayList<Integer> finalRowCol = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        if (mapLayout[row][column] == 1) {
            System.out.println("Oh no! You have run into a ravenous Bugblatter Beast!");
            finalRowCol.add(-1);
        }
        else if (mapLayout[row][column] == 2) {
            System.out.println("AAAARGH! You have fallen into a pit!");
            finalRowCol.add(-2);
        }
        else if (mapLayout[row][column] == 3) {
            System.out.println("You have found the gold!");
            System.out.println();
            System.out.println("You have won!  Congratulations!");
            finalRowCol.add(-3);
        }
        else if (mapLayout[row][column] == 0){

            // print out the current location to the user
            System.out.println("You are in location row:" + row + " col:" + column);

            System.out.println("There are exits to the: " + availableExits);

            // Check each direction individually to avoid array out of bounds
            boolean foundBeast = false;
            boolean foundPit = false;

            // Check North (row - 1)
            if (row > 0) {
                if (mapLayout[row - 1][column] == 1) {
                    foundBeast = true;
                }
                else if (mapLayout[row - 1][column] == 2) {
                    foundPit = true;
                }
            }

            // Check South (row + 1)
            if (row < mapLayout.length - 1) {
                if (mapLayout[row + 1][column] == 1){
                    foundBeast = true;
                }
                else if (mapLayout[row + 1][column] == 2){
                    foundPit = true;
                }
            }

            // Check West (column - 1)
            if (column > 0) {
                if (mapLayout[row][column - 1] == 1){
                    foundBeast = true;
                }
                else if (mapLayout[row][column - 1] == 2){
                    foundPit = true;
                }
            }

            // Check East (column + 1)
            if (column < mapLayout[0].length - 1) {
                if (mapLayout[row][column + 1] == 1){
                    foundBeast = true;
                }
                else if (mapLayout[row][column + 1] == 2){
                    foundPit = true;
                }
            }

            // Print hints
            if (foundBeast) {
                System.out.println("You hear a growling noise.");
            }
            if (foundPit) {
                System.out.println("You feel a breeze.");
            }

            System.out.print("Which way do you want to move? ");
            String userMove = in.nextLine().toUpperCase();
            System.out.println();

            switch (userMove) {
                case "N" -> {
                    row = row - 1;
                    finalRowCol.add(row);
                    finalRowCol.add(column);
                }
                case "E" -> {
                    column = column + 1;
                    finalRowCol.add(row);
                    finalRowCol.add(column);
                }
                case "S" -> {
                    row = row + 1;
                    finalRowCol.add(row);
                    finalRowCol.add(column);
                }
                case "W" -> {
                    column = column - 1;
                    finalRowCol.add(row);
                    finalRowCol.add(column);
                }
            }


        }

        return finalRowCol;

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
        boolean gameWon = false;
        int row = 0;
        int col = 0;
        
        while (!gameWon) {

            String availableExit = availableExits(row, col);

            ArrayList<Integer> moveLayout = moveInLayout(row, col, availableExit, mapLayout);

            if (moveLayout.get(0)==-3){
                gameWon = true;
            }

            else if (moveLayout.get(0)==-2){
                break;
            }

            else if (moveLayout.get(0)==-1){
                break;
            }

            else{
                row = moveLayout.get(0);
                col = moveLayout.get(1);
            }

        }
        return gameWon;
    }

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in); // creates the scanner object 'in'

        // prompts the user for the filename and inputs it
        System.out.print("Enter a map file name: ");
        String map_name = console.nextLine();

        int[][] dimensions = initializeArray(map_name); // calls the initializeArray() function and stores the output 2D array in a variable called dimensions

        if (dimensions!=null) {
            boolean gameWon = mainGame(dimensions);
        }
    }
}
