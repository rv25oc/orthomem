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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.model.Exercice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyListFragment extends Fragment {

    private static final String TAG = "";
    private TextView mWordsTextView;
    private TextView mTitleTextView;
    private TextView mStatusTextView;
    private TextView mNbofwordsTextView;
    private TextView mCreadateTextView;
    private TextView mGoalTextView;
    private FirebaseFirestore mBdd;
    /*
        @BindView (R.id.my_toolbar)
        Toolbar toolbar ;
    */

    public MyListFragment() {/*Required empty public constructor*/}


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_mylist, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mTitleTextView = view.findViewById(R.id.mylist_title);
            mStatusTextView = view.findViewById(R.id.mylist_status);
            mWordsTextView = view.findViewById(R.id.mylist_words);
            mNbofwordsTextView = view.findViewById(R.id.mylist_nbofwords);
            mCreadateTextView = view.findViewById(R.id.mylist_creadate);
            mGoalTextView = view.findViewById(R.id.mylist_goal);

            mBdd = FirebaseFirestore.getInstance();

            //get the intent in the target activity
            Intent myIntent = getActivity().getIntent();

            // receiving our object
            Exercice myExercice = (Exercice) myIntent.getSerializableExtra("EXERCICE");
            mStatusTextView.setText(getContext().getResources().getString(R.string.mylist_status));
            getWordsFromLexique(myExercice.getExercicewords());
            mTitleTextView.setText(myExercice.getLabel());

            mNbofwordsTextView.append("" + myExercice.getExercicewords().size());

            if (myExercice.getCreadate() != null) {
                mCreadateTextView.append(convertDate(myExercice.getCreadate()));
            }
            mGoalTextView.append(myExercice.getGoal());

        }

    private String convertDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy 'à' HH:mm");
        return df.format(date);
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
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                    mStatusTextView.setText(mStatusTextView.getText() + " : ko1 : " + task.getException());
                                }
                                mStatusTextView.setText("");
                            } else {
                                Log.d(TAG, "No such document");
                                mStatusTextView.setText(mStatusTextView.getText() + " : ko2 : " + task.getException());
                            }
                        }
                    });
        }
    }
}
