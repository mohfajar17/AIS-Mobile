package com.example.aismobile.Project.ToolsRequests;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class ToolsReqFragment extends Fragment {

    public ToolsReqFragment() {
        // Required empty public constructor
    }

    public static ToolsReqFragment newInstance() {
        ToolsReqFragment fragment = new ToolsReqFragment();
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
        View view = inflater.inflate(R.layout.fragment_tools_req, container, false);
        return view;
    }
}
