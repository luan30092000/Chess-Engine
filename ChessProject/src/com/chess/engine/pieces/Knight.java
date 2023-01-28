package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.BoardUtils.*;

/**
 * Ex: Knife @35, legal moves are 18, 20, 25, 29, 41, 45, 50, 52
 * Matching variable -17, -15, -10, -6, 6, 10, 15, 17
 */
public class Knight extends Piece {

    /**
     * In a perfect condition, a knife will have at most 8 possible moves
     */
    private final static int[] CANDIDATE_MOVE_COORDINATE = { -17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Implemented knight legal move calculation
     * @param board current board state
     * @return return list of legal move that a specific knight can move
     */
    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (isValidTileCoordinate(candidateDestinationCoordinate)/*candidateDestinationCoordinate > 63 || candidateDestinationCoordinate < 0*/) {    // If candidate is out of chess board bound todo: need new algo
                if(isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                    continue;   // Skip below, continue to irritate through loop
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);    // Get the tile location
                if (!candidateDestinationTile.isTileOccupied()) {   // If tile is not occupied
                    legalMoves.add(new Move());
                } else {    // If it's opponent piece
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {  // Opponent is in a legal move
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //There are some exceptional case for knight when it's on edge of the board
    private static boolean isFirstColumnExclusion(final int piecePosition, final int currentCandidate) {
        return (FIRST_COLUMN[piecePosition] &&
                ((currentCandidate == -17) || (currentCandidate == -10) || (currentCandidate == 6) || (currentCandidate == 15)));
    }

    private static boolean isSecondColumnExclusion(final int piecePosition, final int currentCandidate) {
        return SECOND_COLUMN[piecePosition] && (currentCandidate == -10 || currentCandidate == 6);
    }

    private static boolean isSeventhColumnExclusion(final int piecePosition, final int currentCandidate) {
        return SEVENTH_COLUMN[piecePosition] && (currentCandidate == -6 || currentCandidate == 10);
    }

    private static boolean isEighthColumnExclusion(final int piecePosition, final int currentCandidate) {
        return EIGHTH_COLUMN[piecePosition] &&
                (currentCandidate == -15 || currentCandidate == -6 || currentCandidate == 10|| currentCandidate == 17);
    }
}
