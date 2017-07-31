package com.rg.chessapplication.Chess.Figures;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;

import static java.lang.Math.abs;

/**
 * Created by HP PC on 24.12.2016.
 */

public class Pawn extends ChessPiece {

    boolean firstMove = true;
    boolean direction;

    public Pawn(ChessBoard board, Cell currentCell, boolean colour) {
        super(board, currentCell, colour);
        direction = isDirection();
    }

    @Override
    protected boolean check(Cell toCell){

        if(longMove(toCell)) return false;

        if(sameWidth(toCell)) return false;

        if(firstMove){

            if(! sameHeight(toCell)) return capture(toCell);

            if(! toCell.isEmpty()) return false;

            if(direction == true){
                if(toCell.getWidth() - currentCell.getWidth() <= 2 && toCell.getWidth() - currentCell.getWidth() > 0){
                    firstMove = false;
                    return true;
                }
            }
            else{
                if(toCell.getWidth() - currentCell.getWidth() >= -2 && toCell.getWidth() - currentCell.getWidth() < 0){
                    firstMove = false;
                    return true;
                }
            }
        }

        else{

            if(! sameHeight(toCell)) return capture(toCell);

            if(! toCell.isEmpty()) return false;

            if(direction == true){
                if(toCell.getWidth() - currentCell.getWidth() == 1) return true;
            }
            else{
                if(toCell.getWidth() - currentCell.getWidth() == -1) return true;
            }

        }

        return false;
    }

    private boolean longMove(Cell toCell){
        if (abs(currentCell.getWidth() - toCell.getWidth()) > 2) return true;
        return false;
    }

    private boolean sameWidth(Cell toCell){
        if (currentCell.getWidth() == toCell.getWidth()) return true;
        return false;
    }

    private boolean sameHeight(Cell toCell){
        if (currentCell.getHeight() == toCell.getHeight()) return true;
        return false;
    }

    private boolean capture(Cell toCell){
        if(! toCell.isEmpty()){

            if(direction == true){
                if((toCell.getWidth() - currentCell.getWidth() == 1) && (abs(toCell.getHeight() - currentCell.getHeight()) == 1)) return true;
            }
            else{
                if((toCell.getWidth() - currentCell.getWidth()) == -1 && (abs(toCell.getHeight() - currentCell.getHeight()) == 1)) return true;
            }
        }
        return false;
    }

    private boolean isDirection(){
        if(currentCell.getWidth() == 2) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Pawn";
    }

}
