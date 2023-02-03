package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    protected Board board;
    protected Piece movedPiece;
    protected int destinationCoordinate;
    protected final boolean isFirstMove;


    public Move(final Board board, final Piece pieceMoved, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = pieceMoved;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Board getBoard() {
        return this.board;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) { // Copy all pieces except the moving piece
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        // For the opponent, just need to copy every pieces
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Move the moved piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());    // Switch to the other player
        return builder.build();
    }

    public static final class MajorMove extends Move {

        public MajorMove(Board board, Piece piece, int destinationCoordinate) {
            super(board, piece, destinationCoordinate);
        }

    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(Board board, Piece piece, int destinationCoordinate, final Piece attackedPiece) {
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

}
