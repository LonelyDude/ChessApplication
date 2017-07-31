package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

import static java.lang.Math.abs;

/**
 * Created by HP PC on 24.12.2016.
 */

public class Bishop extends ChessPiece {


    public Bishop(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
    }

    @Override
    protected boolean check(Cell toCell) {

        if(sameHeight(toCell) || sameWidth(toCell)) return false;

        if(abs(currentCell.getHeight() - toCell.getHeight()) != abs(currentCell.getWidth() - toCell.getWidth())) return false;

        if(! freeWay(toCell)) return false;

        return true;
    }

    private boolean freeWay(Cell toCell){
        for (Cell cell: board.getCells()) {
            if(! sameHeight(cell) && ! sameWidth(cell)){
                if(abs(cell.getHeight() - currentCell.getHeight()) == abs(cell.getWidth() - currentCell.getWidth())){
                    if(extremumHeight(toCell)){
                        if(extremumWidth(toCell)){
                            if(cell.getHeight() > currentCell.getHeight() && cell.getHeight() < toCell.getHeight() && cell.getWidth() > currentCell.getWidth() && cell.getWidth() < toCell.getWidth()){
                                if(!cell.isEmpty()) return false;
                            }
                        }
                        else {
                            if(cell.getHeight() > currentCell.getHeight() && cell.getHeight() < toCell.getHeight() && cell.getWidth() < currentCell.getWidth() && cell.getWidth() > toCell.getWidth()){
                                if(!cell.isEmpty()) return false;
                            }
                        }
                    }
                    else{
                        if(extremumWidth(toCell)){
                            if(cell.getHeight() < currentCell.getHeight() && cell.getHeight() > toCell.getHeight() && cell.getWidth() > currentCell.getWidth() && cell.getWidth() < toCell.getWidth()){
                                if(!cell.isEmpty()) return false;
                            }
                        }
                        else {
                            if(cell.getHeight() < currentCell.getHeight() && cell.getHeight() > toCell.getHeight() && cell.getWidth() < currentCell.getWidth() && cell.getWidth() > toCell.getWidth()){
                                if(!cell.isEmpty()) return false;
                            }
                        }
                    }

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
        return "Bishop";
    }
}
