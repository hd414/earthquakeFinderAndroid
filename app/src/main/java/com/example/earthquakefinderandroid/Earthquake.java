package com.example.earthquakefinderandroid;

public class Earthquake {

    private String mdate,mMag,mLoacation;

    public Earthquake(String mMag, String mLoacation,String mdate) {
        this.mdate = mdate;
        this.mMag = mMag;
        this.mLoacation = mLoacation;
    }

    public String getMdate() {
        return mdate;
    }

    public String getmMag() {
        return mMag;
    }

    public String getmLoacation() {
        return mLoacation;
    }
}

