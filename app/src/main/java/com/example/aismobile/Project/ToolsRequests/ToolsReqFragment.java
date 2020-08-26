package com.example.aismobile.Project.ToolsRequests;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ToolsReqFragment extends Fragment {

    public EditText ppEditSearch;
    public ImageView ppBtnSearch;
    public FloatingActionButton ppFabAdd;
    public Spinner ppSpinnerSearch;
    public Spinner ppSpinnerSort;
    public Spinner ppSpinnerSortAD;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
//    public OnListFragmentInteractionListener mListener;
//    public PengambilanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] PSpinnerSearch = {"Semua Data", "Nomor Pengambilan", "Material Request", "Dibuat Oleh",
            "Diambil Oleh", "Diakui Oleh", "Tanggal Pengambilan", "Catatan", "Diakui"};
    public String[] PSpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Pengambilan", "Berdasarkan Material Request",
            "Berdasarkan Dibuat Oleh", "Berdasarkan Diambil Oleh", "Berdasarkan Diakui Oleh", "Berdasarkan Tanggal Pengambilan",
            "Berdasarkan Catatan", "Berdasarkan Diakui"};
    public String[] PSADpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
//    public List<Pickup> pickups;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter;

    public ToolsReqFragment() {
    }

    public static ToolsReqFragment newInstance() {
        ToolsReqFragment fragment = new ToolsReqFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

//        pickups = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools_req, container, false);

        ppFabAdd = (FloatingActionButton) view.findViewById(R.id.ptrFabAdd);
        ppEditSearch = (EditText) view.findViewById(R.id.ptrEditSearch);
        ppBtnSearch = (ImageView) view.findViewById(R.id.ptrBtnSearch);
        ppSpinnerSearch = (Spinner) view.findViewById(R.id.ptrSpinnerSearch);
        ppSpinnerSort = (Spinner) view.findViewById(R.id.ptrSpinnerSort);
        ppSpinnerSortAD = (Spinner) view.findViewById(R.id.ptrSpinnerSortAD);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSearch);
        ppSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSort);
        ppSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSADpinnerSort);
        ppSpinnerSortAD.setAdapter(spinnerAdapter);

        ppSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                setSortAll(position, ppSpinnerSortAD.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ppBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ppEditSearch.getText().toString().matches("")){
//                    ppSpinnerSearch.setSelection(0);
//                    adapter.getFilter().filter("-");
//                } else adapter.getFilter().filter(String.valueOf(ppEditSearch.getText()));
            }
        });

//        loadData("pickup_id DESC");

        return view;
    }
}
