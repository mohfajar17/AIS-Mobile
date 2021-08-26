package com.asukacorp.aismobile.Project.MaterialRequest;

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
import com.asukacorp.aismobile.Data.Project.MaterialRequest;
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

public class MaterialReqFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView pmrTextPaging;
    public EditText pmrEditSearch;
    public ImageView pmrBtnSearch;
    public RecyclerView pmrRecycler;
    public FloatingActionButton pmrFabAdd;
    public Spinner pmrSpinnerSearch;
    public Spinner pmrSpinnerSort;
    public Spinner pmrSpinnerSortAD;
    public Button pmrBtnShowList;
    public ImageButton pmrBtnBefore;
    public ImageButton pmrBtnNext;
    public LinearLayout pmrLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public MaterialReqFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] MRSpinnerSearch = {"Semua Data", "Nomor Material Request", "Job Code", "Tanggal Permintaan",
            "Status", "Dibuat Oleh", "Approval 1", "Approval 2", "Approval 3", "Priority"};
    public String[] MRSpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Material Request", "Berdasarkan Job Code",
            "Berdasarkan Tanggal Permintaan", "Berdasarkan Status", "Berdasarkan Dibuat Oleh", "Berdasarkan Approval 1",
            "Berdasarkan Approval 2", "Berdasarkan Approval 3", "Berdasarkan Priority"};
    public String[] MRADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<MaterialRequest> materialRequests;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public MaterialReqFragment() {
    }

    public static MaterialReqFragment newInstance() {
        MaterialReqFragment fragment = new MaterialReqFragment();
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

        materialRequests = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_req, container, false);

        // Set the adapter
        pmrRecycler = (RecyclerView) view.findViewById(R.id.pmrRecycler);
        if (mColumnCount <= 1) {
            pmrRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pmrRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pmrFabAdd = (FloatingActionButton) view.findViewById(R.id.pmrFabAdd);
        pmrEditSearch = (EditText) view.findViewById(R.id.pmrEditSearch);
        pmrTextPaging = (TextView) view.findViewById(R.id.pmrTextPaging);
        pmrBtnSearch = (ImageView) view.findViewById(R.id.pmrBtnSearch);
        pmrSpinnerSearch = (Spinner) view.findViewById(R.id.pmrSpinnerSearch);
        pmrSpinnerSort = (Spinner) view.findViewById(R.id.pmrSpinnerSort);
        pmrSpinnerSortAD = (Spinner) view.findViewById(R.id.pmrSpinnerSortAD);
        pmrBtnShowList = (Button) view.findViewById(R.id.pmrBtnShowList);
        pmrBtnBefore = (ImageButton) view.findViewById(R.id.pmrBtnBefore);
        pmrBtnNext = (ImageButton) view.findViewById(R.id.pmrBtnNext);
        pmrLayoutPaging = (LinearLayout) view.findViewById(R.id.pmrLayoutPaging);

        pmrBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pmrTextPaging.getText()));
                setSortHalf(pmrSpinnerSort.getSelectedItemPosition(), pmrSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(pmrTextPaging.getText()))+1;
                pmrTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        pmrBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pmrTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pmrTextPaging.getText()))-2);
                    setSortHalf(pmrSpinnerSort.getSelectedItemPosition(), pmrSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(pmrTextPaging.getText()))-1;
                    pmrTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        pmrBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadMaterialAll("material_request_id DESC");
                    loadAll = true;
                    params = pmrLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pmrLayoutPaging.setLayoutParams(params);
                    pmrBtnShowList.setText("Show Half");
                } else {
                    pmrTextPaging.setText("1");
                    counter = 0;
                    loadMaterial("material_request_id DESC");
                    loadAll = false;
                    params = pmrLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pmrLayoutPaging.setLayoutParams(params);
                    pmrBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, MRSpinnerSearch);
        pmrSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, MRSpinnerSort);
        pmrSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, MRADSpinnerSort);
        pmrSpinnerSortAD.setAdapter(spinnerAdapter);

        pmrSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, pmrSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, pmrSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pmrBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pmrEditSearch.getText().toString().matches("")){
                    pmrSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pmrEditSearch.getText()));
            }
        });

