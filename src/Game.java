//
// Assignment 4
// For COMP 248 Section EC – Fall 2020
//

// We're using an Exception to quit the game when it's over.
// This is probably not good to do in general, but it's best for this situation.
// If we don't use exceptions, we'd need a complicated system of returns to handle it.
class GameOver extends Exception {
}

// This class exclusively manages health, turns, and the logic of the game.
public class Game {
    Board board;

    // Tracks how many ships are left.
    int playerShips;
    int enemyShips;

    Game(int rows, int columns, int ships, int grenades) {
        board = new Board(rows, columns, ships, grenades);
        playerShips = ships;
        enemyShips = ships;
    }

    public static void main(String[] args) {
        System.out.println("--- BATTLESHIPS GAME ---");

        // 8 rows & columns, 6 ships each, and 4 grenades each
        Game game = new Game(8, 8, 6, 4);
        game.play();

        System.out.println("--- PROGRAM TERMINATED ---");
    }

    // Play the game until a winner is declared.
    void play() {
        // Play the game until the GameOver signal is received.
        while (true) {
            try {
                doPlayerTurn();
                doEnemyTurn();
            } catch (GameOver e) {
                break;
            }
        }

        // Print out the winner!
        if (playerShips < 1) {
            System.out.println("That was your last ship!\nYou lose!");
            board.reveal();
        } else if (enemyShips < 1) {
            System.out.println("That was the last ship!\nYou win!");
            board.reveal();
        }
    }

    // Do a single player turn.
    void doPlayerTurn() throws GameOver {
        // Display the board and get user input.
        System.out.println(board);
        int[] point = board.inputPoint();
        Tile tile = board.get(point);

        // Display where they hit and add a remark for fun.
        System.out.printf("You attacked   %c%d. ", board.label(point[1]), point[0] + 1);
        // Check if the tile has been revealed...
        if (!tile.revealed) {
            System.out.println(tile.type.randomFriendlyRemark());
            hit(point);

            // If it was a grenade, give the enemy an extra turn.
            if (tile.type.isGrenade()) doEnemyTurn();
        } else {
            System.out.println(tile.type.friendlyRevealedRemark);
        }
    }

    // Do a single enemy turn.
    void doEnemyTurn() throws GameOver {
        // Get a random point on the board to hit.
        int[] point = board.randomPoint();
        Tile tile = board.get(point);

        // Display where they hit and a remark for fun.
        System.out.printf("Enemy attacked %c%d. ", board.label(point[1]), point[0] + 1);
        if (!tile.revealed) {
            System.out.println(tile.type.randomEnemyRemark());
            hit(point);

            // If it was a grenade, the player will get an additional turn.
            if (tile.type.isGrenade()) doPlayerTurn();
        } else {
            System.out.println(tile.type.enemyRevealedRemark);
        }
    }

    // Reveal a tile and adjust HP as necessary.
    void hit(int[] point) throws GameOver {
        board.get(point).revealed = true;

        Type type = board.get(point).type;

        if (type == Type.E_SHIP)
            enemyShips -= 1;
        else if (type == Type.SHIP)
            playerShips -= 1;

        // This is the only place where HP is changed,
        // So we check if the game is over at this point.
        // This exception will be passed up from hit(), into the appropriate doTurn(),
        // And finally into play(), where it will be handled.
        if (enemyShips < 1 || playerShips < 1)
            throw new GameOver();
    }
}
