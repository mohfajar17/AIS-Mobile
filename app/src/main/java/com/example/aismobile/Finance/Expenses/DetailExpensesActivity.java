package com.example.aismobile.Finance.Expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.aismobile.Data.FinanceAccounting.Expense;
import com.example.aismobile.Data.FinanceAccounting.ExpenseDetail;
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

public class DetailExpensesActivity extends AppCompatActivity {

    private Expense expense;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<ExpenseDetail> expenseDetails;
    private ProgressDialog progressDialog;
    private NumberFormat formatter;

    private ImageView buttonBack;
    private ImageView downloadAtachment;
    private TextView textNumber;
    private TextView textKeterangan;
    private TextView textCashAdvance;
    private TextView textExpensesDate;
    private TextView textRekBank;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textCheckedComment;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textComment1;
    private TextView textApproval2;
    private TextView textApproval2Date;
    private TextView textComment2;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    private double grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expenses);

        Bundle bundle = getIntent().getExtras();
        expense = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        expenseDetails = new ArrayList<>();
        formatter = new DecimalFormat("#,###");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textCashAdvance = (TextView) findViewById(R.id.textCashAdvance);
        textExpensesDate = (TextView) findViewById(R.id.textExpensesDate);
        textRekBank = (TextView) findViewById(R.id.textRekBank);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textCheckedComment = (TextView) findViewById(R.id.textCheckedComment);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textComment1 = (TextView) findViewById(R.id.textComment1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval2Date = (TextView) findViewById(R.id.textApproval2Date);
        textComment2 = (TextView) findViewById(R.id.textComment2);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textNumber.setText("Expenses #" + expense.getExpenses_number());
        textKeterangan.setText("Keterangan : " + expense.getExpenses_desc());
        textCashAdvance.setText(expense.getAdvanced_number());
        textExpensesDate.setText(expense.getExpenses_date());
        textRekBank.setText(expense.getBank_account_name());
        textCheckedBy.setText(expense.getChecked_by());
        textCheckedDate.setText(expense.getChecked_date());
        textCheckedComment.setText(expense.getChecked_comment());
        textApproval1.setText(expense.getApproval1());
        textApproval1Date.setText(expense.getApproval_date1());
        textComment1.setText(expense.getApproval_comment1());
        textApproval2.setText(expense.getApproval2());
        textApproval2Date.setText(expense.getApproval_date2());
        textComment2.setText(expense.getApproval_comment2());
        textCatatan.setText(expense.getNotes());
        textCreatedBy.setText(expense.getCreated_by());
        textCreatedDate.setText(expense.getCreated_date());
        textModifiedBy.setText(expense.getModified_by());
        textModifiedDate.setText(expense.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/expenses/"+ expense.getExpenses_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EXPENSE_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    grandTotal = 0;
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenseDetails.add(new ExpenseDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal) + " ");

                        adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailExpensesActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailExpensesActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailExpensesActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("expensesId", "" + expense.getExpenses_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailExpensesActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<ExpenseDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<ExpenseDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_expenses_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textNamaItem.setText(mValues.get(position).getItem_name());
            holder.textJobOrder.setText(mValues.get(position).getJob_order_number());
            holder.textAccount.setText(mValues.get(position).getBank_transaction_type_name() + " | " + mValues.get(position).getCategory());
            holder.textCheck.setText(mValues.get(position).getChecked_app());
            holder.textApprove1.setText(mValues.get(position).getExpenses_app1());
            holder.textApprove2.setText(mValues.get(position).getExpenses_app2());

            double toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. " + formatter.format((long) toDouble));

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
            public final TextView textNamaItem;
            public final TextView textJobOrder;
            public final TextView textAccount;
            public final TextView textNilai;
            public final TextView textCheck;
            public final TextView textApprove1;
            public final TextView textApprove2;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textNamaItem = (TextView) itemView.findViewById(R.id.textNamaItem);
                textJobOrder = (TextView) itemView.findViewById(R.id.textJobOrder);
                textAccount = (TextView) itemView.findViewById(R.id.textAccount);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textCheck = (TextView) itemView.findViewById(R.id.textCheck);
                textApprove1 = (TextView) itemView.findViewById(R.id.textApprove1);
                textApprove2 = (TextView) itemView.findViewById(R.id.textApprove2);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}