package com.example.aismobile.Finance.PaymentSupplier;

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
import com.example.aismobile.Data.FinanceAccounting.PaymentSupplier;
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

public class PaymentSuppliersFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView psTextPaging;
    public EditText psEditSearch;
    public ImageView psBtnSearch;
    public RecyclerView psRecycler;
    public FloatingActionButton psFabAdd;
    public Spinner psSpinnerSearch;
    public Spinner psSpinnerSort;
    public Spinner psSpinnerSortAD;
    public Button psBtnShowList;
    public ImageButton psBtnBefore;
    public ImageButton psBtnNext;
    public LinearLayout psLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public PaymentSuppliersFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Budgeting Number", "Start Date", "End Date",
            "Checked By", "Approval 1", "Approval 2", "Approval 3", "Done"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Budgeting Number", "Berdasarkan Start Date",
            "Berdasarkan End Date", "Berdasarkan Checked By", "Berdasarkan Approval 1", "Berdasarkan Approval 2",
            "Berdasarkan Approval 3", "Berdasarkan Done"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<PaymentSupplier> budgetings;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public PaymentSuppliersFragment() {
    }

    public static PaymentSuppliersFragment newInstance() {
        PaymentSuppliersFragment fragment = new PaymentSuppliersFragment();
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

        budgetings = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_suppliers, container, false);

        // Set the adapter
        psRecycler = (RecyclerView) view.findViewById(R.id.psRecycler);
        if (mColumnCount <= 1) {
            psRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            psRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        psFabAdd = (FloatingActionButton) view.findViewById(R.id.psFabAdd);
        psEditSearch = (EditText) view.findViewById(R.id.psEditSearch);
        psTextPaging = (TextView) view.findViewById(R.id.psTextPaging);
        psBtnSearch = (ImageView) view.findViewById(R.id.psBtnSearch);
        psSpinnerSearch = (Spinner) view.findViewById(R.id.psSpinnerSearch);
        psSpinnerSort = (Spinner) view.findViewById(R.id.psSpinnerSort);
        psSpinnerSortAD = (Spinner) view.findViewById(R.id.psSpinnerSortAD);
        psBtnShowList = (Button) view.findViewById(R.id.psBtnShowList);
        psBtnBefore = (ImageButton) view.findViewById(R.id.psBtnBefore);
        psBtnNext = (ImageButton) view.findViewById(R.id.psBtnNext);
        psLayoutPaging = (LinearLayout) view.findViewById(R.id.psLayoutPaging);

        psBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(psTextPaging.getText()));
                setSortHalf(psSpinnerSort.getSelectedItemPosition(), psSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(psTextPaging.getText()))+1;
                psTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        psBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(psTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(psTextPaging.getText()))-2);
                    setSortHalf(psSpinnerSort.getSelectedItemPosition(), psSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(psTextPaging.getText()))-1;
                    psTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        psBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("budget_supplier_id DESC");
                    loadAll = true;
                    params = psLayoutPaging.getLayoutParams();
                    params.height = 0;
                    psLayoutPaging.setLayoutParams(params);
                    psBtnShowList.setText("Show Half");
                } else {
                    psTextPaging.setText("1");
                    counter = 0;
                    loadData("budget_supplier_id DESC");
                    loadAll = false;
                    params = psLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    psLayoutPaging.setLayoutParams(params);
                    psBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        psSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        psSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        psSpinnerSortAD.setAdapter(spinnerAdapter);

        psSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, psSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, psSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        psBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (psEditSearch.getText().toString().matches("")){
                    psSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(psEditSearch.getText()));
            }
        });

//        loadData("budget_supplier_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("budget_supplier_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("budget_supplier_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("start_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("start_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("checked_by DESC");
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
        else loadDataAll("budget_supplier_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("budget_supplier_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("budget_supplier_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("start_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("start_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("checked_by DESC");
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
        else loadData("budget_supplier_id DESC");
    }

    private void setAdapterList(){
        adapter = new PaymentSuppliersFragment.MyRecyclerViewAdapter(budgetings, mListener);
        psRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        psRecycler.setAdapter(null);
        budgetings.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PAYMENT_SUPPLIER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetings.add(new PaymentSupplier(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (psEditSearch.getText().toString().matches("")){
                                psSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(psEditSearch.getText()));
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
        psRecycler.setAdapter(null);
        budgetings.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PAYMENT_SUPPLIER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetings.add(new PaymentSupplier(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (psEditSearch.getText().toString().matches("")){
                                psSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(psEditSearch.getText()));
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
        void onListFragmentInteraction(PaymentSupplier item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<PaymentSupplier> mValues;
        private final List<PaymentSupplier> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<PaymentSupplier> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_payment_supplier_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.psTextNumber.setText(""+mValues.get(position).getBudget_supplier_number());
            holder.psTextStartDate.setText(""+mValues.get(position).getStart_date());
            holder.psTextEndDate.setText(""+mValues.get(position).getEnd_date());
            holder.psTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.psTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.psTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.psTextApproval3.setText(""+mValues.get(position).getApproval3());
            holder.psTextDone.setText(""+mValues.get(position).getDone());

            if (position%2==0)
                holder.psLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.psLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getBudget_supplier_id(), mValues.get(position));
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
                List<PaymentSupplier> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((PaymentSupplier) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (PaymentSupplier item : values){
                        if (psSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getBudget_supplier_number().toLowerCase().contains(filterPattern)){
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
                        } else if (psSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getBudget_supplier_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getStart_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (psSpinnerSearch.getSelectedItemPosition()==8){
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

            public final TextView psTextNumber;
            public final TextView psTextStartDate;
            public final TextView psTextEndDate;
            public final TextView psTextCheckedBy;
            public final TextView psTextApproval1;
            public final TextView psTextApproval2;
            public final TextView psTextApproval3;
            public final TextView psTextDone;

            public final LinearLayout psLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                psTextNumber = (TextView) view.findViewById(R.id.psTextNumber);
                psTextStartDate = (TextView) view.findViewById(R.id.psTextStartDate);
                psTextEndDate = (TextView) view.findViewById(R.id.psTextEndDate);
                psTextCheckedBy = (TextView) view.findViewById(R.id.psTextCheckedBy);
                psTextApproval1 = (TextView) view.findViewById(R.id.psTextApproval1);
                psTextApproval2 = (TextView) view.findViewById(R.id.psTextApproval2);
                psTextApproval3 = (TextView) view.findViewById(R.id.psTextApproval3);
                psTextDone = (TextView) view.findViewById(R.id.psTextDone);

                psLayoutList = (LinearLayout) view.findViewById(R.id.psLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final PaymentSupplier paymentSupplier) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailPaymentSuppliersActivity.class);
                        intent.putExtra("detail", paymentSupplier);
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
                param.put("feature", "" + "budgeting-supplier");
                param.put("access", "" + "budgeting-supplier:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
