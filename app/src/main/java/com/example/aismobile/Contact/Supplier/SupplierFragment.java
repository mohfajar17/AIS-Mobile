package com.example.aismobile.Contact.Supplier;

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
import com.example.aismobile.Data.Contact.Supplier;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierFragment extends Fragment {

    public TextView sdTextPaging;
    public EditText sdEditSearch;
    public ImageView sdBtnSearch;
    public RecyclerView sdRecycler;
    public FloatingActionButton sdFabAdd;
    public Spinner sdSpinnerSearch;
    public Spinner sdSpinnerSort;
    public Spinner sdSpinnerSortAD;
    public Button sdBtnShowList;
    public ImageButton sdBtnBefore;
    public ImageButton sdBtnNext;
    public LinearLayout sdLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public SupplierFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SDSpinnerSearch = {"Semua Data", "Supplier Name", "Supplier Code", "Supplier Type",
            "Telepon Kantor", "Supplier Contact", "Nomor HP", "E-Mail", "Nama Bank", "Rek Bank", "Aktif"};
    public String[] SDSpinnerSort = {"-- Sort By --", "Berdasarkan Supplier Name", "Berdasarkan Supplier Code",
            "Berdasarkan Supplier Type", "Berdasarkan Telepon Kantor", "Berdasarkan Supplier Contact", "Berdasarkan Nomor HP",
            "Berdasarkan E-Mail", "Berdasarkan Nama Bank", "Berdasarkan Rek Bank", "Berdasarkan Aktif"};
    public String[] SDADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Supplier> suppliers;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public SupplierFragment() {
        // Required empty public constructor
    }

    public static SupplierFragment newInstance() {
        SupplierFragment fragment = new SupplierFragment();
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

        suppliers = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);

        // Set the adapter
        sdRecycler = (RecyclerView) view.findViewById(R.id.sdRecycler);
        if (mColumnCount <= 1) {
            sdRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            sdRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        sdFabAdd = (FloatingActionButton) view.findViewById(R.id.sdFabAdd);
        sdEditSearch = (EditText) view.findViewById(R.id.sdEditSearch);
        sdTextPaging = (TextView) view.findViewById(R.id.sdTextPaging);
        sdBtnSearch = (ImageView) view.findViewById(R.id.sdBtnSearch);
        sdSpinnerSearch = (Spinner) view.findViewById(R.id.sdSpinnerSearch);
        sdSpinnerSort = (Spinner) view.findViewById(R.id.sdSpinnerSort);
        sdSpinnerSortAD = (Spinner) view.findViewById(R.id.sdSpinnerSortAD);
        sdBtnShowList = (Button) view.findViewById(R.id.sdBtnShowList);
        sdBtnBefore = (ImageButton) view.findViewById(R.id.sdBtnBefore);
        sdBtnNext = (ImageButton) view.findViewById(R.id.sdBtnNext);
        sdLayoutPaging = (LinearLayout) view.findViewById(R.id.sdLayoutPaging);

        sdBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(sdTextPaging.getText()));
                setSortHalf(sdSpinnerSort.getSelectedItemPosition(), sdSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(sdTextPaging.getText()))+1;
                sdTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        sdBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(sdTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(sdTextPaging.getText()))-2);
                    setSortHalf(sdSpinnerSort.getSelectedItemPosition(), sdSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(sdTextPaging.getText()))-1;
                    sdTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        sdBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("supplier_id ASC");
                    loadAll = true;
                    params = sdLayoutPaging.getLayoutParams();
                    params.height = 0;
                    sdLayoutPaging.setLayoutParams(params);
                    sdBtnShowList.setText("Show Half");
                } else {
                    sdTextPaging.setText("1");
                    counter = 0;
                    loadData("supplier_id ASC");
                    loadAll = false;
                    params = sdLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    sdLayoutPaging.setLayoutParams(params);
                    sdBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SDSpinnerSearch);
        sdSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SDSpinnerSort);
        sdSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, SDADSpinnerSort);
        sdSpinnerSortAD.setAdapter(spinnerAdapter);

        sdSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, sdSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, sdSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sdBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sdEditSearch.getText().toString().matches("")){
                    sdSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(sdEditSearch.getText()));
            }
        });

