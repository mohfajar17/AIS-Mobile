package com.example.aismobile.Crm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class GrafikKuesionerFragment extends Fragment {

    public GrafikKuesionerFragment() {
    }

    public static GrafikKuesionerFragment newInstance() {
        GrafikKuesionerFragment fragment = new GrafikKuesionerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafik_kuesioner, container, false);
        return view;
    }
}
