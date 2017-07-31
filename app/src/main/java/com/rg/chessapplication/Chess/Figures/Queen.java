package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

/**
 * Created by HP PC on 24.12.2016.
 */

public class Queen extends ChessPiece {

    private Castle castle;
    private Bishop bishop;

    public Queen(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
        castle = new Castle(board, currentCell,colour);
        bishop = new Bishop(board, currentCell,colour);
    }

    @Override
    protected boolean check(Cell toCell) {

        if(sameHeight(toCell) || sameWidth(toCell)) return moveAsCastle(toCell);

        else return moveAsBishop(toCell);

    }

    private boolean moveAsCastle(Cell toCell){
        if(castle.check(toCell)){
            castle.setCurrentCell(toCell);
            bishop.setCurrentCell(toCell);
            return true;
        }
        return false;
    }

    private boolean moveAsBishop(Cell toCell) {
        if(bishop.check(toCell)){
            bishop.setCurrentCell(toCell);
            castle.setCurrentCell(toCell);
            return true;
        }
        return false;
    }

    private boolean sameWidth(Cell toCell){
        if(currentCell.getWidth() == toCell.getWidth()) return true;
        return false;
    }

    private boolean sameHeight(Cell toCell) {
        if(currentCell.getHeight() == toCell.getHeight()) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Queen";
    }
}
