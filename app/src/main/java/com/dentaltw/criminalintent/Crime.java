package com.dentaltw.criminalintent;

import java.util.Date;

/**
 * Created by Scott on 2017/5/3.
 */

public class Crime {
    private int index;
    private String mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(String id) {
        mId = id;
        mDate = new Date();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
