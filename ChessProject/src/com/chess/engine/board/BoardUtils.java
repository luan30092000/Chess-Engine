package com.chess.engine.board;

/**
 * BoardUtils is just a utility class with static method which support the pieces
 * -> No need to initiate the class
 */
public class BoardUtils {
    private BoardUtils() {
        throw new RuntimeException("You cannot initiate this class");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
