package com.example.aismobile.Purchasing.KontrakPerjanjian;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.aismobile.Data.Purchasing.ContractAgreement;
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

public class KontrakPerjanjianFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView kpTextPaging;
    public EditText kpEditSearch;
    public ImageView kpBtnSearch;
    public RecyclerView kpRecycler;
    public FloatingActionButton kpFabAdd;
    public Spinner kpSpinnerSearch;
    public Spinner kpSpinnerSort;
    public Spinner kpSpinnerSortAD;
    public Button kpBtnShowList;
    public ImageButton kpBtnBefore;
    public ImageButton kpBtnNext;
    public LinearLayout kpLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public KontrakPerjanjianFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] KPSpinnerSearch = {"Semua Data", "Nomer Kontrak", "Supplier", "Kontak",
            "Tanggal Kontrak", "Tanggal Awal", "Tanggal Akhir", "Status"};
    public String[] KPSpinnerSort = {"-- Sort By --", "Berdasarkan Nomer Kontrak", "Berdasarkan Supplier",
            "Berdasarkan Kontak", "Berdasarkan Tanggal Kontrak", "Berdasarkan Tanggal Awal", "Berdasarkan Tanggal Akhir",
            "Berdasarkan Status"};
    public String[] KPADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<ContractAgreement> contractAgreements;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public KontrakPerjanjianFragment() {
    }

    public static KontrakPerjanjianFragment newInstance() {
        KontrakPerjanjianFragment fragment = new KontrakPerjanjianFragment();
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

        contractAgreements = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kontrak_perjanjian, container, false);

        // Set the adapter
        kpRecycler = (RecyclerView) view.findViewById(R.id.kpRecycler);
        if (mColumnCount <= 1) {
            kpRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            kpRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        kpFabAdd = (FloatingActionButton) view.findViewById(R.id.kpFabAdd);
        kpEditSearch = (EditText) view.findViewById(R.id.kpEditSearch);
        kpTextPaging = (TextView) view.findViewById(R.id.kpTextPaging);
        kpBtnSearch = (ImageView) view.findViewById(R.id.kpBtnSearch);
        kpSpinnerSearch = (Spinner) view.findViewById(R.id.kpSpinnerSearch);
        kpSpinnerSort = (Spinner) view.findViewById(R.id.kpSpinnerSort);
        kpSpinnerSortAD = (Spinner) view.findViewById(R.id.kpSpinnerSortAD);
        kpBtnShowList = (Button) view.findViewById(R.id.kpBtnShowList);
        kpBtnBefore = (ImageButton) view.findViewById(R.id.kpBtnBefore);
        kpBtnNext = (ImageButton) view.findViewById(R.id.kpBtnNext);
        kpLayoutPaging = (LinearLayout) view.findViewById(R.id.kpLayoutPaging);

        kpBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(kpTextPaging.getText()));
                setSortHalf(kpSpinnerSort.getSelectedItemPosition(), kpSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(kpTextPaging.getText()))+1;
                kpTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        kpBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(kpTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(kpTextPaging.getText()))-2);
                    setSortHalf(kpSpinnerSort.getSelectedItemPosition(), kpSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(kpTextPaging.getText()))-1;
                    kpTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        kpBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("contract_agreement_id DESC");
                    loadAll = true;
                    params = kpLayoutPaging.getLayoutParams();
                    params.height = 0;
                    kpLayoutPaging.setLayoutParams(params);
                    kpBtnShowList.setText("Show Half");
                } else {
                    kpTextPaging.setText("1");
                    counter = 0;
                    loadData("contract_agreement_id DESC");
                    loadAll = false;
                    params = kpLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    kpLayoutPaging.setLayoutParams(params);
                    kpBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, KPSpinnerSearch);
        kpSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, KPSpinnerSort);
        kpSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, KPADSpinnerSort);
        kpSpinnerSortAD.setAdapter(spinnerAdapter);

        kpSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, kpSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, kpSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kpBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kpEditSearch.getText().toString().matches("")){
                    kpSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(kpEditSearch.getText()));
            }
        });

