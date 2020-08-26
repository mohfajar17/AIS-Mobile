package com.example.aismobile.Inventory.Aset;

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
import com.example.aismobile.Data.Inventory.Asset;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsetFragment extends Fragment {

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
    public AsetFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Kode Aset", "Nama Aset", "Model Aset", "Location", "Jumlah",
            "Keterangan", "Status"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Kode Aset", "Berdasarkan Nama Aset",
            "Berdasarkan Model Aset", "Berdasarkan Location", "Berdasarkan Jumlah", "Berdasarkan Keterangan",
            "Berdasarkan Status"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Asset> assets;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public AsetFragment() {
    }

    public static AsetFragment newInstance() {
        AsetFragment fragment = new AsetFragment();
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

        assets = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aset, container, false);

        // Set list adapter
        recycler = (RecyclerView) view.findViewById(R.id.aRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.aFabAdd);
        editSearch = (EditText) view.findViewById(R.id.aEditSearch);
        textPaging = (TextView) view.findViewById(R.id.aTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.aBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.aSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.aSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.aSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.aBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.aBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.aBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.aLayoutPaging);

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
                    loadDataAll("asset_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("asset_id DESC");
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

//        loadData("asset_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("asset_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("asset_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("asset_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("asset_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("short_description ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("short_description DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("location ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("location DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("quantity ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("quantity DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("remark ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("remark DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("status ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("status DESC");
        else loadDataAll("asset_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("asset_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("asset_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("asset_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("asset_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("short_description ASC");
        else if (position == 3 && posAD == 1)
            loadData("short_description DESC");
        else if (position == 4 && posAD == 0)
            loadData("location ASC");
        else if (position == 4 && posAD == 1)
            loadData("location DESC");
        else if (position == 5 && posAD == 0)
            loadData("quantity ASC");
        else if (position == 5 && posAD == 1)
            loadData("quantity DESC");
        else if (position == 6 && posAD == 0)
            loadData("remark ASC");
        else if (position == 6 && posAD == 1)
            loadData("remark DESC");
        else if (position == 7 && posAD == 0)
            loadData("status ASC");
        else if (position == 7 && posAD == 1)
            loadData("status DESC");
        else loadData("asset_id DESC");
    }

    private void setAdapterList(){
        adapter = new AsetFragment.MyRecyclerViewAdapter(assets, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        assets.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ASET_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            assets.add(new Asset(jsonArray.getJSONObject(i)));
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

    public void loadData(final String sortBy){
        progressDialog.show();
        recycler.setAdapter(null);
        assets.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ASET_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            assets.add(new Asset(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(Asset item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Asset> mValues;
        private final List<Asset> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Asset> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_aset_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.aTextCode.setText(""+mValues.get(position).getAsset_code());
            holder.aTextName.setText(""+mValues.get(position).getAsset_name());
            holder.aTextDescription.setText(""+mValues.get(position).getShort_description());
            holder.aTextLocation.setText(""+mValues.get(position).getLocation());
            holder.aTextQuantity.setText(""+mValues.get(position).getQuantity());
            holder.aTextRemark.setText(""+mValues.get(position).getRemark());
            holder.aTextStatus.setText(""+mValues.get(position).getStatus());

            if (position%2==0)
                holder.aLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.aLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<Asset> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Asset) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Asset item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getAsset_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getAsset_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getShort_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getLocation().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getQuantity().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRemark().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStatus().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getAsset_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getAsset_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getShort_description().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getLocation().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getQuantity().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getRemark().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
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

            public final TextView aTextCode;
            public final TextView aTextName;
            public final TextView aTextDescription;
            public final TextView aTextLocation;
            public final TextView aTextQuantity;
            public final TextView aTextRemark;
            public final TextView aTextStatus;

            public final LinearLayout aLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                aTextCode = (TextView) view.findViewById(R.id.aTextCode);
                aTextName = (TextView) view.findViewById(R.id.aTextName);
                aTextDescription = (TextView) view.findViewById(R.id.aTextDescription);
                aTextLocation = (TextView) view.findViewById(R.id.aTextLocation);
                aTextQuantity = (TextView) view.findViewById(R.id.aTextQuantity);
                aTextRemark = (TextView) view.findViewById(R.id.aTextRemark);
                aTextStatus = (TextView) view.findViewById(R.id.aTextStatus);

                aLayoutList = (LinearLayout) view.findViewById(R.id.aLayoutList);
            }
        }
    }
}