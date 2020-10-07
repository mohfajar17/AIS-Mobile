package com.example.aismobile.Finance.Budgeting;

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
import com.example.aismobile.Data.FinanceAccounting.Budgeting;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetingFragment extends Fragment {

    public TextView bTextPaging;
    public EditText bEditSearch;
    public ImageView bBtnSearch;
    public RecyclerView bRecycler;
    public FloatingActionButton bFabAdd;
    public Spinner bSpinnerSearch;
    public Spinner bSpinnerSort;
    public Spinner bSpinnerSortAD;
    public Button bBtnShowList;
    public ImageButton bBtnBefore;
    public ImageButton bBtnNext;
    public LinearLayout bLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public BudgetingFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] BSpinnerSearch = {"Semua Data", "Budgeting Number", "Dibuat Oleh", "Start Date", "End Date",
            "Checked By", "Approval 1", "Approval 2", "Approval 3", "Done"};
    public String[] BSpinnerSort = {"-- Sort By --", "Berdasarkan Budgeting Number", "Berdasarkan Dibuat Oleh",
            "Berdasarkan Start Date", "Berdasarkan End Date", "Berdasarkan Checked By", "Berdasarkan Approval 1",
            "Berdasarkan Approval 2", "Berdasarkan Approval 3", "Berdasarkan Done"};
    public String[] BADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Budgeting> budgetings;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public BudgetingFragment() {
    }
    public static BudgetingFragment newInstance() {
        BudgetingFragment fragment = new BudgetingFragment();
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

        budgetings = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budgeting, container, false);

        // Set the adapter
        bRecycler = (RecyclerView) view.findViewById(R.id.bRecycler);
        if (mColumnCount <= 1) {
            bRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            bRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        bFabAdd = (FloatingActionButton) view.findViewById(R.id.bFabAdd);
        bEditSearch = (EditText) view.findViewById(R.id.bEditSearch);
        bTextPaging = (TextView) view.findViewById(R.id.bTextPaging);
        bBtnSearch = (ImageView) view.findViewById(R.id.bBtnSearch);
        bSpinnerSearch = (Spinner) view.findViewById(R.id.bSpinnerSearch);
        bSpinnerSort = (Spinner) view.findViewById(R.id.bSpinnerSort);
        bSpinnerSortAD = (Spinner) view.findViewById(R.id.bSpinnerSortAD);
        bBtnShowList = (Button) view.findViewById(R.id.bBtnShowList);
        bBtnBefore = (ImageButton) view.findViewById(R.id.bBtnBefore);
        bBtnNext = (ImageButton) view.findViewById(R.id.bBtnNext);
        bLayoutPaging = (LinearLayout) view.findViewById(R.id.bLayoutPaging);

        bBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(bTextPaging.getText()));
                setSortHalf(bSpinnerSort.getSelectedItemPosition(), bSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(bTextPaging.getText()))+1;
                bTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        bBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(bTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(bTextPaging.getText()))-2);
                    setSortHalf(bSpinnerSort.getSelectedItemPosition(), bSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(bTextPaging.getText()))-1;
                    bTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        bBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("budget_id DESC");
                    loadAll = true;
                    params = bLayoutPaging.getLayoutParams();
                    params.height = 0;
                    bLayoutPaging.setLayoutParams(params);
                    bBtnShowList.setText("Show Half");
                } else {
                    bTextPaging.setText("1");
                    counter = 0;
                    loadData("budget_id DESC");
                    loadAll = false;
                    params = bLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    bLayoutPaging.setLayoutParams(params);
                    bBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, BSpinnerSearch);
        bSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, BSpinnerSort);
        bSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, BADSpinnerSort);
        bSpinnerSortAD.setAdapter(spinnerAdapter);

        bSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, bSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, bSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bEditSearch.getText().toString().matches("")){
                    bSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(bEditSearch.getText()));
            }
        });

//        loadData("budget_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("budget_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("budget_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("start_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("start_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("end_date DESC");
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
        else if (position == 9 && posAD == 0)
            loadDataAll("done ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("done DESC");
        else loadDataAll("budget_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("budget_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("budget_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 2 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 3 && posAD == 0)
            loadData("start_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("start_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 4 && posAD == 1)
            loadData("end_date DESC");
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
        else if (position == 9 && posAD == 0)
            loadData("done ASC");
        else if (position == 9 && posAD == 1)
            loadData("done DESC");
        else loadData("budget_id DESC");
    }

    private void setAdapterList(){
        adapter = new BudgetingFragment.MyRecyclerViewAdapter(budgetings, mListener);
        bRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        bRecycler.setAdapter(null);
        budgetings.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BUDGETING_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetings.add(new Budgeting(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (bEditSearch.getText().toString().matches("")){
                                bSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(bEditSearch.getText()));
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
        bRecycler.setAdapter(null);
        budgetings.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BUDGETING_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetings.add(new Budgeting(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (bEditSearch.getText().toString().matches("")){
                                bSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(bEditSearch.getText()));
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
        void onListFragmentInteraction(Budgeting item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Budgeting> mValues;
        private final List<Budgeting> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Budgeting> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_budgeting_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.bTextNumber.setText(""+mValues.get(position).getBudget_number());
            holder.bTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.bTextStartDate.setText(""+mValues.get(position).getStart_date());
            holder.bTextEndDate.setText(""+mValues.get(position).getEnd_date());
            holder.bTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.bTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.bTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.bTextApproval3.setText(""+mValues.get(position).getApproval3());
            holder.bTextDone.setText(""+mValues.get(position).getDone());

            if (position%2==0)
                holder.bLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.bLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailBudgetingActivity.class);
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
                List<Budgeting> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Budgeting) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Budgeting item : values){
                        if (bSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getBudget_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStart_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
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
                        } else if (bSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getBudget_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getStart_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (bSpinnerSearch.getSelectedItemPosition()==9){
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

            public final TextView bTextNumber;
            public final TextView bTextDibuat;
            public final TextView bTextStartDate;
            public final TextView bTextEndDate;
            public final TextView bTextCheckedBy;
            public final TextView bTextApproval1;
            public final TextView bTextApproval2;
            public final TextView bTextApproval3;
            public final TextView bTextDone;

            public final LinearLayout bLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                bTextNumber = (TextView) view.findViewById(R.id.bTextNumber);
                bTextDibuat = (TextView) view.findViewById(R.id.bTextDibuat);
                bTextStartDate = (TextView) view.findViewById(R.id.bTextStartDate);
                bTextEndDate = (TextView) view.findViewById(R.id.bTextEndDate);
                bTextCheckedBy = (TextView) view.findViewById(R.id.bTextCheckedBy);
                bTextApproval1 = (TextView) view.findViewById(R.id.bTextApproval1);
                bTextApproval2 = (TextView) view.findViewById(R.id.bTextApproval2);
                bTextApproval3 = (TextView) view.findViewById(R.id.bTextApproval3);
                bTextDone = (TextView) view.findViewById(R.id.bTextDone);

                bLayoutList = (LinearLayout) view.findViewById(R.id.bLayoutList);
            }
        }
    }
}
