package com.dentaltw.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dentaltw.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by Scott on 2017/5/10.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + CrimeTable.NAME + " (" +
                " _id integer primary key autoincrement, " +
                    CrimeTable.Cols.UUID + ", " +
                    CrimeTable.Cols.TITLE + ", " +
                    CrimeTable.Cols.DATE + ", " +
                    CrimeTable.Cols.SOLVED + ", " +
                    CrimeTable.Cols.SUSPECT+
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
