package com.example.aismobile.Finance.BankTransaction;

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
import com.example.aismobile.Data.FinanceAccounting.BankTransaction;
import com.example.aismobile.R;
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

public class BankTransactionFragment extends Fragment {

    public TextView btTextPaging;
    public EditText btEditSearch;
    public ImageView btBtnSearch;
    public RecyclerView btRecycler;
    public FloatingActionButton btFabAdd;
    public Spinner btSpinnerSearch;
    public Spinner btSpinnerSort;
    public Spinner btSpinnerSortAD;
    public Button btBtnShowList;
    public ImageButton btBtnBefore;
    public ImageButton btBtnNext;
    public LinearLayout btLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public BankTransactionFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Bank Transaction Number", "Rek Bank", "Checked By",
            "Approval 1", "Approval 2", "Tgl Transaksi", "Total Amount", "Status", "Reconciled"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Bank Transaction Number", "Berdasarkan Rek Bank",
            "Berdasarkan Checked By", "Berdasarkan Approval 1", "Berdasarkan Approval 2", "Berdasarkan Tgl Transaksi",
            "Berdasarkan Total Amount", "Berdasarkan Status", "Berdasarkan Reconciled"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<BankTransaction> bankTransactions;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public BankTransactionFragment() {
    }

    public static BankTransactionFragment newInstance() {
        BankTransactionFragment fragment = new BankTransactionFragment();
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

        bankTransactions = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_transaction, container, false);

        // Set the adapter
        btRecycler = (RecyclerView) view.findViewById(R.id.btRecycler);
        if (mColumnCount <= 1) {
            btRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            btRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        btFabAdd = (FloatingActionButton) view.findViewById(R.id.btFabAdd);
        btEditSearch = (EditText) view.findViewById(R.id.btEditSearch);
        btTextPaging = (TextView) view.findViewById(R.id.btTextPaging);
        btBtnSearch = (ImageView) view.findViewById(R.id.btBtnSearch);
        btSpinnerSearch = (Spinner) view.findViewById(R.id.btSpinnerSearch);
        btSpinnerSort = (Spinner) view.findViewById(R.id.btSpinnerSort);
        btSpinnerSortAD = (Spinner) view.findViewById(R.id.btSpinnerSortAD);
        btBtnShowList = (Button) view.findViewById(R.id.btBtnShowList);
        btBtnBefore = (ImageButton) view.findViewById(R.id.btBtnBefore);
        btBtnNext = (ImageButton) view.findViewById(R.id.btBtnNext);
        btLayoutPaging = (LinearLayout) view.findViewById(R.id.btLayoutPaging);

        btBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(btTextPaging.getText()));
                setSortHalf(btSpinnerSort.getSelectedItemPosition(), btSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(btTextPaging.getText()))+1;
                btTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        btBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(btTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(btTextPaging.getText()))-2);
                    setSortHalf(btSpinnerSort.getSelectedItemPosition(), btSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(btTextPaging.getText()))-1;
                    btTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        btBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("transaction_date DESC");
                    loadAll = true;
                    params = btLayoutPaging.getLayoutParams();
                    params.height = 0;
                    btLayoutPaging.setLayoutParams(params);
                    btBtnShowList.setText("Show Half");
                } else {
                    btTextPaging.setText("1");
                    counter = 0;
                    loadData("transaction_date DESC");
                    loadAll = false;
                    params = btLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    btLayoutPaging.setLayoutParams(params);
                    btBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        btSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        btSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        btSpinnerSortAD.setAdapter(spinnerAdapter);

        btSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, btSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, btSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btEditSearch.getText().toString().matches("")){
                    btSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(btEditSearch.getText()));
            }
        });

//        loadData("transaction_date DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("bank_transaction_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("bank_transaction_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("bank_account_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("bank_account_id DESC");
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
            loadDataAll("bt.transaction_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("bt.transaction_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("total_amount ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("total_amount DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("status ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("status DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("reconciled ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("reconciled DESC");
        else loadDataAll("bt.transaction_date DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("bank_transaction_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("bank_transaction_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("bank_account_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("bank_account_id DESC");
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
            loadData("bt.transaction_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("bt.transaction_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("total_amount ASC");
        else if (position == 7 && posAD == 1)
            loadData("total_amount DESC");
        else if (position == 8 && posAD == 0)
            loadData("status ASC");
        else if (position == 8 && posAD == 1)
            loadData("status DESC");
        else if (position == 9 && posAD == 0)
            loadData("reconciled ASC");
        else if (position == 9 && posAD == 1)
            loadData("reconciled DESC");
        else loadData("bt.transaction_date DESC");
    }

    private void setAdapterList(){
        adapter = new BankTransactionFragment.MyRecyclerViewAdapter(bankTransactions, mListener);
        btRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        btRecycler.setAdapter(null);
        bankTransactions.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BANK_TRANSACTION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankTransactions.add(new BankTransaction(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (btEditSearch.getText().toString().matches("")){
                                btSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(btEditSearch.getText()));
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
        btRecycler.setAdapter(null);
        bankTransactions.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BANK_TRANSACTION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankTransactions.add(new BankTransaction(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (btEditSearch.getText().toString().matches("")){
                                btSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(btEditSearch.getText()));
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
        void onListFragmentInteraction(BankTransaction item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<BankTransaction> mValues;
        private final List<BankTransaction> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<BankTransaction> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_bank_transaction_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.btTextNumber.setText(""+mValues.get(position).getBank_transaction_number());
            holder.btTextRekBank.setText(""+mValues.get(position).getBank_transaction_number());
            holder.btTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.btTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.btTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.btTextTglTrans.setText(""+mValues.get(position).getTransaction_date());
            holder.btTextStatus.setText(""+mValues.get(position).getStatus());
            holder.btTextReconciled.setText(""+mValues.get(position).getReconciled());

            double toDouble = Double.valueOf(mValues.get(position).getTotal_amount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.btTextTotal.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.btLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.btLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailBankTransactionActivity.class);
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
                List<BankTransaction> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((BankTransaction) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (BankTransaction item : values){
                        if (btSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTransaction_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStatus().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReconciled().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getTransaction_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getBank_transaction_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getStatus().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (btSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getReconciled().toLowerCase().contains(filterPattern)){
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

            public final TextView btTextNumber;
            public final TextView btTextRekBank;
            public final TextView btTextCheckedBy;
            public final TextView btTextApproval1;
            public final TextView btTextApproval2;
            public final TextView btTextTglTrans;
            public final TextView btTextTotal;
            public final TextView btTextStatus;
            public final TextView btTextReconciled;

            public final LinearLayout btLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                btTextNumber = (TextView) view.findViewById(R.id.btTextNumber);
                btTextRekBank = (TextView) view.findViewById(R.id.btTextRekBank);
                btTextCheckedBy = (TextView) view.findViewById(R.id.btTextCheckedBy);
                btTextApproval1 = (TextView) view.findViewById(R.id.btTextApproval1);
                btTextApproval2 = (TextView) view.findViewById(R.id.btTextApproval2);
                btTextTglTrans = (TextView) view.findViewById(R.id.btTextTglTrans);
                btTextTotal = (TextView) view.findViewById(R.id.btTextTotal);
                btTextStatus = (TextView) view.findViewById(R.id.btTextStatus);
                btTextReconciled = (TextView) view.findViewById(R.id.btTextReconciled);

                btLayoutList = (LinearLayout) view.findViewById(R.id.btLayoutList);
            }
        }
    }
}
