package com.utilisateur.orthomem.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.api.UserHelper;
import com.utilisateur.orthomem.model.Exercice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExerciceListRecyclerViewAdapter
        extends RecyclerView.Adapter<ExerciceListRecyclerViewAdapter.ExerciceListViewHolder>
        implements View.OnClickListener {

    private ArrayList<Exercice> mExercicesList;
    private FirebaseAuth mAuth;
    private ImageButton mIconFavorite;

    private WeakReference<FavoriteIconListener> callbackWeakRef;    // 2 - Declare a Weak Reference to our Callback


    public ExerciceListRecyclerViewAdapter(ArrayList<Exercice> ExcercicesList) {
        this.mExercicesList = ExcercicesList;
        //this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }

    // - - - - - - - - -
    // GESTION DU VIEWHOLDER
    // - - - - - - - - -

    @NonNull
    @Override
    public ExerciceListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row, viewGroup, false);
        return new ExerciceListViewHolder(itemView);
        /*
        ExerciceListViewHolder mExerciceListViewHolder = new ExerciceListViewHolder(itemView);
        return mExerciceListViewHolder;
        */
    }


    @Override
    public void onBindViewHolder(@NonNull ExerciceListViewHolder holder, int position) {
        Exercice exercice = mExercicesList.get(position);


        //holder.updateWithExercice(exercice, this.mCallback);

        holder.mIconFavorite.setImageResource(R.drawable.ic_favorite_green_24dp);

        holder.mtextViewExerciceLabel.setText(exercice.getLabel());
        holder.mtextViewExerciceGoal.setText(exercice.getGoal());
/*
        mAuth = FirebaseAuth.getInstance();
        if (UserHelper.isFavorite(mAuth.getCurrentUser().getUid(),exercice.getId())){mIconFavorite.setImageResource(R.drawable.ic_favorite_green_24dp);};
*/
        if (exercice.getExercicewords() != null) {
            holder.mtextViewExerciceNbOfWords.setText("Nombre de mot(s) : " + exercice.getExercicewords().size());
        }
        if (exercice.getCreadate() != null) {
            holder.mtextViewExerciceNbOfWords.append(" | Date de création : " + exercice.getCreadate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return mExercicesList.size();
    }

    public Exercice getExerciceFromPosition(int position) {
        return this.mExercicesList.get(position);
    }

    public void updateWithExercice(Exercice exercice, ExerciceListRecyclerViewAdapter.FavoriteIconListener callback) {
        this.mIconFavorite.setOnClickListener(this);      //3 - Implement Listener on ImageButton
        this.callbackWeakRef = new WeakReference<ExerciceListRecyclerViewAdapter.FavoriteIconListener>(callback);   //4 - Create a new weak Reference to our Listener
    }

    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        ExerciceListRecyclerViewAdapter.FavoriteIconListener callback = callbackWeakRef.get();

        if (callback != null) {
            //callback.onClickFavoriteIcon(getAdapterPosition());
            // toggleicon ?

            mIconFavorite.setSelected(!mIconFavorite.isSelected());

            if (mIconFavorite.isSelected()) {
                //Handle selected state change
                mIconFavorite.setImageResource(R.drawable.ic_favorite_green_24dp);
            } else {
                //Handle de-select state change
                mIconFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }
    }

    public interface FavoriteIconListener {
        void onClickFavoriteIcon(int position);
    }
    // - - - - - - - - -
    // GESTION DU LISTENER FAVORITICONE
    // - - - - - - - - -

    public class ExerciceListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_favicon)
        ImageButton mIconFavorite;   // 1 - Declare our ImageButton
        private TextView mtextViewExerciceLabel;
        private TextView mtextViewExerciceGoal;
        //private ImageButton mIconFavorite;
        private TextView mtextViewExerciceNbOfWords;


        //itemView est la vue correspondante à 1 ligne de la liste
        public ExerciceListViewHolder(View itemView) {
            super(itemView);
            //Associations vues/valeurs
            mtextViewExerciceLabel = (TextView) itemView.findViewById(R.id.exercice_label);
            mIconFavorite = (ImageButton) itemView.findViewById(R.id.icon_favicon);
            mtextViewExerciceGoal = (TextView) itemView.findViewById(R.id.exercice_goal);
            mtextViewExerciceNbOfWords = (TextView) itemView.findViewById(R.id.exercice_nbofwords);
        }
    }


}

/*










 */
