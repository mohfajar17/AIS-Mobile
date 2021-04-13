package com.example.aismobile.Personalia.Kerja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.aismobile.Data.Personalia.CheckClockDetail;
import com.example.aismobile.Data.Personalia.Fullname;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckClockDetailActivity extends AppCompatActivity {

    private Fullname employee;
    private Context context;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<CheckClockDetail> employeeDet;
    private ProgressDialog progressDialog;
    public boolean loadAll = false;
    public int counter = 0;
    public ViewGroup.LayoutParams params;
    public boolean filter = false;
    public ArrayAdapter<String> spinnerAdapter;
    public String[] SpinnerSearch = {"Semua Data", "JO Number", "Check Clock Date", "Day", "Panggilan Darurat",
            "No Lunch", "Keterangan Libur", "Shift", "Type Jam Kerja", "Ijin Terlambat"};

    public TextView textPaging;
    public EditText editSearch;
    public ImageView btnSearch;
    public RecyclerView recyclerView;
    public Spinner spinnerSearch;
    public Button btnShowList;
    public ImageButton btnBefore;
    public ImageButton btnNext;
    public LinearLayout layoutPaging;

    public ImageView buttonBack;
    public TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_clock_detail);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDet = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        editSearch = (EditText) findViewById(R.id.editSearch);
        textPaging = (TextView) findViewById(R.id.textPaging);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        spinnerSearch = (Spinner) findViewById(R.id.spinnerSearch);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        btnBefore = (ImageButton) findViewById(R.id.btnBefore);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        layoutPaging = (LinearLayout) findViewById(R.id.layoutPaging);

        textTitle = (TextView) findViewById(R.id.textTitle);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textTitle.setText("Check Clock - " + employee.getFullname());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 15*Integer.valueOf(String.valueOf(textPaging.getText()));
                loadData();
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
                    loadData();
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
                    loadDataAll();
                    loadAll = true;
                    params = layoutPaging.getLayoutParams();
                    params.height = 0;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show Half");
                } else {
                    textPaging.setText("1");
                    counter = 0;
                    loadData();
                    loadAll = false;
                    params = layoutPaging.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutPaging.setLayoutParams(params);
                    btnShowList.setText("Show All");
                }
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(CheckClockDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, SpinnerSearch);
        spinnerSearch.setAdapter(spinnerAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearch.getText().toString().matches("")){
                    spinnerSearch.setSelection(0);
                    adapter.getFilter().filter("-");
                } else adapter.getFilter().filter(String.valueOf(editSearch.getText()));
            }
        });

        loadData();
    }

    private void setAdapterList(){
        adapter = new MyRecyclerViewAdapter(employeeDet, context);
        recyclerView.setAdapter(adapter);
    }

    private void loadDataAll() {
        progressDialog.show();
        recyclerView.setAdapter(null);
        employeeDet.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CHECK_CLOCK_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDet.add(new CheckClockDetail(jsonArray.getJSONObject(i)));
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
                        Toast.makeText(CheckClockDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckClockDetailActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CheckClockDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("counter", "" + counter);
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(CheckClockDetailActivity.this).add(request);
    }

    public void loadData(){
        progressDialog.show();
        recyclerView.setAdapter(null);
        employeeDet.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CHECK_CLOCK_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDet.add(new CheckClockDetail(jsonArray.getJSONObject(i)));
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
                        Toast.makeText(CheckClockDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckClockDetailActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CheckClockDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("counter", "" + counter);
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(CheckClockDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<CheckClockDetail> mValues;
        private final List<CheckClockDetail> values;
        private Context context;

        private MyRecyclerViewAdapter(List<CheckClockDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
            values = new ArrayList<>(mValues);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_check_clock_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textJoNumber.setText(""+mValues.get(position).getJob_order());
            holder.textDailyWages.setText("Rp. "+mValues.get(position).getDaily_wages());
            holder.textCheckClockDate.setText(""+mValues.get(position).getDate_check_clock());
            holder.textDay.setText(""+mValues.get(position).getDay());
            holder.textCheckIn.setText(""+mValues.get(position).getCheck_in());
            holder.textCheckOut.setText(""+mValues.get(position).getCheck_out());
            holder.textStartSPKL.setText(""+mValues.get(position).getStart_time());
            holder.textEndSPKL.setText(""+mValues.get(position).getFinish_time());
            holder.textPanggilanDarurat.setText(""+mValues.get(position).getEmergency_call());
            holder.textNoLunch.setText(""+mValues.get(position).getNo_lunch());
            holder.textKetLibur.setText(""+mValues.get(position).getNote_for_shift());
            holder.textShift.setText(""+mValues.get(position).getShift_category());
            holder.textTypeJamKerja.setText(""+mValues.get(position).getType_work_hour());
            holder.textIjinTerlambat.setText(""+mValues.get(position).getPermission_late());

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
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
                List<CheckClockDetail> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0){
                    filteredList.add((CheckClockDetail) values);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CheckClockDetail item : values){
                        if (spinnerSearch.getSelectedItemPosition()==0){
                            if (item.getJob_order().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDate_check_clock().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getDay().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getEmergency_call().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNo_lunch().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getNote_for_shift().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getShift_category().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getType_work_hour().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            } else if (item.getPermission_late().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==1){
                            if (item.getJob_order().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==2){
                            if (item.getDate_check_clock().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==3){
                            if (item.getDay().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==4){
                            if (item.getEmergency_call().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==5){
                            if (item.getNo_lunch().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==6){
                            if (item.getNote_for_shift().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==7){
                            if (item.getShift_category().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==8){
                            if (item.getType_work_hour().toLowerCase().contains(filterPattern)){
                                filteredList.add(item);
                            }
                        } else if (spinnerSearch.getSelectedItemPosition()==9){
                            if (item.getPermission_late().toLowerCase().contains(filterPattern)){
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

            public final TextView textJoNumber;
            public final TextView textDailyWages;
            public final TextView textCheckClockDate;
            public final TextView textDay;
            public final TextView textCheckIn;
            public final TextView textCheckOut;
            public final TextView textStartSPKL;
            public final TextView textEndSPKL;
            public final TextView textPanggilanDarurat;
            public final TextView textNoLunch;
            public final TextView textKetLibur;
            public final TextView textShift;
            public final TextView textTypeJamKerja;
            public final TextView textIjinTerlambat;

            public final LinearLayout layout;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                textJoNumber = (TextView) view.findViewById(R.id.textJoNumber);
                textDailyWages = (TextView) view.findViewById(R.id.textDailyWages);
                textCheckClockDate = (TextView) view.findViewById(R.id.textCheckClockDate);
                textDay = (TextView) view.findViewById(R.id.textDay);
                textCheckIn = (TextView) view.findViewById(R.id.textCheckIn);
                textCheckOut = (TextView) view.findViewById(R.id.textCheckOut);
                textStartSPKL = (TextView) view.findViewById(R.id.textStartSPKL);
                textEndSPKL = (TextView) view.findViewById(R.id.textEndSPKL);
                textPanggilanDarurat = (TextView) view.findViewById(R.id.textPanggilanDarurat);
                textNoLunch = (TextView) view.findViewById(R.id.textNoLunch);
                textKetLibur = (TextView) view.findViewById(R.id.textKetLibur);
                textShift = (TextView) view.findViewById(R.id.textShift);
                textTypeJamKerja = (TextView) view.findViewById(R.id.textTypeJamKerja);
                textIjinTerlambat = (TextView) view.findViewById(R.id.textIjinTerlambat);

                layout = (LinearLayout) view.findViewById(R.id.layout);
            }
        }
    }
}