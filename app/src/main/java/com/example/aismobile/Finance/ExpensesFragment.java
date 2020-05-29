package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aismobile.R;

public class ExpensesFragment extends Fragment {

    private ImageView imageViewExpensesDetail;
    private LinearLayout listExpensesDetail;

    public ExpensesFragment() {
    }

    public static ExpensesFragment newInstance() {
        ExpensesFragment fragment = new ExpensesFragment();
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
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        imageViewExpensesDetail = (ImageView) view.findViewById(R.id.imageViewExpensesDetail);
        listExpensesDetail = (LinearLayout) view.findViewById(R.id.listExpensesDetail);

        imageViewExpensesDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listExpensesDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listExpensesDetail.setLayoutParams(params);
            }
        });

        return view;
    }
}
