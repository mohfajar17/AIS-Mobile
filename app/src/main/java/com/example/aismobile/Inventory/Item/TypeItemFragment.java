package com.example.aismobile.Inventory.Item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aismobile.R;

public class TypeItemFragment extends Fragment {

    private TextView iMenuItem;
    private TextView iMenuKelompokItem;
    private TextView iMenuKategoriItem;
    private TextView iMenuTipeItem;

    public TypeItemFragment() {
    }

    public static TypeItemFragment newInstance() {
        TypeItemFragment fragment = new TypeItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_type_item, container, false);

        //sub menu
        iMenuItem = (TextView) view.findViewById(R.id.tiMenuItem);
        iMenuKelompokItem = (TextView) view.findViewById(R.id.tiMenuKelompokItem);
        iMenuKategoriItem = (TextView) view.findViewById(R.id.tiMenuKategoriItem);
        iMenuTipeItem = (TextView) view.findViewById(R.id.tiMenuTipeItem);

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
        iMenuKategoriItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                KategoriItemFragment mainFragment = KategoriItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
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

        return view;
    }
}