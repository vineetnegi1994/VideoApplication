package com.example.videoapplication.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.videoapplication.R;
import com.example.videoapplication.activity.DashBoardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RelativeLayout librarylayout;
    RelativeLayout photolayout;
    RelativeLayout newprojlayout;
    RelativeLayout videolayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        librarylayout=view.findViewById(R.id.librarylayout);
        photolayout=view.findViewById(R.id.photolayout);
        newprojlayout=view.findViewById(R.id.newprojlayout);
        videolayout=view.findViewById(R.id.videolayout);

        librarylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity)getActivity()).onLibrary();
            }
        });
        photolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity)getActivity()).onPhotoClick();
            }
        });
        newprojlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity)getActivity()).onNewProjClick();
            }
        });
        videolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashBoardActivity)getActivity()).onVideoClick();

            }
        });

        return view;

    }

}
