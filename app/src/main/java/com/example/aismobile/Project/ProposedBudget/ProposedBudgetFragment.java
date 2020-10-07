package com.example.aismobile.Project.ProposedBudget;

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
import com.example.aismobile.Data.Project.ProposedBudget;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProposedBudgetFragment extends Fragment {

    public TextView ppbTextPaging;
    public EditText ppbEditSearch;
    public ImageView ppbBtnSearch;
    public RecyclerView ppbRecycler;
    public FloatingActionButton ppbFabAdd;
    public Spinner ppbSpinnerSearch;
    public Spinner ppbSpinnerSort;
    public Spinner ppbSpinnerSortAD;
    public Button ppbBtnShowList;
    public ImageButton ppbBtnBefore;
    public ImageButton ppbBtnNext;
    public LinearLayout ppbLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public ProposedBudgetFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] PBSpinnerSearch = {"Semua Data", "Proposed Budget", "Rest From", "Job Order", "Penanggung Jawab",
            "Tanggal Permintaan", "Due Date", "Payment Date", "Dibuat Oleh", "Approval 1", "Approval 2", "Approval 3", "Done"};
    public String[] PBSpinnerSort = {"-- Sort By --", "Berdasarkan Proposed Budget", "Berdasarkan Rest From",
            "Berdasarkan Job Order", "Berdasarkan Penanggung Jawab", "Berdasarkan Tanggal Permintaan",
            "Berdasarkan Due Date", "Berdasarkan Dibuat Oleh", "Berdasarkan Approval 1", "Berdasarkan Approval 2",
            "Berdasarkan Approval 3", "Berdasarkan Done"};
    public String[] PBADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<ProposedBudget> proposedBudgets;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ProposedBudgetFragment() {
        // Required empty public constructor
    }

    public static ProposedBudgetFragment newInstance() {
        ProposedBudgetFragment fragment = new ProposedBudgetFragment();
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

        proposedBudgets = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposed_budget, container, false);

        // Set the adapter
        ppbRecycler = (RecyclerView) view.findViewById(R.id.ppbRecycler);
        if (mColumnCount <= 1) {
            ppbRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            ppbRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        ppbFabAdd = (FloatingActionButton) view.findViewById(R.id.ppbFabAdd);
        ppbEditSearch = (EditText) view.findViewById(R.id.ppbEditSearch);
        ppbTextPaging = (TextView) view.findViewById(R.id.ppbTextPaging);
        ppbBtnSearch = (ImageView) view.findViewById(R.id.ppbBtnSearch);
        ppbSpinnerSearch = (Spinner) view.findViewById(R.id.ppbSpinnerSearch);
        ppbSpinnerSort = (Spinner) view.findViewById(R.id.ppbSpinnerSort);
        ppbSpinnerSortAD = (Spinner) view.findViewById(R.id.ppbSpinnerSortAD);
        ppbBtnShowList = (Button) view.findViewById(R.id.ppbBtnShowList);
        ppbBtnBefore = (ImageButton) view.findViewById(R.id.ppbBtnBefore);
        ppbBtnNext = (ImageButton) view.findViewById(R.id.ppbBtnNext);
        ppbLayoutPaging = (LinearLayout) view.findViewById(R.id.ppbLayoutPaging);

        ppbBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(ppbTextPaging.getText()));
                setSortHalf(ppbSpinnerSort.getSelectedItemPosition(), ppbSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(ppbTextPaging.getText()))+1;
                ppbTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        ppbBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(ppbTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(ppbTextPaging.getText()))-2);
                    setSortHalf(ppbSpinnerSort.getSelectedItemPosition(), ppbSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(ppbTextPaging.getText()))-1;
                    ppbTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        ppbBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("cash_advance_id DESC");
                    loadAll = true;
                    params = ppbLayoutPaging.getLayoutParams();
                    params.height = 0;
                    ppbLayoutPaging.setLayoutParams(params);
                    ppbBtnShowList.setText("Show Half");
                } else {
                    ppbTextPaging.setText("1");
                    counter = 0;
                    loadData("cash_advance_id DESC");
                    loadAll = false;
                    params = ppbLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    ppbLayoutPaging.setLayoutParams(params);
                    ppbBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PBSpinnerSearch);
        ppbSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PBSpinnerSort);
        ppbSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PBADSpinnerSort);
        ppbSpinnerSortAD.setAdapter(spinnerAdapter);

        ppbSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, ppbSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, ppbSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ppbBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppbEditSearch.getText().toString().matches("")){
                    ppbSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(ppbEditSearch.getText()));
            }
        });

