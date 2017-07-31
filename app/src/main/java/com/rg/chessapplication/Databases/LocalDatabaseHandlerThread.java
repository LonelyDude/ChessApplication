package com.rg.chessapplication.Databases;


import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.Chess.Figures.ChessPiece;

import java.util.ArrayList;

/**
 * Created by HP PC on 24.01.2017.
 */

public class LocalDatabaseHandlerThread extends HandlerThread {

    private static final String TAG = "DATABASE THREAD";
    private static final String NAME = "Database thread";

    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int UPDATE = 2;
    private static final int CHECK = 3;
    private static final int GET = 4;
    private static final int CLEAR = 5;

    private DatabaseWorker databaseWorker = null;
    private Handler responseHandler = null;
    private Handler messageHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            ChessPiece figure;

            switch (msg.what){
                case INSERT:
                    Log.d(TAG, "Inserting data.");
                    ArrayList<Object> arr = (ArrayList<Object>) msg.obj;
                    databaseWorker.insertIntoDatabase((ChessPiece)arr.get(0), (Boolean) arr.get(1));
                    break;
                case DELETE:
                    Log.d(TAG, "Deleting data.");
                    figure = (ChessPiece) msg.obj;
                    databaseWorker.deleteFromDatabase(figure);
                    break;
                case UPDATE:
                    Log.d(TAG, "Updating data.");
                    arr = (ArrayList<Object>) msg.obj;
                    databaseWorker.updateDatabase((ChessPiece)arr.get(0), (Cell) arr.get(1), (Boolean) arr.get(2));
                    break;
                case CHECK:
                    Log.d(TAG, "Checking data.");
                    responseHandler.obtainMessage(0,databaseWorker.isEmpty()).sendToTarget();
                    break;
                case GET:
                    Log.d(TAG, "Getting data.");
                    ChessBoard board = (ChessBoard) msg.obj;
                    databaseWorker.getFromDatabase(board);
                    break;
                case CLEAR:
                    Log.d(TAG, "Clear data.");
                    databaseWorker.clearDatabase();
            }
        }
    };

    public LocalDatabaseHandlerThread(SQLiteDatabase database, Handler responseHandler) {
        super(NAME);
        databaseWorker = new DatabaseWorker(database);
        this.responseHandler = responseHandler;
    }

    public void insertData(ChessPiece figure, boolean stepColour){
        ArrayList<Object> ar= new ArrayList<>();
        ar.add(figure);
        ar.add(stepColour);

        messageHandler.obtainMessage(INSERT, ar).sendToTarget();
    }

    public void deleteData(ChessPiece figure){
        messageHandler.obtainMessage(DELETE, figure).sendToTarget();
    }

    public void updateData(ChessPiece figure, Cell cell, boolean stepColour){
        ArrayList<Object> ar= new ArrayList<>();
        ar.add(figure);
        ar.add(cell);
        ar.add(stepColour);

        messageHandler.obtainMessage(UPDATE, ar).sendToTarget();
    }

    public void getData(ChessBoard board){
        messageHandler.obtainMessage(GET, board).sendToTarget();
    }

    public void checkData(){
        messageHandler.obtainMessage(CHECK).sendToTarget();
    }

    public void clearData(){
        messageHandler.obtainMessage(CLEAR).sendToTarget();
    }
}
