package com.example.playasarc.proyectoplayas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramon on 26/02/2018.
 */

public class BeachManager {
    private static BeachManager mBeachManager;
    private List<BeachMark> mBeachMark;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private MapDBHandler mapDBHandler;

    public static BeachManager get(Context context) {
        if (mBeachManager == null) {
            mBeachManager = new BeachManager(context);
        }
        return mBeachManager;
    }

    private BeachManager(Context context) {
        mContext = context.getApplicationContext();
        mapDBHandler = new MapDBHandler(mContext);
        mDatabase = mapDBHandler.getWritableDatabase();
        mBeachMark = new ArrayList<>();
        insertDefaultBeaches();

    }

    private void insertDefaultBeaches(){
BeachMark bm1 = new BeachMark();
bm1.setmName("Ciutat Jardí");
bm1.setmLatLng(new LatLng(39.552, 2.688));



        BeachMark bm2 = new BeachMark();
        bm2.setmName("Cala Barques");
        bm2.setmLatLng(new LatLng(39.5, 3.296));


        BeachMark bm3 = new BeachMark();
        bm3.setmName("Platja de Muro");
        bm3.setmLatLng(new LatLng(39.787, 3.13));

        mapDBHandler.addMarkMap(bm1);
        mapDBHandler.addMarkMap(bm2);
        mapDBHandler.addMarkMap(bm3);
    }

    public List<BeachMark> getmBeachMark() {
        mBeachMark = new ArrayList<>();
        MapDBHandler.BeachCursorWrapper bcw = mapDBHandler.getMarkMap();

        try {
            bcw.moveToFirst();
            while (!bcw.isAfterLast()) {
                mBeachMark.add(bcw.getBeach());
                bcw.moveToNext();
            }
        } finally {
            bcw.close();
        }


        return mBeachMark;
    }

    public void addBeachDB(BeachMark bm){
        mapDBHandler.addMarkMap(bm);
        mBeachMark.add(bm);
    }
}
