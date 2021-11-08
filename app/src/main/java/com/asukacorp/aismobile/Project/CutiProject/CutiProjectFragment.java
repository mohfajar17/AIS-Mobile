package com.asukacorp.aismobile.Project.CutiProject;

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
import com.asukacorp.aismobile.Data.Project.Cuti;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutiProjectFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView pcTextPaging;
    public EditText pcEditSearch;
    public ImageView pcBtnSearch;
    public RecyclerView pcRecycler;
    public FloatingActionButton pcFabAdd;
    public Spinner pcSpinnerSearch;
    public Spinner pcSpinnerSort;
    public Spinner pcSpinnerSortAD;
    public Button pcBtnShowList;
    public ImageButton pcBtnBefore;
    public ImageButton pcBtnNext;
    public LinearLayout pcLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public CutiProjectFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CutiSpinnerSearch = {"Semua Data", "Leave Number", "Nama Karyawan", "Kategori Cuti",
            "Approver By", "Processed By"};
    public String[] CutiSpinnerSort = {"-- Sort By --", "Berdasarkan Leave Number", "Berdasarkan Nama Karyawan",
            "Berdasarkan Kategori Cuti", "Berdasarkan Approver By", "Berdasarkan Processed By"};
    public String[] CutiADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Cuti> cutis;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CutiProjectFragment() {
    }

    public static CutiProjectFragment newInstance() {
        CutiProjectFragment fragment = new CutiProjectFragment();
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

        sharedPrefManager = new SharedPrefManager(getContext());

        cutis = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuti_project, container, false);

        // Set the adapter
        pcRecycler = (RecyclerView) view.findViewById(R.id.pcRecycler);
        if (mColumnCount <= 1) {
            pcRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pcRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pcFabAdd = (FloatingActionButton) view.findViewById(R.id.pcFabAdd);
        pcEditSearch = (EditText) view.findViewById(R.id.pcEditSearch);
        pcTextPaging = (TextView) view.findViewById(R.id.pcTextPaging);
        pcBtnSearch = (ImageView) view.findViewById(R.id.pcBtnSearch);
        pcSpinnerSearch = (Spinner) view.findViewById(R.id.pcSpinnerSearch);
        pcSpinnerSort = (Spinner) view.findViewById(R.id.pcSpinnerSort);
        pcSpinnerSortAD = (Spinner) view.findViewById(R.id.pcSpinnerSortAD);
        pcBtnShowList = (Button) view.findViewById(R.id.pcBtnShowList);
        pcBtnBefore = (ImageButton) view.findViewById(R.id.pcBtnBefore);
        pcBtnNext = (ImageButton) view.findViewById(R.id.pcBtnNext);
        pcLayoutPaging = (LinearLayout) view.findViewById(R.id.pcLayoutPaging);

        pcFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCreateAccess("employee-leave:view-khusus");
            }
        });

        pcBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pcTextPaging.getText()));
                setSortHalf(pcSpinnerSort.getSelectedItemPosition(), pcSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pcTextPaging.getText()))+1;
                pcTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pcBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pcTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pcTextPaging.getText()))-2);
                    setSortHalf(pcSpinnerSort.getSelectedItemPosition(), pcSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pcTextPaging.getText()))-1;
                    pcTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pcBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("employee_leave_id DESC");
                    loadAll = true;
                    params = pcLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pcLayoutPaging.setLayoutParams(params);
                    pcBtnShowList.setText("Show Half");
                } else {
                    pcTextPaging.setText("1");
                    counter = 0;
                    loadData("employee_leave_id DESC");
                    loadAll = false;
                    params = pcLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pcLayoutPaging.setLayoutParams(params);
                    pcBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CutiSpinnerSearch);
        pcSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CutiSpinnerSort);
        pcSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CutiADSpinnerSort);
        pcSpinnerSortAD.setAdapter(spinnerAdapter);

        pcSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pcSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pcSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pcBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pcEditSearch.getText().toString().matches("")){
                    pcSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pcEditSearch.getText()));
            }
        });

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("employee_leave_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("employee_leave_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("employee ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("employee DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("leave_category_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("leave_category_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("approver ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("approver DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("processed_by ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("processed_by DESC");
        else loadDataAll("employee_leave_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("employee_leave_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("employee_leave_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("employee ASC");
        else if (position == 2 && posAD == 1)
            loadData("employee DESC");
        else if (position == 3 && posAD == 0)
            loadData("leave_category_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("leave_category_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("approver ASC");
        else if (position == 4 && posAD == 1)
            loadData("approver DESC");
        else if (position == 5 && posAD == 0)
            loadData("processed_by ASC");
        else if (position == 5 && posAD == 1)
            loadData("processed_by DESC");
        else loadData("employee_leave_id DESC");
    }

    private void setAdapterList(){
        adapter = new CutiProjectFragment.MyRecyclerViewAdapter(cutis, mListener);
        pcRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        pcRecycler.setAdapter(null);
        cutis.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_PROJECT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cutis.add(new Cuti(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pcEditSearch.getText().toString().matches("")){
                                pcSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pcEditSearch.getText()));
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
        pcRecycler.setAdapter(null);
        cutis.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_PROJECT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cutis.add(new Cuti(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pcEditSearch.getText().toString().matches("")){
                                pcSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pcEditSearch.getText()));
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
        void onListFragmentInteraction(Cuti item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Cuti> mValues;
        private final List<Cuti> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Cuti> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cuti_project_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pcTextLeaveNumber.setText(""+mValues.get(position).getEmployee_leave_number());
            holder.pcTextNamaKaryawan.setText(""+mValues.get(position).getEmployee());
            holder.pcTextKategoriCuti.setText(""+mValues.get(position).getLeave_category_name());
            holder.pcTextApprover.setText(""+mValues.get(position).getApprover());
            holder.pcTextProcessed.setText(""+mValues.get(position).getProcessed_by());

            if (position%2==0)
                holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess(mValues.get(position), 1, "employee-leave:view-khusus");
                }
            });

            holder.pcBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.valueOf(mValues.get(position).getIs_approved())<1)
                        loadAccess(mValues.get(position), 2, "employee-leave:update-khusus");
                    else Toast.makeText(getActivity(), "Employee Leave has been Approve", Toast.LENGTH_LONG).show();
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
                List<Cuti> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Cuti) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Cuti item : values){
                        if (pcSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getEmployee_leave_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLeave_category_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApprover().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getProcessed_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_leave_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getLeave_category_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getApprover().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getProcessed_by().toLowerCase().contains(filterPattern)){
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

            public final TextView pcTextLeaveNumber;
            public final TextView pcTextNamaKaryawan;
            public final TextView pcTextKategoriCuti;
            public final TextView pcTextApprover;
            public final TextView pcTextProcessed;

            public final ImageView pcBtnEdit;
            public final LinearLayout pskLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pcTextLeaveNumber = (TextView) view.findViewById(R.id.pcTextLeaveNumber);
                pcTextNamaKaryawan = (TextView) view.findViewById(R.id.pcTextNamaKaryawan);
                pcTextKategoriCuti = (TextView) view.findViewById(R.id.pcTextKategoriCuti);
                pcTextApprover = (TextView) view.findViewById(R.id.pcTextApprover);
                pcTextProcessed = (TextView) view.findViewById(R.id.pcTextProcessed);

                pcBtnEdit = (ImageView) view.findViewById(R.id.pcBtnEdit);
                pskLayoutList = (LinearLayout) view.findViewById(R.id.pcLayoutList);
            }
        }
    }

    private void loadAccess(final Cuti cuti, final int code, final String access) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    int isMobile=jsonObject.getInt("is_mobile");
                    if (Integer.valueOf(jsonData.getString("view"))==1 && isMobile==1 && code==1){
                        Intent intent = new Intent(getActivity(), DetailCutiProjectActivity.class);
                        intent.putExtra("detail", cuti);
                        intent.putExtra("code", 0);
                        getContext().startActivity(intent);
                    } else if (Integer.valueOf(jsonData.getString("update"))==1 && isMobile==1 && code==2){
                        Intent intent = new Intent(getActivity(), EditCutiProjectActivity.class);
                        intent.putExtra("idEmpLeave", cuti.getEmployee_leave_id());
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You don't have access", Toast.LENGTH_LONG).show();
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
                param.put("user_id", sharedPrefManager.getUserId());
                param.put("access", access);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void loadCreateAccess(final String access) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    int isMobile=jsonObject.getInt("is_mobile");
                    if (Integer.valueOf(jsonData.getString("create"))==1 && isMobile==1){
                        Intent intent = new Intent(getActivity(), BuatCutiProjectActivity.class);
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You don't have access", Toast.LENGTH_LONG).show();
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
                param.put("user_id", sharedPrefManager.getUserId());
                param.put("access", access);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}