package com.asukacorp.aismobile.Finance.CustomerInvoice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asukacorp.aismobile.R;

public class AturCustomerInvoiceFragment extends Fragment {

    private ImageView imageViewACIDetail;
    private LinearLayout listACIDetail;
    private TextView menuDaftarACI;
    private TextView menuBuatACI;
    private TextView menuCetakACI;

    public AturCustomerInvoiceFragment() {
        // Required empty public constructor
    }

    public static AturCustomerInvoiceFragment newInstance() {
        AturCustomerInvoiceFragment fragment = new AturCustomerInvoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_atur_customer_invoice, container, false);

        imageViewACIDetail = (ImageView) view.findViewById(R.id.imageViewACIDetail);
        listACIDetail = (LinearLayout) view.findViewById(R.id.listACIDetail);
        menuDaftarACI = (TextView) view.findViewById(R.id.menuDaftarACI);
        menuBuatACI = (TextView) view.findViewById(R.id.menuBuatACI);
        menuCetakACI = (TextView) view.findViewById(R.id.menuCetakACI);

        imageViewACIDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listACIDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listACIDetail.setLayoutParams(params);
            }
        });
        menuDaftarACI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CustomerInvoiceFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFragment, fragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuBuatACI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BuatCustomerInvoicesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFragment, fragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuCetakACI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CetakCustomerInvoiceFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerFragment, fragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
