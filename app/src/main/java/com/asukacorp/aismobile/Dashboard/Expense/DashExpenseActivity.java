package com.asukacorp.aismobile.Dashboard.Expense;

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
import com.asukacorp.aismobile.Data.FinanceAccounting.Expense;
import com.asukacorp.aismobile.Finance.Expenses.DetailExpensesActivity;
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

public class DashExpenseActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Expense> expenses;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_expense);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        expenses = new ArrayList<>();
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
                expenses.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        expenses.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_EXPENSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenses.add(new Expense(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(expenses, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashExpenseActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashExpenseActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashExpenseActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashExpenseActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<Expense> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<Expense> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_expenses_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.eTextNumber.setText(""+mValues.get(position).getExpenses_number());
            holder.eTextExpDesk.setText(""+mValues.get(position).getExpenses_desc());
            holder.eTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.eTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.eTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.eTextCashAdvance.setText(""+mValues.get(position).getAdvanced_number());
            holder.eTextDate.setText(""+mValues.get(position).getExpenses_date());
            holder.eTextRekBank.setText(""+mValues.get(position).getBank_account_name());
            holder.eTextDone.setText(""+mValues.get(position).getDone());

            double toDouble = Double.valueOf(mValues.get(position).getTotal_amount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.eTextTotal.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.eLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.eLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getExpenses_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView eTextNumber;
            public final TextView eTextExpDesk;
            public final TextView eTextCheckedBy;
            public final TextView eTextApproval1;
            public final TextView eTextApproval2;
            public final TextView eTextCashAdvance;
            public final TextView eTextDate;
            public final TextView eTextTotal;
            public final TextView eTextRekBank;
            public final TextView eTextDone;

            public final LinearLayout eLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                eTextNumber = (TextView) view.findViewById(R.id.eTextNumber);
                eTextExpDesk = (TextView) view.findViewById(R.id.eTextExpDesk);
                eTextCheckedBy = (TextView) view.findViewById(R.id.eTextCheckedBy);
                eTextApproval1 = (TextView) view.findViewById(R.id.eTextApproval1);
                eTextApproval2 = (TextView) view.findViewById(R.id.eTextApproval2);
                eTextCashAdvance = (TextView) view.findViewById(R.id.eTextCashAdvance);
                eTextDate = (TextView) view.findViewById(R.id.eTextDate);
                eTextTotal = (TextView) view.findViewById(R.id.eTextTotal);
                eTextRekBank = (TextView) view.findViewById(R.id.eTextRekBank);
                eTextDone = (TextView) view.findViewById(R.id.eTextDone);

                eLayoutList = (LinearLayout) view.findViewById(R.id.eLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Expense expense) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashExpenseActivity.this, DetailExpensesActivity.class);
                        intent.putExtra("detail", expense);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashExpenseActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashExpenseActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashExpenseActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "expenses");
                param.put("access", "" + "expenses:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashExpenseActivity.this).add(request);
    }
}