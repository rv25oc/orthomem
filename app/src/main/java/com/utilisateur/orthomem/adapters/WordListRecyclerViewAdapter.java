package com.utilisateur.orthomem.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.controllers.activities.ViewHolderWordList;
import com.utilisateur.orthomem.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordListRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolderWordList> {

    private List<Word> mWordsList;

    // - - - - - - - - -
    // GESTION DU VIEWHOLDER
    // - - - - - - - - -
    @NonNull
    @Override
    public ViewHolderWordList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_word_row,viewGroup,false);
        return new ViewHolderWordList(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWordList holder, int position) {
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

    public interface CheckBoxListener {
        void onClickCheckBox(int position); // 1 - Create interface for callback
    }

    private final CheckBoxListener mCallback; // 2 - Declaring callback


    public WordListRecyclerViewAdapter(ArrayList<Word> WordsList, CheckBoxListener callback) {
        this.mWordsList = WordsList;
        this.mCallback = callback;// 3 - Passing an instance of callback through constructor
    }
}