package com.utilisateur.orthomem.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.model.Word;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class MultiSelectionAdapter extends RecyclerView.Adapter {
    //http://www.sunilandroid.com/2016/12/single-and-multiple-item-selection-in.html

    private List<Word> mWordsList;
    private Context mcontext;

    public MultiSelectionAdapter(List<Word> wordsList, Context context)  {
        mWordsList = wordsList;
        mcontext = context;
    }

    /*
    public interface FavoriteIconListener {
        void onClickFavoriteIcon(int position); // 1 - Create interface for callback
    }

    private final FavoriteIconListener mCallback; // 2 - Declaring callback

    public MultiSelectionAdapter(List<Word> wordsList, FavoriteIconListener callback) {
        mWordsList = wordsList;
        this.mCallback = callback;
    }
*/

    @Override
    public int getItemCount() {
        return mWordsList.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_recyclerview_row, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Word word = mWordsList.get(position);
        initializeViews(word, holder, position);
    }


    private void initializeViews(final Word word, final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).mword_id.setText(word.getId());
        ((ItemViewHolder)holder).mword_label.setText(word.getLabel());
        ((ItemViewHolder)holder).mword_checkbox.setChecked(word.isSelected());
        ((ItemViewHolder)holder).mword_checkbox.setTag(new Integer(position));
        ((ItemViewHolder)holder).mword_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox)view;
                int clickedPos = ((Integer)cb.getTag()).intValue();
                mWordsList.get(clickedPos).setSelected(cb.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mword_id;
        private TextView mword_label;
        private CheckBox mword_checkbox;

        private ItemViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            //Associations vues/valeurs
            mword_id = itemView.findViewById(R.id.word_id);
            mword_label = itemView.findViewById(R.id.word_label);
            mword_checkbox = itemView.findViewById(R.id.word_checkbox);
        }
    }

    public  List<Word> getSelectedItem(){
        for (int i =0; i < mWordsList.size(); i++){
            Word word = mWordsList.get(i);
            if (word.isSelected()){
                mWordsList.add(word);
            }
        }
        return mWordsList;
    }

}
