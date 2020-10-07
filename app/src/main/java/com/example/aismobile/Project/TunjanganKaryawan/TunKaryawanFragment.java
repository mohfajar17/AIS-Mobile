package com.example.aismobile.Project.TunjanganKaryawan;

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
import com.example.aismobile.Data.Project.TunjanganKaryawan;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TunKaryawanFragment extends Fragment {

    public TextView ptkTextPaging;
    public EditText ptkEditSearch;
    public ImageView ptkBtnSearch;
    public RecyclerView ptkRecycler;
    public FloatingActionButton ptkFabAdd;
    public Spinner ptkSpinnerSearch;
    public Spinner ptkSpinnerSort;
    public Spinner ptkSpinnerSortAD;
    public Button ptkBtnShowList;
    public ImageButton ptkBtnBefore;
    public ImageButton ptkBtnNext;
    public LinearLayout ptkLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public TunKaryawanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] TKSpinnerSearch = {"Semua Data", "Allowance Number", "Karyawan", "Additional Allowance Type",
            "Kabupaten", "Job Order", "Tanggal Awal", "Tanggal Akhir", "Verified By", "Approval 1", "Approval 2", "Paid"};
    public String[] TKSpinnerSort = {"-- Sort By --", "Berdasarkan Allowance Number", "Berdasarkan Karyawan",
            "Berdasarkan Additional Allowance Type", "Berdasarkan Kabupaten", "Berdasarkan Job Order", "Berdasarkan Tanggal Awal",
            "Berdasarkan Tanggal Akhir", "Berdasarkan Verified By", "Berdasarkan Approval 1", "Berdasarkan Approval 2",
            "Berdasarkan Paid"};
    public String[] TKADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<TunjanganKaryawan> tunjanganKaryawans;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public TunKaryawanFragment() {
        // Required empty public constructor
    }

    public static TunKaryawanFragment newInstance() {
        TunKaryawanFragment fragment = new TunKaryawanFragment();
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

        tunjanganKaryawans = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tun_karyawan, container, false);

        // Set the adapter
        ptkRecycler = (RecyclerView) view.findViewById(R.id.ptkRecycler);
        if (mColumnCount <= 1) {
            ptkRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            ptkRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        ptkFabAdd = (FloatingActionButton) view.findViewById(R.id.ptkFabAdd);
        ptkEditSearch = (EditText) view.findViewById(R.id.ptkEditSearch);
        ptkTextPaging = (TextView) view.findViewById(R.id.ptkTextPaging);
        ptkBtnSearch = (ImageView) view.findViewById(R.id.ptkBtnSearch);
        ptkSpinnerSearch = (Spinner) view.findViewById(R.id.ptkSpinnerSearch);
        ptkSpinnerSort = (Spinner) view.findViewById(R.id.ptkSpinnerSort);
        ptkSpinnerSortAD = (Spinner) view.findViewById(R.id.ptkSpinnerSortAD);
        ptkBtnShowList = (Button) view.findViewById(R.id.ptkBtnShowList);
        ptkBtnBefore = (ImageButton) view.findViewById(R.id.ptkBtnBefore);
        ptkBtnNext = (ImageButton) view.findViewById(R.id.ptkBtnNext);
        ptkLayoutPaging = (LinearLayout) view.findViewById(R.id.ptkLayoutPaging);

        ptkBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(ptkTextPaging.getText()));
                setSortHalf(ptkSpinnerSort.getSelectedItemPosition(), ptkSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(ptkTextPaging.getText()))+1;
                ptkTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        ptkBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(ptkTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(ptkTextPaging.getText()))-2);
                    setSortHalf(ptkSpinnerSort.getSelectedItemPosition(), ptkSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(ptkTextPaging.getText()))-1;
                    ptkTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        ptkBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("employee_allowance_id DESC");
                    loadAll = true;
                    params = ptkLayoutPaging.getLayoutParams();
                    params.height = 0;
                    ptkLayoutPaging.setLayoutParams(params);
                    ptkBtnShowList.setText("Show Half");
                } else {
                    ptkTextPaging.setText("1");
                    counter = 0;
                    loadData("employee_allowance_id DESC");
                    loadAll = false;
                    params = ptkLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    ptkLayoutPaging.setLayoutParams(params);
                    ptkBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TKSpinnerSearch);
        ptkSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TKSpinnerSort);
        ptkSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TKADSpinnerSort);
        ptkSpinnerSortAD.setAdapter(spinnerAdapter);

        ptkSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, ptkSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, ptkSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ptkBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ptkEditSearch.getText().toString().matches("")){
                    ptkSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(ptkEditSearch.getText()));
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
        adapter = new TunKaryawanFragment.MyRecyclerViewAdapter(tunjanganKaryawans, mListener);
        ptkRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        ptkRecycler.setAdapter(null);
        tunjanganKaryawans.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_TUNJANGAN_KARYAWAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjanganKaryawans.add(new TunjanganKaryawan(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (ptkEditSearch.getText().toString().matches("")){
                                ptkSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(ptkEditSearch.getText()));
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
        ptkRecycler.setAdapter(null);
        tunjanganKaryawans.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_TUNJANGAN_KARYAWAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjanganKaryawans.add(new TunjanganKaryawan(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (ptkEditSearch.getText().toString().matches("")){
                                ptkSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(ptkEditSearch.getText()));
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
        void onListFragmentInteraction(TunjanganKaryawan item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<TunjanganKaryawan> mValues;
        private final List<TunjanganKaryawan> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<TunjanganKaryawan> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_tun_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ptkTextNomor.setText(""+mValues.get(position).getEmployee_allowance_number());
            holder.ptkTextKaryawan.setText(""+mValues.get(position).getEmployee_id());
            holder.ptkTextType.setText(""+mValues.get(position).getAdditional_allowance_type());
            holder.ptkTextKabupaten.setText(""+mValues.get(position).getKab_id());
            holder.ptkTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.ptkTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.ptkTextTglAkhir.setText(""+mValues.get(position).getEnd_date());
            holder.ptkTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());
            holder.ptkTextApproval1.setText(""+mValues.get(position).getApproval1_by());
            holder.ptkTextApproval2.setText(""+mValues.get(position).getApproval2_by());
            holder.ptkTextPaid.setText(""+mValues.get(position).getPaid());

            if (position%2==0)
                holder.ptkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ptkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailTunjanganKaryawanActivity.class);
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
                List<TunjanganKaryawan> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((TunjanganKaryawan) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (TunjanganKaryawan item : values){
                        if (ptkSpinnerSearch.getSelectedItemPosition()==0){
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
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_allowance_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getAdditional_allowance_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getKab_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getVerified_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getApproval1_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getApproval2_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ptkSpinnerSearch.getSelectedItemPosition()==11){
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

            public final TextView ptkTextNomor;
            public final TextView ptkTextKaryawan;
            public final TextView ptkTextType;
            public final TextView ptkTextKabupaten;
            public final TextView ptkTextJobOrder;
            public final TextView ptkTextTglAwal;
            public final TextView ptkTextTglAkhir;
            public final TextView ptkTextVerifiedBy;
            public final TextView ptkTextApproval1;
            public final TextView ptkTextApproval2;
            public final TextView ptkTextPaid;

            public final LinearLayout ptkLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ptkTextNomor = (TextView) view.findViewById(R.id.ptkTextNomor);
                ptkTextKaryawan = (TextView) view.findViewById(R.id.ptkTextKaryawan);
                ptkTextType = (TextView) view.findViewById(R.id.ptkTextType);
                ptkTextKabupaten = (TextView) view.findViewById(R.id.ptkTextKabupaten);
                ptkTextJobOrder = (TextView) view.findViewById(R.id.ptkTextJobOrder);
                ptkTextTglAwal = (TextView) view.findViewById(R.id.ptkTextTglAwal);
                ptkTextTglAkhir = (TextView) view.findViewById(R.id.ptkTextTglAkhir);
                ptkTextVerifiedBy = (TextView) view.findViewById(R.id.ptkTextVerifiedBy);
                ptkTextApproval1 = (TextView) view.findViewById(R.id.ptkTextApproval1);
                ptkTextApproval2 = (TextView) view.findViewById(R.id.ptkTextApproval2);
                ptkTextPaid = (TextView) view.findViewById(R.id.ptkTextPaid);

                ptkLayoutList = (LinearLayout) view.findViewById(R.id.ptkLayoutList);
            }
        }
    }
}
