package com.example.playasarc.proyectoplayas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ramon on 27/02/2018.
 */

public class BeachReportManager {
    private static BeachReportManager mBeachReportManager;
    private List<BeachReport> mBeachReport;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private ReportDBHandler reportDBHandler;

    public static BeachReportManager get(Context context) {
        if (mBeachReportManager == null) {
            mBeachReportManager = new BeachReportManager(context);
        }
        return mBeachReportManager;
    }

    private BeachReportManager(Context context) {
        mContext = context.getApplicationContext();
        reportDBHandler = new ReportDBHandler(mContext);
        mDatabase = reportDBHandler.getWritableDatabase();
        mBeachReport = new ArrayList<>();
    }

    public List<BeachReport> getmBeachReports(UUID id_beach) {
        mBeachReport = new ArrayList<>();
        ReportDBHandler.ReportCursorWrapper bcw = reportDBHandler.getReportBeach(id_beach);

        try {
            bcw.moveToFirst();
            while (!bcw.isAfterLast()) {
                mBeachReport.add(bcw.getBeach());
                bcw.moveToNext();
            }
        } finally {
            bcw.close();
        }

        return mBeachReport;
    }

    public BeachReport getOneBeachReports(UUID id_beachReport) {
        ReportDBHandler.ReportCursorWrapper bcw = reportDBHandler.getOneReportBeach(id_beachReport);
        bcw.moveToFirst();
        BeachReport br = bcw.getBeach();
        bcw.close();
        return br;

    }

    public void addReport(BeachReport br){
        reportDBHandler.addReport(br);
    }
}
