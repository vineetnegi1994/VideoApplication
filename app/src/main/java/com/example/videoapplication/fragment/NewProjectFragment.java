package com.example.videoapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.videoapplication.R;
import com.example.videoapplication.activity.DashBoardActivity;
import com.google.android.material.snackbar.Snackbar;


public class NewProjectFragment extends Fragment {
    TextView projectName;
    RelativeLayout startproj;
    public NewProjectFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_project, container, false);
        projectName=view.findViewById(R.id.projectName);
        startproj=view.findViewById(R.id.startproj);
        startproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!projectName.getText().toString().trim().equals("")){
                    ((DashBoardActivity)getActivity()).onNewProjectBtnClick();
                }
                else{
                    Snackbar.make(view, "Enter Project Title.", Snackbar.LENGTH_LONG).show();

                }
            }
        });
        return view;
    }



}
