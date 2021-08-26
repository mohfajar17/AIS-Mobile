package com.asukacorp.aismobile.Finance.Expenses;

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
import android.widget.Spinner;
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
import com.asukacorp.aismobile.Data.FinanceAccounting.ExpenseDetail;
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

public class DetailExpensesActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
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

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, loadAgain = 0;
    private ArrayAdapter<String> adapterApproval;
    private ArrayAdapter<String> adapterChecked;
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
        setContentView(R.layout.activity_detail_expenses);

        Bundle bundle = getIntent().getExtras();
        expense = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

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
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                    recyclerView.setAdapter(adapter);
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
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                    recyclerView.setAdapter(adapter);
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
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (approval == 1 && akses1 > 0){
                    if (expense.getChecked_by().toLowerCase().contains("-".toLowerCase()) ||
                            expense.getDone().toLowerCase().contains("Ya".toLowerCase()))
                        Toast.makeText(DetailExpensesActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i<expenseDetails.size(); i++) {
                            if (i == expenseDetails.size()-1)
                                loadAgain = 1;

                            updateApproval(String.valueOf(expenseDetails.get(i).getExpenses_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApprove2)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textCheck)).getText().toString());
                        }
                        updateApprovalId();
                        textApproval1.setText(sharedPrefManager.getUserDisplayName());
                        textApproval1Date.setText(dateFormater.format(dateObj));
                        textComment1.setText(editCommand.getText().toString());
                    }
                } else if (approval == 2 && akses2 > 0){
                    if (expense.getChecked_by().toLowerCase().contains("-".toLowerCase()) ||
                            expense.getDone().toLowerCase().contains("Ya".toLowerCase()) ||
                            expense.getApproval1().toLowerCase().contains("-".toLowerCase()))
                        Toast.makeText(DetailExpensesActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i<expenseDetails.size(); i++) {
                            if (i == expenseDetails.size()-1)
                                loadAgain = 1;

                            updateApproval(String.valueOf(expenseDetails.get(i).getExpenses_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApprove1)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textCheck)).getText().toString());
                        }
                        updateApprovalId();
                        textApproval2.setText(sharedPrefManager.getUserDisplayName());
                        textApproval2Date.setText(dateFormater.format(dateObj));
                        textComment2.setText(editCommand.getText().toString());
                    }
                } else if (approval == 3){
                    for (int i = 0; i<expenseDetails.size(); i++) {
                        if (i == expenseDetails.size()-1)
                            loadAgain = 1;

                        updateApproval(String.valueOf(expenseDetails.get(i).getExpenses_detail_id()),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApprove1)).getText().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApprove2)).getText().toString(),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval3)).getSelectedItem().toString());
                    }
                    updateApprovalId();
                    textCheckedBy.setText(sharedPrefManager.getUserDisplayName());
                    textCheckedDate.setText(dateFormater.format(dateObj));
                    textCheckedComment.setText(editCommand.getText().toString());
                } else Toast.makeText(DetailExpensesActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
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

        if (!expense.getExpenses_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(context, R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/expenses/"+ expense.getExpenses_file_name());
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
                    Toast.makeText(DetailExpensesActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailExpensesActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "16");
                return param;
            }
        };
        Volley.newRequestQueue(DetailExpensesActivity.this).add(request);
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

    public void updateApproval(final String id, final String approve1, final String approve2, final String approve3){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_EXPENSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (loadAgain == 1) {
                            loadDetail();
                            loadAgain = 0;
                        }
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
                param.put("approve1", approve1);
                param.put("approve2", approve2);
                param.put("approve3", approve3);
                return param;
            }
        };
        Volley.newRequestQueue(DetailExpensesActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_EXPENSES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        if (approval == 1)
                            expense.setApproval1(sharedPrefManager.getUserDisplayName());
                        else if (approval == 2)
                            expense.setApproval2(sharedPrefManager.getUserDisplayName());
                        else if (approval == 3)
                            expense.setChecked_by(sharedPrefManager.getUserDisplayName());
                        Toast.makeText(DetailExpensesActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailExpensesActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailExpensesActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
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
                param.put("id", "" + expense.getExpenses_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailExpensesActivity.this).add(request);
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
                        recyclerView.setAdapter(null);
                        expenseDetails.clear();

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            expenseDetails.add(new ExpenseDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal) + " ");

                        adapter = new MyRecyclerViewAdapter(expenseDetails, context);
                        recyclerView.setAdapter(adapter);

                        changeColor();
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

            if (approval == 1 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.textApprove1.getLayoutParams();
                params.height = 0;
                holder.textApprove1.setLayoutParams(params);
            } else if (approval == 2 && code == 1) {
                params =  holder.editApproval2.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval2.setLayoutParams(params);
                params =  holder.textApprove2.getLayoutParams();
                params.height = 0;
                holder.textApprove2.setLayoutParams(params);
            } else if (approval == 3 && code == 1){
                params =  holder.editApproval3.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval3.setLayoutParams(params);
                params =  holder.textCheck.getLayoutParams();
                params.height = 0;
                holder.textCheck.setLayoutParams(params);
            }

            String[] approve = {"Approved", "Reject", "-"};
            String[] check = {"Approved", "Pending", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailExpensesActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            adapterChecked = new ArrayAdapter<String>(DetailExpensesActivity.this, android.R.layout.simple_spinner_dropdown_item, check);
            holder.editApproval1.setAdapter(adapterApproval);
            holder.editApproval2.setAdapter(adapterApproval);
            holder.editApproval3.setAdapter(adapterChecked);
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
            public final Spinner editApproval1;
            public final Spinner editApproval2;
            public final Spinner editApproval3;

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
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);
                editApproval2 = (Spinner) itemView.findViewById(R.id.editApproval2);
                editApproval3 = (Spinner) itemView.findViewById(R.id.editApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}