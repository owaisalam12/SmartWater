package com.example.oalam.smartwater;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationRecordsFragment extends Fragment {

    String pack, title, text;
    ListView listView;
    ArrayList<Notifications> words;
    NotificationsAdapter adapter;
    //    SharedPreferences mPreferences;
    SharedPreferences prefs;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public NotificationRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_records, container, false);
        setHasOptionsMenu(true);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotice, new IntentFilter("Msg"));
        prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


        loadData();

        listView = (ListView) view.findViewById(R.id.listView);
        //creating the adapter
        adapter = new NotificationsAdapter(getActivity(), R.layout.list_item, words);
        //attaching adapter to the listview
        listView.setAdapter(adapter);


        return view;
    }


    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pack = intent.getStringExtra("package");
            title = intent.getStringExtra("title");
            text = intent.getStringExtra("text");

            String packageName = "com.example.oalam.notification";
            if (pack.equals(packageName) || pack.contains(packageName)) {
                words.add(new Notifications(title, text, currentTime()));
                adapter.notifyDataSetChanged();
            }

//            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor.putString("text", text);
//            editor.apply();

            saveData();
        }
    };

    private void saveData() {
        SharedPreferences.Editor editor = prefs.edit();
//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        Gson gson = new Gson();
        String json = gson.toJson(words);
        editor.putString("data", json);
        editor.apply();

    }

    private void loadData() {
        //SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        //prefs.edit().clear().apply();
        Gson gson = new Gson();
        String json = prefs.getString("data", null);
        Type type = new TypeToken<ArrayList<Notifications>>() {
        }.getType();
        words = gson.fromJson(json, type);
        if (words == null) {
            words = new ArrayList<Notifications>();

        }
    }

    private String currentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String time = dateFormat.format(new Date()).toString();

        return time;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mymenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear) {
            //for clear
            prefs.edit().clear().apply();
            //for refresh fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
