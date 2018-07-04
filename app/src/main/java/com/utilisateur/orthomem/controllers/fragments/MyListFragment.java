// MyListFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.model.Exercice;


public class MyListFragment extends Fragment {



        public MyListFragment() {
        //public MyListFragment(String exerciceid) {
            // Required empty public constructor
        }

    //private static final String EXERCICEID = "EXERCICEID init";
    //private String myExerciceId;

    private TextView mTitleTextView;
    private TextView mListNameTextView;
    private TextView mStatusTextView;


    /*
            MyListFragment myFragment = new MyListFragment();

            //Recupération de la chaine passée en parametre
            Bundle myBundle = new Bundle();
            myBundle.putString("EXERCICEID", exerciceid); // pass your values and retrieve them in the other Activity using a Bundle

            myFragment.setArguments(myBundle);
            return myFragment;
        }




        // Cf. http://tutos-android-france.com/fragments/
        public static MyListFragment newInstance(String exerciceid) {
            MyListFragment fragment = new MyListFragment();
            Bundle args = new Bundle();
            args.putString(EXERCICEID, exerciceid);
            fragment.setArguments(args);
            return fragment;
        }
*/
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.activity_mylist, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mTitleTextView = view.findViewById(R.id.mylist_title);
            mStatusTextView = view.findViewById(R.id.mylist_status);
            mStatusTextView.setText(null);

            mListNameTextView = view.findViewById(R.id.mylist_name);


            //get the intent in the target activity
            Intent myIntent = getActivity().getIntent();

            // receiving our object
            Exercice myExercice = (Exercice) myIntent.getSerializableExtra("EXERCICE");

            mTitleTextView.setText(myExercice.getLabel());
            mStatusTextView.setText(myExercice.getExercicewords().toString());
/*
            //get the attached bundle from the intent
            Bundle myBundle = myIntent.getExtras();

            //Extracting the stored data from the bundle
            myExerciceId = myBundle.getString("EXERCICEID");
            mStatusTextView.setText(myExerciceId);
*/

/*
            if (getArguments() != null) {
                Bundle args = getArguments();
                if (args.containsKey(EXERCICEID)){
                    mListNameTextView.setText(args.getString("Contenu de l'exercice : " + EXERCICEID));}
            } else {
                mListNameTextView.setText("Arguments null " + EXERCICEID);
            }
*/
        }
}
