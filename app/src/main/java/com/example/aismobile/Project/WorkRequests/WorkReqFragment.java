package com.example.aismobile.Project.WorkRequests;

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
import com.example.aismobile.Data.Project.WorkOrder;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkReqFragment extends Fragment {

    public TextView pwrTextPaging;
    public EditText pwrEditSearch;
    public ImageView pwrBtnSearch;
    public RecyclerView pwrRecycler;
    public FloatingActionButton pwrFabAdd;
    public Spinner pwrSpinnerSearch;
    public Spinner pwrSpinnerSort;
    public Spinner pwrSpinnerSortAD;
    public Button pwrBtnShowList;
    public ImageButton pwrBtnBefore;
    public ImageButton pwrBtnNext;
    public LinearLayout pwrLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public WorkReqFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] WRSpinnerSearch = {"Semua Data", "Nomor Work Request", "Job Order", "Tanggal Awal",
            "Dibuat Oleh", "Checked By", "Approval 1", "Approval 2", "Approval 3"};
    public String[] WRSpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Work Request", "Berdasarkan Job Order",
            "Berdasarkan Tanggal Awal", "Berdasarkan Dibuat Oleh", "Berdasarkan Checked By", "Berdasarkan Approval 1",
            "Berdasarkan Approval 2", "Berdasarkan Approval 3"};
    public String[] WRADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<WorkOrder> workOrders;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public WorkReqFragment() {
        // Required empty public constructor
    }

    public static WorkReqFragment newInstance() {
        WorkReqFragment fragment = new WorkReqFragment();
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

        workOrders = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_req, container, false);;

        // Set the adapter
        pwrRecycler = (RecyclerView) view.findViewById(R.id.pwrRecycler);
        if (mColumnCount <= 1) {
            pwrRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pwrRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pwrFabAdd = (FloatingActionButton) view.findViewById(R.id.pwrFabAdd);
        pwrEditSearch = (EditText) view.findViewById(R.id.pwrEditSearch);
        pwrTextPaging = (TextView) view.findViewById(R.id.pwrTextPaging);
        pwrBtnSearch = (ImageView) view.findViewById(R.id.pwrBtnSearch);
        pwrSpinnerSearch = (Spinner) view.findViewById(R.id.pwrSpinnerSearch);
        pwrSpinnerSort = (Spinner) view.findViewById(R.id.pwrSpinnerSort);
        pwrSpinnerSortAD = (Spinner) view.findViewById(R.id.pwrSpinnerSortAD);
        pwrBtnShowList = (Button) view.findViewById(R.id.pwrBtnShowList);
        pwrBtnBefore = (ImageButton) view.findViewById(R.id.pwrBtnBefore);
        pwrBtnNext = (ImageButton) view.findViewById(R.id.pwrBtnNext);
        pwrLayoutPaging = (LinearLayout) view.findViewById(R.id.pwrLayoutPaging);

        pwrBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pwrTextPaging.getText()));
                setSortHalf(pwrSpinnerSort.getSelectedItemPosition(), pwrSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pwrTextPaging.getText()))+1;
                pwrTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pwrBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pwrTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pwrTextPaging.getText()))-2);
                    setSortHalf(pwrSpinnerSort.getSelectedItemPosition(), pwrSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pwrTextPaging.getText()))-1;
                    pwrTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pwrBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("work_order_id DESC");
                    loadAll = true;
                    params = pwrLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pwrLayoutPaging.setLayoutParams(params);
                    pwrBtnShowList.setText("Show Half");
                } else {
                    pwrTextPaging.setText("1");
                    counter = 0;
                    loadData("work_order_id DESC");
                    loadAll = false;
                    params = pwrLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pwrLayoutPaging.setLayoutParams(params);
                    pwrBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WRSpinnerSearch);
        pwrSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WRSpinnerSort);
        pwrSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WRADSpinnerSort);
        pwrSpinnerSortAD.setAdapter(spinnerAdapter);

        pwrSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pwrSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pwrSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pwrBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwrEditSearch.getText().toString().matches("")){
                    pwrSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pwrEditSearch.getText()));
            }
        });

        loadData("work_order_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("work_order_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("work_order_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("begin_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("begin_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("approval1 DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("approval2 ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("approval2 DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("approval3 ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("approval3 DESC");
        else loadDataAll("work_order_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("work_order_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("work_order_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("begin_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("begin_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadData("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 6 && posAD == 1)
            loadData("approval1 DESC");
        else if (position == 7 && posAD == 0)
            loadData("approval2 ASC");
        else if (position == 7 && posAD == 1)
            loadData("approval2 DESC");
        else if (position == 8 && posAD == 0)
            loadData("approval3 ASC");
        else if (position == 8 && posAD == 1)
            loadData("approval3 DESC");
        else loadData("work_order_id DESC");
    }

    private void setAdapterList(){
        adapter = new WorkReqFragment.MyRecyclerViewAdapter(workOrders, mListener);
        pwrRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        pwrRecycler.setAdapter(null);
        workOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workOrders.add(new WorkOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pwrEditSearch.getText().toString().matches("")){
                                pwrSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pwrEditSearch.getText()));
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
        pwrRecycler.setAdapter(null);
        workOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workOrders.add(new WorkOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pwrEditSearch.getText().toString().matches("")){
                                pwrSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pwrEditSearch.getText()));
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
        void onListFragmentInteraction(WorkOrder item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<WorkOrder> mValues;
        private final List<WorkOrder> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<WorkOrder> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pwrTextNomorWorkReq.setText(""+mValues.get(position).getWork_order_number());
            holder.pwrTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.pwrTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.pwrTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pwrTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.pwrTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pwrTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pwrTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (position%2==0)
                holder.pwrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pwrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailWorkReqActivity.class);
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
                List<WorkOrder> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((WorkOrder) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (WorkOrder item : values){
                        if (pwrSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getWork_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getWork_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwrSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
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

            public final TextView pwrTextNomorWorkReq;
            public final TextView pwrTextJobOrder;
            public final TextView pwrTextTglAwal;
            public final TextView pwrTextDibuat;
            public final TextView pwrTextCheckedBy;
            public final TextView pwrTextApproval1;
            public final TextView pwrTextApproval2;
            public final TextView pwrTextApproval3;

            public final LinearLayout pwrLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pwrTextNomorWorkReq = (TextView) view.findViewById(R.id.pwrTextNomorWorkReq);
                pwrTextJobOrder = (TextView) view.findViewById(R.id.pwrTextJobOrder);
                pwrTextTglAwal = (TextView) view.findViewById(R.id.pwrTextTglAwal);
                pwrTextDibuat = (TextView) view.findViewById(R.id.pwrTextDibuat);
                pwrTextCheckedBy = (TextView) view.findViewById(R.id.pwrTextCheckedBy);
                pwrTextApproval1 = (TextView) view.findViewById(R.id.pwrTextApproval1);
                pwrTextApproval2 = (TextView) view.findViewById(R.id.pwrTextApproval2);
                pwrTextApproval3 = (TextView) view.findViewById(R.id.pwrTextApproval3);

                pwrLayoutList = (LinearLayout) view.findViewById(R.id.pwrLayoutList);
            }
        }
    }
}
