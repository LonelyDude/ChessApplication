package com.rg.chessapplication.Databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.Chess.Figures.ChessPiece;

/**
 * Created by HP PC on 21.01.2017.
 */

public class DatabaseWorker {

    private SQLiteDatabase database = null;

    public DatabaseWorker(SQLiteDatabase db){
        database = db;
    }

    public boolean isEmpty(){
        if(database.query(DatabaseConstants.TABLE_NAME, null, null, null, null, null, null).getCount() == 0) return true;
        return false;
    }

    public void insertIntoDatabase(ChessPiece figure, boolean stepColour){
        ContentValues values = getValues(figure, stepColour);
        database.insert(DatabaseConstants.TABLE_NAME, null, values);
    }

    private ContentValues getValues(ChessPiece figure, boolean stepColour){
        ContentValues values = new ContentValues();

        values.put(DatabaseConstants.FIGURE, figure.toString());
        values.put(DatabaseConstants.CELL_HEIGHT, figure.getCurrentCell().getHeight());
        values.put(DatabaseConstants.CELL_WIDTH, figure.getCurrentCell().getWidth());
        values.put(DatabaseConstants.FIGURE_COLOUR, makeNumber(figure.isColour()));
        values.put(DatabaseConstants.STEP_COLOUR, makeNumber(stepColour));

        return values;
    }

    public void getFromDatabase(ChessBoard board){
        Cursor cursor = database.query(DatabaseConstants.TABLE_NAME, null, null, null, null, null, null);

        FigureCursorWrapper cursorWrapper = new FigureCursorWrapper(cursor, board);

        cursorWrapper.moveToFirst();

        board.setStepColour(cursorWrapper.getStepColour());

        try {
            while (!cursorWrapper.isAfterLast()){
                ChessPiece figure = cursorWrapper.getFigure();
                Cell figureCell = figure.getCurrentCell();
                for(Cell cell: board.getCells()){
                    if(figureCell.equals(cell)) cell.setFigure(figure);
                }
                cursorWrapper.moveToNext();
            }
        }
        finally{
            cursorWrapper.close();
        }
    }

    public void updateDatabase(ChessPiece figure, Cell fromCell, boolean colour){
        Log.d("TAG", "Database updated.");
        ContentValues values = getValues(figure, colour);
        String[] where = {String.valueOf(fromCell.getHeight()), String.valueOf(fromCell.getWidth())};
        database.update(DatabaseConstants.TABLE_NAME, values, DatabaseConstants.CELL_HEIGHT + " = ? AND "
                + DatabaseConstants.CELL_WIDTH + "= ?",where);

        int stepColour = (colour) ? 1 : 0;
        String sql =  "update " + DatabaseConstants.TABLE_NAME + " set " + DatabaseConstants.STEP_COLOUR + " = " + String.valueOf(stepColour);
        database.execSQL(sql);
    }

    public void deleteFromDatabase(ChessPiece figure){
        String[] where = {String.valueOf(figure.getCurrentCell().getHeight()), String.valueOf(figure.getCurrentCell().getWidth())};
        database.delete(DatabaseConstants.TABLE_NAME, DatabaseConstants.CELL_HEIGHT + " = ? AND "
                + DatabaseConstants.CELL_WIDTH + "= ?" , where);
    }

    private int makeNumber(boolean bool){
        return (bool) ? 1 : 0;
    }

    public void clearDatabase(){
        database.execSQL("DELETE FROM " + DatabaseConstants.TABLE_NAME);
    }
}
