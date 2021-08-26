package com.asukacorp.aismobile.Finance.CustomerReceives;

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
import com.asukacorp.aismobile.Data.FinanceAccounting.CustomerReceives;
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

public class CustomerReceivesFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView daTextPaging;
    public EditText daEditSearch;
    public ImageView daBtnSearch;
    public RecyclerView daRecycler;
    public FloatingActionButton daFabAdd;
    public Spinner daSpinnerSearch;
    public Spinner daSpinnerSort;
    public Spinner daSpinnerSortAD;
    public Button daBtnShowList;
    public ImageButton daBtnBefore;
    public ImageButton daBtnNext;
    public LinearLayout daLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public CustomerReceivesFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Customer Receives Number", "Customer Receives Date",
            "Receipt Number", "Ekspedisi", "Company"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Customer Receives Number", "Berdasarkan Customer Receives Date",
            "Berdasarkan Receipt Number", "Berdasarkan Ekspedisi", "Berdasarkan Company"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CustomerReceives> customerReceives;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CustomerReceivesFragment() {
    }

    public static CustomerReceivesFragment newInstance() {
        CustomerReceivesFragment fragment = new CustomerReceivesFragment();
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

        customerReceives = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_receives, container, false);

        // Set the adapter
        daRecycler = (RecyclerView) view.findViewById(R.id.crRecycler);
        if (mColumnCount <= 1) {
            daRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            daRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        daFabAdd = (FloatingActionButton) view.findViewById(R.id.crFabAdd);
        daEditSearch = (EditText) view.findViewById(R.id.crEditSearch);
        daTextPaging = (TextView) view.findViewById(R.id.crTextPaging);
        daBtnSearch = (ImageView) view.findViewById(R.id.crBtnSearch);
        daSpinnerSearch = (Spinner) view.findViewById(R.id.crSpinnerSearch);
        daSpinnerSort = (Spinner) view.findViewById(R.id.crSpinnerSort);
        daSpinnerSortAD = (Spinner) view.findViewById(R.id.crSpinnerSortAD);
        daBtnShowList = (Button) view.findViewById(R.id.crBtnShowList);
        daBtnBefore = (ImageButton) view.findViewById(R.id.crBtnBefore);
        daBtnNext = (ImageButton) view.findViewById(R.id.crBtnNext);
        daLayoutPaging = (LinearLayout) view.findViewById(R.id.crLayoutPaging);

        daBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(daTextPaging.getText()));
                setSortHalf(daSpinnerSort.getSelectedItemPosition(), daSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(daTextPaging.getText()))+1;
                daTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        daBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(daTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(daTextPaging.getText()))-2);
                    setSortHalf(daSpinnerSort.getSelectedItemPosition(), daSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(daTextPaging.getText()))-1;
                    daTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        daBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("cr.customer_receive_id DESC");
                    loadAll = true;
                    params = daLayoutPaging.getLayoutParams();
                    params.height = 0;
                    daLayoutPaging.setLayoutParams(params);
                    daBtnShowList.setText("Show Half");
                } else {
                    daTextPaging.setText("1");
                    counter = 0;
                    loadData("cr.customer_receive_id DESC");
                    loadAll = false;
                    params = daLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    daLayoutPaging.setLayoutParams(params);
                    daBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        daSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        daSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        daSpinnerSortAD.setAdapter(spinnerAdapter);

        daSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, daSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, daSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daEditSearch.getText().toString().matches("")){
                    daSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
            }
        });

//        loadData("account_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("customer_receive_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("customer_receive_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("cr.customer_receive_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("cr.customer_receive_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("receipt_number ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("receipt_number DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("ekspedisi_id ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("ekspedisi_id DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("company_name ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("company_name DESC");
        else loadDataAll("cr.customer_receive_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("customer_receive_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("customer_receive_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("cr.customer_receive_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("cr.customer_receive_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("receipt_number ASC");
        else if (position == 3 && posAD == 1)
            loadData("receipt_number DESC");
        else if (position == 4 && posAD == 0)
            loadData("ekspedisi_id ASC");
        else if (position == 4 && posAD == 1)
            loadData("ekspedisi_id DESC");
        else if (position == 5 && posAD == 0)
            loadData("company_name ASC");
        else if (position == 5 && posAD == 1)
            loadData("company_name DESC");
        else loadData("cr.customer_receive_id DESC");
    }

    private void setAdapterList(){
        adapter = new CustomerReceivesFragment.MyRecyclerViewAdapter(customerReceives, mListener);
        daRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        daRecycler.setAdapter(null);
        customerReceives.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_RECEIVES_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerReceives.add(new CustomerReceives(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (daEditSearch.getText().toString().matches("")){
                                daSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
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
        daRecycler.setAdapter(null);
        customerReceives.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_RECEIVES_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerReceives.add(new CustomerReceives(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (daEditSearch.getText().toString().matches("")){
                                daSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
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
        void onListFragmentInteraction(CustomerReceives item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CustomerReceives> mValues;
        private final List<CustomerReceives> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CustomerReceives> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_customer_receives_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textCrNumber.setText(""+mValues.get(position).getCustomer_receive_number());
            holder.textCrDate.setText(""+mValues.get(position).getCustomer_receive_date());
            holder.textReceiptNumber.setText(""+mValues.get(position).getReceipt_number());
            holder.textExpedisi.setText(""+mValues.get(position).getEkspedisi_id());
            holder.textCompany.setText(""+mValues.get(position).getCompany_name());

            if (position%2==0)
                holder.daLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.daLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getCustomer_receive_id(), mValues.get(position));
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
                List<CustomerReceives> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CustomerReceives) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CustomerReceives item : values){
                        if (daSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getCustomer_receive_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCustomer_receive_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReceipt_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEkspedisi_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getCustomer_receive_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCustomer_receive_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getReceipt_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getEkspedisi_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
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

            public final TextView textCrNumber;
            public final TextView textCrDate;
            public final TextView textReceiptNumber;
            public final TextView textExpedisi;
            public final TextView textCompany;

            public final LinearLayout daLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                textCrNumber = (TextView) view.findViewById(R.id.textCrNumber);
                textCrDate = (TextView) view.findViewById(R.id.textCrDate);
                textReceiptNumber = (TextView) view.findViewById(R.id.textReceiptNumber);
                textExpedisi = (TextView) view.findViewById(R.id.textExpedisi);
                textCompany = (TextView) view.findViewById(R.id.textCompany);

                daLayoutList = (LinearLayout) view.findViewById(R.id.layoutList);
            }
        }
    }

    private void loadAccess(final String id, final CustomerReceives customerReceives) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), CustomerReceiveDetailActivity.class);
                        intent.putExtra("detail", customerReceives);
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
                param.put("feature", "" + "customer-receive");
                param.put("access", "" + "customer-receive:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}