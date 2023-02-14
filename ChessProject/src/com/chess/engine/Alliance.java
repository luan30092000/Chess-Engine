package com.chess.engine;

import com.chess.engine.board.Board;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.board.BoardUtils;

public enum Alliance {
    WHITE {

        /**
         * Use to determine move up or down of the board array depends on alliance
         * @return direction -1 for white as white is at the bottom of the table, thus go up
         */
        @Override
        public int getDirection() {
            return -1;
        }

        public boolean isWhite() {
            return true;
        }

        public boolean isBlack() {
            return false;
        }

        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public boolean isPromotionTile(int tileCoordinate) {
            if (BoardUtils.EIGHTH_RANK[tileCoordinate]) {
                return true;
            }
            return false;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        public boolean isWhite() {
            return false;
        }

        public boolean isBlack() {
            return true;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public boolean isPromotionTile(int tileCoordinate) {
            if(BoardUtils.FIRST_RANK[tileCoordinate]) {
                return true;
            } else {
                return false;
            }
        }

    };
    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
    public abstract boolean isPromotionTile(int coordinate);
}
