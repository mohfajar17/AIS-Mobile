package com.asukacorp.aismobile.Crm.CustomerFeedback;

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
import com.asukacorp.aismobile.Data.CRM.CustomerFeedback;
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

public class CustomerFeedbackFragment extends Fragment {

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
    public CustomerFeedbackFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Feedback Number", "Feedback Date", "Feedback Subject",
            "Company Name", "Contact", "Contact Personal", "Marketing Aspect", "Feedback Category", "Feedback Status"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Feedback Number", "Berdasarkan Feedback Date",
            "Berdasarkan Feedback Subject", "Berdasarkan Company Name", "Berdasarkan Contact", "Berdasarkan Contact Personal",
            "Berdasarkan Marketing Aspect", "Berdasarkan Feedback Category", "Berdasarkan Feedback Status"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<CustomerFeedback> customerFeedbacks;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public CustomerFeedbackFragment() {
    }

    public static CustomerFeedbackFragment newInstance() {
        CustomerFeedbackFragment fragment = new CustomerFeedbackFragment();
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

        customerFeedbacks = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_feedback, container, false);

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.cfRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.cfFabAdd);
        editSearch = (EditText) view.findViewById(R.id.cfEditSearch);
        textPaging = (TextView) view.findViewById(R.id.cfTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.cfBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.cfSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.cfSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.cfSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.cfBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.cfBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.cfBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.cfLayoutPaging);

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
                    loadDataAll("feedback_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("feedback_id DESC");
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

//        loadData("feedback_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("feedback_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("feedback_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("feedback_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("feedback_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("feedback_subject ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("feedback_subject DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("company_name ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("company_name DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("contact_id ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("contact_id DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("contact_personal ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("contact_personal DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("marketing_aspect_id ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("marketing_aspect_id DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("feedback_category_id ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("feedback_category_id DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("feedback_status ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("feedback_status DESC");
        else loadDataAll("feedback_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("feedback_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("feedback_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("feedback_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("feedback_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("feedback_subject ASC");
        else if (position == 3 && posAD == 1)
            loadData("feedback_subject DESC");
        else if (position == 4 && posAD == 0)
            loadData("company_name ASC");
        else if (position == 4 && posAD == 1)
            loadData("company_name DESC");
        else if (position == 5 && posAD == 0)
            loadData("contact_id ASC");
        else if (position == 5 && posAD == 1)
            loadData("contact_id DESC");
        else if (position == 6 && posAD == 0)
            loadData("contact_personal ASC");
        else if (position == 6 && posAD == 1)
            loadData("contact_personal DESC");
        else if (position == 7 && posAD == 0)
            loadData("marketing_aspect_id ASC");
        else if (position == 7 && posAD == 1)
            loadData("marketing_aspect_id DESC");
        else if (position == 8 && posAD == 0)
            loadData("feedback_category_id ASC");
        else if (position == 8 && posAD == 1)
            loadData("feedback_category_id DESC");
        else if (position == 9 && posAD == 0)
            loadData("feedback_status ASC");
        else if (position == 9 && posAD == 1)
            loadData("feedback_status DESC");
        else loadData("feedback_id DESC");
    }

    private void setAdapterList(){
        adapter = new CustomerFeedbackFragment.MyRecyclerViewAdapter(customerFeedbacks, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        customerFeedbacks.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_FEEDBACK_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerFeedbacks.add(new CustomerFeedback(jsonArray.getJSONObject(i)));
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
        customerFeedbacks.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_FEEDBACK_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            customerFeedbacks.add(new CustomerFeedback(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(CustomerFeedback item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CustomerFeedback> mValues;
        private final List<CustomerFeedback> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<CustomerFeedback> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_customer_feedback_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.cfTextNumber.setText(""+mValues.get(position).getFeedback_number());
            holder.cfTextDate.setText(""+mValues.get(position).getFeedback_date());
            holder.cfTextSubjact.setText(""+mValues.get(position).getFeedback_subject());
            holder.cfTextCompanyName.setText(""+mValues.get(position).getCompany_name());
            holder.cfTextContact.setText(""+mValues.get(position).getContact_id());
            holder.cfTextContactPersonal.setText(""+mValues.get(position).getContact_personal());
            holder.cfTextMarketing.setText(""+mValues.get(position).getMarketing_aspect_id());
            holder.cfTextCategory.setText(""+mValues.get(position).getFeedback_category_id());
            holder.cfTextStatus.setText(""+mValues.get(position).getFeedback_status());

            if (position%2==0)
                holder.cfLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.cfLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getFeedback_id(), mValues.get(position));
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
                List<CustomerFeedback> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CustomerFeedback) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CustomerFeedback item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getFeedback_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFeedback_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFeedback_subject().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_personal().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getMarketing_aspect_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFeedback_category_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFeedback_status().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getFeedback_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getFeedback_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getFeedback_subject().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getContact_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getContact_personal().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getMarketing_aspect_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getFeedback_category_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==9){
                            if (item.getFeedback_status().toLowerCase().contains(filterPattern)){
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

            public final TextView cfTextNumber;
            public final TextView cfTextDate;
            public final TextView cfTextSubjact;
            public final TextView cfTextCompanyName;
            public final TextView cfTextContact;
            public final TextView cfTextContactPersonal;
            public final TextView cfTextMarketing;
            public final TextView cfTextCategory;
            public final TextView cfTextStatus;

            public final LinearLayout cfLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                cfTextNumber = (TextView) view.findViewById(R.id.cfTextNumber);
                cfTextDate = (TextView) view.findViewById(R.id.cfTextDate);
                cfTextSubjact = (TextView) view.findViewById(R.id.cfTextSubjact);
                cfTextCompanyName = (TextView) view.findViewById(R.id.cfTextCompanyName);
                cfTextContact = (TextView) view.findViewById(R.id.cfTextContact);
                cfTextContactPersonal = (TextView) view.findViewById(R.id.cfTextContactPersonal);
                cfTextMarketing = (TextView) view.findViewById(R.id.cfTextMarketing);
                cfTextCategory = (TextView) view.findViewById(R.id.cfTextCategory);
                cfTextStatus = (TextView) view.findViewById(R.id.cfTextStatus);

                cfLayoutList = (LinearLayout) view.findViewById(R.id.cfLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final CustomerFeedback customerFeedback) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailCustomerFeedbackActivity.class);
                        intent.putExtra("detail", customerFeedback);
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
                param.put("feature", "" + "customer-feedback");
                param.put("access", "" + "customer-feedback:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
