package com.example.aismobile.Purchasing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aismobile.R;

public class GoodsRecivedNoteFragment extends Fragment {

    public GoodsRecivedNoteFragment() {
    }

    public static GoodsRecivedNoteFragment newInstance() {
        GoodsRecivedNoteFragment fragment = new GoodsRecivedNoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_goods_recived_note, container, false);
        return view;
    }
}
