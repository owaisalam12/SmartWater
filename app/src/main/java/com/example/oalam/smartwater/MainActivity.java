package com.example.oalam.smartwater;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;

    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

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
        goToFragment2(new HomeFragment());

        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
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
    private void goToFragment2(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
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
                goToFragment2(new HomeFragment());
                break;
            case 1://
                goToFragment2(new AboutFragment());
                break;
            case 2://
                goToFragment2(new AboutFragment());
                break;
            case 3://
                goToFragment2(new AboutFragment());
                break;
            case 4:// about
                goToFragment2(new AboutFragment());
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
    private void addUserData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/");

        String phonenumber = new LoginActivity().getMynumber();
        String name = new LoginActivity().getMyname();
        String token= new LoginActivity().getMytoken();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser() .getUid();
            if ((phonenumber != null && !phonenumber.isEmpty() && !phonenumber.equals("null")) && (name != null && !name.isEmpty() && !name.equals("null")) && (userId != null && !userId.isEmpty() && !userId.equals("null"))&& (token != null && !token.isEmpty() && !token.equals("null"))){
                userData userData = new userData(name, phonenumber,token);
                myRef.child(userId).setValue(userData);
            }

        }else{

        }
    }
}
