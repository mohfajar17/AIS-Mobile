package com.asukacorp.aismobile.Contact.Contacts;

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
import com.asukacorp.aismobile.Data.Contact.Contact;
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

public class ContactsFragment extends Fragment {

    public SharedPrefManager sharedPrefManager;

    public TextView conTextPaging;
    public EditText conEditSearch;
    public ImageView conBtnSearch;
    public RecyclerView conRecycler;
    public FloatingActionButton conFabAdd;
    public Spinner conSpinnerSearch;
    public Spinner conSpinnerSort;
    public Spinner conSpinnerSortAD;
    public Button conBtnShowList;
    public ImageButton conBtnBefore;
    public ImageButton conBtnNext;
    public LinearLayout conLayoutPaging;

    public ProgressDialog progressDialog;
    public int mColumnCount = 1;
    public static final String ARG_COLUMN_COUNT = "column-count";
    public OnListFragmentInteractionListener mListener;
    public ContactsFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] CONSpinnerSearch = {"Semua Data", "Contact Name", "Company Name", "Job Title",
            "Email", "Phone"};
    public String[] CONSpinnerSort = {"-- Sort By --", "Berdasarkan Contact Name", "Berdasarkan Company Name",
            "Berdasarkan Job Title", "Berdasarkan Email", "Berdasarkan Phone"};
    public String[] CONADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<Contact> contacts;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public ContactsFragment() {
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
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

        contacts = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Set the adapter
        conRecycler = (RecyclerView) view.findViewById(R.id.conRecycler);
        if (mColumnCount <= 1) {
            conRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            conRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        conFabAdd = (FloatingActionButton) view.findViewById(R.id.conFabAdd);
        conEditSearch = (EditText) view.findViewById(R.id.conEditSearch);
        conTextPaging = (TextView) view.findViewById(R.id.conTextPaging);
        conBtnSearch = (ImageView) view.findViewById(R.id.conBtnSearch);
        conSpinnerSearch = (Spinner) view.findViewById(R.id.conSpinnerSearch);
        conSpinnerSort = (Spinner) view.findViewById(R.id.conSpinnerSort);
        conSpinnerSortAD = (Spinner) view.findViewById(R.id.conSpinnerSortAD);
        conBtnShowList = (Button) view.findViewById(R.id.conBtnShowList);
        conBtnBefore = (ImageButton) view.findViewById(R.id.conBtnBefore);
        conBtnNext = (ImageButton) view.findViewById(R.id.conBtnNext);
        conLayoutPaging = (LinearLayout) view.findViewById(R.id.conLayoutPaging);

        conBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(conTextPaging.getText()));
                setSortHalf(conSpinnerSort.getSelectedItemPosition(), conSpinnerSortAD.getSelectedItemPosition());
                int textValue = Integer.valueOf(String.valueOf(conTextPaging.getText()))+1;
                conTextPaging.setText(""+textValue);
                filter = true;
            }
        });
        conBtnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(String.valueOf(conTextPaging.getText())) > 1) {
                    counter = 15*(Integer.valueOf(String.valueOf(conTextPaging.getText()))-2);
                    setSortHalf(conSpinnerSort.getSelectedItemPosition(), conSpinnerSortAD.getSelectedItemPosition());
                    int textValue = Integer.valueOf(String.valueOf(conTextPaging.getText()))-1;
                    conTextPaging.setText(""+textValue);
                    filter = true;
                }
            }
        });

        conBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadAll==false){
                    counter = -1;
                    loadDataAll("contact_name ASC");
                    loadAll = true;
                    params = conLayoutPaging.getLayoutParams();
                    params.height = 0;
                    conLayoutPaging.setLayoutParams(params);
                    conBtnShowList.setText("Show Half");
                } else {
                    conTextPaging.setText("1");
                    counter = 0;
                    loadData("contact_name ASC");
                    loadAll = false;
                    params = conLayoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                    conLayoutPaging.setLayoutParams(params);
                    conBtnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CONSpinnerSearch);
        conSpinnerSearch.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CONSpinnerSort);
        conSpinnerSort.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, CONADSpinnerSort);
        conSpinnerSortAD.setAdapter(spinnerAdapter);

        conSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (counter<0){
                    setSortAll(position, conSpinnerSortAD.getSelectedItemPosition());
                } else {
                    setSortHalf(position, conSpinnerSortAD.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        conBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conEditSearch.getText().toString().matches("")){
                    conSpinnerSearch.setSelection(0);
                    adapter.getFilter().filter("a");
                } else adapter.getFilter().filter(String.valueOf(conEditSearch.getText()));
            }
        });

