package com.utilisateur.orthomem.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.activities.WordListViewHolder;
import com.utilisateur.orthomem.model.Word;

import java.util.List;

public class WordListRecyclerViewAdapter extends RecyclerView.Adapter<WordListViewHolder> {

    private List<Word> mWordsList;

    // - - - - - - - - -
    // GESTION DU VIEWHOLDER
    // - - - - - - - - -
    //cette fonction permet de créer les viewHolder et d'indiquer la vue à "inflater" (à partir des layout xml)
    @NonNull
    @Override
    public WordListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_recyclerview_row,viewGroup,false);
        WordListViewHolder mWordListViewHolder = new WordListViewHolder(itemView);
        return mWordListViewHolder;
    }

    //Peupler les cellules de la ligne avec les valeurs de chaque Exercice en appellant la fonction dédiée de l'objet Exercice
    @Override
    public void onBindViewHolder(@NonNull WordListViewHolder holder, int position) {
        Word word = mWordsList.get(position);
        holder.bind(word);
        holder.updateWithWord(word, this.mCallback);// 4 - Passing an instance of callback through each ViewHolder
    }

    @Override
    public int getItemCount() {
        return mWordsList.size();
    }


    public Word getWordFromPosition(int position){
        return this.mWordsList.get(position);
    }

    // - - - - - - - - -
    // GESTION DU LISTENER
    // - - - - - - - - -


    public interface CheckBoxListener {
        void onClickCheckBox(int position); // 1 - Create interface for callback
    }

    private final CheckBoxListener mCallback; // 2 - Declaring callback


    //ajouter un constructeur prenant en entrée une liste + le callback
    public WordListRecyclerViewAdapter(List<Word> WordsList, CheckBoxListener callback) {
        this.mWordsList = WordsList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }

    public List<Word> getSelectedItem(){
        for (int i =0; i < mWordsList.size(); i++){
            Word word = mWordsList.get(i);
            if (word.isSelected()){
                mWordsList.add(word);
            }
        }
        return mWordsList;
    }
}
