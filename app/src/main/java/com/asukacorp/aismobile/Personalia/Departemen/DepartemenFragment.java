package com.asukacorp.aismobile.Personalia.Departemen;

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
import com.asukacorp.aismobile.Data.Personalia.Department;
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

public class DepartemenFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    private LinearLayout kShowFilter;
    private LinearLayout kLayoutFilter;

    public TextView textPaging;
    public EditText editSearch;
    public ImageView btnSearch;
    public RecyclerView recycler;
    public FloatingActionButton fabAdd;
    public Spinner spinnerSearch;
    public Spinner spinnerSort;
    public Spinner spinnerSortAD;
    public Button btnShowList;
    public ImageButton btnBefore;
    public ImageButton btnNext;
    public LinearLayout layoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public DepartemenFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Kode", "Kode Item", "Nama Departemen", "Kepala Department",
            "Catatan", "Atasnya", "Perusahaan"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Kode", "Berdasarkan Kode Item", "Berdasarkan Nama Departemen",
            "Berdasarkan Kepala Department", "Berdasarkan Catatan", "Berdasarkan Atasnya", "Berdasarkan Perusahaan"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Department> hariLiburs;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public DepartemenFragment() {
    }

    public static DepartemenFragment newInstance() {
        DepartemenFragment fragment = new DepartemenFragment();
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

        hariLiburs = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departemen, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.depShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.depLayoutFilter);

        kShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = kShowFilter.getLayoutParams();
                params.height = 0;
                kShowFilter.setLayoutParams(params);
                params = kLayoutFilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                kLayoutFilter.setLayoutParams(params);
            }
        });

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.depRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.empFabAdd);
        editSearch = (EditText) view.findViewById(R.id.depEditSearch);
        textPaging = (TextView) view.findViewById(R.id.depTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.depBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.depSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.depSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.depSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.depBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.depBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.depBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.depLayoutPaging);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(textPaging.getText()));
                setSortHalf(spinnerSort.getSelectedItemPosition(), spinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(textPaging.getText()))+1;
                textPaging.setText(""+textValue);
                filter = true;
            }
        });
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(textPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(textPaging.getText()))-2);
                    setSortHalf(spinnerSort.getSelectedItemPosition(), spinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(textPaging.getText()))-1;
                    textPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("department_id ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("department_id ASC");
                    loadAll = false;
                    params = layoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        spinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSort);
        spinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ADSpinnerSort);
        spinnerSortAD.setAdapter(spinnerAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, spinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, spinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearch.getText().toString().matches("")){
                    loadData("department_id ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("department_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("department_code ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("department_code DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("code_for_item ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("code_for_item DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("department_name ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("department_name DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("head_department ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("head_department DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("department_notes ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("department_notes DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("atasan ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("atasan DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("company_name ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("company_name DESC");
        else loadDataAll("department_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("department_code ASC");
        else if (position == 1 && posAD == 1)
            loadData("department_code DESC");
        else if (position == 2 && posAD == 0)
            loadData("code_for_item ASC");
        else if (position == 2 && posAD == 1)
            loadData("code_for_item DESC");
        else if (position == 3 && posAD == 0)
            loadData("department_name ASC");
        else if (position == 3 && posAD == 1)
            loadData("department_name DESC");
        else if (position == 4 && posAD == 0)
            loadData("head_department ASC");
        else if (position == 4 && posAD == 1)
            loadData("head_department DESC");
        else if (position == 5 && posAD == 0)
            loadData("department_notes ASC");
        else if (position == 5 && posAD == 1)
            loadData("department_notes DESC");
        else if (position == 6 && posAD == 0)
            loadData("atasan ASC");
        else if (position == 6 && posAD == 1)
            loadData("atasan DESC");
        else if (position == 7 && posAD == 0)
            loadData("company_name ASC");
        else if (position == 7 && posAD == 1)
            loadData("company_name DESC");
        else loadData("department_id ASC");
    }

    private void setAdapterList(){
        adapter = new DepartemenFragment.MyRecyclerViewAdapter(hariLiburs, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        hariLiburs.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DEPARTEMEN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            hariLiburs.add(new Department(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                            } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
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
        recycler.setAdapter(null);
        hariLiburs.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DEPARTEMEN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            hariLiburs.add(new Department(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                            } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
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
        void onListFragmentInteraction(Department item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Department> mValues;
        private final List<Department> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Department> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_departemen_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.depTextKode.setText(""+mValues.get(position).getDepartment_code());
            holder.depTextKodeItem.setText(""+mValues.get(position).getCode_for_item());
            holder.depTextNamaDep.setText(""+mValues.get(position).getDepartment_name());
            holder.depTextKepalaDep.setText(""+mValues.get(position).getHead_department());
            holder.depTextCatatan.setText(""+mValues.get(position).getDepartment_notes());
            holder.depTextAtasnya.setText(""+mValues.get(position).getAtasan());
            holder.depTextPerusahaan.setText(""+mValues.get(position).getCompany_name());
            holder.depTextJumlah.setText("");

            if (position%2==0)
                holder.depLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.depLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getDepartment_id(), mValues.get(position));
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
                List<Department> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Department) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Department item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getDepartment_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCode_for_item().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDepartment_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getHead_department().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDepartment_notes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getDepartment_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCode_for_item().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getDepartment_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getHead_department().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getDepartment_notes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
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

            public final TextView depTextKode;
            public final TextView depTextKodeItem;
            public final TextView depTextNamaDep;
            public final TextView depTextKepalaDep;
            public final TextView depTextCatatan;
            public final TextView depTextAtasnya;
            public final TextView depTextPerusahaan;
            public final TextView depTextJumlah;

            public final LinearLayout depLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                depTextKode = (TextView) view.findViewById(R.id.depTextKode);
                depTextKodeItem = (TextView) view.findViewById(R.id.depTextKodeItem);
                depTextNamaDep = (TextView) view.findViewById(R.id.depTextNamaDep);
                depTextKepalaDep = (TextView) view.findViewById(R.id.depTextKepalaDep);
                depTextCatatan = (TextView) view.findViewById(R.id.depTextCatatan);
                depTextAtasnya = (TextView) view.findViewById(R.id.depTextAtasnya);
                depTextPerusahaan = (TextView) view.findViewById(R.id.depTextPerusahaan);
                depTextJumlah = (TextView) view.findViewById(R.id.depTextJumlah);

                depLayoutList = (LinearLayout) view.findViewById(R.id.depLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Department department) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailDepartemenActivity.class);
                        intent.putExtra("detail", department);
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
                param.put("feature", "" + "department");
                param.put("access", "" + "department:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
