package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView {
    public JFrame gameFrame;
    public JPanel boardPanel;
    public JPanel scorePanel;
    public JPanel infoPanel;
    public JButton[][] squareButtons;
    public JButton undoButton;
    public JButton redoButton;
    public JButton newButton;
    public JButton resetButton;
    public JButton forfeitButton;

    /**
     * Default constructor for the GameView
     * @param boardWidth the width of the board
     * @param boardHeight the height of the board
     */
    public GameView(int boardWidth, int boardHeight){
        // Code used from example listed on the assignment page
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        // Set up the game frame
        gameFrame = new JFrame("Chess");
        gameFrame.setSize(800, 900);
        // Set up the board panel that contains the chessboard
        boardPanel = new JPanel();
        setupBoardPanel(boardWidth, boardHeight);
        // Set up information panel
        infoPanel = new JPanel();
        setupInfoPanel();
        // Display the game frame
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Draws out the squares of the chess board as buttons
     * @param boardWidth the width of the board
     * @param boardHeight the height of the board
     */
    private void setupBoardPanel(int boardWidth, int boardHeight) {
        // Makes the panel a grid layout
        boardPanel.setLayout(new GridLayout(boardWidth, boardHeight));
        // Represent all squares as a clickable button array
        squareButtons = new JButton[boardWidth][boardHeight];
        // Iterate through the array
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {
                // Determine if the square is dark or light
                Color squareColor;
                if ((i + j) % 2 == 1) {
                    squareColor = new Color(182,155,76);
                }
                else {
                    squareColor = new Color(130, 82, 1);
                }
                // Create and style a new button
                JButton currSquare = new JButton();
                currSquare.setOpaque(true);
                currSquare.setBackground(squareColor);
                currSquare.setBorderPainted(false);
                // Clicking the button will return the button's position
                currSquare.setActionCommand( i + ":" + j);
                // Set the square as the button
                squareButtons[i][j] = currSquare;
                // Add the button to the board panel
                boardPanel.add(squareButtons[i][j]);
            }
        }
        // Add the board panel to the game frame
        gameFrame.add(boardPanel, BorderLayout.CENTER);
    }

    public void setupInfoPanel() {
        infoPanel.setLayout(new GridLayout(1,4));
        undoButton = new JButton();
        undoButton.setText("Undo");
        undoButton.setActionCommand("Undo");
        redoButton = new JButton();
        redoButton.setText("Redo");
        redoButton.setActionCommand("Redo");
        newButton = new JButton();
        newButton.setText("New Game");
        newButton.setActionCommand("New Game");
        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setActionCommand("Reset");
        forfeitButton = new JButton();
        forfeitButton.setText("Forfeit");
        forfeitButton.setActionCommand("Forfeit");
        infoPanel.add(undoButton);
        infoPanel.add(redoButton);
        infoPanel.add(newButton);
        infoPanel.add(resetButton);
        gameFrame.add(infoPanel, BorderLayout.PAGE_END);
    }

    public void setupScorePanel() {
        scorePanel.setLayout(new BorderLayout());

    }



    /**
     * Sets the image of the piece on position on the board
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     * @param filepath the filepath of the piece's icon image
     */
    public void setPiece(int xPosition, int yPosition, String filepath) {
        squareButtons[xPosition][yPosition].setIcon(new ImageIcon(filepath));
    }

    /**
     * Help set the listeners of each square button on the board
     * @param boardWidth the width of the board
     * @param boardHeight the height of the board
     * @param listener the listener to set to the button
     */
    public void setSquareListeners(int boardWidth, int boardHeight, ActionListener listener) {
        // Iterate through the entire board
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                // Set the listener to each button
                squareButtons[i][j].addActionListener(listener);
            }
        }
    }
}