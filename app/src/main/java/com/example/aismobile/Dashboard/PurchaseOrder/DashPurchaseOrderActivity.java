package com.example.aismobile.Dashboard.PurchaseOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseOrder;
import com.example.aismobile.Purchasing.PurchaseOrder.DetailPurchaseOrderActivity;
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

public class DashPurchaseOrderActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<PurchaseOrder> purchaseOrders;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_purchase_order);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        purchaseOrders = new ArrayList<>();
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
                purchaseOrders.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        purchaseOrders.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_PURCHASE_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseOrders.add(new PurchaseOrder(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(purchaseOrders, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashPurchaseOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashPurchaseOrderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashPurchaseOrderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PurchaseOrder> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PurchaseOrder> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_purchase_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.poTextNomor.setText(""+mValues.get(position).getPurchase_order_number());
            holder.poTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.poTextfile.setText("");
            holder.poTextTglKedatangan.setText(""+mValues.get(position).getEnd_date());
            holder.poTextJobOrder.setText(""+mValues.get(position).getJob_order_number());
            holder.poTextTermPembayaran.setText(""+mValues.get(position).getPayment_term_id());
            holder.poTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.poTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.poTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.poTextApproval1.setText(""+mValues.get(position).getPo_approval1());

            if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==1)
                holder.poTextStatus.setText("New");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==2)
                holder.poTextStatus.setText("Pending");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==3)
                holder.poTextStatus.setText("Progress");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==4)
                holder.poTextStatus.setText("Complete");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==5)
                holder.poTextStatus.setText("Closed");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==6)
                holder.poTextStatus.setText("Cancel");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==7)
                holder.poTextStatus.setText("Received");

            if (position%2==0)
                holder.poLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.poLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getPurchase_order_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView poTextNomor;
            public final TextView poTextSupplier;
            public final TextView poTextfile;
            public final TextView poTextTglKedatangan;
            public final TextView poTextJobOrder;
            public final TextView poTextTermPembayaran;
            public final TextView poTextDibuat;
            public final TextView poTextCheckedBy;
            public final TextView poTextPersetujuan;
            public final TextView poTextApproval1;
            public final TextView poTextStatus;

            public final LinearLayout poLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                poTextNomor = (TextView) view.findViewById(R.id.poTextNomor);
                poTextSupplier = (TextView) view.findViewById(R.id.poTextSupplier);
                poTextfile = (TextView) view.findViewById(R.id.poTextfile);
                poTextTglKedatangan = (TextView) view.findViewById(R.id.poTextTglKedatangan);
                poTextJobOrder = (TextView) view.findViewById(R.id.poTextJobOrder);
                poTextTermPembayaran = (TextView) view.findViewById(R.id.poTextTermPembayaran);
                poTextDibuat = (TextView) view.findViewById(R.id.poTextDibuat);
                poTextCheckedBy = (TextView) view.findViewById(R.id.poTextCheckedBy);
                poTextPersetujuan = (TextView) view.findViewById(R.id.poTextPersetujuan);
                poTextApproval1 = (TextView) view.findViewById(R.id.poTextApproval1);
                poTextStatus = (TextView) view.findViewById(R.id.poTextStatus);

                poLayoutList = (LinearLayout) view.findViewById(R.id.poLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final PurchaseOrder purchaseOrder) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashPurchaseOrderActivity.this, DetailPurchaseOrderActivity.class);
                        intent.putExtra("detail", purchaseOrder);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashPurchaseOrderActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashPurchaseOrderActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "purchase-order");
                param.put("access", "" + "purchase-order:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashPurchaseOrderActivity.this).add(request);
    }
}