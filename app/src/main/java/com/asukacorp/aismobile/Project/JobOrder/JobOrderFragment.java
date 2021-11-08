package com.asukacorp.aismobile.Project.JobOrder;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.JobOrder;
import com.asukacorp.aismobile.Marketing.JoPictures.AddPicturesActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
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

public class JobOrderFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    private String empDepartemen;

    public TextView textViewList;
    public EditText editTextSearchJO;
    public ImageView imageSearchJO;
    public RecyclerView recyclerView;
    public FloatingActionButton fab0, fab1, fab2;
    public Spinner spinnerSearchJO;
    public Spinner spinnerSortJO;
    public Spinner spinnerSortADJO;
    public Button buttonShowAllList;
    public ImageButton buttonNext;
    public ImageButton buttonBefore;
    public LinearLayout layoutListJO;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public JobOrderFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] JOSpinnerSearch = {"Semua Data", "Nomor Job Order", "Departemen", "PIC", "Tipe Job Order",
            "Keterangan Job Order", "Nilai Job Order", "Status Job Order"};
    public String[] JOSpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Job Order", "Berdasarkan Departemen",
            "Berdasarkan PIC", "Berdasarkan Tipe", "Berdasarkan Nilai", "Berdasarkan Status"};
    public String[] JOADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<JobOrder> jobOrders;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;
    public boolean flag = true;

    public JobOrderFragment() {
    }

    public static JobOrderFragment newInstance() {
        JobOrderFragment fragment = new JobOrderFragment();
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

        jobOrders = new ArrayList<>();
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_order, container, false);

        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerJobOrder);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fab0 = (FloatingActionButton) view.findViewById(R.id.fab0);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    fab1.show();
                    fab2.show();
                    fab1.animate().translationY(-(fab0.getCustomSize()+20));
                    fab2.animate().translationY(-(fab0.getCustomSize()+fab1.getCustomSize()+40));

                    fab0.setImageResource(R.drawable.ic_close);
                    flag = false;

                }else {
                    fab1.hide();
                    fab2.hide();
                    fab1.animate().translationY(0);
                    fab2.animate().translationY(0);

                    fab0.setImageResource(R.drawable.ic_add);
                    flag = true;

                }
            }
        });

        editTextSearchJO = (EditText) view.findViewById(R.id.editTextSearchJO);
        textViewList = (TextView) view.findViewById(R.id.textViewList);
        imageSearchJO = (ImageView) view.findViewById(R.id.imageSearchJO);
        spinnerSearchJO = (Spinner) view.findViewById(R.id.spinnerSearchJO);
        spinnerSortJO = (Spinner) view.findViewById(R.id.spinnerSortJO);
        spinnerSortADJO = (Spinner) view.findViewById(R.id.spinnerSortADJO);
        buttonShowAllList = (Button) view.findViewById(R.id.buttonShowAllList);
        buttonNext = (ImageButton) view.findViewById(R.id.buttonNext);
        buttonBefore = (ImageButton) view.findViewById(R.id.buttonBefore);
        layoutListJO = (LinearLayout) view.findViewById(R.id.layoutListJO);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(textViewList.getText()));
                setSortHalf(spinnerSortJO.getSelectedItemPosition(), spinnerSortADJO.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(textViewList.getText()))+1;
                textViewList.setText(""+textValue);
                filter = true;
            }
        });
        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(textViewList.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(textViewList.getText()))-2);
                    setSortHalf(spinnerSortJO.getSelectedItemPosition(), spinnerSortADJO.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(textViewList.getText()))-1;
                    textViewList.setText(""+textValue);
                    filter = true;
                }
            }
        });

        buttonShowAllList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadJobOrderAll("job_order_id DESC");
                    loadAll = true;
                    params = layoutListJO.getLayoutParams();
                    params.height = 0;
                    layoutListJO.setLayoutParams(params);
                    buttonShowAllList.setText("Show Half");
                } else {
                    textViewList.setText("1");
                    counter = 0;
                    loadJobOrder("job_order_id DESC");
                    loadAll = false;
                    params = layoutListJO.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutListJO.setLayoutParams(params);
                    buttonShowAllList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, JOSpinnerSearch);
        spinnerSearchJO.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, JOSpinnerSort);
        spinnerSortJO.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, JOADSpinnerSort);
        spinnerSortADJO.setAdapter(spinnerAdapter);

        spinnerSortJO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, spinnerSortADJO.getSelectedItemPosition());
                } else {
                    setSortHalf(position, spinnerSortADJO.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        imageSearchJO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearchJO.getText().toString().matches("")){
                    spinnerSearchJO.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(editTextSearchJO.getText()));
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPicturesActivity.class);
                startActivityForResult(intent,1);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuatJobOrderActivity.class);
                startActivityForResult(intent,1);
            }
        });

