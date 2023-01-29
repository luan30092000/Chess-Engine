package com.chess.engine.board;

/**
 * BoardUtils is just a utility class with static method which support the pieces
 * -> No need to initiate the class
 */

public class BoardUtils {

    public static final int NUM_TILES_PER_ROW = 8, NUM_TILES = 64;

    /**
     * Bool array with specific element that are marked TRUE to check piece's special (edge) locations
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0),
    SECOND_COLUMN = initColumn(1),
    SEVENTH_COLUMN = initColumn(6),
    EIGHTH_COLUMN = initColumn(7);


    /**
     * Make bool arr of 64 element and assigned True to specific edge elements
     * @param columnNumber the col num index 0 1 6 7 (knight's edge cases)
     * @return
     */
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while(columnNumber < 64);
        return column;
    }

    private BoardUtils() {
        throw new RuntimeException("You cannot initiate this class");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
