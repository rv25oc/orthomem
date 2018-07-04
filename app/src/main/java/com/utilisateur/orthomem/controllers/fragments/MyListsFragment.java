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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.controllers.activities.MyListActivity;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;


public class MyListsFragment extends Fragment
            /*implements View.OnClickListener*/
            implements ExerciceListRecyclerViewAdapter.FavoriteIconListener {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 25;

    private static final String TAG = "FavoriteFragTAG init";
    private TextView mStatusTextView;
    private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private List<Exercice> mFakeExos = new ArrayList<>();
    private List<Exercice> mExos = new ArrayList<>();
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

        mStatusTextView = view.findViewById(R.id.mylists_status);

        mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();

        //FirebaseUser myUser = mAuth.getCurrentUser();
        //this.updateUI(myUser);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.myListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));  //Définit l'agencement des cellules, ici de façon verticale, comme une ListView
        //recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),2)); //Possibilité d'adapter en vue de type grille comme une RecyclerView, avec 2 cellules par ligne

        MakeExos();

        mAdapter = new ExerciceListRecyclerViewAdapter(mExos, this);
        mRecyclerView.setAdapter(mAdapter);     //puis créer un MyAdapter, lui fournir notre liste d'exercicies et le callback. Cet Adapter servira à remplir notre recyclerview

        // Calling the method that configuring click on RecyclerView
        configureOnClickRecyclerView();
/*
        MakeFakeExos();

        mAdapter = new ExerciceListRecyclerViewAdapter(mFakeExos, this);
        mRecyclerView.setAdapter(mAdapter);     //puis créer un MyAdapter, lui fournir notre liste d'exercicies et le callback. Cet Adapter servira à remplir notre recyclerview


        // Calling the method that configuring click on RecyclerView
       configureOnClickRecyclerView();
*/

    }

    private void MakeExos() {

        mBdd.collection("exercices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot mydocument : task.getResult()) {
                                Log.d(TAG, mydocument.getId() + " => " + mydocument.getId() + " | " + mydocument.getData());
                                mydocument.getData();

                                mExos.add(mydocument.toObject(Exercice.class));

                               // Stockage de l'Id de l'exercice récupéré depuis Firebase
                                String myId = mydocument.getId();
                                final Exercice myExercice = mExos.get(mExos.size()-1);
                                myExercice.setId(myId);

                               //mExos.add(new Exercice(document.getId(),document.get("label").toString(),document.get("goal").toString()));


                                // Stockage de la liste de mots
                                mBdd.collection("exercices").document(mydocument.getId()).collection("exercicewords")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                    if (task2.isSuccessful()) {
                                                        ArrayList<String> myWordsList = new ArrayList<String>();

                                                        for (DocumentSnapshot mydocument2 : task2.getResult()) {


                                                            mStatusTextView.setText(mStatusTextView.getText()+" | "+mydocument2.getId());
                                                            myWordsList.add(mydocument2.getId());
                                                        }
                                                        myExercice.setExercicewords(myWordsList);
                                                    }
                                                 else {
                                                    Log.w(TAG, "Error getting documents2.", task2.getException());
                                            }
                                        }
                                });
                            mStatusTextView.setText("ok");

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            mStatusTextView.setText("ko");
                        }
                    }
                });
    }
/*
    private void MakeFakeExos() {
        List<String> mWordsList1 = new ArrayList<>(Arrays.asList("Mot1", "Mot2", "Mot3"));
        mFakeExos.add(new Exercice("E1","Exo 1","Objectif pédagogique : Obj 1", mWordsList1));

        List<String> mWordsList2 = new ArrayList<>(Arrays.asList("Mot11", "Mot22", "Mot333", "Mot444", "Mot555"));
        mFakeExos.add(new Exercice("E2", "Exo 2","Objectif pédagogique : Obj 1", mWordsList2));
    }
*/
/*
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);    // 1 - Declare our interface that will be implemented by any container activity
    }
*/


    // --------------
    // ACTIONS
    // --------------


    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.recyclerview_row)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // 1 - Get Exercice from adapter
                        Exercice clickedExercice = mAdapter.getExerciceFromPosition(position);
                        Toast.makeText(getContext(), "You clicked on user : "+clickedExercice.getId(), Toast.LENGTH_SHORT).show();
                        //startMyListActivity(clickedExercice.getId());// Lancer l'activité MyList en passant l'id de l'exercice comme paramètre

                        startMyListActivity(clickedExercice);// Lancer l'activité MyList en passant l'exercice comme paramètre
                    }
                });
    }


    // 2 - Because of implementing the interface, we have to override its method

    @Override
    public void onClickFavoriteIcon(int position) {
        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);
        Toast.makeText(getContext(), "You are trying to favorite an exercice : "+FavoriteExercice.getLabel(), Toast.LENGTH_SHORT).show();
    }


    private void startMyListActivity(Exercice exercice){ //old = startMyListActivity(String exerciceid)
/*
        //https://zocada.com/using-intents-extras-pass-data-activities-android-beginners-guide/
        //Adding key value pairs to the bundle
        Bundle myBundle = new Bundle();
        myBundle.putString("EXERCICEID", exerciceid); // pass your values and retrieve them in the other Activity using a Bundle

        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        //attach the bundle to the Intent object
        myIntent.putExtras(myBundle);
        startActivity(myIntent);
 */


        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        //attach the bundle to the Intent object
            //myIntent.putExtras(myBundle);
        myIntent.putExtra("EXERCICE", exercice);
        startActivity(myIntent);

    }
}
