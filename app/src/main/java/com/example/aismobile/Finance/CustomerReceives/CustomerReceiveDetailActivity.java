package com.example.aismobile.Finance.CustomerReceives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.aismobile.Data.FinanceAccounting.CustomerInvoiceDetail;
import com.example.aismobile.Data.FinanceAccounting.CustomerReceives;
import com.example.aismobile.Data.FinanceAccounting.CustomerReceivesDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerReceiveDetailActivity extends AppCompatActivity {

    private CustomerReceives expense;
    private NumberFormat formatter;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<CustomerReceivesDetail> expenseDetails;
    private ProgressDialog progressDialog;

    private double toDouble;
    private double grandTotal;

    private ImageView buttonBack;
    private TextView textCrNumber;
    private TextView textCrDate;
    private TextView textReceiptNumber;
    private TextView textEkspedisi;
    private TextView textCompany;
    private TextView textDeliveryDate;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_receive_detail);

        Bundle bundle = getIntent().getExtras();
        expense = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        expenseDetails = new ArrayList<>();
        formatter = new DecimalFormat("#,###");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textCrNumber = (TextView) findViewById(R.id.textCrNumber);
        textCrDate = (TextView) findViewById(R.id.textCrDate);
        textReceiptNumber = (TextView) findViewById(R.id.textReceiptNumber);
        textEkspedisi = (TextView) findViewById(R.id.textEkspedisi);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textDeliveryDate = (TextView) findViewById(R.id.textDeliveryDate);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textCrNumber.setText(expense.getCustomer_receive_number());
        textCrDate.setText(expense.getCustomer_receive_date());
        textReceiptNumber.setText(expense.getReceipt_number());
        textEkspedisi.setText(expense.getEkspedisi_id());
        textCompany.setText(expense.getCompany_name());
        textDeliveryDate.setText(expense.getDelivery_date());
        textCreatedBy.setText(expense.getCreated_by());
        textCreatedDate.setText(expense.getCreated_date());
        textModifiedBy.setText(expense.getModified_by());
        textModifiedDate.setText(expense.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_RECEIVES_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    grandTotal = 0;
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenseDetails.add(new CustomerReceivesDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));

                        adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(CustomerReceiveDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerReceiveDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CustomerReceiveDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("crId", "" + expense.getCustomer_receive_id());
                return param;
            }
        };
        Volley.newRequestQueue(CustomerReceiveDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<CustomerReceivesDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<CustomerReceivesDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_customer_receive_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textCustomerInvoice.setText(mValues.get(position).getSales_order_invoice_number());
            holder.textDescription.setText(mValues.get(position).getSales_order_invoice_description());

            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textAmount.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textCustomerInvoice;
            public final TextView textDescription;
            public final TextView textAmount;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textCustomerInvoice = (TextView) itemView.findViewById(R.id.textCustomerInvoice);
                textDescription = (TextView) itemView.findViewById(R.id.textDescription);
                textAmount = (TextView) itemView.findViewById(R.id.textAmount);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}