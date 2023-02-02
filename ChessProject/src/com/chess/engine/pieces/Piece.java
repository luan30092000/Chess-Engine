package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    //TODO more work here!!
    protected final boolean isFirstMove = false;

    public Piece(final Alliance pieceAlliance, final int piecePosition) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    // Because this class is abstract, each piece will have its own way of calculation legal move
    public abstract Collection<Move> calculateLegalMove(final Board board);

    /**
     * To print name of each piece
     */
    enum PieceType {

        PAWN("P"),
        KNIGHT("K"),
        BISHOP("B"),
        ROOK("R"),
        KING("K"),
        QUEEN("Q");

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
