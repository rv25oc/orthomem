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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.api.UserHelper;
import com.utilisateur.orthomem.controllers.activities.MyListActivity;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Date;


public class MyListsFragment extends Fragment
            implements ExerciceListRecyclerViewAdapter.FavoriteIconListener {

    /*
    *  1. Se connecte à Firebase
    *  2. Liste et stocke les Exercices en local
    *  3. Affiche les données dans un RecyclerView
    *  4. Envoie l'Exercice cliqué à l'activité MyList via Extra
    */


    private FirebaseAuth mAuth;
    private static final String TAG = "";
    private TextView mStatusTextView;
    private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private ArrayList<Exercice> mExos;
    private FirebaseFirestore mBdd;


    public MyListsFragment() {/*Required empty public constructor*/}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mylists, container, false);
     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStatusTextView = view.findViewById(R.id.mylists_status);
        mBdd = FirebaseFirestore.getInstance();

        mExos = LoadExos(); //Charger la RecyclerView avec la collection d'Exercices stockée sur Firebase

        Log.w(TAG, "mExos " + mExos.size());

        mRecyclerView = view.findViewById(R.id.myListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ExerciceListRecyclerViewAdapter(mExos, this);
        mRecyclerView.setAdapter(mAdapter);

        configureOnClickRecyclerView();
    }

    private ArrayList<Exercice> LoadExos() {

        final ArrayList<Exercice> myExos = new ArrayList<>();

        mBdd.collection("exercices").orderBy("creadate", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                        if (task1.isSuccessful()) {
                            for (final DocumentSnapshot mydocument : task1.getResult()) {
                                Log.w(TAG, mydocument.getId() + " => | " + mydocument.getData());
                                mydocument.getData();

                                //final Exercice myExercice = mExos.get(mExos.size()-1);
                                final ArrayList<String> myIdsList = new ArrayList<>();

                                // Stockage de la liste de mots dans l'exercice courant
                                mBdd.collection("exercices").document(mydocument.getId()).collection("exercicewords")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            for (DocumentSnapshot mydocument2 : task2.getResult()) {
                                                mydocument2.getData();
                                                Log.w(TAG, "getId() : "+mydocument2.getId() + " => | mydocument2.get(field wordid) :" + mydocument2.get("wordid").toString());

                                                myIdsList.add(mydocument2.get("wordid").toString());
                                            }

                                            //TEST DE FAVORIS

                                            Date mydate = mydocument.getDate("creadate");
                                            myExos.add(new Exercice(mydocument.getId(),mydocument.get("label").toString(),mydocument.get("goal").toString(), mydate, myIdsList));
                                            mStatusTextView.setText(myExos.size()+getResources().getString(R.string.mylists_nbofexercices));
                                            Log.w(TAG, " mydocument2 myIdsList.size()  : "+myIdsList.size());
                                            mAdapter.notifyDataSetChanged();
                                        }
                                        else {
                                            Log.w(TAG, "(task2) Error getting DocumentSnapshot ExerciceWords", task2.getException());
                                            mStatusTextView.setText("ko2");
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.w(TAG, "(task1) Error getting DocumentSnapshot Exercices.", task1.getException());
                            mStatusTextView.setText("ko1");
                        }
                    }
                });

        return myExos;
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.recyclerview_exercice_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Exercice clickedExercice = mAdapter.getExerciceFromPosition(position);
                        //Toast.makeText(getContext(), "You clicked on Exercice : "+clickedExercice.getId(), Toast.LENGTH_SHORT).show();
                        startMyListActivity(clickedExercice);// Lancer l'activité MyList en passant l'exercice comme paramètre
                    }
                });
    }

    @Override
    public void onClickFavoriteIcon(int position) {

        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            UserHelper.toogleFavorite(mAuth.getCurrentUser().getUid(), FavoriteExercice.getId());
        }
    }

    private void startMyListActivity(Exercice exercice){
        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        myIntent.putExtra("EXERCICE", exercice);
        startActivity(myIntent);
    }
}
