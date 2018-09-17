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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.model.Word;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyListFragment extends Fragment {

    private static final String TAG = "";
    @BindView(R.id.mylist_title)
    TextView mTitleTextView;
    @BindView(R.id.mylist_status)
    TextView mStatusTextView;
    @BindView(R.id.mylist_words)
    TextView mWordsTextView;
    @BindView(R.id.mylist_nbofwords)
    TextView mNbofwordsTextView;
    @BindView(R.id.mylist_creadate)
    TextView mCreadateTextView;
    @BindView(R.id.mylist_goal)
    TextView mGoalTextView;
    private FirebaseFirestore mBdd;


    public MyListFragment() {/*Required empty public constructor*/}

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.activity_mylist, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ButterKnife.bind(this, view);

            mBdd = FirebaseFirestore.getInstance();

            //get the intent in the target activity
            Intent myIntent = getActivity().getIntent();

            // receiving our object
            Exercice myExercice = (Exercice) myIntent.getSerializableExtra("EXERCICE");

            mTitleTextView.setText(myExercice.getLabel());
            mStatusTextView.setText(myExercice.getExercicewords().toString());
            getWordsFromLexique(myExercice.getExercicewords());

            mNbofwordsTextView.append("" + myExercice.getExercicewords().size());

            if (myExercice.getCreadate() != null) {
                mCreadateTextView.setText(convertDateToHour(myExercice.getCreadate()));
            }

            mGoalTextView.append(myExercice.getGoal());

        }

    private String convertDateToHour(Date date) {
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        return dfTime.format(date);
    }

    private void getWordsFromLexique(final ArrayList<String> wordsidslist) {

        for (int i = 0; i < wordsidslist.size(); i++) {
            String str1 = wordsidslist.get(i);
            mBdd.collection("openlexique").document("4ZY9gNoKyuRkMENywsKV").collection("words").document(str1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    document.getData();
                                    Log.w(TAG, document.getId() + " => | " + document.getData());
                                    mWordsTextView.append("\n " + document.get("label"));
                                    mStatusTextView.setText("");
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    mStatusTextView.setText(mStatusTextView.getText() + " : ko1 : " + task.getException());
                                }
                            } else {
                                Log.d(TAG, "No such document");
                                mStatusTextView.setText(mStatusTextView.getText() + " : ko2 : " + task.getException());
                            }
                        }
                    });
        }
    }
}
