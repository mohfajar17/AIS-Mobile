package com.asukacorp.aismobile.Personalia.Pangkat;

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
import com.asukacorp.aismobile.Data.Personalia.JobTitle;
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

public class PangkatFragment extends Fragment {

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
    public PangkatFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Kode Pangkat", "Nama Pangkat", "Deskripsi", "Aktif"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Kode Pangkat", "Berdasarkan Nama Pangkat",
            "Berdasarkan Deskripsi", "Berdasarkan Aktif"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<JobTitle> jobTitles;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public PangkatFragment() {
    }

    public static PangkatFragment newInstance() {
        PangkatFragment fragment = new PangkatFragment();
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

        jobTitles = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pangkat, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.pShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.pLayoutFilter);

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
        recycler = (RecyclerView) view.findViewById(R.id.pRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.pFabAdd);
        editSearch = (EditText) view.findViewById(R.id.pEditSearch);
        textPaging = (TextView) view.findViewById(R.id.pTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.pBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.pSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.pSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.pSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.pBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.pBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.pBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.pLayoutPaging);

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
                    loadDataAll("jobtitle_name ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("jobtitle_name ASC");
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
                    loadData("jobtitle_name ASC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("jobtitle_name ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("jobtitle_code ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("jobtitle_code DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("jobtitle_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("jobtitle_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("jobtitle_description ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("jobtitle_description DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("jobtitle_name ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("jobtitle_code ASC");
        else if (position == 1 && posAD == 1)
            loadData("jobtitle_code DESC");
        else if (position == 2 && posAD == 0)
            loadData("jobtitle_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("jobtitle_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("jobtitle_description ASC");
        else if (position == 3 && posAD == 1)
            loadData("jobtitle_description DESC");
        else if (position == 4 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 4 && posAD == 1)
            loadData("is_active DESC");
        else loadData("jobtitle_name ASC");
    }

    private void setAdapterList(){
        adapter = new PangkatFragment.MyRecyclerViewAdapter(jobTitles, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        jobTitles.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_TITLE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            jobTitles.add(new JobTitle(jsonArray.getJSONObject(i)));
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
        jobTitles.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_TITLE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            jobTitles.add(new JobTitle(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(JobTitle item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<JobTitle> mValues;
        private final List<JobTitle> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<JobTitle> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_pangkat_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pTextKode.setText(""+mValues.get(position).getJobtitle_code());
            holder.pTextNama.setText(""+mValues.get(position).getJobtitle_name());
            holder.pTextDeskripsi.setText(""+mValues.get(position).getJobtitle_description());
            holder.pTextAktif.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.pLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getJobtitle_id(), mValues.get(position));
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
                List<JobTitle> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((JobTitle) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (JobTitle item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getJobtitle_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJobtitle_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJobtitle_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getJobtitle_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJobtitle_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getJobtitle_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
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

            public final TextView pTextKode;
            public final TextView pTextNama;
            public final TextView pTextDeskripsi;
            public final TextView pTextAktif;

            public final LinearLayout pLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pTextKode = (TextView) view.findViewById(R.id.pTextKode);
                pTextNama = (TextView) view.findViewById(R.id.pTextNama);
                pTextDeskripsi = (TextView) view.findViewById(R.id.pTextDeskripsi);
                pTextAktif = (TextView) view.findViewById(R.id.pTextAktif);

                pLayoutList = (LinearLayout) view.findViewById(R.id.pLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final JobTitle jobTitle) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailPangkatActivity.class);
                        intent.putExtra("detail", jobTitle);
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
                param.put("feature", "" + "job-title");
                param.put("access", "" + "job-title:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
