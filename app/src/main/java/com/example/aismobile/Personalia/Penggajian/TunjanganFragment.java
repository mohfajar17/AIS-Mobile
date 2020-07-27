package com.example.aismobile.Personalia.Penggajian;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Personalia.Allowance;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TunjanganFragment extends Fragment {

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

    private LinearLayout kShowFilter;
    private LinearLayout kLayoutFilter;

    public TextView textPaging;
    public EditText editSearch;
    public ImageView btnSearch;
    public RecyclerView recycler;
    public FloatingActionButton fabAdd;
    public Spinner spinnerSearch;
    public Spinner spinnerSort;
    public Spinner spinnerSortAD;
    public Button btnShowList;
    public ImageButton btnBefore;
    public ImageButton btnNext;
    public LinearLayout layoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public TunjanganFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Tunjangan", "Kode Laporan", "Jenis Tunjangan", "Kelompok Tunjangan",
            "Nilai", "Satuan Tunjangan"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Tunjangan", "Berdasarkan Kode Laporan",
            "Berdasarkan Jenis Tunjangan", "Berdasarkan Kelompok Tunjangan", "Berdasarkan Nilai", "Berdasarkan Satuan Tunjangan"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Allowance> allowances;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public TunjanganFragment() {
    }

    public static TunjanganFragment newInstance() {
        TunjanganFragment fragment = new TunjanganFragment();
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

        allowances = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tunjangan, container, false);

        menuPotonganKaryawan = (TextView) view.findViewById(R.id.ptMenuPotonganKaryawan);
        menuTunjanganKaryawan = (TextView) view.findViewById(R.id.ptMenuTunjanganKaryawan);
        menuTunjanganTemporary = (TextView) view.findViewById(R.id.ptMenuTunjanganTemporary);
        menuTunjanganJenjang = (TextView) view.findViewById(R.id.ptMenuTunjanganJenjang);
        menuPotongan = (TextView) view.findViewById(R.id.ptMenuPotongan);
        menuTunjangan = (TextView) view.findViewById(R.id.ptMenuTunjangan);
        menuGolGaji = (TextView) view.findViewById(R.id.ptMenuGolGaji);
        menuStatusKawin = (TextView) view.findViewById(R.id.ptMenuStatusKawin);
        menuSalaryCorrect = (TextView) view.findViewById(R.id.ptMenuSalaryCorrect);
        menuLateDeduc = (TextView) view.findViewById(R.id.ptMenuLateDeduc);
        menuSisaCuti = (TextView) view.findViewById(R.id.ptMenuSisaCuti);
        menuKabupaten = (TextView) view.findViewById(R.id.ptMenuKabupaten);
        menuProvinsi = (TextView) view.findViewById(R.id.ptMenuProvinsi);

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
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                TunjanganFragment mainFragment = TunjanganFragment.newInstance();
//                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//                fragmentTransaction.disallowAddToBackStack();
//                fragmentTransaction.commit();
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StatusKawinFragment mainFragment = StatusKawinFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
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

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.ptShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.ptLayoutFilter);

        kShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = kShowFilter.getLayoutParams();
                params.height = 0;
                kShowFilter.setLayoutParams(params);
                params = kLayoutFilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                kLayoutFilter.setLayoutParams(params);
            }
        });

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.ptRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.ptFabAdd);
        editSearch = (EditText) view.findViewById(R.id.ptEditSearch);
        textPaging = (TextView) view.findViewById(R.id.ptTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.ptBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.ptSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.ptSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.ptSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.ptBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.ptBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.ptBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.ptLayoutPaging);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(textPaging.getText()));
                setSortHalf(spinnerSort.getSelectedItemPosition(), spinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(textPaging.getText()))+1;
                textPaging.setText(""+textValue);
                filter = true;
            }
        });
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(textPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(textPaging.getText()))-2);
                    setSortHalf(spinnerSort.getSelectedItemPosition(), spinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(textPaging.getText()))-1;
                    textPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("allowance_name ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("allowance_name ASC");
                    loadAll = false;
                    params = layoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        spinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        spinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        spinnerSortAD.setAdapter(spinnerAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, spinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, spinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearch.getText().toString().matches("")){
                    loadData("allowance_name ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("allowance_name ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("allowance_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("allowance_name DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("report_code ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("report_code DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("allowance_type_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("allowance_type_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("allowance_group_name ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("allowance_group_name DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("value ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("value DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("adjustment ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("adjustment DESC");
        else loadDataAll("allowance_name ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("allowance_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("allowance_name DESC");
        else if (position == 2 && posAD == 0)
            loadData("report_code ASC");
        else if (position == 2 && posAD == 1)
            loadData("report_code DESC");
        else if (position == 3 && posAD == 0)
            loadData("allowance_type_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("allowance_type_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("allowance_group_name ASC");
        else if (position == 4 && posAD == 1)
            loadData("allowance_group_name DESC");
        else if (position == 5 && posAD == 0)
            loadData("value ASC");
        else if (position == 5 && posAD == 1)
            loadData("value DESC");
        else if (position == 6 && posAD == 0)
            loadData("allowance_unit ASC");
        else if (position == 6 && posAD == 1)
            loadData("allowance_unit DESC");
        else loadData("allowance_name ASC");
    }

    private void setAdapterList(){
        adapter = new TunjanganFragment.MyRecyclerViewAdapter(allowances, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        allowances.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ALLOWANCE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            allowances.add(new Allowance(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                            } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
                            filter = false;
                        }
                    } else {
                        Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("counter", "" + counter);
                param.put("sortBy", "" + sortBy);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public void loadData(final String sortBy){
        progressDialog.show();
        recycler.setAdapter(null);
        allowances.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ALLOWANCE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            allowances.add(new Allowance(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                            } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
                            filter = false;
                        }
                    } else {
                        Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("counter", "" + counter);
                param.put("sortBy", "" + sortBy);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10 && resultCode == getActivity().RESULT_OK ){
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Allowance item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Allowance> mValues;
        private final List<Allowance> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Allowance> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_tunjangan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ptTextName.setText(""+mValues.get(position).getAllowance_name());
            holder.ptTextKode.setText(""+mValues.get(position).getReport_code());
            holder.ptTextJenis.setText(""+mValues.get(position).getAllowance_type_name());
            holder.ptTextKelompok.setText(""+mValues.get(position).getAllowance_group_name());
            holder.ptTextNilai.setText(""+mValues.get(position).getValue());
            holder.ptTextSatuan.setText(""+mValues.get(position).getAllowance_unit());

            if (position%2==0)
                holder.ptLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ptLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), ""+mValues.get(position).getCreated_by(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(), JobOrderDetailActivity.class);
//                    intent.putExtra("detailJO", mValues.get(position));
//                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Allowance> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Allowance) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Allowance item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getAllowance_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReport_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAllowance_type_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAllowance_group_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getValue().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAllowance_unit().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getAllowance_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getReport_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getAllowance_type_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getAllowance_group_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getValue().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getAllowance_unit().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mValues.clear();
                mValues.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView ptTextName;
            public final TextView ptTextKode;
            public final TextView ptTextJenis;
            public final TextView ptTextKelompok;
            public final TextView ptTextNilai;
            public final TextView ptTextSatuan;

            public final LinearLayout ptLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ptTextName = (TextView) view.findViewById(R.id.ptTextName);
                ptTextKode = (TextView) view.findViewById(R.id.ptTextKode);
                ptTextJenis = (TextView) view.findViewById(R.id.ptTextJenis);
                ptTextKelompok = (TextView) view.findViewById(R.id.ptTextKelompok);
                ptTextNilai = (TextView) view.findViewById(R.id.ptTextNilai);
                ptTextSatuan = (TextView) view.findViewById(R.id.ptTextSatuan);

                ptLayoutList = (LinearLayout) view.findViewById(R.id.ptLayoutList);
            }
        }
    }
}