// ParametersFragment.java
package com.utilisateur.orthomem.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utilisateur.orthomem.R;


public class ParametersFragment extends Fragment {

    //private OnButtonClickedListener myCallback;    //2 - Declare callback

    public ParametersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_parameters, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();



    }


    //TEST HG - LANCER UNE NOUVELLE ACTIVITE AU CLIC
    //Set onClickListener to Exercicie label -- A TERME L'UNE DES LIGNES DE LA LISTE
    //result.findViewById(R.id.myLists_MyListButton).setOnClickListener(this);
    //FIN TEST HG


    /*
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);    // 1 - Declare our interface that will be implemented by any container activity
    }
*/
    /*
    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }


    @Override
    public void onClick(View v) {
        // 5 - Spread the click to the parent activity
        mCallback.onButtonClicked(v);
    }
*/
}