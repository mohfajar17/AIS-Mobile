package com.example.aismobile.Purchasing.PurchaseOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseOrder;
import com.example.aismobile.Data.Purchasing.PurchaseOrderDetail;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;
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

public class DetailPurchaseOrderActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private PurchaseOrder purchaseOrder;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<PurchaseOrderDetail> purchaseOrderDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private ImageView downloadAtachment;
    private TextView textJobOrder;
    private TextView textKeteranganJobOrder;
    private TextView textJenisPembelian;
    private TextView textContract;
    private TextView textSupplier;
    private TextView textNomorPenawaran;
    private TextView textTglPenawaran;
    private TextView textTglAwal;
    private TextView textTglKedatangan;
    private TextView textTerminPembayaran;
    private TextView textPaymentDesc;
    private TextView textPersetujuan;
    private TextView textJenisPajak;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textPoComment;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    private TextView textTotal;
    private TextView textBudget;
    private TextView textDiscount;
    private TextView textEfisiensi;
    private TextView textDPP;
    private TextView textPajak;
    private TextView textGrandTotal;

    private double total, budget, discount, efisiensi, efisiensiPerc, dpp, pajak, grandTotal;

    private int code = 0, approval = 0, approvalAssignId = 0, user = 0, akses1 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private EditText editCommand;
    private TextView btnSaveApprove;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_purchase_order);

        Bundle bundle = getIntent().getExtras();
        purchaseOrder = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);
        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        purchaseOrderDetails = new ArrayList<>();

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
        editCommand = (EditText) findViewById(R.id.editCommand);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1 && textApproval1.getText().toString().matches("-")){
                    btnApprove1.setBackgroundResource(R.drawable.circle_red);
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(purchaseOrderDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2 && textPersetujuan.getText().toString().matches("-")){
                    btnApprove2.setBackgroundResource(R.drawable.circle_red);
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(purchaseOrderDetails, context);
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
                    if (purchaseOrder.getChecked_by().toLowerCase().contains("-".toLowerCase()))
                        Toast.makeText(DetailPurchaseOrderActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i<purchaseOrderDetails.size(); i++)
                            updateApproval(String.valueOf(purchaseOrderDetails.get(i).getMaterial_request_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString());
                        updateApprovalId();
                        textApproval1.setText(sharedPrefManager.getUserDisplayName());
                        textApproval1Date.setText(dateFormater.format(dateObj));
                        textPoComment.setText(editCommand.getText().toString());
                    }
                } else if (approval == 2){
                    updateApprovalId();
                } else Toast.makeText(DetailPurchaseOrderActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approval = 0;
                changeColor();
                loadDetail();
            }
        });

        textTotal = (TextView) findViewById(R.id.textTotal);
        textBudget = (TextView) findViewById(R.id.textBudget);
        textDiscount = (TextView) findViewById(R.id.textDiscount);
        textEfisiensi = (TextView) findViewById(R.id.textEfisiensi);
        textDPP = (TextView) findViewById(R.id.textDPP);
        textPajak = (TextView) findViewById(R.id.textPajak);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textKeteranganJobOrder = (TextView) findViewById(R.id.textKeteranganJobOrder);
        textJenisPembelian = (TextView) findViewById(R.id.textJenisPembelian);
        textContract = (TextView) findViewById(R.id.textContract);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textNomorPenawaran = (TextView) findViewById(R.id.textNomorPenawaran);
        textTglPenawaran = (TextView) findViewById(R.id.textTglPenawaran);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglKedatangan = (TextView) findViewById(R.id.textTglKedatangan);
        textTerminPembayaran = (TextView) findViewById(R.id.textTerminPembayaran);
        textPaymentDesc = (TextView) findViewById(R.id.textPaymentDesc);
        textPersetujuan = (TextView) findViewById(R.id.textPersetujuan);
        textJenisPajak = (TextView) findViewById(R.id.textJenisPajak);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textPoComment = (TextView) findViewById(R.id.textPoComment);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textJenisPembelian.setText(purchaseOrder.getPurchase_order_type_id());
        textJobOrder.setText(purchaseOrder.getJob_order_number());
        textKeteranganJobOrder.setText(purchaseOrder.getJob_order_description());
        textContract.setText(purchaseOrder.getContract_agreement_id());
        textSupplier.setText(purchaseOrder.getSupplier_id());
        textNomorPenawaran.setText(purchaseOrder.getPurchase_quotation_number());
        textTglPenawaran.setText(purchaseOrder.getPurchase_quotation_date());
        textTglAwal.setText(purchaseOrder.getBegin_date());
        textTglKedatangan.setText(purchaseOrder.getEnd_date());
        textTerminPembayaran.setText(purchaseOrder.getPayment_term_id());
        textPaymentDesc.setText(purchaseOrder.getPayment_desc());
        textPersetujuan.setText(purchaseOrder.getApproval_assign_id());
        textJenisPajak.setText(purchaseOrder.getTax_type_id());
        textCheckedBy.setText(purchaseOrder.getChecked_by());
        textCheckedDate.setText(purchaseOrder.getChecked_date());
        textApproval1.setText(purchaseOrder.getPo_approval1());
        textApproval1Date.setText(purchaseOrder.getPo_approval_date1());
        textCatatan.setText(purchaseOrder.getNotes());
        textPoComment.setText(purchaseOrder.getPo_comment1());
        textCreatedBy.setText(purchaseOrder.getCreated_by());
        textCreatedDate.setText(purchaseOrder.getCreated_date());
        textModifiedBy.setText(purchaseOrder.getModified_by());
        textModifiedDate.setText(purchaseOrder.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Detail PO #" + purchaseOrder.getPurchase_order_number());
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
        if (!purchaseOrder.getPurchase_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(context, R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/purchaseQuotation/"+ purchaseOrder.getPurchase_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }

        changeColor();
        loadDetail();
        loadApprovalAssign();
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "8");
                return param;
            }
        };
        Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
    }

    private void loadApprovalAssign() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_ASSIGN_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        approvalAssignId = jsonArray.getJSONObject(0).getInt("approval_assign_id");
                    } else approvalAssignId = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + sharedPrefManager.getEmployeeId());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
    }

    public void changeColor(){
        if (textPersetujuan.getText().toString().matches("-")){
            btnApprove2.setBackgroundResource(R.drawable.circle_green);
        } else btnApprove2.setBackgroundResource(R.drawable.circle_blue_new);

        if (textApproval1.getText().toString().matches("-")){
            btnApprove1.setBackgroundResource(R.drawable.circle_green);
        } else btnApprove1.setBackgroundResource(R.drawable.circle_blue_new);
    }

    public void updateApproval(final String id, final String approve1){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_PURCHASE_ORDER, new Response.Listener<String>() {
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
                return param;
            }
        };
        Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        if (approval == 1){
            user = Integer.valueOf(sharedPrefManager.getUserId());
        } else user = approvalAssignId;

        if (user > 0){
            StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_PURCHASE_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status=jsonObject.getInt("status");
                        if(status==1){
                            loadDetail();
                            textPersetujuan.setText(sharedPrefManager.getUserDisplayName());
                            Toast.makeText(DetailPurchaseOrderActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailPurchaseOrderActivity.this, "Filed update data", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(DetailPurchaseOrderActivity.this, "", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(DetailPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("id", "" + purchaseOrder.getPurchase_order_id());
                    param.put("user", "" + user);
                    param.put("command", editCommand.getText().toString() + " ");
                    param.put("code", "" + approval);
                    return param;
                }
            };
            Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
        } else {
            progressDialog.dismiss();
            Toast.makeText(DetailPurchaseOrderActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
        }
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_ORDER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        total = 0; budget = 0; discount = 0; efisiensi = 0; efisiensiPerc = 0; dpp = 0; pajak = 0; grandTotal = 0;
                        recyclerView.setAdapter(null);
                        purchaseOrderDetails.clear();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseOrderDetails.add(new PurchaseOrderDetail(jsonArray.getJSONObject(i)));

                            if (!jsonArray.getJSONObject(i).getString("po_app1").toLowerCase().contains("Reject".toLowerCase())) {
                                total += (jsonArray.getJSONObject(i).getDouble("quantity") * jsonArray.getJSONObject(i).getDouble("unit_price_buy")) - jsonArray.getJSONObject(i).getDouble("discount");
                                budget += jsonArray.getJSONObject(i).getDouble("quantity") * jsonArray.getJSONObject(i).getDouble("max_budget");
                                discount += jsonArray.getJSONObject(i).getDouble("discount");
                            }
                        }

                        efisiensi = budget - (total - discount);
                        if (efisiensi > 0) {
                            efisiensiPerc = ((budget - (total - discount)) / budget) * 100;
                        } else {
                            efisiensi = 0;
                            efisiensiPerc = 0;
                        }

                        dpp = total;
                        pajak = total*Double.valueOf(purchaseOrder.getTax_type_rate())/100;
                        grandTotal = dpp+pajak;

                        textTotal.setText("Rp. " + formatter.format((long) total));
                        textBudget.setText("Rp. " + formatter.format((long) budget));
                        textDiscount.setText("Rp. " + formatter.format((long) discount));
                        textEfisiensi.setText("Rp. " + formatter.format((long) efisiensi) + " (" + new DecimalFormat("##.##").format(efisiensiPerc) + "%)");
                        textDPP.setText("Rp. " + formatter.format((long) dpp));
                        textPajak.setText("Rp. " + formatter.format((long) pajak));
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));

                        if (efisiensi > 0)
                            textEfisiensi.setTextColor(getResources().getColor(R.color.colorAsukaGreen));

                        adapter = new MyRecyclerViewAdapter(purchaseOrderDetails, context);
                        recyclerView.setAdapter(adapter);

                        changeColor();
                        approval = 0;
                    } else {
                        Toast.makeText(DetailPurchaseOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailPurchaseOrderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPurchaseOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("po_id", "" + purchaseOrder.getPurchase_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPurchaseOrderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PurchaseOrderDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PurchaseOrderDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_purchase_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textSpecification.setText(mValues.get(position).getItem_specification());
            holder.textRemark.setText(mValues.get(position).getNotes());
            holder.textJobOrder.setText(mValues.get(position).getJob_order_number() + "\n" + mValues.get(position).getJob_order_description());
            holder.textStatus.setText(mValues.get(position).getStatus());
            holder.textQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());
            holder.textApproval1.setText(mValues.get(position).getPo_app1());

            double toDouble;
            toDouble = Double.valueOf(mValues.get(position).getMax_budget());
            holder.textBudget.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getDiscount());
            holder.textDiscount.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getUnit_price_buy());
            holder.textPrice.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = (Double.valueOf(mValues.get(position).getUnit_price_buy())*Double.valueOf(mValues.get(position).getQuantity()))-Double.valueOf(mValues.get(position).getDiscount());
            holder.textSubTotal.setText("Rp. " + formatter.format((long) toDouble));

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
            }

            String[] approve = {"Approved", "Reject", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailPurchaseOrderActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            holder.editApproval1.setAdapter(adapterApproval);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textNo;
            public final TextView textItem;
            public final TextView textSpecification;
            public final TextView textRemark;
            public final TextView textJobOrder;
            public final TextView textStatus;
            public final TextView textQty;
            public final TextView textPrice;
            public final TextView textDiscount;
            public final TextView textBudget;
            public final TextView textSubTotal;
            public final TextView textApproval1;
            public final Spinner editApproval1;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textItem = (TextView) itemView.findViewById(R.id.textItem);
                textSpecification = (TextView) itemView.findViewById(R.id.textSpecification);
                textRemark = (TextView) itemView.findViewById(R.id.textRemark);
                textJobOrder = (TextView) itemView.findViewById(R.id.textJobOrder);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);
                textQty = (TextView) itemView.findViewById(R.id.textQty);
                textPrice = (TextView) itemView.findViewById(R.id.textPrice);
                textDiscount = (TextView) itemView.findViewById(R.id.textDiscount);
                textBudget = (TextView) itemView.findViewById(R.id.textBudget);
                textSubTotal = (TextView) itemView.findViewById(R.id.textSubTotal);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}