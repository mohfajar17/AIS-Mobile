package com.example.aismobile.Personalia.Karyawan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.aismobile.Data.Personalia.Employee.Employee;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KaryawanFragment extends Fragment {

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
    public KaryawanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Badge", "Nama Lengkap", "Jenjang Karyawan", "Status Kerja",
            "Departemen", "Lokasi Kerja", "Tanggal Awal Kerja", "Tanggal Akhir Kontrak", "Salary", "Working Status"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Badge", "Berdasarkan Nama Lengkap",
            "Berdasarkan Jenjang Karyawan", "Berdasarkan Status Kerja", "Berdasarkan Departemen", "Berdasarkan Lokasi Kerja",
            "Berdasarkan Tanggal Awal Kerja", "Berdasarkan Tanggal Akhir Kontrak", "Berdasarkan Salary",
            "Working Status"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Employee> hariLiburs;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public KaryawanFragment() {
    }

    public static KaryawanFragment newInstance() {
        KaryawanFragment fragment = new KaryawanFragment();
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

        hariLiburs = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_karyawan, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.empShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.empLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.empRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.empFabAdd);
        editSearch = (EditText) view.findViewById(R.id.empEditSearch);
        textPaging = (TextView) view.findViewById(R.id.empTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.empBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.empSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.empSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.empSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.empBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.empBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.empBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.empLayoutPaging);

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
            loadDataAll("employee_number ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("employee_number DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("fullname ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("fullname DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("employee_grade_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("employee_grade_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("employee_status ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("employee_status DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("department_name ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("department_name DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("company_workbase_name ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("company_workbase_name DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("join_date ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("join_date DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("termination_date ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("termination_date DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("is_active DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("working_status ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("working_status DESC");
        else loadDataAll("fullname ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("employee_number ASC");
        else if (position == 1 && posAD == 1)
            loadData("employee_number DESC");
        else if (position == 2 && posAD == 0)
            loadData("fullname ASC");
        else if (position == 2 && posAD == 1)
            loadData("fullname DESC");
        else if (position == 3 && posAD == 0)
            loadData("employee_grade_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("employee_grade_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("employee_status ASC");
        else if (position == 4 && posAD == 1)
            loadData("employee_status DESC");
        else if (position == 5 && posAD == 0)
            loadData("department_name ASC");
        else if (position == 5 && posAD == 1)
            loadData("department_name DESC");
        else if (position == 6 && posAD == 0)
            loadData("company_workbase_name ASC");
        else if (position == 6 && posAD == 1)
            loadData("company_workbase_name DESC");
        else if (position == 7 && posAD == 0)
            loadData("join_date ASC");
        else if (position == 7 && posAD == 1)
            loadData("join_date DESC");
        else if (position == 8 && posAD == 0)
            loadData("termination_date ASC");
        else if (position == 8 && posAD == 1)
            loadData("termination_date DESC");
        else if (position == 9 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 9 && posAD == 1)
            loadData("is_active DESC");
        else if (position == 10 && posAD == 0)
            loadData("working_status ASC");
        else if (position == 10 && posAD == 1)
            loadData("working_status DESC");
        else loadData("fullname ASC");
    }

    private void setAdapterList(){
        adapter = new KaryawanFragment.MyRecyclerViewAdapter(hariLiburs, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        hariLiburs.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            hariLiburs.add(new Employee(jsonArray.getJSONObject(i)));
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
        hariLiburs.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            hariLiburs.add(new Employee(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(Employee item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Employee> mValues;
        private final List<Employee> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Employee> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.empTextNomor.setText(""+mValues.get(position).getEmployee_number());
            holder.empTextNama.setText(""+mValues.get(position).getFullname());
            holder.empTextJenjang.setText(""+mValues.get(position).getEmployee_grade_name());
            holder.empTextStatusKerja.setText(""+mValues.get(position).getEmployee_status());
            holder.empTextDepartemen.setText(""+mValues.get(position).getDepartment_name());
            holder.empTextLokasi.setText(""+mValues.get(position).getCompany_workbase_name());
            holder.empTextAwalKontrak.setText(""+mValues.get(position).getJoin_date());
            holder.empTextAkhirKontrak.setText(""+mValues.get(position).getTermination_date());
            holder.empTextSalary.setText(""+mValues.get(position).getIs_active());
            holder.empTextStatus.setText(""+mValues.get(position).getWorking_status());

            if (position%2==0)
                holder.empLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.empLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailKaryawanActivity.class);
                    intent.putExtra("detail", mValues.get(position));
                    holder.itemView.getContext().startActivity(intent);
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
                List<Employee> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Employee) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Employee item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getEmployee_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFullname().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDepartment_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_workbase_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJoin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTermination_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getWorking_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getFullname().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getEmployee_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getEmployee_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getDepartment_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getCompany_workbase_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getJoin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getTermination_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==9){
                            if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==10){
                            if (item.getWorking_status().toLowerCase().contains(filterPattern)){
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

            public final TextView empTextNomor;
            public final TextView empTextNama;
            public final TextView empTextJenjang;
            public final TextView empTextStatusKerja;
            public final TextView empTextDepartemen;
            public final TextView empTextLokasi;
            public final TextView empTextAwalKontrak;
            public final TextView empTextAkhirKontrak;
            public final TextView empTextSalary;
            public final TextView empTextStatus;

            public final LinearLayout empLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                empTextNomor = (TextView) view.findViewById(R.id.empTextNomor);
                empTextNama = (TextView) view.findViewById(R.id.empTextNama);
                empTextJenjang = (TextView) view.findViewById(R.id.empTextJenjang);
                empTextStatusKerja = (TextView) view.findViewById(R.id.empTextStatusKerja);
                empTextDepartemen = (TextView) view.findViewById(R.id.empTextDepartemen);
                empTextLokasi = (TextView) view.findViewById(R.id.empTextLokasi);
                empTextAwalKontrak = (TextView) view.findViewById(R.id.empTextAwalKontrak);
                empTextAkhirKontrak = (TextView) view.findViewById(R.id.empTextAkhirKontrak);
                empTextSalary = (TextView) view.findViewById(R.id.empTextSalary);
                empTextStatus = (TextView) view.findViewById(R.id.empTextStatus);

                empLayoutList = (LinearLayout) view.findViewById(R.id.empLayoutList);
            }
        }
    }
}
