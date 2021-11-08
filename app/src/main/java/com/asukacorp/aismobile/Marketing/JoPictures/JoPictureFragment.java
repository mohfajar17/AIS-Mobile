package com.asukacorp.aismobile.Marketing.JoPictures;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
import com.asukacorp.aismobile.Data.Marketing.JoPicture;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoPictureFragment extends Fragment {

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
    public JoPictureFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "JO Number", "JO Description", "Picture Description"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Tanggal Upload", "Berdasarkan Job Order Number"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<JoPicture> joPictures;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;
    private Dialog myDialog;

    public JoPictureFragment() { }

    public static JoPictureFragment newInstance() {
        JoPictureFragment fragment = new JoPictureFragment();
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

        myDialog = new Dialog(getContext());

        sharedPrefManager = new SharedPrefManager(getContext());

        joPictures = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jo_picture, container, false);

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.jopRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.jopFabAdd);
        editSearch = (EditText) view.findViewById(R.id.jopEditSearch);
        textPaging = (TextView) view.findViewById(R.id.jopTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.jopBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.jopSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.jopSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.jopSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.jopBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.jopBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.jopBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.jopLayoutPaging);

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
                    loadDataAll("job_order_picture_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("job_order_picture_id DESC");
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

//        loadData("sales_quotation_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("job_order_picture_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("job_order_picture_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("job_order_number ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("job_order_number DESC");
        else loadDataAll("job_order_picture_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("job_order_picture_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("job_order_picture_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("job_order_number ASC");
        else if (position == 2 && posAD == 1)
            loadData("job_order_number DESC");
        else loadData("job_order_picture_id DESC");
    }

    private void setAdapterList(){
        adapter = new JoPictureFragment.MyRecyclerViewAdapter(joPictures, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        joPictures.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_ORDER_PICTURE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joPictures.add(new JoPicture(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

//                        if (filter){
//                            if (editSearch.getText().toString().matches("")){
//                                spinnerSearch.setSelection(0);
//                                adapter.getFilter().filter("a");
//                            } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
//                            filter = false;
//                        }
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
        joPictures.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_ORDER_PICTURE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joPictures.add(new JoPicture(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(JoPicture item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<JoPicture> mValues;
        private final List<JoPicture> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<JoPicture> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_jo_picture_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.jopTextNumber.setText(""+mValues.get(position).getJob_order_number());
            holder.jopTextJoDeskripsi.setText(""+mValues.get(position).getJob_order_description());
            holder.jopTextDeskripsi.setText(""+mValues.get(position).getDescription());
            if (mValues.get(position).getFile_name().equals(""))
                holder.jopTextFile.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_image));
            Picasso.get().load(Config.DATA_URL_JO_PICTURE+mValues.get(position).getFile_name()).into(holder.jopTextFile);

            if (position%2==0)
                holder.jopLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.jopLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowPopup(mValues.get(position).getFile_name());
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
                List<JoPicture> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((JoPicture) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (JoPicture item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getJob_order_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDescription().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getJob_order_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getJob_order_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getDescription().toLowerCase().contains(filterPattern)){
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

            public final TextView jopTextNumber;
            public final TextView jopTextJoDeskripsi;
            public final TextView jopTextDeskripsi;
            public final ImageView jopTextFile;

            public final LinearLayout jopLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                jopTextNumber = (TextView) view.findViewById(R.id.jopTextNumber);
                jopTextJoDeskripsi = (TextView) view.findViewById(R.id.jopTextJoDeskripsi);
                jopTextDeskripsi = (TextView) view.findViewById(R.id.jopTextDeskripsi);
                jopTextFile = (ImageView) view.findViewById(R.id.jopTextFile);

                jopLayoutList = (LinearLayout) view.findViewById(R.id.jopLayoutList);
            }
        }
    }

    public void ShowPopup(String url) {
        ImageView imageNo;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageNo = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(Config.DATA_URL_JO_PICTURE+url).into(imageNo);
        if (imageNo.getDrawable() == null)
            imageNo.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}