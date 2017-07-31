package com.rg.chessapplication.Chess;

import com.rg.chessapplication.Chess.Board.ChessBoard;

/**
 * Created by HP PC on 24.12.2016.
 */
public class Game {
    private static Game game;
    private static ChessBoard board;

    public static Game getInstance() {
        if(game == null) game = new Game();
        if(board == null) board = new ChessBoard(null);
        return game;
    }

    private Game() {

    }

    public ChessBoard getBoard(){
        return board;
    }
}
