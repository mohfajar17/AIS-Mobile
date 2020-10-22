package com.example.aismobile.Purchasing.CashOnDelivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Purchasing.CashOnDelivery;
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

public class CashOnDeliveryFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView codTextPaging;
    public EditText codEditSearch;
    public ImageView codBtnSearch;
    public RecyclerView codRecycler;
    public FloatingActionButton codFabAdd;
    public Spinner codSpinnerSearch;
    public Spinner codSpinnerSort;
    public Spinner codSpinnerSortAD;
    public Button codBtnShowList;
    public ImageButton codBtnBefore;
    public ImageButton codBtnNext;
    public LinearLayout codLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public CashOnDeliveryFragment.OnListFragmentInteractionListener mListener;
    public CashOnDeliveryFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CODSpinnerSearch = {"Semua Data", "Cash On Delivery Number", "Supplier", "Tanggal Akhir",
            "Termin Pembayaran", "Checked By", "Persetujuan", "Approval 1", "Status"};
    public String[] CODSpinnerSort = {"-- Sort By --", "Berdasarkan Cash On Delivery Number", "Berdasarkan Supplier",
            "Berdasarkan Tanggal Akhir", "Berdasarkan Termin Pembayaran", "Berdasarkan Checked By",
            "Berdasarkan Persetujuan", "Berdasarkan Approval 1", "Berdasarkan Status"};
    public String[] CODADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CashOnDelivery> cashOnDeliveries;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CashOnDeliveryFragment() {
    }

    public static CashOnDeliveryFragment newInstance() {
        CashOnDeliveryFragment fragment = new CashOnDeliveryFragment();
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

        cashOnDeliveries = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_on_delivery, container, false);

        codRecycler = (RecyclerView) view.findViewById(R.id.codRecycler);
        if (mColumnCount <= 1) {
            codRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            codRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        codFabAdd = (FloatingActionButton) view.findViewById(R.id.codFabAdd);
        codEditSearch = (EditText) view.findViewById(R.id.codEditSearch);
        codTextPaging = (TextView) view.findViewById(R.id.codTextPaging);
        codBtnSearch = (ImageView) view.findViewById(R.id.codBtnSearch);
        codSpinnerSearch = (Spinner) view.findViewById(R.id.codSpinnerSearch);
        codSpinnerSort = (Spinner) view.findViewById(R.id.codSpinnerSort);
        codSpinnerSortAD = (Spinner) view.findViewById(R.id.codSpinnerSortAD);
        codBtnShowList = (Button) view.findViewById(R.id.codBtnShowList);
        codBtnBefore = (ImageButton) view.findViewById(R.id.codBtnBefore);
        codBtnNext = (ImageButton) view.findViewById(R.id.codBtnNext);
        codLayoutPaging = (LinearLayout) view.findViewById(R.id.codLayoutPaging);

        codBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(codTextPaging.getText()));
                setSortHalf(codSpinnerSort.getSelectedItemPosition(), codSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(codTextPaging.getText()))+1;
                codTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        codBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(codTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(codTextPaging.getText()))-2);
                    setSortHalf(codSpinnerSort.getSelectedItemPosition(), codSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(codTextPaging.getText()))-1;
                    codTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        codBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("cash_on_delivery_id DESC");
                    loadAll = true;
                    params = codLayoutPaging.getLayoutParams();
                    params.height = 0;
                    codLayoutPaging.setLayoutParams(params);
                    codBtnShowList.setText("Show Half");
                } else {
                    codTextPaging.setText("1");
                    counter = 0;
                    loadData("cash_on_delivery_id DESC");
                    loadAll = false;
                    params = codLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    codLayoutPaging.setLayoutParams(params);
                    codBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CODSpinnerSearch);
        codSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CODSpinnerSort);
        codSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CODADSpinnerSort);
        codSpinnerSortAD.setAdapter(spinnerAdapter);

        codSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, codSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, codSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        codBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codEditSearch.getText().toString().matches("")){
                    codSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(codEditSearch.getText()));
            }
        });

