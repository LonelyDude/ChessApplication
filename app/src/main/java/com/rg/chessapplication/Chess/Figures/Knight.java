package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

import static java.lang.Math.abs;

/**
 * Created by HP PC on 24.12.2016.
 */

public class Knight extends ChessPiece {


    public Knight(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
    }

    @Override
    protected boolean check(Cell toCell) {
        if(!checkHeight(toCell) && !checkWidth(toCell)) return false;

        if(checkWidth(toCell)){
            if(abs(currentCell.getHeight() - toCell.getHeight()) != 2) return false;
        }

        if(checkHeight(toCell)){
            if(abs(currentCell.getWidth() - toCell.getWidth()) != 2) return false;
        }

        return true;
    }

    private boolean checkWidth(Cell toCell){
        if(abs(currentCell.getWidth() - toCell.getWidth()) == 1) return true;
        return false;
    }

    private boolean checkHeight(Cell toCell){
        if(abs(currentCell.getHeight() - toCell.getHeight()) == 1) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
