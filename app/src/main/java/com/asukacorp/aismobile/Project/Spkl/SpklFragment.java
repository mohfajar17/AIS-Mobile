package com.asukacorp.aismobile.Project.Spkl;

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
import com.asukacorp.aismobile.Data.Project.Spkl;
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

public class SpklFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView pskTextPaging;
    public EditText pskEditSearch;
    public ImageView pskBtnSearch;
    public RecyclerView pskRecycler;
    public FloatingActionButton pskFabAdd;
    public Spinner pskSpinnerSearch;
    public Spinner pskSpinnerSort;
    public Spinner pskSpinnerSortAD;
    public Button pskBtnShowList;
    public ImageButton pskBtnBefore;
    public ImageButton pskBtnNext;
    public LinearLayout pskLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public SpklFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpklSpinnerSearch = {"Semua Data", "Overtime Workorder Number", "Proposed Date", "Work Description",
            "Work Location", "Job Code", "File", "Department", "Approval 1", "Approval 2", "Verified By"};
    public String[] SpklSpinnerSort = {"-- Sort By --", "Berdasarkan Overtime Workorder Number", "Berdasarkan Proposed Date",
            "Berdasarkan Work Description", "Berdasarkan Work Location", "Berdasarkan Job Code", "Berdasarkan File",
            "Berdasarkan Department", "Berdasarkan Approval 1", "Berdasarkan Approval 2", "Berdasarkan Verified By"};
    public String[] SpklADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Spkl> spkls;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public SpklFragment() {
    }

    public static SpklFragment newInstance() {
        SpklFragment fragment = new SpklFragment();
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

        spkls = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spkl, container, false);

        // Set the adapter
        pskRecycler = (RecyclerView) view.findViewById(R.id.pskRecycler);
        if (mColumnCount <= 1) {
            pskRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pskRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pskFabAdd = (FloatingActionButton) view.findViewById(R.id.pskFabAdd);
        pskEditSearch = (EditText) view.findViewById(R.id.pskEditSearch);
        pskTextPaging = (TextView) view.findViewById(R.id.pskTextPaging);
        pskBtnSearch = (ImageView) view.findViewById(R.id.pskBtnSearch);
        pskSpinnerSearch = (Spinner) view.findViewById(R.id.pskSpinnerSearch);
        pskSpinnerSort = (Spinner) view.findViewById(R.id.pskSpinnerSort);
        pskSpinnerSortAD = (Spinner) view.findViewById(R.id.pskSpinnerSortAD);
        pskBtnShowList = (Button) view.findViewById(R.id.pskBtnShowList);
        pskBtnBefore = (ImageButton) view.findViewById(R.id.pskBtnBefore);
        pskBtnNext = (ImageButton) view.findViewById(R.id.pskBtnNext);
        pskLayoutPaging = (LinearLayout) view.findViewById(R.id.pskLayoutPaging);

        pskFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuatSpklActivity.class);
                startActivityForResult(intent,1);
            }
        });

        pskBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pskTextPaging.getText()));
                setSortHalf(pskSpinnerSort.getSelectedItemPosition(), pskSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pskTextPaging.getText()))+1;
                pskTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pskBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pskTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pskTextPaging.getText()))-2);
                    setSortHalf(pskSpinnerSort.getSelectedItemPosition(), pskSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pskTextPaging.getText()))-1;
                    pskTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pskBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("overtime_workorder_id DESC");
                    loadAll = true;
                    params = pskLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pskLayoutPaging.setLayoutParams(params);
                    pskBtnShowList.setText("Show Half");
                } else {
                    pskTextPaging.setText("1");
                    counter = 0;
                    loadData("overtime_workorder_id DESC");
                    loadAll = false;
                    params = pskLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pskLayoutPaging.setLayoutParams(params);
                    pskBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpklSpinnerSearch);
        pskSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpklSpinnerSort);
        pskSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SpklADSpinnerSort);
        pskSpinnerSortAD.setAdapter(spinnerAdapter);

        pskSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pskSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pskSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pskBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pskEditSearch.getText().toString().matches("")){
                    pskSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pskEditSearch.getText()));
            }
        });

