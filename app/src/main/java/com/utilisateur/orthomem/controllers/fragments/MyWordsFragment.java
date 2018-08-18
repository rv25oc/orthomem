// MyListsFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.WordListRecyclerViewAdapter;
import com.utilisateur.orthomem.controllers.activities.AddListActivity;
import com.utilisateur.orthomem.model.Word;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class MyWordsFragment extends Fragment
        implements WordListRecyclerViewAdapter.CheckBoxListener {

    private static final String TAG = "";
    private TextView mStatusTextView;
    private TextView mTitleTextView;
    private Button mSubmitButton;
    private Integer mNbOfSyllabes;
    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private WordListRecyclerViewAdapter mAdapter;
    private ArrayList<Word> mWordsList = new ArrayList<>(); // Liste de chargement de la RecyclerView
    private ArrayList<Word> mSelectedWords = new ArrayList<>();  // Coolection des mots cochés/sélectionnés
    private ArrayList<String> mListIds = new ArrayList<>(); // Liste des wordids reçue/à transmettre à la fenêtre mere
    private ArrayList<String> mListLabels = new ArrayList<>(); //// Liste des labels à reçue/transmettre à la fenêtre mere
    private FirebaseFirestore mBdd;

    public MyWordsFragment() {/*Required empty public constructor*/}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_mywords, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = view.findViewById(R.id.mywords_title);
        mStatusTextView = view.findViewById(R.id.mywords_status);
        mCheckBox = view.findViewById(R.id.word_checkbox);
        mSubmitButton = view.findViewById(R.id.mywords_submit_button);


        Intent myIntent = Objects.requireNonNull(getActivity()).getIntent();
        Bundle extras = myIntent.getExtras();
        if(extras!=null) {
            mNbOfSyllabes = extras.getInt("NBOFSYLLABES");
            mListIds = extras.getStringArrayList("LISTIDS");
            mListLabels = extras.getStringArrayList("LISTLABELS");
            for (int i = 0; i < mListIds.size(); i++) {
                mSelectedWords.add(new Word(mListIds.get(i), mListLabels.get(i),(long)0)); //Récupération de la sélection précédente
           }
            mListIds.clear();
            mListLabels.clear();
        }

        mBdd = FirebaseFirestore.getInstance();

        //mWordsList.add(new Word("xxx","mylabel", (long)3));

        getLexique(mNbOfSyllabes);

        mRecyclerView = view.findViewById(R.id.myWordsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new WordListRecyclerViewAdapter(mWordsList, this);
        mRecyclerView.setAdapter(mAdapter);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity();
            }
        });
    }

    private void startNextActivity(){
        for (int i = 0; i < mSelectedWords.size(); i++) {
            mListIds.add(mSelectedWords.get(i).getWordId());
            mListLabels.add(mSelectedWords.get(i).getLabel());
        }

        // End the activity
        Intent myintent = new Intent(getActivity(), AddListActivity.class);
        myintent.putExtra("NEWIDS", mListIds);
        myintent.putExtra("NEWLABELS", mListLabels);
        getActivity().setResult(RESULT_OK, myintent);
        getActivity().finish();
    }

    public void getLexique(final Integer NbOfSyllabes) {
        mBdd.collection("openlexique").document("4ZY9gNoKyuRkMENywsKV")
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mTitleTextView.setText(mTitleTextView.getText()+" | "+document.getString("version"));

                        CollectionReference myRef = mBdd.collection("openlexique").document(document.getId()).collection("words");
                        Query myQuery=myRef.orderBy("label");

                        if (NbOfSyllabes != 0) {
                            if(NbOfSyllabes < 4){
                                myQuery = myRef.whereEqualTo("nbsyllabes",NbOfSyllabes);
                            } else {
                                if(NbOfSyllabes == 4){
                                    myQuery = myRef.orderBy("nbsyllabes").whereGreaterThanOrEqualTo("nbsyllabes",NbOfSyllabes);
                                }
                            }
                        }

                            myQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot mydocument : task.getResult()) {

                                                mydocument.getData();
                                                Log.w(TAG, mydocument.getId() + " => | " + mydocument.getData());

                                                if((task.getResult().size()>0) & (NbOfSyllabes>0)) {
                                                    mStatusTextView.setText(getResources().getString(R.string.mywords_status_partlexique_start) + NbOfSyllabes.toString() + getResources().getString(R.string.mywords_status_partlexique_middle) + Integer.toString(task.getResult().size()) + getResources().getString(R.string.mywords_status_partlexique_end));

                                                    if(NbOfSyllabes==4){
                                                        //mStatusTextView.setText(getResources().getString(R.string.mywords_status_partlexique_start) + NbOfSyllabes.toString() + getResources().getString(R.string.mywords_status_partlexique_middle_if4syllabes) + Integer.toString(task.getResult().size()) + getResources().getString(R.string.mywords_status_partlexique_end));
                                                    }
                                                }
                                                else {
                                                    mStatusTextView.setText(getResources().getString(R.string.mywords_status_fulllexique) + task.getResult().size() + getResources().getString(R.string.mywords_status_partlexique_end) );
                                                }


                                                String motsacocher="";

                                                // Pré selection des mots associés précédemment
                                                Boolean isselected = false;

                                                for (int i = 0; i < mSelectedWords.size(); i++) {

                                                    String str1 =mydocument.getId();
                                                    String str2 =mSelectedWords.get(i).getWordId();

                                                    if (mydocument.getId().equals(mSelectedWords.get(i).getWordId()))
                                                    {
                                                        Log.w(TAG, " Élément Coché : " + mydocument.get("label").toString());
                                                        isselected=true;
                                                        motsacocher = motsacocher + " | "+ mydocument.get("label").toString();
                                                        break;
                                                        //mSelectedWords.add(new Word(mydocument.getId(), mydocument.get("label").toString(), (long) mydocument.get("nbsyllabes"), (boolean) isselected)); //Affectation à la nouvelle sélection de mots
                                                    }
                                                }
                                                mWordsList.add(new Word(mydocument.getId(), mydocument.get("label").toString(), (Long) mydocument.get("nbsyllabes"), (boolean) isselected));
                                            }
                                        } else {
                                            Log.w(TAG, "Error getting documents.", task.getException());
                                            mStatusTextView.setText(mStatusTextView.getText()+" : ko1 : " +task.getException());
                                        }
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                        mStatusTextView.setText(mStatusTextView.getText()+" : ko2 : " +task.getException());
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    mStatusTextView.setText(mStatusTextView.getText()+" : ko3 : " +task.getException());
                }
            }
        });
        mSubmitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickCheckBox(int position) {
        //(Cas Check) Si mSelectedWords est vide, on ajoute CheckedWord
        //(Cas UnCheck) Sinon on parse mSelectedWordset et on retire CheckedWord s'il existe
        //(Cas Check) Si aucune suppression on ajoute CheckedWord

        Word CheckedWord = mAdapter.getWordFromPosition(position);
        String action = "";

        if (mSelectedWords.size()==0){
            mSelectedWords.add(CheckedWord);
        }else {
            for (int i = 0; i < mSelectedWords.size(); i++) {
                Word oldword = mSelectedWords.get(i);

                if (oldword.getWordId().equals(CheckedWord.getWordId())) {
                    mSelectedWords.remove(i);
                    action = "suppression";
                }
            }

            if (!action.equals("suppression")) {
                mSelectedWords.add(CheckedWord);
                action = "ajout";
            }
            //showToast("Mot cliqué : " + CheckedWord.getLabel() + "\n mSelectedWords.size() : " + mSelectedWords.size() + "\n Type d'action : " + action);
        }
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


}






//configureOnClickRecyclerView();
 /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
*/