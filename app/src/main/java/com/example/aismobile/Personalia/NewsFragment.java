package com.example.aismobile.Personalia;

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
import com.example.aismobile.Data.Personalia.PersonNews;
import com.example.aismobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    private LinearLayout kShowFilter;
    private LinearLayout kLayoutFilter;

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
    public NewsFragment.MyRecyclerViewAdapter adapter;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "News Title", "News Date", "Start Publish Date",
            "Finish Publish Date", "Dibuat Oleh"};
    public String[] SpinnerSort = {"-- Sort By --", "Berdasarkan News Title", "Berdasarkan News Date",
            "Berdasarkan Start Publish Date", "Berdasarkan Finish Publish Date", "Berdasarkan Dibuat Oleh"};
    public String[] ADSpinnerSort = {"ASC", "DESC"};
    public boolean loadAll = false;
    public List<PersonNews> personNews;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
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

        personNews = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        //show filter
        kShowFilter = (LinearLayout) view.findViewById(R.id.nShowFilter);
        kLayoutFilter = (LinearLayout) view.findViewById(R.id.nLayoutFilter);

        kShowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = kShowFilter.getLayoutParams();
                params.height = 0;
                kShowFilter.setLayoutParams(params);
                params = kLayoutFilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;;
                kLayoutFilter.setLayoutParams(params);
            }
        });

        // Set the adapter
        recycler = (RecyclerView) view.findViewById(R.id.nRecycler);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }

        fabAdd = (FloatingActionButton) view.findViewById(R.id.nFabAdd);
        editSearch = (EditText) view.findViewById(R.id.nEditSearch);
        textPaging = (TextView) view.findViewById(R.id.nTextPaging);
        btnSearch = (ImageView) view.findViewById(R.id.nBtnSearch);
        spinnerSearch = (Spinner) view.findViewById(R.id.nSpinnerSearch);
        spinnerSort = (Spinner) view.findViewById(R.id.nSpinnerSort);
        spinnerSortAD = (Spinner) view.findViewById(R.id.nSpinnerSortAD);
        btnShowList = (Button) view.findViewById(R.id.nBtnShowList);
        btnBefore = (ImageButton) view.findViewById(R.id.nBtnBefore);
        btnNext = (ImageButton) view.findViewById(R.id.nBtnNext);
        layoutPaging = (LinearLayout) view.findViewById(R.id.nLayoutPaging);

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
                    loadDataAll("news_id DESC");
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData("news_id DESC");
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
                    loadData("news_id DESC");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

//        loadData("news_id ASC");

        return view;
    }

    private void setSortAll(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadDataAll("news_title ASC");
        else if (position == 1 && posAD == 1)
            loadDataAll("news_title DESC");
        else if (position == 2 && posAD == 0)
            loadDataAll("news_date ASC");
        else if (position == 2 && posAD == 1)
            loadDataAll("news_date DESC");
        else if (position == 3 && posAD == 0)
            loadDataAll("start_publish_date ASC");
        else if (position == 3 && posAD == 1)
            loadDataAll("start_publish_date DESC");
        else if (position == 4 && posAD == 0)
            loadDataAll("finish_publish_date ASC");
        else if (position == 4 && posAD == 1)
            loadDataAll("finish_publish_date DESC");
        else if (position == 5 && posAD == 0)
            loadDataAll("created_by ASC");
        else if (position == 5 && posAD == 1)
            loadDataAll("created_by DESC");
        else loadDataAll("news_id DESC");
    }

    private void setSortHalf(int position, int posAD){
        if (position == 1 && posAD == 0)
            loadData("news_title ASC");
        else if (position == 1 && posAD == 1)
            loadData("news_title DESC");
        else if (position == 2 && posAD == 0)
            loadData("news_date ASC");
        else if (position == 2 && posAD == 1)
            loadData("news_date DESC");
        else if (position == 3 && posAD == 0)
            loadData("start_publish_date ASC");
        else if (position == 3 && posAD == 1)
            loadData("start_publish_date DESC");
        else if (position == 4 && posAD == 0)
            loadData("finish_publish_date ASC");
        else if (position == 4 && posAD == 1)
            loadData("finish_publish_date DESC");
        else if (position == 5 && posAD == 0)
            loadData("created_by ASC");
        else if (position == 5 && posAD == 1)
            loadData("created_by DESC");
        else loadData("news_id DESC");
    }

    private void setAdapterList(){
        adapter = new NewsFragment.MyRecyclerViewAdapter(personNews, mListener);
        recycler.setAdapter(adapter);
    }

    private void loadDataAll(final String sortBy) {
        progressDialog.show();
        recycler.setAdapter(null);
        personNews.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PERSONALIA_NEWS_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            personNews.add(new PersonNews(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();

                        if (filter){
                            if (editSearch.getText().toString().matches("")){
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
        personNews.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PERSONALIA_NEWS_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            personNews.add(new PersonNews(jsonArray.getJSONObject(i)));
                        }
                        setAdapterList();
                        if (filter){
                            if (editSearch.getText().toString().matches("")){
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
        void onListFragmentInteraction(PersonNews item);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<PersonNews> mValues;
        private final List<PersonNews> values;
        private final OnListFragmentInteractionListener mListener;

        private MyRecyclerViewAdapter(List<PersonNews> mValues, OnListFragmentInteractionListener mListener) {
            this.mValues = mValues;
            this.mListener = mListener;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_news_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.nTextTitle.setText(""+mValues.get(position).getNews_title());
            holder.nTextDate.setText(""+mValues.get(position).getNews_date());
            holder.nTextStartDate.setText(""+mValues.get(position).getStart_publish_date());
            holder.nTextFinishDate.setText(""+mValues.get(position).getFinish_publish_date());
            holder.nTextDibuat.setText(""+mValues.get(position).getCreated_by());

            if (position%2==0)
                holder.pLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

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
                List<PersonNews> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((PersonNews) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (PersonNews item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getNews_title().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNews_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getStart_publish_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getFinish_publish_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getCreated_by().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getNews_title().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getNews_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getStart_publish_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getFinish_publish_date().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
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

            public final TextView nTextTitle;
            public final TextView nTextDate;
            public final TextView nTextStartDate;
            public final TextView nTextFinishDate;
            public final TextView nTextDibuat;

            public final LinearLayout pLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                nTextTitle = (TextView) view.findViewById(R.id.nTextTitle);
                nTextDate = (TextView) view.findViewById(R.id.nTextDate);
                nTextStartDate = (TextView) view.findViewById(R.id.nTextStartDate);
                nTextFinishDate = (TextView) view.findViewById(R.id.nTextFinishDate);
                nTextDibuat = (TextView) view.findViewById(R.id.nTextDibuat);

                pLayoutList = (LinearLayout) view.findViewById(R.id.nLayoutList);
            }
        }
    }
}
