package com.rg.chessapplication.Chess.Board;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.rg.chessapplication.Chess.Figures.Bishop;
import com.rg.chessapplication.Chess.Figures.Castle;
import com.rg.chessapplication.Chess.Figures.ChessPiece;
import com.rg.chessapplication.Chess.Figures.King;
import com.rg.chessapplication.Chess.Figures.Knight;
import com.rg.chessapplication.Chess.Figures.Pawn;
import com.rg.chessapplication.Chess.Figures.Queen;
import com.rg.chessapplication.Databases.LocalDatabaseHandlerThread;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP PC on 23.12.2016.
 */

public class ChessBoard implements Serializable{

    private final ArrayList<Cell> cells = new ArrayList<>(64);
    private final ArrayList<ChessPiece> figures = new ArrayList<>(32);
    private boolean stepColour = true;
    private LocalDatabaseHandlerThread databaseThread = null;
    private boolean empty = false;
    private boolean queryResult = false;

    public ChessBoard(LocalDatabaseHandlerThread databaseThread){
        this.databaseThread = databaseThread;

        createBoard();
    }

    private void createBoard(){
        for(int i = 8; i > 0; i--){
            for(int j = 1; j < 9; j++){
                cells.add(new Cell(j,i));
            }
        }
    }

    public void fillBoardWithFigures(boolean choice){
        if(!empty && choice){ //Пустая таблица
            createFiguresFromDatabase();
        }
        else{
            createFigures();
        }
        queryResult = true;
    }

    private void createFigures(){

        databaseThread.clearData();
        figures.clear();

        for(Cell cell: cells){
            if(cell.getFigure() != null) cell.setFigure(null);
        }

        stepColour = true;

        for (Cell cell : cells){
            if(cell.getWidth() == 2) cell.setFigure(new Pawn(this,cell,true));
            if(cell.getWidth() == 1){
                if(cell.getHeight() == 1 || cell.getHeight() == 8) cell.setFigure(new Castle(this,cell,true));
                if(cell.getHeight() == 2 || cell.getHeight() == 7) cell.setFigure(new Knight(this,cell,true));
                if(cell.getHeight() == 3 || cell.getHeight() == 6) cell.setFigure(new Bishop(this,cell,true));
                if(cell.getHeight() == 4) cell.setFigure(new Queen(this,cell,true));
                if(cell.getHeight() == 5)cell.setFigure(new King(this,cell,true));
            }

            if(cell.getWidth() == 7) cell.setFigure(new Pawn(this,cell,false));
            if(cell.getWidth() == 8){
                if(cell.getHeight() == 1 || cell.getHeight() == 8) cell.setFigure(new Castle(this,cell,false));
                if(cell.getHeight() == 2 || cell.getHeight() == 7) cell.setFigure(new Knight(this,cell,false));
                if(cell.getHeight() == 3 || cell.getHeight() == 6) cell.setFigure(new Bishop(this,cell,false));
                if(cell.getHeight() == 4) cell.setFigure(new Queen(this,cell,false));
                if(cell.getHeight() == 5)cell.setFigure(new King(this,cell,false));
            }
        }

        for (Cell cell:cells) {
            if(cell.getFigure() != null) figures.add(cell.getFigure());
        }

        for(ChessPiece figure: figures){
            databaseThread.insertData(figure, isStepColour());
        }

    }

    private void createFiguresFromDatabase(){
        databaseThread.getData(this);
    }

    public boolean checkToKing(boolean colour){
        Cell kingCell = null;
        ChessPiece king = null;

        for(ChessPiece figure: figures){
            if(figure.getClass() == King.class && figure.isColour() == colour){
                kingCell = figure.getCurrentCell();
                king = figure;
                break;
            }
        }

        for(ChessPiece figure: figures){
            if(figure.isColour() != colour){
                Cell tmp = figure.getCurrentCell();
                if(figure.move(kingCell)){
                    figure.move(tmp);
                    kingCell.setFigure(king);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean mate(boolean colour){

        if(checkToKing(colour)){
            for(ChessPiece figure: figures){
                if(figure.isColour() == colour){
                    Cell tmpCell = figure.getCurrentCell();
                    for (Cell cell: cells){
                        ChessPiece tmp = cell.getFigure();
                        if(figure.move(cell)){
                            if(checkToKing(colour)){
                                figure.move(tmpCell);
                                cell.setFigure(tmp);
                            }
                            else{
                                figure.move(tmpCell);
                                cell.setFigure(tmp);
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        else return false;
    }

    public boolean step(Cell fromCell, Cell toCell, boolean colour){

        ChessPiece figure = null;
        ChessPiece tmp = null;

        for (Cell cell: cells) {
            if(cell.equals(toCell)){
                toCell = cell;
                tmp = toCell.getFigure();
            }
            if(cell.equals(fromCell)){
                fromCell = cell;
                figure = fromCell.getFigure();
            }
        }

        if(figure.move(toCell)){
            if(checkToKing(colour)){
                figure.move(fromCell);
                toCell.setFigure(tmp);
                return false;
            }
            if(tmp != null){
                figures.remove(tmp);
                databaseThread.deleteData(tmp);
            }
            stepColour = !stepColour;
            databaseThread.updateData(figure, fromCell, isStepColour());
            return true;
        }

        return false;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public ArrayList<ChessPiece> getFigures() {
        return figures;
    }

    public boolean isStepColour() {
        return stepColour;
    }

    public void setStepColour(boolean stepColour) {
        this.stepColour = stepColour;
    }

    public boolean isQueryResult() {
        return queryResult;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setQueryResult(boolean queryResult) {
        this.queryResult = queryResult;
    }
}
