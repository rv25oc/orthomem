package com.utilisateur.orthomem.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.activities.ViewHolderExerciceList;
import com.utilisateur.orthomem.model.Exercice;

import java.util.ArrayList;

public class ExerciceListRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolderExerciceList> {

    private ArrayList<Exercice> mExercicesList;
    private FavoriteIconListener mCallback;

    @NonNull
    @Override
    public ViewHolderExerciceList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_exercice_row,viewGroup,false);
        return new ViewHolderExerciceList(itemView);
    }

    @Override
    public int getItemCount() {return mExercicesList.size();}

    public Exercice getExerciceFromPosition(int position){return this.mExercicesList.get(position);}

    public interface FavoriteIconListener {
        void onClickFavoriteIcon(int position);
    }

    public ExerciceListRecyclerViewAdapter(ArrayList<Exercice> ExcercicesList, FavoriteIconListener callback) {
        this.mExercicesList = ExcercicesList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderExerciceList holder, int position) {
        Exercice exercice = mExercicesList.get(position);
        holder.bind(exercice);
        holder.updateWithExercice(exercice, this.mCallback);
    }
}
