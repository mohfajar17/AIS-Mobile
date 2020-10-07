package com.example.aismobile.Finance.CustomerInvoice;

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
import com.example.aismobile.Data.FinanceAccounting.CustomerInvoice;
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

public class CustomerInvoiceFragment extends Fragment {

    public TextView ciTextPaging;
    public EditText ciEditSearch;
    public ImageView ciBtnSearch;
    public RecyclerView ciRecycler;
    public FloatingActionButton ciFabAdd;
    public Spinner ciSpinnerSearch;
    public Spinner ciSpinnerSort;
    public Spinner ciSpinnerSortAD;
    public Button ciBtnShowList;
    public ImageButton ciBtnBefore;
    public ImageButton ciBtnNext;
    public LinearLayout ciLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public CustomerInvoiceFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CISpinnerSearch = {"Semua Data", "Nomor Customer Invoice", "Sales Order Invoice Description",
            "Job Order", "Sales Quotation",
            "Work Completion", "Due Date", "Client PO Number", "Status", "Payment Late", "Grand Total"};
    public String[] CISpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Customer Invoice", "Berdasarkan Sales Order Invoice Description",
            "Berdasarkan Job Order", "Berdasarkan Sales Quotation", "Berdasarkan Work Completion", "Berdasarkan Due Date",
            "Berdasarkan Client PO Number", "Berdasarkan Status", "Berdasarkan Payment Late", "Berdasarkan Grand Total"};
    public String[] CIADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CustomerInvoice> customerInvoices;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CustomerInvoiceFragment() {
    }

    public static CustomerInvoiceFragment newInstance() {
        CustomerInvoiceFragment fragment = new CustomerInvoiceFragment();
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

        customerInvoices = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_invoice, container, false);

        // Set the adapter
        ciRecycler = (RecyclerView) view.findViewById(R.id.ciRecycler);
        if (mColumnCount <= 1) {
            ciRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            ciRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        ciFabAdd = (FloatingActionButton) view.findViewById(R.id.ciFabAdd);
        ciEditSearch = (EditText) view.findViewById(R.id.ciEditSearch);
        ciTextPaging = (TextView) view.findViewById(R.id.ciTextPaging);
        ciBtnSearch = (ImageView) view.findViewById(R.id.ciBtnSearch);
        ciSpinnerSearch = (Spinner) view.findViewById(R.id.ciSpinnerSearch);
        ciSpinnerSort = (Spinner) view.findViewById(R.id.ciSpinnerSort);
        ciSpinnerSortAD = (Spinner) view.findViewById(R.id.ciSpinnerSortAD);
        ciBtnShowList = (Button) view.findViewById(R.id.ciBtnShowList);
        ciBtnBefore = (ImageButton) view.findViewById(R.id.ciBtnBefore);
        ciBtnNext = (ImageButton) view.findViewById(R.id.ciBtnNext);
        ciLayoutPaging = (LinearLayout) view.findViewById(R.id.ciLayoutPaging);

        ciBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(ciTextPaging.getText()));
                setSortHalf(ciSpinnerSort.getSelectedItemPosition(), ciSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(ciTextPaging.getText()))+1;
                ciTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        ciBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(ciTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(ciTextPaging.getText()))-2);
                    setSortHalf(ciSpinnerSort.getSelectedItemPosition(), ciSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(ciTextPaging.getText()))-1;
                    ciTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        ciBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("sales_order_invoice_id DESC");
                    loadAll = true;
                    params = ciLayoutPaging.getLayoutParams();
                    params.height = 0;
                    ciLayoutPaging.setLayoutParams(params);
                    ciBtnShowList.setText("Show Half");
                } else {
                    ciTextPaging.setText("1");
                    counter = 0;
                    loadData("sales_order_invoice_id DESC");
                    loadAll = false;
                    params = ciLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    ciLayoutPaging.setLayoutParams(params);
                    ciBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CISpinnerSearch);
        ciSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CISpinnerSort);
        ciSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CIADSpinnerSort);
        ciSpinnerSortAD.setAdapter(spinnerAdapter);

        ciSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, ciSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, ciSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ciBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ciEditSearch.getText().toString().matches("")){
                    ciSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(ciEditSearch.getText()));
            }
        });

