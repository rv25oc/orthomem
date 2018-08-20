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
    private FavoriteIconListener mCallback;

    // - - - - - - - - -
    // GESTION DU VIEWHOLDER
    // - - - - - - - - -

    @NonNull
    @Override
    public ExerciceListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row,viewGroup,false);
        return new ExerciceListViewHolder(itemView);

        //ExerciceListViewHolder mExerciceListViewHolder = new ExerciceListViewHolder(itemView);
        //return mExerciceListViewHolder;
    }

    @Override
    public int getItemCount() {return mExercicesList.size();}

    public Exercice getExerciceFromPosition(int position){return this.mExercicesList.get(position);}


    // - - - - - - - - -
    // GESTION DU LISTENER FAVORITICONE
    // - - - - - - - - -

    public interface FavoriteIconListener {
        void onClickFavoriteIcon(int position);
    }

    public ExerciceListRecyclerViewAdapter(List<Exercice> ExcercicesList, FavoriteIconListener callback) {
        this.mExercicesList = ExcercicesList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceListViewHolder holder, int position) {
        Exercice exercice = mExercicesList.get(position);
        holder.bind(exercice);
        holder.updateWithExercice(exercice, this.mCallback);
    }
}
