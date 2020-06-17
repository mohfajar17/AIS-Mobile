package com.example.aismobile.Safety;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class SafetyFileReportFragment extends Fragment {

    public SafetyFileReportFragment() {
    }

    public static SafetyFileReportFragment newInstance() {
        SafetyFileReportFragment fragment = new SafetyFileReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_safety_file_report, container, false);
        return view;
    }
}