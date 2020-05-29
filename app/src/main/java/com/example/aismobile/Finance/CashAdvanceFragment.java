package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aismobile.R;

public class CashAdvanceFragment extends Fragment {

    private ImageView imageViewCADetail;
    private LinearLayout listCADetail;

    public CashAdvanceFragment() {
        // Required empty public constructor
    }

    public static CashAdvanceFragment newInstance() {
        CashAdvanceFragment fragment = new CashAdvanceFragment();
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
        View view = inflater.inflate(R.layout.fragment_cash_advance, container, false);

        imageViewCADetail = (ImageView) view.findViewById(R.id.imageViewCADetail);
        listCADetail = (LinearLayout) view.findViewById(R.id.listCADetail);

        imageViewCADetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listCADetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listCADetail.setLayoutParams(params);
            }
        });

        return view;
    }
}
