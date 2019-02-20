package com.example.oalam.smartwater;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView.OnMenuClickListener;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences mPreferences;
    private FirebaseAuth mAuth;
    TextView headerTextView;
    String prefName;
    String userName;
    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        headerTextView = (TextView) findViewById(R.id.duo_view_header_text_title);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState != null) {
            userName = mPreferences.getString("prefName", null);
            headerTextView.setText(userName);


        } else {

        }

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        // goToFragment(new TestFragment(), false);
        goToFragment2(new HomeFragment(), "HOME_FRAGMENT");
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
        // headerUserName();
        addUserData();
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        //Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onHeaderClicked() {
        //Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }

    private void goToFragment2(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, TAG).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0://home
                // goToFragment(new Test2Fragment(),false);
                goToFragment2(new HomeFragment(), "HOME_FRAGMENT");
                break;
            case 1:// Sensor
                goToFragment2(new SensorFragment(), "SENSOR_FRAGMENT");
                break;
            case 2://
                goToFragment2(new SensorSeekbarFragment(), "SEEKBAR_FRAGMENT");
                break;
            case 3://
                goToFragment2(new NotificationRecordsFragment(), "RECORDS_FRAGMENT");
                break;
            case 4://
                goToFragment2(new AboutFragment(), "ABOUT_FRAGMENT");
                break;
            default:
                //goToFragment(new MainFragment(), false);
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }

    private void addUserData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/");

        String phonenumber = new LoginActivity().getMynumber();
        String name = new LoginActivity().getMyname();
        String mac=new LoginActivity().getMymac();
        prefName = name;
        String token = new LoginActivity().getMytoken();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if ((phonenumber != null && !phonenumber.isEmpty() && !phonenumber.equals("null")) && (name != null && !name.isEmpty() && !name.equals("null")) && (userId != null && !userId.isEmpty() && !userId.equals("null")) && (token != null && !token.isEmpty() && !token.equals("null")) && (mac != null && !mac.isEmpty() && !mac.equals("null"))) {
                prefName = name;
                headerTextView.setText(prefName);
                userData userData = new userData(name, phonenumber, token,mac);
                myRef.child(userId).setValue(userData);
            }

        } else {

        }
    }

    private void headerUserName() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userData dbUserData = dataSnapshot.getValue(userData.class);
//                                Log.v("MainFragmentActivity ",dbUserData.getNameUser());
//                                Log.v("MainFragmentActivity ",dbUserData.getPhoneNumberUser());
//                                Log.v("MainFragmentActivity ",dbUserData.getDeviceTokenUser());

                if ((dbUserData.getNameUser() != null && !dbUserData.getNameUser().isEmpty() && !dbUserData.getNameUser().equals("null"))) {
                    headerTextView.setText(dbUserData.getNameUser());
                    // prefName=headerTextView.getText().toString();
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


    }

    public static class userData {
        public String nameUser;
        public String phoneNumberUser;
        public String deviceTokenUser;
        public String raspMAC;

        public userData() {

        }

        public userData(String nameUser, String phoneNumberUser, String deviceTokenUser, String raspMAC) {
            this.nameUser = nameUser;
            this.phoneNumberUser = phoneNumberUser;
            this.deviceTokenUser = deviceTokenUser;
            this.raspMAC=raspMAC;

        }


        public String getDeviceTokenUser() {
            return deviceTokenUser;
        }
        public String getraspMAC() {
            return raspMAC;
        }

        public String getNameUser() {
            return nameUser;
        }

        public String getPhoneNumberUser() {
            return phoneNumberUser;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //headerUserName();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("prefName", prefName);
        preferencesEditor.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            goToFragment2(new HomeFragment(), "HOME_FRAGMENT");
            setTitle(mTitles.get(0));


        }


    }
}
