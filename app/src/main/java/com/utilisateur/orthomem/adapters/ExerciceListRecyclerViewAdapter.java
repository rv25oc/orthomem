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
        void onClickFavoriteIcon(int position); // 1 - Create interface for callback
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



/*
    private String id;
    private String label;
    private String goal;
    private List<String> wordids;
    private String ownerid;
    private Listener callback;

    public interface Listener {
        void onDataChanged();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }


    public ExerciceListRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<Exercice> options, Listener callback, String id, String label, String goal, List<String> wordids, String ownerid) {
        super(options);
        this.callback = callback;// 3 - Passing an instance of callback through constructor
        this.id=id;
        this.goal=label;
        this.goal=goal;
        this.wordids=wordids;
        this.ownerid=ownerid;
    }

        //Peupler les cellules de la ligne avec les valeurs de chaque Exercice en appellant la fonction dédiée de l'objet Exercice
    @Override
    public void onBindViewHolder(@NonNull ExerciceListViewHolder holder, int position, @NonNull Exercice exercice) {
        holder.bind(exercice);
        holder.updateWithExercice(exercice);// 4 - Passing an instance of callback through each ViewHolder
    }
*/
