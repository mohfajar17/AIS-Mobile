package com.asukacorp.aismobile.Purchasing.PurchaseOrder;

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
import com.asukacorp.aismobile.Data.Purchasing.PurchaseOrder;
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

public class PurchaseOrdersFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView poTextPaging;
    public EditText poEditSearch;
    public ImageView poBtnSearch;
    public RecyclerView poRecycler;
    public FloatingActionButton poFabAdd;
    public Spinner poSpinnerSearch;
    public Spinner poSpinnerSort;
    public Spinner poSpinnerSortAD;
    public Button poBtnShowList;
    public ImageButton poBtnBefore;
    public ImageButton poBtnNext;
    public LinearLayout poLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public PurchaseOrdersFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] POSpinnerSearch = {"Semua Data", "Purchase Order Number", "Supplier", "Tanggal Kedatangan",
            "Job Order", "Termin Pembayaran", "Dibuat Oleh", "Checked By", "Persetujuan", "Approval 1", "Status"};
    public String[] POSpinnerSort = {"-- Sort By --", "Berdasarkan Purchase Order Number", "Berdasarkan Supplier",
            "Berdasarkan Tanggal Kedatangan", "Berdasarkan Job Order", "Berdasarkan Termin Pembayaran", "Berdasarkan Dibuat Oleh",
            "Berdasarkan Checked By", "Berdasarkan Persetujuan", "Berdasarkan Approval 1", "Berdasarkan Status"};
    public String[] POADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<PurchaseOrder> purchaseOrders;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public PurchaseOrdersFragment() {
    }

    public static PurchaseOrdersFragment newInstance() {
        PurchaseOrdersFragment fragment = new PurchaseOrdersFragment();
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

        purchaseOrders = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_orders, container, false);

        // Set the adapter
        poRecycler = (RecyclerView) view.findViewById(R.id.poRecycler);
        if (mColumnCount <= 1) {
            poRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            poRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        poFabAdd = (FloatingActionButton) view.findViewById(R.id.poFabAdd);
        poEditSearch = (EditText) view.findViewById(R.id.poEditSearch);
        poTextPaging = (TextView) view.findViewById(R.id.poTextPaging);
        poBtnSearch = (ImageView) view.findViewById(R.id.poBtnSearch);
        poSpinnerSearch = (Spinner) view.findViewById(R.id.poSpinnerSearch);
        poSpinnerSort = (Spinner) view.findViewById(R.id.poSpinnerSort);
        poSpinnerSortAD = (Spinner) view.findViewById(R.id.poSpinnerSortAD);
        poBtnShowList = (Button) view.findViewById(R.id.poBtnShowList);
        poBtnBefore = (ImageButton) view.findViewById(R.id.poBtnBefore);
        poBtnNext = (ImageButton) view.findViewById(R.id.poBtnNext);
        poLayoutPaging = (LinearLayout) view.findViewById(R.id.poLayoutPaging);

        poBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(poTextPaging.getText()));
                setSortHalf(poSpinnerSort.getSelectedItemPosition(), poSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(poTextPaging.getText()))+1;
                poTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        poBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(poTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(poTextPaging.getText()))-2);
                    setSortHalf(poSpinnerSort.getSelectedItemPosition(), poSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(poTextPaging.getText()))-1;
                    poTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        poBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("purchase_order_id DESC");
                    loadAll = true;
                    params = poLayoutPaging.getLayoutParams();
                    params.height = 0;
                    poLayoutPaging.setLayoutParams(params);
                    poBtnShowList.setText("Show Half");
                } else {
                    poTextPaging.setText("1");
                    counter = 0;
                    loadData("purchase_order_id DESC");
                    loadAll = false;
                    params = poLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    poLayoutPaging.setLayoutParams(params);
                    poBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, POSpinnerSearch);
        poSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, POSpinnerSort);
        poSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, POADSpinnerSort);
        poSpinnerSortAD.setAdapter(spinnerAdapter);

        poSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, poSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, poSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        poBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poEditSearch.getText().toString().matches("")){
                    poSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(poEditSearch.getText()));
            }
        });

