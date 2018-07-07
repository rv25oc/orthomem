// AddListFragment.java
package com.utilisateur.orthomem.controllers.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.MultiSelectionAdapter;
import com.utilisateur.orthomem.controllers.activities.MyListActivity;
import com.utilisateur.orthomem.controllers.activities.MyWordsActivity;
import com.utilisateur.orthomem.model.Exercice;
import com.utilisateur.orthomem.model.Word;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    ArrayList<Integer> mUserItems = new ArrayList<>();


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
    }
}