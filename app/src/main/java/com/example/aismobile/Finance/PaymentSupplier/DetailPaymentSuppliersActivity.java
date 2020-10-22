package com.example.aismobile.Finance.PaymentSupplier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.FinanceAccounting.PaymentSupplier;
import com.example.aismobile.Data.FinanceAccounting.PaymentSupplierDetail;
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

public class DetailPaymentSuppliersActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private PaymentSupplier paymentSupplier;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<PaymentSupplierDetail> paymentSupplierDetails;
    private ProgressDialog progressDialog;

    private double toDouble;
    private double grandTotal;
    private NumberFormat formatter;

    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textPsNumber;
    private TextView textStartDate;
    private TextView textEndDate;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textComment1;
    private TextView textApproval2;
    private TextView textApproval2Date;
    private TextView textComment2;
    private TextView textApproval3;
    private TextView textApproval3Date;
    private TextView textComment3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, akses3 = 0;
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
        setContentView(R.layout.activity_detail_payment_suppliers);

        Bundle bundle = getIntent().getExtras();
        paymentSupplier = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        paymentSupplierDetails = new ArrayList<>();

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
                if (approval != 1){
                    btnApprove1.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(paymentSupplierDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2){
                    btnApprove2.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(paymentSupplierDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3){
                    btnApprove3.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 3;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(paymentSupplierDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval == 1 && akses1 > 0){
                    if (paymentSupplier.getChecked_by().toLowerCase().contains("-".toLowerCase()) ||
                            paymentSupplier.getDone().toLowerCase().contains("Ya".toLowerCase()))
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i< paymentSupplierDetails.size(); i++)
                            updateApproval(String.valueOf(paymentSupplierDetails.get(i).getBudget_supplier_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                        updateApprovalId();
                    }
                } else if (approval == 2 && akses2 > 0){
                    if (paymentSupplier.getChecked_by().toLowerCase().contains("-".toLowerCase()) ||
                            paymentSupplier.getDone().toLowerCase().contains("Ya".toLowerCase()) ||
                            paymentSupplier.getApproval1().toLowerCase().contains("-".toLowerCase()))
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i< paymentSupplierDetails.size(); i++)
                            updateApproval(String.valueOf(paymentSupplierDetails.get(i).getBudget_supplier_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval3)).getText().toString());
                        updateApprovalId();
                    }
                } else if (approval == 3 && akses3 > 0){
                    if (paymentSupplier.getChecked_by().toLowerCase().contains("-".toLowerCase()) ||
                            paymentSupplier.getDone().toLowerCase().contains("Ya".toLowerCase()) ||
                            paymentSupplier.getApproval1().toLowerCase().contains("-".toLowerCase()) ||
                            paymentSupplier.getApproval2().toLowerCase().contains("-".toLowerCase()))
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else{
                        for (int i = 0; i< paymentSupplierDetails.size(); i++)
                            updateApproval(String.valueOf(paymentSupplierDetails.get(i).getBudget_supplier_detail_id()),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval1)).getText().toString(),
                                    ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.textApproval2)).getText().toString(),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval3)).getSelectedItem().toString());
                        updateApprovalId();
                    }
                } else Toast.makeText(DetailPaymentSuppliersActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
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

        textPsNumber = (TextView) findViewById(R.id.textPsNumber);
        textStartDate = (TextView) findViewById(R.id.textStartDate);
        textEndDate = (TextView) findViewById(R.id.textEndDate);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textComment1 = (TextView) findViewById(R.id.textComment1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval2Date = (TextView) findViewById(R.id.textApproval2Date);
        textComment2 = (TextView) findViewById(R.id.textComment2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textApproval3Date = (TextView) findViewById(R.id.textApproval3Date);
        textComment3 = (TextView) findViewById(R.id.textComment3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textPsNumber.setText(paymentSupplier.getBudget_supplier_number());
        textStartDate.setText(paymentSupplier.getStart_date());
        textEndDate.setText(paymentSupplier.getEnd_date());
        textCheckedBy.setText(paymentSupplier.getChecked_by());
        textCheckedDate.setText(paymentSupplier.getChecked_date());
        textApproval1.setText(paymentSupplier.getApproval1());
        textApproval1Date.setText(paymentSupplier.getApproval_date1());
        textComment1.setText(paymentSupplier.getApproval_comment1());
        textApproval2.setText(paymentSupplier.getApproval2());
        textApproval2Date.setText(paymentSupplier.getApproval_date2());
        textComment2.setText(paymentSupplier.getApproval_comment2());
        textApproval3.setText(paymentSupplier.getApproval3());
        textApproval3Date.setText(paymentSupplier.getApproval_date3());
        textComment3.setText(paymentSupplier.getApproval_comment3());
        textCatatan.setText(paymentSupplier.getNotes());
        textCreatedBy.setText(paymentSupplier.getCreated_by());
        textCreatedDate.setText(paymentSupplier.getCreated_date());
        textModifiedBy.setText(paymentSupplier.getModified_by());
        textModifiedDate.setText(paymentSupplier.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutDetail.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutDetail.setLayoutParams(params);
                menuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCatatan.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCatatan.setLayoutParams(params);
                menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutHistory.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutHistory.setLayoutParams(params);
                menuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });

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
                    akses3 = jsonObject.getInt("access3");
                } catch (JSONException e) {
                    Toast.makeText(DetailPaymentSuppliersActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPaymentSuppliersActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "14");
                return param;
            }
        };
        Volley.newRequestQueue(DetailPaymentSuppliersActivity.this).add(request);
    }

    public void changeColor(){
        btnApprove1.setTextColor(getResources().getColor(R.color.colorWhite));
        btnApprove2.setTextColor(getResources().getColor(R.color.colorWhite));
        btnApprove3.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void updateApproval(final String id, final String approve1, final String approve2, final String approve3){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_PAYMENT_SUPPLIER, new Response.Listener<String>() {
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
                param.put("approve1", approve1);
                param.put("approve2", approve2);
                param.put("approve3", approve3);
                return param;
            }
        };
        Volley.newRequestQueue(DetailPaymentSuppliersActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_PAYMENT_SUPPLIER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailPaymentSuppliersActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPaymentSuppliersActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("id", "" + paymentSupplier.getBudget_supplier_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("command", editCommand.getText().toString() + " ");
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailPaymentSuppliersActivity.this).add(request);
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }

    public void loadDetail(){
        progressDialog.show();
        recyclerView.setAdapter(null);
        paymentSupplierDetails.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PAYMENT_SUPPLIER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        grandTotal = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            paymentSupplierDetails.add(new PaymentSupplierDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));
                        adapter = new MyRecyclerViewAdapter(paymentSupplierDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailPaymentSuppliersActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailPaymentSuppliersActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPaymentSuppliersActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("psId", "" + paymentSupplier.getBudget_supplier_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPaymentSuppliersActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PaymentSupplierDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PaymentSupplierDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_budgeting_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textKeterangan.setText(mValues.get(position).getSupplier_id());
            holder.textDepartment.setText(mValues.get(position).getBank_account());
            holder.textProjectLocation.setText(mValues.get(position).getBank_name());
            holder.textToDepartment.setText(mValues.get(position).getName_of_bank_account());
            holder.textApproval1.setText(mValues.get(position).getBudget_supplier_app1());
            holder.textApproval2.setText(mValues.get(position).getBudget_supplier_app2());
            holder.textApproval3.setText(mValues.get(position).getBudget_supplier_app3());

            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (approval == 1 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.textApproval1.getLayoutParams();
                params.height = 0;
                holder.textApproval1.setLayoutParams(params);
            } else if (approval == 2 && code == 1) {
                params =  holder.editApproval2.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval2.setLayoutParams(params);
                params =  holder.textApproval2.getLayoutParams();
                params.height = 0;
                holder.textApproval2.setLayoutParams(params);
            } else if (approval == 3 && code == 1){
                params =  holder.editApproval3.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval3.setLayoutParams(params);
                params =  holder.textApproval3.getLayoutParams();
                params.height = 0;
                holder.textApproval3.setLayoutParams(params);
            }

            String[] approve = {"Approved", "Reject", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailPaymentSuppliersActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            holder.editApproval1.setAdapter(adapterApproval);
            holder.editApproval2.setAdapter(adapterApproval);
            holder.editApproval3.setAdapter(adapterApproval);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textKeterangan;
            public final TextView textDepartment;
            public final TextView textProjectLocation;
            public final TextView textToDepartment;
            public final TextView textNilai;
            public final TextView textApproval1;
            public final TextView textApproval2;
            public final TextView textApproval3;
            public final Spinner editApproval1;
            public final Spinner editApproval2;
            public final Spinner editApproval3;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textDepartment = (TextView) itemView.findViewById(R.id.textDepartment);
                textProjectLocation = (TextView) itemView.findViewById(R.id.textProjectLocation);
                textToDepartment = (TextView) itemView.findViewById(R.id.textToDepartment);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                textApproval2 = (TextView) itemView.findViewById(R.id.textApproval2);
                textApproval3 = (TextView) itemView.findViewById(R.id.textApproval3);
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);
                editApproval2 = (Spinner) itemView.findViewById(R.id.editApproval2);
                editApproval3 = (Spinner) itemView.findViewById(R.id.editApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}