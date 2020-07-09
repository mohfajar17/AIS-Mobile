package com.example.aismobile.Finance.SupplierInvoice;

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
import com.example.aismobile.Data.FinanceAccounting.SupplierInvoice;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierInvoiceFragment extends Fragment {

    public TextView siTextPaging;
    public EditText siEditSearch;
    public ImageView siBtnSearch;
    public RecyclerView siRecycler;
    public FloatingActionButton siFabAdd;
    public Spinner siSpinnerSearch;
    public Spinner siSpinnerSort;
    public Spinner siSpinnerSortAD;
    public Button siBtnShowList;
    public ImageButton siBtnBefore;
    public ImageButton siBtnNext;
    public LinearLayout siLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public SupplierInvoiceFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SISpinnerSearch = {"Semua Data", "Supplier Invoice Number", "Supplier Name", "Supplier Invoice Date",
            "Invoice Receipt Date", "Due Date", "Payment Date", "Late Days", "Total", "Status"};
    public String[] SISpinnerSort = {"-- Sort By --", "Berdasarkan Supplier Invoice Number", "Berdasarkan Supplier Name",
            "Berdasarkan Supplier Invoice Date", "Berdasarkan Invoice Receipt Date", "Berdasarkan Due Date",
            "Berdasarkan Payment Date", "Berdasarkan Late Days", "Berdasarkan Total", "Berdasarkan Status"};
    public String[] SIADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<SupplierInvoice> supplierInvoices;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public SupplierInvoiceFragment() {
    }

    public static SupplierInvoiceFragment newInstance() {
        SupplierInvoiceFragment fragment = new SupplierInvoiceFragment();
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

        supplierInvoices = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_invoice, container, false);

        // Set the adapter
        siRecycler = (RecyclerView) view.findViewById(R.id.siRecycler);
        if (mColumnCount <= 1) {
            siRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            siRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        siFabAdd = (FloatingActionButton) view.findViewById(R.id.siFabAdd);
        siEditSearch = (EditText) view.findViewById(R.id.siEditSearch);
        siTextPaging = (TextView) view.findViewById(R.id.siTextPaging);
        siBtnSearch = (ImageView) view.findViewById(R.id.siBtnSearch);
        siSpinnerSearch = (Spinner) view.findViewById(R.id.siSpinnerSearch);
        siSpinnerSort = (Spinner) view.findViewById(R.id.siSpinnerSort);
        siSpinnerSortAD = (Spinner) view.findViewById(R.id.siSpinnerSortAD);
        siBtnShowList = (Button) view.findViewById(R.id.siBtnShowList);
        siBtnBefore = (ImageButton) view.findViewById(R.id.siBtnBefore);
        siBtnNext = (ImageButton) view.findViewById(R.id.siBtnNext);
        siLayoutPaging = (LinearLayout) view.findViewById(R.id.siLayoutPaging);

        siBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(siTextPaging.getText()));
                setSortHalf(siSpinnerSort.getSelectedItemPosition(), siSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(siTextPaging.getText()))+1;
                siTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        siBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(siTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(siTextPaging.getText()))-2);
                    setSortHalf(siSpinnerSort.getSelectedItemPosition(), siSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(siTextPaging.getText()))-1;
                    siTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        siBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("invoice_receipt_date DESC");
                    loadAll = true;
                    params = siLayoutPaging.getLayoutParams();
                    params.height = 0;
                    siLayoutPaging.setLayoutParams(params);
                    siBtnShowList.setText("Show Half");
                } else {
                    siTextPaging.setText("1");
                    counter = 0;
                    loadData("invoice_receipt_date DESC");
                    loadAll = false;
                    params = siLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    siLayoutPaging.setLayoutParams(params);
                    siBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SISpinnerSearch);
        siSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SISpinnerSort);
        siSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SIADSpinnerSort);
        siSpinnerSortAD.setAdapter(spinnerAdapter);

        siSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, siSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, siSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        siBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siEditSearch.getText().toString().matches("")){
                    siSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(siEditSearch.getText()));
            }
        });

        loadData("invoice_receipt_date DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("supplier_invoice_number ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("supplier_invoice_number ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("supplier_invoice_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("supplier_invoice_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("invoice_receipt_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("invoice_receipt_date DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("due_date ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("due_date DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("payment_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("payment_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("late_days ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("late_days DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("TotalSI ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("TotalSI DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("supplier_invoice_status ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("supplier_invoice_status DESC");
        else loadDataAll("invoice_receipt_date DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("supplier_invoice_number ASC");
        else if (position == 1 && posAD == 1)
            loadData("supplier_invoice_number ASC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("supplier_invoice_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("supplier_invoice_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("invoice_receipt_date ASC");
        else if (position == 4 && posAD == 1)
            loadData("invoice_receipt_date DESC");
        else if (position == 5 && posAD == 0)
            loadData("due_date ASC");
        else if (position == 5 && posAD == 1)
            loadData("due_date DESC");
        else if (position == 6 && posAD == 0)
            loadData("payment_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("payment_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("late_days ASC");
        else if (position == 7 && posAD == 1)
            loadData("late_days DESC");
        else if (position == 8 && posAD == 0)
            loadData("TotalSI ASC");
        else if (position == 8 && posAD == 1)
            loadData("TotalSI DESC");
        else if (position == 9 && posAD == 0)
            loadData("supplier_invoice_status ASC");
        else if (position == 9 && posAD == 1)
            loadData("supplier_invoice_status DESC");
        else loadData("invoice_receipt_date DESC");
    }

    private void setAdapterList(){
        adapter = new SupplierInvoiceFragment.MyRecyclerViewAdapter(supplierInvoices, mListener);
        siRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        siRecycler.setAdapter(null);
        supplierInvoices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_INVOICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            supplierInvoices.add(new SupplierInvoice(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (siEditSearch.getText().toString().matches("")){
                                siSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(siEditSearch.getText()));
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
        siRecycler.setAdapter(null);
        supplierInvoices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_INVOICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            supplierInvoices.add(new SupplierInvoice(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (siEditSearch.getText().toString().matches("")){
                                siSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(siEditSearch.getText()));
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
        void onListFragmentInteraction(SupplierInvoice item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<SupplierInvoice> mValues;
        private final List<SupplierInvoice> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<SupplierInvoice> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_supplier_invoice_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.siTextNumber.setText(""+mValues.get(position).getSupplier_invoice_number());
            holder.siTextName.setText(""+mValues.get(position).getSupplier_name());
            holder.siTextInvoiceDate.setText(""+mValues.get(position).getSupplier_invoice_date());
            holder.siTextReceiptDate.setText(""+mValues.get(position).getInvoice_receipt_date());
            holder.siTextDueDate.setText(""+mValues.get(position).getDue_date());
            holder.siTextPaymentDate.setText(""+mValues.get(position).getPayment_date());
            holder.siTextLateDays.setText(""+mValues.get(position).getLate_days());
            holder.siTextTotalSI.setText(""+mValues.get(position).getTotalSI());
            holder.siTextStatus.setText(""+mValues.get(position).getSupplier_invoice_status());

            if (position%2==0)
                holder.siLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.siLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<SupplierInvoice> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((SupplierInvoice) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (SupplierInvoice item : values){
                        if (siSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getSupplier_invoice_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_invoice_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getInvoice_receipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPayment_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLate_days().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTotalSI().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_invoice_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getSupplier_invoice_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getSupplier_invoice_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getInvoice_receipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getPayment_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getLate_days().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getTotalSI().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (siSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getSupplier_invoice_status().toLowerCase().contains(filterPattern)){
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

            public final TextView siTextNumber;
            public final TextView siTextName;
            public final TextView siTextInvoiceDate;
            public final TextView siTextReceiptDate;
            public final TextView siTextDueDate;
            public final TextView siTextPaymentDate;
            public final TextView siTextLateDays;
            public final TextView siTextTotalSI;
            public final TextView siTextStatus;

            public final LinearLayout siLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                siTextNumber = (TextView) view.findViewById(R.id.siTextNumber);
                siTextName = (TextView) view.findViewById(R.id.siTextName);
                siTextInvoiceDate = (TextView) view.findViewById(R.id.siTextInvoiceDate);
                siTextReceiptDate = (TextView) view.findViewById(R.id.siTextReceiptDate);
                siTextDueDate = (TextView) view.findViewById(R.id.siTextDueDate);
                siTextPaymentDate = (TextView) view.findViewById(R.id.siTextPaymentDate);
                siTextLateDays = (TextView) view.findViewById(R.id.siTextLateDays);
                siTextTotalSI = (TextView) view.findViewById(R.id.siTextTotalSI);
                siTextStatus = (TextView) view.findViewById(R.id.siTextStatus);

                siLayoutList = (LinearLayout) view.findViewById(R.id.siLayoutList);
            }
        }
    }
}