//        loadData("contact_name ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("contact_name ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("contact_name ASC");
        else if (position == 2 && posAD == 0)
            loadDataAll("company_name ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("company_name DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("contact_jobtitle ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("contact_jobtitle DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("contact_email ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("contact_email DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("contact_phone1 ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("contact_phone1 DESC");
        else loadDataAll("contact_name ASC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("contact_name ASC");
        else if (position == 1 && posAD == 1)
            loadData("contact_name ASC");
        else if (position == 2 && posAD == 0)
            loadData("company_name ASC");
        else if (position == 2 && posAD == 1)
            loadData("company_name DESC");
        else if (position == 3 && posAD == 0)
            loadData("contact_jobtitle ASC");
        else if (position == 3 && posAD == 1)
            loadData("contact_jobtitle DESC");
        else if (position == 4 && posAD == 0)
            loadData("contact_email ASC");
        else if (position == 4 && posAD == 1)
            loadData("contact_email DESC");
        else if (position == 5 && posAD == 0)
            loadData("contact_phone1 ASC");
        else if (position == 5 && posAD == 1)
            loadData("contact_phone1 DESC");
        else loadData("contact_name ASC");
    }

    private void setAdapterList(){
        adapter = new ContactsFragment.MyRecyclerViewAdapter(contacts, mListener);
        conRecycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        conRecycler.setAdapter(null);
        contacts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CONTACT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            contacts.add(new Contact(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (conEditSearch.getText().toString().matches("")){
                                conSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(conEditSearch.getText()));
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
        conRecycler.setAdapter(null);
        contacts.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CONTACT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            contacts.add(new Contact(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (conEditSearch.getText().toString().matches("")){
                                conSpinnerSearch.setSelection(0);
                                adapter.getFilter().filter("a");
                            } else adapter.getFilter().filter(String.valueOf(conEditSearch.getText()));
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
        void onListFragmentInteraction(Contact item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Contact> mValues;
        private final List<Contact> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<Contact> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_contact_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.conTextName.setText(""+mValues.get(position).getContact_name());
            holder.conTextCompany.setText(""+mValues.get(position).getCompany_name());
            holder.conTextJobTitle.setText(""+mValues.get(position).getContact_jobtitle());
            holder.conTextEmail.setText(""+mValues.get(position).getContact_email());
            holder.conTextPhone.setText(""+mValues.get(position).getContact_phone1());

            if (position%2==0)
                holder.conLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.conLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getContact_id(), mValues.get(position));
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
                List<Contact> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((Contact) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Contact item : values){
                        if (conSpinnerSearch.getSelectedItemPosition()==0){
                            if (item.getContact_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_jobtitle().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_email().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getContact_phone1().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (conSpinnerSearch.getSelectedItemPosition()==1){
                            if (item.getContact_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (conSpinnerSearch.getSelectedItemPosition()==2){
                            if (item.getCompany_name().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (conSpinnerSearch.getSelectedItemPosition()==3){
                            if (item.getContact_jobtitle().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (conSpinnerSearch.getSelectedItemPosition()==4){
                            if (item.getContact_email().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (conSpinnerSearch.getSelectedItemPosition()==5){
                            if (item.getContact_phone1().toLowerCase().contains(filterPattern)){
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

            public final TextView conTextName;
            public final TextView conTextCompany;
            public final TextView conTextJobTitle;
            public final TextView conTextEmail;
            public final TextView conTextPhone;

            public final LinearLayout conLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                conTextName = (TextView) view.findViewById(R.id.conTextName);
                conTextCompany = (TextView) view.findViewById(R.id.conTextCompany);
                conTextJobTitle = (TextView) view.findViewById(R.id.conTextJobTitle);
                conTextEmail = (TextView) view.findViewById(R.id.conTextEmail);
                conTextPhone = (TextView) view.findViewById(R.id.conTextPhone);

                conLayoutList = (LinearLayout) view.findViewById(R.id.conLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Contact contact) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(getActivity(), DetailContactsActivity.class);
                        intent.putExtra("detail", contact);
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
                param.put("feature", "" + "contact");
                param.put("access", "" + "contact:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
