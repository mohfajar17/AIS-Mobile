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
import com.example.aismobile.Data.Personalia.MaritalStatus;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public StatusKawinFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Nama Status", "Wajib Pajak", "Status Kawin", "Jumlah Tanggungan",
            "Jumlah Pajak", "PTKP", "JPK", "UMK"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Nama Status", "Berdasarkan Wajib Pajak",
            "Berdasarkan Status Kawin", "Berdasarkan Jumlah Tanggungan", "Berdasarkan Jumlah Pajak", "Berdasarkan PTKP",
            "Berdasarkan JPK", "Berdasarkan UMK"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<MaritalStatus> maritalStatuses;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public StatusKawinFragment() {
    }

    public static StatusKawinFragment newInstance() {
        StatusKawinFragment fragment = new StatusKawinFragment();
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

        maritalStatuses = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
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

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.pskShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.pskLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.pskRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.pskFabAdd);
        editSearch = (EditText) view.findViewById(R.id.pskEditSearch);
        textPaging = (TextView) view.findViewById(R.id.pskTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.pskBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.pskSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.pskSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.pskSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.pskBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.pskBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.pskBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.pskLayoutPaging);

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
                    loadDataAll("marital_status_id ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("marital_status_id ASC");
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
                    loadData("marital_status_id ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("marital_status_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("marital_status_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("marital_status_name DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("minimum_amount ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("minimum_amount DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("minimum_amount_maried ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("minimum_amount_maried DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("person_to_care ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("person_to_care DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("tax_amount ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("tax_amount DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("ptkp_tahunan ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("ptkp_tahunan DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("jpk_bulanan ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("jpk_bulanan DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("umk_amount ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("umk_amount DESC");
        else loadDataAll("marital_status_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("marital_status_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("marital_status_name DESC");
        else if (position == 2 && posAD == 0)
            loadData("minimum_amount ASC");
        else if (position == 2 && posAD == 1)
            loadData("minimum_amount DESC");
        else if (position == 3 && posAD == 0)
            loadData("minimum_amount_maried ASC");
        else if (position == 3 && posAD == 1)
            loadData("minimum_amount_maried DESC");
        else if (position == 4 && posAD == 0)
            loadData("person_to_care ASC");
        else if (position == 4 && posAD == 1)
            loadData("person_to_care DESC");
        else if (position == 5 && posAD == 0)
            loadData("tax_amount ASC");
        else if (position == 5 && posAD == 1)
            loadData("tax_amount DESC");
        else if (position == 6 && posAD == 0)
            loadData("ptkp_tahunan ASC");
        else if (position == 6 && posAD == 1)
            loadData("ptkp_tahunan DESC");
        else if (position == 7 && posAD == 0)
            loadData("jpk_bulanan ASC");
        else if (position == 7 && posAD == 1)
            loadData("jpk_bulanan DESC");
        else if (position == 8 && posAD == 0)
            loadData("umk_amount ASC");
        else if (position == 8 && posAD == 1)
            loadData("umk_amount DESC");
        else loadData("marital_status_id ASC");
    }

    private void setAdapterList(){
        adapter = new StatusKawinFragment.MyRecyclerViewAdapter(maritalStatuses, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        maritalStatuses.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MARITAL_STATUS_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            maritalStatuses.add(new MaritalStatus(jsonArray.getJSONObject(i)));
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
        maritalStatuses.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MARITAL_STATUS_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            maritalStatuses.add(new MaritalStatus(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(MaritalStatus item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<MaritalStatus> mValues;
        private final List<MaritalStatus> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<MaritalStatus> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_status_kawin_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pskTextKode.setText("");
            holder.pskTextNama.setText(""+mValues.get(position).getMarital_status_name());
            holder.pskTextWajibPajak.setText(""+mValues.get(position).getMinimum_amount());
            holder.pskTextStatus.setText(""+mValues.get(position).getMinimum_amount_maried());
            holder.pskTextTanggungan.setText(""+mValues.get(position).getPerson_to_care());
            holder.pskTextPajak.setText(""+mValues.get(position).getTax_amount());
            holder.pskTextPtkp.setText(""+mValues.get(position).getPtkp_tahunan());
            holder.pskTextJpk.setText(""+mValues.get(position).getJpk_bulanan());
            holder.pskTextUmk.setText(""+mValues.get(position).getUmk_amount());

            if (position%2==0)
                holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<MaritalStatus> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((MaritalStatus) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (MaritalStatus item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            /*if (item.getSalary_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else*/ if (item.getMarital_status_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getMinimum_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getMinimum_amount_maried().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPerson_to_care().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTax_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPtkp_tahunan().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJpk_bulanan().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getUmk_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
//                            if (item.getSalary_grade_name().toLowerCase().contains(filterPattern)){
//                                filteredList.add(item);
//                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getMarital_status_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getMinimum_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getMinimum_amount_maried().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getPerson_to_care().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getTax_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getPtkp_tahunan().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getJpk_bulanan().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==9){
                            if (item.getUmk_amount().toLowerCase().contains(filterPattern)){
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

            public final TextView pskTextKode;
            public final TextView pskTextNama;
            public final TextView pskTextWajibPajak;
            public final TextView pskTextStatus;
            public final TextView pskTextTanggungan;
            public final TextView pskTextPajak;
            public final TextView pskTextPtkp;
            public final TextView pskTextJpk;
            public final TextView pskTextUmk;

            public final LinearLayout pskLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pskTextKode = (TextView) view.findViewById(R.id.pskTextKode);
                pskTextNama = (TextView) view.findViewById(R.id.pskTextNama);
                pskTextWajibPajak = (TextView) view.findViewById(R.id.pskTextWajibPajak);
                pskTextStatus = (TextView) view.findViewById(R.id.pskTextStatus);
                pskTextTanggungan = (TextView) view.findViewById(R.id.pskTextTanggungan);
                pskTextPajak = (TextView) view.findViewById(R.id.pskTextPajak);
                pskTextPtkp = (TextView) view.findViewById(R.id.pskTextPtkp);
                pskTextJpk = (TextView) view.findViewById(R.id.pskTextJpk);
                pskTextUmk = (TextView) view.findViewById(R.id.pskTextUmk);

                pskLayoutList = (LinearLayout) view.findViewById(R.id.pskLayoutList);
            }
        }
    }
}