package com.example.oalam.smartwater;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView levelTextView, temperatureTextView, turbidityTextView, estimatedTimeTextView,currentWaterFlowValue;
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
        estimatedTimeTextView=view.findViewById(R.id.estimatedTimeValue);
        currentWaterFlowValue=view.findViewById(R.id.currentWaterFlowValue);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Dummy/");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data dbData = dataSnapshot.getValue(data.class);

                if ((dbData.getLevel() != null && !dbData.getLevel().isEmpty() && !dbData.getLevel().equals("null")) && (dbData.getTemperature() != null && !dbData.getTemperature().isEmpty() && !dbData.getTemperature().equals("null")) && (dbData.getTurbidity() != null && !dbData.getTurbidity().isEmpty() && !dbData.getTurbidity().equals("null"))) {
                    //int level=Integer.parseInt(dbData.getLevel());
                  //  Toast.makeText(getActivity(), "Level:"+dbData.getLevel()+" Temp:"+dbData.getTemperature()+" Turb:"+dbData.getTurbidity()+" Flow:"+dbData.getFlow(), Toast.LENGTH_SHORT).show();
                    int level=waterlevel(Integer.parseInt(dbData.getLevel()));
                    int percentage;
                    if(level==24){percentage=0;}
                    else if(level==23){percentage=4;}  else if(level==22){percentage=8;}
                    else if(level==21){percentage=12;} else if(level==20){percentage=16;}
                    else if(level==19){percentage=20;} else if(level==18){percentage=24;}
                    else if(level==17){percentage=28;} else if(level==16){percentage=32;}
                    else if(level==15){percentage=36;} else if(level==14){percentage=40;}
                    else if(level==13){percentage=44;} else if(level==12){percentage=48;}
                    else if(level==11){percentage=52;} else if(level==10){percentage=56;}
                    else if(level==9){percentage=60;}  else if(level==8){percentage=64;}
                    else if(level==7){percentage=68;}  else if(level==6){percentage=72;}
                    else if(level==5){percentage=76;}  else if(level==4){percentage=80;}
                    else if(level==3){percentage=84;}  else if(level==2){percentage=88;}
                    else if(level==1){percentage=96;}  else if(level==0){percentage=100;}
                    else{
                        percentage=-1; //to show error
                    }
                    levelTextView.setText(dbData.getLevel());
                    waveLoadingView.setProgressValue(percentage);
                    waveLoadingView.setCenterTitle(percentage + "%");
                    //temperatureTextView.setText(dbData.getTemperature());
                    //turbidityTextView.setText(dbData.getTurbidity());

                    double Vol=estimatedTime(waterlevel(Integer.parseInt(dbData.getLevel())));
                    double time=Vol/Double.parseDouble(dbData.getFlow());
                    DecimalFormat df = new DecimalFormat("#.0");
                    double t=time/60;
                    estimatedTimeTextView.setText(String.valueOf(df.format(t))+" mins");
                    currentWaterFlowValue.setText(dbData.getFlow()+" ltr/m");

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
        public String flow;

        public data() {

        }

        public data(String level, String temperature, String turbidity, String flow) {
            this.level = level;
            this.temperature = temperature;
            this.turbidity = turbidity;
            this.flow = flow;
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

        public String getFlow() {
            return flow;
        }
    }

public int waterlevel(int sensorHeight){

        int diff=28-24;
        int H2=sensorHeight-diff;

        return H2;
}

public double estimatedTime(int H2){
        double v=3.141*(13.25*13.25)*H2;

        return v;
    }
}

