// AddListFragment.java
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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.api.ExerciceHelper;
import com.utilisateur.orthomem.controllers.activities.MyListsActivity;
import com.utilisateur.orthomem.controllers.activities.MyWordsActivity;
import com.utilisateur.orthomem.model.Exercice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddListFragment extends Fragment {

    //Cf. https://github.com/codingdemos/MultichoiceTutorial/blob/master/app/src/main/java/com/example/multichoicetutorial/MainActivity.java

    private static final String TAG = "";
    private TextView mStatusTextView;
    private EditText mLabel_EditText;
    private EditText mGoal_EditText;
    private SeekBar mNbOfSyllabes_SeekBar;
    private Button mSelection_Button;
    private TextView mSelected_label_TextView;
    private TextView mSelected_value_TextView;
    String[] listItems;
    boolean[] checkedItems;
    List<String> mUserItems = new ArrayList<>();
    private Button mSubmit_Button;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mBdd;


    public AddListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_addlist, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStatusTextView = view.findViewById(R.id.addlist_status);
        mLabel_EditText = view.findViewById(R.id.label_edittext_addlist);
        mGoal_EditText = view.findViewById(R.id.goal_edittext_addlist);
        mNbOfSyllabes_SeekBar = view.findViewById(R.id.nbofsyllabes_seekBar);
        mSelection_Button = view.findViewById(R.id.addlist_selection_button);
        mSelected_label_TextView = view.findViewById(R.id.addlist_selectedwordslist_label);
        mSelected_value_TextView = view.findViewById(R.id.addlist_selectedwordslist_value);
        mSelected_label_TextView.setText(mSelected_label_TextView.getText()+"0"+getString(R.string.addlist_selected_value_label_end));
        mSubmit_Button = view.findViewById(R.id.addlist_submit_button);


        mAuth = FirebaseAuth.getInstance();
        mBdd = FirebaseFirestore.getInstance();

        mUserItems = new ArrayList<>(Arrays.asList("TY2rZbDprdaWqCjIRMe7", "gpjLgiC7uqyaP6lhHBZi", "DvnDT5v5hpZqZ0L8x7R7"));

        //listItems = new String[]{""};
        //listItems = getResources().getStringArray(R.array.shopping_item);
        //checkedItems = new boolean[listItems.length];


        mSelection_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(getActivity(), MyWordsActivity.class);
                myintent.putExtra("NBOFSYLLABES", mNbOfSyllabes_SeekBar.getProgress());
                startActivity(myintent);
            }
        });

        mSubmit_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ExerciceHelper.createExercice(mAuth.getUid(), mLabel_EditText.getText().toString(), mGoal_EditText.getText().toString(), mUserItems);
                Toast.makeText(getContext(), "Liste bien ajoutée ", Toast.LENGTH_SHORT).show();
                Intent myintent = new Intent(getActivity(), MyListsActivity.class);
                startActivity(myintent);
            }
        });
    }
}