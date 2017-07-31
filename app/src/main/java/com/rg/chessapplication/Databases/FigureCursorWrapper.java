package com.rg.chessapplication.Databases;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;
import android.widget.Switch;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.Chess.Figures.Bishop;
import com.rg.chessapplication.Chess.Figures.Castle;
import com.rg.chessapplication.Chess.Figures.ChessPiece;
import com.rg.chessapplication.Chess.Figures.King;
import com.rg.chessapplication.Chess.Figures.Knight;
import com.rg.chessapplication.Chess.Figures.Pawn;
import com.rg.chessapplication.Chess.Figures.Queen;

/**
 * Created by HP PC on 21.01.2017.
 */

public class FigureCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */

    private ChessBoard board = null;

    public FigureCursorWrapper(Cursor cursor, ChessBoard board) {
        super(cursor);
        this.board = board;
    }

    public ChessPiece getFigure(){
        int width = Integer.valueOf(getString(getColumnIndex(DatabaseConstants.CELL_WIDTH)));
        int height = Integer.valueOf(getString(getColumnIndex(DatabaseConstants.CELL_HEIGHT)));
        String figure = getString(getColumnIndex(DatabaseConstants.FIGURE));
        boolean figureColour = (Integer.valueOf(getString(getColumnIndex(DatabaseConstants.FIGURE_COLOUR))) == 1) ? true : false;

        Cell cell = findCell(width, height);

        ChessPiece piece = createFigure(figure, cell, figureColour);

        return piece;
    }

    public boolean getStepColour(){
        boolean stepColour = (Integer.valueOf(getString(getColumnIndex(DatabaseConstants.STEP_COLOUR))) == 1) ? true : false;
        Log.d("Database thread", "Database thread " + String.valueOf(stepColour));
        return stepColour;
    }

    private ChessPiece createFigure(String figure, Cell cell, boolean colour){
        ChessPiece piece = null;
        switch(figure){
            case "Pawn":
                piece = new Pawn(board, cell, colour);
                break;
            case "Castle":
                piece = new Castle(board, cell, colour);
                break;
            case "Knight":
                piece = new Knight(board, cell, colour);
                break;
            case "Bishop":
                piece = new Bishop(board, cell, colour);
                break;
            case "Queen":
                piece = new Queen(board, cell, colour);
                break;
            case "King":
                piece = new King(board, cell, colour);
                break;
        }
        return piece;
    }

    private Cell findCell(int width, int height){
        Cell cell = null;

        for(Cell c : board.getCells()){
            if(c.getWidth() == width && c.getHeight() == height){
                cell = c;
                break;
            }
        }

        return cell;
    }

}
