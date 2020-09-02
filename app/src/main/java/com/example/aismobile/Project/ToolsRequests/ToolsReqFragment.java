package com.example.aismobile.Project.ToolsRequests;

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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.aismobile.Data.Project.ResourcesRequest;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsReqFragment extends Fragment {

    public EditText editSearch;
    public ImageView btnSearch;
    public FloatingActionButton fabAdd;
    public Spinner spinnerSearch;
    public Spinner spinnerSort;
    public Spinner spinnerSortAD;
    public RecyclerView recycler;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public ToolsReqFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] PSpinnerSearch = {"Semua Data", "Nomor Tools Request", "Job Code", "Kontak", "Dibuat Oleh",
            "Awal Penggunaan", "Akhir Penggunaan", "Versi"};
    public String[] PSpinnerSort = {"-- Sort By --", "Berdasarkan Nomor Tools Request", "Berdasarkan Job Code",
            "Berdasarkan Kontak", "Berdasarkan Dibuat Oleh", "Berdasarkan Awal Penggunaan", "Berdasarkan Akhir Penggunaan",
            "Berdasarkan Versi"};
    public String[] PSADpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<ResourcesRequest> resourcesRequests;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter;

    public ToolsReqFragment() {
    }

    public static ToolsReqFragment newInstance() {
        ToolsReqFragment fragment = new ToolsReqFragment();
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

        resourcesRequests = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools_req, container, false);

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.ptrRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.ptrFabAdd);
        editSearch = (EditText) view.findViewById(R.id.ptrEditSearch);
        btnSearch = (ImageView) view.findViewById(R.id.ptrBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.ptrSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.ptrSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.ptrSpinnerSortAD);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSearch);
        spinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSort);
        spinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSADpinnerSort);
        spinnerSortAD.setAdapter(spinnerAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSortAll(position, spinnerSortAD.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearch.getText().toString().matches("")){
                    spinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadDataAll("resources_request_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("resources_request_number ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("resources_request_number DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("job_order_number ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("job_order_number DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("contact_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("contact_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("begin_date ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("begin_date DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("end_date ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("end_date DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("version ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("version DESC");
        else loadDataAll("resources_request_id DESC");
    }

    private void setAdapterList(){
        adapter = new ToolsReqFragment.MyRecyclerViewAdapter(resourcesRequests, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        resourcesRequests.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_TOOLS_REQUEST_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            resourcesRequests.add(new ResourcesRequest(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                                spinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
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
        void onListFragmentInteraction(ResourcesRequest item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<ResourcesRequest> mValues;
        private final List<ResourcesRequest> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<ResourcesRequest> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_tools_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ptrTextNomor.setText(""+mValues.get(position).getResources_request_number());
            holder.ptrTextJobCode.setText(""+mValues.get(position).getJob_order_number());
            holder.ptrTextKontak.setText(""+mValues.get(position).getContact_id());
            holder.ptrTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.ptrTextAwalPenggunaan.setText(""+mValues.get(position).getBegin_date());
            holder.ptrTextAkhirPenggunaan.setText(""+mValues.get(position).getEnd_date());
            holder.ptrTextVersi.setText(""+mValues.get(position).getVersion());

            if (position%2==0)
                holder.layoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<ResourcesRequest> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((ResourcesRequest) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ResourcesRequest item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getResources_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getVersion().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getResources_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getBegin_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getEnd_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getVersion().toLowerCase().contains(filterPattern)){
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

            public final TextView ptrTextNomor;
            public final TextView ptrTextJobCode;
            public final TextView ptrTextKontak;
            public final TextView ptrTextDibuat;
            public final TextView ptrTextAwalPenggunaan;
            public final TextView ptrTextAkhirPenggunaan;
            public final TextView ptrTextVersi;

            public final LinearLayout layoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ptrTextNomor = (TextView) view.findViewById(R.id.ptrTextNomor);
                ptrTextJobCode = (TextView) view.findViewById(R.id.ptrTextJobCode);
                ptrTextKontak = (TextView) view.findViewById(R.id.ptrTextKontak);
                ptrTextDibuat = (TextView) view.findViewById(R.id.ptrTextDibuat);
                ptrTextAwalPenggunaan = (TextView) view.findViewById(R.id.ptrTextAwalPenggunaan);
                ptrTextAkhirPenggunaan = (TextView) view.findViewById(R.id.ptrTextAkhirPenggunaan);
                ptrTextVersi = (TextView) view.findViewById(R.id.ptrTextVersi);

                layoutList = (LinearLayout) view.findViewById(R.id.layoutList);
            }
        }
    }
}
