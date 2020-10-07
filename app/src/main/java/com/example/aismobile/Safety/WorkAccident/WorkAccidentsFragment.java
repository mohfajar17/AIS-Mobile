package com.example.aismobile.Safety.WorkAccident;

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
import com.example.aismobile.Data.Safety.WorkAccident;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkAccidentsFragment extends Fragment {

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
    public WorkAccidentsFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Employee", "Employee Name", "Day", "Date", "Time",
            "Job Grade", "Company Workbase", "Accident Type"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Employee", "Berdasarkan Employee Name",
            "Berdasarkan Day", "Berdasarkan Date", "Berdasarkan Time", "Berdasarkan Job Grade", "Berdasarkan Company Workbase",
            "Berdasarkan Accident Type"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<WorkAccident> workAccidents;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public WorkAccidentsFragment() {
        // Required empty public constructor
    }

    public static WorkAccidentsFragment newInstance() {
        WorkAccidentsFragment fragment = new WorkAccidentsFragment();
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

        workAccidents = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_accidents, container, false);

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.waRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.soFabAdd);
        editSearch = (EditText) view.findViewById(R.id.waEditSearch);
        textPaging = (TextView) view.findViewById(R.id.waTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.waBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.waSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.waSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.waSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.waBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.waBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.waBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.waLayoutPaging);

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
                    loadDataAll("work_accident_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("work_accident_id DESC");
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

//        loadData("work_accident_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("work_accident_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("work_accident_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("short_description ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("short_description DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("sales_order_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("sales_order_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("due_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("due_date DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("status ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("status DESC");
        else loadDataAll("work_accident_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("work_accident_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("work_accident_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("short_description ASC");
        else if (position == 2 && posAD == 1)
            loadData("short_description DESC");
        else if (position == 3 && posAD == 0)
            loadData("sales_order_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("sales_order_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("due_date ASC");
        else if (position == 4 && posAD == 1)
            loadData("due_date DESC");
        else if (position == 5 && posAD == 0)
            loadData("status ASC");
        else if (position == 5 && posAD == 1)
            loadData("status DESC");
        else loadData("work_accident_id DESC");
    }

    private void setAdapterList(){
        adapter = new WorkAccidentsFragment.MyRecyclerViewAdapter(workAccidents, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        workAccidents.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_ACCIDENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workAccidents.add(new WorkAccident(jsonArray.getJSONObject(i)));
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
        workAccidents.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_ACCIDENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workAccidents.add(new WorkAccident(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(WorkAccident item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<WorkAccident> mValues;
        private final List<WorkAccident> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<WorkAccident> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_accident_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.waTextEmployee.setText(""+mValues.get(position).getEmployee_id());
            holder.waTextEmployeeName.setText(""+mValues.get(position).getEmployee_name());
            holder.waTextDay.setText(""+mValues.get(position).getDay_accident());
            holder.waTextDate.setText(""+mValues.get(position).getDate_accident());
            holder.waTextTime.setText(""+mValues.get(position).getTime_accident());
            holder.waTextJobGrade.setText(""+mValues.get(position).getJob_grade_id());
            holder.waTextWorkbase.setText(""+mValues.get(position).getCompany_workbase_id());
            holder.waTextType.setText(""+mValues.get(position).getAccident_type());

            if (position%2==0)
                holder.soLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.soLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailWorkAccidentActivity.class);
                    intent.putExtra("detail", mValues.get(position));
                    holder.itemView.getContext().startActivity(intent);
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
                List<WorkAccident> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((WorkAccident) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (WorkAccident item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getEmployee_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmployee_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDay_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDate_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTime_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_grade_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_workbase_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAccident_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getEmployee_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getEmployee_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getDay_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getDate_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getTime_accident().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getJob_grade_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getCompany_workbase_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getAccident_type().toLowerCase().contains(filterPattern)){
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

            public final TextView waTextEmployee;
            public final TextView waTextEmployeeName;
            public final TextView waTextDay;
            public final TextView waTextDate;
            public final TextView waTextTime;
            public final TextView waTextJobGrade;
            public final TextView waTextWorkbase;
            public final TextView waTextType;

            public final LinearLayout soLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                waTextEmployee = (TextView) view.findViewById(R.id.waTextEmployee);
                waTextEmployeeName = (TextView) view.findViewById(R.id.waTextEmployeeName);
                waTextDay = (TextView) view.findViewById(R.id.waTextDay);
                waTextDate = (TextView) view.findViewById(R.id.waTextDate);
                waTextTime = (TextView) view.findViewById(R.id.waTextTime);
                waTextJobGrade = (TextView) view.findViewById(R.id.waTextJobGrade);
                waTextWorkbase = (TextView) view.findViewById(R.id.waTextWorkbase);
                waTextType = (TextView) view.findViewById(R.id.waTextType);

                soLayoutList = (LinearLayout) view.findViewById(R.id.waLayoutList);
            }
        }
    }
}
