package com.example.aismobile.Dashboard.BankTransaction;

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
import com.example.aismobile.Data.FinanceAccounting.BankTransaction;
import com.example.aismobile.Finance.BankTransaction.DetailBankTransactionActivity;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
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

public class DashBankTransactionActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<BankTransaction> bankTransactions;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bank_transaction);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        bankTransactions = new ArrayList<>();
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
                bankTransactions.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        bankTransactions.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_BANK_TRANSACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankTransactions.add(new BankTransaction(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(bankTransactions, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashBankTransactionActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashBankTransactionActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashBankTransactionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashBankTransactionActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<BankTransaction> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<BankTransaction> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_bank_transaction_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.btTextNumber.setText(""+mValues.get(position).getBank_transaction_number());
            holder.btTextRekBank.setText(""+mValues.get(position).getBank_transaction_number());
            holder.btTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.btTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.btTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.btTextTglTrans.setText(""+mValues.get(position).getTransaction_date());
            holder.btTextStatus.setText(""+mValues.get(position).getStatus());
            holder.btTextReconciled.setText(""+mValues.get(position).getReconciled());

            double toDouble = Double.valueOf(mValues.get(position).getTotal_amount());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.btTextTotal.setText("Rp. "+ formatter.format((long) toDouble));

            if (position%2==0)
                holder.btLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.btLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getBank_transaction_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView btTextNumber;
            public final TextView btTextRekBank;
            public final TextView btTextCheckedBy;
            public final TextView btTextApproval1;
            public final TextView btTextApproval2;
            public final TextView btTextTglTrans;
            public final TextView btTextTotal;
            public final TextView btTextStatus;
            public final TextView btTextReconciled;

            public final LinearLayout btLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                btTextNumber = (TextView) view.findViewById(R.id.btTextNumber);
                btTextRekBank = (TextView) view.findViewById(R.id.btTextRekBank);
                btTextCheckedBy = (TextView) view.findViewById(R.id.btTextCheckedBy);
                btTextApproval1 = (TextView) view.findViewById(R.id.btTextApproval1);
                btTextApproval2 = (TextView) view.findViewById(R.id.btTextApproval2);
                btTextTglTrans = (TextView) view.findViewById(R.id.btTextTglTrans);
                btTextTotal = (TextView) view.findViewById(R.id.btTextTotal);
                btTextStatus = (TextView) view.findViewById(R.id.btTextStatus);
                btTextReconciled = (TextView) view.findViewById(R.id.btTextReconciled);

                btLayoutList = (LinearLayout) view.findViewById(R.id.btLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final BankTransaction materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashBankTransactionActivity.this, DetailBankTransactionActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashBankTransactionActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashBankTransactionActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashBankTransactionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "bank-transaction");
                param.put("access", "" + "bank-transaction:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashBankTransactionActivity.this).add(request);
    }
}