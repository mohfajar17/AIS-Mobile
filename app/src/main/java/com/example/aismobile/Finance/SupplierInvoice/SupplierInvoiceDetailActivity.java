package com.example.aismobile.Finance.SupplierInvoice;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.FinanceAccounting.SupplierInvoice;
import com.example.aismobile.Data.FinanceAccounting.SupplierInvoiceDetail;
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

public class SupplierInvoiceDetailActivity extends AppCompatActivity {

    private double toDouble;

    private NumberFormat formatter;
    private ViewGroup.LayoutParams params;
    private SupplierInvoice supplierInvoice;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SupplierInvoiceDetail> cashOnDeliveryDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textPaymentLate;
    private TextView textNumber;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;
    private ImageView downloadAtachment;

    private TextView textTransactionNumber;
    private TextView textSupplier;
    private TextView textAccount;
    private TextView textTaxNumber;
    private TextView textNomorBuktiPotong;
    private TextView textTanggalBuktiPotong;
    private TextView textSiDate;
    private TextView textInvoiceDate;
    private TextView textPaymentDate;
    private TextView textScheduleDate;
    private TextView textDueDate;
    private TextView textBillDescription;
    private TextView textNilai;
    private TextView textDiscount;
    private TextView textPPN;
    private TextView textPPH;
    private TextView textStamp;
    private TextView textSubTotal;
    private TextView textGrandTotal;
    private TextView textLunas;
    private TextView textBankTransaction;
    private TextView textInvoiceDescription;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_invoice_detail);

        Bundle bundle = getIntent().getExtras();
        supplierInvoice = bundle.getParcelable("detail");

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        cashOnDeliveryDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textTransactionNumber = (TextView) findViewById(R.id.textTransactionNumber);
        textSupplier = (TextView) findViewById(R.id.textSupplier);
        textAccount = (TextView) findViewById(R.id.textAccount);
        textTaxNumber = (TextView) findViewById(R.id.textTaxNumber);
        textNomorBuktiPotong = (TextView) findViewById(R.id.textNomorBuktiPotong);
        textTanggalBuktiPotong = (TextView) findViewById(R.id.textTanggalBuktiPotong);
        textSiDate = (TextView) findViewById(R.id.textSiDate);
        textInvoiceDate = (TextView) findViewById(R.id.textInvoiceDate);
        textPaymentDate = (TextView) findViewById(R.id.textPaymentDate);
        textScheduleDate = (TextView) findViewById(R.id.textScheduleDate);
        textDueDate = (TextView) findViewById(R.id.textDueDate);
        textBillDescription = (TextView) findViewById(R.id.textBillDescription);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textDiscount = (TextView) findViewById(R.id.textDiscount);
        textPPN = (TextView) findViewById(R.id.textPPN);
        textPPH = (TextView) findViewById(R.id.textPPH);
        textStamp = (TextView) findViewById(R.id.textStamp);
        textSubTotal = (TextView) findViewById(R.id.textSubTotal);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);
        textLunas = (TextView) findViewById(R.id.textLunas);
        textBankTransaction = (TextView) findViewById(R.id.textBankTransaction);
        textInvoiceDescription = (TextView) findViewById(R.id.textInvoiceDescription);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textTransactionNumber.setText(supplierInvoice.getTransaction_number());
        textSupplier.setText(supplierInvoice.getSupplier_name());
        textAccount.setText(supplierInvoice.getCategory());
        textTaxNumber.setText(supplierInvoice.getTax_number());
        textNomorBuktiPotong.setText(supplierInvoice.getNumber_pieces_of_evidence());
        textTanggalBuktiPotong.setText(supplierInvoice.getDate_pieces_of_evidence());
        textSiDate.setText(supplierInvoice.getSupplier_invoice_date());
        textInvoiceDate.setText(supplierInvoice.getInvoice_receipt_date());
        textPaymentDate.setText(supplierInvoice.getPayment_date());
        textScheduleDate.setText(supplierInvoice.getSchedule_date());
        textDueDate.setText(supplierInvoice.getDue_date());
        textBillDescription.setText(supplierInvoice.getBank_transaction_type_name());

        toDouble = Double.valueOf(supplierInvoice.getAmount());
        textNilai.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getDiscount());
        textDiscount.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getPpn());
        textPPN.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getPph());
        textPPH.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getStamp());
        textStamp.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getTotalSI());
        textSubTotal.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(supplierInvoice.getTotalSI());
        textGrandTotal.setText("Rp. " + formatter.format((long) toDouble));
        if (supplierInvoice.getSupplier_invoice_status().toLowerCase().contains("paid"))
            textLunas.setText("LUNAS!");
        else {
            params = textLunas.getLayoutParams();
            params.height = 0;
            textLunas.setLayoutParams(params);
        }
        textBankTransaction.setText(supplierInvoice.getBank_transaction_number());
        textInvoiceDescription.setText(supplierInvoice.getSupplier_invoice_description());
        textCatatan.setText(supplierInvoice.getNotes());
        textCreatedBy.setText(supplierInvoice.getCreated_by());
        textCreatedDate.setText(supplierInvoice.getCreated_date());
        textModifiedBy.setText(supplierInvoice.getModified_by());
        textModifiedDate.setText(supplierInvoice.getModified_date());

        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textPaymentLate = (TextView) findViewById(R.id.textPaymentLate);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("#" + supplierInvoice.getSupplier_invoice_number());
        if (Integer.valueOf(supplierInvoice.getLate_days()) < 1)
            textPaymentLate.setText("Payment Late : Complete");
        else textPaymentLate.setText("Payment Late : " + supplierInvoice.getLate_days() + " Day");
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

        if (!supplierInvoice.getSupplier_invoice_file_name().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(context, R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/supplierInvoice/" + supplierInvoice.getSupplier_invoice_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }

        loadDetail();
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_INVOICE_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveryDetails.add(new SupplierInvoiceDetail(jsonArray.getJSONObject(i)));
                        }

                        adapter = new MyRecyclerViewAdapter(cashOnDeliveryDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(SupplierInvoiceDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(SupplierInvoiceDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SupplierInvoiceDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("siId", "" + supplierInvoice.getSupplier_invoice_id());
                return param;
            }
        };
        Volley.newRequestQueue(SupplierInvoiceDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<SupplierInvoiceDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<SupplierInvoiceDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_supplier_invoice_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textGoodsReceivedNote.setText(mValues.get(position).getGrn());
            holder.textServicesReceipt.setText(mValues.get(position).getServices_receipt());
            holder.textWorkHandover.setText(mValues.get(position).getWork_handover());
            holder.textPurchase.setText(mValues.get(position).getCash_on_delivery());
            holder.textSupplier.setText(mValues.get(position).getSupplier_name());
            holder.textCatatan.setText(mValues.get(position).getNotes());
            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textAmount.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getDiscount());
            holder.textDiscount.setText("Rp. " + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getPpn());
            holder.textPPN.setText("Rp. " + formatter.format((long) toDouble));
            holder.textAkuntansi.setText("Edit");

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
            public final TextView textGoodsReceivedNote;
            public final TextView textServicesReceipt;
            public final TextView textWorkHandover;
            public final TextView textPurchase;
            public final TextView textSupplier;
            public final TextView textCatatan;
            public final TextView textAmount;
            public final TextView textDiscount;
            public final TextView textPPN;
            public final TextView textAkuntansi;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textGoodsReceivedNote = (TextView) itemView.findViewById(R.id.textGoodsReceivedNote);
                textServicesReceipt = (TextView) itemView.findViewById(R.id.textServicesReceipt);
                textWorkHandover = (TextView) itemView.findViewById(R.id.textWorkHandover);
                textPurchase = (TextView) itemView.findViewById(R.id.textPurchase);
                textSupplier = (TextView) itemView.findViewById(R.id.textSupplier);
                textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);
                textAmount = (TextView) itemView.findViewById(R.id.textAmount);
                textDiscount = (TextView) itemView.findViewById(R.id.textDiscount);
                textPPN = (TextView) itemView.findViewById(R.id.textPPN);
                textAkuntansi = (TextView) itemView.findViewById(R.id.textAkuntansi);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}