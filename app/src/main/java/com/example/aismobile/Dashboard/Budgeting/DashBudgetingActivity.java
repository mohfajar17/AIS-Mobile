package com.example.aismobile.Dashboard.Budgeting;

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
import com.example.aismobile.Data.FinanceAccounting.Budgeting;
import com.example.aismobile.Finance.Budgeting.DetailBudgetingActivity;
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

public class DashBudgetingActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Budgeting> budgetings;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_budgeting);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        budgetings = new ArrayList<>();
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
                budgetings.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        budgetings.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_BUDGETING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetings.add(new Budgeting(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(budgetings, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashBudgetingActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashBudgetingActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashBudgetingActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<Budgeting> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<Budgeting> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_budgeting_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.bTextNumber.setText(""+mValues.get(position).getBudget_number());
            holder.bTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.bTextStartDate.setText(""+mValues.get(position).getStart_date());
            holder.bTextEndDate.setText(""+mValues.get(position).getEnd_date());
            holder.bTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.bTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.bTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.bTextApproval3.setText(""+mValues.get(position).getApproval3());
            holder.bTextDone.setText(""+mValues.get(position).getDone());

            if (position%2==0)
                holder.bLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.bLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getBudget_id(), mValues.get(position));
                    Intent intent = new Intent(DashBudgetingActivity.this, DetailBudgetingActivity.class);
                    intent.putExtra("detail", mValues.get(position));
                    intent.putExtra("code", 1);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView bTextNumber;
            public final TextView bTextDibuat;
            public final TextView bTextStartDate;
            public final TextView bTextEndDate;
            public final TextView bTextCheckedBy;
            public final TextView bTextApproval1;
            public final TextView bTextApproval2;
            public final TextView bTextApproval3;
            public final TextView bTextDone;

            public final LinearLayout bLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                bTextNumber = (TextView) view.findViewById(R.id.bTextNumber);
                bTextDibuat = (TextView) view.findViewById(R.id.bTextDibuat);
                bTextStartDate = (TextView) view.findViewById(R.id.bTextStartDate);
                bTextEndDate = (TextView) view.findViewById(R.id.bTextEndDate);
                bTextCheckedBy = (TextView) view.findViewById(R.id.bTextCheckedBy);
                bTextApproval1 = (TextView) view.findViewById(R.id.bTextApproval1);
                bTextApproval2 = (TextView) view.findViewById(R.id.bTextApproval2);
                bTextApproval3 = (TextView) view.findViewById(R.id.bTextApproval3);
                bTextDone = (TextView) view.findViewById(R.id.bTextDone);

                bLayoutList = (LinearLayout) view.findViewById(R.id.bLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Budgeting materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashBudgetingActivity.this, DetailBudgetingActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashBudgetingActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashBudgetingActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "budgeting");
                param.put("access", "" + "budgeting:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashBudgetingActivity.this).add(request);
    }
}