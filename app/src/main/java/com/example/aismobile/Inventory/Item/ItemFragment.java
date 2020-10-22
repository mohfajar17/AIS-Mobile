package com.example.aismobile.Inventory.Item;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.aismobile.Data.Inventory.Item;
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

public class ItemFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    private TextView iMenuItem;
    private TextView iMenuKelompokItem;
    private TextView iMenuKategoriItem;
    private TextView iMenuTipeItem;
    private LinearLayout iShowFilter;
    private LinearLayout ilayoutFilter;

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
    public ItemFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "Kode Item", "Nama Item", "Spesifikasi Item", "Stok Sekarang", "Rack",
            "Warehouse Stocks", "Item Section", "Aset", "Aktif"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan Kode Item", "Berdasarkan Nama Item",
            "Berdasarkan Spesifikasi Item", "Berdasarkan Stok Sekarang", "Berdasarkan Rack", "Berdasarkan Warehouse Stocks",
            "Berdasarkan Item Section", "Berdasarkan Aset", "Berdasarkan Aktif"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Item> items;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ItemFragment() {
    }

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
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

        items = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        //sub menu
        iMenuItem = (TextView) view.findViewById(R.id.iMenuItem);
        iMenuKelompokItem = (TextView) view.findViewById(R.id.iMenuKelompokItem);
        iMenuKategoriItem = (TextView) view.findViewById(R.id.iMenuKategoriItem);
        iMenuTipeItem = (TextView) view.findViewById(R.id.iMenuTipeItem);
        iShowFilter = (LinearLayout) view.findViewById(R.id.iShowFilter);
        ilayoutFilter = (LinearLayout) view.findViewById(R.id.ilayoutFilter);

        iMenuKelompokItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                KelompokItemFragment mainFragment = KelompokItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        iMenuKategoriItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                KategoriItemFragment mainFragment = KategoriItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        iMenuTipeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TypeItemFragment mainFragment = TypeItemFragment.newInstance();
                fragmentTransaction.replace(R.id.containerFragment, mainFragment);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        iShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = iShowFilter.getLayoutParams();
                params.height = 0;
                iShowFilter.setLayoutParams(params);
                params = ilayoutFilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                ilayoutFilter.setLayoutParams(params);
            }
        });

        // Set list adapter
        recycler = (RecyclerView) view.findViewById(R.id.iRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.iFabAdd);
        editSearch = (EditText) view.findViewById(R.id.iEditSearch);
        textPaging = (TextView) view.findViewById(R.id.iTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.iBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.iSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.iSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.iSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.iBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.iBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.iBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.iLayoutPaging);

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
                    loadDataAll("item_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("item_id DESC");
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

        loadData("item_id DESC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("item_code ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("item_code DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("item_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("item_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("item_specification ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("item_specification DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("current_stock ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("current_stock DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("rack_code ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("rack_code DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("is_warehouse ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("is_warehouse DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("item_section ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("item_section DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("is_asset ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("is_asset DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("item_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("item_code ASC");
        else if (position == 1 && posAD == 1)
            loadData("item_code DESC");
        else if (position == 2 && posAD == 0)
            loadData("item_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("item_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("item_specification ASC");
        else if (position == 3 && posAD == 1)
            loadData("item_specification DESC");
        else if (position == 4 && posAD == 0)
            loadData("current_stock ASC");
        else if (position == 4 && posAD == 1)
            loadData("current_stock DESC");
        else if (position == 5 && posAD == 0)
            loadData("rack_code ASC");
        else if (position == 5 && posAD == 1)
            loadData("rack_code DESC");
        else if (position == 6 && posAD == 0)
            loadData("is_warehouse ASC");
        else if (position == 6 && posAD == 1)
            loadData("is_warehouse DESC");
        else if (position == 7 && posAD == 0)
            loadData("item_section ASC");
        else if (position == 7 && posAD == 1)
            loadData("item_section DESC");
        else if (position == 8 && posAD == 0)
            loadData("is_asset ASC");
        else if (position == 8 && posAD == 1)
            loadData("is_asset DESC");
        else if (position == 9 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 9 && posAD == 1)
            loadData("is_active DESC");
        else loadData("item_id DESC");
    }

    private void setAdapterList(){
        adapter = new ItemFragment.MyRecyclerViewAdapter(items, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        items.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ITEM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            items.add(new Item(jsonArray.getJSONObject(i)));
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
        items.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ITEM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            items.add(new Item(jsonArray.getJSONObject(i)));
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
        void onListFragmentInteraction(Item item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Item> mValues;
        private final List<Item> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Item> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.iTextCode.setText(""+mValues.get(position).getItem_code());
            holder.iTextName.setText(""+mValues.get(position).getItem_name());
            holder.iTextSpesifikasi.setText(""+mValues.get(position).getItem_specification());
            holder.iTextStock.setText(""+mValues.get(position).getCurrent_stock());
            holder.iTextRack.setText(""+mValues.get(position).getRack_code());
            holder.iTextWarehouse.setText(""+mValues.get(position).getIs_warehouse());
            holder.iTextSection.setText(""+mValues.get(position).getItem_section());
            holder.iTextAsset.setText(""+mValues.get(position).getIs_asset());
            holder.iTextActive.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.iLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.iLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getItem_id(), mValues.get(position));
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
                List<Item> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Item) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Item item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getItem_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getItem_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getItem_specification().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCurrent_stock().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getRack_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_warehouse().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getItem_section().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_asset().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getItem_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getItem_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getItem_specification().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getCurrent_stock().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getRack_code().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getIs_warehouse().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getItem_section().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getIs_asset().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==9){
                            if (item.getIs_active().toLowerCase().contains(filterPattern)){
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

            public final TextView iTextCode;
            public final TextView iTextName;
            public final TextView iTextSpesifikasi;
            public final TextView iTextStock;
            public final TextView iTextRack;
            public final TextView iTextWarehouse;
            public final TextView iTextSection;
            public final TextView iTextAsset;
            public final TextView iTextActive;

            public final LinearLayout iLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                iTextCode = (TextView) view.findViewById(R.id.iTextCode);
                iTextName = (TextView) view.findViewById(R.id.iTextName);
                iTextSpesifikasi = (TextView) view.findViewById(R.id.iTextSpesifikasi);
                iTextStock = (TextView) view.findViewById(R.id.iTextStock);
                iTextRack = (TextView) view.findViewById(R.id.iTextRack);
                iTextWarehouse = (TextView) view.findViewById(R.id.iTextWarehouse);
                iTextSection = (TextView) view.findViewById(R.id.iTextSection);
                iTextAsset = (TextView) view.findViewById(R.id.iTextAsset);
                iTextActive = (TextView) view.findViewById(R.id.iTextActive);

                iLayoutList = (LinearLayout) view.findViewById(R.id.iLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Item item) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailItemActivity.class);
                        intent.putExtra("detail", item);
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
                param.put("feature", "" + "item-and-service");
                param.put("access", "" + "item-and-service:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
