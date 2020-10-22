package com.example.aismobile.Dashboard.WorkRequest;

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
import com.example.aismobile.Data.Project.WorkOrder;
import com.example.aismobile.Project.WorkRequests.DetailWorkReqActivity;
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

public class DashWorkRequestActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<WorkOrder> workOrders;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_work_request);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        workOrders = new ArrayList<>();
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
                workOrders.clear();
                loadDetail();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_WORK_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workOrders.add(new WorkOrder(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(workOrders, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashWorkRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashWorkRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashWorkRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashWorkRequestActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<WorkOrder> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<WorkOrder> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_work_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pwrTextNomorWorkReq.setText(""+mValues.get(position).getWork_order_number());
            holder.pwrTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.pwrTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.pwrTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pwrTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.pwrTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pwrTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pwrTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (position%2==0)
                holder.pwrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pwrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getWork_order_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView pwrTextNomorWorkReq;
            public final TextView pwrTextJobOrder;
            public final TextView pwrTextTglAwal;
            public final TextView pwrTextDibuat;
            public final TextView pwrTextCheckedBy;
            public final TextView pwrTextApproval1;
            public final TextView pwrTextApproval2;
            public final TextView pwrTextApproval3;

            public final LinearLayout pwrLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pwrTextNomorWorkReq = (TextView) view.findViewById(R.id.pwrTextNomorWorkReq);
                pwrTextJobOrder = (TextView) view.findViewById(R.id.pwrTextJobOrder);
                pwrTextTglAwal = (TextView) view.findViewById(R.id.pwrTextTglAwal);
                pwrTextDibuat = (TextView) view.findViewById(R.id.pwrTextDibuat);
                pwrTextCheckedBy = (TextView) view.findViewById(R.id.pwrTextCheckedBy);
                pwrTextApproval1 = (TextView) view.findViewById(R.id.pwrTextApproval1);
                pwrTextApproval2 = (TextView) view.findViewById(R.id.pwrTextApproval2);
                pwrTextApproval3 = (TextView) view.findViewById(R.id.pwrTextApproval3);

                pwrLayoutList = (LinearLayout) view.findViewById(R.id.pwrLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final WorkOrder workOrder) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashWorkRequestActivity.this, DetailWorkReqActivity.class);
                        intent.putExtra("detail", workOrder);
                        intent.putExtra("code", 1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashWorkRequestActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashWorkRequestActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashWorkRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "work-order");
                param.put("access", "" + "work-order:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashWorkRequestActivity.this).add(request);
    }
}