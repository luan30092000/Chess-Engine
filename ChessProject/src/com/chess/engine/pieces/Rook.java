package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Same implementing as Bishop, refer to bishop for more detail
 */
public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_DIRECTION = {-8, -1, 1, 8};

    Rook(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }


    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentAdditionCandidate : CANDIDATE_MOVE_DIRECTION) {
            int candidateDestinationCoordinate = piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(piecePosition, currentAdditionCandidate) ||  // Edge cases
                        isEighthColumnExclusion(piecePosition, currentAdditionCandidate)) {
                    break;
                }
                    candidateDestinationCoordinate += currentAdditionCandidate;
                final Tile candidateDestinationTile = board.getTile(currentAdditionCandidate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                    break;
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return BoardUtils.FIRST_COLUMN[piecePosition] && (currentCandidateDirection == -1);
    }

    private static boolean isEighthColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return BoardUtils.EIGHTH_COLUMN[piecePosition] && (currentCandidateDirection == 1);
    }
}
