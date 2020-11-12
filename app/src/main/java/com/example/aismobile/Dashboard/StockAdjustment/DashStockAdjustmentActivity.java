package com.example.aismobile.Dashboard.StockAdjustment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Inventory.StockAdjustment;
import com.example.aismobile.Inventory.Stock.DetailStockActivity;
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

public class DashStockAdjustmentActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<StockAdjustment> stockAdjustments;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_stock_adjustment);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        stockAdjustments = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                stockAdjustments.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        stockAdjustments.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_STOCK_ADJUSMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            stockAdjustments.add(new StockAdjustment(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(stockAdjustments, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashStockAdjustmentActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashStockAdjustmentActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashStockAdjustmentActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashStockAdjustmentActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<StockAdjustment> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<StockAdjustment> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_stock_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.saTextNumber.setText(""+mValues.get(position).getAdjustment_number());
            holder.saTextDate.setText(""+mValues.get(position).getAdjustment_date());
            holder.saTextDesc.setText(""+mValues.get(position).getShort_description());
            holder.saTextNote.setText(""+mValues.get(position).getNotes());
            holder.saTextApproval.setText(""+mValues.get(position).getApproval_by());

            if (position%2==0)
                holder.saLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.saLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getStock_adjustment_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView saTextNumber;
            public final TextView saTextDate;
            public final TextView saTextDesc;
            public final TextView saTextNote;
            public final TextView saTextApproval;

            public final LinearLayout saLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                saTextNumber = (TextView) view.findViewById(R.id.saTextNumber);
                saTextDate = (TextView) view.findViewById(R.id.saTextDate);
                saTextDesc = (TextView) view.findViewById(R.id.saTextDesc);
                saTextNote = (TextView) view.findViewById(R.id.saTextNote);
                saTextApproval = (TextView) view.findViewById(R.id.saTextApproval);

                saLayoutList = (LinearLayout) view.findViewById(R.id.saLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final StockAdjustment materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashStockAdjustmentActivity.this, DetailStockActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashStockAdjustmentActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashStockAdjustmentActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashStockAdjustmentActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "stock-adjustment");
                param.put("access", "" + "stock-adjustment:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashStockAdjustmentActivity.this).add(request);
    }
}