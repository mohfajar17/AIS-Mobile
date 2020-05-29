package com.example.aismobile.Finance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aismobile.R;

public class PaymentSuppliersFragment extends Fragment {

    private ImageView imageViewPSDetail;
    private LinearLayout listPSDetail;

    public PaymentSuppliersFragment() {
        // Required empty public constructor
    }

    public static PaymentSuppliersFragment newInstance() {
        PaymentSuppliersFragment fragment = new PaymentSuppliersFragment();
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
        View view = inflater.inflate(R.layout.fragment_payment_suppliers, container, false);

        imageViewPSDetail = (ImageView) view.findViewById(R.id.imageViewPSDetail);
        listPSDetail = (LinearLayout) view.findViewById(R.id.listPSDetail);

        imageViewPSDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listPSDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listPSDetail.setLayoutParams(params);
            }
        });

        return view;
    }
}
