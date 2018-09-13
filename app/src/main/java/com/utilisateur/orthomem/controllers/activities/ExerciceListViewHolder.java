package com.utilisateur.orthomem.controllers.activities;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.utilisateur.orthomem.R;
import com.utilisateur.orthomem.adapters.ExerciceListRecyclerViewAdapter;
import com.utilisateur.orthomem.api.UserHelper;
import com.utilisateur.orthomem.model.Exercice;

import java.lang.ref.WeakReference;

import butterknife.BindView;

