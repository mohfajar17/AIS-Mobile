package com.example.aismobile.Finance.CustomerInvoice;

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
import com.example.aismobile.Data.FinanceAccounting.CustomerInvoice;
import com.example.aismobile.Data.FinanceAccounting.CustomerInvoiceDetail;
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

public class CustomerInvoiceDetailActivity extends AppCompatActivity {

    private NumberFormat formatter;
    private ViewGroup.LayoutParams params;
    private CustomerInvoice customerInvoice;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<CustomerInvoiceDetail> cashOnDeliveryDetails;
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

    private TextView textWorkCompletion;
    private TextView textJobOrder;
    private TextView textSalesQuotation;
    private TextView textPerusahaan;
    private TextView textClientPONumber;
    private TextView textPersetujuan;
    private TextView textAccount;
    private TextView textTaxNumber;
    private TextView textNomorBuktiPotong;
//    private TextView textNoResi;
    private TextView textTanggalBuktiPotong;
    private TextView textPaymentDate;
    private TextView textInvoiceDate;
    private TextView textDueDate;
    private TextView textCustomerReceiveDate;
    private TextView textMaterialDescription;
    private TextView textNilaiMaterial;
    private TextView textDiscountMaterial;
    private TextView textPPNMaterial;
    private TextView textJasaDescription;
    private TextView textNilaiJasa;
    private TextView textDiscountJasa;
    private TextView textPPNJasa;
    private TextView textPPHJasa;
    private TextView textAdjustmentDescription;
    private TextView textNilaiAdjustment;
    private TextView textLunas;
    private TextView textBankTransaction;
    private TextView textCustomerReceive;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textCommercialValue;
    private TextView textDiscount;
    private TextView textTotalAfterDiscount;
    private TextView textPPN10;
    private TextView textPPHJasaFinal;
    private TextView textGrandTotalNoWHT;
    private TextView textGrandTotalWHT;

    private double toDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_invoice_detail);

        Bundle bundle = getIntent().getExtras();
        customerInvoice = bundle.getParcelable("detail");

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

        textWorkCompletion = (TextView) findViewById(R.id.textWorkCompletion);
        textJobOrder = (TextView) findViewById(R.id.textJobOrder);
        textSalesQuotation = (TextView) findViewById(R.id.textSalesQuotation);
        textPerusahaan = (TextView) findViewById(R.id.textPerusahaan);
        textClientPONumber = (TextView) findViewById(R.id.textClientPONumber);
        textPersetujuan = (TextView) findViewById(R.id.textPersetujuan);
        textAccount = (TextView) findViewById(R.id.textAccount);
        textTaxNumber = (TextView) findViewById(R.id.textTaxNumber);
        textNomorBuktiPotong = (TextView) findViewById(R.id.textNomorBuktiPotong);
//        textNoResi = (TextView) findViewById(R.id.textNoResi);
        textTanggalBuktiPotong = (TextView) findViewById(R.id.textTanggalBuktiPotong);
        textPaymentDate = (TextView) findViewById(R.id.textPaymentDate);
        textInvoiceDate = (TextView) findViewById(R.id.textInvoiceDate);
        textDueDate = (TextView) findViewById(R.id.textDueDate);
        textCustomerReceiveDate = (TextView) findViewById(R.id.textCustomerReceiveDate);
        textMaterialDescription = (TextView) findViewById(R.id.textMaterialDescription);
        textNilaiMaterial = (TextView) findViewById(R.id.textNilaiMaterial);
        textDiscountMaterial = (TextView) findViewById(R.id.textDiscountMaterial);
        textPPNMaterial = (TextView) findViewById(R.id.textPPNMaterial);
        textJasaDescription = (TextView) findViewById(R.id.textJasaDescription);
        textNilaiJasa = (TextView) findViewById(R.id.textNilaiJasa);
        textDiscountJasa = (TextView) findViewById(R.id.textDiscountJasa);
        textPPNJasa = (TextView) findViewById(R.id.textPPNJasa);
        textPPHJasa = (TextView) findViewById(R.id.textPPHJasa);
        textAdjustmentDescription = (TextView) findViewById(R.id.textAdjustmentDescription);
        textNilaiAdjustment = (TextView) findViewById(R.id.textNilaiAdjustment);
        textLunas = (TextView) findViewById(R.id.textLunas);
        textBankTransaction = (TextView) findViewById(R.id.textBankTransaction);
        textCustomerReceive = (TextView) findViewById(R.id.textCustomerReceive);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textCommercialValue = (TextView) findViewById(R.id.textCommercialValue);
        textDiscount = (TextView) findViewById(R.id.textDiscount);
        textTotalAfterDiscount = (TextView) findViewById(R.id.textTotalAfterDiscount);
        textPPN10 = (TextView) findViewById(R.id.textPPN10);
        textPPHJasaFinal = (TextView) findViewById(R.id.textPPHJasaFinal);
        textGrandTotalNoWHT = (TextView) findViewById(R.id.textGrandTotalNoWHT);
        textGrandTotalWHT = (TextView) findViewById(R.id.textGrandTotalWHT);

        double commercialValue = Double.valueOf(customerInvoice.getService_amount());
        textCommercialValue.setText("Rp. " + formatter.format((long) commercialValue));
        double discount = Double.valueOf(customerInvoice.getService_discount());
        textDiscount.setText("Rp. " + formatter.format((long) discount));
        double totalAfterDiscount = Double.valueOf(customerInvoice.getService_amount()) - Double.valueOf(customerInvoice.getService_discount());
        textTotalAfterDiscount.setText("Rp. " + formatter.format((long) totalAfterDiscount));
        double ppn = Double.valueOf(customerInvoice.getService_amount()) * 10 / 100;
        textPPN10.setText("Rp. " + formatter.format((long) ppn));
        double pph = Double.valueOf(customerInvoice.getService_amount()) * Double.valueOf(customerInvoice.getTax_type_rate()) / 100;
        textPPHJasaFinal.setText("Rp. " + formatter.format((long) pph));
        double gtWithoutWHT = totalAfterDiscount + ppn + pph;
        textGrandTotalNoWHT.setText("Rp. " + formatter.format((long) gtWithoutWHT));
        double gtWHT = totalAfterDiscount + ppn;
        textGrandTotalWHT.setText("Rp. " + formatter.format((long) gtWHT));

        textWorkCompletion.setText(customerInvoice.getSales_order_invoice_number());
        textJobOrder.setText(customerInvoice.getJob_order_id());
        textSalesQuotation.setText(customerInvoice.getSales_quotation_id());
        textPerusahaan.setText(customerInvoice.getCompany_name());
        textClientPONumber.setText(customerInvoice.getClient_po_number());
        textPersetujuan.setText(customerInvoice.getApproval_assign());
        textAccount.setText(customerInvoice.getCategory() + " | " + customerInvoice.getBank_transaction_type_name());
        textTaxNumber.setText(customerInvoice.getTax_number());
        textNomorBuktiPotong.setText(customerInvoice.getNumber_pieces_of_evidence());
