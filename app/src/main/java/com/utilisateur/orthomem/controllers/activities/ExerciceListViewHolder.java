package com.utilisateur.orthomem.controllers.activities;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.model.Exercice;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class ExerciceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mtextViewExerciceLabel;
    private TextView mtextViewExerciceGoal;
    private TextView mtextViewExerciceNbOfWords;
    //private ImageButton mIconFavorite;


    @BindView(R.id.icon_favicon) ImageButton mIconFavorite;    // 1 - Declare our ImageButton
    private WeakReference<ExerciceListRecyclerViewAdapter.FavoriteIconListener> callbackWeakRef;    // 2 - Declare a Weak Reference to our Callback

    public void updateWithExercice(Exercice exercice, ExerciceListRecyclerViewAdapter.FavoriteIconListener callback) {
        this.mIconFavorite.setOnClickListener(this);      //3 - Implement Listener on ImageButton
        this.callbackWeakRef = new WeakReference<ExerciceListRecyclerViewAdapter.FavoriteIconListener>(callback);   //4 - Create a new weak Reference to our Listener
    }

    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        ExerciceListRecyclerViewAdapter.FavoriteIconListener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickFavoriteIcon(getAdapterPosition());
    }

    //itemView est la vue correspondante à 1 ligne de la liste
    public ExerciceListViewHolder(View itemView) {
        super(itemView);
        //Associations vues/valeurs
        mtextViewExerciceLabel = (TextView) itemView.findViewById(R.id.exercice_label);
        mIconFavorite = (ImageButton) itemView.findViewById(R.id.icon_favicon);
        mtextViewExerciceGoal = (TextView) itemView.findViewById(R.id.exercice_goal);
        mtextViewExerciceNbOfWords = (TextView) itemView.findViewById(R.id.exercice_nbofwords);
    }

    //puis ajouter une fonction pour remplir la cellule/les textViews en fonction d'un Exercice
    public void bind(Exercice exercice){
        mtextViewExerciceLabel.setText(exercice.getLabel());
        mtextViewExerciceGoal.setText(exercice.getGoal());
        if (exercice.getExercicewords() != null){mtextViewExerciceNbOfWords.setText("Nombre de mot(s) : "+exercice.getExercicewords().size());}
        if (exercice.getCreadate() != null){mtextViewExerciceNbOfWords.append(" | Date de création : "+exercice.getCreadate().toString());}
    }
    /*

    public void updateWithExercice(Exercice exercice) {
        //this.mIconFavorite.setOnClickListener(this);
    }


     */
}
