package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aismobile.R;

public class BankTransactionFragment extends Fragment {

    private ImageView imageViewBTDetail;
    private LinearLayout listBTDetail;

    public BankTransactionFragment() {
    }

    public static BankTransactionFragment newInstance() {
        BankTransactionFragment fragment = new BankTransactionFragment();
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
        View view = inflater.inflate(R.layout.fragment_bank_transaction, container, false);

        imageViewBTDetail = (ImageView) view.findViewById(R.id.imageViewBTDetail);
        listBTDetail = (LinearLayout) view.findViewById(R.id.listBTDetail);

        imageViewBTDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listBTDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listBTDetail.setLayoutParams(params);
            }
        });

        return view;
    }
}