//        loadData("purchase_order_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("purchase_order_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("purchase_order_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("job_order_number ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("job_order_number DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("payment_term_id ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("payment_term_id DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("checked_by DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("approval_assign_id ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("approval_assign_id DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("po_approval1 ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("po_approval1 DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("purchase_order_status_id ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("purchase_order_status_id DESC");
        else loadDataAll("purchase_order_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("purchase_order_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("purchase_order_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("job_order_number ASC");
        else if (position == 4 && posAD == 1)
            loadData("job_order_number DESC");
        else if (position == 5 && posAD == 0)
            loadData("payment_term_id ASC");
        else if (position == 5 && posAD == 1)
            loadData("payment_term_id DESC");
        else if (position == 6 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 6 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 7 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 7 && posAD == 1)
            loadData("checked_by DESC");
        else if (position == 8 && posAD == 0)
            loadData("approval_assign_id ASC");
        else if (position == 8 && posAD == 1)
            loadData("approval_assign_id DESC");
        else if (position == 9 && posAD == 0)
            loadData("po_approval1 ASC");
        else if (position == 9 && posAD == 1)
            loadData("po_approval1 DESC");
        else if (position == 10 && posAD == 0)
            loadData("purchase_order_status_id ASC");
        else if (position == 10 && posAD == 1)
            loadData("purchase_order_status_id DESC");
        else loadData("purchase_order_id DESC");
    }

    private void setAdapterList(){
        adapter = new PurchaseOrdersFragment.MyRecyclerViewAdapter(purchaseOrders, mListener);
        poRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        poRecycler.setAdapter(null);
        purchaseOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseOrders.add(new PurchaseOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (poEditSearch.getText().toString().matches("")){
                                poSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(poEditSearch.getText()));
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
        poRecycler.setAdapter(null);
        purchaseOrders.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_ORDER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseOrders.add(new PurchaseOrder(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (poEditSearch.getText().toString().matches("")){
                                poSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(poEditSearch.getText()));
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
        void onListFragmentInteraction(PurchaseOrder item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<PurchaseOrder> mValues;
        private final List<PurchaseOrder> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<PurchaseOrder> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_purchase_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.poTextNomor.setText(""+mValues.get(position).getPurchase_order_number());
            holder.poTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.poTextfile.setText("");
            holder.poTextTglKedatangan.setText(""+mValues.get(position).getEnd_date());
            holder.poTextJobOrder.setText(""+mValues.get(position).getJob_order_number());
            holder.poTextTermPembayaran.setText(""+mValues.get(position).getPayment_term_id());
            holder.poTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.poTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.poTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.poTextApproval1.setText(""+mValues.get(position).getPo_approval1());

            if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==1)
                holder.poTextStatus.setText("New");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==2)
                holder.poTextStatus.setText("Pending");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==3)
                holder.poTextStatus.setText("Progress");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==4)
                holder.poTextStatus.setText("Complete");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==5)
                holder.poTextStatus.setText("Closed");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==6)
                holder.poTextStatus.setText("Cancel");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==7)
                holder.poTextStatus.setText("Received");

            if (position%2==0)
                holder.poLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.poLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getPurchase_order_id(), mValues.get(position));
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
                List<PurchaseOrder> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((PurchaseOrder) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (PurchaseOrder item : values){
                        if (poSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getPurchase_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPayment_term_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval_assign_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPo_approval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPurchase_order_status_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getPurchase_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getPayment_term_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getApproval_assign_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getPo_approval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (poSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getPurchase_order_status_id().toLowerCase().contains(filterPattern)){
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

            public final TextView poTextNomor;
            public final TextView poTextSupplier;
            public final TextView poTextfile;
            public final TextView poTextTglKedatangan;
            public final TextView poTextJobOrder;
            public final TextView poTextTermPembayaran;
            public final TextView poTextDibuat;
            public final TextView poTextCheckedBy;
            public final TextView poTextPersetujuan;
            public final TextView poTextApproval1;
            public final TextView poTextStatus;

            public final LinearLayout poLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                poTextNomor = (TextView) view.findViewById(R.id.poTextNomor);
                poTextSupplier = (TextView) view.findViewById(R.id.poTextSupplier);
                poTextfile = (TextView) view.findViewById(R.id.poTextfile);
                poTextTglKedatangan = (TextView) view.findViewById(R.id.poTextTglKedatangan);
                poTextJobOrder = (TextView) view.findViewById(R.id.poTextJobOrder);
                poTextTermPembayaran = (TextView) view.findViewById(R.id.poTextTermPembayaran);
                poTextDibuat = (TextView) view.findViewById(R.id.poTextDibuat);
                poTextCheckedBy = (TextView) view.findViewById(R.id.poTextCheckedBy);
                poTextPersetujuan = (TextView) view.findViewById(R.id.poTextPersetujuan);
                poTextApproval1 = (TextView) view.findViewById(R.id.poTextApproval1);
                poTextStatus = (TextView) view.findViewById(R.id.poTextStatus);

                poLayoutList = (LinearLayout) view.findViewById(R.id.poLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final PurchaseOrder purchaseOrder) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailPurchaseOrderActivity.class);
                        intent.putExtra("detail", purchaseOrder);
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
                param.put("feature", "" + "purchase-order");
                param.put("access", "" + "purchase-order:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
