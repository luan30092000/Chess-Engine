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
 * Mostly a union of bishop and rook
 */
public class Queen extends Piece{

    private final static int[] CANDIDATE_MOVE_DIRECTION = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }

    @Override
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
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
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
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return BoardUtils.FIRST_COLUMN[piecePosition] &&
                (currentCandidateDirection == -9 ||
                        currentCandidateDirection == -1 ||
                        currentCandidateDirection == 7);
    }

    private static boolean isEighthColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return BoardUtils.EIGHTH_COLUMN[piecePosition] &&
                (currentCandidateDirection == -7 ||
                        currentCandidateDirection == 1 ||
                        currentCandidateDirection == 9);
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }


}
