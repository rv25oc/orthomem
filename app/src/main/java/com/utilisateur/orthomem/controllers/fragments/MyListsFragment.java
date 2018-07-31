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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.controllers.activities.MyListActivity;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MyListsFragment extends Fragment
            /*implements View.OnClickListener*/
            implements ExerciceListRecyclerViewAdapter.FavoriteIconListener {

    /*
    *  1. Se connecte à Firebase
    *  2. Liste et stocke les Exercices en local
    *  3. Affiche les données dans un RecyclerView
    *  4. Envoie l'Exercice cliqué à l'activité MyList via Bundle
    */



    private static final String TAG = "";
    private String mymsg="mymsg init";
    private TextView mStatusTextView;
    private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private List<Exercice> mExos = new ArrayList<>();
    private Exercice mExercice = new Exercice();
    private List<String> mWordsList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;


    public MyListsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_mylists, container, false);
     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mymsg="";
        mStatusTextView = view.findViewById(R.id.mylists_status);

        //mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();
        //MakeFakeExos();
        MakeExos();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.myListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ExerciceListRecyclerViewAdapter(mExos, this);//puis créer un MyAdapter, lui fournir notre liste d'exercices et le callback. Cet Adapter servira à remplir notre recyclerview
        mRecyclerView.setAdapter(mAdapter);

        // Calling the method that configuring click on RecyclerView
       configureOnClickRecyclerView();

    }



    private void MakeExos() {

        mBdd.collection("exercices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                        if (task1.isSuccessful()) {
                            for (DocumentSnapshot mydocument : task1.getResult()) {
                                Log.w(TAG, mydocument.getId() + " => | " + mydocument.getData());

                                mydocument.getData();

                                // Création des objects locaux METHODE 2
                                mExos.add(new Exercice(mydocument.getId(),mydocument.get("label").toString(),mydocument.get("goal").toString()));
/*
                                // Création des objects locaux METHODE 1
                                mExos.add(mydocument.toObject(Exercice.class));
                                String myId = mydocument.getId();
                                myExercice.setId(myId); // Affectation de l'id en manuel car toObject() de la METHODE 1 ne le fait visiblement pas
*/
                                final Exercice myExercice = mExos.get(mExos.size()-1);


                                // Stockage de la liste de mots dans l'exercice courant
                                mBdd.collection("exercices").document(mydocument.getId()).collection("exercicewords")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                        if (task2.isSuccessful()) {
                                            ArrayList<String> myWordsList = new ArrayList<String>();

                                            int test =0;

                                            for (DocumentSnapshot mydocument2 : task2.getResult()) {

                                                myWordsList.add(mydocument2.getId());

                                                //VERSION KO
                                                //test++;
                                                //mStatusTextView.setText(mStatusTextView.getText()+" . "+test);

                                                mStatusTextView.setText(mStatusTextView.getText()+" | "+mydocument2.getId());
                                            }
                                            myExercice.setExercicewords(myWordsList);
                                        }
                                        else {
                                            Log.w(TAG, "Error getting documents2.", task2.getException());
                                        }
                                    }
                                });

                                mymsg="mExos.size()="+mExos.size()+"| mWordsList.size()="+mWordsList.size();
                                Log.w(TAG, "(task1) Getting Exercices  ok. mExos.size()="+ mExos.size()+"| mWordsList.size()="+mWordsList.size());

                                mStatusTextView.setText(mStatusTextView.getText()+" : ok");
                            }

                            Toast.makeText(getContext(),mymsg, Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(TAG, "(task1) Error getting Exercices  ko.", task1.getException());
                            mStatusTextView.setText("ko");
                        }
                    }
                });
    }
/*
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);    // 1 - Declare our interface that will be implemented by any container activity
    }
*/


    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.recyclerview_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // 1 - Get Exercice from adapter
                        Exercice clickedExercice = mAdapter.getExerciceFromPosition(position);
                        Toast.makeText(getContext(), "You clicked on Exercice : "+clickedExercice.getId(), Toast.LENGTH_SHORT).show();
                        //startMyListActivity(clickedExercice.getId());// Lancer l'activité MyList en passant l'id de l'exercice comme paramètre

                        startMyListActivity(clickedExercice);// Lancer l'activité MyList en passant l'exercice comme paramètre
                    }
                });
    }

    private void MakeFakeExos() {
        List<String> mWordsList1 = new ArrayList<>(Arrays.asList("Mot1", "Mot2", "Mot3"));
        mExos.add(new Exercice("E1","Exo 1","Objectif pédagogique : Obj 1", mWordsList1));

        List<String> mWordsList2 = new ArrayList<>(Arrays.asList("Mot11", "Mot22", "Mot33", "Mot44", "Mot55"));
        mExos.add(new Exercice("E22", "Exo 22","Objectif pédagogique : Obj 22", mWordsList2));
        mExos.add(new Exercice("E333","Exo 333","Objectif pédagogique : Obj 333"));
    }

    @Override
    public void onClickFavoriteIcon(int position) {
        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);
        Toast.makeText(getContext(), "You are trying to favorite an exercice : "+FavoriteExercice.getLabel(), Toast.LENGTH_SHORT).show();
    }


    private void startMyListActivity(Exercice exercice){ //old = startMyListActivity(String exerciceid)
        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        //attach the bundle to the Intent object
            //myIntent.putExtras(myBundle);
        myIntent.putExtra("EXERCICE", exercice);
        startActivity(myIntent);

    }
}
