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
    @NonNull
    @Override
    public WordListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_recyclerview_row,viewGroup,false);
        return new WordListViewHolder(itemView);

        //WordListViewHolder mWordListViewHolder = new WordListViewHolder(itemView);
        //return mWordListViewHolder;
    }

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


    public WordListRecyclerViewAdapter(List<Word> WordsList, CheckBoxListener callback) {
        this.mWordsList = WordsList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }


}




/*
    public List<Word> getSelectedItem(){
        for (int i =0; i < mWordsList.size(); i++){
            Word word = mWordsList.get(i);
            if (word.isSelected()){
                mWordsList.add(word);
            }
        }
        return mWordsList;
    }

    public void setSelectedItem() {

        for (int i = 0; i < mOldList.size(); i++) {
            if (mydocument.getId().equals(mOldList.get(i)))
            {
                //COCHER LA LIGNE
                //mCheckBox.setSelected(true);
                Log.w(TAG, " Élément Coché : " + mydocument.get("label").toString());
            }
        }
    }
    */