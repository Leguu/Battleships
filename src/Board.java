//
// Assignment 4
// For COMP 248 Section EC – Fall 2020
//

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

// The board on which the game is played.
// This handles pretty much everything Game does not.
// Fun fact: The entire game can be run on arbitrary numbers of rows, columns, ships, and grenades!
// See the constructor for constraints.
class Board {
    // This is the internal board.
    // It is in the form: Grid[Row][Column].
    // Columns are marked by Letters.
    // Rows are marked by Numbers.
    private final Tile[][] board;

    // Initializes the board when it is created.
    // Ensure rows × columns > 2 × (ships + grenades).
    // Ensure 1 <= rows <= 9.
    // Ensure 1 <= columns <= 26.
    // If these constraints are not met, the program will crash.
    Board(int rows, int columns, int ships, int grenades) {
        if (rows * columns < 2 * (ships + grenades)) {
            throw new IllegalArgumentException("There are more ships and grenades than there is space on the board!");
        } else if (rows < 1 || 9 < rows) {
            throw new IllegalArgumentException("Rows have to be in the single digits, excluding 0.");
        } else if (columns < 1 || 26 < columns) {
            throw new IllegalArgumentException("There can't be more columns than letters in the alphabet (26).");
        }

        board = new Tile[rows][columns];
        for (Tile[] tiles : board)
            for (int i = 0; i < tiles.length; i++)
                tiles[i] = new Tile();

        System.out.println(this);

        System.out.println("Place your ships:");
        for (int i = 0; i < ships; i += 1)
            placeUserTile(Type.SHIP);

        System.out.println("Place your grenades:");
        for (int i = 0; i < grenades; i++)
            placeUserTile(Type.GRENADE);

        System.out.print("Your opponent is placing their pieces... ");
        for (int i = 0; i < ships; i++)
            placeEnemyTile(Type.E_SHIP);
        for (int i = 0; i < grenades; i++)
            placeEnemyTile(Type.E_GRENADE);
        System.out.println("Done!");
    }

    // Gets a random point that is within the bounds of the board.
    int[] randomPoint() {
        Random r = new Random();
        return new int[]{r.nextInt(board.length), r.nextInt(board[0].length)};
    }

    // This function will accept user input, and ask again if the user input is not a point.
    int[] inputPoint() {
        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("> ");
                String input = in.nextLine();
                return parsePoint(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // This will place an enemy tile on a random point.
    void placeEnemyTile(Type type) {
        int[] point = randomPoint();
        while (get(point).type != Type.EMPTY)
            point = randomPoint();
        set(point, type);
    }

    // Asks the user to set a type, but makes sure that it's unique
    private void placeUserTile(Type type) {
        int[] point = inputPoint();
        while (get(point).type != Type.EMPTY) {
            System.out.println("You can't place that if there's something already there...");
            point = inputPoint();
        }
        set(point, type);
    }

    // Set a tile on the grid to some type.
    void set(int[] point, Type type) {
        board[point[0]][point[1]].type = type;
    }

    // Get the tile of a grid.
    Tile get(int[] point) {
        return board[point[0]][point[1]];
    }

    // Takes an input in the form {Letter}{Number} or {Number}{Letter} and transforms it into an internal point.
    // The format of an internal point is: {Row, Column} or {Number, Letter}.
    // Since this is the main "entry point" for points,
    // We're doing all checks here.
    private int[] parsePoint(String point) {
        char[] characters = point.toCharArray();

        if (characters.length != 2)
            throw new IllegalArgumentException("You haven't entered 2 characters.");

        // This puts the letters SECOND
        Arrays.sort(characters);

        if (!Character.isDigit(characters[0]) || !Character.isLetter(characters[1]))
            throw new IllegalArgumentException("Your input has to be of the form: {Letter}{Number}.");

        int[] points = new int[]{Character.getNumericValue(characters[0]) - 1, coordinate(characters[1])};

        // Validate that the points are correct
        if (points[0] < 0 || board.length <= points[0])
            throw new IndexOutOfBoundsException("Row is not valid.");
        if (points[1] < 0 || board[points[0]].length <= points[1])
            throw new IndexOutOfBoundsException("Column is not valid.");

        return points;
    }

    // Takes a letter and transforms it into an index
    private int coordinate(char character) {
        character = Character.toUpperCase(character);
        // This error should be impossible, but it's there just in case.
        if (!Character.isLetter(character))
            throw new IllegalArgumentException(character + " is not a letter...");
        return character - 65;
    }

    // Takes an index and transforms it into a row label
    char label(int number) {
        char character = (char) (number + 65);
        // This error should be impossible, but it's there just in case.
        if (!Character.isLetter(character))
            throw new IllegalArgumentException("The number " + number + " cannot be transformed into a letter.");
        return character;
    }

    // Reveal all tiles on the board.
    // IRREVERSIBLE ACTION.
    void reveal() {
        for (Tile[] tiles : board)
            for (Tile tile : tiles)
                tile.revealed = true;
        System.out.println(this);
    }

    // The string representation of the board.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // First print the top row of labels, i.e., A B C...
        sb.append("____");
        for (int i = 0; i < board[0].length; i += 1)
            sb.append(label(i)).append('_');
        sb.append('\n');

        // Next Go through every row and print the row number and its contents
        for (int i = 0; i < board.length; i++) {
            sb.append(i + 1).append(" | ");
            // This just turns everything on the row into a string and joins them
            sb.append(Arrays.stream(board[i]).map(Tile::toString).collect(Collectors.joining(" ")));
            sb.append('\n');
        }
        // Not pretty but it gets rid of the final newline...
        sb.deleteCharAt(sb.lastIndexOf("\n"));

        return sb.toString();
    }
}
