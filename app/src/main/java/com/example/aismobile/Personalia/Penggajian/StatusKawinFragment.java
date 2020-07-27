package com.example.aismobile.Personalia.Penggajian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aismobile.R;

public class StatusKawinFragment extends Fragment {

    private TextView menuPotonganKaryawan;
    private TextView menuTunjanganKaryawan;
    private TextView menuTunjanganTemporary;
    private TextView menuTunjanganJenjang;
    private TextView menuPotongan;
    private TextView menuTunjangan;
    private TextView menuGolGaji;
    private TextView menuStatusKawin;
    private TextView menuSalaryCorrect;
    private TextView menuLateDeduc;
    private TextView menuSisaCuti;
    private TextView menuKabupaten;
    private TextView menuProvinsi;

    public StatusKawinFragment() {
    }

    public static StatusKawinFragment newInstance() {
        StatusKawinFragment fragment = new StatusKawinFragment();
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
        View view = inflater.inflate(R.layout.fragment_status_kawin, container, false);

        menuPotonganKaryawan = (TextView) view.findViewById(R.id.pskMenuPotonganKaryawan);
        menuTunjanganKaryawan = (TextView) view.findViewById(R.id.pskMenuTunjanganKaryawan);
        menuTunjanganTemporary = (TextView) view.findViewById(R.id.pskMenuTunjanganTemporary);
        menuTunjanganJenjang = (TextView) view.findViewById(R.id.pskMenuTunjanganJenjang);
        menuPotongan = (TextView) view.findViewById(R.id.pskMenuPotongan);
        menuTunjangan = (TextView) view.findViewById(R.id.pskMenuTunjangan);
        menuGolGaji = (TextView) view.findViewById(R.id.pskMenuGolGaji);
        menuStatusKawin = (TextView) view.findViewById(R.id.pskMenuStatusKawin);
        menuSalaryCorrect = (TextView) view.findViewById(R.id.pskMenuSalaryCorrect);
        menuLateDeduc = (TextView) view.findViewById(R.id.pskMenuLateDeduc);
        menuSisaCuti = (TextView) view.findViewById(R.id.pskMenuSisaCuti);
        menuKabupaten = (TextView) view.findViewById(R.id.pskMenuKabupaten);
        menuProvinsi = (TextView) view.findViewById(R.id.pskMenuProvinsi);

        menuPotonganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PotonganKaryawanFragment mainFragment = PotonganKaryawanFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuTunjanganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TunjanganKaryawanFragment mainFragment = TunjanganKaryawanFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuTunjanganTemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TunjanganTemporaryFragment mainFragment = TunjanganTemporaryFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuTunjanganJenjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TunjanganJenjangFragment mainFragment = TunjanganJenjangFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuPotongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PotonganFragment mainFragment = PotonganFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuTunjangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TunjanganFragment mainFragment = TunjanganFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuGolGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GolonganGajiFragment mainFragment = GolonganGajiFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuStatusKawin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                StatusKawinFragment mainFragment = StatusKawinFragment.newInstance();
//                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//                fragmentTransaction.disallowAddToBackStack();
//                fragmentTransaction.commit();
            }
        });
        menuSalaryCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SalaryCorrectionFragment mainFragment = SalaryCorrectionFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuLateDeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LateDeductionFragment mainFragment = LateDeductionFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuSisaCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SisaCutiFragment mainFragment = SisaCutiFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuKabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                KabupatenFragment mainFragment = KabupatenFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        menuProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ProvinsiFragment mainFragment = ProvinsiFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}