package com.example.aismobile.Crm.Lead;

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
import com.example.aismobile.Data.CRM.Lead;
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

public class LeadsFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

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
    public LeadsFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Lead Name", "Lead Phone", "Lead Email", "Person",
            "Position", "Personal Phone", "Status"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Lead Name", "Berdasarkan Lead Phone",
            "Berdasarkan Lead Email", "Berdasarkan Person", "Berdasarkan Position", "Berdasarkan Personal Phone",
            "Berdasarkan Status"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Lead> leads;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public LeadsFragment() {
    }

    public static LeadsFragment newInstance() {
        LeadsFragment fragment = new LeadsFragment();
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

        leads = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leads, container, false);

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.lRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.lFabAdd);
        editSearch = (EditText) view.findViewById(R.id.lEditSearch);
        textPaging = (TextView) view.findViewById(R.id.lTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.lBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.lSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.lSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.lSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.lBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.lBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.lBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.lLayoutPaging);

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
                    loadDataAll("lead_name ASC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("lead_name ASC");
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
                    spinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("lead_name ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("lead_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("lead_name DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("lead_phone ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("lead_phone DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("lead_email ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("lead_email DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("person ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("person DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("position ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("position DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("personal_phone ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("personal_phone DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("status ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("status DESC");
        else loadDataAll("lead_name ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("lead_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("lead_name DESC");
        else if (position == 2 && posAD == 0)
            loadData("lead_phone ASC");
        else if (position == 2 && posAD == 1)
            loadData("lead_phone DESC");
        else if (position == 3 && posAD == 0)
            loadData("lead_email ASC");
        else if (position == 3 && posAD == 1)
            loadData("lead_email DESC");
        else if (position == 4 && posAD == 0)
            loadData("person ASC");
        else if (position == 4 && posAD == 1)
            loadData("person DESC");
        else if (position == 5 && posAD == 0)
            loadData("position ASC");
        else if (position == 5 && posAD == 1)
            loadData("position DESC");
        else if (position == 6 && posAD == 0)
            loadData("personal_phone ASC");
        else if (position == 6 && posAD == 1)
            loadData("personal_phone DESC");
        else if (position == 7 && posAD == 0)
            loadData("status ASC");
        else if (position == 7 && posAD == 1)
            loadData("status DESC");
        else loadData("lead_name ASC");
    }

    private void setAdapterList(){
        adapter = new LeadsFragment.MyRecyclerViewAdapter(leads, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        leads.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LEAD_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            leads.add(new Lead(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                                spinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
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
        leads.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LEAD_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            leads.add(new Lead(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (editSearch.getText().toString().matches("")){
                                spinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
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
        void onListFragmentInteraction(Lead item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Lead> mValues;
        private final List<Lead> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Lead> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_leads_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.lTextName.setText(""+mValues.get(position).getLead_name());
            holder.lTextPhone.setText(""+mValues.get(position).getLead_phone());
            holder.lTextEmail.setText(""+mValues.get(position).getLead_email());
            holder.lTextPerson.setText(""+mValues.get(position).getPerson());
            holder.lTextPosition.setText(""+mValues.get(position).getPosition());
            holder.lTextPersonalPhone.setText(""+mValues.get(position).getPersonal_phone());
            holder.lTextStatus.setText(""+mValues.get(position).getStatus());

            if (position%2==0)
                holder.lLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.lLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getLead_id(), mValues.get(position));
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
                List<Lead> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Lead) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Lead item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getLead_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLead_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLead_email().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPerson().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPosition().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPersonal_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStatus().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getLead_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getLead_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getLead_email().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPerson().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPosition().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPersonal_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getStatus().toLowerCase().contains(filterPattern)){
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

            public final TextView lTextName;
            public final TextView lTextPhone;
            public final TextView lTextEmail;
            public final TextView lTextPerson;
            public final TextView lTextPosition;
            public final TextView lTextPersonalPhone;
            public final TextView lTextStatus;

            public final LinearLayout lLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                lTextName = (TextView) view.findViewById(R.id.lTextName);
                lTextPhone = (TextView) view.findViewById(R.id.lTextPhone);
                lTextEmail = (TextView) view.findViewById(R.id.lTextEmail);
                lTextPerson = (TextView) view.findViewById(R.id.lTextPerson);
                lTextPosition = (TextView) view.findViewById(R.id.lTextPosition);
                lTextPersonalPhone = (TextView) view.findViewById(R.id.lTextPersonalPhone);
                lTextStatus = (TextView) view.findViewById(R.id.lTextStatus);

                lLayoutList = (LinearLayout) view.findViewById(R.id.lLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Lead lead) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailLeadsActivity.class);
                        intent.putExtra("detail", lead);
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
                param.put("feature", "" + "lead");
                param.put("access", "" + "lead:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
