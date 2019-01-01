package com.example.oalam.smartwater;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView levelTextView, temperatureTextView, turbidityTextView;
    WaveLoadingView waveLoadingView;

    public SensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        waveLoadingView = (WaveLoadingView) view.findViewById(R.id.waveLoadingView);
        levelTextView = view.findViewById(R.id.LevelValue);
        temperatureTextView = view.findViewById(R.id.temperatureValue);
        turbidityTextView = view.findViewById(R.id.turbidityValue);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("data/");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data dbData = dataSnapshot.getValue(data.class);

                if ((dbData.getLevel() != null && !dbData.getLevel().isEmpty() && !dbData.getLevel().equals("null")) && (dbData.getTemperature() != null && !dbData.getTemperature().isEmpty() && !dbData.getTemperature().equals("null")) && (dbData.getTurbidity() != null && !dbData.getTurbidity().isEmpty() && !dbData.getTurbidity().equals("null"))) {
                    levelTextView.setText(dbData.getLevel());
                    temperatureTextView.setText(dbData.getTemperature());
                    turbidityTextView.setText(dbData.getTurbidity());
                    waveLoadingView.setProgressValue(Integer.parseInt(dbData.getLevel()));
                    waveLoadingView.setCenterTitle(dbData.getLevel() + "%");
                }
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
        return view;
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

