package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aismobile.R;

public class BudgetingFragment extends Fragment {

    private ImageView imageViewBudgetingDetail;
    private LinearLayout listBudgetingDetail;

    public BudgetingFragment() {
        // Required empty public constructor
    }
    public static BudgetingFragment newInstance() {
        BudgetingFragment fragment = new BudgetingFragment();
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
        View view = inflater.inflate(R.layout.fragment_budgeting, container, false);

        imageViewBudgetingDetail = (ImageView) view.findViewById(R.id.imageViewBudgetingDetail);
        listBudgetingDetail = (LinearLayout) view.findViewById(R.id.listBudgetingDetail);

        imageViewBudgetingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listBudgetingDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listBudgetingDetail.setLayoutParams(params);
            }
        });

        return view;
    }
}
