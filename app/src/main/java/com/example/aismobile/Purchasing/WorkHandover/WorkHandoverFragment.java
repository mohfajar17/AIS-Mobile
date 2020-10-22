package com.example.aismobile.Purchasing.WorkHandover;

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
import com.example.aismobile.Data.Purchasing.WorkHandover;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkHandoverFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView whTextPaging;
    public EditText whEditSearch;
    public ImageView whBtnSearch;
    public RecyclerView whRecycler;
    public FloatingActionButton whFabAdd;
    public Spinner whSpinnerSearch;
    public Spinner whSpinnerSort;
    public Spinner whSpinnerSortAD;
    public Button whBtnShowList;
    public ImageButton whBtnBefore;
    public ImageButton whBtnNext;
    public LinearLayout whLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public WorkHandoverFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] WHSpinnerSearch = {"Semua Data", "Work Handover Number", "Tanggal Penerimaan", "Work Order",
            "Supplier", "Purchase Service Notes", "Diakui"};
    public String[] WHSpinnerSort = {"-- Sort By --", "Berdasarkan Work Handover Number", "Berdasarkan Tanggal Penerimaan",
            "Berdasarkan Work Order", "Berdasarkan Supplier", "Berdasarkan Purchase Service Notes", "Berdasarkan Diakui"};
    public String[] WHADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<WorkHandover> workHandovers;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public WorkHandoverFragment() {
    }

    public static WorkHandoverFragment newInstance() {
        WorkHandoverFragment fragment = new WorkHandoverFragment();
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

        workHandovers = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_handover, container, false);

        // Set the adapter
        whRecycler = (RecyclerView) view.findViewById(R.id.whRecycler);
        if (mColumnCount <= 1) {
            whRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            whRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        whFabAdd = (FloatingActionButton) view.findViewById(R.id.whFabAdd);
        whEditSearch = (EditText) view.findViewById(R.id.whEditSearch);
        whTextPaging = (TextView) view.findViewById(R.id.whTextPaging);
        whBtnSearch = (ImageView) view.findViewById(R.id.whBtnSearch);
        whSpinnerSearch = (Spinner) view.findViewById(R.id.whSpinnerSearch);
        whSpinnerSort = (Spinner) view.findViewById(R.id.whSpinnerSort);
        whSpinnerSortAD = (Spinner) view.findViewById(R.id.whSpinnerSortAD);
        whBtnShowList = (Button) view.findViewById(R.id.whBtnShowList);
        whBtnBefore = (ImageButton) view.findViewById(R.id.whBtnBefore);
        whBtnNext = (ImageButton) view.findViewById(R.id.whBtnNext);
        whLayoutPaging = (LinearLayout) view.findViewById(R.id.whLayoutPaging);

        whBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(whTextPaging.getText()));
                setSortHalf(whSpinnerSort.getSelectedItemPosition(), whSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(whTextPaging.getText()))+1;
                whTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        whBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(whTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(whTextPaging.getText()))-2);
                    setSortHalf(whSpinnerSort.getSelectedItemPosition(), whSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(whTextPaging.getText()))-1;
                    whTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        whBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("work_handover_id DESC");
                    loadAll = true;
                    params = whLayoutPaging.getLayoutParams();
                    params.height = 0;
                    whLayoutPaging.setLayoutParams(params);
                    whBtnShowList.setText("Show Half");
                } else {
                    whTextPaging.setText("1");
                    counter = 0;
                    loadData("work_handover_id DESC");
                    loadAll = false;
                    params = whLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    whLayoutPaging.setLayoutParams(params);
                    whBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WHSpinnerSearch);
        whSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WHSpinnerSort);
        whSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WHADSpinnerSort);
        whSpinnerSortAD.setAdapter(spinnerAdapter);

        whSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, whSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, whSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        whBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whEditSearch.getText().toString().matches("")){
                    whSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(whEditSearch.getText()));
            }
        });

//        loadData("work_handover_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("work_handover_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("work_handover_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("purchase_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("purchase_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("supplier_name ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("supplier_name DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("notes ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("notes DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("recognized DESC");
        else loadDataAll("work_handover_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("work_handover_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("work_handover_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("purchase_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("purchase_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("supplier_name ASC");
        else if (position == 4 && posAD == 1)
            loadData("supplier_name DESC");
        else if (position == 5 && posAD == 0)
            loadData("notes ASC");
        else if (position == 5 && posAD == 1)
            loadData("notes DESC");
        else if (position == 6 && posAD == 0)
            loadData("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadData("recognized DESC");
        else loadData("work_handover_id DESC");
    }

    private void setAdapterList(){
        adapter = new WorkHandoverFragment.MyRecyclerViewAdapter(workHandovers, mListener);
        whRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        whRecycler.setAdapter(null);
        workHandovers.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_HANDOVER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workHandovers.add(new WorkHandover(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (whEditSearch.getText().toString().matches("")){
                                whSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(whEditSearch.getText()));
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
        whRecycler.setAdapter(null);
        workHandovers.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_HANDOVER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workHandovers.add(new WorkHandover(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (whEditSearch.getText().toString().matches("")){
                                whSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(whEditSearch.getText()));
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
        void onListFragmentInteraction(WorkHandover item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<WorkHandover> mValues;
        private final List<WorkHandover> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<WorkHandover> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_handover_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.whTextNomor.setText(""+mValues.get(position).getWork_handover_number());
            holder.whTextTglPenerimaan.setText(""+mValues.get(position).getReceipt_date());
            holder.whTextWorkOrder.setText(""+mValues.get(position).getPurchase_service_id());
            holder.whTextSupplier.setText(""+mValues.get(position).getSupplier_name());
            holder.whTextNote.setText(""+mValues.get(position).getNotes());
            holder.whTextstatus.setText(""+mValues.get(position).getRecognized());

            if (position%2==0)
                holder.whLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.whLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getWork_handover_id(), mValues.get(position));
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
                List<WorkHandover> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((WorkHandover) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (WorkHandover item : values){
                        if (whSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getWork_handover_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPurchase_service_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRecognized().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getWork_handover_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getPurchase_service_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (whSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getRecognized().toLowerCase().contains(filterPattern)){
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

            public final TextView whTextNomor;
            public final TextView whTextTglPenerimaan;
            public final TextView whTextWorkOrder;
            public final TextView whTextSupplier;
            public final TextView whTextNote;
            public final TextView whTextstatus;

            public final LinearLayout whLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                whTextNomor = (TextView) view.findViewById(R.id.whTextNomor);
                whTextTglPenerimaan = (TextView) view.findViewById(R.id.whTextTglPenerimaan);
                whTextWorkOrder = (TextView) view.findViewById(R.id.whTextWorkOrder);
                whTextSupplier = (TextView) view.findViewById(R.id.whTextSupplier);
                whTextNote = (TextView) view.findViewById(R.id.whTextNote);
                whTextstatus = (TextView) view.findViewById(R.id.whTextStatus);

                whLayoutList = (LinearLayout) view.findViewById(R.id.whLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final WorkHandover workHandover) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailWorkHandoverActivity.class);
                        intent.putExtra("detail", workHandover);
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
                param.put("feature", "" + "work-handover");
                param.put("access", "" + "work-handover:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
