package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,
                       final Collection<Move> blackStandardMoves,
                       final Collection<Move> whiteStandardMoves) {
        super(board, blackStandardMoves, whiteStandardMoves);
    }
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                    final Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        // If it's kings first move and not incheck
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // White king side castle
            if (!this.board.getTile(5).isTileOccupied() &&
                    !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isTileOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttacksOnTile(5, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentsLegalMoves).isEmpty()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board,
                                this.playerKing,
                                6,
                                (Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),
                                5));
                    }
                }
            }
            // White Queen side castle
            if (!this.board.getTile(1).isTileOccupied() &&
                    !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttacksOnTile(1, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(2, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(3, opponentsLegalMoves).isEmpty()) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                this.playerKing,
                                2,
                                (Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),
                                3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
