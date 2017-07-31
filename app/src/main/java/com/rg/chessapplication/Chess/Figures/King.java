package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

import static java.lang.Math.abs;

/**
 * Created by HP PC on 24.12.2016.
 */

public class King extends ChessPiece {


    public King(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
    }

    @Override
    protected boolean check(Cell toCell) {

        if(longMove(toCell)) return false;

        return true;

    }

    private boolean longMove(Cell toCell){
        if (abs(currentCell.getWidth() - toCell.getWidth()) > 1 || abs(currentCell.getHeight() - toCell.getHeight()) > 1) return true;
        return false;
    }

    @Override
    public String toString() {
        return "King";
    }
}
