package com.dentaltw.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Scott on 2017/5/3.
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CrimeListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }
        else{
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_fragment_container, newDetail)
                .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
