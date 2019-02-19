package com.example.oalam.smartwater;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorSeekbarFragment extends Fragment {
    CustomSeekBar seekbarTurb;
    CustomSeekBar seekbarLvl;
    CustomSeekBar seekbarTemp;

    private float totalSpan = 100;
    private float redSpan = 33.3f;
    private float greenSpan = 33.3f;
    TextView levelTextView, temperatureTextView, turbidityTextView;

    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences mPreferences;


    public SensorSeekbarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor_seekbar, container, false);

        seekbarTurb = ((CustomSeekBar) view.findViewById(R.id.seekBarTurbidity));
        seekbarLvl = ((CustomSeekBar) view.findViewById(R.id.seekBarWaterLevel));
        seekbarTemp = ((CustomSeekBar) view.findViewById(R.id.seekBarTemperature));
        levelTextView = view.findViewById(R.id.LevelValue);
        temperatureTextView = view.findViewById(R.id.temperatureValue);
        turbidityTextView = view.findViewById(R.id.turbidityValue);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("data/");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data dbData = dataSnapshot.getValue(data.class);

                //   Toast.makeText(getContext(), "Key:"+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getContext(), "Level:"+dbData.getLevel(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getContext(), "temperature:"+dbData.getTemperature(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(), "turbidity:"+dbData.getTurbidity(), Toast.LENGTH_SHORT).show();
//                Log.v("SensorFragment ",dbData.getLevel());
                //              Log.v("SensorFragment ",dataSnapshot.getKey());
                //            Log.v("SensorFragment ",dbData.getTemperature());
                //           Log.v("SensorFragment ",dbData.getTurbidity());

                if ((dbData.getLevel() != null && !dbData.getLevel().isEmpty() && !dbData.getLevel().equals("null")) && (dbData.getTemperature() != null && !dbData.getTemperature().isEmpty() && !dbData.getTemperature().equals("null")) && (dbData.getTurbidity() != null && !dbData.getTurbidity().isEmpty() && !dbData.getTurbidity().equals("null"))) {
                    levelTextView.setText(dbData.getLevel());
                    temperatureTextView.setText(dbData.getTemperature());
                    turbidityTextView.setText(dbData.getTurbidity());
                    Double turb=Double.parseDouble(dbData.getTurbidity());
                    Double level=Double.parseDouble(dbData.getLevel());
                    Double temp=Double.parseDouble(dbData.getTemperature());

                    seekbarTurb.setMax(1000);
                    seekbarTurb.setEnabled(false);
                    seekbarLvl.setEnabled(false);
                    seekbarTemp.setEnabled(false);

                    seekbarTurb.setProgress(Integer.valueOf(turb.intValue()));
                    seekbarLvl.setProgress(Integer.valueOf(level.intValue()));
                    seekbarTemp.setProgress(Integer.valueOf(temp.intValue()));


                    // waveLoadingView.setProgressValue(Integer.parseInt(dbData.getLevel()));
                    //waveLoadingView.setCenterTitle(dbData.getLevel()+"%");
                }
                //level=levelTextView.getText().toString();
                //temperature=temperatureTextView.getText().toString();
                //turbidity=turbidityTextView.getText().toString();

                // Toast.makeText(getContext(), "onChildAdded Called", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        initDataToSeekbar();

        return view;
    }


    private void initDataToSeekbar() {
        seekbarTemperature();
        seekbarTurbidity();
        seekbarWaterLevel();
    }


    private void seekbarTurbidity() {

        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((redSpan / totalSpan) * 100);
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);

        seekbarTurb.initData(progressItemList);
        seekbarTurb.invalidate();
    }

    private void seekbarWaterLevel() {

        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((redSpan / totalSpan) * 100);
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);

        seekbarLvl.initData(progressItemList);
        seekbarLvl.invalidate();
    }

    private void seekbarTemperature() {

        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((redSpan / totalSpan) * 100);
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);

        seekbarTemp.initData(progressItemList);
        seekbarTemp.invalidate();
    }

    public static class data {
        public String level;
        public String temperature;
        public String turbidity;

        public data() {

        }

        public data(String level, String temperature, String turbidity) {
            this.level = level;
            this.temperature = temperature;
            this.turbidity = turbidity;
        }

        public String getLevel() {
            return level;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getTurbidity() {
            return turbidity;
        }
    }

}
