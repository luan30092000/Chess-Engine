package com.chess.GUI;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private final Color LIGHT_TILE_COLOUR = Color.decode("#FFFACD");
    private final Color DARK_TILE_COLOUR = Color.decode("#593E1A");
    public Table() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);

    }
    
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }
    
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open up PGN file");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }


    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<TilePanel>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();

        }
    }

    private class TilePanel extends JPanel {

        private final int TILE_ID;

        TilePanel(final BoardPanel boardPanel,
                  final int tileID) {
            super(new GridBagLayout());
            this.TILE_ID = tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileColor() {
            if(BoardUtils.FIRST_ROW[this.TILE_ID] ||
                    BoardUtils.THIRD_ROW[this.TILE_ID] ||
                    BoardUtils.FIFTH_ROW[this.TILE_ID] ||
                    BoardUtils.SEVENTH_ROW[this.TILE_ID]) {
                setBackground(this.TILE_ID % 2 == 0 ? LIGHT_TILE_COLOUR : DARK_TILE_COLOUR);
            } else if ( BoardUtils.SECOND_COLUMN[this.TILE_ID] ||
                    BoardUtils.FOURTH_ROW[this.TILE_ID] ||
                    BoardUtils.SIXTH_ROW[this.TILE_ID] ||
                    BoardUtils.EIGHTH_COLUMN[this.TILE_ID]) {
                setBackground(this.TILE_ID % 2 != 0 ? LIGHT_TILE_COLOUR : DARK_TILE_COLOUR);
            }
        }
    }
}
