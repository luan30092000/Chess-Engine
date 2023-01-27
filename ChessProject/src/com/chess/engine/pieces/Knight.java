package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Move> calculateLegalMove(Board board) {

        int candidateDestinationCoordinate;

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATE) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (candidateDestinationCoordinate > 63 || candidateDestinationCoordinate < 0) {    // If candidate is out of chess board bound todo: need new algo
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
}
