package com.example.aismobile.Project.Pengambilan;

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
import com.example.aismobile.Data.Pickup;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PengambilanFragment extends Fragment {

    public TextView ppTextPaging;
    public EditText ppEditSearch;
    public ImageView ppBtnSearch;
    public RecyclerView ppRecycler;
    public FloatingActionButton ppFabAdd;
    public Spinner ppSpinnerSearch;
    public Spinner ppSpinnerSort;
    public Button ppBtnShowList;
    public ImageButton ppBtnBefore;
    public ImageButton ppBtnNext;
    public LinearLayout ppLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public PengambilanFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] PSpinnerSearch = {"Semua Data", "Nomor Pengambilan", "Material Request", "Dibuat Oleh",
            "Diambil Oleh", "Diakui Oleh", "Tanggal Pengambilan", "Catatan", "Diakui"};
    public String[] PSpinnerSort = {"Berdasarkan Nomor Pengambilan ASC", "Berdasarkan Nomor Pengambilan DESC",
            "Berdasarkan Material Request ASC", "Berdasarkan Material Request DESC", "Berdasarkan Dibuat Oleh ASC",
            "Berdasarkan Dibuat Oleh DESC", "Berdasarkan Diambil Oleh ASC", "Berdasarkan Diambil Oleh DESC",
            "Berdasarkan Diakui Oleh ASC", "Berdasarkan Diakui Oleh DESC", "Berdasarkan Tanggal Pengambilan ASC",
            "Berdasarkan Tanggal Pengambilan DESC", "Berdasarkan Catatan ASC", "Berdasarkan Catatan DESC",
            "Berdasarkan Diakui ASC", "Berdasarkan Diakui DESC"};
    public boolean loadAll = false;
    public List<Pickup> pickups;
    public int counter = 0;
    public ViewGroup.LayoutParams params;

    public PengambilanFragment() {
        // Required empty public constructor
    }

    public static PengambilanFragment newInstance() {
        PengambilanFragment fragment = new PengambilanFragment();
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

        pickups = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengambilan, container, false);

        // Set the adapter
        ppRecycler = (RecyclerView) view.findViewById(R.id.ppRecycler);
        if (mColumnCount <= 1) {
            ppRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            ppRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        ppFabAdd = (FloatingActionButton) view.findViewById(R.id.ppFabAdd);
        ppEditSearch = (EditText) view.findViewById(R.id.ppEditSearch);
        ppTextPaging = (TextView) view.findViewById(R.id.ppTextPaging);
        ppBtnSearch = (ImageView) view.findViewById(R.id.ppBtnSearch);
        ppSpinnerSearch = (Spinner) view.findViewById(R.id.ppSpinnerSearch);
        ppSpinnerSort = (Spinner) view.findViewById(R.id.ppSpinnerSort);
        ppBtnShowList = (Button) view.findViewById(R.id.ppBtnShowList);
        ppBtnBefore = (ImageButton) view.findViewById(R.id.ppBtnBefore);
        ppBtnNext = (ImageButton) view.findViewById(R.id.ppBtnNext);
        ppLayoutPaging = (LinearLayout) view.findViewById(R.id.ppLayoutPaging);

        ppBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(ppTextPaging.getText()));
                setSortHalf(ppSpinnerSort.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(ppTextPaging.getText()))+1;
                ppTextPaging.setText(""+textValue);
            }
        });
        ppBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(ppTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(ppTextPaging.getText()))-2);
                    setSortHalf(ppSpinnerSort.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(ppTextPaging.getText()))-1;
                    ppTextPaging.setText(""+textValue);
                }
            }
        });

        ppBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("pickup_number DESC");
                    loadAll = true;
                    params = ppLayoutPaging.getLayoutParams();
                    params.height = 0;
                    ppLayoutPaging.setLayoutParams(params);
                    ppBtnShowList.setText("Show Half");
                } else {
                    ppTextPaging.setText("1");
                    counter = 0;
                    loadData("pickup_number DESC");
                    loadAll = false;
                    params = ppLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    ppLayoutPaging.setLayoutParams(params);
                    ppBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSearch);
        ppSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, PSpinnerSort);
        ppSpinnerSort.setAdapter(spinnerAdapter);

        ppSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position);
                } else {
                    setSortHalf(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ppBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ppEditSearch.getText().toString().matches("")){
                    ppSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(ppEditSearch.getText()));
            }
        });

        loadData("pickup_number DESC");

        return view;
    }

    private void setSortAll(int position){
        if (position == 0)
            loadDataAll("pickup_number ASC");
        else if (position == 1)
            loadDataAll("pickup_number DESC");
        else if (position == 2)
            loadDataAll("material_request_number ASC");
        else if (position == 3)
            loadDataAll("material_request_number DESC");
        else if (position == 4)
            loadDataAll("created_by ASC");
        else if (position == 5)
            loadDataAll("created_by DESC");
        else if (position == 6)
            loadDataAll("pickup_by ASC");
        else if (position == 7)
            loadDataAll("pickup_by DESC");
        else if (position == 8)
            loadDataAll("recognized_by ASC");
        else if (position == 9)
            loadDataAll("recognized_by DESC");
        else if (position == 10)
            loadDataAll("taken_date ASC");
        else if (position == 11)
            loadDataAll("taken_date DESC");
        else if (position == 12)
            loadDataAll("notes ASC");
        else if (position == 13)
            loadDataAll("notes DESC");
        else if (position == 14)
            loadDataAll("recognized ASC");
        else if (position == 15)
            loadDataAll("recognized DESC");
        else loadDataAll("pickup_number DESC");
    }

    private void setSortHalf(int position){
        if (position == 0)
            loadData("pickup_number ASC");
        else if (position == 1)
            loadData("pickup_number DESC");
        else if (position == 2)
            loadData("material_request_number ASC");
        else if (position == 3)
            loadData("material_request_number DESC");
        else if (position == 4)
            loadData("created_by ASC");
        else if (position == 5)
            loadData("created_by DESC");
        else if (position == 6)
            loadData("pickup_by ASC");
        else if (position == 7)
            loadData("pickup_by DESC");
        else if (position == 8)
            loadData("recognized_by ASC");
        else if (position == 9)
            loadData("recognized_by DESC");
        else if (position == 10)
            loadData("taken_date ASC");
        else if (position == 11)
            loadData("taken_date DESC");
        else if (position == 12)
            loadData("notes ASC");
        else if (position == 13)
            loadData("notes DESC");
        else if (position == 14)
            loadData("recognized ASC");
        else if (position == 15)
            loadData("recognized DESC");
        else loadData("pickup_number DESC");
    }

    private void setAdapterList(){
        adapter = new PengambilanFragment.MyRecyclerViewAdapter(pickups, mListener);
        ppRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        ppRecycler.setAdapter(null);
        pickups.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PICKUP_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            pickups.add(new Pickup(jsonArray.getJSONObject(i)));
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
                param.put("sortBy", "" + sortBy);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    public void loadData(final String sortBy){
        progressDialog.show();
        ppRecycler.setAdapter(null);
        pickups.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PICKUP_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            pickups.add(new Pickup(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(Pickup item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Pickup> mValues;
        private final List<Pickup> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Pickup> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_pengambilan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ppTextNomorPengambilan.setText(""+mValues.get(position).getPickup_number());
            holder.ppTextMaterialReq.setText(""+mValues.get(position).getMaterial_request_id());
            holder.ppTextDibuatOleh.setText(""+mValues.get(position).getCreated_by());
            holder.ppTextDiambilOleh.setText(""+mValues.get(position).getPickup_by());
            holder.ppTextDiakuiOleh.setText(""+mValues.get(position).getRecognized_by());
            holder.ppTextTglPengambilan.setText(""+mValues.get(position).getTaken_date());
            holder.ppTextCatatan.setText(""+mValues.get(position).getNotes());
            holder.ppTextDiakui.setText(""+mValues.get(position).getRecognized());

            if (position%2==0)
                holder.ppLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ppLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<Pickup> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Pickup) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Pickup item : values){
                        if (ppSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getPickup_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getMaterial_request_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPickup_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRecognized_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getTaken_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRecognized().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getPickup_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getMaterial_request_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getPickup_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getRecognized_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getTaken_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getNotes().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (ppSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getRecognized().toLowerCase().contains(filterPattern)){
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

            public final TextView ppTextNomorPengambilan;
            public final TextView ppTextMaterialReq;
            public final TextView ppTextDibuatOleh;
            public final TextView ppTextDiambilOleh;
            public final TextView ppTextDiakuiOleh;
            public final TextView ppTextTglPengambilan;
            public final TextView ppTextCatatan;
            public final TextView ppTextDiakui;

            public final LinearLayout ppLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ppTextNomorPengambilan = (TextView) view.findViewById(R.id.ppTextNomorPengambilan);
                ppTextMaterialReq = (TextView) view.findViewById(R.id.ppTextMaterialReq);
                ppTextDibuatOleh = (TextView) view.findViewById(R.id.ppTextDibuatOleh);
                ppTextDiambilOleh = (TextView) view.findViewById(R.id.ppTextDiambilOleh);
                ppTextDiakuiOleh = (TextView) view.findViewById(R.id.ppTextDiakuiOleh);
                ppTextTglPengambilan = (TextView) view.findViewById(R.id.ppTextTglPengambilan);
                ppTextCatatan = (TextView) view.findViewById(R.id.ppTextCatatan);
                ppTextDiakui = (TextView) view.findViewById(R.id.ppTextDiakui);

                ppLayoutList = (LinearLayout) view.findViewById(R.id.ppLayoutList);
            }
        }
    }
}
