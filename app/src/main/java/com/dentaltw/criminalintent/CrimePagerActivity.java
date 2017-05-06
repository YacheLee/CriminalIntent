package com.dentaltw.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.dentaltw.android.criminalintent.crime_id";
    private ViewPager mViewPager;
    private Button mPrevButton;
    private Button mNextButton;
    private Crime mCrime;
    private List<Crime> mCrimeList;

    public static Intent newIntent(Context packageContext, String crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        String crimeId = (String)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimeList = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position);
                mCrime = crime;
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });
        mViewPager.setCurrentItem(Integer.parseInt(crimeId));

        mPrevButton = (Button) findViewById(R.id.btn_prev);
        mNextButton = (Button) findViewById(R.id.btn_next);
        updateButtonStatus();

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                updateButtonStatus();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                updateButtonStatus();
            }
        });
    }

    private void updateButtonStatus(){
        final int currentItem = mViewPager.getCurrentItem();
        boolean isFirst = currentItem==0;
        boolean isLast = currentItem==mCrimeList.size()-1;
        mPrevButton.setEnabled(isFirst? false: true);
        mNextButton.setEnabled(isLast? false: true);
    }
}