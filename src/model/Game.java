package model;

import model.pieces.Pawn;
import model.pieces.Piece;
import view.Print;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A Game is an object that is in charge of the chess game logic
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Game {
    public enum GameCondition {
        Normal, Invalid, Check, Checkmate, Stalemate
    }
    public Board gameBoard;
    public Player currPlayer;
    public Player nextPlayer;
    public List<Move> moveHistory;
    public int moveCounter;
    public int matchCounter;
    public GameCondition gameCondition;

    /**
     *  Default constructor for a Game
     */
    public Game() {
        this.gameBoard = new Board();
        this.currPlayer = new Player("Player 1", Piece.Color.White);
        this.nextPlayer = new Player("Player 2", Piece.Color.Black);
        this.moveHistory = new ArrayList<>();
        this.moveCounter = 0;
        this.matchCounter = 0;
        this.gameCondition = GameCondition.Normal;
    }

    public Game(Player player1, Player player2) {
        this.gameBoard = new Board();
        this.currPlayer = player1;
        this.nextPlayer = player2;
        this.moveHistory = new ArrayList<>();
        this.moveCounter = 0;
        this.matchCounter = 0;
        this.gameCondition = GameCondition.Normal;
    }

    /**
     * Checks if a move is valid (without checking moving into check)
     * @param player the player making the move
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @return true if the move is valid, false if the move is invalid
     */
    public boolean checkValidMove(Player player, int originX, int originY, int targetX, int targetY) {
        // Initialize variables to use in checks
        Square origin = gameBoard.gameState[originX][originY];
        Square target = gameBoard.gameState[targetX][targetY];
        Piece selectedPiece = origin.currentPiece;

        // Run all checks and return
        return !origin.checkEmptySquare()
                && origin.checkPieceColor(player)
                && target.checkNoMovement(selectedPiece.xPosition, selectedPiece.yPosition)
                && (target.checkEmptySquare() || !target.checkPieceColor(player))
                && selectedPiece.checkMove(player, gameBoard, target);
    }

    /**
     * Moves a piece from the origin square to target square
     * @param selectedPiece the piece selected for movement
     * @param origin the starting position of the move
     * @param target the ending position of the move
     */
    public void movePiece(Piece selectedPiece, Square origin, Square target, boolean simulation) {
        Move currMove = new Move();
        // Update previous move information
        currMove.selectedPiece = selectedPiece;
        currMove.originSquare = origin;
        currMove.targetSquare = target;
        currMove.originX = origin.xPosition;
        currMove.originY = origin.yPosition;
        currMove.currPlayer = currPlayer;
        currMove.nextPlayer = nextPlayer;
        if (selectedPiece.type == Piece.PieceType.Pawn) {
            currMove.pawnFirstMove = ((Pawn) selectedPiece).firstMove;
        }
        // Update opponent's piece if eaten
        if (target.currentPiece != null) {
            currMove.takenPiece = target.currentPiece;
            target.currentPiece.live = false;
        }
        // Clear undid moves when making a new move
        if (!simulation && moveCounter != moveHistory.size()) {
            for (int i = moveHistory.size() - 1; i >= moveCounter; i--) {
                //Move.printMove(moveHistory.get(i));
                moveHistory.remove(i);
            }
        }
        moveHistory.add(currMove);
        moveCounter++;

        // Update piece, game state, and move history
        gameBoard.gameState[origin.xPosition][origin.yPosition].currentPiece = null;
        gameBoard.gameState[target.xPosition][target.yPosition].currentPiece = selectedPiece;
        selectedPiece.xPosition = target.xPosition;
        selectedPiece.yPosition = target.yPosition;
    }


    /**
     * Reverts the previous move
     */
    public void undoMove(boolean simulation) {
        if (moveCounter == 0) {
            return;
        }
        //System.out.println("Undo move:  Move Counter=" + moveCounter);
        //Move.printMoveList(moveHistory);

        //Print.printBoard(gameBoard);

        Move prevMove;
        if (simulation) {
            prevMove = moveHistory.get(moveHistory.size() - 1);
        }
        else {
            prevMove = moveHistory.get(moveCounter - 1);
        }

        if (prevMove.selectedPiece.type == Piece.PieceType.Pawn) {
            ((Pawn) prevMove.selectedPiece).firstMove = prevMove.pawnFirstMove;
        }
        // Update piece and game state
        gameBoard.gameState[prevMove.originSquare.xPosition][prevMove.originSquare.yPosition].currentPiece = prevMove.selectedPiece;
        gameBoard.gameState[prevMove.targetSquare.xPosition][prevMove.targetSquare.yPosition].currentPiece = prevMove.takenPiece;
        prevMove.selectedPiece.xPosition = prevMove.originSquare.xPosition;
        prevMove.selectedPiece.yPosition = prevMove.originSquare.yPosition;
        if (prevMove.targetSquare.currentPiece != null) {
            prevMove.targetSquare.currentPiece.live = true;
        }
        //Move.printMoveList(moveHistory);
        //Print.printBoard(gameBoard);

        currPlayer = prevMove.currPlayer;
        nextPlayer = prevMove.nextPlayer;
        moveCounter--;
    }

    public boolean redoMove() {
        if (moveCounter == moveHistory.size()) {
            return false;
        }
        //Print.printBoard(gameBoard);

        Move nextMove = moveHistory.get(moveCounter);
        if (nextMove.selectedPiece.type == Piece.PieceType.Pawn) {
            ((Pawn) nextMove.selectedPiece).firstMove = nextMove.pawnFirstMove;
        }

        if (nextMove.targetSquare.currentPiece != null) {
            nextMove.targetSquare.currentPiece.live = false;
        }
        gameBoard.gameState[nextMove.targetSquare.xPosition][nextMove.targetSquare.yPosition].currentPiece = nextMove.selectedPiece;
        gameBoard.gameState[nextMove.originSquare.xPosition][nextMove.originSquare.yPosition].currentPiece = null;
        nextMove.selectedPiece.xPosition = nextMove.targetSquare.xPosition;
        nextMove.selectedPiece.yPosition = nextMove.targetSquare.yPosition;
        currPlayer = nextMove.nextPlayer;
        nextPlayer = nextMove.currPlayer;
        //Print.printBoard(gameBoard);

        moveCounter++;
        return true;
    }

    public void clearMove() {
        undoMove(true);
        //System.out.println("Clearing move:  Move Counter=" + moveCounter);
        //Move.printMoveList(moveHistory);
        if (moveHistory.size() > 0) {
            moveHistory.remove(moveHistory.size() - 1);
        }
        //Move.printMoveList(moveHistory);
    }


    /**
     * Wraps the movement checks and commands to move a piece
     * @param player the player taking a turn
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @return true if the move is valid, false if the move is invalid
     */
    public boolean makeMove(Player player, int originX, int originY, int targetX, int targetY, boolean simulation) {
        // Check for out of bounds positions
        if (Square.checkOutOfBounds(originX, originY) || Square.checkOutOfBounds(targetX, targetY)) {
            return false;
        }
        // Initialize variables to use in checks
        Square origin = gameBoard.gameState[originX][originY];
        Square target = gameBoard.gameState[targetX][targetY];
        Piece selectedPiece = origin.currentPiece;
        // Check if move is valid
        if (checkValidMove(player, originX, originY, targetX, targetY)) {
            movePiece(selectedPiece, origin, target, simulation);
            // Disallow move if moving into check
            if (checkCheck(player)) {
                clearMove();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if a player is currently in check or not
     * @param player the player to see if in check or not
     * @return true if in check, false if not in check
     *
     */
    public boolean checkCheck(Player player) {
        // Find the king piece from the player's piece set
        Piece king = null;
        for (Piece piece : player.pieceSet) {
            if (piece.type == Piece.PieceType.King) {
                king = piece;
                break;
            }
        }
        // Get the opponent player
        Player opponent = player == currPlayer ? nextPlayer : currPlayer;
        // Check if any of the opponent's live pieces have a valid move to the king's square
        for (Piece piece : opponent.pieceSet) {
            if (piece.live) {
                if (checkValidMove(opponent, piece.xPosition, piece.yPosition, king.xPosition, king.yPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a player is currently in checkmate or not
     * @param player the player to check for if in checkmate or not
     * @return true if in checkmate, false if not in checkmate
     */
    public boolean checkStaleCheckmate(Player player) {
        // Get each piece for the player
        for (Piece piece : player.pieceSet) {
            if (piece.live) {
                // Check all possible moves for that piece
                for (int x = 0; x < Board.boardWidth; x++) {
                    for (int y = 0; y < Board.boardHeight; y++) {
                        // If the move is valid and removes check there is no checkmate
                        if(makeMove(player, piece.xPosition, piece.yPosition, x, y, true)) {
                            clearMove();
                            return false;
                        }
                    }
                }
            }
        }
        // If there are no possible moves it is checkmate
        return true;
    }

    public List<Square> getPossibleMoves(Piece piece) {
        List<Square> possibleMoves = new ArrayList<>();
        for (int x = 0; x < Board.boardWidth; x++) {
            for (int y = 0; y < Board.boardHeight; y++) {
                if (makeMove(currPlayer, piece.xPosition, piece.yPosition, x, y, true)) {
                    clearMove();
                    possibleMoves.add(gameBoard.gameState[x][y]);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Wraps a move and game condition checks to take a turn
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @return true if the turn is valid, false if the turn is invalid
     */
    public boolean takeTurn(int originX, int originY, int targetX, int targetY) {
        // Check if the move is valid
        if (makeMove(currPlayer, originX, originY, targetX, targetY, false)) {
            // Check for a pawn's first move to flip the first move boolean
            Piece currPiece = gameBoard.gameState[targetX][targetY].currentPiece;
            if (currPiece.type == Piece.PieceType.Pawn) {
                Pawn pawn = (Pawn) currPiece;
                pawn.firstMove = false;
            }
            // Check if the move places the opponent in check
            if (checkCheck(nextPlayer)) {
                // Check if the move places the opponent in checkmate
                if (checkStaleCheckmate(nextPlayer)) {
                    gameCondition = GameCondition.Checkmate;
                    currPlayer.score++;
                }
                else {
                    gameCondition = GameCondition.Check;
                }
            }
            // Check if the move places the players in a stalemate
            else if (checkStaleCheckmate(nextPlayer)) {
                gameCondition = GameCondition.Stalemate;
            }
            // Set the game condition as normal
            else {
                gameCondition = GameCondition.Normal;
            }
            // After making a valid move, switch the turn to the next player
            swapTurn();
            return true;
        }
        // Set the game condition to invalid
        gameCondition = GameCondition.Invalid;
        return false;
    }

    public void forfeitGame() {
        nextPlayer.score++;

    }

    /**
     * Helper function to swap the current player and next player for the game loop
     */
    public void swapTurn() {
        Player temp = currPlayer;
        currPlayer = nextPlayer;
        nextPlayer = temp;
    }

    /**
     * Sets up the pieces for a standard game of chess
     */
    public void setupGame() {
        //Place white and black pawns onto the board and player piece list
        for (int i = 0; i < Board.boardWidth; i++) {
            gameBoard.addPiece(Piece.PieceType.Pawn, nextPlayer,i,1);
            gameBoard.addPiece(Piece.PieceType.Pawn, currPlayer,i,6);
        }
        //Place white and black rooks onto the gameBoard and player piece list
        for (int i = 0; i < Board.boardWidth; i += 7) {
            gameBoard.addPiece(Piece.PieceType.Rook, nextPlayer,i,0);
            gameBoard.addPiece(Piece.PieceType.Rook, currPlayer,i,7);
        }
        //Place white and black knights onto the gameBoard and player piece list
        for (int i = 1; i < Board.boardWidth; i += 5) {
            gameBoard.addPiece(Piece.PieceType.Knight, nextPlayer,i,0);
            gameBoard.addPiece(Piece.PieceType.Knight, currPlayer,i,7);
        }
        //Place white and black bishops onto the gameBoard and player piece list
        for (int i = 2; i < Board.boardWidth; i += 3) {
            gameBoard.addPiece(Piece.PieceType.Bishop, nextPlayer,i,0);
            gameBoard.addPiece(Piece.PieceType.Bishop, currPlayer,i,7);
        }
        //Place black and white queens on the gameBoard and player piece list
        gameBoard.addPiece(Piece.PieceType.Queen, nextPlayer,4,0);
        gameBoard.addPiece(Piece.PieceType.Queen, currPlayer,4,7);

        //Place black and white kings on the gameBoard and player piece list
        gameBoard.addPiece(Piece.PieceType.King, nextPlayer,3,0);
        gameBoard.addPiece(Piece.PieceType.King, currPlayer,3,7);
    }

}

