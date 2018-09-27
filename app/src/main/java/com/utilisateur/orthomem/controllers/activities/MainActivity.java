package com.utilisateur.orthomem.controllers.activities;


import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.api.App;
import com.utilisateur.orthomem.controllers.fragments.AddListFragment;
import com.utilisateur.orthomem.controllers.fragments.FavoriteFragment;
import com.utilisateur.orthomem.controllers.fragments.HomeFragment;
import com.utilisateur.orthomem.controllers.fragments.MyListFragment;
import com.utilisateur.orthomem.controllers.fragments.MyListsFragment;
import com.utilisateur.orthomem.controllers.fragments.ParametersFragment;


public class MainActivity extends /*FragmentActivity*/ AppCompatActivity {

    /**********
     *
     * 1. Initialisation de la BottomNavigationView
     *
     **********/
    private static final String TAG="";
    private BottomNavigationView mNavigation;
    private MyListFragment myListFragment;
    private FirebaseFirestore mBdd;

    @Override
    protected void onResume() {
        super.onResume();
        //Crashlytics.getInstance().crash();  // Force application to crash for testing CrashLytic service from firebase
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
/*
            // Firebase Performance Monitor
            Trace myTrace = FirebasePerformance.getInstance().newTrace("Navigation trace");
            myTrace.start();

            ClipData.Item myitem = cache.fetch("Navigation");
            if (myitem != null) {
                myTrace.incrementMetric("Navigation_hit", 1);
            } else {
                myTrace.incrementMetric("iNavigation_hit_miss", 1);
            }
            myTrace.stop();
*/

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.w(TAG, "Navigation case : home");
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_favorite:
                    Log.w(TAG, "Navigation case : favorite");
                    showFragment(new FavoriteFragment());
                    return true;
                case R.id.navigation_mylists:
                    Log.w(TAG, "Navigation case : mylists");
                    //showFragment(new MyListsFragment());

                    configureAndShowDetailFragment(); // Gestion des vues en mode paysage >600dp
                    if (myListFragment == null || !myListFragment.isVisible()) {
                        showFragment(new MyListsFragment());
                    }
                    return true;
                case R.id.navigation_addlist:
                    Log.w(TAG, "ONavigation case : addlist");
                    showFragment(new AddListFragment());
                    return true;
                case R.id.navigation_parameters:
                    Log.w(TAG, "Navigation case : parameters");
                    showFragment(new ParametersFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mNavigation = findViewById(R.id.navigation);

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //mNavigation.setVisibility(GONE);

        App.getInstance().initFirebaseSdk(); //SINGLETON PATTERN

        mBdd = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mBdd.setFirestoreSettings(settings); //Permet d'utiliser les TIMESTAMPS de Firebase

        showFragment(new HomeFragment());
    }

    // Rattachement du Fragment à l'activité
    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    // Gestion des activités mylists+mylist en mode paysage >600dp
    private void configureAndShowDetailFragment() {
        myListFragment = (MyListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        if (myListFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            myListFragment = new MyListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, myListFragment)
                    .commit();
        }
    }

}
