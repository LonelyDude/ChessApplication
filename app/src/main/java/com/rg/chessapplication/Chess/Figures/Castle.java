package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

import org.w3c.dom.ProcessingInstruction;

import static java.lang.Math.abs;

/**
 * Created by HP PC on 24.12.2016.
 */

public class Castle extends ChessPiece {


    public Castle(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
    }

    @Override
    protected boolean check(Cell toCell) {

        if(! sameWidth(toCell) && ! sameHeight(toCell)) return false;

        if(sameHeight(toCell)){
            if(freeWayOnSameHeight(toCell)) return true;
        }

        if(sameWidth(toCell)){
            if(freeWayOnSameWidth(toCell)) return true;
        }

        return false;
    }

    private boolean freeWayOnSameHeight(Cell toCell){
        if(extremumWidth(toCell)){
            for (Cell cell : board.getCells()) {
                if(cell.getWidth() > currentCell.getWidth() && cell.getWidth() < toCell.getWidth() && sameHeight(cell)){
                    if(! cell.isEmpty()) return false;
                }
            }
        }
        else{
            for (Cell cell : board.getCells()) {
                if(cell.getWidth() < currentCell.getWidth() && cell.getWidth() > toCell.getWidth() && sameHeight(cell)){
                    if(! cell.isEmpty()) return false;
                }
            }
        }
        return true;
    }

    private boolean freeWayOnSameWidth(Cell toCell){
        if(extremumHeight(toCell)){
            for (Cell cell : board.getCells()) {
                if(cell.getHeight() > currentCell.getHeight() && cell.getHeight() < toCell.getHeight() && sameWidth(cell)){
                    if(! cell.isEmpty()) return false;
                }
            }
        }
        else{
            for (Cell cell : board.getCells()) {
                if(cell.getHeight() < currentCell.getHeight() && cell.getHeight() > toCell.getHeight() && sameWidth(cell)){
                    if(! cell.isEmpty()) return false;
                }
            }
        }
        return true;
    }

    private boolean sameWidth(Cell toCell){
        if(currentCell.getWidth() == toCell.getWidth()) return true;
        return false;
    }

    private boolean sameHeight(Cell toCell) {
        if(currentCell.getHeight() == toCell.getHeight()) return true;
        return false;
    }

    private boolean extremumWidth(Cell toCell){
        if(toCell.getWidth() > currentCell.getWidth()) return true;
        return false;
    }

    private boolean extremumHeight(Cell toCell){
        if(toCell.getHeight() > currentCell.getHeight()) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Castle";
    }
}