//        loadData("cash_on_delivery_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("cash_on_delivery_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("cash_on_delivery_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("approval_assign_id ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("approval_assign_id DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("approval1 DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("purchase_order_status_id ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("purchase_order_status_id DESC");
        else loadDataAll("cash_on_delivery_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("cash_on_delivery_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("cash_on_delivery_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 4 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 5 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadData("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadData("approval_assign_id ASC");
        else if (position == 6 && posAD == 1)
            loadData("approval_assign_id DESC");
        else if (position == 7 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 7 && posAD == 1)
            loadData("approval1 DESC");
        else if (position == 8 && posAD == 0)
            loadData("purchase_order_status_id ASC");
        else if (position == 8 && posAD == 1)
            loadData("purchase_order_status_id DESC");
        else loadData("cash_on_delivery_id DESC");
    }

    private void setAdapterList(){
        adapter = new CashOnDeliveryFragment.MyRecyclerViewAdapter(cashOnDeliveries, mListener);
        codRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        codRecycler.setAdapter(null);
        cashOnDeliveries.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_ON_DELIVERY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveries.add(new CashOnDelivery(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (codEditSearch.getText().toString().matches("")){
                                codSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(codEditSearch.getText()));
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
        codRecycler.setAdapter(null);
        cashOnDeliveries.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_ON_DELIVERY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveries.add(new CashOnDelivery(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (codEditSearch.getText().toString().matches("")){
                                codSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(codEditSearch.getText()));
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
        if (context instanceof CashOnDeliveryFragment.OnListFragmentInteractionListener) {
            mListener = (CashOnDeliveryFragment.OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(CashOnDelivery item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<CashOnDeliveryFragment.MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CashOnDelivery> mValues;
        private final List<CashOnDelivery> values;
        private final CashOnDeliveryFragment.OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CashOnDelivery> mValues, CashOnDeliveryFragment.OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public CashOnDeliveryFragment.MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_on_delivery_list, parent, false);
            return new CashOnDeliveryFragment.MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CashOnDeliveryFragment.MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.codTextNomor.setText(""+mValues.get(position).getCash_on_delivery_number());
            holder.codTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.codTextfile.setText("");
            holder.codTextTglAwal.setText(""+mValues.get(position).getEnd_date());
            holder.codTextTermPembayaran.setText(""+mValues.get(position).getJob_order_id());
            holder.codTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.codTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.codTextApproval1.setText(""+mValues.get(position).getApproval1());

            if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==1)
                holder.codTextStatus.setText("New");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==2)
                holder.codTextStatus.setText("Pending");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==3)
                holder.codTextStatus.setText("Progress");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==4)
                holder.codTextStatus.setText("Complete");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==5)
                holder.codTextStatus.setText("Closed");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==6)
                holder.codTextStatus.setText("Cancel");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==7)
                holder.codTextStatus.setText("Received");

            if (position%2==0)
                holder.codLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.codLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getCash_on_delivery_id(), mValues.get(position));
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
                List<CashOnDelivery> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CashOnDelivery) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CashOnDelivery item : values){
                        if (codSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getCash_on_delivery_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval_assign_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPurchase_order_status_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getCash_on_delivery_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval_assign_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (codSpinnerSearch.getSelectedItemPosition()==8){
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

            public final TextView codTextNomor;
            public final TextView codTextSupplier;
            public final TextView codTextfile;
            public final TextView codTextTglAwal;
            public final TextView codTextTermPembayaran;
            public final TextView codTextCheckedBy;
            public final TextView codTextPersetujuan;
            public final TextView codTextApproval1;
            public final TextView codTextStatus;

            public final LinearLayout codLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                codTextNomor = (TextView) view.findViewById(R.id.codTextNomor);
                codTextSupplier = (TextView) view.findViewById(R.id.codTextSupplier);
                codTextfile = (TextView) view.findViewById(R.id.codTextfile);
                codTextTglAwal = (TextView) view.findViewById(R.id.codTextTglAkhir);
                codTextTermPembayaran = (TextView) view.findViewById(R.id.codTextJobOrder);
                codTextCheckedBy = (TextView) view.findViewById(R.id.codTextCheckedBy);
                codTextPersetujuan = (TextView) view.findViewById(R.id.codTextPersetujuan);
                codTextApproval1 = (TextView) view.findViewById(R.id.codTextApproval1);
                codTextStatus = (TextView) view.findViewById(R.id.codTextStatus);

                codLayoutList = (LinearLayout) view.findViewById(R.id.codLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final CashOnDelivery cashOnDelivery) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailCashOnDeliveryActivity.class);
                        intent.putExtra("detail", cashOnDelivery);
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
                param.put("feature", "" + "cash-on-delivery");
                param.put("access", "" + "cash-on-delivery:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
