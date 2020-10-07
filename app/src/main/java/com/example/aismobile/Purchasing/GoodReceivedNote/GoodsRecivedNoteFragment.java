package com.example.aismobile.Purchasing.GoodReceivedNote;

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
import com.example.aismobile.Data.Purchasing.GoodReceivedNote;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsRecivedNoteFragment extends Fragment {

    public TextView grnTextPaging;
    public EditText grnEditSearch;
    public ImageView grnBtnSearch;
    public RecyclerView grnRecycler;
    public FloatingActionButton grnFabAdd;
    public Spinner grnSpinnerSearch;
    public Spinner grnSpinnerSort;
    public Spinner grnSpinnerSortAD;
    public Button grnBtnShowList;
    public ImageButton grnBtnBefore;
    public ImageButton grnBtnNext;
    public LinearLayout grnLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public GoodsRecivedNoteFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] GRNSpinnerSearch = {"Semua Data", "GRN Number", "Tanggal Penerimaan", "Purchase Order",
            "Dibuat Oleh", "Supplier", "Diakui"};
    public String[] GRNSpinnerSort = {"-- Sort By --", "Berdasarkan GRN Number", "Berdasarkan Tanggal Penerimaan",
            "Berdasarkan Purchase Order", "Berdasarkan Dibuat Oleh", "Berdasarkan Supplier", "Berdasarkan Diakui"};
    public String[] GRNADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<GoodReceivedNote> goodReceivedNotes;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public GoodsRecivedNoteFragment() {
    }

    public static GoodsRecivedNoteFragment newInstance() {
        GoodsRecivedNoteFragment fragment = new GoodsRecivedNoteFragment();
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

        goodReceivedNotes = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_recived_note, container, false);

        // Set the adapter
        grnRecycler = (RecyclerView) view.findViewById(R.id.grnRecycler);
        if (mColumnCount <= 1) {
            grnRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            grnRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        grnFabAdd = (FloatingActionButton) view.findViewById(R.id.grnFabAdd);
        grnEditSearch = (EditText) view.findViewById(R.id.grnEditSearch);
        grnTextPaging = (TextView) view.findViewById(R.id.grnTextPaging);
        grnBtnSearch = (ImageView) view.findViewById(R.id.grnBtnSearch);
        grnSpinnerSearch = (Spinner) view.findViewById(R.id.grnSpinnerSearch);
        grnSpinnerSort = (Spinner) view.findViewById(R.id.grnSpinnerSort);
        grnSpinnerSortAD = (Spinner) view.findViewById(R.id.grnSpinnerSortAD);
        grnBtnShowList = (Button) view.findViewById(R.id.grnBtnShowList);
        grnBtnBefore = (ImageButton) view.findViewById(R.id.grnBtnBefore);
        grnBtnNext = (ImageButton) view.findViewById(R.id.grnBtnNext);
        grnLayoutPaging = (LinearLayout) view.findViewById(R.id.grnLayoutPaging);

        grnBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(grnTextPaging.getText()));
                setSortHalf(grnSpinnerSort.getSelectedItemPosition(), grnSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(grnTextPaging.getText()))+1;
                grnTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        grnBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(grnTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(grnTextPaging.getText()))-2);
                    setSortHalf(grnSpinnerSort.getSelectedItemPosition(), grnSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(grnTextPaging.getText()))-1;
                    grnTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        grnBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("grn_id DESC");
                    loadAll = true;
                    params = grnLayoutPaging.getLayoutParams();
                    params.height = 0;
                    grnLayoutPaging.setLayoutParams(params);
                    grnBtnShowList.setText("Show Half");
                } else {
                    grnTextPaging.setText("1");
                    counter = 0;
                    loadData("grn_id DESC");
                    loadAll = false;
                    params = grnLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    grnLayoutPaging.setLayoutParams(params);
                    grnBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, GRNSpinnerSearch);
        grnSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, GRNSpinnerSort);
        grnSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, GRNADSpinnerSort);
        grnSpinnerSortAD.setAdapter(spinnerAdapter);

        grnSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, grnSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, grnSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        grnBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grnEditSearch.getText().toString().matches("")){
                    grnSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(grnEditSearch.getText()));
            }
        });

