// MyListFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utilisateur.orthomem.R;



public class MyListFragment extends Fragment {

        public MyListFragment() {
            // Required empty public constructor       }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {



            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.activity_mylist, container, false);
 /*
 Erreur : cannot resolve method getIntend() : Problème de contexte ???

            //get the intent in the target activity
            Intent myIntent = getIntent();

            //get the attached bundle from the intent
            Bundle myBundle = myIntent.getExtras();

            //Extracting the stored data from the bundle
            String myId = myBundle.getString("EXERCICIEID");

            Toast.makeText(getContext(), "Words of Exercice number: "+myId, Toast.LENGTH_SHORT).show();
*/
        }
}
