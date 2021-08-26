package com.asukacorp.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asukacorp.aismobile.R;

public class TaxReportsFragment extends Fragment {

    public TaxReportsFragment() {
    }

    public static TaxReportsFragment newInstance() {
        TaxReportsFragment fragment = new TaxReportsFragment();
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
        View view = inflater.inflate(R.layout.fragment_tax_reports, container, false);
        return view;
    }
}
