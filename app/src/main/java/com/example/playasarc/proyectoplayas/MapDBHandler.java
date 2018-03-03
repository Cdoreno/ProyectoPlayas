package com.example.playasarc.proyectoplayas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by Ramon on 27/02/2018.
 */

public class MapDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "maps.db";

    //TABLE NAME
    public static final String TABLE_MARK = "mark";
    //TABLE COLUMNS NAME
    public static final String COLUMN_UUID = "mark_uuid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";


    public MapDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARK + "(" +
                COLUMN_UUID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LAT + " REAL, "+
                COLUMN_LNG + " REAL "+
                ");";
        db.execSQL(CREATE_MARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARK);
        onCreate(db);
    }

    //Add Row
    public void addMarkMap(BeachMark beachMark) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, String.valueOf(beachMark.getmId()));
        values.put(COLUMN_NAME, beachMark.getmName());
        values.put(COLUMN_LAT, beachMark.getmLatLng().latitude);
        values.put(COLUMN_LNG, beachMark.getmLatLng().longitude);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MARK, null, values);
        db.close();
    }

    public BeachCursorWrapper getMarkMap() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_MARK, null);

        return new BeachCursorWrapper(mCursor);
    }

    public class BeachCursorWrapper extends CursorWrapper {
        public BeachCursorWrapper(Cursor cursor) {
            super(cursor);
        }
        public BeachMark getBeach() {
            String uuidString = getString(getColumnIndex(COLUMN_UUID));
            String name = getString(getColumnIndex(COLUMN_NAME));
            double lat = getDouble(getColumnIndex(COLUMN_LAT));
            double lng = getDouble(getColumnIndex(COLUMN_LNG));

            BeachMark bm = new BeachMark(UUID.fromString(uuidString));
            bm.setmName(name);
            bm.setmLatLng(new LatLng(lat,lng));
            return bm;
        }
    }
}