//        loadData("grn_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("grn_id ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("grn_id DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("purchase_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("purchase_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("supplier_name ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("supplier_name DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("recognized DESC");
        else loadDataAll("grn_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("grn_id ASC");
        else if (position == 1 && posAD == 1)
            loadData("grn_id DESC");
        else if (position == 2 && posAD == 0)
            loadData("receipt_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("receipt_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("purchase_order_id ASC");
        else if (position == 3 && posAD == 1)
            loadData("purchase_order_id DESC");
        else if (position == 4 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 4 && posAD == 1)
            loadData("created_by DESC");
        else if (position == 5 && posAD == 0)
            loadData("supplier_name ASC");
        else if (position == 5 && posAD == 1)
            loadData("supplier_name DESC");
        else if (position == 6 && posAD == 0)
            loadData("recognized ASC");
        else if (position == 6 && posAD == 1)
            loadData("recognized DESC");
        else loadData("grn_id DESC");
    }

    private void setAdapterList(){
        adapter = new GoodsRecivedNoteFragment.MyRecyclerViewAdapter(goodReceivedNotes, mListener);
        grnRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        grnRecycler.setAdapter(null);
        goodReceivedNotes.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_GRN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            goodReceivedNotes.add(new GoodReceivedNote(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (grnEditSearch.getText().toString().matches("")){
                                grnSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(grnEditSearch.getText()));
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
        grnRecycler.setAdapter(null);
        goodReceivedNotes.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_GRN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            goodReceivedNotes.add(new GoodReceivedNote(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (grnEditSearch.getText().toString().matches("")){
                                grnSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("-");
                            } else adapter.getFilter().filter(String.valueOf(grnEditSearch.getText()));
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
        void onListFragmentInteraction(GoodReceivedNote item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<GoodReceivedNote> mValues;
        private final List<GoodReceivedNote> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<GoodReceivedNote> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_goods_recived_note_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.grnTextNomor.setText(""+mValues.get(position).getGrn_number());
            holder.grnTextTglPenerimaan.setText(""+mValues.get(position).getReceipt_date());
            holder.grnTextPurchaseOrder.setText(""+mValues.get(position).getPurchase_order_id());
            holder.grnTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.grnTextSupplier.setText(""+mValues.get(position).getSupplier_name());
            holder.grnTextDiakui.setText(""+mValues.get(position).getRecognized());

            if (position%2==0)
                holder.grnLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.grnLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailGoodRecivedNoteActivity.class);
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
                List<GoodReceivedNote> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((GoodReceivedNote) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (GoodReceivedNote item : values){
                        if (grnSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getGrn_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPurchase_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRecognized().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getGrn_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getReceipt_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getPurchase_order_id().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (grnSpinnerSearch.getSelectedItemPosition()==6){
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

            public final TextView grnTextNomor;
            public final TextView grnTextTglPenerimaan;
            public final TextView grnTextPurchaseOrder;
            public final TextView grnTextDibuat;
            public final TextView grnTextSupplier;
            public final TextView grnTextDiakui;

            public final LinearLayout grnLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                grnTextNomor = (TextView) view.findViewById(R.id.grnTextNomor);
                grnTextTglPenerimaan = (TextView) view.findViewById(R.id.grnTextTglPenerimaan);
                grnTextPurchaseOrder = (TextView) view.findViewById(R.id.grnTextPurchaseOrder);
                grnTextDibuat = (TextView) view.findViewById(R.id.grnTextDibuat);
                grnTextSupplier = (TextView) view.findViewById(R.id.grnTextSupplier);
                grnTextDiakui = (TextView) view.findViewById(R.id.grnTextStatus);

                grnLayoutList = (LinearLayout) view.findViewById(R.id.grnLayoutList);
            }
        }
    }
}
