package com.example.aismobile.Purchasing.WorkOrder;

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
import com.example.aismobile.Data.Purchasing.PurchaseService;
import com.example.aismobile.Data.Purchasing.PurchaseServiceDetail;
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

public class DetailWorkOrdderActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private PurchaseService purchaseService;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<PurchaseServiceDetail> purchaseServiceDetails;
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
    private TextView textCatatan;
    private TextView textPoComment;
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

    private double total;
    private double budget;
    private double discount;
    private double efisiensi;
    private double dpp;
    private double pajak;
    private double grandTotal;

    private int code = 0, approval = 0, approvalAssignId = 0, user = 0, akses1 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private EditText editCommand;
    private TextView btnSaveApprove;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_ordder);

        Bundle bundle = getIntent().getExtras();
        purchaseService = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        purchaseServiceDetails = new ArrayList<>();

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
                if (approval != 1){
                    btnApprove1.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(purchaseServiceDetails, context);
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
                    adapter = new MyRecyclerViewAdapter(purchaseServiceDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval == 1 && akses1 > 0){
                    if (purchaseService.getChecked_by().toLowerCase().contains("-".toLowerCase()))
                        Toast.makeText(DetailWorkOrdderActivity.this, "You are not able to approve because it has not been Checking", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i<purchaseServiceDetails.size(); i++)
                            updateApproval(String.valueOf(purchaseServiceDetails.get(i).getWork_order_detail_id()),
                                    ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString());
                        updateApprovalId();
                    }
                } else if (approval == 2){
                    updateApprovalId();
                } else Toast.makeText(DetailWorkOrdderActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
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
        textJenisPembelian = (TextView) findViewById(R.id.textJenisPembelian);
        textContract = (TextView) findViewById(R.id.textContract);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textNomorPenawaran = (TextView) findViewById(R.id.textNomorPenawaran);
        textTglPenawaran = (TextView) findViewById(R.id.textTglPenawaran);
        textTglAwal = (TextView) findViewById(R.id.textTglAwal);
        textTglKedatangan = (TextView) findViewById(R.id.textTglAkhir);
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

        textJenisPembelian.setText(purchaseService.getPurchase_order_type_id());
        textContract.setText(purchaseService.getContract_agreement_id());
        textSupplier.setText(purchaseService.getSupplier_id());
        textNomorPenawaran.setText(purchaseService.getPurchase_quotation_number());
        textTglPenawaran.setText(purchaseService.getPurchase_quotation_date());
        textTglAwal.setText(purchaseService.getBegin_date());
        textTglKedatangan.setText(purchaseService.getEnd_date());
        textTerminPembayaran.setText(purchaseService.getPayment_term_id());
        textPaymentDesc.setText(purchaseService.getPayment_desc());
        textPersetujuan.setText(purchaseService.getApproval_assign_id());
        textJenisPajak.setText(purchaseService.getTax_type_id());
        textCheckedBy.setText(purchaseService.getChecked_by());
        textCheckedDate.setText(purchaseService.getChecked_date());
        textApproval1.setText(purchaseService.getPo_approval1());
        textApproval1Date.setText(purchaseService.getPo_approval_date1());
        textCatatan.setText(purchaseService.getNotes());
        textPoComment.setText(purchaseService.getPo_comment1());
        textCreatedBy.setText(purchaseService.getCreated_by());
        textCreatedDate.setText(purchaseService.getCreated_date());
        textModifiedBy.setText(purchaseService.getModified_by());
        textModifiedDate.setText(purchaseService.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Detail WO #" + purchaseService.getPurchase_service_number());
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
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/purchaseQuotation/"+ purchaseService.getPurchase_service_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

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
                Toast.makeText(DetailWorkOrdderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "9");
                return param;
            }
        };
        Volley.newRequestQueue(DetailWorkOrdderActivity.this).add(request);
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
                Toast.makeText(DetailWorkOrdderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(DetailWorkOrdderActivity.this).add(request);
    }

    public void changeColor(){
        btnApprove1.setTextColor(getResources().getColor(R.color.colorWhite));
        btnApprove2.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void updateApproval(final String id, final String approve1){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_WORK_ORDER, new Response.Listener<String>() {
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
        Volley.newRequestQueue(DetailWorkOrdderActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        if (approval == 1){
            user = Integer.valueOf(sharedPrefManager.getUserId());
        } else user = approvalAssignId;

        if (user > 0){
            StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_WORK_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status=jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(DetailWorkOrdderActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailWorkOrdderActivity.this, "Filed update data", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(DetailWorkOrdderActivity.this, "", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(DetailWorkOrdderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("id", "" + purchaseService.getPurchase_service_id());
                    param.put("user", "" + user);
                    param.put("command", editCommand.getText().toString() + " ");
                    param.put("code", "" + approval);
                    return param;
                }
            };
            Volley.newRequestQueue(DetailWorkOrdderActivity.this).add(request);
        } else {
            progressDialog.dismiss();
            Toast.makeText(DetailWorkOrdderActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
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
        recyclerView.setAdapter(null);
        purchaseServiceDetails.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_PURCHASE_SERVICE_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            purchaseServiceDetails.add(new PurchaseServiceDetail(jsonArray.getJSONObject(i)));

                            total += (jsonArray.getJSONObject(i).getDouble("quantity")*jsonArray.getJSONObject(i).getDouble("unit_price"))-jsonArray.getJSONObject(i).getDouble("discount");
                            budget += jsonArray.getJSONObject(i).getDouble("quantity")*jsonArray.getJSONObject(i).getDouble("max_budget");
                            discount += jsonArray.getJSONObject(i).getDouble("discount");
                        }
                        efisiensi = budget - total;
                        if (efisiensi < 0)
                            efisiensi = 0;
                        dpp = total;
                        pajak = total*Double.valueOf(purchaseService.getTax_type_rate())/100;
                        grandTotal = dpp+pajak;

                        adapter = new MyRecyclerViewAdapter(purchaseServiceDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailWorkOrdderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailWorkOrdderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailWorkOrdderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("ps_id", "" + purchaseService.getPurchase_service_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailWorkOrdderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<PurchaseServiceDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<PurchaseServiceDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_work_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textItem.setText(mValues.get(position).getItem_name());
            holder.textQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());
            holder.textApproval1.setText(mValues.get(position).getPs_app1());

            double toDouble;
            NumberFormat formatter = new DecimalFormat("#,###");
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

            if (mValues.size() == position+1){
                textTotal.setText("Rp. " + formatter.format((long) total));
                textBudget.setText("Rp. " + formatter.format((long) budget));
                textDiscount.setText("Rp. " + formatter.format((long) discount));
                textEfisiensi.setText("Rp. " + formatter.format((long) efisiensi));
                textDPP.setText("Rp. " + formatter.format((long) dpp));
                textPajak.setText("Rp. " + formatter.format((long) pajak));
                textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));
            }

            if (approval == 1 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.textApproval1.getLayoutParams();
                params.height = 0;
                holder.textApproval1.setLayoutParams(params);
            }

            String[] approve = {"Approved", "Reject", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailWorkOrdderActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
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