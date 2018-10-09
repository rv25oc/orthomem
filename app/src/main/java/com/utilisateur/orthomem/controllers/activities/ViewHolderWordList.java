package com.utilisateur.orthomem.controllers.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.WordListRecyclerViewAdapter;
import com.utilisateur.orthomem.model.Word;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class ViewHolderWordList extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mtextViewWordId;
    private TextView mtextViewWordLabel;
    @BindView(R.id.word_checkbox) CheckBox mCheckboxWord;    // 1 - Declare our ImageButton
    private WeakReference<WordListRecyclerViewAdapter.CheckBoxListener> callbackWeakRef;    // 2 - Declare a Weak Reference to our Callback

    public void updateWithWord(Word word, WordListRecyclerViewAdapter.CheckBoxListener callback) {

        this.mCheckboxWord.setOnClickListener(this);      //3 - Implement Listener on ImageButton
        this.callbackWeakRef = new WeakReference<WordListRecyclerViewAdapter.CheckBoxListener>(callback);   //4 - Create a new weak Reference to our Listener
    }

    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        WordListRecyclerViewAdapter.CheckBoxListener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickCheckBox(getAdapterPosition());
    }

    //itemView est la vue correspondante à 1 ligne de la liste
    public ViewHolderWordList(View itemView) {
        super(itemView);

        //Associations vues/valeurs
        mtextViewWordId = itemView.findViewById(R.id.word_id);
        mtextViewWordLabel = itemView.findViewById(R.id.word_label);
        mCheckboxWord = itemView.findViewById(R.id.word_checkbox);
    }

    //puis ajouter une fonction pour remplir la cellule/les textViews en fonction d'un Mot
    public void bind(Word word){
        mtextViewWordLabel.setText(word.getLabel());
        mCheckboxWord.setChecked(word.isSelected());
        mtextViewWordId.setText(word.getWordId());
    }
}
