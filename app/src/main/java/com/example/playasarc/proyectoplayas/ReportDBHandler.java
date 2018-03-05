package com.example.playasarc.proyectoplayas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Ramon on 27/02/2018.
 */

public class ReportDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reports.db";

    //TABLE NAME
    public static final String TABLE_REPORT = "report";
    //TABLE COLUMNS NAME
    public static final String COLUMN_UUID = "report_uuid";
    public static final String COLUMN_UUID_BEACH = "report_uuid_beach";
    public static final String COLUMN_TEMP = "temperatura";
    public static final String COLUMN_FLAG = "flag";
    public static final String COLUMN_AFLU = "afluencia";
    public static final String COLUMN_DATE = "date";


    public ReportDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REPORT_TABLE = "CREATE TABLE " + TABLE_REPORT + "(" +
                COLUMN_UUID + " TEXT PRIMARY KEY, " +
                COLUMN_UUID_BEACH + " TEXT, " +
                COLUMN_TEMP + " REAL, " +
                COLUMN_FLAG + " TEXT, "+
                COLUMN_AFLU + " TEXT, "+
                COLUMN_DATE + " DATE "+
                ");";
        db.execSQL(CREATE_REPORT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        onCreate(db);
    }

    //Add Row
    public void addReport(BeachReport br) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID_BEACH, String.valueOf(br.getmIdBeach()));
        values.put(COLUMN_UUID,String.valueOf(br.getmId()));
        values.put(COLUMN_AFLU, br.getmAfluencia());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        values.put(COLUMN_DATE, sdf.format(br.getmDate()));
        values.put(COLUMN_FLAG, br.getmColorFlag());
        values.put(COLUMN_TEMP, br.getmTemperature());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REPORT, null, values);
        db.close();
    }

    public ReportCursorWrapper getReportBeach(UUID id_beach) {
        SQLiteDatabase db = getWritableDatabase();
        String idAux = String.valueOf(id_beach);
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_REPORT + " WHERE " + COLUMN_UUID_BEACH +
                " =\"" + idAux +"\" ORDER BY "+COLUMN_DATE+" DESC;", null);

        return new ReportCursorWrapper(mCursor);
    }

    public ReportCursorWrapper getOneReportBeach(UUID id_beachReport) {
        SQLiteDatabase db = getWritableDatabase();
        String idAux = String.valueOf(id_beachReport);
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_REPORT + " WHERE " + COLUMN_UUID +
                " =\"" + idAux + "\";", null);

        return new ReportCursorWrapper(mCursor);
    }

    public class ReportCursorWrapper extends CursorWrapper {
        public ReportCursorWrapper(Cursor cursor) {
            super(cursor);
        }
        public BeachReport getBeach() {
            String uuidString = getString(getColumnIndex(COLUMN_UUID));
            String uuid2String = getString(getColumnIndex(COLUMN_UUID_BEACH));
            String aflu = getString(getColumnIndex(COLUMN_AFLU));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = null;
            try {
                date = sdf.parse(getString(getColumnIndex(COLUMN_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double temp = getDouble(getColumnIndex(COLUMN_TEMP));
            String flag = getString(getColumnIndex(COLUMN_FLAG));

            BeachReport br = new BeachReport(UUID.fromString(uuid2String),UUID.fromString(uuidString));
            br.setmAfluencia(aflu);
            br.setmDate(date);
            br.setmTemperature(temp);
            br.setmColorFlag(flag);
            return br;
        }
    }
}