//        loadMaterial("material_request_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadMaterialAll("material_request_id ASC");
        else if (position == 1 && posAD == 1)
            loadMaterialAll("material_request_id DESC");
        else if (position == 2 && posAD == 0)
            loadMaterialAll("job_order_id ASC");
        else if (position == 2 && posAD == 1)
            loadMaterialAll("job_order_id DESC");
        else if (position == 3 && posAD == 0)
            loadMaterialAll("requisition_date ASC");
        else if (position == 3 && posAD == 1)
            loadMaterialAll("requisition_date DESC");
        else if (position == 4 && posAD == 0)
            loadMaterialAll("material_request_status ASC");
        else if (position == 4 && posAD == 1)
            loadMaterialAll("material_request_status DESC");
        else if (position == 5 && posAD == 0)
            loadMaterialAll("created_by ASC");
        else if (position == 5 && posAD == 1)
            loadMaterialAll("created_by DESC");
        else if (position == 6 && posAD == 0)
            loadMaterialAll("approval1 ASC");
        else if (position == 6 && posAD == 1)
            loadMaterialAll("approval1 DESC");
        else if (position == 7 && posAD == 0)
            loadMaterialAll("approval2 ASC");
        else if (position == 7 && posAD == 1)
            loadMaterialAll("approval2 DESC");
        else if (position == 8 && posAD == 0)
            loadMaterialAll("approval3 ASC");
        else if (position == 8 && posAD == 1)
            loadMaterialAll("approval3 DESC");
        else if (position == 9 && posAD == 0)
            loadMaterialAll("priority ASC");
        else if (position == 9 && posAD == 1)
            loadMaterialAll("priority DESC");
        else loadMaterialAll("material_request_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadMaterial("material_request_id ASC");
        else if (position == 1 && posAD == 1)
            loadMaterial("material_request_id DESC");
        else if (position == 2 && posAD == 0)
            loadMaterial("job_order_id ASC");
        else if (position == 2 && posAD == 1)
            loadMaterial("job_order_id DESC");
        else if (position == 3 && posAD == 0)
            loadMaterial("requisition_date ASC");
        else if (position == 3 && posAD == 1)
            loadMaterial("requisition_date DESC");
        else if (position == 4 && posAD == 0)
            loadMaterial("material_request_status ASC");
        else if (position == 4 && posAD == 1)
            loadMaterial("material_request_status DESC");
        else if (position == 5 && posAD == 0)
            loadMaterial("created_by ASC");
        else if (position == 5 && posAD == 1)
            loadMaterial("created_by DESC");
        else if (position == 6 && posAD == 0)
            loadMaterial("approval1 ASC");
        else if (position == 6 && posAD == 1)
            loadMaterial("approval1 DESC");
        else if (position == 7 && posAD == 0)
            loadMaterial("approval2 ASC");
        else if (position == 7 && posAD == 1)
            loadMaterial("approval2 DESC");
        else if (position == 8 && posAD == 0)
            loadMaterial("approval3 ASC");
        else if (position == 8 && posAD == 1)
            loadMaterial("approval3 DESC");
        else if (position == 9 && posAD == 0)
            loadMaterial("priority ASC");
        else if (position == 9 && posAD == 1)
            loadMaterial("priority DESC");
        else loadMaterial("material_request_id DESC");
    }

    private void setAdapterList(){
        adapter = new MaterialReqFragment.MyRecyclerViewAdapter(materialRequests, mListener);
        pmrRecycler.setAdapter(adapter);
    }

    private void loadMaterialAll(final String sortBy) {
        progressDialog.show();
        pmrRecycler.setAdapter(null);
        materialRequests.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MATERIAL_REQUISITION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequests.add(new MaterialRequest(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pmrEditSearch.getText().toString().matches("")){
                                pmrSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pmrEditSearch.getText()));
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

    public void loadMaterial(final String sortBy){
        progressDialog.show();
        pmrRecycler.setAdapter(null);
        materialRequests.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_MATERIAL_REQUISITION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequests.add(new MaterialRequest(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (pmrEditSearch.getText().toString().matches("")){
                                pmrSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(pmrEditSearch.getText()));
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
        void onListFragmentInteraction(MaterialRequest item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<MaterialRequest> mValues;
        private final List<MaterialRequest> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<MaterialRequest> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_material_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pmrTextNomorMaterial.setText(""+mValues.get(position).getMaterial_request_number());
            holder.pmrTextJobCode.setText(""+mValues.get(position).getJob_order_id());
            holder.pmrTextTglPermintaan.setText(""+mValues.get(position).getRequisition_date());
            holder.pmrStatus.setText(""+mValues.get(position).getMaterial_request_status());
            holder.pmrTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pmrTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pmrTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pmrTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getPriority())==4)
                holder.pmrTextPriority.setText("Urgent");
            else if (Integer.valueOf(mValues.get(position).getPriority())==3)
                holder.pmrTextPriority.setText("High");
            else if (Integer.valueOf(mValues.get(position).getPriority())==2)
                holder.pmrTextPriority.setText("Medium");
            else holder.pmrTextPriority.setText("Normal");

            if (position%2==0)
                holder.pmrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pmrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getMaterial_request_id(), mValues.get(position));
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
                List<MaterialRequest> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((MaterialRequest) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (MaterialRequest item : values){
                        if (pmrSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getMaterial_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRequisition_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getMaterial_request_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPriority().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getMaterial_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getRequisition_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getMaterial_request_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getApproval2().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getApproval3().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pmrSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getPriority().toLowerCase().contains(filterPattern)){
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

            public final TextView pmrTextNomorMaterial;
            public final TextView pmrTextJobCode;
            public final TextView pmrTextTglPermintaan;
            public final TextView pmrStatus;
            public final TextView pmrTextDibuat;
            public final TextView pmrTextApproval1;
            public final TextView pmrTextApproval2;
            public final TextView pmrTextApproval3;
            public final TextView pmrTextPriority;

            public final LinearLayout pmrLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pmrTextNomorMaterial = (TextView) view.findViewById(R.id.pmrTextNomorMaterial);
                pmrTextJobCode = (TextView) view.findViewById(R.id.pmrTextJobCode);
                pmrTextTglPermintaan = (TextView) view.findViewById(R.id.pmrTextTglPermintaan);
                pmrStatus = (TextView) view.findViewById(R.id.pmrStatus);
                pmrTextDibuat = (TextView) view.findViewById(R.id.pmrTextDibuat);
                pmrTextApproval1 = (TextView) view.findViewById(R.id.pmrTextApproval1);
                pmrTextApproval2 = (TextView) view.findViewById(R.id.pmrTextApproval2);
                pmrTextApproval3 = (TextView) view.findViewById(R.id.pmrTextApproval3);
                pmrTextPriority = (TextView) view.findViewById(R.id.pmrTextPriority);

                pmrLayoutList = (LinearLayout) view.findViewById(R.id.pmrLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final MaterialRequest materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailMaterialRequestActivity.class);
                        intent.putExtra("detail", materialRequest);
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
                param.put("feature", "" + "material-request");
                param.put("access", "" + "material-request:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
