package com.dentaltw.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Scott on 2017/5/3.
 */

public class CrimeListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    public static final int REQUEST_CRIME = 1;
    private int mPosition = 0;

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private boolean mSubtitleVisible;
    private Callbacks mCallbacks;

    private TextView message;
    private Button addBtn;

    public interface Callbacks{
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        message = (TextView) view.findViewById(R.id.message_text);
        addBtn = (Button) view.findViewById(R.id.add_crime_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCrime();
            }
        });

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== REQUEST_CRIME){
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = (MenuItem) menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
        else{
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                addCrime();
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCrime() {
        UUID crimeId = UUID.randomUUID();
        Crime crime = new Crime(crimeId);
        CrimeLab.get(getActivity()).addCrime(crime);
        updateUI();
        mCallbacks.onCrimeSelected(crime);
    }

    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int count = crimeLab.getCrimes().size();
        String subTitle = getResources().getQuantityString(R.plurals.subtitle_plural, count, count);

        if (mSubtitleVisible) {
            subTitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subTitle);
    }

    public void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        int size = crimes.size();

        if (mCrimeAdapter==null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mCrimeAdapter);
        }
        else{
            mCrimeAdapter.setCrimes(crimes);
            mCrimeAdapter.notifyItemChanged(mPosition);
        }

        boolean isZero = size==0;
        mRecyclerView.setVisibility(isZero? View.INVISIBLE : View.VISIBLE);
        message.setVisibility(isZero? View.VISIBLE : View.INVISIBLE);
        addBtn.setVisibility(isZero? View.VISIBLE : View.INVISIBLE);
        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Crime mCrime;
        private ImageView mSolvedImageView;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(mCrime.isSolved()? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onCrimeSelected(mCrime);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimeList;

        public CrimeAdapter(List<Crime> crimeList) {
            mCrimeList = crimeList;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimeList.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimeList = crimes;
        }
    }
}