//        loadData("supplier_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("supplier_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("supplier_name ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("supplier_number ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("supplier_number DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("supplier_type ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("supplier_type DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("office_phone ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("office_phone DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("supplier_contact ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("supplier_contact DESC");
        else if (position == 6 && posAD == 0)
            loadDataAll("phone ASC");
        else if (position == 6 && posAD == 1)
            loadDataAll("phone DESC");
        else if (position == 7 && posAD == 0)
            loadDataAll("email_address ASC");
        else if (position == 7 && posAD == 1)
            loadDataAll("email_address DESC");
        else if (position == 8 && posAD == 0)
            loadDataAll("bank_name ASC");
        else if (position == 8 && posAD == 1)
            loadDataAll("bank_name DESC");
        else if (position == 9 && posAD == 0)
            loadDataAll("bank_account ASC");
        else if (position == 9 && posAD == 1)
            loadDataAll("bank_account DESC");
        else if (position == 10 && posAD == 0)
            loadDataAll("is_active ASC");
        else if (position == 10 && posAD == 1)
            loadDataAll("is_active DESC");
        else loadDataAll("supplier_id ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("supplier_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("supplier_name ASC");
        else if (position == 2 && posAD == 0)
            loadData("supplier_number ASC");
        else if (position == 2 && posAD == 1)
            loadData("supplier_number DESC");
        else if (position == 3 && posAD == 0)
            loadData("supplier_type ASC");
        else if (position == 3 && posAD == 1)
            loadData("supplier_type DESC");
        else if (position == 4 && posAD == 0)
            loadData("office_phone ASC");
        else if (position == 4 && posAD == 1)
            loadData("office_phone DESC");
        else if (position == 5 && posAD == 0)
            loadData("supplier_contact ASC");
        else if (position == 5 && posAD == 1)
            loadData("supplier_contact DESC");
        else if (position == 6 && posAD == 0)
            loadData("phone ASC");
        else if (position == 6 && posAD == 1)
            loadData("phone DESC");
        else if (position == 7 && posAD == 0)
            loadData("email_address ASC");
        else if (position == 7 && posAD == 1)
            loadData("email_address DESC");
        else if (position == 8 && posAD == 0)
            loadData("bank_name ASC");
        else if (position == 8 && posAD == 1)
            loadData("bank_name DESC");
        else if (position == 9 && posAD == 0)
            loadData("bank_account ASC");
        else if (position == 9 && posAD == 1)
            loadData("bank_account DESC");
        else if (position == 10 && posAD == 0)
            loadData("is_active ASC");
        else if (position == 10 && posAD == 1)
            loadData("is_active DESC");
        else loadData("supplier_id ASC");
    }

    private void setAdapterList(){
        adapter = new SupplierFragment.MyRecyclerViewAdapter(suppliers, mListener);
        sdRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        sdRecycler.setAdapter(null);
        suppliers.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            suppliers.add(new Supplier(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (sdEditSearch.getText().toString().matches("")){
                                sdSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(sdEditSearch.getText()));
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
        sdRecycler.setAdapter(null);
        suppliers.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            suppliers.add(new Supplier(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (sdEditSearch.getText().toString().matches("")){
                                sdSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(sdEditSearch.getText()));
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
        void onListFragmentInteraction(Supplier item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Supplier> mValues;
        private final List<Supplier> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Supplier> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_supplier_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.sdTextName.setText(""+mValues.get(position).getSupplier_name());
            holder.sdTextCode.setText(""+mValues.get(position).getSupplier_number());
            holder.sdTextType.setText(""+mValues.get(position).getSupplier_type());
            holder.sdTextTlpKantor.setText(""+mValues.get(position).getOffice_phone());
            holder.sdTextSupContact.setText(""+mValues.get(position).getSupplier_contact());
            holder.sdTextNoHp.setText(""+mValues.get(position).getPhone());
            holder.sdTextEmail.setText(""+mValues.get(position).getEmail_address());
            holder.sdTextBank.setText(""+mValues.get(position).getBank_name());
            holder.sdTextBankRek.setText(""+mValues.get(position).getBank_account());
            holder.sdTextAktif.setText(""+mValues.get(position).getIs_active());

            if (position%2==0)
                holder.sdLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.sdLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailSupplierActivity.class);
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
                List<Supplier> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Supplier) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Supplier item : values){
                        if (sdSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getOffice_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getSupplier_contact().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPhone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmail_address().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getBank_account().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getIs_active().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getSupplier_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getSupplier_number().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getSupplier_type().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getOffice_phone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getSupplier_contact().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==6){
                            if (item.getPhone().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==7){
                            if (item.getEmail_address().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==8){
                            if (item.getBank_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==9){
                            if (item.getBank_account().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (sdSpinnerSearch.getSelectedItemPosition()==10){
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

            public final TextView sdTextName;
            public final TextView sdTextCode;
            public final TextView sdTextType;
            public final TextView sdTextTlpKantor;
            public final TextView sdTextSupContact;
            public final TextView sdTextNoHp;
            public final TextView sdTextEmail;
            public final TextView sdTextBank;
            public final TextView sdTextBankRek;
            public final TextView sdTextAktif;

            public final LinearLayout sdLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                sdTextName = (TextView) view.findViewById(R.id.sdTextName);
                sdTextCode = (TextView) view.findViewById(R.id.sdTextCode);
                sdTextType = (TextView) view.findViewById(R.id.sdTextType);
                sdTextTlpKantor = (TextView) view.findViewById(R.id.sdTextTlpKantor);
                sdTextSupContact = (TextView) view.findViewById(R.id.sdTextSupContact);
                sdTextNoHp = (TextView) view.findViewById(R.id.sdTextNoHp);
                sdTextEmail = (TextView) view.findViewById(R.id.sdTextEmail);
                sdTextBank = (TextView) view.findViewById(R.id.sdTextBank);
                sdTextBankRek = (TextView) view.findViewById(R.id.sdTextBankRek);
                sdTextAktif = (TextView) view.findViewById(R.id.sdTextAktif);

                sdLayoutList = (LinearLayout) view.findViewById(R.id.sdLayoutList);
            }
        }
    }
}
