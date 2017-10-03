package controller;

import model.Board;
import model.Game;
import model.Player;
import model.Square;
import model.pieces.Piece;
import view.GameView;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A GameController is an object that is in charge of communication between the model and the view
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class GameController {
    public Game gameModel;
    public GameView gameView;
    // Variables to hold information relevant to selecting and moving a piece
    private boolean selectingPiece = true;
    private Square selectedSquare;
    private Piece selectedPiece;


    /**
     * Default constructor for a GameController
     * @param gameModel model used for the game
     * @param gameView view used for the game
     */
    public GameController(Game gameModel, GameView gameView) {
        this.gameView = gameView;
        gameModel = new Game();
        gameView.setSquareListeners(Board.boardWidth, Board.boardHeight, new SquareListener());
        gameView.newButton.addActionListener(new GameListener());
        gameView.resetButton.addActionListener(new GameListener());
        gameView.forfeitButton.addActionListener(new GameListener());
        gameView.undoButton.addActionListener(new GameListener());
        gameView.redoButton.addActionListener(new GameListener());
        newGame(true, false);
        updateScores();
        updateTurn();
        setUpPieces();
    }

    /**
     * Gets the icons for the pieces and places them on the correct positions on the board
     */
    public void setUpPieces() {
        // Iterate through the entire board
        for (int y = 0; y < Board.boardHeight; y++) {
            for (int x = 0; x < Board.boardWidth; x++) {
                // Get the current piece information
                Piece currPiece = gameModel.gameBoard.gameState[x][y].currentPiece;
                if (currPiece != null) {
                    // Get the filepath to the icon and set it to the button
                    String pieceFilepath = getPieceFilepath(currPiece);
                    gameView.setPiece(x, y, pieceFilepath);
                }
                else {
                    // Set the icon of the button to null
                    gameView.setPiece(x, y, null);
                }
            }
        }
    }

    /**
     * Using the piece information, get the filepath to its icon
     * @param piece the piece to convert to string
     * @return filepath to the piece's icon
     */
    public static String getPieceFilepath(Piece piece) {
        // Start with the assets directory
        StringBuilder filepath = new StringBuilder("./assets/");
        // Append the color and name to the string
        filepath.append(piece.color.toString());
        filepath.append(piece.type.toString());
        // Finish with the .png format
        filepath.append(".png");
        return filepath.toString();
    }

    /**
     * Starts a new game
     * @param init set to true if initializing the first game, false if not
     * @param playAgain set to true if starting a new game from the play again menu, false if not
     */
    public void newGame(boolean init, boolean playAgain) {
        // Initialize the text fields for input
        JTextField playerOneInput = new JTextField();
        JTextField playerTwoInput = new JTextField();
        // Set the prompt objects
        Object[] prompt = {"Player 1:", playerOneInput, "Player 2:", playerTwoInput};
        // Display the prompt
        do {
            int option = JOptionPane.showConfirmDialog(gameView.gameFrame, prompt, "Enter a player name", JOptionPane.OK_CANCEL_OPTION);
            // If the user selects OK, then start a new game with new players using the text field inputs
            if (option == JOptionPane.OK_OPTION) {
                gameModel = new Game(new Player(playerOneInput.getText(), Piece.Color.White),
                        new Player(playerTwoInput.getText(), Piece.Color.Black));
                gameModel.setupStandardGame();
                updateScores();
                updateTurn();
                break;
            }
            // If starting from the play again menu, return to it if cancelling
            if (playAgain && (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)) {
                playAgain();
            }
        }
        // Loop this dialog if it is the first time initializing the game
        while (init);
    }

    /**
     * Resets the game with the existing players
     */
    public void resetGame() {
        // Get the players
        Player player1 = gameModel.currPlayer.playerColor == Piece.Color.White ?
                gameModel.currPlayer : gameModel.nextPlayer;
        Player player2 = gameModel.currPlayer.playerColor == Piece.Color.Black ?
                gameModel.currPlayer : gameModel.nextPlayer;
        // Make a new game with the players
        gameModel = new Game(player1, player2);
        // Set up the game
        gameModel.setupStandardGame();
        updateTurn();
    }

    /**
     * Updates the scores in the GUI
     */
    public void updateScores() {
        // Get the players
        Player player1 = gameModel.currPlayer.playerColor == Piece.Color.White ?
            gameModel.currPlayer : gameModel.nextPlayer;
        Player player2 = gameModel.currPlayer.playerColor == Piece.Color.Black ?
                gameModel.currPlayer : gameModel.nextPlayer;
        // Set the labels to the player's name and score
        gameView.playerOneLabel.setText(" " + player1.playerName + ": " + player1.score);
        gameView.playerTwoLabel.setText(player2.playerName + ": " + player2.score + " ");
    }

    /**
     * Update the turn in the GUI
     */
    public void updateTurn() {
        // Set the label to the current player
        gameView.currPlayerLabel.setText("It is " + gameModel.currPlayer.playerName + "'s turn");
    }

    /**
     * Show a dialog to allow users to start a new game, reset the game, or exit the application
     */
    public void playAgain() {
        // Set the options for the dialog
        String[] options = new String[] {"New Game", "Restart Game", "Exit"};
        // Create the dialog
        int option = JOptionPane.showOptionDialog(null, "Would you like to play again?", "Play again",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        // Check for which option is selected
        switch (option) {
            case 0:
                newGame(false, true);
                break;
            case 1:
                resetGame();
                break;
            case 2:
                gameView.gameFrame.dispose();
                break;
        }

    }

    /**
     * Listener to handle the buttons in the option panel
     */
    class GameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Check which button is pressed based on the action command
            switch (e.getActionCommand()) {
                case "Undo":
                    // Check if the undo is valid
                    if (!gameModel.undoMove(false)) {
                        // Show an error message if it is not valid
                        JOptionPane.showMessageDialog(null, "There are no moves to undo!",
                                "Invalid Undo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        // Update the turn and board if valid
                        updateTurn();
                        gameView.resetBoardColors();
                    }
                    break;
                case "Redo":
                    // Check if the redo is valid
                    if (!gameModel.redoMove()) {
                        // Show an error message if it is not valid
                        JOptionPane.showMessageDialog(null, "There are no moves to redo!",
                                "Invalid Redo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        // Update the turn and board if valid
                        updateTurn();
                        gameView.resetBoardColors();
                    }
                    break;
                case "Forfeit":
                    // Show a confirmation to the player who is forfeiting
                    int option = JOptionPane.showConfirmDialog(gameView.gameFrame,
                            gameModel.currPlayer.playerName + ": are you sure you want to forfeit this game?",
                            "Forfeit", JOptionPane.OK_CANCEL_OPTION);
                    // Update the score if forfeiting and show play again dialog
                    if (option == JOptionPane.OK_OPTION) {
                        gameModel.nextPlayer.score++;
                        updateScores();
                        playAgain();
                    }
                    break;
                case "Reset":
                    // Show a confirmation for resetting the game
                    option = JOptionPane.showConfirmDialog(gameView.gameFrame,
                            "Are you sure you want to reset the game?",
                            "Reset", JOptionPane.OK_CANCEL_OPTION);
                    // Resets the game
                    if (option == JOptionPane.OK_OPTION) {
                        resetGame();
                    }
                    break;
                case "New Game":
                    // Show a confirmation for starting a new game
                    option = JOptionPane.showConfirmDialog(gameView.gameFrame,
                            "Are you sure you want to start a new game?",
                            "Reset", JOptionPane.OK_CANCEL_OPTION);
                    // Start a new game
                    if (option == JOptionPane.OK_OPTION) {
                        newGame(false, false);
                    }
                    break;
            }
            setUpPieces();
        }
    }


    /**
     * Listener for each SquareButton that includes selecting then moving a piece
     */
    class SquareListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the x and y positions from the button's action command
            int xPosition = Integer.parseInt(e.getActionCommand().split(":")[0]);
            int yPosition = Integer.parseInt(e.getActionCommand().split(":")[1]);
            // Check if the player is selecting a piece
            if (selectingPiece) {
                selectPiece(gameModel.gameBoard.gameState[xPosition][yPosition]);
            }
            // If the player is moving a selected piece
            else {
                movePiece(xPosition, yPosition);
            }
        }

        /**
         * Selects and piece and gives visual feedback and possible moves
         * @param square the square the user selects
         */
        private void selectPiece(Square square) {
            // Check if the player selects an empty square or a square with an opponent's piece
            if (square.checkEmptySquare() || !square.checkPieceColor(gameModel.currPlayer)) {
                // Show a dialog to signify the selection is invalid
                JOptionPane.showMessageDialog(null, "Please select a valid square",
                        "Invalid Selection", JOptionPane.INFORMATION_MESSAGE);
            }
            // Save the selected square and piece information to use when moving the piece
            else {
                selectedSquare = square;
                gameView.squareButtons[square.xPosition][square.yPosition].setBackground(Color.RED);
                selectedPiece = selectedSquare.currentPiece;
                List<Square> possibleMoves = gameModel.getPossibleMoves(selectedPiece);
                for (Square target : possibleMoves) {
                    gameView.squareButtons[target.xPosition][target.yPosition].setBackground(Color.GREEN);
                }

                // Flip the boolean to indicate the piece is selected
                selectingPiece = false;
            }
        }

        /**
         *
         * @param xPosition
         * @param yPosition
         */
        private void movePiece(int xPosition, int yPosition) {
            // Check if the turn is valid
            if (gameModel.takeTurn(selectedSquare.xPosition, selectedSquare.yPosition, xPosition, yPosition)) {
                // Update the player's turn
                updateTurn();
                // Move the piece icon from origin to target
                gameView.setPiece(xPosition, yPosition, getPieceFilepath(selectedPiece));
                gameView.setPiece(selectedSquare.xPosition, selectedSquare.yPosition, null);
                // Check if this move places the opponent in check
                if (gameModel.gameCondition == Game.GameCondition.Check) {
                    JOptionPane.showMessageDialog(null, "Check: " + gameModel.currPlayer.playerName + " is in check!",
                            "Check", JOptionPane.INFORMATION_MESSAGE);
                }
                // Check if this move places the opponent in checkmate
                else if (gameModel.gameCondition == Game.GameCondition.Checkmate) {
                    JOptionPane.showMessageDialog(null, "Checkmate: " + gameModel.nextPlayer.playerName + " Wins!",
                            "Checkmate", JOptionPane.INFORMATION_MESSAGE);
                    updateScores();
                    playAgain();
                }
                // Check if this move places the players in a stalemate
                else if (gameModel.gameCondition == Game.GameCondition.Stalemate) {
                    JOptionPane.showMessageDialog(null, "Stalemate: Is it a tie!",
                            "Stalemate", JOptionPane.INFORMATION_MESSAGE);
                    playAgain();
                }
            }
            // If the move is invalid, show a dialog to signify the move is invalid
            else {
                JOptionPane.showMessageDialog(null, "This move is invalid",
                        "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
            }
            // Flip the boolean to indicate the player will be selecting a piece next
            gameView.resetBoardColors();
            selectingPiece = true;
        }

    }

    public static void main(String[] args) {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
    }

}
