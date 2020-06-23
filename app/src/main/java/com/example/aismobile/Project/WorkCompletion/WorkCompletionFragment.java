package com.example.aismobile.Project.WorkCompletion;

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
import com.example.aismobile.Data.WorkCompletion;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkCompletionFragment extends Fragment {


    public TextView pwcTextPaging;
    public EditText pwcEditSearch;
    public ImageView pwcBtnSearch;
    public RecyclerView pwcRecycler;
    public FloatingActionButton pwcFabAdd;
    public Spinner pwcSpinnerSearch;
    public Spinner pwcSpinnerSort;
    public Button pwcBtnShowList;
    public ImageButton pwcBtnBefore;
    public ImageButton pwcBtnNext;
    public LinearLayout pwcLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public WorkCompletionFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] WCSpinnerSearch = {"Semua Data", "Work Completion", "Job Order", "SQ Number", "Start Work", "End Work", "Diterima Oleh", "Dibuat Oleh"};
    public String[] WCSpinnerSort = {"Berdasarkan Work Completion", "Berdasarkan Job Order", "Berdasarkan SQ Number", "Berdasarkan Start Work", "Berdasarkan End Work", "Berdasarkan Diterima Oleh", "Berdasarkan Dibuat Oleh"};
    public boolean loadAll = false;
    public List<WorkCompletion> workCompletions;
    public int counter = 0;
    public ViewGroup.LayoutParams params;

    public WorkCompletionFragment() {
    }

    public static WorkCompletionFragment newInstance() {
        WorkCompletionFragment fragment = new WorkCompletionFragment();
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

        workCompletions = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_completion, container, false);


        // Set the adapter
        pwcRecycler = (RecyclerView) view.findViewById(R.id.pwcRecycler);
        if (mColumnCount <= 1) {
            pwcRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            pwcRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        pwcFabAdd = (FloatingActionButton) view.findViewById(R.id.pwcFabAdd);
        pwcEditSearch = (EditText) view.findViewById(R.id.pwcEditSearch);
        pwcTextPaging = (TextView) view.findViewById(R.id.pwcTextPaging);
        pwcBtnSearch = (ImageView) view.findViewById(R.id.pwcBtnSearch);
        pwcSpinnerSearch = (Spinner) view.findViewById(R.id.pwcSpinnerSearch);
        pwcSpinnerSort = (Spinner) view.findViewById(R.id.pwcSpinnerSort);
        pwcBtnShowList = (Button) view.findViewById(R.id.pwcBtnShowList);
        pwcBtnBefore = (ImageButton) view.findViewById(R.id.pwcBtnBefore);
        pwcBtnNext = (ImageButton) view.findViewById(R.id.pwcBtnNext);
        pwcLayoutPaging = (LinearLayout) view.findViewById(R.id.pwcLayoutPaging);

        pwcBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(pwcTextPaging.getText()));
                loadWorkCompletion();
                int textValue = Integer.valueOf(String.valueOf(pwcTextPaging.getText()))+1;
                pwcTextPaging.setText(""+textValue);
            }
        });
        pwcBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(pwcTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(pwcTextPaging.getText()))-2);
                    loadWorkCompletion();
                    int textValue = Integer.valueOf(String.valueOf(pwcTextPaging.getText()))-1;
                    pwcTextPaging.setText(""+textValue);
                }
            }
        });

        pwcBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    pwcRecycler.setAdapter(null);
                    workCompletions.clear();
                    loadWorkCompletionAll();
                    loadAll = true;
                    params = pwcLayoutPaging.getLayoutParams();
                    params.height = 0;
                    pwcLayoutPaging.setLayoutParams(params);
                    params = pwcSpinnerSort.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pwcSpinnerSort.setLayoutParams(params);
                    pwcBtnShowList.setText("Show Half");
                } else {
                    pwcTextPaging.setText("1");
                    counter = 0;
                    pwcRecycler.setAdapter(null);
                    workCompletions.clear();
                    loadWorkCompletion();
                    loadAll = false;
                    params = pwcLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    pwcLayoutPaging.setLayoutParams(params);
                    params = pwcSpinnerSort.getLayoutParams();
                    params.height = 0;;
                    pwcSpinnerSort.setLayoutParams(params);
                    pwcBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WCSpinnerSearch);
        pwcSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, WCSpinnerSort);
        pwcSpinnerSort.setAdapter(spinnerAdapter);

        pwcSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    Collections.sort(workCompletions, new ComparatorJobProgressReportNumber());
                else if (position == 1)
                    Collections.sort(workCompletions, new ComparatorJobOrder());
                else if (position == 2)
                    Collections.sort(workCompletions, new ComparatorSQNumber());
                else if (position == 3)
                    Collections.sort(workCompletions, new ComparatorStart());
                else if (position == 4)
                    Collections.sort(workCompletions, new ComparatorEnd());
                else if (position == 5)
                    Collections.sort(workCompletions, new ComparatorDiterima());
                else if (position == 6)
                    Collections.sort(workCompletions, new ComparatorDibuat());

                setAdapterList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pwcBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwcEditSearch.getText().toString().matches("")){
                    pwcSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(pwcEditSearch.getText()));
            }
        });

        loadWorkCompletion();

        return view;
    }

    private void setAdapterList(){
        adapter = new WorkCompletionFragment.MyRecyclerViewAdapter(workCompletions, mListener);
        pwcRecycler.setAdapter(adapter);
    }

    private void loadWorkCompletionAll() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_COMPLETION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workCompletions.add(new WorkCompletion(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
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
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public void loadWorkCompletion(){
        progressDialog.show();
        pwcRecycler.setAdapter(null);
        workCompletions.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_COMPLETION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workCompletions.add(new WorkCompletion(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
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
        void onListFragmentInteraction(WorkCompletion item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<WorkCompletion> mValues;
        private final List<WorkCompletion> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<WorkCompletion> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_completion_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pwcTextWorkCompletion.setText(""+mValues.get(position).getJob_progress_report_number());
            holder.pwcTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.pwcTextKeterangan.setText(""+mValues.get(position).getDescription());
            holder.pwcSqNumber.setText(""+mValues.get(position).getSales_quotation_id());
            holder.pwcTextStart.setText(""+mValues.get(position).getStart_work());
            holder.pwcTextEnd.setText(""+mValues.get(position).getEnd_work());
            holder.pwcTextDiterima.setText(""+mValues.get(position).getAccepted_by());
            holder.pwcTextDibuat.setText(""+mValues.get(position).getCreated_by());

            if (position%2==0)
                holder.pwcLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pwcLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<WorkCompletion> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((WorkCompletion) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (WorkCompletion item : values){
                        if (pwcSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getJob_progress_report_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSales_quotation_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStart_work().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEnd_work().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAccepted_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getJob_progress_report_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJob_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getSales_quotation_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getStart_work().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getEnd_work().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getAccepted_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (pwcSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
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

            public final TextView pwcTextWorkCompletion;
            public final TextView pwcTextJobOrder;
            public final TextView pwcTextKeterangan;
            public final TextView pwcSqNumber;
            public final TextView pwcTextStart;
            public final TextView pwcTextEnd;
            public final TextView pwcTextDiterima;
            public final TextView pwcTextDibuat;

            public final LinearLayout pwcLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pwcTextWorkCompletion = (TextView) view.findViewById(R.id.pwcTextWorkCompletion);
                pwcTextJobOrder = (TextView) view.findViewById(R.id.pwcTextJobOrder);
                pwcTextKeterangan = (TextView) view.findViewById(R.id.pwcTextKeterangan);
                pwcSqNumber = (TextView) view.findViewById(R.id.pwcSqNumber);
                pwcTextStart = (TextView) view.findViewById(R.id.pwcTextStart);
                pwcTextEnd = (TextView) view.findViewById(R.id.pwcTextEnd);
                pwcTextDiterima = (TextView) view.findViewById(R.id.pwcTextDiterima);
                pwcTextDibuat = (TextView) view.findViewById(R.id.pwcTextDibuat);

                pwcLayoutList = (LinearLayout) view.findViewById(R.id.pwcLayoutList);
            }
        }
    }

    private class ComparatorJobProgressReportNumber implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getJob_progress_report_number().compareTo(o2.getJob_progress_report_number());
        }
    }
    private class ComparatorJobOrder implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getJob_order_id().compareTo(o2.getJob_order_id());
        }
    }
    private class ComparatorSQNumber implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getSales_quotation_id().compareTo(o2.getSales_quotation_id());
        }
    }
    private class ComparatorStart implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getStart_work().compareTo(o2.getStart_work());
        }
    }
    private class ComparatorEnd implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getEnd_work().compareTo(o2.getEnd_work());
        }
    }
    private class ComparatorDiterima implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getAccepted_by().compareTo(o2.getAccepted_by());
        }
    }
    private class ComparatorDibuat implements Comparator<WorkCompletion> {

        @Override
        public int compare(WorkCompletion o1, WorkCompletion o2) {
            return o1.getCreated_by().compareTo(o2.getCreated_by());
        }
    }
}
