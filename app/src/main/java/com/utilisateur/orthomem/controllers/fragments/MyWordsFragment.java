// MyListsFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.WordListRecyclerViewAdapter;
import com.utilisateur.orthomem.model.Word;
import com.utilisateur.orthomem.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyWordsFragment extends Fragment
        implements WordListRecyclerViewAdapter.CheckBoxListener {

    private static final String TAG = "";
    private TextView mStatusTextView;
    private TextView mTitleTextView;
    private Button mSelection_Button;
    private Integer mNbOfSyllabes;
    private RecyclerView mRecyclerView;
    private WordListRecyclerViewAdapter mAdapter;
    private List<Word> mWordsList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;

    public MyWordsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_mywords, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = view.findViewById(R.id.mywords_title);
        mStatusTextView = view.findViewById(R.id.mywords_status);

        Intent myIntent = Objects.requireNonNull(getActivity()).getIntent();
        mNbOfSyllabes = (Integer) myIntent.getSerializableExtra("NBOFSYLLABES");

        mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();

        mWordsList.add(new Word("xxx","mylabel", 3));
        // Cf. http://www.chansek.com/RecyclerView-no-adapter-attached-skipping-layout/

            mRecyclerView = view.findViewById(R.id.myWordsRecyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            getLexique(mNbOfSyllabes);
            mAdapter = new WordListRecyclerViewAdapter(mWordsList, this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.getAdapter();

            configureOnClickRecyclerView();

 /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/

    }

    public void getLexique(Integer NbOfSyllabes) {

        mBdd.collection("openlexique").document("4ZY9gNoKyuRkMENywsKV")
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mTitleTextView.setText(mTitleTextView.getText()+"\n"+document.getString("version"));


                            mBdd.collection("openlexique").document(document.getId()).collection("words")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            mStatusTextView.setText("ok");

                                            for (DocumentSnapshot mydocument : task.getResult()) {
                                                Log.d(TAG, mydocument.getId() + " => " + mydocument.getId() + " | " + mydocument.getData());
                                                mydocument.getData();
                                                mWordsList.add(mydocument.toObject(Word.class));

                                                // Stockage de l'Id de l'exercice récupéré depuis Firebase
                                                String myId = mydocument.getId();
                                                final Word myWord = mWordsList.get(mWordsList.size()-1);
                                                myWord.setId(myId);
                                            }
                                        } else {
                                            Log.w(TAG, "Error getting documents.", task.getException());
                                            mStatusTextView.setText("ko");
                                        }
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void selectedClick() {
        List<Word> list = mAdapter.getSelectedItem();
        if (list.size() > 0){
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < list.size(); index++){
                Word myword = list.get(index);
                sb.append(myword.getLabel()+"\n");
            }
            showToast(sb.toString());
        }else{
            showToast("Please select any city");
        }
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.word_recyclerview_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // 1 - Get Word from adapter
                        Word clickedWord = mAdapter.getWordFromPosition(position);
                        // Action au clic sur un mot
                        Toast.makeText(getContext(), "You clicked on Word : "+clickedWord.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClickCheckBox(int position) {
        Word CheckedWord = mAdapter.getWordFromPosition(position);
        Toast.makeText(getContext(), "You checked the word : "+CheckedWord.getLabel(), Toast.LENGTH_SHORT).show();
    }
}
