package com.example.aismobile.Inventory.Item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aismobile.R;

public class KategoriItemFragment extends Fragment {

    private TextView iMenuItem;
    private TextView iMenuKelompokItem;
    private TextView iMenuKategoriItem;
    private TextView iMenuTipeItem;

    public KategoriItemFragment() {
    }

    public static KategoriItemFragment newInstance() {
        KategoriItemFragment fragment = new KategoriItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_kategori_item, container, false);

        //sub menu
        iMenuItem = (TextView) view.findViewById(R.id.ktiMenuItem);
        iMenuKelompokItem = (TextView) view.findViewById(R.id.ktiMenuKelompokItem);
        iMenuKategoriItem = (TextView) view.findViewById(R.id.ktiMenuKategoriItem);
        iMenuTipeItem = (TextView) view.findViewById(R.id.ktiMenuTipeItem);

        iMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ItemFragment mainFragment = ItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        iMenuKelompokItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                KelompokItemFragment mainFragment = KelompokItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        iMenuTipeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TypeItemFragment mainFragment = TypeItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}