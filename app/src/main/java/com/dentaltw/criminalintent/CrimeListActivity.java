package com.dentaltw.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Scott on 2017/5/3.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CrimeListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
