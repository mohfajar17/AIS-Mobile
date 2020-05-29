package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class DaftarAkunFragment extends Fragment {

    public DaftarAkunFragment() {
        // Required empty public constructor
    }

    public static DaftarAkunFragment newInstance() {
        DaftarAkunFragment fragment = new DaftarAkunFragment();
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
        View view = inflater.inflate(R.layout.fragment_daftar_akun, container, false);
        return view;
    }
}
