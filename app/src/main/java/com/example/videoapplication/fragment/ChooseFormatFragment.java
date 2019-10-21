package com.example.videoapplication.fragment;


import android.content.Intent;
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
public class ChooseFormatFragment extends Fragment {
    RelativeLayout format_1;
    RelativeLayout format_2;
    RelativeLayout format_3;
    RelativeLayout format_4;
    RelativeLayout add_fr_1;
    RelativeLayout add_fr_2;
    RelativeLayout add_fr_3;
    RelativeLayout add_fr_4;


    public ChooseFormatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_format, container, false);
        format_1 = view.findViewById(R.id.format_1);
        format_2 = view.findViewById(R.id.format_2);
        format_3 = view.findViewById(R.id.format_3);
        format_4 = view.findViewById(R.id.format_4);
        add_fr_1 = view.findViewById(R.id.add_fr_1);
        add_fr_2 = view.findViewById(R.id.add_fr_2);
        add_fr_3 = view.findViewById(R.id.add_fr_3);
        add_fr_4 = view.findViewById(R.id.add_fr_4);

        format_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_fr_1.setVisibility(View.VISIBLE);
                add_fr_2.setVisibility(View.GONE);
                add_fr_3.setVisibility(View.GONE);
                add_fr_4.setVisibility(View.GONE);
                ((DashBoardActivity) getActivity()).onFormatClick();
            }
        });


        format_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_fr_2.setVisibility(View.VISIBLE);
                add_fr_1.setVisibility(View.GONE);
                add_fr_3.setVisibility(View.GONE);
                add_fr_4.setVisibility(View.GONE);
                ((DashBoardActivity) getActivity()).onFormatClick();
            }
        });
        format_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_fr_3.setVisibility(View.VISIBLE);
                add_fr_1.setVisibility(View.GONE);
                add_fr_2.setVisibility(View.GONE);
                add_fr_4.setVisibility(View.GONE);
                ((DashBoardActivity) getActivity()).onFormatClick();
            }
        });
        format_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_fr_4.setVisibility(View.VISIBLE);
                add_fr_1.setVisibility(View.GONE);
                add_fr_2.setVisibility(View.GONE);
                add_fr_3.setVisibility(View.GONE);
                ((DashBoardActivity) getActivity()).onFormatClick();
            }
        });
        return view;

    }

}
