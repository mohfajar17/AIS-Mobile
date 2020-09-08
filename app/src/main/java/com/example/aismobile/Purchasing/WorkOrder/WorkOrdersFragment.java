package com.example.aismobile.Purchasing.WorkOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseService;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkOrdersFragment extends Fragment {

    public TextView woTextPaging;
    public EditText woEditSearch;
    public ImageView woBtnSearch;
    public RecyclerView woRecycler;
    public FloatingActionButton woFabAdd;
    public Spinner woSpinnerSearch;
    public Spinner woSpinnerSort;
    public Spinner woSpinnerSortAD;
    public Button woBtnShowList;
    public ImageButton woBtnBefore;
    public ImageButton woBtnNext;
    public LinearLayout woLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public WorkOrdersFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] WOSpinnerSearch = {"Semua Data", "Work Order Number", "Supplier", "Tanggal Akhir",
            "Termin Pembayaran", "Checked By", "Persetujuan", "Approval 1", "Status"};
    public String[] WOSpinnerSort = {"-- Sort By --", "Berdasarkan Work Order Number", "Berdasarkan Supplier",
            "Berdasarkan Tanggal Akhir", "Berdasarkan Termin Pembayaran", "Berdasarkan Checked By",
            "Berdasarkan Persetujuan", "Berdasarkan Approval 1", "Berdasarkan Status"};
    public String[] WOADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<PurchaseService> purchaseServices;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public WorkOrdersFragment() {
    }

    public static WorkOrdersFragment newInstance() {
        WorkOrdersFragment fragment = new WorkOrdersFragment();
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

        purchaseServices = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_orders, container, false);// Set the adapter

        woRecycler = (RecyclerView) view.findViewById(R.id.woRecycler);
        if (mColumnCount <= 1) {
            woRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            woRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        woFabAdd = (FloatingActionButton) view.findViewById(R.id.woFabAdd);
        woEditSearch = (EditText) view.findViewById(R.id.woEditSearch);
        woTextPaging = (TextView) view.findViewById(R.id.woTextPaging);
        woBtnSearch = (ImageView) view.findViewById(R.id.woBtnSearch);
        woSpinnerSearch = (Spinner) view.findViewById(R.id.woSpinnerSearch);
        woSpinnerSort = (Spinner) view.findViewById(R.id.woSpinnerSort);
        woSpinnerSortAD = (Spinner) view.findViewById(R.id.woSpinnerSortAD);
        woBtnShowList = (Button) view.findViewById(R.id.woBtnShowList);
        woBtnBefore = (ImageButton) view.findViewById(R.id.woBtnBefore);
        woBtnNext = (ImageButton) view.findViewById(R.id.woBtnNext);
        woLayoutPaging = (LinearLayout) view.findViewById(R.id.woLayoutPaging);

        woBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(woTextPaging.getText()));
                setSortHalf(woSpinnerSort.getSelectedItemPosition(), woSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(woTextPaging.getText()))+1;
                woTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        woBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(woTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(woTextPaging.getText()))-2);
                    setSortHalf(woSpinnerSort.getSelectedItemPosition(), woSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(woTextPaging.getText()))-1;
                    woTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        woBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("purchase_order_id DESC");
                    loadAll = true;
                    params = woLayoutPaging.getLayoutParams();
                    params.height = 0;
                    woLayoutPaging.setLayoutParams(params);
                    woBtnShowList.setText("Show Half");
                } else {
                    woTextPaging.setText("1");
                    counter = 0;
                    loadData("purchase_order_id DESC");
                    loadAll = false;
                    params = woLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    woLayoutPaging.setLayoutParams(params);
                    woBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WOSpinnerSearch);
        woSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WOSpinnerSort);
        woSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WOADSpinnerSort);
        woSpinnerSortAD.setAdapter(spinnerAdapter);

        woSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, woSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, woSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        woBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (woEditSearch.getText().toString().matches("")){
                    woSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(woEditSearch.getText()));
            }
        });

        loadData("purchase_order_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("purchase_service_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("purchase_service_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("payment_term_id ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("payment_term_id DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("approval_assign_id ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("approval_assign_id DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("po_approval1 ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("po_approval1 DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("purchase_order_status_id ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("purchase_order_status_id DESC");
        else loadDataAll("purchase_service_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("purchase_service_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("purchase_service_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("payment_term_id ASC");
        else if (position == 4 && posAD == 1)
            loadData("payment_term_id DESC");
        else if (position == 5 && posAD == 0)
            loadData("checked_by ASC");
        else if (position == 5 && posAD == 1)
            loadData("checked_by DESC");
        else if (position == 6 && posAD == 0)
            loadData("approval_assign_id ASC");
        else if (position == 6 && posAD == 1)
            loadData("approval_assign_id DESC");
        else if (position == 7 && posAD == 0)
            loadData("po_approval1 ASC");
        else if (position == 7 && posAD == 1)
            loadData("po_approval1 DESC");
        else if (position == 8 && posAD == 0)
            loadData("purchase_order_status_id ASC");
        else if (position == 8 && posAD == 1)
            loadData("purchase_order_status_id DESC");
        else loadData("purchase_service_id DESC");
    }

    private void setAdapterList(){
        adapter = new WorkOrdersFragment.MyRecyclerViewAdapter(purchaseServices, mListener);
        woRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        woRecycler.setAdapter(null);
        purchaseServices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_SERVICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseServices.add(new PurchaseService(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (woEditSearch.getText().toString().matches("")){
                                woSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(woEditSearch.getText()));
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
        woRecycler.setAdapter(null);
        purchaseServices.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_SERVICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseServices.add(new PurchaseService(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (woEditSearch.getText().toString().matches("")){
                                woSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(woEditSearch.getText()));
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
        void onListFragmentInteraction(PurchaseService item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<PurchaseService> mValues;
        private final List<PurchaseService> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<PurchaseService> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_orders_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.woTextNomor.setText(""+mValues.get(position).getPurchase_service_number());
            holder.woTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.woTextfile.setText("");
            holder.woTextTglAwal.setText(""+mValues.get(position).getEnd_date());
            holder.woTextTermPembayaran.setText(""+mValues.get(position).getPayment_term_id());
            holder.woTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.woTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.woTextApproval1.setText(""+mValues.get(position).getPo_approval1());
            holder.woTextStatus.setText(""+mValues.get(position).getPurchase_order_status_id());

            if (position%2==0)
                holder.woLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.woLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailWorkOrdderActivity.class);
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
                List<PurchaseService> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((PurchaseService) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (PurchaseService item : values){
                        if (woSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getPurchase_service_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPayment_term_id().toLowerCase().contains(filterPattern)){
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
                        } else if (woSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getPurchase_service_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPayment_term_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getChecked_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval_assign_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getPo_approval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (woSpinnerSearch.getSelectedItemPosition()==8){
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

            public final TextView woTextNomor;
            public final TextView woTextSupplier;
            public final TextView woTextfile;
            public final TextView woTextTglAwal;
            public final TextView woTextTermPembayaran;
            public final TextView woTextCheckedBy;
            public final TextView woTextPersetujuan;
            public final TextView woTextApproval1;
            public final TextView woTextStatus;

            public final LinearLayout woLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                woTextNomor = (TextView) view.findViewById(R.id.woTextNomor);
                woTextSupplier = (TextView) view.findViewById(R.id.woTextSupplier);
                woTextfile = (TextView) view.findViewById(R.id.woTextfile);
                woTextTglAwal = (TextView) view.findViewById(R.id.woTextTglAkhir);
                woTextTermPembayaran = (TextView) view.findViewById(R.id.woTextTermPembayaran);
                woTextCheckedBy = (TextView) view.findViewById(R.id.woTextCheckedBy);
                woTextPersetujuan = (TextView) view.findViewById(R.id.woTextPersetujuan);
                woTextApproval1 = (TextView) view.findViewById(R.id.woTextApproval1);
                woTextStatus = (TextView) view.findViewById(R.id.woTextStatus);

                woLayoutList = (LinearLayout) view.findViewById(R.id.woLayoutList);
            }
        }
    }
}
