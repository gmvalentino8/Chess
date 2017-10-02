package controller;

import model.Board;
import model.Game;
import model.Player;
import model.Square;
import model.pieces.Piece;
import view.GameView;
import view.Print;

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
        gameModel.setupGame();
        gameView.setSquareListeners(Board.boardWidth, Board.boardHeight, new SquareListener());
        gameView.undoButton.addActionListener(new GameListener());
        gameView.redoButton.addActionListener(new GameListener());
        gameView.newButton.addActionListener(new GameListener());
        gameView.resetButton.addActionListener(new GameListener());
        newPlayers(true);
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

        private void movePiece(int xPosition, int yPosition) {
            // Check if the turn is valid
            if (gameModel.takeTurn(selectedSquare.xPosition, selectedSquare.yPosition, xPosition, yPosition)) {
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

                }
                // Check if this move places the players in a stalemate
                else if (gameModel.gameCondition == Game.GameCondition.Stalemate) {
                    JOptionPane.showMessageDialog(null, "Stalemate: Is it a tie!",
                            "Stalemate", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            // If the move is invalid, show a dialog to signify the move is invalid
            else {
                JOptionPane.showMessageDialog(null, "This move is invalid",
                        "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
            }
            // Flip the boolean to indicate the player will be selecting a piece next
            resetBoardColors();
            selectingPiece = true;
        }

    }

    public void newPlayers(boolean start) {
        JTextField playerOneInput = new JTextField();
        JTextField playerTwoInput = new JTextField();
        Object[] prompt = {"Player 1:", playerOneInput, "Player 2:", playerTwoInput};
        int option = start ? JOptionPane.OK_OPTION : JOptionPane.OK_CANCEL_OPTION;
        JOptionPane.showConfirmDialog(gameView.gameFrame, prompt,"Enter a player name", option);
        gameModel = new Game(new Player(playerOneInput.getText(), Piece.Color.White),
                new Player(playerTwoInput.getText(), Piece.Color.Black));
        gameModel.setupGame();
    }

    class GameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Undo":
                    gameModel.undoMove(false);
                    break;
                case "Redo":
                    gameModel.redoMove();
                    break;
                case "Forfeit:":
                    gameModel.nextPlayer.score++;
                case "Restart":
                    Player player1 = gameModel.currPlayer.playerColor == Piece.Color.White ?
                            gameModel.currPlayer : gameModel.nextPlayer;
                    Player player2 = gameModel.currPlayer.playerColor == Piece.Color.Black ?
                        gameModel.currPlayer : gameModel.nextPlayer;
                    gameModel = new Game(player1, player2);
                    gameModel.setupGame();
                    break;
                case "New Game":
                    newPlayers(false);
                    break;
            }
            setUpPieces();
        }
    }

    public void resetBoardColors() {
        for (int i = 0; i < Board.boardWidth; i++) {
            for (int j = 0; j < Board.boardHeight; j++) {
                Color squareColor;
                if ((i + j) % 2 == 0) {
                    squareColor = new Color(130, 82, 1);
                }
                else {
                    squareColor = new Color(182,155,76);
                }
                gameView.squareButtons[i][j].setBackground(squareColor);
            }
        }
    }

    public static void main(String[] args) {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        /**
        Game game = new Game();
        game.setupGame();
        game.takeTurn(0,6,0,5);
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.takeTurn(1,1,1,2);
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.undoMove();
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.undoMove();
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.redoMove();
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.undoMove();
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
        game.takeTurn(1,7,2,5);
        Print.printBoard(game.gameBoard);
        System.out.println("Move Counter: " + game.moveCounter + " Move List Count: " + game.moveHistory.size());
         */
    }

}
