package com.example.aismobile.Contact.Perusahaan;

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
import com.example.aismobile.Data.Contact.Company;
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

public class PerusahaanFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView prsTextPaging;
    public EditText prsEditSearch;
    public ImageView prsBtnSearch;
    public RecyclerView prsRecycler;
    public FloatingActionButton prsFabAdd;
    public Spinner prsSpinnerSearch;
    public Spinner prsSpinnerSort;
    public Spinner prsSpinnerSortAD;
    public Button prsBtnShowList;
    public ImageButton prsBtnBefore;
    public ImageButton prsBtnNext;
    public LinearLayout prsLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public PerusahaanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] PRSSpinnerSearch = {"Semua Data", "Nama", "Code", "Alamat", "Kota", "E-Mail"};
    public String[] PRSSpinnerSort = {"-- Sort By --", "Berdasarkan Nama", "Berdasarkan Code",
            "Berdasarkan Alamat", "Berdasarkan Kota", "Berdasarkan E-Mail"};
    public String[] PRSADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Company> companies;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public PerusahaanFragment() {
    }

    public static PerusahaanFragment newInstance() {
        PerusahaanFragment fragment = new PerusahaanFragment();
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

        companies = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perusahaan, container, false);

        // Set the adapter
        prsRecycler = (RecyclerView) view.findViewById(R.id.prsRecycler);
        if (mColumnCount <= 1) {
            prsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            prsRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        prsFabAdd = (FloatingActionButton) view.findViewById(R.id.prsFabAdd);
        prsEditSearch = (EditText) view.findViewById(R.id.prsEditSearch);
        prsTextPaging = (TextView) view.findViewById(R.id.prsTextPaging);
        prsBtnSearch = (ImageView) view.findViewById(R.id.prsBtnSearch);
        prsSpinnerSearch = (Spinner) view.findViewById(R.id.prsSpinnerSearch);
        prsSpinnerSort = (Spinner) view.findViewById(R.id.prsSpinnerSort);
        prsSpinnerSortAD = (Spinner) view.findViewById(R.id.prsSpinnerSortAD);
        prsBtnShowList = (Button) view.findViewById(R.id.prsBtnShowList);
        prsBtnBefore = (ImageButton) view.findViewById(R.id.prsBtnBefore);
        prsBtnNext = (ImageButton) view.findViewById(R.id.prsBtnNext);
        prsLayoutPaging = (LinearLayout) view.findViewById(R.id.prsLayoutPaging);

        prsBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(prsTextPaging.getText()));
                setSortHalf(prsSpinnerSort.getSelectedItemPosition(), prsSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(prsTextPaging.getText()))+1;
                prsTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        prsBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(prsTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(prsTextPaging.getText()))-2);
                    setSortHalf(prsSpinnerSort.getSelectedItemPosition(), prsSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(prsTextPaging.getText()))-1;
                    prsTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        prsBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("company_id ASC");
                    loadAll = true;
                    params = prsLayoutPaging.getLayoutParams();
                    params.height = 0;
                    prsLayoutPaging.setLayoutParams(params);
                    prsBtnShowList.setText("Show Half");
                } else {
                    prsTextPaging.setText("1");
                    counter = 0;
                    loadData("company_id ASC");
                    loadAll = false;
                    params = prsLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    prsLayoutPaging.setLayoutParams(params);
                    prsBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PRSSpinnerSearch);
        prsSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PRSSpinnerSort);
        prsSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PRSADSpinnerSort);
        prsSpinnerSortAD.setAdapter(spinnerAdapter);

        prsSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, prsSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, prsSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        prsBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prsEditSearch.getText().toString().matches("")){
                    prsSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(prsEditSearch.getText()));
            }
        });

//        loadData("company_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("company_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("company_name ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("company_code ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("company_code DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("company_address ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("company_address DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("company_city ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("company_city DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("company_email ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("company_email DESC");
        else loadDataAll("company_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("company_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("company_name ASC");
        else if (position == 2 && posAD == 0)
            loadData("company_code ASC");
        else if (position == 2 && posAD == 1)
            loadData("company_code DESC");
        else if (position == 3 && posAD == 0)
            loadData("company_address ASC");
        else if (position == 3 && posAD == 1)
            loadData("company_address DESC");
        else if (position == 4 && posAD == 0)
            loadData("company_city ASC");
        else if (position == 4 && posAD == 1)
            loadData("company_city DESC");
        else if (position == 5 && posAD == 0)
            loadData("company_email ASC");
        else if (position == 5 && posAD == 1)
            loadData("company_email DESC");
        else loadData("company_id ASC");
    }

    private void setAdapterList(){
        adapter = new PerusahaanFragment.MyRecyclerViewAdapter(companies, mListener);
        prsRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        prsRecycler.setAdapter(null);
        companies.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_COMPANY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            companies.add(new Company(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (prsEditSearch.getText().toString().matches("")){
                                prsSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(prsEditSearch.getText()));
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
        prsRecycler.setAdapter(null);
        companies.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_COMPANY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            companies.add(new Company(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (prsEditSearch.getText().toString().matches("")){
                                prsSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(prsEditSearch.getText()));
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
        void onListFragmentInteraction(Company item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Company> mValues;
        private final List<Company> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Company> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_perusahaan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.prsTextName.setText(""+mValues.get(position).getCompany_name());
            holder.prsTextCode.setText(""+mValues.get(position).getCompany_code());
            holder.prsTextAlamat.setText(""+mValues.get(position).getCompany_address());
            holder.prsTextKota.setText(""+mValues.get(position).getCompany_city());
            holder.prsTextEmail.setText(""+mValues.get(position).getCompany_email());

            if (position%2==0)
                holder.prsLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.prsLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getCompany_id(), mValues.get(position));
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
                List<Company> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Company) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Company item : values){
                        if (prsSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_address().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_city().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_email().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (prsSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (prsSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCompany_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (prsSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getCompany_address().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (prsSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCompany_city().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (prsSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getCompany_email().toLowerCase().contains(filterPattern)){
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

            public final TextView prsTextName;
            public final TextView prsTextCode;
            public final TextView prsTextAlamat;
            public final TextView prsTextKota;
            public final TextView prsTextEmail;

            public final LinearLayout prsLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                prsTextName = (TextView) view.findViewById(R.id.prsTextName);
                prsTextCode = (TextView) view.findViewById(R.id.prsTextCode);
                prsTextAlamat = (TextView) view.findViewById(R.id.prsTextAlamat);
                prsTextKota = (TextView) view.findViewById(R.id.prsTextKota);
                prsTextEmail = (TextView) view.findViewById(R.id.prsTextEmail);

                prsLayoutList = (LinearLayout) view.findViewById(R.id.prsLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Company company) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailPerusahaanActivity.class);
                        intent.putExtra("detail", company);
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
                param.put("feature", "" + "company");
                param.put("access", "" + "company:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
