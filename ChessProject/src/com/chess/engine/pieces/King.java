package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * King is less complex than Pawn,
 * With limit variant such as they cannot move to attacked area of component
 * King essential will be the same as Queen, but 1 box only and cannot move into
 * attacked box
 */
public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_DIRECTION = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.KING, pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentAdditionCandidate : CANDIDATE_MOVE_DIRECTION) {
            final int candidateDestinationCoordinate = this.piecePosition + currentAdditionCandidate;
            if (isFirstColumnExclusion(piecePosition, currentAdditionCandidate) ||
                    isEightColumnExclusion(piecePosition, currentAdditionCandidate)) {
                continue;
            }
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));    // Non-Attacked move
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    if (pieceAtDestination.pieceAlliance != this.pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getMovedPiece().pieceAlliance, move.getDestinationCoordinate());
    }

    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidate) {
        return BoardUtils.FIRST_COLUMN[piecePosition] &&
                (currentCandidate == -9 || currentCandidate == -1 || currentCandidate == 7);
    }

    private static boolean isEightColumnExclusion(final int piecePosition, final int currentCandidate) {
        return BoardUtils.EIGHTH_COLUMN[piecePosition] &&
                (currentCandidate == -7 || currentCandidate == 1 || currentCandidate == 9);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
