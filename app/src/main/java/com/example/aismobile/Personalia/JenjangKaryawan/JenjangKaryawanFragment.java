package com.example.aismobile.Personalia.JenjangKaryawan;

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
import com.example.aismobile.Data.Personalia.EmployeeGrade;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JenjangKaryawanFragment extends Fragment {

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
    public JenjangKaryawanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Nama Jenjang", "Kode Laporan", "Pangkat", "Jabatan",
            "Golongan Gaji", "Overtime Limit", "Aktif"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Nama Jenjang", "Berdasarkan Kode Laporan",
            "Berdasarkan Pangkat", "Berdasarkan Jabatan", "Berdasarkan Golongan Gaji", "Berdasarkan Overtime Limit",
            "Berdasarkan Aktif"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<EmployeeGrade> employeeGrades;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public JenjangKaryawanFragment() {
    }

    public static JenjangKaryawanFragment newInstance() {
        JenjangKaryawanFragment fragment = new JenjangKaryawanFragment();
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

        employeeGrades = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jenjang_karyawan, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.jkShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.jkLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.jkRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.jkFabAdd);
        editSearch = (EditText) view.findViewById(R.id.jkEditSearch);
        textPaging = (TextView) view.findViewById(R.id.jkTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.jkBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.jkSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.jkSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.jkSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.jkBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.jkBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.jkBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.jkLayoutPaging);

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
                    loadDataAll("employee_grade_name ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("employee_grade_name ASC");
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
                    loadData("employee_grade_name ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("employee_grade_name ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("employee_grade_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("employee_grade_name DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("report_code ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("report_code DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("jobtitle_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("jobtitle_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("job_grade_name ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("job_grade_name DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("salary_grade_name ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("salary_grade_name DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("overtime_limit ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("overtime_limit DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("employee_grade_name ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("employee_grade_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("employee_grade_name DESC");
        else if (position == 2 && posAD == 0)
            loadData("report_code ASC");
        else if (position == 2 && posAD == 1)
            loadData("report_code DESC");
        else if (position == 3 && posAD == 0)
            loadData("jobtitle_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("jobtitle_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("job_grade_name ASC");
        else if (position == 4 && posAD == 1)
            loadData("job_grade_name DESC");
        else if (position == 5 && posAD == 0)
            loadData("salary_grade_name ASC");
        else if (position == 5 && posAD == 1)
            loadData("salary_grade_name DESC");
        else if (position == 6 && posAD == 0)
            loadData("overtime_limit ASC");
        else if (position == 6 && posAD == 1)
            loadData("overtime_limit DESC");
        else if (position == 7 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 7 && posAD == 1)
            loadData("is_active DESC");
        else loadData("employee_grade_name ASC");
    }

    private void setAdapterList(){
        adapter = new JenjangKaryawanFragment.MyRecyclerViewAdapter(employeeGrades, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        employeeGrades.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_GRADE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeGrades.add(new EmployeeGrade(jsonArray.getJSONObject(i)));
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
        employeeGrades.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_GRADE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeGrades.add(new EmployeeGrade(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(EmployeeGrade item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<EmployeeGrade> mValues;
        private final List<EmployeeGrade> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<EmployeeGrade> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_jenjang_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.jkTextNama.setText(""+mValues.get(position).getEmployee_grade_name());
            holder.jkTextKode.setText(""+mValues.get(position).getReport_code());
            holder.jkTextPangkat.setText(""+mValues.get(position).getJobtitle_name());
            holder.jkTextJabatan.setText(""+mValues.get(position).getJob_grade_name());
            holder.jkTextGolongan.setText(""+mValues.get(position).getSalary_grade_name());
            holder.jkTextCount.setText("");
            holder.jkTextLimit.setText(""+mValues.get(position).getOvertime_limit());
            holder.jkTextAktif.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.jkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.jkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailJenjangKaryawanActivity.class);
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
                List<EmployeeGrade> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((EmployeeGrade) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (EmployeeGrade item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getEmployee_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReport_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJobtitle_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSalary_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getOvertime_limit().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getReport_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJobtitle_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getJob_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getSalary_grade_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getOvertime_limit().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
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

            public final TextView jkTextNama;
            public final TextView jkTextKode;
            public final TextView jkTextPangkat;
            public final TextView jkTextJabatan;
            public final TextView jkTextGolongan;
            public final TextView jkTextCount;
            public final TextView jkTextLimit;
            public final TextView jkTextAktif;

            public final LinearLayout jkLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                jkTextNama = (TextView) view.findViewById(R.id.jkTextNama);
                jkTextKode = (TextView) view.findViewById(R.id.jkTextKode);
                jkTextPangkat = (TextView) view.findViewById(R.id.jkTextPangkat);
                jkTextJabatan = (TextView) view.findViewById(R.id.jkTextJabatan);
                jkTextGolongan = (TextView) view.findViewById(R.id.jkTextGolongan);
                jkTextCount = (TextView) view.findViewById(R.id.jkTextCount);
                jkTextLimit = (TextView) view.findViewById(R.id.jkTextLimit);
                jkTextAktif = (TextView) view.findViewById(R.id.jkTextAktif);

                jkLayoutList = (LinearLayout) view.findViewById(R.id.jkLayoutList);
            }
        }
    }
}