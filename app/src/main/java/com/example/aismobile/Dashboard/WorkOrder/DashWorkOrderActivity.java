package com.example.aismobile.Dashboard.WorkOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseService;
import com.example.aismobile.Purchasing.WorkOrder.DetailWorkOrdderActivity;
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

public class DashWorkOrderActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<PurchaseService> purchaseServices;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_work_order);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        purchaseServices = new ArrayList<>();
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
                purchaseServices.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        purchaseServices.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_WORK_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseServices.add(new PurchaseService(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(purchaseServices, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashWorkOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashWorkOrderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashWorkOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashWorkOrderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PurchaseService> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PurchaseService> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_orders_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.woTextNomor.setText(""+mValues.get(position).getPurchase_service_number());
            holder.woTextSupplier.setText(""+mValues.get(position).getSupplier_id());
            holder.woTextfile.setText("");
            holder.woTextTglAwal.setText(""+mValues.get(position).getEnd_date());
            holder.woTextTermPembayaran.setText(""+mValues.get(position).getPayment_term_id());
            holder.woTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.woTextPersetujuan.setText(""+mValues.get(position).getApproval_assign_id());
            holder.woTextApproval1.setText(""+mValues.get(position).getPo_approval1());

            if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==1)
                holder.woTextStatus.setText("New");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==2)
                holder.woTextStatus.setText("Pending");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==3)
                holder.woTextStatus.setText("Progress");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==4)
                holder.woTextStatus.setText("Complete");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==5)
                holder.woTextStatus.setText("Closed");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==6)
                holder.woTextStatus.setText("Cancel");
            else if (Integer.valueOf(mValues.get(position).getPurchase_order_status_id())==7)
                holder.woTextStatus.setText("Received");

            if (position%2==0)
                holder.woLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.woLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getPurchase_service_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView woTextNomor;
            public final TextView woTextSupplier;
            public final TextView woTextfile;
            public final TextView woTextTglAwal;
            public final TextView woTextTermPembayaran;
            public final TextView woTextCheckedBy;
            public final TextView woTextPersetujuan;
            public final TextView woTextApproval1;
            public final TextView woTextStatus;

            public final LinearLayout woLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                woTextNomor = (TextView) view.findViewById(R.id.woTextNomor);
                woTextSupplier = (TextView) view.findViewById(R.id.woTextSupplier);
                woTextfile = (TextView) view.findViewById(R.id.woTextfile);
                woTextTglAwal = (TextView) view.findViewById(R.id.woTextTglAkhir);
                woTextTermPembayaran = (TextView) view.findViewById(R.id.woTextTermPembayaran);
                woTextCheckedBy = (TextView) view.findViewById(R.id.woTextCheckedBy);
                woTextPersetujuan = (TextView) view.findViewById(R.id.woTextPersetujuan);
                woTextApproval1 = (TextView) view.findViewById(R.id.woTextApproval1);
                woTextStatus = (TextView) view.findViewById(R.id.woTextStatus);

                woLayoutList = (LinearLayout) view.findViewById(R.id.woLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final PurchaseService purchaseService) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashWorkOrderActivity.this, DetailWorkOrdderActivity.class);
                        intent.putExtra("detail", purchaseService);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashWorkOrderActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashWorkOrderActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashWorkOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "purchase-service");
                param.put("access", "" + "purchase-service:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashWorkOrderActivity.this).add(request);
    }
}