//        loadJobOrder("job_order_id DESC");
        loadEmployeeData();

        return view;
    }

    private void loadEmployeeData() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        empDepartemen = jsonData.getString("department_id");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("employee_id", sharedPrefManager.getEmployeeId());
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void setSortAll(int position, int posAD){
        if(position == 1 && posAD == 0)
            loadJobOrderAll("job_order_id ASC");
        else if(position == 1 && posAD == 1)
            loadJobOrderAll("job_order_id DESC");
        else if(position == 2 && posAD == 0)
            loadJobOrderAll("department_id ASC");
        else if(position == 2 && posAD == 1)
            loadJobOrderAll("department_id DESC");
        else if(position == 3 && posAD == 0)
            loadJobOrderAll("supervisor ASC");
        else if(position == 3 && posAD == 1)
            loadJobOrderAll("supervisor DESC");
        else if(position == 4 && posAD == 0)
            loadJobOrderAll("job_order_type ASC");
        else if(position == 4 && posAD == 1)
            loadJobOrderAll("job_order_type DESC");
        else if(position == 5 && posAD == 0)
            loadJobOrderAll("amount ASC");
        else if(position == 5 && posAD == 1)
            loadJobOrderAll("amount DESC");
        else if(position == 6 && posAD == 0)
            loadJobOrderAll("job_order_status ASC");
        else if(position == 6 && posAD == 1)
            loadJobOrderAll("job_order_status DESC");
        else loadJobOrderAll("job_order_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if(position == 1 && posAD == 0)
            loadJobOrder("job_order_id ASC");
        else if(position == 1 && posAD == 1)
            loadJobOrder("job_order_id DESC");
        else if(position == 2 && posAD == 0)
            loadJobOrder("department_id ASC");
        else if(position == 2 && posAD == 1)
            loadJobOrder("department_id DESC");
        else if(position == 3 && posAD == 0)
            loadJobOrder("supervisor ASC");
        else if(position == 3 && posAD == 1)
            loadJobOrder("supervisor DESC");
        else if(position == 4 && posAD == 0)
            loadJobOrder("job_order_type ASC");
        else if(position == 4 && posAD == 1)
            loadJobOrder("job_order_type DESC");
        else if(position == 5 && posAD == 0)
            loadJobOrder("amount ASC");
        else if(position == 5 && posAD == 1)
            loadJobOrder("amount DESC");
        else if(position == 6 && posAD == 0)
            loadJobOrder("job_order_status ASC");
        else if(position == 6 && posAD == 1)
            loadJobOrder("job_order_status DESC");
        else loadJobOrder("job_order_id DESC");
    }

    private void setAdapterList(){
        adapter = new JobOrderFragment.MyRecyclerViewAdapter(jobOrders, mListener);
        recyclerView.setAdapter(adapter);
    }

    private void loadJobOrderAll(final String sortBy) {
        progressDialog.show();
        recyclerView.setAdapter(null);
        jobOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            jobOrders.add(new JobOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editTextSearchJO.getText().toString().matches("")){
                                spinnerSearchJO.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(editTextSearchJO.getText()));
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

    public void loadJobOrder(final String sortBy){
        progressDialog.show();
        recyclerView.setAdapter(null);
        jobOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            jobOrders.add(new JobOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editTextSearchJO.getText().toString().matches("")){
                                spinnerSearchJO.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(editTextSearchJO.getText()));
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
//            loadJobOrder();
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
        void onListFragmentInteraction(JobOrder item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<JobOrder> mValues;
        private final List<JobOrder> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<JobOrder> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_job_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.JONomor.setText(""+mValues.get(position).getJob_order_number());
            holder.JODepartemen.setText(""+mValues.get(position).getDepartment_id());
            holder.JOPic.setText(""+mValues.get(position).getSupervisor());
            holder.JOTipeJob.setText(""+mValues.get(position).getJob_order_type());
            holder.JOKeterangan.setText(""+mValues.get(position).getJob_order_description());
            holder.JOStatus.setText(""+mValues.get(position).getJob_order_status());

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.JONilai.setText("Rp. "+ formatter.format(Long.valueOf(mValues.get(position).getAmount())));

            if (position%2==0)
                holder.layoutJobOrderColor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layoutJobOrderColor.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.JOEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.valueOf(sharedPrefManager.getUserId()) == 1 || //akses untuk admin
                            Integer.valueOf(sharedPrefManager.getUserId()) == 5 || //update hanya untuk pak alfan, pak bambang, bu ida
                            Integer.valueOf(sharedPrefManager.getUserId()) == 17 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 34 ||
                            Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1 ||
                            Integer.valueOf(sharedPrefManager.getEmployeeId()) == 5 ||
                            Integer.valueOf(sharedPrefManager.getEmployeeId()) == 77) {
                        Intent intent = new Intent(getActivity(), UpdateJobOrderActivity.class);
                        intent.putExtra("detail", mValues.get(position));
                        holder.itemView.getContext().startActivity(intent);
                    } else Toast.makeText(getActivity(), "You don't have access to edit Job Order", Toast.LENGTH_LONG).show();
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int canView = 0;
                    if (Integer.valueOf(sharedPrefManager.getUserId()) == 1 || Integer.valueOf(sharedPrefManager.getUserId()) == 5 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 34 || Integer.valueOf(sharedPrefManager.getUserId()) == 107 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 17 || Integer.valueOf(sharedPrefManager.getUserId()) == 19 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 35 || Integer.valueOf(sharedPrefManager.getUserId()) == 166 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 181 || Integer.valueOf(sharedPrefManager.getUserId()) == 154 ||
                            Integer.valueOf(sharedPrefManager.getUserId()) == 300)
                        canView = 1;
                    else {
                        if (Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 5 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 393 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 77 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 149 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 85 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 57 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3234 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3249 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1695 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 2111 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1955 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3133 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 2063 ||
                                Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3533 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3535)
                            canView = 1;
                        else {
                            if (mValues.get(position).getSupervisor().toLowerCase().contains(sharedPrefManager.getUserDisplayName().toLowerCase()))
                                canView = 1;
                            else {
                                if (empDepartemen.toLowerCase().contains(mValues.get(position).getDepartment_id().toLowerCase())){
                                    if (Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1354 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 1275 ||
                                            Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3202 || Integer.valueOf(sharedPrefManager.getEmployeeId()) == 3538)
                                        canView = 1;
                                    else canView = 0;
                                } else canView = 0;
                            }
                        }
                    }

                    if (mValues.get(position).getJob_order_id()==1371){
                        if (Integer.valueOf(sharedPrefManager.getUserId())==1 || Integer.valueOf(sharedPrefManager.getUserId())==5 || Integer.valueOf(sharedPrefManager.getEmployeeId())==1)
                            canView=1;
                        else canView = 0;
                    }

                    if (mValues.get(position).getDepartment_id().toLowerCase().contains("Office Pasuruan".toLowerCase())){
                        if (!sharedPrefManager.getEmployeeId().equals("null"))
                            if (Integer.valueOf(sharedPrefManager.getEmployeeId())==149 || Integer.valueOf(sharedPrefManager.getEmployeeId())==289)
                                canView = 1;
                    }

                    if (canView == 1){
                        loadAccess("" + mValues.get(position).getJob_order_id(), mValues.get(position));
                    } else Toast.makeText(getActivity(), "You don't have access to view Job Order", Toast.LENGTH_LONG).show();
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
                List<JobOrder> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((JobOrder) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (JobOrder item : values){
                        if (spinnerSearchJO.getSelectedItemPosition()==0){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDepartment_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupervisor().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAmount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==1){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==2){
                            if (item.getDepartment_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==3){
                            if (item.getSupervisor().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==4){
                            if (item.getJob_order_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==5){
                            if (item.getJob_order_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==6){
                            if (item.getAmount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearchJO.getSelectedItemPosition()==7){
                            if (item.getJob_order_status().toLowerCase().contains(filterPattern)){
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

            public final TextView JONomor;
            public final TextView JODepartemen;
            public final TextView JOPic;
            public final TextView JOTipeJob;
            public final TextView JOKeterangan;
            public final TextView JONilai;
            public final TextView JOStatus;
            public final LinearLayout JOEdit;
            public final LinearLayout layoutJobOrderColor;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                JONomor = (TextView) view.findViewById(R.id.JONomor);
                JODepartemen = (TextView) view.findViewById(R.id.JODepartemen);
                JOPic = (TextView) view.findViewById(R.id.JOPic);
                JOTipeJob = (TextView) view.findViewById(R.id.JOTipeJob);
                JOKeterangan = (TextView) view.findViewById(R.id.JOKeterangan);
                JONilai = (TextView) view.findViewById(R.id.JONilai);
                JOStatus = (TextView) view.findViewById(R.id.JOStatus);
                JOEdit = (LinearLayout) view.findViewById(R.id.JOEdit);
                layoutJobOrderColor = (LinearLayout) view.findViewById(R.id.layoutJobOrderColor);
            }
        }
    }

    private void loadAccess(final String id, final JobOrder jobOrder) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), JobOrderDetailActivity.class);
                        intent.putExtra("detailJO", jobOrder);
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You don't have access", Toast.LENGTH_LONG).show();
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
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "job-order");
                param.put("access", "" + "job-order:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
