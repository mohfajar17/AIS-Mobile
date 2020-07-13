package com.example.aismobile.Finance.BankAccount;

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
import com.example.aismobile.Data.FinanceAccounting.BankAccount;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountsFragment extends Fragment {

    public TextView baTextPaging;
    public EditText baEditSearch;
    public ImageView baBtnSearch;
    public RecyclerView baRecycler;
    public FloatingActionButton baFabAdd;
    public Spinner baSpinnerSearch;
    public Spinner baSpinnerSort;
    public Spinner baSpinnerSortAD;
    public Button baBtnShowList;
    public ImageButton baBtnBefore;
    public ImageButton baBtnNext;
    public LinearLayout baLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public BankAccountsFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Bank Account Name", "Bank Account Number", "Bank Name",
            "Currency Code", "Ending Reconcile", "Last Reconciled Date", "Active"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Bank Account Name", "Berdasarkan Bank Account Number",
            "Berdasarkan Bank Name", "Berdasarkan Currency Code", "Berdasarkan Ending Reconcile", "Berdasarkan Last Reconciled Date",
            "Berdasarkan Active"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<BankAccount> bankAccounts;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public BankAccountsFragment() {
    }

    public static BankAccountsFragment newInstance() {
        BankAccountsFragment fragment = new BankAccountsFragment();
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

        bankAccounts = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_accounts, container, false);

        // Set the adapter
        baRecycler = (RecyclerView) view.findViewById(R.id.baRecycler);
        if (mColumnCount <= 1) {
            baRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            baRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        baFabAdd = (FloatingActionButton) view.findViewById(R.id.baFabAdd);
        baEditSearch = (EditText) view.findViewById(R.id.baEditSearch);
        baTextPaging = (TextView) view.findViewById(R.id.baTextPaging);
        baBtnSearch = (ImageView) view.findViewById(R.id.baBtnSearch);
        baSpinnerSearch = (Spinner) view.findViewById(R.id.baSpinnerSearch);
        baSpinnerSort = (Spinner) view.findViewById(R.id.baSpinnerSort);
        baSpinnerSortAD = (Spinner) view.findViewById(R.id.baSpinnerSortAD);
        baBtnShowList = (Button) view.findViewById(R.id.baBtnShowList);
        baBtnBefore = (ImageButton) view.findViewById(R.id.baBtnBefore);
        baBtnNext = (ImageButton) view.findViewById(R.id.baBtnNext);
        baLayoutPaging = (LinearLayout) view.findViewById(R.id.baLayoutPaging);

        baBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(baTextPaging.getText()));
                setSortHalf(baSpinnerSort.getSelectedItemPosition(), baSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(baTextPaging.getText()))+1;
                baTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        baBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(baTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(baTextPaging.getText()))-2);
                    setSortHalf(baSpinnerSort.getSelectedItemPosition(), baSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(baTextPaging.getText()))-1;
                    baTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        baBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("bank_account_id ASC");
                    loadAll = true;
                    params = baLayoutPaging.getLayoutParams();
                    params.height = 0;
                    baLayoutPaging.setLayoutParams(params);
                    baBtnShowList.setText("Show Half");
                } else {
                    baTextPaging.setText("1");
                    counter = 0;
                    loadData("bank_account_id ASC");
                    loadAll = false;
                    params = baLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    baLayoutPaging.setLayoutParams(params);
                    baBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        baSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        baSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        baSpinnerSortAD.setAdapter(spinnerAdapter);

        baSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, baSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, baSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        baBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baEditSearch.getText().toString().matches("")){
                    baSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(baEditSearch.getText()));
            }
        });

        loadData("bank_account_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("bank_account_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("bank_account_name ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("bank_account_number ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("bank_account_number DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("bank_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("bank_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("currency_code ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("currency_code DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("ending_reconcile_balance ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("ending_reconcile_balance DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("last_reconciled_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("last_reconciled_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("bank_account_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("bank_account_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("bank_account_name ASC");
        else if (position == 2 && posAD == 0)
            loadData("bank_account_number ASC");
        else if (position == 2 && posAD == 1)
            loadData("bank_account_number DESC");
        else if (position == 3 && posAD == 0)
            loadData("bank_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("bank_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("currency_code ASC");
        else if (position == 4 && posAD == 1)
            loadData("currency_code DESC");
        else if (position == 5 && posAD == 0)
            loadData("ending_reconcile_balance ASC");
        else if (position == 5 && posAD == 1)
            loadData("ending_reconcile_balance DESC");
        else if (position == 6 && posAD == 0)
            loadData("last_reconciled_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("last_reconciled_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 7 && posAD == 1)
            loadData("is_active DESC");
        else loadData("bank_account_id ASC");
    }

    private void setAdapterList(){
        adapter = new BankAccountsFragment.MyRecyclerViewAdapter(bankAccounts, mListener);
        baRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        baRecycler.setAdapter(null);
        bankAccounts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BANK_ACCOUNT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankAccounts.add(new BankAccount(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (baEditSearch.getText().toString().matches("")){
                                baSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(baEditSearch.getText()));
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
        baRecycler.setAdapter(null);
        bankAccounts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BANK_ACCOUNT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankAccounts.add(new BankAccount(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (baEditSearch.getText().toString().matches("")){
                                baSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(baEditSearch.getText()));
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
        void onListFragmentInteraction(BankAccount item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<BankAccount> mValues;
        private final List<BankAccount> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<BankAccount> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_bank_accounts_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.baTextName.setText(""+mValues.get(position).getBank_account_name());
            holder.baTextNumber.setText(""+mValues.get(position).getBank_account_number());
            holder.baTextBankName.setText(""+mValues.get(position).getBank_name());
            holder.baTextCode.setText(""+mValues.get(position).getCurrency_code());
            holder.baTextBalance.setText(""+mValues.get(position).getEnding_reconcile_balance());
            holder.baTextDate.setText(""+mValues.get(position).getLast_reconciled_date());
            holder.baTextIsActive.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.baLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.baLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), ""+mValues.get(position).getCreated_by(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(), JobOrderDetailActivity.class);
//                    intent.putExtra("detailJO", mValues.get(position));
//                    holder.itemView.getContext().startActivity(intent);
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
                List<BankAccount> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((BankAccount) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (BankAccount item : values){
                        if (baSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getBank_account_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_account_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCurrency_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnding_reconcile_balance().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLast_reconciled_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getBank_account_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getBank_account_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getBank_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCurrency_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getEnding_reconcile_balance().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getLast_reconciled_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (baSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getIs_active().toLowerCase().contains(filterPattern)){
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

            public final TextView baTextName;
            public final TextView baTextNumber;
            public final TextView baTextBankName;
            public final TextView baTextCode;
            public final TextView baTextBalance;
            public final TextView baTextDate;
            public final TextView baTextIsActive;

            public final LinearLayout baLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                baTextName = (TextView) view.findViewById(R.id.baTextName);
                baTextNumber = (TextView) view.findViewById(R.id.baTextNumber);
                baTextBankName = (TextView) view.findViewById(R.id.baTextBankName);
                baTextCode = (TextView) view.findViewById(R.id.baTextCode);
                baTextBalance = (TextView) view.findViewById(R.id.baTextBalance);
                baTextDate = (TextView) view.findViewById(R.id.baTextDate);
                baTextIsActive = (TextView) view.findViewById(R.id.baTextIsActive);

                baLayoutList = (LinearLayout) view.findViewById(R.id.baLayoutList);
            }
        }
    }
}
