package com.rg.chessapplication.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HP PC on 21.01.2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "board_db";
    private static final int VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "Database created.");
        db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_NAME + "("
                + DatabaseConstants.FIGURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseConstants.CELL_WIDTH + " TINYINT(1)," + DatabaseConstants.CELL_HEIGHT + " TINYINT(1),"
                + DatabaseConstants.FIGURE + " VARCHAR(10)," + DatabaseConstants.FIGURE_COLOUR + " BIT,"
                + DatabaseConstants.STEP_COLOUR + " BIT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NO
    }

}
