package com.example.aismobile.Contact.Access;

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
import com.example.aismobile.Data.Contact.AccessRequest;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessFragment extends Fragment {

    public TextView arTextPaging;
    public EditText arEditSearch;
    public ImageView arBtnSearch;
    public RecyclerView arRecycler;
    public FloatingActionButton arFabAdd;
    public Spinner arSpinnerSearch;
    public Spinner arSpinnerSort;
    public Spinner arSpinnerSortAD;
    public Button arBtnShowList;
    public ImageButton arBtnBefore;
    public ImageButton arBtnNext;
    public LinearLayout arLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public AccessFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] ARSpinnerSearch = {"Semua Data", "Access Request Number", "Employee", "Request Date",
            "Notes", "Approval 1"};
    public String[] ARSpinnerSort = {"-- Sort By --", "Berdasarkan Contact Name", "Berdasarkan Company Name",
            "Berdasarkan Job Title", "Berdasarkan Email", "Berdasarkan Phone"};
    public String[] ARADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<AccessRequest> accessRequests;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public AccessFragment() {
        // Required empty public constructor
    }

    public static AccessFragment newInstance() {
        AccessFragment fragment = new AccessFragment();
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

        accessRequests = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_access, container, false);

        // Set the adapter
        arRecycler = (RecyclerView) view.findViewById(R.id.arRecycler);
        if (mColumnCount <= 1) {
            arRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            arRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        arFabAdd = (FloatingActionButton) view.findViewById(R.id.arFabAdd);
        arEditSearch = (EditText) view.findViewById(R.id.arEditSearch);
        arTextPaging = (TextView) view.findViewById(R.id.arTextPaging);
        arBtnSearch = (ImageView) view.findViewById(R.id.arBtnSearch);
        arSpinnerSearch = (Spinner) view.findViewById(R.id.arSpinnerSearch);
        arSpinnerSort = (Spinner) view.findViewById(R.id.arSpinnerSort);
        arSpinnerSortAD = (Spinner) view.findViewById(R.id.arSpinnerSortAD);
        arBtnShowList = (Button) view.findViewById(R.id.arBtnShowList);
        arBtnBefore = (ImageButton) view.findViewById(R.id.arBtnBefore);
        arBtnNext = (ImageButton) view.findViewById(R.id.arBtnNext);
        arLayoutPaging = (LinearLayout) view.findViewById(R.id.arLayoutPaging);

        arBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(arTextPaging.getText()));
                setSortHalf(arSpinnerSort.getSelectedItemPosition(), arSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(arTextPaging.getText()))+1;
                arTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        arBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(arTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(arTextPaging.getText()))-2);
                    setSortHalf(arSpinnerSort.getSelectedItemPosition(), arSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(arTextPaging.getText()))-1;
                    arTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        arBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("access_request_id DESC");
                    loadAll = true;
                    params = arLayoutPaging.getLayoutParams();
                    params.height = 0;
                    arLayoutPaging.setLayoutParams(params);
                    arBtnShowList.setText("Show Half");
                } else {
                    arTextPaging.setText("1");
                    counter = 0;
                    loadData("access_request_id DESC");
                    loadAll = false;
                    params = arLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    arLayoutPaging.setLayoutParams(params);
                    arBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ARSpinnerSearch);
        arSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ARSpinnerSort);
        arSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, ARADSpinnerSort);
        arSpinnerSortAD.setAdapter(spinnerAdapter);

        arSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, arSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, arSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arEditSearch.getText().toString().matches("")){
                    arSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(arEditSearch.getText()));
            }
        });

        loadData("access_request_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("access_request_number ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("access_request_number ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("employee_request ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("employee_request DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("request_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("request_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("request_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("request_date DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("approval1 ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("approval1 DESC");
        else loadDataAll("access_request_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("access_request_number ASC");
        else if (position == 1 && posAD == 1)
            loadData("access_request_number ASC");
        else if (position == 2 && posAD == 0)
            loadData("employee_request ASC");
        else if (position == 2 && posAD == 1)
            loadData("employee_request DESC");
        else if (position == 3 && posAD == 0)
            loadData("request_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("request_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("notes ASC");
        else if (position == 4 && posAD == 1)
            loadData("notes DESC");
        else if (position == 5 && posAD == 0)
            loadData("approval1 ASC");
        else if (position == 5 && posAD == 1)
            loadData("approval1 DESC");
        else loadData("access_request_id DESC");
    }

    private void setAdapterList(){
        adapter = new AccessFragment.MyRecyclerViewAdapter(accessRequests, mListener);
        arRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        arRecycler.setAdapter(null);
        accessRequests.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_REQUEST_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            accessRequests.add(new AccessRequest(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (arEditSearch.getText().toString().matches("")){
                                arSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(arEditSearch.getText()));
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
        arRecycler.setAdapter(null);
        accessRequests.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_REQUEST_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            accessRequests.add(new AccessRequest(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (arEditSearch.getText().toString().matches("")){
                                arSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(arEditSearch.getText()));
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
        void onListFragmentInteraction(AccessRequest item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<AccessRequest> mValues;
        private final List<AccessRequest> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<AccessRequest> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_access_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.arTextNumber.setText(""+mValues.get(position).getAccess_request_number());
            holder.arTextEmployee.setText(""+mValues.get(position).getEmployee_request());
            holder.arTextReqDate.setText(""+mValues.get(position).getRequest_date());
            holder.arTextNote.setText(""+mValues.get(position).getNotes());
            holder.arTextApproval1.setText(""+mValues.get(position).getApproval1());

            if (position%2==0)
                holder.arLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.arLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<AccessRequest> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((AccessRequest) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (AccessRequest item : values){
                        if (arSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getAccess_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_request().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRequest_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getApproval1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (arSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getAccess_request_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (arSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee_request().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (arSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getRequest_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (arSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (arSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getApproval1().toLowerCase().contains(filterPattern)){
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

            public final TextView arTextNumber;
            public final TextView arTextEmployee;
            public final TextView arTextReqDate;
            public final TextView arTextNote;
            public final TextView arTextApproval1;

            public final LinearLayout arLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                arTextNumber = (TextView) view.findViewById(R.id.arTextNumber);
                arTextEmployee = (TextView) view.findViewById(R.id.arTextEmployee);
                arTextReqDate = (TextView) view.findViewById(R.id.arTextReqDate);
                arTextNote = (TextView) view.findViewById(R.id.arTextNote);
                arTextApproval1 = (TextView) view.findViewById(R.id.arTextApproval1);

                arLayoutList = (LinearLayout) view.findViewById(R.id.arLayoutList);
            }
        }
    }
}