//        loadData("sales_order_invoice_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("sales_order_invoice_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("sales_order_invoice_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("sales_order_invoice_description ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("sales_order_invoice_description DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("sales_quotation_id ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("sales_quotation_id DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("job_progress_report_id ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("job_progress_report_id DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("due_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("due_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("client_po_number ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("client_po_number DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("sales_order_invoice_status ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("sales_order_invoice_status DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("payment_late ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("payment_late DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("grand_total ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("grand_total DESC");
        else loadDataAll("sales_order_invoice_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("supplier_invoice_number ASC");
        else if (position == 1 && posAD == 1)
            loadData("supplier_invoice_number ASC");
        else if (position == 2 && posAD == 0)
            loadData("sales_order_invoice_description ASC");
        else if (position == 2 && posAD == 1)
            loadData("sales_order_invoice_description DESC");
        else if (position == 3 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("sales_quotation_id ASC");
        else if (position == 4 && posAD == 1)
            loadData("sales_quotation_id DESC");
        else if (position == 5 && posAD == 0)
            loadData("job_progress_report_id ASC");
        else if (position == 5 && posAD == 1)
            loadData("job_progress_report_id DESC");
        else if (position == 6 && posAD == 0)
            loadData("due_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("due_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("client_po_number ASC");
        else if (position == 7 && posAD == 1)
            loadData("client_po_number DESC");
        else if (position == 8 && posAD == 0)
            loadData("sales_order_invoice_status ASC");
        else if (position == 8 && posAD == 1)
            loadData("sales_order_invoice_status DESC");
        else if (position == 9 && posAD == 0)
            loadData("payment_late ASC");
        else if (position == 9 && posAD == 1)
            loadData("payment_late DESC");
        else if (position == 10 && posAD == 0)
            loadData("grand_total ASC");
        else if (position == 10 && posAD == 1)
            loadData("grand_total DESC");
        else loadData("sales_order_invoice_id DESC");
    }

    private void setAdapterList(){
        adapter = new CustomerInvoiceFragment.MyRecyclerViewAdapter(customerInvoices, mListener);
        ciRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        ciRecycler.setAdapter(null);
        customerInvoices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_INVOICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerInvoices.add(new CustomerInvoice(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (ciEditSearch.getText().toString().matches("")){
                                ciSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(ciEditSearch.getText()));
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
        ciRecycler.setAdapter(null);
        customerInvoices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_INVOICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerInvoices.add(new CustomerInvoice(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (ciEditSearch.getText().toString().matches("")){
                                ciSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(ciEditSearch.getText()));
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
        void onListFragmentInteraction(CustomerInvoice item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CustomerInvoice> mValues;
        private final List<CustomerInvoice> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CustomerInvoice> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_customer_invoice_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ciTextNumber.setText(""+mValues.get(position).getSales_order_invoice_number());
            holder.ciTextSoiDesk.setText(""+mValues.get(position).getSales_order_invoice_description());
            holder.ciTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.ciTextSalesQuot.setText(""+mValues.get(position).getSales_quotation_id());
            holder.ciTextWorkCompletion.setText(""+mValues.get(position).getJob_progress_report_id());
            holder.ciTextDueDate.setText(""+mValues.get(position).getDue_date());
            holder.ciTextPONumber.setText(""+mValues.get(position).getClient_po_number());
            holder.ciTextStatus.setText(""+mValues.get(position).getSales_order_invoice_status());
            holder.ciTextPaymentLate.setText(""+mValues.get(position).getPayment_late());

            double toDouble = Double.valueOf(mValues.get(position).getGrand_total());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.ciTextTotal.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.ciLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ciLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CustomerInvoiceDetailActivity.class);
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
                List<CustomerInvoice> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CustomerInvoice) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CustomerInvoice item : values){
                        if (ciSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getSales_order_invoice_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSales_order_invoice_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSales_quotation_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_progress_report_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getClient_po_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSales_order_invoice_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPayment_late().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getGrand_total().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getSales_order_invoice_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSales_order_invoice_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getSales_quotation_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getJob_progress_report_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getDue_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getClient_po_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getSales_order_invoice_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getPayment_late().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ciSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getGrand_total().toLowerCase().contains(filterPattern)){
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

            public final TextView ciTextNumber;
            public final TextView ciTextSoiDesk;
            public final TextView ciTextJobOrder;
            public final TextView ciTextSalesQuot;
            public final TextView ciTextWorkCompletion;
            public final TextView ciTextDueDate;
            public final TextView ciTextPONumber;
            public final TextView ciTextStatus;
            public final TextView ciTextPaymentLate;
            public final TextView ciTextTotal;

            public final LinearLayout ciLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ciTextNumber = (TextView) view.findViewById(R.id.ciTextNumber);
                ciTextSoiDesk = (TextView) view.findViewById(R.id.ciTextSoiDesk);
                ciTextJobOrder = (TextView) view.findViewById(R.id.ciTextJobOrder);
                ciTextSalesQuot = (TextView) view.findViewById(R.id.ciTextSalesQuot);
                ciTextWorkCompletion = (TextView) view.findViewById(R.id.ciTextWorkCompletion);
                ciTextDueDate = (TextView) view.findViewById(R.id.ciTextDueDate);
                ciTextPONumber = (TextView) view.findViewById(R.id.ciTextPONumber);
                ciTextStatus = (TextView) view.findViewById(R.id.ciTextStatus);
                ciTextPaymentLate = (TextView) view.findViewById(R.id.ciTextPaymentLate);
                ciTextTotal = (TextView) view.findViewById(R.id.ciTextTotal);

                ciLayoutList = (LinearLayout) view.findViewById(R.id.ciLayoutList);
            }
        }
    }
}
