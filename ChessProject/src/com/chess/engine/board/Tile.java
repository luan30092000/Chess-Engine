package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

// Immutable class
public abstract class Tile {

    // Only subclass access and once set, should be the same throughout the game
    protected final int tileCoordinate;

    /**
     * Create all possible empty tiles for furture use
     */
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleTiles();

    private static Map<Integer, EmptyTile> createAllPossibleTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        // Immutable create a copy of all possible tile positions (64) and saved them in cache for reuse
        // Also when you return this copy of tile, user cannot modify it
        return ImmutableMap.copyOf(emptyTileMap);
        // Or we can just simply
        // return emptyTileMap;
    }

    // The only access is here

    /**
     * Create tile with numb
     * @param tileCoordinate
     * @param piece
     * @return
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    /**
     * Why using static nested inner class?
     * If they always go together -> Compact
     * https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
     */

    private static final class EmptyTile extends  Tile {

        private EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    private static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return pieceOnTile.getPieceAlliance().isBlack() ? pieceOnTile.toString().toLowerCase() :
                    pieceOnTile.toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
