package com.example.playasarc.proyectoplayas;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by Ramon on 26/02/2018.
 */

public class BeachMark {
    private UUID mId;
    private String mName;
    private LatLng mLatLng;

    public BeachMark() {
        mId = UUID.randomUUID();
    }

    public BeachMark(UUID uuid) {
        mId = uuid;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }
}
