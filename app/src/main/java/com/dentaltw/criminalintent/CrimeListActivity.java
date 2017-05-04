package com.dentaltw.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Scott on 2017/5/3.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
