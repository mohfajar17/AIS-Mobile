package com.example.aismobile.Finance.Expenses;

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
import com.example.aismobile.Data.FinanceAccounting.Expense;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
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

public class ExpensesFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView eTextPaging;
    public EditText eEditSearch;
    public ImageView eBtnSearch;
    public RecyclerView eRecycler;
    public FloatingActionButton eFabAdd;
    public Spinner eSpinnerSearch;
    public Spinner eSpinnerSort;
    public Spinner eSpinnerSortAD;
    public Button eBtnShowList;
    public ImageButton eBtnBefore;
    public ImageButton eBtnNext;
    public LinearLayout eLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public ExpensesFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] ESpinnerSearch = {"Semua Data", "Nomor Customer Invoice", "Sales Order Invoice Description",
            "Job Order", "Sales Quotation",
            "Work Completion", "Due Date", "Client PO Number", "Status", "Payment Late", "Grand Total"};
    public String[] ESpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Customer Invoice", "Berdasarkan Sales Order Invoice Description",
            "Berdasarkan Job Order", "Berdasarkan Sales Quotation", "Berdasarkan Work Completion", "Berdasarkan Due Date",
            "Berdasarkan Client PO Number", "Berdasarkan Status", "Berdasarkan Payment Late", "Berdasarkan Grand Total"};
    public String[] EADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Expense> expenses;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ExpensesFragment() {
    }

    public static ExpensesFragment newInstance() {
        ExpensesFragment fragment = new ExpensesFragment();
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

        expenses = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        // Set the adapter
        eRecycler = (RecyclerView) view.findViewById(R.id.eRecycler);
        if (mColumnCount <= 1) {
            eRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            eRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        eFabAdd = (FloatingActionButton) view.findViewById(R.id.eFabAdd);
        eEditSearch = (EditText) view.findViewById(R.id.eEditSearch);
        eTextPaging = (TextView) view.findViewById(R.id.eTextPaging);
        eBtnSearch = (ImageView) view.findViewById(R.id.eBtnSearch);
        eSpinnerSearch = (Spinner) view.findViewById(R.id.eSpinnerSearch);
        eSpinnerSort = (Spinner) view.findViewById(R.id.eSpinnerSort);
        eSpinnerSortAD = (Spinner) view.findViewById(R.id.eSpinnerSortAD);
        eBtnShowList = (Button) view.findViewById(R.id.eBtnShowList);
        eBtnBefore = (ImageButton) view.findViewById(R.id.eBtnBefore);
        eBtnNext = (ImageButton) view.findViewById(R.id.eBtnNext);
        eLayoutPaging = (LinearLayout) view.findViewById(R.id.eLayoutPaging);

        eBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(eTextPaging.getText()));
                setSortHalf(eSpinnerSort.getSelectedItemPosition(), eSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(eTextPaging.getText()))+1;
                eTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        eBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(eTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(eTextPaging.getText()))-2);
                    setSortHalf(eSpinnerSort.getSelectedItemPosition(), eSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(eTextPaging.getText()))-1;
                    eTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        eBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("begin_date DESC");
                    loadAll = true;
                    params = eLayoutPaging.getLayoutParams();
                    params.height = 0;
                    eLayoutPaging.setLayoutParams(params);
                    eBtnShowList.setText("Show Half");
                } else {
                    eTextPaging.setText("1");
                    counter = 0;
                    loadData("begin_date DESC");
                    loadAll = false;
                    params = eLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    eLayoutPaging.setLayoutParams(params);
                    eBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ESpinnerSearch);
        eSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ESpinnerSort);
        eSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, EADSpinnerSort);
        eSpinnerSortAD.setAdapter(spinnerAdapter);

        eSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, eSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, eSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eEditSearch.getText().toString().matches("")){
                    eSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(eEditSearch.getText()));
            }
        });

