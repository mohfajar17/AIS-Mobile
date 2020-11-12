package com.example.aismobile.Dashboard.ProposedBudget;

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
import com.example.aismobile.Data.Project.ProposedBudget;
import com.example.aismobile.Project.ProposedBudget.DetailProposedBudgetActivity;
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

public class DashProposedBudgetActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<ProposedBudget> proposedBudgets;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_proposed_budget);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        proposedBudgets = new ArrayList<>();
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
                proposedBudgets.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        proposedBudgets.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_PROPOSED_BUDGET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            proposedBudgets.add(new ProposedBudget(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(proposedBudgets, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashProposedBudgetActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashProposedBudgetActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashProposedBudgetActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<ProposedBudget> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<ProposedBudget> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_proposed_budget_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ppbTextNomorProposedBudget.setText(""+mValues.get(position).getCash_advance_number());
            holder.ppbTextRest.setText(""+mValues.get(position).getRest_from());
            holder.ppbTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.ppbTextPenanggungJwb.setText(""+mValues.get(position).getPerson_in_charge());
            holder.ppbTextTglPermintaan.setText(""+mValues.get(position).getRequisition_date());
            holder.ppbTextDueDate.setText(""+mValues.get(position).getDue_date());
            holder.ppbTextPaymentDate.setText(""+mValues.get(position).getPayment_date());
            holder.ppbTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.ppbTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.ppbTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.ppbTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getDone())==2)
                holder.ppbTextDone.setText("Tidak");
            else holder.ppbTextDone.setText("Ya");

            if (position%2==0)
                holder.ppbLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ppbLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getCash_advance_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView ppbTextNomorProposedBudget;
            public final TextView ppbTextRest;
            public final TextView ppbTextJobOrder;
            public final TextView ppbTextPenanggungJwb;
            public final TextView ppbTextTglPermintaan;
            public final TextView ppbTextDueDate;
            public final TextView ppbTextPaymentDate;
            public final TextView ppbTextDibuat;
            public final TextView ppbTextApproval1;
            public final TextView ppbTextApproval2;
            public final TextView ppbTextApproval3;
            public final TextView ppbTextDone;

            public final LinearLayout ppbLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ppbTextNomorProposedBudget = (TextView) view.findViewById(R.id.ppbTextNomorProposedBudget);
                ppbTextRest = (TextView) view.findViewById(R.id.ppbTextRest);
                ppbTextJobOrder = (TextView) view.findViewById(R.id.ppbTextJobOrder);
                ppbTextPenanggungJwb = (TextView) view.findViewById(R.id.ppbTextPenanggungJwb);
                ppbTextTglPermintaan = (TextView) view.findViewById(R.id.ppbTextTglPermintaan);
                ppbTextDueDate = (TextView) view.findViewById(R.id.ppbTextDueDate);
                ppbTextPaymentDate = (TextView) view.findViewById(R.id.ppbTextPaymentDate);
                ppbTextDibuat = (TextView) view.findViewById(R.id.ppbTextDibuat);
                ppbTextApproval1 = (TextView) view.findViewById(R.id.ppbTextApproval1);
                ppbTextApproval2 = (TextView) view.findViewById(R.id.ppbTextApproval2);
                ppbTextApproval3 = (TextView) view.findViewById(R.id.ppbTextApproval3);
                ppbTextDone = (TextView) view.findViewById(R.id.ppbTextDone);

                ppbLayoutList = (LinearLayout) view.findViewById(R.id.ppbLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final ProposedBudget proposedBudget) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashProposedBudgetActivity.this, DetailProposedBudgetActivity.class);
                        intent.putExtra("detail", proposedBudget);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashProposedBudgetActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashProposedBudgetActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashProposedBudgetActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "cash-advance");
                param.put("access", "" + "cash-advance:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashProposedBudgetActivity.this).add(request);
    }
}