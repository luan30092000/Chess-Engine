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
 * Addition variable -9, -7, 7, 9 to calculate legal move
 */
public class Bishop extends Piece{

    private final static int[] CANDIDATE_MOVE_DIRECTION = {-9, -7, 7, 9};

    Bishop(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Implemented knight legal move calculation
     * Depth-First Check CANDIDATE_MOVE_COORDINATE
     * Bishop is long range unit, use while until out of range, with exceptional
     * @param board current board state
     * @return collection(set) of legal move for this specified bishop
     */
    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentAdditionCandidate : CANDIDATE_MOVE_DIRECTION) {
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExclusion(this.piecePosition, currentAdditionCandidate) ||
                        isEighthColumnExclusion(this.piecePosition, currentAdditionCandidate)) {
                    break;
                }
                candidateDestinationCoordinate += currentAdditionCandidate;
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                    break; // As if there is a occupied tile on the specific CANDIDATE_MOVE_DIRECTION, break the loop, move to next direction
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return (BoardUtils.FIRST_COLUMN[piecePosition] && (currentCandidateDirection == -9 || currentCandidateDirection == 7));
    }

    private static boolean isEighthColumnExclusion(final int piecePosition, final int currentCandidateDirection) {
        return (BoardUtils.EIGHTH_COLUMN[piecePosition] && (currentCandidateDirection == -7 || currentCandidateDirection == 9));
    }
}
