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

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMove) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMove);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves, final Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        // If it's kings first move and not incheck
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // White king side castle
            if (!this.board.getTile(61).isTileOccupied() &&
                    !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttacksOnTile(61, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentsLegalMoves).isEmpty()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board,
                                this.playerKing,
                                62,
                                (Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),
                                61));
                    }
                }
            }
            // White Queen side castle
            if (!this.board.getTile(59).isTileOccupied() &&
                    !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isTileOccupied() &&
                        rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    if (Player.calculateAttacksOnTile(57, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(58, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentsLegalMoves).isEmpty()) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                this.playerKing,
                                58,
                                (Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),
                                59));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
