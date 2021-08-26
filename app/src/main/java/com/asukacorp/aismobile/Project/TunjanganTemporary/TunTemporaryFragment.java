package com.asukacorp.aismobile.Project.TunjanganTemporary;

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
import com.asukacorp.aismobile.Data.Project.TunjanganTemporary;
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

public class TunTemporaryFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView pttTextPaging;
    public EditText pttEditSearch;
    public ImageView pttBtnSearch;
    public RecyclerView pttRecycler;
    public FloatingActionButton pttFabAdd;
    public Spinner pttSpinnerSearch;
    public Spinner pttSpinnerSort;
    public Spinner pttSpinnerSortAD;
    public Button pttBtnShowList;
    public ImageButton pttBtnBefore;
    public ImageButton pttBtnNext;
    public LinearLayout pttLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public TunTemporaryFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] TTSpinnerSearch = {"Semua Data", "Allowance Number", "Employee Name", "Additional Allowance Type",
            "Kabupaten", "Job Order", "Tanggal Awal", "Tanggal Akhir", "Verified By", "Approval 1", "Approval 2", "Paid"};
    public String[] TTSpinnerSort = {"-- Sort By --", "Berdasarkan Allowance Number", "Berdasarkan Employee Name",
            "Berdasarkan Additional Allowance Type", "Berdasarkan Kabupaten", "Berdasarkan Job Order", "Berdasarkan Tanggal Awal",
            "Berdasarkan Tanggal Akhir", "Berdasarkan Verified By", "Berdasarkan Approval 1", "Berdasarkan Approval 2",
            "Berdasarkan Paid"};
    public String[] TTADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<TunjanganTemporary> tunjanganTemporaries;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public TunTemporaryFragment() {
        // Required empty public constructor
    }

    public static TunTemporaryFragment newInstance() {
        TunTemporaryFragment fragment = new TunTemporaryFragment();
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

        tunjanganTemporaries = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tun_temporary, container, false);

        // Set the adapter
        pttRecycler = (RecyclerView) view.findViewById(R.id.pttRecycler);
        if (mColumnCount <= 1) {
            pttRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pttRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pttFabAdd = (FloatingActionButton) view.findViewById(R.id.pttFabAdd);
        pttEditSearch = (EditText) view.findViewById(R.id.pttEditSearch);
        pttTextPaging = (TextView) view.findViewById(R.id.pttTextPaging);
        pttBtnSearch = (ImageView) view.findViewById(R.id.pttBtnSearch);
        pttSpinnerSearch = (Spinner) view.findViewById(R.id.pttSpinnerSearch);
        pttSpinnerSort = (Spinner) view.findViewById(R.id.pttSpinnerSort);
        pttSpinnerSortAD = (Spinner) view.findViewById(R.id.pttSpinnerSortAD);
        pttBtnShowList = (Button) view.findViewById(R.id.pttBtnShowList);
        pttBtnBefore = (ImageButton) view.findViewById(R.id.pttBtnBefore);
        pttBtnNext = (ImageButton) view.findViewById(R.id.pttBtnNext);
        pttLayoutPaging = (LinearLayout) view.findViewById(R.id.pttLayoutPaging);

        pttBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pttTextPaging.getText()));
                setSortHalf(pttSpinnerSort.getSelectedItemPosition(), pttSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pttTextPaging.getText()))+1;
                pttTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pttBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pttTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pttTextPaging.getText()))-2);
                    setSortHalf(pttSpinnerSort.getSelectedItemPosition(), pttSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pttTextPaging.getText()))-1;
                    pttTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pttBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("employee_allowance_id DESC");
                    loadAll = true;
                    params = pttLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pttLayoutPaging.setLayoutParams(params);
                    pttBtnShowList.setText("Show Half");
                } else {
                    pttTextPaging.setText("1");
                    counter = 0;
                    loadData("employee_allowance_id DESC");
                    loadAll = false;
                    params = pttLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pttLayoutPaging.setLayoutParams(params);
                    pttBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TTSpinnerSearch);
        pttSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TTSpinnerSort);
        pttSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TTADSpinnerSort);
        pttSpinnerSortAD.setAdapter(spinnerAdapter);

        pttSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pttSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pttSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pttBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pttEditSearch.getText().toString().matches("")){
                    pttSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pttEditSearch.getText()));
            }
        });

