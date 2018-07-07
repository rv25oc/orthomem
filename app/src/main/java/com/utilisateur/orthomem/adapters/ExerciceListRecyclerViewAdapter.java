package com.utilisateur.orthomem.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.activities.ExerciceListViewHolder;
import com.utilisateur.orthomem.model.Exercice;

import java.util.List;

public class ExerciceListRecyclerViewAdapter extends RecyclerView.Adapter<ExerciceListViewHolder> {

    private List<Exercice> mExercicesList;


    // - - - - - - - - -
    // GESTION DU VIEWHOLDER
    // - - - - - - - - -
    //cette fonction permet de créer les viewHolder et d'indiquer la vue à "inflater" (à partir des layout xml)
    @NonNull
    @Override
    public ExerciceListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row,viewGroup,false);
        ExerciceListViewHolder mExerciceListViewHolder = new ExerciceListViewHolder(itemView);
        return mExerciceListViewHolder;
    }

    //Peupler les cellules de la ligne avec les valeurs de chaque Exercice en appellant la fonction dédiée de l'objet Exercice
    @Override
    public void onBindViewHolder(@NonNull ExerciceListViewHolder holder, int position) {
        Exercice exercice = mExercicesList.get(position);
        holder.bind(exercice);
        holder.updateWithExercice(exercice, this.mCallback);// 4 - Passing an instance of callback through each ViewHolder
    }

    @Override
    public int getItemCount() {
        return mExercicesList.size();
    }


    public Exercice getExerciceFromPosition(int position){
        return this.mExercicesList.get(position);
    }

    // - - - - - - - - -
    // GESTION DU LISTENER
    // - - - - - - - - -


    public interface FavoriteIconListener {
        void onClickFavoriteIcon(int position); // 1 - Create interface for callback
    }

    private final FavoriteIconListener mCallback; // 2 - Declaring callback


    //ajouter un constructeur prenant en entrée une liste + le callback
    public ExerciceListRecyclerViewAdapter(List<Exercice> ExcercicesList, FavoriteIconListener callback) {
        this.mExercicesList = ExcercicesList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }
}
