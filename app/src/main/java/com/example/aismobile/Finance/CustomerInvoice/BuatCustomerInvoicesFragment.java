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

import com.example.aismobile.Finance.DaftarAkunFragment;
import com.example.aismobile.R;

public class BuatCustomerInvoicesFragment extends Fragment {

    private ImageView imageViewBCIDetail;
    private LinearLayout listBCIDetail;
    private TextView menuDaftarBCI;
    private TextView menuAturBCI;

    public BuatCustomerInvoicesFragment() {
        // Required empty public constructor
    }

    public static BuatCustomerInvoicesFragment newInstance() {
        BuatCustomerInvoicesFragment fragment = new BuatCustomerInvoicesFragment();
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
        View view  = inflater.inflate(R.layout.fragment_buat_customer_invoices, container, false);

        imageViewBCIDetail = (ImageView) view.findViewById(R.id.imageViewBCIDetail);
        listBCIDetail = (LinearLayout) view.findViewById(R.id.listBCIDetail);
        menuDaftarBCI = (TextView) view.findViewById(R.id.menuDaftarBCI);
        menuAturBCI = (TextView) view.findViewById(R.id.menuAturBCI);

        imageViewBCIDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = listBCIDetail.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                listBCIDetail.setLayoutParams(params);
            }
        });

        menuDaftarBCI.setOnClickListener(new View.OnClickListener() {
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
        menuAturBCI.setOnClickListener(new View.OnClickListener() {
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
