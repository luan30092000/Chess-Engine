package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ex: Knife @35, legal moves are 18, 20, 25, 29, 41, 45, 50, 52
 * Addition variable -17, -15, -10, -6, 6, 10, 15, 17 to calculate legal move
 */
public class Knight extends Piece {

    /**
     * In a perfect condition, a knife will have at most 8 possible moves
     */
    private final static int[] CANDIDATE_MOVE_DIRECTION = { -17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Implemented knight legal move calculation
     * Breath-First Check CANDIDATE_MOVE_COORDINATE
     * Knight cannot go Depth Check anyway because they are short distance unit
     * @param board current board state
     * @return return list of legal move for this specified knight
     */
    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentAdditionCandidate : CANDIDATE_MOVE_DIRECTION) {
            final int candidateDestinationCoordinate = this.piecePosition + currentAdditionCandidate;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {/*candidateDestinationCoordinate > 63 || candidateDestinationCoordinate < 0*/   // If candidate is out of chess board bound
                if(isFirstColumnExclusion(this.piecePosition, currentAdditionCandidate) ||
                        isSecondColumnExclusion(this.piecePosition, currentAdditionCandidate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentAdditionCandidate) ||
                        isEighthColumnExclusion(this.piecePosition, currentAdditionCandidate)) {
                    continue;   // Skip below, continue to irritate through loop
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);    // Get the tile location
                if (!candidateDestinationTile.isTileOccupied()) {   // If tile is not occupied
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // Add normal move
                } else {    // If it's opponent piece
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {  // Opponent is in a legal move, add attackedMove
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //There are some exceptional case for knight when it's on edge of the board
    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidate) {
        return (BoardUtils.FIRST_COLUMN[piecePosition] &&
                ((currentCandidate == -17) || (currentCandidate == -10) || (currentCandidate == 6) || (currentCandidate == 15)));
    }

    private static boolean isSecondColumnExclusion(final int piecePosition, final int currentCandidate) {
        return BoardUtils.SECOND_COLUMN[piecePosition] && (currentCandidate == -10 || currentCandidate == 6);
    }

    private static boolean isSeventhColumnExclusion(final int piecePosition, final int currentCandidate) {
        return BoardUtils.SEVENTH_COLUMN[piecePosition] && (currentCandidate == -6 || currentCandidate == 10);
    }

    private static boolean isEighthColumnExclusion(final int piecePosition, final int currentCandidate) {
        return BoardUtils.EIGHTH_COLUMN[piecePosition] &&
                (currentCandidate == -15 || currentCandidate == -6 || currentCandidate == 10|| currentCandidate == 17);
    }
}
