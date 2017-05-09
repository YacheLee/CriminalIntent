package com.dentaltw.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2017/5/3.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    private CrimeLab(Context context){
        mCrimes = new ArrayList();
    }
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(String id){
        for(Crime crime: mCrimes){
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return mCrimes.get(Integer.parseInt(id));
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime crime){
        mCrimes.add(crime);
    }
}