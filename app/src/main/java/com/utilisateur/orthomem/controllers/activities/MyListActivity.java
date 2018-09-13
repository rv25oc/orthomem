package com.utilisateur.orthomem.controllers.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.firebase.perf.metrics.AddTrace;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.fragments.MyListFragment;

public class MyListActivity extends FragmentActivity {

    @Override

    @AddTrace(name = "MyListonCreateTrace", enabled = true /* optional */)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment : https://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
            MyListFragment myFragment = new MyListFragment();

            //Adding key value default pairs to the bundle
            Bundle myBundle = new Bundle();
            myBundle.putString("DEFAULT_BUNDLE", "DEFAULT_BUNDLE value"); //Default value

            myFragment.setArguments(myBundle);
            getSupportFragmentManager().beginTransaction().add(
                    android.R.id.content, myFragment).commit();
        }

    }
}