//        loadData("employee_allowance_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("employee_allowance_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("employee_allowance_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("employee_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("employee_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("additional_allowance_type ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("additional_allowance_type DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("kab_id ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("kab_id DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("begin_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("begin_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("verified_by ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("verified_by DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("approval1_by ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("approval1_by DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("approval2_by ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("approval2_by DESC");
        else if (position == 11 && posAD == 0)
            loadDataAll("paid ASC");
        else if (position == 11 && posAD == 1)
            loadDataAll("paid DESC");
        else loadDataAll("employee_allowance_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("employee_allowance_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("employee_allowance_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("employee_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("employee_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("additional_allowance_type ASC");
        else if (position == 3 && posAD == 1)
            loadData("additional_allowance_type DESC");
        else if (position == 4 && posAD == 0)
            loadData("kab_id ASC");
        else if (position == 4 && posAD == 1)
            loadData("kab_id DESC");
        else if (position == 5 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 5 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 6 && posAD == 0)
            loadData("begin_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("begin_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 7 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 8 && posAD == 0)
            loadData("verified_by ASC");
        else if (position == 8 && posAD == 1)
            loadData("verified_by DESC");
        else if (position == 9 && posAD == 0)
            loadData("approval1_by ASC");
        else if (position == 9 && posAD == 1)
            loadData("approval1_by DESC");
        else if (position == 10 && posAD == 0)
            loadData("approval2_by ASC");
        else if (position == 10 && posAD == 1)
            loadData("approval2_by DESC");
        else if (position == 11 && posAD == 0)
            loadData("paid ASC");
        else if (position == 11 && posAD == 1)
            loadData("paid DESC");
        else loadData("employee_allowance_id DESC");
    }

    private void setAdapterList(){
        adapter = new TunTemporaryFragment.MyRecyclerViewAdapter(tunjanganTemporaries, mListener);
        pttRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        pttRecycler.setAdapter(null);
        tunjanganTemporaries.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_TUNJANGAN_TEMPORARY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjanganTemporaries.add(new TunjanganTemporary(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pttEditSearch.getText().toString().matches("")){
                                pttSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pttEditSearch.getText()));
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
        pttRecycler.setAdapter(null);
        tunjanganTemporaries.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_TUNJANGAN_TEMPORARY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjanganTemporaries.add(new TunjanganTemporary(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (pttEditSearch.getText().toString().matches("")){
                                pttSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pttEditSearch.getText()));
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
        void onListFragmentInteraction(TunjanganTemporary item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<TunjanganTemporary> mValues;
        private final List<TunjanganTemporary> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<TunjanganTemporary> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_tun_temporary_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pttTextNomor.setText(""+mValues.get(position).getEmployee_allowance_number());
            holder.pttTextKaryawan.setText(""+mValues.get(position).getEmployee_id());
            holder.pttTextType.setText(""+mValues.get(position).getAdditional_allowance_type());
            holder.pttTextKabupaten.setText(""+mValues.get(position).getKab_id());
            holder.pttTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.pttTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.pttTextTglAkhir.setText(""+mValues.get(position).getEnd_date());
            holder.pttTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());
            holder.pttTextApproval1.setText(""+mValues.get(position).getApproval1_by());
            holder.pttTextApproval2.setText(""+mValues.get(position).getApproval2_by());
            holder.pttTextPaid.setText(""+mValues.get(position).getPaid());

            if (position%2==0)
                holder.pttLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pttLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getEmployee_allowance_id(), mValues.get(position));
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
                List<TunjanganTemporary> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((TunjanganTemporary) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (TunjanganTemporary item : values){
                        if (pttSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getEmployee_allowance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAdditional_allowance_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getKab_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getVerified_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPaid().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_allowance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getAdditional_allowance_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getKab_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getVerified_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getApproval1_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getApproval2_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pttSpinnerSearch.getSelectedItemPosition()==11){
                            if (item.getPaid().toLowerCase().contains(filterPattern)){
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

            public final TextView pttTextNomor;
            public final TextView pttTextKaryawan;
            public final TextView pttTextType;
            public final TextView pttTextKabupaten;
            public final TextView pttTextJobOrder;
            public final TextView pttTextTglAwal;
            public final TextView pttTextTglAkhir;
            public final TextView pttTextVerifiedBy;
            public final TextView pttTextApproval1;
            public final TextView pttTextApproval2;
            public final TextView pttTextPaid;

            public final LinearLayout pttLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pttTextNomor = (TextView) view.findViewById(R.id.pttTextNomor);
                pttTextKaryawan = (TextView) view.findViewById(R.id.pttTextKaryawan);
                pttTextType = (TextView) view.findViewById(R.id.pttTextType);
                pttTextKabupaten = (TextView) view.findViewById(R.id.pttTextKabupaten);
                pttTextJobOrder = (TextView) view.findViewById(R.id.pttTextJobOrder);
                pttTextTglAwal = (TextView) view.findViewById(R.id.pttTextTglAwal);
                pttTextTglAkhir = (TextView) view.findViewById(R.id.pttTextTglAkhir);
                pttTextVerifiedBy = (TextView) view.findViewById(R.id.pttTextVerifiedBy);
                pttTextApproval1 = (TextView) view.findViewById(R.id.pttTextApproval1);
                pttTextApproval2 = (TextView) view.findViewById(R.id.pttTextApproval2);
                pttTextPaid = (TextView) view.findViewById(R.id.pttTextPaid);

                pttLayoutList = (LinearLayout) view.findViewById(R.id.pttLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final TunjanganTemporary tunjanganTemporary) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailTunTemporaryActivity.class);
                        intent.putExtra("detail", tunjanganTemporary);
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
                param.put("feature", "" + "employee-allowance-temp");
                param.put("access", "" + "employee-allowance-temp:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
