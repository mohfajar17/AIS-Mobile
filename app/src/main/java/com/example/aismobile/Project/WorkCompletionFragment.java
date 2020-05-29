package com.example.aismobile.Project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class WorkCompletionFragment extends Fragment {
    public WorkCompletionFragment() {
        // Required empty public constructor
    }

    public static WorkCompletionFragment newInstance() {
        WorkCompletionFragment fragment = new WorkCompletionFragment();
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
        return inflater.inflate(R.layout.fragment_work_completion, container, false);
    }
}
