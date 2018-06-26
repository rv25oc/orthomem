// MyListsFragment.java
package com.utilisateur.orthomem.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public static final int GAME_ACTIVITY_REQUEST_CODE = 25;
    private RecyclerView mRecyclerView;
    private ExerciceListRecyclerViewAdapter mAdapter;
    private List<Exercice> mExos = new ArrayList<>();


    public MyListsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result=inflater.inflate(R.layout.activity_mylists, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        mRecyclerView = (RecyclerView) result.findViewById(R.id.myListsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));  //Définit l'agencement des cellules, ici de façon verticale, comme une ListView
        //recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),2)); //Possibilité d'adapter en vue de type grille comme une RecyclerView, avec 2 cellules par ligne

        MakeFakeExos();

        mAdapter = new ExerciceListRecyclerViewAdapter(mExos, this);
        mRecyclerView.setAdapter(mAdapter);     //puis créer un MyAdapter, lui fournir notre liste d'exercicies et le callback. Cet Adapter servira à remplir notre recyclerview


        // Calling the method that configuring click on RecyclerView
        this.configureOnClickRecyclerView();

        return result;

    }

    private void MakeFakeExos() {
        List<String> mWordsList1 = new ArrayList<>(Arrays.asList("Mot1", "Mot2", "Mot3"));
        mExos.add(new Exercice("E1","Exo 1","Objectif pédagogique : Obj 1", mWordsList1));

        List<String> mWordsList2 = new ArrayList<>(Arrays.asList("Mot11", "Mot22", "Mot333", "Mot444", "Mot555"));
        mExos.add(new Exercice("E2", "Exo 2","Objectif pédagogique : Obj 1", mWordsList2));
    }

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

                        Toast.makeText(getContext(), "You clicked on user : "+clickedExercice.getExerciceLabel(), Toast.LENGTH_SHORT).show();


                        startMyListActivity(clickedExercice.getExerciceId());// Lancer l'activité MyList en passant l'id de l'exercice comme parametre
                    }
                });
    }


    // 2 - Because of implementing the interface, we have to override its method

    @Override
    public void onClickFavoriteIcon(int position) {
        Exercice FavoriteExercice = mAdapter.getExerciceFromPosition(position);
        Toast.makeText(getContext(), "You are trying to favorite an exercice : "+FavoriteExercice.getExerciceLabel(), Toast.LENGTH_SHORT).show();
    }


    private void startMyListActivity(String exerciceid){
        //https://zocada.com/using-intents-extras-pass-data-activities-android-beginners-guide/

        //Adding key value pairs to the bundle
        Bundle myBundle = new Bundle();
        myBundle.putString("EXERCICEID", exerciceid); // pass your values and retrieve them in the other Activity using a Bundle

        Intent myIntent = new Intent(this.getActivity(), MyListActivity.class);
        //attach the bundle to the Intent object
        myIntent.putExtras(myBundle);

        startActivity(myIntent);
    }
}