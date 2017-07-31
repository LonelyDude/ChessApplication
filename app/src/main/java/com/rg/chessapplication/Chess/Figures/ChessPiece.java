package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

/**
 * Created by HP PC on 23.12.2016.
 */

public abstract class ChessPiece {
    protected boolean colour;
    protected Cell currentCell;
    protected ChessBoard board;

    public ChessPiece(ChessBoard board, Cell currentCell, boolean colour){
        this.board = board;
        this.currentCell = currentCell;
        this.colour = colour;
    }

    public boolean move(Cell toCell){

        if(samePosition(toCell)) return false;

        if(sameColour(toCell)) return false;

        if(check(toCell)){
            toCell.setFigure(this);
            currentCell.setFigure(null);
            currentCell = toCell;
            return true;
        }
        return false;
    }

    protected abstract boolean check(Cell toCell);

    private boolean sameColour(Cell toCell){
        if(toCell.getFigure() != null){
            if(toCell.getFigure().isColour() == colour) return true;
        }
        return false;
    }

    private boolean samePosition(Cell toCell){
        if(currentCell.equals(toCell)) return true;
        return false;
    }

    public boolean isColour() {
        return colour;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
