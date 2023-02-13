package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Pawn is a short distance unit
 * Have many variable such as promotion, move vertical but capture diagonal, 1 way move for black and white
 * Can only capture diagonal when tile is occupied by opponent
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_DIRECTION = {7, 8, 9, 16}; //8, 16 are non-attacked moves; 7, 9 are attacked move

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.PAWN, pieceAlliance, piecePosition, true);
    }

    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN, pieceAlliance, piecePosition, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentAdditionCandidate : CANDIDATE_MOVE_DIRECTION) {
            final int candidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * currentAdditionCandidate;
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { //If out of bound (table)
                continue;
            }
            if (currentAdditionCandidate == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {    // non-attacked move, 1 box
                //TODO Promotion Pawn
                legalMoves.add(new Move.PawnMove(board, this, candidateDestinationCoordinate));
            } else if (candidateDestinationCoordinate == 16 && this.isFirstMove &&   // Non-attack move, 2 boxes
                    ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                            (BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8); // Check for the double box position
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, behindCandidateDestinationCoordinate));
                }
            } else if (currentAdditionCandidate == 7 &&  // Capture Move
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) || // Not edge case
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {    // Not edge case
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) { // Only if tile's occupied
                    final Piece pieceOnDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (pieceOnDestination.pieceAlliance != this.pieceAlliance) {   // Only if occupied by component
                        //TODO if promotion also happens
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (currentAdditionCandidate == 9 && // Capture Move
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||  // Not edge case
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) { // Not edge case
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {   // Only if tile's occupied
                    final Piece pieceOnDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (pieceOnDestination.pieceAlliance != this.pieceAlliance) {   // Only if occupied by component
                        //TODO if promotion also happens
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * Make new pieces with same alliance and destination Coordinate
     * @param move  to get alliance type
     * @return  new piece(Pawn) with same alliance and new position
     */
    @Override
    public Pawn makeMovePiece(Move move) {
        return new Pawn(move.getMovedPiece().pieceAlliance, move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
