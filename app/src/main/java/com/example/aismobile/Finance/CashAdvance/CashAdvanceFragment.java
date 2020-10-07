package com.example.aismobile.Finance.CashAdvance;

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
import com.example.aismobile.Data.FinanceAccounting.CashAdvance;
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

public class CashAdvanceFragment extends Fragment {

    public TextView caTextPaging;
    public EditText caEditSearch;
    public ImageView caBtnSearch;
    public RecyclerView caRecycler;
    public FloatingActionButton caFabAdd;
    public Spinner caSpinnerSearch;
    public Spinner caSpinnerSort;
    public Spinner caSpinnerSortAD;
    public Button caBtnShowList;
    public ImageButton caBtnBefore;
    public ImageButton caBtnNext;
    public LinearLayout caLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public CashAdvanceFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CASpinnerSearch = {"Semua Data", "Nomor Cash Advance", "Tanggal Cash Advance",
            "Advanced For", "Received By", "Nilai", "Status"};
    public String[] CASpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Cash Advance", "Berdasarkan Tanggal Cash Advance",
            "Berdasarkan Advanced For", "Berdasarkan Received By", "Berdasarkan Nilai", "Berdasarkan Status"};
    public String[] CAADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CashAdvance> cashAdvances;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CashAdvanceFragment() {
        // Required empty public constructor
    }

    public static CashAdvanceFragment newInstance() {
        CashAdvanceFragment fragment = new CashAdvanceFragment();
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

        cashAdvances = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_advance, container, false);

        // Set the adapter
        caRecycler = (RecyclerView) view.findViewById(R.id.caRecycler);
        if (mColumnCount <= 1) {
            caRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            caRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        caFabAdd = (FloatingActionButton) view.findViewById(R.id.caFabAdd);
        caEditSearch = (EditText) view.findViewById(R.id.caEditSearch);
        caTextPaging = (TextView) view.findViewById(R.id.caTextPaging);
        caBtnSearch = (ImageView) view.findViewById(R.id.caBtnSearch);
        caSpinnerSearch = (Spinner) view.findViewById(R.id.caSpinnerSearch);
        caSpinnerSort = (Spinner) view.findViewById(R.id.caSpinnerSort);
        caSpinnerSortAD = (Spinner) view.findViewById(R.id.caSpinnerSortAD);
        caBtnShowList = (Button) view.findViewById(R.id.caBtnShowList);
        caBtnBefore = (ImageButton) view.findViewById(R.id.caBtnBefore);
        caBtnNext = (ImageButton) view.findViewById(R.id.caBtnNext);
        caLayoutPaging = (LinearLayout) view.findViewById(R.id.caLayoutPaging);

        caBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(caTextPaging.getText()));
                setSortHalf(caSpinnerSort.getSelectedItemPosition(), caSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(caTextPaging.getText()))+1;
                caTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        caBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(caTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(caTextPaging.getText()))-2);
                    setSortHalf(caSpinnerSort.getSelectedItemPosition(), caSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(caTextPaging.getText()))-1;
                    caTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        caBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("advanced_id DESC");
                    loadAll = true;
                    params = caLayoutPaging.getLayoutParams();
                    params.height = 0;
                    caLayoutPaging.setLayoutParams(params);
                    caBtnShowList.setText("Show Half");
                } else {
                    caTextPaging.setText("1");
                    counter = 0;
                    loadData("advanced_id DESC");
                    loadAll = false;
                    params = caLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    caLayoutPaging.setLayoutParams(params);
                    caBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CASpinnerSearch);
        caSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CASpinnerSort);
        caSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CAADSpinnerSort);
        caSpinnerSortAD.setAdapter(spinnerAdapter);

        caSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, caSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, caSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        caBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caEditSearch.getText().toString().matches("")){
                    caSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(caEditSearch.getText()));
            }
        });

//        loadData("advanced_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("advanced_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("advanced_id ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("advanced_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("advanced_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("advanced_for ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("advanced_for DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("received_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("received_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("amount ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("amount DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("status ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("status DESC");
        else loadDataAll("advanced_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("advanced_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("advanced_id ASC");
        else if (position == 2 && posAD == 0)
            loadData("advanced_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("advanced_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("advanced_for ASC");
        else if (position == 3 && posAD == 1)
            loadData("advanced_for DESC");
        else if (position == 4 && posAD == 0)
            loadData("received_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("received_by DESC");
        else if (position == 5 && posAD == 0)
            loadData("amount ASC");
        else if (position == 5 && posAD == 1)
            loadData("amount DESC");
        else if (position == 6 && posAD == 0)
            loadData("status ASC");
        else if (position == 6 && posAD == 1)
            loadData("status DESC");
        else loadData("advanced_id DESC");
    }

    private void setAdapterList(){
        adapter = new CashAdvanceFragment.MyRecyclerViewAdapter(cashAdvances, mListener);
        caRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        caRecycler.setAdapter(null);
        cashAdvances.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_ADVANCE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashAdvances.add(new CashAdvance(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (caEditSearch.getText().toString().matches("")){
                                caSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(caEditSearch.getText()));
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
        caRecycler.setAdapter(null);
        cashAdvances.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CASH_ADVANCE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashAdvances.add(new CashAdvance(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (caEditSearch.getText().toString().matches("")){
                                caSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(caEditSearch.getText()));
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
        void onListFragmentInteraction(CashAdvance item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CashAdvance> mValues;
        private final List<CashAdvance> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CashAdvance> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_advance_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.caTextNumber.setText(""+mValues.get(position).getAdvanced_number());
            holder.caTextDate.setText(""+mValues.get(position).getAdvanced_date());
            holder.caTextAdvancedFor.setText(""+mValues.get(position).getAdvanced_for());
            holder.caTextReceived.setText(""+mValues.get(position).getReceived_by());
            holder.caTextStatus.setText(""+mValues.get(position).getStatus());

            double toDouble = Double.valueOf(mValues.get(position).getAmount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.caTextNilai.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.caLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.caLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailCashAdvanceActivity.class);
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
                List<CashAdvance> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CashAdvance) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CashAdvance item : values){
                        if (caSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getAdvanced_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAdvanced_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAdvanced_for().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReceived_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAmount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStatus().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getAdvanced_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getAdvanced_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getAdvanced_for().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getReceived_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getAmount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (caSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getStatus().toLowerCase().contains(filterPattern)){
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

            public final TextView caTextNumber;
            public final TextView caTextDate;
            public final TextView caTextAdvancedFor;
            public final TextView caTextReceived;
            public final TextView caTextNilai;
            public final TextView caTextStatus;

            public final LinearLayout caLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                caTextNumber = (TextView) view.findViewById(R.id.caTextNumber);
                caTextDate = (TextView) view.findViewById(R.id.caTextDate);
                caTextAdvancedFor = (TextView) view.findViewById(R.id.caTextAdvancedFor);
                caTextReceived = (TextView) view.findViewById(R.id.caTextReceived);
                caTextNilai = (TextView) view.findViewById(R.id.caTextNilai);
                caTextStatus = (TextView) view.findViewById(R.id.caTextStatus);

                caLayoutList = (LinearLayout) view.findViewById(R.id.caLayoutList);
            }
        }
    }
}
