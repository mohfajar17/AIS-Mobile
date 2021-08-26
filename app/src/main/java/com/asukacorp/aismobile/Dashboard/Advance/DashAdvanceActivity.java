package com.asukacorp.aismobile.Dashboard.Advance;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.FinanceAccounting.CashAdvance;
import com.asukacorp.aismobile.Finance.CashAdvance.DetailCashAdvanceActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashAdvanceActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<CashAdvance> cashAdvances;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_advance);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        cashAdvances = new ArrayList<>();
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
                cashAdvances.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        cashAdvances.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_CASH_ADVANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashAdvances.add(new CashAdvance(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(cashAdvances, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashAdvanceActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashAdvanceActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashAdvanceActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashAdvanceActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<CashAdvance> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<CashAdvance> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cash_advance_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.caTextNumber.setText(""+mValues.get(position).getAdvanced_number());
            holder.caTextDate.setText(""+mValues.get(position).getAdvanced_date());
            holder.caTextAdvancedFor.setText(""+mValues.get(position).getAdvanced_for());
            holder.caTextReceived.setText(""+mValues.get(position).getReceived_by());
            holder.caTextStatus.setText(""+mValues.get(position).getStatus());

            double toDouble = Double.valueOf(mValues.get(position).getAmount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.caTextNilai.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.caLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.caLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getAdvanced_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView caTextNumber;
            public final TextView caTextDate;
            public final TextView caTextAdvancedFor;
            public final TextView caTextReceived;
            public final TextView caTextNilai;
            public final TextView caTextStatus;

            public final LinearLayout caLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                caTextNumber = (TextView) view.findViewById(R.id.caTextNumber);
                caTextDate = (TextView) view.findViewById(R.id.caTextDate);
                caTextAdvancedFor = (TextView) view.findViewById(R.id.caTextAdvancedFor);
                caTextReceived = (TextView) view.findViewById(R.id.caTextReceived);
                caTextNilai = (TextView) view.findViewById(R.id.caTextNilai);
                caTextStatus = (TextView) view.findViewById(R.id.caTextStatus);

                caLayoutList = (LinearLayout) view.findViewById(R.id.caLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final CashAdvance cashAdvance) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashAdvanceActivity.this, DetailCashAdvanceActivity.class);
                        intent.putExtra("detail", cashAdvance);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashAdvanceActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashAdvanceActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashAdvanceActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(DashAdvanceActivity.this).add(request);
    }
}