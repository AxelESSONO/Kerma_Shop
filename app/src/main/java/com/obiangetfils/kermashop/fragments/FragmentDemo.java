package com.obiangetfils.kermashop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obiangetfils.kermashop.R;

public class FragmentDemo extends Fragment {



    public FragmentDemo() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentDemo newInstance() {
        Bundle args = new Bundle();
        FragmentDemo fragment = new FragmentDemo();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        return view;
    }
}