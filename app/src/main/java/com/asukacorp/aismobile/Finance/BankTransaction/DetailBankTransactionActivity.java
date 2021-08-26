package com.asukacorp.aismobile.Finance.BankTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.asukacorp.aismobile.Data.FinanceAccounting.BankTransaction;
import com.asukacorp.aismobile.Data.FinanceAccounting.BankTransactionDetail;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBankTransactionActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private BankTransaction bankTransaction;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<BankTransactionDetail> bankTransactionDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private ImageView downloadAtachment;
    private TextView textNumber;
    private TextView textBtNumber;
    private TextView textKeterangan;
    private TextView textRekBank;
    private TextView textAccountNumber;
    private TextView textTransactionNumber;
    private TextView textTotalAmount;
    private TextView textTglTrans;
    private TextView textReconciledBy;
    private TextView textTglRekon;
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

    private double toDouble;
    private double grandTotal;
    private double totalPenyesuaian;
    private NumberFormat formatter;

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private TextView btnApprove3;
    private TextView btnSaveApprove;
    private EditText editCommand;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bank_transaction);

        Bundle bundle = getIntent().getExtras();
        bankTransaction = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        bankTransactionDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        layoutApproval = (LinearLayout) findViewById(R.id.layoutApproval);
        if (code > 0){
            params = layoutApproval.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutApproval.setLayoutParams(params);
            loadAccess();
        }
        btnApprove1 = (TextView) findViewById(R.id.btnApprove1);
        btnApprove2 = (TextView) findViewById(R.id.btnApprove2);
        btnApprove3 = (TextView) findViewById(R.id.btnApprove3);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        editCommand = (EditText) findViewById(R.id.editCommand);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1 && textApproval1.getText().toString().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && textApproval2.getText().toString().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3 && textCheckedBy.getText().toString().matches("-")){
                    btnApprove3.setBackgroundResource(R.drawable.circle_red);
                    approval = 3;
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0){
                    if (bankTransaction.getChecked_by().matches("-") ||
                            bankTransaction.getReconciled().toLowerCase().contains("Ya".toLowerCase()))
                        Toast.makeText(DetailBankTransactionActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i<bankTransactionDetails.size(); i++)
                            updateApproval(String.valueOf(bankTransactionDetails.get(i).getBank_transaction_detail_id()));
                        updateApprovalId();
                        textApproval1.setText(sharedPrefManager.getUserDisplayName());
                        textApproval1Date.setText(dateFormater.format(dateObj));
                        textComment1.setText(editCommand.getText().toString());
                    }
                } else if (approval == 2 && akses2 > 0){
                    if (bankTransaction.getChecked_by().matches("-") ||
                            bankTransaction.getReconciled().toLowerCase().contains("Ya".toLowerCase()) ||
                            bankTransaction.getApproval1().matches("-"))
                        Toast.makeText(DetailBankTransactionActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i<bankTransactionDetails.size(); i++)
                            updateApproval(String.valueOf(bankTransactionDetails.get(i).getBank_transaction_detail_id()));
                        updateApprovalId();
                        textApproval2.setText(sharedPrefManager.getUserDisplayName());
                        textApproval2Date.setText(dateFormater.format(dateObj));
                        textComment2.setText(editCommand.getText().toString());
                    }
                } else if (approval == 3){
                    updateApprovalId();
                    textCheckedBy.setText(sharedPrefManager.getUserDisplayName());
                    textCheckedDate.setText(dateFormater.format(dateObj));
                    textCheckedComment.setText(editCommand.getText().toString());
                } else Toast.makeText(DetailBankTransactionActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
                changeColor();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetail();
                changeColor();
                approval = 0;
            }
        });

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textBtNumber = (TextView) findViewById(R.id.textBtNumber);
        textKeterangan = (TextView) findViewById(R.id.textKeterangan);
        textRekBank = (TextView) findViewById(R.id.textRekBank);
        textAccountNumber = (TextView) findViewById(R.id.textAccountNumber);
        textTransactionNumber = (TextView) findViewById(R.id.textTransactionNumber);
        textTotalAmount = (TextView) findViewById(R.id.textTotalAmount);
        textTglTrans = (TextView) findViewById(R.id.textTglTrans);
        textReconciledBy = (TextView) findViewById(R.id.textReconciledBy);
        textTglRekon = (TextView) findViewById(R.id.textTglRekon);
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

        textNumber.setText("Bank Transaction #" + bankTransaction.getBank_transaction_number());
        textBtNumber.setText(bankTransaction.getBank_transaction_number());
        textKeterangan.setText(bankTransaction.getBank_transaction_description());
        textRekBank.setText(bankTransaction.getBank_name());
        textAccountNumber.setText(bankTransaction.getBank_account_number());
        textTransactionNumber.setText(bankTransaction.getBank_transaction_number());
        textTotalAmount.setText(bankTransaction.getTotal_amount());
        textTglTrans.setText(bankTransaction.getTransaction_date());
        textReconciledBy.setText(bankTransaction.getReconciled_by());
        textTglRekon.setText(bankTransaction.getReconciled_date());
        textCheckedBy.setText(bankTransaction.getChecked_by());
        textCheckedDate.setText(bankTransaction.getChecked_date());
        textCheckedComment.setText(bankTransaction.getChecked_comment());
        textApproval1.setText(bankTransaction.getApproval1());
        textApproval1Date.setText(bankTransaction.getApproval_date1());
        textComment1.setText(bankTransaction.getApproval_comment1());
        textApproval2.setText(bankTransaction.getApproval2());
        textApproval2Date.setText(bankTransaction.getApproval_date2());
        textComment2.setText(bankTransaction.getApproval_comment2());
        textCatatan.setText(bankTransaction.getNotes());
        textCreatedBy.setText(bankTransaction.getCreated_by());
        textCreatedDate.setText(bankTransaction.getCreated_date());
        textModifiedBy.setText(bankTransaction.getModified_by());
        textModifiedDate.setText(bankTransaction.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!bankTransaction.getBank_transaction_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(context, R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/bankTransaction/" + bankTransaction.getBank_transaction_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }

        changeColor();
        loadDetail();
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                    akses2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    Toast.makeText(DetailBankTransactionActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBankTransactionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "15");
                return param;
            }
        };
        Volley.newRequestQueue(DetailBankTransactionActivity.this).add(request);
    }

    public void changeColor(){
        if (textApproval1.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            if (textCheckedBy.getText().toString().matches("-"))
                btnApprove3.setBackgroundResource(R.drawable.circle_green);
            else btnApprove3.setBackgroundResource(R.drawable.circle_blue_new);
        } else if (textApproval2.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
            if (textCheckedBy.getText().toString().matches("-"))
                btnApprove3.setBackgroundResource(R.drawable.circle_green);
            else btnApprove3.setBackgroundResource(R.drawable.circle_blue_new);
        } else if (!textApproval1.getText().toString().matches("-") &&
                !textApproval2.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
            btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);
            if (textCheckedBy.getText().toString().matches("-"))
                btnApprove3.setBackgroundResource(R.drawable.circle_green);
            else btnApprove3.setBackgroundResource(R.drawable.circle_blue_new);
        } else {
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
        }
    }

    public void updateApproval(final String id){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_BANK_TRANSACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", id);
                return param;
            }
        };
        Volley.newRequestQueue(DetailBankTransactionActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_BANK_TRANSACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (approval == 1)
                            bankTransaction.setApproval1(sharedPrefManager.getUserDisplayName());
                        else if (approval == 2)
                            bankTransaction.setApproval2(sharedPrefManager.getUserDisplayName());
                        else if (approval == 3)
                            bankTransaction.setChecked_by(sharedPrefManager.getUserDisplayName());
                        Toast.makeText(DetailBankTransactionActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailBankTransactionActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailBankTransactionActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBankTransactionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + bankTransaction.getBank_transaction_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailBankTransactionActivity.this).add(request);
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BANK_TRANSACTION_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        recyclerView.setAdapter(null);
                        bankTransactionDetails.clear();

                        grandTotal = 0;
                        totalPenyesuaian = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            bankTransactionDetails.add(new BankTransactionDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                            totalPenyesuaian += Math.abs(jsonArray.getJSONObject(i).getDouble("adjustment_value"));
                        }
                        toDouble = grandTotal - totalPenyesuaian;
                        textGrandTotal.setText("Rp. " + formatter.format((long) toDouble));

                        adapter = new MyRecyclerViewAdapter(bankTransactionDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailBankTransactionActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailBankTransactionActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBankTransactionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("btId", "" + bankTransaction.getBank_transaction_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailBankTransactionActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<BankTransactionDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<BankTransactionDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_bank_transaction_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textSupplierInvoice.setText(mValues.get(position).getSupplier_invoice());
            holder.textProposedBudget.setText(mValues.get(position).getProposed_budget());
            holder.textCustomerInvoice.setText(mValues.get(position).getCustomer_invoice());
            holder.textCashProjectReport.setText(mValues.get(position).getCash_project_report());
            holder.textDestination.setText(mValues.get(position).getDestination());
            holder.textTransactionDetailName.setText(mValues.get(position).getTransaction_detail_name());
            holder.textAccount.setText(mValues.get(position).getCategory() + " | " + mValues.get(position).getBank_transaction_type_name());

            toDouble = Math.abs(Double.valueOf(mValues.get(position).getAdjustment_value()));
            holder.textPenyesuaian.setText(formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText(formatter.format((long) toDouble));

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
            public final TextView textSupplierInvoice;
            public final TextView textProposedBudget;
            public final TextView textCustomerInvoice;
            public final TextView textCashProjectReport;
            public final TextView textTransactionDetailName;
            public final TextView textDestination;
            public final TextView textPenyesuaian;
            public final TextView textNilai;
            public final TextView textAccount;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textSupplierInvoice = (TextView) itemView.findViewById(R.id.textSupplierInvoice);
                textProposedBudget = (TextView) itemView.findViewById(R.id.textProposedBudget);
                textCustomerInvoice = (TextView) itemView.findViewById(R.id.textCustomerInvoice);
                textCashProjectReport = (TextView) itemView.findViewById(R.id.textCashProjectReport);
                textTransactionDetailName = (TextView) itemView.findViewById(R.id.textTransactionDetailName);
                textDestination = (TextView) itemView.findViewById(R.id.textDestination);
                textPenyesuaian = (TextView) itemView.findViewById(R.id.textPenyesuaian);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textAccount = (TextView) itemView.findViewById(R.id.textAccount);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}