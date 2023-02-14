package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

/**
 * Applying builder pattern
 * Builder pattern: https://refactoring.guru/design-patterns/builder
 *                  https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java
 * List:            https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 */
public class Board {

    /**
     * Could have use array, but array is not immutable, List is immutable
     * A board consists of list of tile with pieces content on it
     * Active white/black pieces -> non-active white/black pieces
     * White and Black players
     */
    private final Map<Integer, Piece> boardConfig;
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;    //Active pieces on board
    private final Collection<Piece> blackPieces;    //Active pieces on board
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;

    /**
     * To initiate board and after each time a player make a move
     * new board is created
     *
     * @param builder
     */
    private Board(Builder builder) {
        this.boardConfig = Collections.unmodifiableMap(builder.boardConfig);
        this.gameBoard = createGameBoard(builder);  // Create game board with tile associated with pieces from boardConfig
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    /**
     * Print board "natively"
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    /**
     * make container of legal moves of all piece for a side
     * @param activePieces Collection of active pieces which we have method to gather
     * @return  Collection of all moves from all active pieces from a team
     */
    private Collection<Move> calculateLegalMoves(final Collection<Piece> activePieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : activePieces) {
            legalMoves.addAll(piece.calculateLegalMove(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * Tracking active piece for each team
     * @param gameBoard current board state
     * @param alliance  which team we are counting
     * @return  Collection of active piece, use collection since order doesn't matter
     */
    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public Tile getTile(final int tileCoordinate) {
        return this.gameBoard.get(tileCoordinate);
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Create actual game board is here,
     * create tile with initial associated pieces from boardConfig (hold initial location of all pieces)
     * @param builder to get boardConfig
     * @return List of tile with associated pieces
     */
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            // If boardConfig returns Pieces, create occupied Tile with associated Pieces
            // Else get an empty tile from EMPTY_TILES_CACHE (Tile class) with associated tile number
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    /**
     * Create stand board into boardConfig to set up board
     * Associate mainly with BUilder
     * @return this Board
     */
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        for (int i = 8; i < 16; i++) {
            builder.setPiece(new Pawn(Alliance.BLACK, i));
        }
        // While Layout
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        for (int i = 48; i < 56; i++) {
            builder.setPiece(new Pawn(Alliance.WHITE, i));
        }
        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public Iterable<Move> getAllLegalMove() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    /**
     * Builder associates with pattern,
     * Contain boardConfig: hold initial location of all pieces
     * CreateGameBoard() -> creatStandBoard() (boardConfig, setMoveMaker(), build(), setPiece()) -> return Board
     * Keep track of next move maker
     */
    public static class Builder  {
        public Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        /**
         * setPiece() -> boardConfig -> createGameBoard()
         * @param piece     piece that are being put into board builder to make board
         * @return Builder  with new pieces location
         */
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        /**
         * Set the current moveMaker
         * @param nextMoveMaker
         * @return
         */
        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setMoveTransition(final Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }
}
