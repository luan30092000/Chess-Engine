package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * A pending board to check the status of the board after the move,
 * if any illegal occur, continue to use current board and discard this board,
 * else use this pending board officially
 */
public class MoveTransition {

    private final Board fromBoard;
    private final Board toBoard;
    private final Move move;
    private final MoveStatus moveStatus;    // Tell you whether you can do the move or not as you are in check,...

    public MoveTransition(final Board fromBoard, final Board toBoard, final Move move, final MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Board getToBoard() {
        return this.toBoard;
    }

    public Move getMove() {
        return this.move;
    }

    public MoveStatus getMoveStatus() {
        return moveStatus;
    }
}
