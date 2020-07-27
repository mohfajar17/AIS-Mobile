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
import com.example.aismobile.Data.Personalia.EmployeeDeduction;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotonganKaryawanFragment extends Fragment {

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
    public PotonganKaryawanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Karyawan", "Potongan", "Tanggal Awal", "Tanggal Akhir",
            "Kategori Potongan", "Aktif"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Karyawan", "Berdasarkan Potongan",
            "Berdasarkan Tanggal Awal", "Berdasarkan Tanggal Akhir", "Berdasarkan Kategori Potongan",
            "Berdasarkan Aktif"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<EmployeeDeduction> employeeDeductions;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public PotonganKaryawanFragment() {
    }

    public static PotonganKaryawanFragment newInstance() {
        PotonganKaryawanFragment fragment = new PotonganKaryawanFragment();
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

        employeeDeductions = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_potongan_karyawan, container, false);

        menuPotonganKaryawan = (TextView) view.findViewById(R.id.ppkMenuPotonganKaryawan);
        menuTunjanganKaryawan = (TextView) view.findViewById(R.id.ppkMenuTunjanganKaryawan);
        menuTunjanganTemporary = (TextView) view.findViewById(R.id.ppkMenuTunjanganTemporary);
        menuTunjanganJenjang = (TextView) view.findViewById(R.id.ppkMenuTunjanganJenjang);
        menuPotongan = (TextView) view.findViewById(R.id.ppkMenuPotongan);
        menuTunjangan = (TextView) view.findViewById(R.id.ppkMenuTunjangan);
        menuGolGaji = (TextView) view.findViewById(R.id.ppkMenuGolGaji);
        menuStatusKawin = (TextView) view.findViewById(R.id.ppkMenuStatusKawin);
        menuSalaryCorrect = (TextView) view.findViewById(R.id.ppkMenuSalaryCorrect);
        menuLateDeduc = (TextView) view.findViewById(R.id.ppkMenuLateDeduc);
        menuSisaCuti = (TextView) view.findViewById(R.id.ppkMenuSisaCuti);
        menuKabupaten = (TextView) view.findViewById(R.id.ppkMenuKabupaten);
        menuProvinsi = (TextView) view.findViewById(R.id.ppkMenuProvinsi);

        menuPotonganKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                PotonganKaryawanFragment mainFragment = PotonganKaryawanFragment.newInstance();
//                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
//                fragmentTransaction.disallowAddToBackStack();
//                fragmentTransaction.commit();
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
        kShowFilter = (LinearLayout) view.findViewById(R.id.ppkShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.ppkLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.ppkRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.ppkFabAdd);
        editSearch = (EditText) view.findViewById(R.id.ppkEditSearch);
        textPaging = (TextView) view.findViewById(R.id.ppkTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.ppkBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.ppkSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.ppkSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.ppkSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.ppkBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.ppkBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.ppkBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.ppkLayoutPaging);

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
                    loadDataAll("fullname ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("fullname ASC");
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
                    loadData("fullname ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("fullname ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("fullname ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("fullname DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("deduction_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("deduction_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("date_begin ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("date_begin DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("date_end ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("date_end DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("tax ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("tax DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("fullname ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("fullname ASC");
        else if (position == 1 && posAD == 1)
            loadData("fullname DESC");
        else if (position == 2 && posAD == 0)
            loadData("deduction_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("deduction_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("date_begin ASC");
        else if (position == 3 && posAD == 1)
            loadData("date_begin DESC");
        else if (position == 4 && posAD == 0)
            loadData("date_end ASC");
        else if (position == 4 && posAD == 1)
            loadData("date_end DESC");
        else if (position == 5 && posAD == 0)
            loadData("tax ASC");
        else if (position == 5 && posAD == 1)
            loadData("tax DESC");
        else if (position == 6 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 6 && posAD == 1)
            loadData("is_active DESC");
        else loadData("fullname ASC");
    }

    private void setAdapterList(){
        adapter = new PotonganKaryawanFragment.MyRecyclerViewAdapter(employeeDeductions, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        employeeDeductions.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_DEDUCTION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDeductions.add(new EmployeeDeduction(jsonArray.getJSONObject(i)));
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
        employeeDeductions.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_DEDUCTION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDeductions.add(new EmployeeDeduction(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(EmployeeDeduction item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<EmployeeDeduction> mValues;
        private final List<EmployeeDeduction> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<EmployeeDeduction> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_potongan_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ppkTextKaryawan.setText(""+mValues.get(position).getFullname());
            holder.ppkTextPotongan.setText(""+mValues.get(position).getDeduction_name());
            holder.ppkTextTglAwal.setText(""+mValues.get(position).getDate_begin());
            holder.ppkTextTglAkhir.setText(""+mValues.get(position).getDate_end());
            holder.ppkTextKategori.setText(""+mValues.get(position).getTax());
            holder.ppkTextAktif.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.ppkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ppkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<EmployeeDeduction> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((EmployeeDeduction) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (EmployeeDeduction item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getFullname().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDeduction_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDate_begin().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDate_end().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTax().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getFullname().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getDeduction_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getDate_begin().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getDate_end().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getTax().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getIs_active().toLowerCase().contains(filterPattern)){
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

            public final TextView ppkTextKaryawan;
            public final TextView ppkTextPotongan;
            public final TextView ppkTextTglAwal;
            public final TextView ppkTextTglAkhir;
            public final TextView ppkTextKategori;
            public final TextView ppkTextAktif;

            public final LinearLayout ppkLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ppkTextKaryawan = (TextView) view.findViewById(R.id.ppkTextKaryawan);
                ppkTextPotongan = (TextView) view.findViewById(R.id.ppkTextPotongan);
                ppkTextTglAwal = (TextView) view.findViewById(R.id.ppkTextTglAwal);
                ppkTextTglAkhir = (TextView) view.findViewById(R.id.ppkTextTglAkhir);
                ppkTextKategori = (TextView) view.findViewById(R.id.ppkTextKategori);
                ppkTextAktif = (TextView) view.findViewById(R.id.ppkTextAktif);

                ppkLayoutList = (LinearLayout) view.findViewById(R.id.ppkLayoutList);
            }
        }
    }
}
