import java.util.ArrayList;
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

        int[][] map = null;

        // Try to open and read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {

            // The first line tells us how big the map is
            String firstLine = reader.readLine();
            int dimension = Integer.parseInt(firstLine.trim());

            // Create our map with the right size
            map = new int[dimension][dimension];

            // Variables to keep track of where we are in the map
            int location;
            int row = 0;
            int col = 0;

            // Read the file character by character
            while ((location = reader.read()) != -1) {
                char ch = (char) location;

                // Commas separate the numbers, so we skip them
                if (ch == ',') {
                    continue;
                }

                // When we hit a newline, move to the next row
                if (ch == '\n') {
                    row++;
                    col = 0;
                    continue;
                }

                // If it's a digit, add it to our map
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
     * This method checks the row and column from the 2D array and then returns the available exits depending on where
     * the user currently is in the array
     *
     * @param row The row in which the user currently is in the 2D array
     * @param column The column in which the user currently is in the 2D array
     * @param mapLayout The 2D array to check dimensions
     * @return returns the available exits to the user playing the game
     */
    public static String availableExits(int row, int column, int[][] mapLayout){

        String availableExit = "";
        int maxIndex = mapLayout.length - 1;

        // Check which directions the player can move from their current position
        // We can't go North if we're at the top row
        if (row > 0) {
            availableExit += "N";
        }
        // Can't go East if we're at the rightmost column
        if (column < maxIndex) {
            availableExit += "E";
        }
        // Can't go South if we're at the bottom row
        if (row < maxIndex) {
            availableExit += "S";
        }
        // Can't go West if we're at the leftmost column
        if (column > 0) {
            availableExit += "W";
        }

        return availableExit;
    }

    /**
     * This method checks adjacent rooms for dangers and provides hints to the player
     *
     * @param row The current row position
     * @param column The current column position
     * @param mapLayout The 2D array representing the game map
     */
    public static void checkForHints(int row, int column, int[][] mapLayout) {

        int maxIndex = mapLayout.length - 1;
        boolean foundPit = false;
        boolean foundBeast = false;

        // Check all four adjacent rooms for dangers
        // Check North
        if (row > 0 && mapLayout[row - 1][column] == 2) {
            foundPit = true;
        }
        if (row > 0 && mapLayout[row - 1][column] == 1) {
            foundBeast = true;
        }

        // Check East
        if (column < maxIndex && mapLayout[row][column + 1] == 2) {
            foundPit = true;
        }
        if (column < maxIndex && mapLayout[row][column + 1] == 1) {
            foundBeast = true;
        }

        // Check South
        if (row < maxIndex && mapLayout[row + 1][column] == 2) {
            foundPit = true;
        }
        if (row < maxIndex && mapLayout[row + 1][column] == 1) {
            foundBeast = true;
        }

        // Check West
        if (column > 0 && mapLayout[row][column - 1] == 2) {
            foundPit = true;
        }
        if (column > 0 && mapLayout[row][column - 1] == 1) {
            foundBeast = true;
        }

        // Give the player a hint if danger is nearby
        if (foundPit) {
            System.out.println("You feel a breeze.");
        }
        if (foundBeast) {
            System.out.println("You hear a growling noise.");
        }
    }

    public static ArrayList<Integer> moveInLayout(int row, int column, String availableExits, int[][] mapLayout){

        ArrayList<Integer> finalRowCol = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        // Check what's at the current location and respond accordingly
        if (mapLayout[row][column] == 1) {
            // Uh oh, there's a beast here!
            System.out.println("Oh no! You have run into a ravenous Bugblatter Beast!");
            finalRowCol.add(-2); // -2 means game over (lost)
        }
        else if (mapLayout[row][column] == 2) {
            // Player fell into a pit
            System.out.println("AAAARGH! You have fallen into a pit!");
            finalRowCol.add(-2); // -2 means game over (lost)
        }
        else if (mapLayout[row][column] == 3) {
            // Victory! Player found the gold
            System.out.println("You have found the gold!");
            System.out.println();
            System.out.println("You have won!  Congratulations!");
            finalRowCol.add(-1); // -1 means game won
        }
        else if (mapLayout[row][column] == 0){
            // Safe spot, player can choose where to move next
            System.out.print("Which way do you want to move? ");
            String userMove = in.nextLine().toUpperCase();

            // Make sure the player chose a valid direction
            if (!availableExits.contains(userMove)) {
                System.out.println("Invalid move! Please choose from available exits: " + availableExits);
                // Return current position since move was invalid
                finalRowCol.add(row);
                finalRowCol.add(column);
            }
            else if (userMove.equals("N")) {
                // Moving North means decreasing the row
                row = row - 1;
                finalRowCol.add(row);
                finalRowCol.add(column);
            } else if (userMove.equals("E")) {
                // Moving East means increasing the column
                column = column + 1;
                finalRowCol.add(row);
                finalRowCol.add(column);
            } else if (userMove.equals("S")) {
                // Moving South means increasing the row
                row = row + 1;
                finalRowCol.add(row);
                finalRowCol.add(column);
            } else if (userMove.equals("W")) {
                // Moving West means decreasing the column
                column = column - 1;
                finalRowCol.add(row);
                finalRowCol.add(column);
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
     * @return returns either true/false depending on whether the user has won the game
     */
    public static boolean mainGame(int[][] mapLayout){

        // Start at the top-left corner of the map
        int row = 0;
        int col = 0;
        boolean gameWon = false;
        boolean gameLost = false;

        // Keep playing until the player wins or loses
        while (!gameWon && !gameLost) {

            // Show the player where they are
            System.out.println("You are in location row:" + row + " col:" + col);

            // Figure out which directions they can go
            String availableExit = availableExits(row, col, mapLayout);
            System.out.println("There are exits to the: " + availableExit);

            // Check for nearby dangers and give hints
            checkForHints(row, col, mapLayout);

            // Get the player's move and see what happens
            ArrayList<Integer> moveLayout = moveInLayout(row, col, availableExit, mapLayout);

            // Check if the move resulted in a game-ending event
            if (moveLayout.size() > 0) {
                int statusCode = moveLayout.get(0);

                if (statusCode == -1) {
                    // Player found the gold and won!
                    gameWon = true;
                } else if (statusCode == -2) {
                    // Player hit a beast or pit and lost
                    gameLost = true;
                } else {
                    // Normal move - update the player's position
                    row = moveLayout.get(0);
                    col = moveLayout.get(1);
                }
            }
        }

        return gameWon;
    }

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        // Ask the player for the map file
        System.out.print("Enter a map file name: ");
        String map_name = console.nextLine();

        // Load the map from the file
        int[][] dimensions = initializeArray(map_name);

        // Make sure the map loaded successfully before starting the game
        if (dimensions != null) {
            boolean gameWon = mainGame(dimensions);
        } else {
            System.out.println("Failed to load map. Exiting.");
        }
    }
}