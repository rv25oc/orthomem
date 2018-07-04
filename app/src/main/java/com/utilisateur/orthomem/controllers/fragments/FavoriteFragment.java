// FavoriteFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;



public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragTAG init";
    private TextView mStatusTextView;
    private TextView mTitleTextView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favorite, container, false);
       }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = view.findViewById(R.id.favorite_title);
        mStatusTextView = view.findViewById(R.id.favorite_status);

        mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();

        FirebaseUser myUser = mAuth.getCurrentUser();
        updateUI(myUser);

    }

    private void updateUI(FirebaseUser user) {
        
        if (user != null) {
            mStatusTextView.setText(mStatusTextView.getText() + " " + user.getEmail()+"\n"+user.getUid());
        }    else {
            mStatusTextView.setText(R.string.signed_out);
        }
    }
}