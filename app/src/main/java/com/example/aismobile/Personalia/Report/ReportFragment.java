package com.example.aismobile.Personalia.Report;

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
import com.example.aismobile.Data.Personalia.EmployeeReport;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFragment extends Fragment {

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
    public ReportFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Bulan dan Tahun", "Karyawan", "Start Job Code",
            "Nama Lokasi Kerja", "Person", "Status Kerja"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Bulan dan Tahun", "Berdasarkan Karyawan",
            "Berdasarkan Job Code", "Berdasarkan Nama Lokasi Kerja", "Berdasarkan Gaji Pokok", "Total Tunjangan",
            "Lembur", "Absent", "Koreksi", "Total Pendapatan", "Tunjangan Lokasi", "Tunjangan Perjalanan Dinas",
            "Total Potongan", "Take Home Pay", "Total Biaya", "Person", "Status Kerja"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<EmployeeReport> employeeReports;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
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

        employeeReports = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.rShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.rLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.rRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.rFabAdd);
        editSearch = (EditText) view.findViewById(R.id.rEditSearch);
        textPaging = (TextView) view.findViewById(R.id.rTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.rBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.rSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.rSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.rSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.rBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.rBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.rBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.rLayoutPaging);

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
                    loadDataAll("employee_report_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("employee_report_id DESC");
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
                    loadData("employee_report_id DESC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("employee_report_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("month_year ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("month_year DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("employee ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("employee DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("job_order_number ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("job_order_number DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("company_workbase_name ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("company_workbase_name DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("basic_salary ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("basic_salary DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("total_tunjangan ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("total_tunjangan DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("overtime ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("overtime DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("absent ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("absent DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("less_payment ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("less_payment DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("total_pendapatan ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("total_pendapatan DESC");
        else if (position == 11 && posAD == 0)
            loadDataAll("location_project_allowance ASC");
        else if (position == 11 && posAD == 1)
            loadDataAll("location_project_allowance DESC");
        else if (position == 12 && posAD == 0)
            loadDataAll("official_travel_allowance ASC");
        else if (position == 12 && posAD == 1)
            loadDataAll("official_travel_allowance DESC");
        else if (position == 13 && posAD == 0)
            loadDataAll("total_potongan ASC");
        else if (position == 13 && posAD == 1)
            loadDataAll("total_potongan DESC");
        else if (position == 14 && posAD == 0)
            loadDataAll("total_dibayar ASC");
        else if (position == 14 && posAD == 1)
            loadDataAll("total_dibayar DESC");
        else if (position == 15 && posAD == 0)
            loadDataAll("total_biaya ASC");
        else if (position == 15 && posAD == 1)
            loadDataAll("total_biaya DESC");
        else if (position == 16 && posAD == 0)
            loadDataAll("person ASC");
        else if (position == 16 && posAD == 1)
            loadDataAll("person DESC");
        else if (position == 17 && posAD == 0)
            loadDataAll("employee_status ASC");
        else if (position == 17 && posAD == 1)
            loadDataAll("employee_status DESC");
        else loadDataAll("employee_report_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("month_year ASC");
        else if (position == 1 && posAD == 1)
            loadData("month_year DESC");
        else if (position == 2 && posAD == 0)
            loadData("employee ASC");
        else if (position == 2 && posAD == 1)
            loadData("employee DESC");
        else if (position == 3 && posAD == 0)
            loadData("job_order_number ASC");
        else if (position == 3 && posAD == 1)
            loadData("job_order_number DESC");
        else if (position == 4 && posAD == 0)
            loadData("company_workbase_name ASC");
        else if (position == 4 && posAD == 1)
            loadData("company_workbase_name DESC");
        else if (position == 5 && posAD == 0)
            loadData("basic_salary ASC");
        else if (position == 5 && posAD == 1)
            loadData("basic_salary DESC");
        else if (position == 6 && posAD == 0)
            loadData("total_tunjangan ASC");
        else if (position == 6 && posAD == 1)
            loadData("total_tunjangan DESC");
        else if (position == 7 && posAD == 0)
            loadData("overtime ASC");
        else if (position == 7 && posAD == 1)
            loadData("overtime DESC");
        else if (position == 8 && posAD == 0)
            loadData("absent ASC");
        else if (position == 8 && posAD == 1)
            loadData("absent DESC");
        else if (position == 9 && posAD == 0)
            loadData("less_payment ASC");
        else if (position == 9 && posAD == 1)
            loadData("less_payment DESC");
        else if (position == 10 && posAD == 0)
            loadData("total_pendapatan ASC");
        else if (position == 10 && posAD == 1)
            loadData("total_pendapatan DESC");
        else if (position == 11 && posAD == 0)
            loadData("location_project_allowance ASC");
        else if (position == 11 && posAD == 1)
            loadData("location_project_allowance DESC");
        else if (position == 12 && posAD == 0)
            loadData("official_travel_allowance ASC");
        else if (position == 12 && posAD == 1)
            loadData("official_travel_allowance DESC");
        else if (position == 13 && posAD == 0)
            loadData("total_potongan ASC");
        else if (position == 13 && posAD == 1)
            loadData("total_potongan DESC");
        else if (position == 14 && posAD == 0)
            loadData("total_dibayar ASC");
        else if (position == 14 && posAD == 1)
            loadData("total_dibayar DESC");
        else if (position == 15 && posAD == 0)
            loadData("total_biaya ASC");
        else if (position == 15 && posAD == 1)
            loadData("total_biaya DESC");
        else if (position == 16 && posAD == 0)
            loadData("person ASC");
        else if (position == 16 && posAD == 1)
            loadData("person DESC");
        else if (position == 17 && posAD == 0)
            loadData("employee_status ASC");
        else if (position == 17 && posAD == 1)
            loadData("employee_status DESC");
        else loadData("employee_report_id DESC");
    }

    private void setAdapterList(){
        adapter = new ReportFragment.MyRecyclerViewAdapter(employeeReports, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        employeeReports.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_REPORT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeReports.add(new EmployeeReport(jsonArray.getJSONObject(i)));
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
        employeeReports.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_REPORT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeReports.add(new EmployeeReport(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(EmployeeReport item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<EmployeeReport> mValues;
        private final List<EmployeeReport> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<EmployeeReport> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_report_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            double toDouble = 0;
            NumberFormat formatter = new DecimalFormat("#,###");

            holder.rTextBulan.setText(mValues.get(position).getMonth_year());
            holder.rTextKaryawan.setText(mValues.get(position).getEmployee());
            holder.rTextJobCode.setText(mValues.get(position).getJob_order_number());
            holder.rTextLokasiKerja.setText(mValues.get(position).getCompany_workbase_name());
            toDouble = Double.valueOf(mValues.get(position).getBasic_salary());
            holder.rTextGajiPokok.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getTotal_tunjangan());
            holder.rTextTotTunjangan.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getOvertime());
            holder.rTextLembur.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getAbsent());
            holder.rTextAbsent.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getLess_payment());
            holder.rTextKoreksi.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getTotal_pendapatan());
            holder.rTextTotPendapatan.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getLocation_project_allowance());
            holder.rTextTunLokasi.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getOfficial_travel_allowance());
            holder.rTextTunPerjalanan.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getTotal_potongan());
            holder.rTextTotPotongan.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getTotal_dibayar());
            holder.rTextTakeHome.setText(""+formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getTotal_biaya());
            holder.rTextTotBiaya.setText(""+formatter.format((long) toDouble));
            holder.rTextPerson.setText(mValues.get(position).getPerson());
            holder.rTextStatus.setText(mValues.get(position).getEmployee_status());

            if (position%2==0)
                holder.rLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.rLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
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
                List<EmployeeReport> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((EmployeeReport) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (EmployeeReport item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getMonth_year().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_workbase_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPerson().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getMonth_year().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCompany_workbase_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getPerson().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getEmployee_status().toLowerCase().contains(filterPattern)){
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

            public final TextView rTextBulan;
            public final TextView rTextKaryawan;
            public final TextView rTextJobCode;
            public final TextView rTextLokasiKerja;
            public final TextView rTextGajiPokok;
            public final TextView rTextTotTunjangan;
            public final TextView rTextLembur;
            public final TextView rTextAbsent;
            public final TextView rTextKoreksi;
            public final TextView rTextTotPendapatan;
            public final TextView rTextTunLokasi;
            public final TextView rTextTunPerjalanan;
            public final TextView rTextTotPotongan;
            public final TextView rTextTakeHome;
            public final TextView rTextTotBiaya;
            public final TextView rTextPerson;
            public final TextView rTextStatus;

            public final LinearLayout rLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                rTextBulan = (TextView) view.findViewById(R.id.rTextBulan);
                rTextKaryawan = (TextView) view.findViewById(R.id.rTextKaryawan);
                rTextJobCode = (TextView) view.findViewById(R.id.rTextJobCode);
                rTextLokasiKerja = (TextView) view.findViewById(R.id.rTextLokasiKerja);
                rTextGajiPokok = (TextView) view.findViewById(R.id.rTextGajiPokok);
                rTextTotTunjangan = (TextView) view.findViewById(R.id.rTextTotTunjangan);
                rTextLembur = (TextView) view.findViewById(R.id.rTextLembur);
                rTextAbsent = (TextView) view.findViewById(R.id.rTextAbsent);
                rTextKoreksi = (TextView) view.findViewById(R.id.rTextKoreksi);
                rTextTotPendapatan = (TextView) view.findViewById(R.id.rTextTotPendapatan);
                rTextTunLokasi = (TextView) view.findViewById(R.id.rTextTunLokasi);
                rTextTunPerjalanan = (TextView) view.findViewById(R.id.rTextTunPerjalanan);
                rTextTotPotongan = (TextView) view.findViewById(R.id.rTextTotPotongan);
                rTextTakeHome = (TextView) view.findViewById(R.id.rTextTakeHome);
                rTextTotBiaya = (TextView) view.findViewById(R.id.rTextTotBiaya);
                rTextPerson = (TextView) view.findViewById(R.id.rTextPerson);
                rTextStatus = (TextView) view.findViewById(R.id.rTextStatus);

                rLayoutList = (LinearLayout) view.findViewById(R.id.rLayoutList);
            }
        }
    }
}