//        loadData("begin_date DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("expenses_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("expenses_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("expenses_desc ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("expenses_desc DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("checked_by DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("approval1 DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("approval2 ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("approval2 DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("advanced_number ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("advanced_number DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("expenses_date ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("expenses_date DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("total_amount ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("total_amount DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("bank_account_name ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("bank_account_name DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("done ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("done DESC");
        else loadDataAll("begin_date DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("expenses_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("expenses_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("expenses_desc ASC");
        else if (position == 2 && posAD == 1)
            loadData("expenses_desc DESC");
        else if (position == 3 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 3 && posAD == 1)
            loadData("checked_by DESC");
        else if (position == 4 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 4 && posAD == 1)
            loadData("approval1 DESC");
        else if (position == 5 && posAD == 0)
            loadData("approval2 ASC");
        else if (position == 5 && posAD == 1)
            loadData("approval2 DESC");
        else if (position == 6 && posAD == 0)
            loadData("advanced_number ASC");
        else if (position == 6 && posAD == 1)
            loadData("advanced_number DESC");
        else if (position == 7 && posAD == 0)
            loadData("expenses_date ASC");
        else if (position == 7 && posAD == 1)
            loadData("expenses_date DESC");
        else if (position == 8 && posAD == 0)
            loadData("total_amount ASC");
        else if (position == 8 && posAD == 1)
            loadData("total_amount DESC");
        else if (position == 9 && posAD == 0)
            loadData("bank_account_name ASC");
        else if (position == 9 && posAD == 1)
            loadData("bank_account_name DESC");
        else if (position == 10 && posAD == 0)
            loadData("done ASC");
        else if (position == 10 && posAD == 1)
            loadData("done DESC");
        else loadData("begin_date DESC");
    }

    private void setAdapterList(){
        adapter = new ExpensesFragment.MyRecyclerViewAdapter(expenses, mListener);
        eRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        eRecycler.setAdapter(null);
        expenses.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EXPENSE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenses.add(new Expense(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (eEditSearch.getText().toString().matches("")){
                                eSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(eEditSearch.getText()));
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
        eRecycler.setAdapter(null);
        expenses.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EXPENSE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenses.add(new Expense(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (eEditSearch.getText().toString().matches("")){
                                eSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(eEditSearch.getText()));
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
        void onListFragmentInteraction(Expense item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Expense> mValues;
        private final List<Expense> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Expense> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_expenses_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.eTextNumber.setText(""+mValues.get(position).getExpenses_number());
            holder.eTextExpDesk.setText(""+mValues.get(position).getExpenses_desc());
            holder.eTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.eTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.eTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.eTextCashAdvance.setText(""+mValues.get(position).getAdvanced_number());
            holder.eTextDate.setText(""+mValues.get(position).getExpenses_date());
            holder.eTextRekBank.setText(""+mValues.get(position).getBank_account_name());
            holder.eTextDone.setText(""+mValues.get(position).getDone());

            double toDouble = Double.valueOf(mValues.get(position).getTotal_amount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.eTextTotal.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.eLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.eLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getExpenses_id(), mValues.get(position));
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
                List<Expense> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Expense) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Expense item : values){
                        if (eSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getExpenses_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getExpenses_desc().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAdvanced_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getExpenses_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTotal_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_account_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getExpenses_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getExpenses_desc().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getAdvanced_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getExpenses_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getTotal_amount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getBank_account_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (eSpinnerSearch.getSelectedItemPosition()==10){
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

            public final TextView eTextNumber;
            public final TextView eTextExpDesk;
            public final TextView eTextCheckedBy;
            public final TextView eTextApproval1;
            public final TextView eTextApproval2;
            public final TextView eTextCashAdvance;
            public final TextView eTextDate;
            public final TextView eTextTotal;
            public final TextView eTextRekBank;
            public final TextView eTextDone;

            public final LinearLayout eLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                eTextNumber = (TextView) view.findViewById(R.id.eTextNumber);
                eTextExpDesk = (TextView) view.findViewById(R.id.eTextExpDesk);
                eTextCheckedBy = (TextView) view.findViewById(R.id.eTextCheckedBy);
                eTextApproval1 = (TextView) view.findViewById(R.id.eTextApproval1);
                eTextApproval2 = (TextView) view.findViewById(R.id.eTextApproval2);
                eTextCashAdvance = (TextView) view.findViewById(R.id.eTextCashAdvance);
                eTextDate = (TextView) view.findViewById(R.id.eTextDate);
                eTextTotal = (TextView) view.findViewById(R.id.eTextTotal);
                eTextRekBank = (TextView) view.findViewById(R.id.eTextRekBank);
                eTextDone = (TextView) view.findViewById(R.id.eTextDone);

                eLayoutList = (LinearLayout) view.findViewById(R.id.eLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Expense expense) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailExpensesActivity.class);
                        intent.putExtra("detail", expense);
                        intent.putExtra("code", 0);
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
                param.put("feature", "" + "expenses");
                param.put("access", "" + "expenses:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
