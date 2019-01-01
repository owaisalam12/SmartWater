package com.example.oalam.smartwater;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    CardView statistics, notifRecords, prjAbout, seekbar;
    private MenuAdapter mMenuAdapter;
    ArrayList<String> mTitlesF = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        statistics = (CardView) view.findViewById(R.id.card_view_waterLevel);
        statistics.setOnClickListener(this);
        notifRecords = (CardView) view.findViewById(R.id.card_view_statistics);
        notifRecords.setOnClickListener(this);
        prjAbout = (CardView) view.findViewById(R.id.card_view_about);
        prjAbout.setOnClickListener(this);
        seekbar = (CardView) view.findViewById(R.id.card_view_Records);
        seekbar.setOnClickListener(this);
        mTitlesF = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.menuOptions)));
        mMenuAdapter = new MenuAdapter(mTitlesF);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_view_waterLevel:
                getActivity().setTitle(mTitlesF.get(1));
                mMenuAdapter.setViewSelected(1, true);
                goToFragment(new SensorFragment(), "SENSOR_FRAGMENT");
                break;

            case R.id.card_view_statistics:
                getActivity().setTitle(mTitlesF.get(2));
                mMenuAdapter.setViewSelected(2, true);
                goToFragment(new SensorSeekbarFragment(), "SEEKBAR_FRAGMENT");
                break;

            case R.id.card_view_Records:
                getActivity().setTitle(mTitlesF.get(3));
                mMenuAdapter.setViewSelected(3, true);
                goToFragment(new NotificationRecordsFragment(), "RECORDS_FRAGMENT");
                break;

            case R.id.card_view_about:
                getActivity().setTitle(mTitlesF.get(4));
                mMenuAdapter.setViewSelected(4, true);
                goToFragment(new AboutFragment(), "ABOUT_FRAGMENT");

                break;
        }
    }

    private void goToFragment(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, TAG).commit();
    }

}