//        loadData("overtime_workorder_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("overtime_workorder_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("overtime_workorder_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("proposed_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("proposed_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("work_description ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("work_description DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("work_location ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("work_location DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("job_order_id ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("job_order_id DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("overtime_file_name ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("overtime_file_name DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("department_id ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("department_id DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("approval1_by ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("approval1_by DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("approval2_by ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("approval2_by DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("verified_by ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("verified_by DESC");
        else loadDataAll("overtime_workorder_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("overtime_workorder_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("overtime_workorder_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("proposed_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("proposed_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("work_description ASC");
        else if (position == 3 && posAD == 1)
            loadData("work_description DESC");
        else if (position == 4 && posAD == 0)
            loadData("work_location ASC");
        else if (position == 4 && posAD == 1)
            loadData("work_location DESC");
        else if (position == 5 && posAD == 0)
            loadData("job_order_id ASC");
        else if (position == 5 && posAD == 1)
            loadData("job_order_id DESC");
        else if (position == 6 && posAD == 0)
            loadData("overtime_file_name ASC");
        else if (position == 6 && posAD == 1)
            loadData("overtime_file_name DESC");
        else if (position == 7 && posAD == 0)
            loadData("department_id ASC");
        else if (position == 7 && posAD == 1)
            loadData("department_id DESC");
        else if (position == 8 && posAD == 0)
            loadData("approval1_by ASC");
        else if (position == 8 && posAD == 1)
            loadData("approval1_by DESC");
        else if (position == 9 && posAD == 0)
            loadData("approval2_by ASC");
        else if (position == 9 && posAD == 1)
            loadData("approval2_by DESC");
        else if (position == 10 && posAD == 0)
            loadData("verified_by ASC");
        else if (position == 10 && posAD == 1)
            loadData("verified_by DESC");
        else loadData("overtime_workorder_id DESC");
    }

    private void setAdapterList(){
        adapter = new SpklFragment.MyRecyclerViewAdapter(spkls, mListener);
        pskRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        pskRecycler.setAdapter(null);
        spkls.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SPKL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spkls.add(new Spkl(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pskEditSearch.getText().toString().matches("")){
                                pskSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pskEditSearch.getText()));
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
        pskRecycler.setAdapter(null);
        spkls.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SPKL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spkls.add(new Spkl(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pskEditSearch.getText().toString().matches("")){
                                pskSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pskEditSearch.getText()));
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
        void onListFragmentInteraction(Spkl item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Spkl> mValues;
        private final List<Spkl> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Spkl> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_spkl_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pskTextSpklNumber.setText(""+mValues.get(position).getOvertime_workorder_number());
            holder.pskTextDate.setText(""+mValues.get(position).getProposed_date());
            holder.pskTextDescription.setText(""+mValues.get(position).getWork_description());
            holder.pskTextLocation.setText(""+mValues.get(position).getWork_location());
            holder.pskTextJobCode.setText(""+mValues.get(position).getJob_order_id());
            holder.pskTextFile.setText(""+mValues.get(position).getOvertime_file_name());
            holder.pskTextDepartment.setText(""+mValues.get(position).getDepartment_id());
            holder.pskTextApproval1.setText(""+mValues.get(position).getApproval1_by());
            holder.pskTextApproval2.setText(""+mValues.get(position).getApproval2_by());
            holder.pskTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());

            if (position%2==0)
                holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.pskBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mValues.get(position).getApproval1_by().isEmpty()){
                        Intent intent = new Intent(getActivity(), UpdateSpklActivity.class);
                        intent.putExtra("detail", mValues.get(position));
                        holder.itemView.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Overtime Workorder has been Approval I", Toast.LENGTH_LONG).show();
                    }
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getOvertime_workorder_id(), mValues.get(position));
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
                List<Spkl> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Spkl) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Spkl item : values){
                        if (pskSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getOvertime_workorder_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getProposed_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getWork_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getWork_location().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getOvertime_file_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDepartment_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getVerified_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getOvertime_workorder_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getProposed_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getWork_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getWork_location().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getOvertime_file_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getDepartment_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getApproval1_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getApproval2_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pskSpinnerSearch.getSelectedItemPosition()==10){
                            if (item.getVerified_by().toLowerCase().contains(filterPattern)){
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

            public final TextView pskTextSpklNumber;
            public final TextView pskTextDate;
            public final TextView pskTextDescription;
            public final TextView pskTextLocation;
            public final TextView pskTextJobCode;
            public final TextView pskTextFile;
            public final TextView pskTextDepartment;
            public final TextView pskTextApproval1;
            public final TextView pskTextApproval2;
            public final TextView pskTextVerifiedBy;

            public final ImageView pskBtnEdit;
            public final LinearLayout pskLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pskTextSpklNumber = (TextView) view.findViewById(R.id.pskTextSpklNumber);
                pskTextDate = (TextView) view.findViewById(R.id.pskTextDate);
                pskTextDescription = (TextView) view.findViewById(R.id.pskTextDescription);
                pskTextLocation = (TextView) view.findViewById(R.id.pskTextLocation);
                pskTextJobCode = (TextView) view.findViewById(R.id.pskTextJobCode);
                pskTextFile = (TextView) view.findViewById(R.id.pskTextFile);
                pskTextDepartment = (TextView) view.findViewById(R.id.pskTextDepartment);
                pskTextApproval1 = (TextView) view.findViewById(R.id.pskTextApproval1);
                pskTextApproval2 = (TextView) view.findViewById(R.id.pskTextApproval2);
                pskTextVerifiedBy = (TextView) view.findViewById(R.id.pskTextVerifiedBy);

                pskBtnEdit = (ImageView) view.findViewById(R.id.pskBtnEdit);
                pskLayoutList = (LinearLayout) view.findViewById(R.id.pskLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Spkl spkl) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailSpklActivity.class);
                        intent.putExtra("detail", spkl);
                        intent.putExtra("code", 0);
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You don't have access", Toast.LENGTH_LONG).show();
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
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "overtime-workorder");
                param.put("access", "" + "overtime-workorder:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
