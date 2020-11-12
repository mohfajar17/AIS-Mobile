package com.example.aismobile.Dashboard.CashProjectReport;

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
import com.example.aismobile.Data.Project.CashProjectReport;
import com.example.aismobile.Project.CashProject.DetailCashProjectActivity;
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

public class DashCashProjectReportActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<CashProjectReport> cashProjectReports;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_cash_project_report);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        cashProjectReports = new ArrayList<>();
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
                cashProjectReports.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        cashProjectReports.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_CASH_PROJECT_REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashProjectReports.add(new CashProjectReport(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(cashProjectReports, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashCashProjectReportActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCashProjectReportActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCashProjectReportActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashCashProjectReportActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<CashProjectReport> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<CashProjectReport> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_project_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pcpTextNomorCashProject.setText(""+mValues.get(position).getResponsbility_advance_number());
            holder.pcpTextProposedBudget.setText(""+mValues.get(position).getCash_advance_number());
            holder.pcpTextKetJobOrder.setText(""+mValues.get(position).getJob_order_id()); //untuk ket job order
            holder.pcpTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pcpTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pcpTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pcpTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getDone())==2)
                holder.pcpTextDone.setText("Tidak");
            else holder.pcpTextDone.setText("Ya");

            if (position%2==0)
                holder.pcpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pcpLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getResponsbility_advance_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView pcpTextNomorCashProject;
            public final TextView pcpTextProposedBudget;
            public final TextView pcpTextKetJobOrder;
            public final TextView pcpTextDibuat;
            public final TextView pcpTextApproval1;
            public final TextView pcpTextApproval2;
            public final TextView pcpTextApproval3;
            public final TextView pcpTextDone;

            public final LinearLayout pcpLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pcpTextNomorCashProject = (TextView) view.findViewById(R.id.pcpTextNomorCashProject);
                pcpTextProposedBudget = (TextView) view.findViewById(R.id.pcpTextProposedBudget);
                pcpTextKetJobOrder = (TextView) view.findViewById(R.id.pcpTextKetJobOrder);
                pcpTextDibuat = (TextView) view.findViewById(R.id.pcpTextDibuat);
                pcpTextApproval1 = (TextView) view.findViewById(R.id.pcpTextApproval1);
                pcpTextApproval2 = (TextView) view.findViewById(R.id.pcpTextApproval2);
                pcpTextApproval3 = (TextView) view.findViewById(R.id.pcpTextApproval3);
                pcpTextDone = (TextView) view.findViewById(R.id.pcpTextDone);

                pcpLayoutList = (LinearLayout) view.findViewById(R.id.pcpLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final CashProjectReport cashProjectReport) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashCashProjectReportActivity.this, DetailCashProjectActivity.class);
                        intent.putExtra("detail", cashProjectReport);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashCashProjectReportActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCashProjectReportActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCashProjectReportActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "responsbility-advance");
                param.put("access", "" + "respons-advance:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashCashProjectReportActivity.this).add(request);
    }
}