package com.asukacorp.aismobile.Personalia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asukacorp.aismobile.R;

public class KalenderFragment extends Fragment {

    public KalenderFragment() {
    }

    public static KalenderFragment newInstance() {
        KalenderFragment fragment = new KalenderFragment();
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
        View view = inflater.inflate(R.layout.fragment_kalender, container, false);
        return view;
    }
}
