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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.api.UserHelper;
import com.utilisateur.orthomem.controllers.activities.MyListActivity;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Date;


public class FavoriteFragment extends Fragment
        implements ExerciceListRecyclerViewAdapter.FavoriteIconListener {

    private static final String TAG = "FavoriteFragTAG init";
    private TextView mStatusTextView;
    private TextView mTitleTextView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;
    private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private ArrayList<Exercice> mFavoritesExos;
    private String mUserID = "";
    private String mUserLabel = "";

    public FavoriteFragment() {/* Required empty public constructor*/}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = view.findViewById(R.id.favorites_title);
        mStatusTextView = view.findViewById(R.id.favorites_status);
        mBdd = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mFavoritesExos = LoadFavoritesExos(); //Charger la RecyclerView avec la collection d'Exercices stockée sur Firebase
        Log.w(TAG, "mFavoriteExos " + mFavoritesExos.size());

        mRecyclerView = view.findViewById(R.id.favorites_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ExerciceListRecyclerViewAdapter(mFavoritesExos, this);
        mRecyclerView.setAdapter(mAdapter);

        configureOnClickRecyclerView();
    }


    private ArrayList<Exercice> LoadFavoritesExos() {

        final ArrayList<Exercice> myExos = new ArrayList<>();

        UserHelper.getFavorites(mAuth.getUid())
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                        if (task1.isSuccessful()) {
                            for (final DocumentSnapshot mydocument1 : task1.getResult()) {
                                Log.w(TAG, mydocument1.getId() + " => | " + mydocument1.getData());
                                mydocument1.getData();


                                // Stockage de la liste de exercices (complets) issues des ids Favorites
                                mBdd.collection("exercices").document(mydocument1.get("exerciceid").toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                                if (task2.isSuccessful()) {
                                                    DocumentSnapshot mydocument2 = task2.getResult();
                                                    if (mydocument2.exists()) {
                                                        mydocument2.getData();
                                                        Log.w(TAG, "getId() : " + mydocument2.getId() + " => | document.get(field exercice.getId() :" + mydocument2.getId());

                                                        //final Exercice myExercice = mExos.get(mExos.size()-1);
                                                        final ArrayList<String> myIdsList = new ArrayList<>();
                                                        // Stockage de la liste de mots dans l'exercice courant
                                                        mBdd.collection("exercices").document(mydocument1.get("exerciceid").toString()).collection("exercicewords")
                                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                                                                if (task3.isSuccessful()) {
                                                                    for (DocumentSnapshot mydocument3 : task3.getResult()) {
                                                                        mydocument3.getData();
                                                                        Log.w(TAG, "getId() : " + mydocument3.getId() + " => | mydocument3.get(field wordid) :" + mydocument3.get("wordid").toString());
                                                                        myIdsList.add(mydocument3.get("wordid").toString());
                                                                        //mStatusTextView.append(" | " + mydocument3.get("wordid").toString().substring(0, 3));
                                                                    }
                                                                    Log.w(TAG, " mydocument2 myIdsList.size()  : " + myIdsList.size());
                                                                    mAdapter.notifyDataSetChanged();
                                                                } else {
                                                                    Log.w(TAG, "(task3) Error getting DocumentSnapshot ExerciceWords", task3.getException());
                                                                    mStatusTextView.setText("ko3");
                                                                }
                                                            }
                                                        });
                                                        Date mydate = mydocument2.getDate("creadate");
                                                        myExos.add(new Exercice(mydocument2.getId(), mydocument2.get("label").toString(), mydocument2.get("goal").toString(), mydate, myIdsList));
                                                        //Toast.makeText(getContext(), "myExos.size() =  " + myExos.size(), Toast.LENGTH_SHORT).show();
                                                        mStatusTextView.setText(myExos.size() + getResources().getString(R.string.mylists_nbofexercices));
                                                    }
                                                } else {
                                                    Log.w(TAG, "(task2) Error getting DocumentSnapshot Exercices", task2.getException());
                                                    mStatusTextView.setText("ko2");
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.w(TAG, "(task1) Error getting QuerySnapshot Favorites.", task1.getException());
                            mStatusTextView.setText("ko1");
                        }
                    }
                });
        return myExos;
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.recyclerview_exercice_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Exercice clickedExercice = mAdapter.getExerciceFromPosition(position);
                        //Toast.makeText(getContext(), "You clicked on Exercice : " + clickedExercice.getId(), Toast.LENGTH_SHORT).show();
                        startMyListActivity(clickedExercice);// Lancer l'activité MyList en passant l'exercice comme paramètre
                    }
                });
    }

    @Override
    public void onClickFavoriteIcon(int position) {

        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);

        if (mAuth.getCurrentUser() != null) {
            UserHelper.toogleFavorite(mAuth.getCurrentUser().getUid(), FavoriteExercice.getId());
        }
    }

    private void startMyListActivity(Exercice exercice) {
        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        myIntent.putExtra("EXERCICE", exercice);
        startActivity(myIntent);
    }
}