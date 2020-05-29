package com.example.aismobile.Finance.CustomerInvoice;

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

import com.example.aismobile.R;

public class CetakCustomerInvoiceFragment extends Fragment {

    private ImageView imageViewCCIDetail;
    private LinearLayout listCCIDetail;
    private TextView menuDaftarCCI;
    private TextView menuBuatCCI;
    private TextView menuAturCCI;

    public CetakCustomerInvoiceFragment() {
        // Required empty public constructor
    }

    public static CetakCustomerInvoiceFragment newInstance() {
        CetakCustomerInvoiceFragment fragment = new CetakCustomerInvoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_cetak_customer_invoice, container, false);

        imageViewCCIDetail = (ImageView) view.findViewById(R.id.imageViewCCIDetail);
        listCCIDetail = (LinearLayout) view.findViewById(R.id.listCCIDetail);
        menuDaftarCCI = (TextView) view.findViewById(R.id.menuDaftarCCI);
        menuBuatCCI = (TextView) view.findViewById(R.id.menuBuatCCI);
        menuAturCCI = (TextView) view.findViewById(R.id.menuAturCCI);

        imageViewCCIDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listCCIDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listCCIDetail.setLayoutParams(params);
            }
        });
        menuDaftarCCI.setOnClickListener(new View.OnClickListener() {
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
        menuBuatCCI.setOnClickListener(new View.OnClickListener() {
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
        menuAturCCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AturCustomerInvoiceFragment();
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
