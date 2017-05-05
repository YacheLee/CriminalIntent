package com.dentaltw.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.dentaltw.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, String crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String crimeID = (String) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeID);
    }
}