//        loadData("contract_agreement_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("contract_agreement_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("contract_agreement_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("contact_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("contact_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("agreement_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("agreement_date DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("begin_date ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("begin_date DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("agreement_status ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("agreement_status DESC");
        else loadDataAll("contract_agreement_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("contract_agreement_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("contract_agreement_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_id ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_id DESC");
        else if (position == 3 && posAD == 0)
            loadData("contact_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("contact_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("agreement_date ASC");
        else if (position == 4 && posAD == 1)
            loadData("agreement_date DESC");
        else if (position == 5 && posAD == 0)
            loadData("begin_date ASC");
        else if (position == 5 && posAD == 1)
            loadData("begin_date DESC");
        else if (position == 6 && posAD == 0)
            loadData("end_date ASC");
        else if (position == 6 && posAD == 1)
            loadData("end_date DESC");
        else if (position == 7 && posAD == 0)
            loadData("agreement_status ASC");
        else if (position == 7 && posAD == 1)
            loadData("agreement_status DESC");
        else loadData("contract_agreement_id DESC");
    }

    private void setAdapterList(){
        adapter = new KontrakPerjanjianFragment.MyRecyclerViewAdapter(contractAgreements, mListener);
        kpRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        kpRecycler.setAdapter(null);
        contractAgreements.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CONTTRACT_AGREEMENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            contractAgreements.add(new ContractAgreement(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (kpEditSearch.getText().toString().matches("")){
                                kpSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(kpEditSearch.getText()));
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
        kpRecycler.setAdapter(null);
        contractAgreements.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CONTTRACT_AGREEMENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            contractAgreements.add(new ContractAgreement(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (kpEditSearch.getText().toString().matches("")){
                                kpSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(kpEditSearch.getText()));
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
        void onListFragmentInteraction(ContractAgreement item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<ContractAgreement> mValues;
        private final List<ContractAgreement> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<ContractAgreement> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_kontrak_perjanjian_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.kpTextNomor.setText(""+mValues.get(position).getAgreement_number());
            holder.kpTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.kpTextKontak.setText(""+mValues.get(position).getContact_id());
            holder.kpTextTglKontrak.setText(""+mValues.get(position).getAgreement_date());
            holder.kpTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.kpTextTglAkhir.setText(""+mValues.get(position).getEnd_date());
            holder.kpTextTglStatus.setText(""+mValues.get(position).getAgreement_status());

            if (position%2==0)
                holder.kpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.kpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (mValues.get(position).getAgreement_file_name().toLowerCase().contains("pdf".toLowerCase())){
                holder.kpTextFile.setText("Download");

                holder.kpTextFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/contractAgreement/"+ mValues.get(position).getAgreement_file_name());
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                });
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getContract_agreement_id(), mValues.get(position));
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
                List<ContractAgreement> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((ContractAgreement) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ContractAgreement item : values){
                        if (kpSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getAgreement_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAgreement_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAgreement_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getAgreement_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getAgreement_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (kpSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getAgreement_status().toLowerCase().contains(filterPattern)){
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

            public final TextView kpTextNomor;
            public final TextView kpTextSupplier;
            public final TextView kpTextKontak;
            public final TextView kpTextFile;
            public final TextView kpTextTglKontrak;
            public final TextView kpTextTglAwal;
            public final TextView kpTextTglAkhir;
            public final TextView kpTextTglStatus;

            public final LinearLayout kpLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                kpTextNomor = (TextView) view.findViewById(R.id.kpTextNomor);
                kpTextSupplier = (TextView) view.findViewById(R.id.kpTextSupplier);
                kpTextKontak = (TextView) view.findViewById(R.id.kpTextKontak);
                kpTextFile = (TextView) view.findViewById(R.id.kpTextFile);
                kpTextTglKontrak = (TextView) view.findViewById(R.id.kpTextTglKontrak);
                kpTextTglAwal = (TextView) view.findViewById(R.id.kpTextTglAwal);
                kpTextTglAkhir = (TextView) view.findViewById(R.id.kpTextTglAkhir);
                kpTextTglStatus = (TextView) view.findViewById(R.id.kpTextStatus);

                kpLayoutList = (LinearLayout) view.findViewById(R.id.kpLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final ContractAgreement contractAgreement) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), KontrakPerjanjianDetailActivity.class);
                        intent.putExtra("detail", contractAgreement);
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
                param.put("feature", "" + "contract-agreement");
                param.put("access", "" + "contract-agreement:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
