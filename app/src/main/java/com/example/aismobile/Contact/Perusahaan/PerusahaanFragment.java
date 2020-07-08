package com.example.aismobile.Contact.Perusahaan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class PerusahaanFragment extends Fragment {

    public PerusahaanFragment() {
        // Required empty public constructor
    }

    public static PerusahaanFragment newInstance() {
        PerusahaanFragment fragment = new PerusahaanFragment();
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
        View view = inflater.inflate(R.layout.fragment_perusahaan, container, false);

        return view;
    }
}
