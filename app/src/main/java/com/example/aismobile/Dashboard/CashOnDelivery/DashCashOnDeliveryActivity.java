package com.example.aismobile.Dashboard.CashOnDelivery;

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
import com.example.aismobile.Data.Purchasing.CashOnDelivery;
import com.example.aismobile.Purchasing.CashOnDelivery.DetailCashOnDeliveryActivity;
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

public class DashCashOnDeliveryActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<CashOnDelivery> cashOnDeliveries;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_cash_on_delivery);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        cashOnDeliveries = new ArrayList<>();
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
                cashOnDeliveries.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        cashOnDeliveries.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_CASH_ON_DELIVERY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveries.add(new CashOnDelivery(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(cashOnDeliveries, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashCashOnDeliveryActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCashOnDeliveryActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCashOnDeliveryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashCashOnDeliveryActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<CashOnDelivery> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<CashOnDelivery> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_on_delivery_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.codTextNomor.setText(""+mValues.get(position).getCash_on_delivery_number());
            holder.codTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.codTextfile.setText("");
            holder.codTextTglAwal.setText(""+mValues.get(position).getEnd_date());
            holder.codTextTermPembayaran.setText(""+mValues.get(position).getJob_order_id());
            holder.codTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.codTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.codTextApproval1.setText(""+mValues.get(position).getApproval1());

            if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==1)
                holder.codTextStatus.setText("New");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==2)
                holder.codTextStatus.setText("Pending");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==3)
                holder.codTextStatus.setText("Progress");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==4)
                holder.codTextStatus.setText("Complete");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==5)
                holder.codTextStatus.setText("Closed");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==6)
                holder.codTextStatus.setText("Cancel");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==7)
                holder.codTextStatus.setText("Received");

            if (position%2==0)
                holder.codLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.codLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getCash_on_delivery_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView codTextNomor;
            public final TextView codTextSupplier;
            public final TextView codTextfile;
            public final TextView codTextTglAwal;
            public final TextView codTextTermPembayaran;
            public final TextView codTextCheckedBy;
            public final TextView codTextPersetujuan;
            public final TextView codTextApproval1;
            public final TextView codTextStatus;

            public final LinearLayout codLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                codTextNomor = (TextView) view.findViewById(R.id.codTextNomor);
                codTextSupplier = (TextView) view.findViewById(R.id.codTextSupplier);
                codTextfile = (TextView) view.findViewById(R.id.codTextfile);
                codTextTglAwal = (TextView) view.findViewById(R.id.codTextTglAkhir);
                codTextTermPembayaran = (TextView) view.findViewById(R.id.codTextJobOrder);
                codTextCheckedBy = (TextView) view.findViewById(R.id.codTextCheckedBy);
                codTextPersetujuan = (TextView) view.findViewById(R.id.codTextPersetujuan);
                codTextApproval1 = (TextView) view.findViewById(R.id.codTextApproval1);
                codTextStatus = (TextView) view.findViewById(R.id.codTextStatus);

                codLayoutList = (LinearLayout) view.findViewById(R.id.codLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final CashOnDelivery materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashCashOnDeliveryActivity.this, DetailCashOnDeliveryActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashCashOnDeliveryActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCashOnDeliveryActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCashOnDeliveryActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "cash-on-delivery");
                param.put("access", "" + "cash-on-delivery:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashCashOnDeliveryActivity.this).add(request);
    }
}