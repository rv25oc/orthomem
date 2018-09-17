// FavoriteFragment.java
package com.utilisateur.orthomem.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteFragment extends Fragment
        implements ExerciceListRecyclerViewAdapter.FavoriteIconListener {

    private static final String TAG = "FavoriteFragTAG init";

    @BindView(R.id.favorites_title)
    TextView mTitleTextView;
    @BindView(R.id.favorites_status)
    TextView mStatusTextView;
    @BindView(R.id.favorites_recyclerview)
    RecyclerView mRecyclerView;


    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;
    //private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private ArrayList<Exercice> mFavoritesExos;
    private String mUserID = "";
    private String mUserLabel = "";

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
        //return inflater.inflate(R.layout.activity_favorite, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBdd = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //String str = (String) UserHelper.getUser(mAuth.getUid()).getResult().getData().get("label").toString();
        //mTitleTextView.append(str);

        mFavoritesExos = LoadFavoritesExos(); //Charger la RecyclerView avec la collection d'Exercices stockée sur Firebase

        Log.w(TAG, "mFavoriteExos " + mFavoritesExos.size());

        //mRecyclerView = view.findViewById(R.id.favorites_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ExerciceListRecyclerViewAdapter(mFavoritesExos);
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
                                                                        Log.w(TAG, "getId() : " + mydocument3.getId() + " => | mydocument2.get(field wordid) :" + mydocument3.get("wordid").toString());
                                                                        myIdsList.add(mydocument3.get("wordid").toString());
                                                                        mStatusTextView.append(" | " + mydocument3.get("wordid").toString().substring(0, 3));
                                                                    }
                                                                    Log.w(TAG, " mydocument2 myIdsList.size()  : " + myIdsList.size());
                                                                } else {
                                                                    Log.w(TAG, "(task3) Error getting DocumentSnapshot ExerciceWords", task3.getException());
                                                                    mStatusTextView.setText("ko3");
                                                                }
                                                            }
                                                        });

                                                        myExos.add(new Exercice(mydocument2.getId(), mydocument2.get("label").toString(), mydocument2.get("goal").toString(), myIdsList));
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
        ItemClickSupport.addTo(mRecyclerView, R.layout.recyclerview_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Exercice clickedExercice = mAdapter.getExerciceFromPosition(position);
                        Toast.makeText(getContext(), "You clicked on Exercice : " + clickedExercice.getId(), Toast.LENGTH_SHORT).show();
                        startMyListActivity(clickedExercice);// Lancer l'activité MyList en passant l'exercice comme paramètre
                    }
                });
    }

    @Override
    public void onClickFavoriteIcon(int position) {

        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);
        Toast.makeText(getContext(), "You are trying to favorite an exercice : " + FavoriteExercice.getLabel(), Toast.LENGTH_SHORT).show();

        String mystr = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_white_24dp, null).toString();
        Toast.makeText(getContext(), "myString : " + mystr, Toast.LENGTH_SHORT).show();
        if (mAuth.getCurrentUser() != null) {
            //UserHelper.toogleFavorite(mAuth.getCurrentUser().getUid(), FavoriteExercice.getId());
        }
    }

    private void startMyListActivity(Exercice exercice) {
        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        myIntent.putExtra("EXERCICE", exercice);
        startActivity(myIntent);

    }
}