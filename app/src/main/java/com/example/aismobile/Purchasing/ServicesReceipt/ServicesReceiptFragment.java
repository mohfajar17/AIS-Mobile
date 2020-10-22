package com.example.aismobile.Purchasing.ServicesReceipt;

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
import com.example.aismobile.Data.Purchasing.ServicesReceipt;
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

public class ServicesReceiptFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView srTextPaging;
    public EditText srEditSearch;
    public ImageView srBtnSearch;
    public RecyclerView srRecycler;
    public FloatingActionButton srFabAdd;
    public Spinner srSpinnerSearch;
    public Spinner srSpinnerSort;
    public Spinner srSpinnerSortAD;
    public Button srBtnShowList;
    public ImageButton srBtnBefore;
    public ImageButton srBtnNext;
    public LinearLayout srLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public ServicesReceiptFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SRSpinnerSearch = {"Semua Data", "Services Receipt Number", "Tanggal Penerimaan", "Cash On Delivery",
            "Dibuat Oleh", "Catatan", "Diakui"};
    public String[] SRSpinnerSort = {"-- Sort By --", "Berdasarkan Services Receipt Number", "Berdasarkan Tanggal Penerimaan",
            "Berdasarkan Cash On Delivery", "Berdasarkan Dibuat Oleh", "Berdasarkan Catatan", "Berdasarkan Diakui"};
    public String[] SRADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<ServicesReceipt> servicesReceipts;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ServicesReceiptFragment() {
    }

    public static ServicesReceiptFragment newInstance() {
        ServicesReceiptFragment fragment = new ServicesReceiptFragment();
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

        servicesReceipts = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services_receipt, container, false);

        // Set the adapter
        srRecycler = (RecyclerView) view.findViewById(R.id.srRecycler);
        if (mColumnCount <= 1) {
            srRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            srRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        srFabAdd = (FloatingActionButton) view.findViewById(R.id.srFabAdd);
        srEditSearch = (EditText) view.findViewById(R.id.srEditSearch);
        srTextPaging = (TextView) view.findViewById(R.id.srTextPaging);
        srBtnSearch = (ImageView) view.findViewById(R.id.srBtnSearch);
        srSpinnerSearch = (Spinner) view.findViewById(R.id.srSpinnerSearch);
        srSpinnerSort = (Spinner) view.findViewById(R.id.srSpinnerSort);
        srSpinnerSortAD = (Spinner) view.findViewById(R.id.srSpinnerSortAD);
        srBtnShowList = (Button) view.findViewById(R.id.srBtnShowList);
        srBtnBefore = (ImageButton) view.findViewById(R.id.srBtnBefore);
        srBtnNext = (ImageButton) view.findViewById(R.id.srBtnNext);
        srLayoutPaging = (LinearLayout) view.findViewById(R.id.srLayoutPaging);

        srBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(srTextPaging.getText()));
                setSortHalf(srSpinnerSort.getSelectedItemPosition(), srSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(srTextPaging.getText()))+1;
                srTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        srBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(srTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(srTextPaging.getText()))-2);
                    setSortHalf(srSpinnerSort.getSelectedItemPosition(), srSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(srTextPaging.getText()))-1;
                    srTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        srBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("services_receipt_id DESC");
                    loadAll = true;
                    params = srLayoutPaging.getLayoutParams();
                    params.height = 0;
                    srLayoutPaging.setLayoutParams(params);
                    srBtnShowList.setText("Show Half");
                } else {
                    srTextPaging.setText("1");
                    counter = 0;
                    loadData("services_receipt_id DESC");
                    loadAll = false;
                    params = srLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    srLayoutPaging.setLayoutParams(params);
                    srBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SRSpinnerSearch);
        srSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SRSpinnerSort);
        srSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SRADSpinnerSort);
        srSpinnerSortAD.setAdapter(spinnerAdapter);

        srSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, srSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, srSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        srBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (srEditSearch.getText().toString().matches("")){
                    srSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(srEditSearch.getText()));
            }
        });

//        loadData("services_receipt_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("services_receipt_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("services_receipt_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("cash_on_delivery_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("cash_on_delivery_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("notes ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("notes DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("recognized DESC");
        else loadDataAll("services_receipt_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("services_receipt_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("services_receipt_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("cash_on_delivery_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("cash_on_delivery_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadData("notes ASC");
        else if (position == 5 && posAD == 1)
            loadData("notes DESC");
        else if (position == 6 && posAD == 0)
            loadData("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadData("recognized DESC");
        else loadData("services_receipt_id DESC");
    }

    private void setAdapterList(){
        adapter = new ServicesReceiptFragment.MyRecyclerViewAdapter(servicesReceipts, mListener);
        srRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        srRecycler.setAdapter(null);
        servicesReceipts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SERCICES_RECEIPT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            servicesReceipts.add(new ServicesReceipt(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (srEditSearch.getText().toString().matches("")){
                                srSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(srEditSearch.getText()));
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
        srRecycler.setAdapter(null);
        servicesReceipts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SERCICES_RECEIPT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            servicesReceipts.add(new ServicesReceipt(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (srEditSearch.getText().toString().matches("")){
                                srSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(srEditSearch.getText()));
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
        void onListFragmentInteraction(ServicesReceipt item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<ServicesReceipt> mValues;
        private final List<ServicesReceipt> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<ServicesReceipt> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_services_receipt_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.srTextNomor.setText(""+mValues.get(position).getServices_receipt_number());
            holder.srTextTglPenerimaan.setText(""+mValues.get(position).getReceipt_date());
            holder.srTextCOD.setText(""+mValues.get(position).getCash_on_delivery_id());
            holder.srTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.srTextNote.setText(""+mValues.get(position).getNotes());
            holder.srTextstatus.setText(""+mValues.get(position).getRecognized());

            if (position%2==0)
                holder.srLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.srLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getServices_receipt_id(), mValues.get(position));
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
                List<ServicesReceipt> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((ServicesReceipt) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ServicesReceipt item : values){
                        if (srSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getServices_receipt_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCash_on_delivery_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRecognized().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getServices_receipt_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getCash_on_delivery_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (srSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getRecognized().toLowerCase().contains(filterPattern)){
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

            public final TextView srTextNomor;
            public final TextView srTextTglPenerimaan;
            public final TextView srTextCOD;
            public final TextView srTextDibuat;
            public final TextView srTextNote;
            public final TextView srTextstatus;

            public final LinearLayout srLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                srTextNomor = (TextView) view.findViewById(R.id.srTextNomor);
                srTextTglPenerimaan = (TextView) view.findViewById(R.id.srTextTglPenerimaan);
                srTextCOD = (TextView) view.findViewById(R.id.srTextCOD);
                srTextDibuat = (TextView) view.findViewById(R.id.srTextDibuat);
                srTextNote = (TextView) view.findViewById(R.id.srTextNote);
                srTextstatus = (TextView) view.findViewById(R.id.srTextStatus);

                srLayoutList = (LinearLayout) view.findViewById(R.id.srLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final ServicesReceipt servicesReceipt) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailServicesReceiptActivity.class);
                        intent.putExtra("detail", servicesReceipt);
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
                param.put("feature", "" + "services-receipt");
                param.put("access", "" + "services-receipt:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