//        textNoResi.setText(cashOnDelivery.getJob_order_id());
        textTanggalBuktiPotong.setText(customerInvoice.getDate_pieces_of_evidence());
        textPaymentDate.setText(customerInvoice.getPayment_date());
        textInvoiceDate.setText(customerInvoice.getInvoice_date());
        textDueDate.setText(customerInvoice.getDue_date());
        textCustomerReceiveDate.setText(customerInvoice.getCustomer_receive_date());
        textMaterialDescription.setText(customerInvoice.getMaterial_description());
        toDouble = Double.valueOf(customerInvoice.getAmount());
        textNilaiMaterial.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(customerInvoice.getDiscount());
        textDiscountMaterial.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(customerInvoice.getPpn());
        textPPNMaterial.setText("Rp. " + formatter.format((long) toDouble));
        textJasaDescription.setText(customerInvoice.getService_description());
        toDouble = Double.valueOf(customerInvoice.getService_amount());
        textNilaiJasa.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(customerInvoice.getService_discount());
        textDiscountJasa.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(customerInvoice.getService_ppn());
        textPPNJasa.setText("Rp. " + formatter.format((long) toDouble));
        toDouble = Double.valueOf(customerInvoice.getService_amount()) * Double.valueOf(customerInvoice.getTax_type_rate()) / 100;
        textPPHJasa.setText("Rp. " + formatter.format((long) toDouble) + " (" + customerInvoice.getTax_type() + ")");
        textAdjustmentDescription.setText(customerInvoice.getAdjustment_desc());
        toDouble = Double.valueOf(customerInvoice.getAdjustment_value());
        textNilaiAdjustment.setText("Rp. " + formatter.format((long) toDouble));
        if (customerInvoice.getSales_order_invoice_status().toLowerCase().contains("paid"))
            textLunas.setText("LUNAS!");
        else {
            params = textLunas.getLayoutParams();
            params.height = 0;
            textLunas.setLayoutParams(params);
        }
        textBankTransaction.setText(customerInvoice.getBank_transaction_number());
        textCustomerReceive.setText(customerInvoice.getCustomer_receive_number());
        textCatatan.setText(customerInvoice.getNotes());
        textCreatedBy.setText(customerInvoice.getCreated_by());
        textCreatedDate.setText(customerInvoice.getCreated_date());
        textModifiedBy.setText(customerInvoice.getModified_by());
        textModifiedDate.setText(customerInvoice.getModified_date());

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

        textNumber.setText("#" + customerInvoice.getSales_order_invoice_number());
        if (Integer.valueOf(customerInvoice.getPayment_late()) < 1)
            textPaymentLate.setText("Payment Late : Complete");
        else textPaymentLate.setText("Payment Late : " + customerInvoice.getPayment_late() + " Day");
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
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/media/files/SalesOrderInvoice/"+ customerInvoice.getSales_order_invoice_id());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUSTOMER_INVOICE_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveryDetails.add(new CustomerInvoiceDetail(jsonArray.getJSONObject(i)));
                        }

                        adapter = new MyRecyclerViewAdapter(cashOnDeliveryDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(CustomerInvoiceDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerInvoiceDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CustomerInvoiceDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("ciId", "" + customerInvoice.getSales_order_invoice_id());
                return param;
            }
        };
        Volley.newRequestQueue(CustomerInvoiceDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<CustomerInvoiceDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<CustomerInvoiceDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_customer_invoice_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textDescription.setText(mValues.get(position).getDescription());
            holder.textQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());
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
            public final TextView textDescription;
            public final TextView textQty;
            public final TextView textAmount;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textDescription = (TextView) itemView.findViewById(R.id.textDescription);
                textQty = (TextView) itemView.findViewById(R.id.textQty);
                textAmount = (TextView) itemView.findViewById(R.id.textAmount);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}