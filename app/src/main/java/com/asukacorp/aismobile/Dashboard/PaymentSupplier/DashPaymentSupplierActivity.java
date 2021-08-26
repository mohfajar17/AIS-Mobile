package com.asukacorp.aismobile.Dashboard.PaymentSupplier;

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
import com.asukacorp.aismobile.Data.FinanceAccounting.PaymentSupplier;
import com.asukacorp.aismobile.Finance.PaymentSupplier.DetailPaymentSuppliersActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashPaymentSupplierActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<PaymentSupplier> paymentSuppliers;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_payment_supplier);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        paymentSuppliers = new ArrayList<>();
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
                paymentSuppliers.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        paymentSuppliers.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_PAYMENT_SUPPLIER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            paymentSuppliers.add(new PaymentSupplier(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(paymentSuppliers, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashPaymentSupplierActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashPaymentSupplierActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashPaymentSupplierActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashPaymentSupplierActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PaymentSupplier> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PaymentSupplier> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_payment_supplier_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.psTextNumber.setText(""+mValues.get(position).getBudget_supplier_number());
            holder.psTextStartDate.setText(""+mValues.get(position).getStart_date());
            holder.psTextEndDate.setText(""+mValues.get(position).getEnd_date());
            holder.psTextCheckedBy.setText(""+mValues.get(position).getChecked_by());
            holder.psTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.psTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.psTextApproval3.setText(""+mValues.get(position).getApproval3());
            holder.psTextDone.setText(""+mValues.get(position).getDone());

            if (position%2==0)
                holder.psLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.psLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getBudget_supplier_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView psTextNumber;
            public final TextView psTextStartDate;
            public final TextView psTextEndDate;
            public final TextView psTextCheckedBy;
            public final TextView psTextApproval1;
            public final TextView psTextApproval2;
            public final TextView psTextApproval3;
            public final TextView psTextDone;

            public final LinearLayout psLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                psTextNumber = (TextView) view.findViewById(R.id.psTextNumber);
                psTextStartDate = (TextView) view.findViewById(R.id.psTextStartDate);
                psTextEndDate = (TextView) view.findViewById(R.id.psTextEndDate);
                psTextCheckedBy = (TextView) view.findViewById(R.id.psTextCheckedBy);
                psTextApproval1 = (TextView) view.findViewById(R.id.psTextApproval1);
                psTextApproval2 = (TextView) view.findViewById(R.id.psTextApproval2);
                psTextApproval3 = (TextView) view.findViewById(R.id.psTextApproval3);
                psTextDone = (TextView) view.findViewById(R.id.psTextDone);

                psLayoutList = (LinearLayout) view.findViewById(R.id.psLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final PaymentSupplier materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashPaymentSupplierActivity.this, DetailPaymentSuppliersActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashPaymentSupplierActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashPaymentSupplierActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashPaymentSupplierActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "budgeting-supplier");
                param.put("access", "" + "budgeting-supplier:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashPaymentSupplierActivity.this).add(request);
    }
}