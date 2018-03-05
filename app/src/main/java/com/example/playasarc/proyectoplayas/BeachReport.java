package com.example.playasarc.proyectoplayas;

;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ramon on 26/02/2018.
 */

public class BeachReport {
    private UUID mIdBeach;
    private UUID mId;
    private double mTemperature;
    private String mColorFlag;
    private String mAfluencia;
    private Date mDate;

    public BeachReport(UUID idb) {
        mIdBeach=idb;
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public BeachReport(UUID idb, UUID id) {
        mIdBeach=idb;
        mId = id;
    }

    public UUID getmIdBeach() {
        return mIdBeach;
    }

    public void setmIdBeach(UUID mIdBeach) {
        this.mIdBeach = mIdBeach;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public double getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getmColorFlag() {
        return mColorFlag;
    }

    public void setmColorFlag(String mColorFlag) {
        this.mColorFlag = mColorFlag;
    }

    public String getmAfluencia() {
        return mAfluencia;
    }

    public void setmAfluencia(String mAfluencia) {
        this.mAfluencia = mAfluencia;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
