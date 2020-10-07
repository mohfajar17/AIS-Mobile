package com.example.aismobile.Finance.DaftarAkun;

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
import com.example.aismobile.Data.FinanceAccounting.DaftarAkun;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaftarAkunFragment extends Fragment {

    public TextView daTextPaging;
    public EditText daEditSearch;
    public ImageView daBtnSearch;
    public RecyclerView daRecycler;
    public FloatingActionButton daFabAdd;
    public Spinner daSpinnerSearch;
    public Spinner daSpinnerSort;
    public Spinner daSpinnerSortAD;
    public Button daBtnShowList;
    public ImageButton daBtnBefore;
    public ImageButton daBtnNext;
    public LinearLayout daLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public DaftarAkunFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Type", "Code", "Account", "Account Code", "Is Group",
            "Is Active"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Type", "Berdasarkan Code", "Berdasarkan Account",
            "Berdasarkan Account Code", "Berdasarkan Is Group", "Berdasarkan Is Active"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<DaftarAkun> daftarAkuns;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public DaftarAkunFragment() {
    }

    public static DaftarAkunFragment newInstance() {
        DaftarAkunFragment fragment = new DaftarAkunFragment();
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

        daftarAkuns = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar_akun, container, false);

        // Set the adapter
        daRecycler = (RecyclerView) view.findViewById(R.id.daRecycler);
        if (mColumnCount <= 1) {
            daRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            daRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        daFabAdd = (FloatingActionButton) view.findViewById(R.id.daFabAdd);
        daEditSearch = (EditText) view.findViewById(R.id.daEditSearch);
        daTextPaging = (TextView) view.findViewById(R.id.daTextPaging);
        daBtnSearch = (ImageView) view.findViewById(R.id.daBtnSearch);
        daSpinnerSearch = (Spinner) view.findViewById(R.id.daSpinnerSearch);
        daSpinnerSort = (Spinner) view.findViewById(R.id.daSpinnerSort);
        daSpinnerSortAD = (Spinner) view.findViewById(R.id.daSpinnerSortAD);
        daBtnShowList = (Button) view.findViewById(R.id.daBtnShowList);
        daBtnBefore = (ImageButton) view.findViewById(R.id.daBtnBefore);
        daBtnNext = (ImageButton) view.findViewById(R.id.daBtnNext);
        daLayoutPaging = (LinearLayout) view.findViewById(R.id.daLayoutPaging);

        daBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(daTextPaging.getText()));
                setSortHalf(daSpinnerSort.getSelectedItemPosition(), daSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(daTextPaging.getText()))+1;
                daTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        daBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(daTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(daTextPaging.getText()))-2);
                    setSortHalf(daSpinnerSort.getSelectedItemPosition(), daSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(daTextPaging.getText()))-1;
                    daTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        daBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("account_id ASC");
                    loadAll = true;
                    params = daLayoutPaging.getLayoutParams();
                    params.height = 0;
                    daLayoutPaging.setLayoutParams(params);
                    daBtnShowList.setText("Show Half");
                } else {
                    daTextPaging.setText("1");
                    counter = 0;
                    loadData("account_id ASC");
                    loadAll = false;
                    params = daLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    daLayoutPaging.setLayoutParams(params);
                    daBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        daSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        daSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        daSpinnerSortAD.setAdapter(spinnerAdapter);

        daSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, daSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, daSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daEditSearch.getText().toString().matches("")){
                    daSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
            }
        });

//        loadData("account_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("chart_of_account_type_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("chart_of_account_type_name ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("chart_of_account_group_code ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("chart_of_account_group_code DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("account ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("account DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("account_code ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("account_code DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("is_group ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("is_group DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("account_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("chart_of_account_type_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("chart_of_account_type_name ASC");
        else if (position == 2 && posAD == 0)
            loadData("chart_of_account_group_code ASC");
        else if (position == 2 && posAD == 1)
            loadData("chart_of_account_group_code DESC");
        else if (position == 3 && posAD == 0)
            loadData("account ASC");
        else if (position == 3 && posAD == 1)
            loadData("account DESC");
        else if (position == 4 && posAD == 0)
            loadData("account_code ASC");
        else if (position == 4 && posAD == 1)
            loadData("account_code DESC");
        else if (position == 5 && posAD == 0)
            loadData("is_group ASC");
        else if (position == 5 && posAD == 1)
            loadData("is_group DESC");
        else if (position == 6 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 6 && posAD == 1)
            loadData("is_active DESC");
        else loadData("account_id ASC");
    }

    private void setAdapterList(){
        adapter = new DaftarAkunFragment.MyRecyclerViewAdapter(daftarAkuns, mListener);
        daRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        daRecycler.setAdapter(null);
        daftarAkuns.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DAFTAR_AKUN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            daftarAkuns.add(new DaftarAkun(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (daEditSearch.getText().toString().matches("")){
                                daSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
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
        daRecycler.setAdapter(null);
        daftarAkuns.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DAFTAR_AKUN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            daftarAkuns.add(new DaftarAkun(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (daEditSearch.getText().toString().matches("")){
                                daSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(daEditSearch.getText()));
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
        void onListFragmentInteraction(DaftarAkun item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<DaftarAkun> mValues;
        private final List<DaftarAkun> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<DaftarAkun> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_daftar_akun_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.daTextCOAType.setText(""+mValues.get(position).getChart_of_account_type_name());
            holder.daTextCOACode.setText(""+mValues.get(position).getChart_of_account_group_code());
            holder.daTextAccount.setText(""+mValues.get(position).getAccount());
            holder.daTextAccountCode.setText(""+mValues.get(position).getAccount_code());
            holder.daTextIsGroup.setText(""+mValues.get(position).getIs_group());
            holder.daTextIsActive.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.daLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.daLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), ""+mValues.get(position).getCreated_by(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(), JobOrderDetailActivity.class);
//                    intent.putExtra("detailJO", mValues.get(position));
//                    holder.itemView.getContext().startActivity(intent);
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
                List<DaftarAkun> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((DaftarAkun) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (DaftarAkun item : values){
                        if (daSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getChart_of_account_type_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getChart_of_account_group_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAccount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAccount_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_group().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getChart_of_account_type_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getChart_of_account_group_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getAccount().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getAccount_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getIs_group().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (daSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getIs_active().toLowerCase().contains(filterPattern)){
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

            public final TextView daTextCOAType;
            public final TextView daTextCOACode;
            public final TextView daTextAccount;
            public final TextView daTextAccountCode;
            public final TextView daTextIsGroup;
            public final TextView daTextIsActive;

            public final LinearLayout daLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                daTextCOAType = (TextView) view.findViewById(R.id.daTextCOAType);
                daTextCOACode = (TextView) view.findViewById(R.id.daTextCOACode);
                daTextAccount = (TextView) view.findViewById(R.id.daTextAccount);
                daTextAccountCode = (TextView) view.findViewById(R.id.daTextAccountCode);
                daTextIsGroup = (TextView) view.findViewById(R.id.daTextIsGroup);
                daTextIsActive = (TextView) view.findViewById(R.id.daTextIsActive);

                daLayoutList = (LinearLayout) view.findViewById(R.id.daLayoutList);
            }
        }
    }
}
