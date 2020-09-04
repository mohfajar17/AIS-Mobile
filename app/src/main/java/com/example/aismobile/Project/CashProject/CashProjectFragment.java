package com.example.aismobile.Project.CashProject;

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
import com.example.aismobile.Data.Project.CashProjectReport;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashProjectFragment extends Fragment {

    public TextView pcpTextPaging;
    public EditText pcpEditSearch;
    public ImageView pcpBtnSearch;
    public RecyclerView pcpRecycler;
    public FloatingActionButton pcpFabAdd;
    public Spinner pcpSpinnerSearch;
    public Spinner pcpSpinnerSort;
    public Spinner pcpSpinnerSortAD;
    public Button pcpBtnShowList;
    public ImageButton pcpBtnBefore;
    public ImageButton pcpBtnNext;
    public LinearLayout pcpLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public CashProjectFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CPSpinnerSearch = {"Semua Data", "Cash Project Report", "Proposed Budget","Keterangan Job Order",
            "Dibuat Oleh", "Approval 1", "Approval 2", "Approval 3", "Done"};
    public String[] CPSpinnerSort = {"-- Sort By --", "Berdasarkan Cash Project Report", "Berdasarkan Proposed Budget",
            "Berdasarkan Keterangan Job Order", "Berdasarkan Dibuat Oleh", "Berdasarkan Approval 1", "Berdasarkan Approval 2",
            "Berdasarkan Approval 3", "Berdasarkan Done"};
    public String[] CPADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CashProjectReport> cashProjectReports;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter;

    public CashProjectFragment() {
    }

    public static CashProjectFragment newInstance() {
        CashProjectFragment fragment = new CashProjectFragment();
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

        cashProjectReports = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_project, container, false);

        // Set the adapter
        pcpRecycler = (RecyclerView) view.findViewById(R.id.pcpRecycler);
        if (mColumnCount <= 1) {
            pcpRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pcpRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pcpFabAdd = (FloatingActionButton) view.findViewById(R.id.pcpFabAdd);
        pcpEditSearch = (EditText) view.findViewById(R.id.pcpEditSearch);
        pcpTextPaging = (TextView) view.findViewById(R.id.pcpTextPaging);
        pcpBtnSearch = (ImageView) view.findViewById(R.id.pcpBtnSearch);
        pcpSpinnerSearch = (Spinner) view.findViewById(R.id.pcpSpinnerSearch);
        pcpSpinnerSort = (Spinner) view.findViewById(R.id.pcpSpinnerSort);
        pcpSpinnerSortAD = (Spinner) view.findViewById(R.id.pcpSpinnerSortAD);
        pcpBtnShowList = (Button) view.findViewById(R.id.pcpBtnShowList);
        pcpBtnBefore = (ImageButton) view.findViewById(R.id.pcpBtnBefore);
        pcpBtnNext = (ImageButton) view.findViewById(R.id.pcpBtnNext);
        pcpLayoutPaging = (LinearLayout) view.findViewById(R.id.pcpLayoutPaging);

        pcpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pcpTextPaging.getText()));
                setSortHalf(pcpSpinnerSort.getSelectedItemPosition(), pcpSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pcpTextPaging.getText()))+1;
                pcpTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pcpBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pcpTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pcpTextPaging.getText()))-2);
                    setSortHalf(pcpSpinnerSort.getSelectedItemPosition(), pcpSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pcpTextPaging.getText()))-1;
                    pcpTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pcpBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("responsbility_advance_id DESC");
                    loadAll = true;
                    params = pcpLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pcpLayoutPaging.setLayoutParams(params);
                    pcpBtnShowList.setText("Show Half");
                } else {
                    pcpTextPaging.setText("1");
                    counter = 0;
                    loadData("responsbility_advance_id DESC");
                    loadAll = false;
                    params = pcpLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pcpLayoutPaging.setLayoutParams(params);
                    pcpBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CPSpinnerSearch);
        pcpSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CPSpinnerSort);
        pcpSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CPADSpinnerSort);
        pcpSpinnerSortAD.setAdapter(spinnerAdapter);

        pcpSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pcpSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pcpSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pcpBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pcpEditSearch.getText().toString().matches("")){
                    pcpSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pcpEditSearch.getText()));
            }
        });

        loadData("responsbility_advance_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("responsbility_advance_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("responsbility_advance_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("cash_advance_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("cash_advance_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("responsbility_advance_number ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("responsbility_advance_number DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("approval1 DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("approval2 ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("approval2 DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("approval3 ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("approval3 DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("done ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("done DESC");
        else loadDataAll("responsbility_advance_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("responsbility_advance_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("responsbility_advance_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("cash_advance_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("cash_advance_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("responsbility_advance_number ASC");
        else if (position == 3 && posAD == 1)
            loadData("responsbility_advance_number DESC");
        else if (position == 4 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 5 && posAD == 1)
            loadData("approval1 DESC");
        else if (position == 6 && posAD == 0)
            loadData("approval2 ASC");
        else if (position == 6 && posAD == 1)
            loadData("approval2 DESC");
        else if (position == 7 && posAD == 0)
            loadData("approval3 ASC");
        else if (position == 7 && posAD == 1)
            loadData("approval3 DESC");
        else if (position == 8 && posAD == 0)
            loadData("done ASC");
        else if (position == 8 && posAD == 1)
            loadData("done DESC");
        else loadData("responsbility_advance_id DESC");
    }

    private void setAdapterList(){
        adapter = new CashProjectFragment.MyRecyclerViewAdapter(cashProjectReports, mListener);
        pcpRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        pcpRecycler.setAdapter(null);
        cashProjectReports.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_PROJECT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashProjectReports.add(new CashProjectReport(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pcpEditSearch.getText().toString().matches("")){
                                pcpSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pcpEditSearch.getText()));
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
        pcpRecycler.setAdapter(null);
        cashProjectReports.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_PROJECT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashProjectReports.add(new CashProjectReport(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pcpEditSearch.getText().toString().matches("")){
                                pcpSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pcpEditSearch.getText()));
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
        void onListFragmentInteraction(CashProjectReport item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CashProjectReport> mValues;
        private final List<CashProjectReport> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CashProjectReport> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_project_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pcpTextNomorCashProject.setText(""+mValues.get(position).getResponsbility_advance_number());
            holder.pcpTextProposedBudget.setText(""+mValues.get(position).getCash_advance_id());
            holder.pcpTextKetJobOrder.setText(""+mValues.get(position).getJob_order_id()); //untuk ket job order
            holder.pcpTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pcpTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pcpTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pcpTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getDone())==2)
                holder.pcpTextDone.setText("Tidak");
            else holder.pcpTextDone.setText("Ya");

            if (position%2==0)
                holder.pcpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pcpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailCashProjectActivity.class);
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
                List<CashProjectReport> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CashProjectReport) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CashProjectReport item : values){
                        if (pcpSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getResponsbility_advance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCash_advance_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){   //untuk ket job order
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getResponsbility_advance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCash_advance_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){      //untuk ket job order
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==11){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pcpSpinnerSearch.getSelectedItemPosition()==12){
                            if (item.getDone().toLowerCase().contains(filterPattern)){
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

            public final TextView pcpTextNomorCashProject;
            public final TextView pcpTextProposedBudget;
            public final TextView pcpTextKetJobOrder;
            public final TextView pcpTextDibuat;
            public final TextView pcpTextApproval1;
            public final TextView pcpTextApproval2;
            public final TextView pcpTextApproval3;
            public final TextView pcpTextDone;

            public final LinearLayout pcpLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pcpTextNomorCashProject = (TextView) view.findViewById(R.id.pcpTextNomorCashProject);
                pcpTextProposedBudget = (TextView) view.findViewById(R.id.pcpTextProposedBudget);
                pcpTextKetJobOrder = (TextView) view.findViewById(R.id.pcpTextKetJobOrder);
                pcpTextDibuat = (TextView) view.findViewById(R.id.pcpTextDibuat);
                pcpTextApproval1 = (TextView) view.findViewById(R.id.pcpTextApproval1);
                pcpTextApproval2 = (TextView) view.findViewById(R.id.pcpTextApproval2);
                pcpTextApproval3 = (TextView) view.findViewById(R.id.pcpTextApproval3);
                pcpTextDone = (TextView) view.findViewById(R.id.pcpTextDone);

                pcpLayoutList = (LinearLayout) view.findViewById(R.id.pcpLayoutList);
            }
        }
    }
}
