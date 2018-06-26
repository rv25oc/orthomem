package com.utilisateur.orthomem.controllers.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.fragments.MyListFragment;

public class MyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment : https://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
                MyListFragment details = new MyListFragment();
                details.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(
                        android.R.id.content, details).commit();
            }
        }
    }

