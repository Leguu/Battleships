//
// Assignment 4
// Written by: Asil Erturan, 40164714
// For COMP 248 Section EC – Fall 2020
//

// A single tile on the board.
// Note that the 'owner' of the tile is a member of the Type enum.
public class Tile {
    Type type = Type.EMPTY;
    boolean revealed = false;

    // If the tile is not revealed, then the representation has to be empty.
    // And if the tile is revealed and empty, it has to be "missed".
    @Override
    public String toString() {
        if (revealed) {
            return (type == Type.EMPTY) ? Type.MISS.representation : type.representation;
        } else {
            return Type.EMPTY.representation;
        }
    }
}
