package com.utilisateur.orthomem.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.api.ExerciceHelper;
import com.utilisateur.orthomem.controllers.activities.MyListsActivity;
import com.utilisateur.orthomem.controllers.activities.MyWordsActivity;



public class AddListFragment extends Fragment {

    //Cf. https://github.com/codingdemos/MultichoiceTutorial/blob/master/app/src/main/java/com/example/multichoicetutorial/MainActivity.java

    public static final int MYWORDS_ACTIVITY_REQUEST_CODE =222;
    private EditText mLabel_EditText;
    private EditText mGoal_EditText;
    private SeekBar mNbOfSyllabes_SeekBar;
    private Button mSelectionButton;
    private Button mSubmitButton;
    private TextView mSelected_id_TextView;
    private TextView mSelected_label_TextView;
    private ArrayList<String> mListIds = new ArrayList<>();
    private ArrayList<String> mListLabels = new ArrayList<>();
    private FirebaseFirestore mBdd;


    public AddListFragment() {/*Required empty public constructor*/}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addlist, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(MYWORDS_ACTIVITY_REQUEST_CODE==requestCode && getActivity().RESULT_OK == resultCode) {
            mListIds.clear();

            // Récupération des Mots sélectionnés
            if (data != null) {
                Bundle extras = data.getExtras();
                mListIds = extras.getStringArrayList("NEWIDS");


                if(mListIds != null && mListIds.size()>0){
                    mSelected_id_TextView.setText(getResources().getString(R.string.addlist_selectedids_start) + Integer.toString(mListIds.size()) + getString(R.string.addlist_selectedids_end));
                    mSubmitButton.setBackground(getResources().getDrawable(R.drawable.orthobutton));
                    mSubmitButton.setTextColor(getResources().getColor(R.color.black));
                    mSubmitButton.setEnabled(true);
                }
                else {
                    mSelected_id_TextView.setText(getResources().getString(R.string.addlist_selectedids_start));
                    mSubmitButton.setBackground(getResources().getDrawable(R.drawable.orthobutton_disabled));
                    mSubmitButton.setTextColor(getResources().getColor(R.color.orthomidgrey));
                    mSubmitButton.setEnabled(false);
                }

                mListLabels = extras.getStringArrayList("NEWLABELS");
                mSelected_label_TextView.setText(getString(R.string.addlist_selectedlabels));
                if(mListLabels.size()>0){
                    mSelected_label_TextView.setText(getString(R.string.addlist_selectedlabels));
                    for (int i = 0; i < mListLabels.size(); i++) {
                        mSelected_label_TextView.append(mListLabels.get(i));
                        if(i<mListLabels.size()-1){mSelected_label_TextView.append(", ");}
                    }
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLabel_EditText = view.findViewById(R.id.label_edittext_addlist);
        mGoal_EditText = view.findViewById(R.id.goal_edittext_addlist);
        mNbOfSyllabes_SeekBar = view.findViewById(R.id.nbofsyllabes_seekBar);
        mSelectionButton = view.findViewById(R.id.addlist_selection_button);
        mSelected_id_TextView = view.findViewById(R.id.addlist_selectedids);
        mSelected_id_TextView.setText(mSelected_id_TextView.getText());
        mSelected_label_TextView = view.findViewById(R.id.addlist_selectedlabels);
        mSubmitButton = view.findViewById(R.id.addlist_submit_button);

/*
        //mListIds = new ArrayList<>();
        mListIds.add("TY2rZbDprdaWqCjIRMe7");
        mListIds.add("gpjLgiC7uqyaP6lhHBZi");
        mListIds.add("DvnDT5v5hpZqZ0L8x7R7");
*/

        if(mListIds.size()>0){
            mSubmitButton.setBackground(getResources().getDrawable(R.drawable.orthobutton));
            mSubmitButton.setTextColor(getResources().getColor(R.color.black));
            mSubmitButton.setEnabled(true);
        }
        else {
            mSubmitButton.setBackground(getResources().getDrawable(R.drawable.orthobutton_disabled));
            mSubmitButton.setTextColor(getResources().getColor(R.color.orthomidgrey));
            mSubmitButton.setEnabled(false);
        }

        mBdd = FirebaseFirestore.getInstance();


        mSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {startNextActivity("select");
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {startNextActivity("submit");
            }
        });
    }

    private void startNextActivity(String type){
        switch (type) {
            case "select":
                Intent myIntent = new Intent(getActivity(), MyWordsActivity.class);
                myIntent.putExtra("NBOFSYLLABES", mNbOfSyllabes_SeekBar.getProgress());
                myIntent.putExtra("LISTIDS", mListIds);
                myIntent.putExtra("LISTLABELS", mListLabels);
                startActivityForResult(myIntent,MYWORDS_ACTIVITY_REQUEST_CODE);
                break;
            case "submit":
                ExerciceHelper.createExercice(mLabel_EditText.getText().toString(), mGoal_EditText.getText().toString(), mListIds);
                showToast(mLabel_EditText.getText().toString() + " bien ajoutée.");
                Intent myintent = new Intent(getActivity(), MyListsActivity.class);
                startActivity(myintent);
                break;
        }
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}