//        loadData("cash_advance_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("cash_advance_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("cash_advance_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("rest_from ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("rest_from DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("person_in_charge ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("person_in_charge DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("requisition_date ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("requisition_date DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("due_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("due_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("payment_date ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("payment_date DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("approval1 DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("approval2 ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("approval2 DESC");
        else if (position == 11 && posAD == 0)
            loadDataAll("approval3 ASC");
        else if (position == 11 && posAD == 1)
            loadDataAll("approval3 DESC");
        else if (position == 12 && posAD == 0)
            loadDataAll("done ASC");
        else if (position == 12 && posAD == 1)
            loadDataAll("done DESC");
        else loadDataAll("cash_advance_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("cash_advance_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("cash_advance_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("rest_from ASC");
        else if (position == 2 && posAD == 1)
            loadData("rest_from DESC");
        else if (position == 3 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("person_in_charge ASC");
        else if (position == 4 && posAD == 1)
            loadData("person_in_charge DESC");
        else if (position == 5 && posAD == 0)
            loadData("requisition_date ASC");
        else if (position == 5 && posAD == 1)
            loadData("requisition_date DESC");
        else if (position == 6 && posAD == 0)
            loadData("due_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("due_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("payment_date ASC");
        else if (position == 7 && posAD == 1)
            loadData("payment_date DESC");
        else if (position == 8 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 8 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 9 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 9 && posAD == 1)
            loadData("approval1 DESC");
        else if (position == 10 && posAD == 0)
            loadData("approval2 ASC");
        else if (position == 10 && posAD == 1)
            loadData("approval2 DESC");
        else if (position == 11 && posAD == 0)
            loadData("approval3 ASC");
        else if (position == 11 && posAD == 1)
            loadData("approval3 DESC");
        else if (position == 12 && posAD == 0)
            loadData("done ASC");
        else if (position == 12 && posAD == 1)
            loadData("done DESC");
        else loadData("cash_advance_id DESC");
    }

    private void setAdapterList(){
        adapter = new ProposedBudgetFragment.MyRecyclerViewAdapter(proposedBudgets, mListener);
        ppbRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        ppbRecycler.setAdapter(null);
        proposedBudgets.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PROPOSE_BUDGET_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgets.add(new ProposedBudget(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                    } else {
                        Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();

                    if (filter){
                        if (ppbEditSearch.getText().toString().matches("")){
                            ppbSpinnerSearch.setSelection(0);
                            adapter.getFilter().filter("-");
                        } else adapter.getFilter().filter(String.valueOf(ppbEditSearch.getText()));
                        filter = false;
                    }
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
        ppbRecycler.setAdapter(null);
        proposedBudgets.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PROPOSE_BUDGET_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgets.add(new ProposedBudget(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                    } else {
                        Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();

                    if (filter){
                        if (ppbEditSearch.getText().toString().matches("")){
                            ppbSpinnerSearch.setSelection(0);
                            adapter.getFilter().filter("-");
                        } else adapter.getFilter().filter(String.valueOf(ppbEditSearch.getText()));
                        filter = false;
                    }
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
        void onListFragmentInteraction(ProposedBudget item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<ProposedBudget> mValues;
        private final List<ProposedBudget> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<ProposedBudget> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_proposed_budget_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ppbTextNomorProposedBudget.setText(""+mValues.get(position).getCash_advance_number());
            holder.ppbTextRest.setText(""+mValues.get(position).getRest_from());
            holder.ppbTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.ppbTextPenanggungJwb.setText(""+mValues.get(position).getPerson_in_charge());
            holder.ppbTextTglPermintaan.setText(""+mValues.get(position).getRequisition_date());
            holder.ppbTextDueDate.setText(""+mValues.get(position).getDue_date());
            holder.ppbTextPaymentDate.setText(""+mValues.get(position).getPayment_date());
            holder.ppbTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.ppbTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.ppbTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.ppbTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getDone())==2)
                holder.ppbTextDone.setText("Tidak");
            else holder.ppbTextDone.setText("Ya");

            if (position%2==0)
                holder.ppbLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ppbLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailProposedBudgetActivity.class);
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
                List<ProposedBudget> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((ProposedBudget) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ProposedBudget item : values){
                        if (ppbSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getCash_advance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRest_from().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPerson_in_charge().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRequisition_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPayment_date().toLowerCase().contains(filterPattern)){
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
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getCash_advance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getRest_from().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPerson_in_charge().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getRequisition_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getPayment_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==11){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppbSpinnerSearch.getSelectedItemPosition()==12){
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

            public final TextView ppbTextNomorProposedBudget;
            public final TextView ppbTextRest;
            public final TextView ppbTextJobOrder;
            public final TextView ppbTextPenanggungJwb;
            public final TextView ppbTextTglPermintaan;
            public final TextView ppbTextDueDate;
            public final TextView ppbTextPaymentDate;
            public final TextView ppbTextDibuat;
            public final TextView ppbTextApproval1;
            public final TextView ppbTextApproval2;
            public final TextView ppbTextApproval3;
            public final TextView ppbTextDone;

            public final LinearLayout ppbLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ppbTextNomorProposedBudget = (TextView) view.findViewById(R.id.ppbTextNomorProposedBudget);
                ppbTextRest = (TextView) view.findViewById(R.id.ppbTextRest);
                ppbTextJobOrder = (TextView) view.findViewById(R.id.ppbTextJobOrder);
                ppbTextPenanggungJwb = (TextView) view.findViewById(R.id.ppbTextPenanggungJwb);
                ppbTextTglPermintaan = (TextView) view.findViewById(R.id.ppbTextTglPermintaan);
                ppbTextDueDate = (TextView) view.findViewById(R.id.ppbTextDueDate);
                ppbTextPaymentDate = (TextView) view.findViewById(R.id.ppbTextPaymentDate);
                ppbTextDibuat = (TextView) view.findViewById(R.id.ppbTextDibuat);
                ppbTextApproval1 = (TextView) view.findViewById(R.id.ppbTextApproval1);
                ppbTextApproval2 = (TextView) view.findViewById(R.id.ppbTextApproval2);
                ppbTextApproval3 = (TextView) view.findViewById(R.id.ppbTextApproval3);
                ppbTextDone = (TextView) view.findViewById(R.id.ppbTextDone);

                ppbLayoutList = (LinearLayout) view.findViewById(R.id.ppbLayoutList);
            }
        }
    }
}
