package com.chess.engine.board;

/**
 * BoardUtils is just a utility class with static method which support the pieces
 * -> No need to initiate the class
 */

public class BoardUtils {

    public static final int NUM_TILES_PER_ROW = 8, NUM_TILES = 64, SIDE_MOVE = 1;

    /**
     * Bool array with specific element that are marked TRUE to check piece's special (edge) locations
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0),
            SECOND_COLUMN = initColumn(1),
            SEVENTH_COLUMN = initColumn(6),
            EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] FIRST_ROW = initRow(0),
            SECOND_ROW = initRow(8),
            THIRD_ROW = initRow(16),
            FOURTH_ROW = initRow(24),
            FIFTH_ROW = initRow(32),
            SIXTH_ROW = initRow(40),
            SEVENTH_ROW = initRow(48),
            EIGHTH_ROW = initRow(56);


    /**
     * Make bool arr of 64 element and assigned True to specific edge elements
     * @param columnNumber the col num index 0 1 6 7 (knight's edge cases)
     * @return
     */
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] table = new boolean[64];

        do {
            table[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while(columnNumber < 64);
        return table;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] table = new boolean[64];

        do {
            table[rowNumber] = true;
            rowNumber += SIDE_MOVE;
        } while (rowNumber % NUM_TILES_PER_ROW != 0);
        return table;
    }

    private BoardUtils() {
        throw new RuntimeException("You cannot initiate this class");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
