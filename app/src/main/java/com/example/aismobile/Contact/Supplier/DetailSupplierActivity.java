package com.example.aismobile.Contact.Supplier;

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
import com.example.aismobile.Data.Contact.Supplier;
import com.example.aismobile.Data.Contact.SupplierDetail;
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

public class DetailSupplierActivity extends AppCompatActivity {

    private double toDouble;
    private NumberFormat formatter;
    private ViewGroup.LayoutParams params;
    private Supplier supplier;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SupplierDetail> cashOnDeliveryDetails;
    private ProgressDialog progressDialog;

    private TextView textName;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuSupplierInvoice;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private ScrollView layoutSupplierInvoice;
    private ImageView downloadAtachment;

    private TextView textNamaSupplier;
    private TextView textSupplierType;
    private TextView textEmail;
    private TextView textWebsite;
    private TextView textSupplierContact;
    private TextView textLastLogin;
    private TextView textAlamatKantor1;
    private TextView textAlamatNPWP;
    private TextView textTeleponKantor;
    private TextView textFaxKantor;
    private TextView textNoHP;
    private TextView textNPWP;
    private TextView textSPPKPNumber;
    private TextView textNamaBank;
    private TextView textPemilikAkun;
    private TextView textRekBank;
    private TextView textCatatan;

    private TextView textDPP;
    private TextView textDiscount;
    private TextView textPPN;
    private TextView textPPH;
    private TextView textStamp;
    private TextView textAdjustment;
    private TextView textTotal;

    private double dpp;
    private double discount;
    private double ppn;
    private double pph;
    private double stamp;
    private double adjustment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_supplier);

        Bundle bundle = getIntent().getExtras();
        supplier = bundle.getParcelable("detail");
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

        textDPP = (TextView) findViewById(R.id.textDPP);
        textDiscount = (TextView) findViewById(R.id.textDiscount);
        textPPN = (TextView) findViewById(R.id.textPPN);
        textPPH = (TextView) findViewById(R.id.textPPH);
        textStamp = (TextView) findViewById(R.id.textStamp);
        textAdjustment = (TextView) findViewById(R.id.textAdjustment);
        textTotal = (TextView) findViewById(R.id.textTotal);

        textNamaSupplier = (TextView) findViewById(R.id.textNamaSupplier);
        textSupplierType = (TextView) findViewById(R.id.textSupplierType);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        textSupplierContact = (TextView) findViewById(R.id.textSupplierContact);
        textLastLogin = (TextView) findViewById(R.id.textLastLogin);
        textAlamatKantor1 = (TextView) findViewById(R.id.textAlamatKantor1);
        textAlamatNPWP = (TextView) findViewById(R.id.textAlamatNPWP);
        textTeleponKantor = (TextView) findViewById(R.id.textTeleponKantor);
        textFaxKantor = (TextView) findViewById(R.id.textFaxKantor);
        textNoHP = (TextView) findViewById(R.id.textNoHP);
        textNPWP = (TextView) findViewById(R.id.textNPWP);
        textSPPKPNumber = (TextView) findViewById(R.id.textSPPKPNumber);
        textNamaBank = (TextView) findViewById(R.id.textNamaBank);
        textPemilikAkun = (TextView) findViewById(R.id.textPemilikAkun);
        textRekBank = (TextView) findViewById(R.id.textRekBank);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textNamaSupplier.setText(supplier.getSupplier_name());
        textSupplierType.setText(supplier.getSupplier_type());
        textEmail.setText(supplier.getEmail_address());
        textWebsite.setText(supplier.getWebsite());
        textSupplierContact.setText(supplier.getSupplier_contact());
        textLastLogin.setText(supplier.getSupplier_lastlogin());
        textAlamatKantor1.setText(supplier.getOffice_address1());
        textAlamatNPWP.setText(supplier.getOffice_address2());
        textTeleponKantor.setText(supplier.getOffice_phone());
        textFaxKantor.setText(supplier.getOffice_fax());
        textNoHP.setText(supplier.getPhone());
        textNPWP.setText(supplier.getNpwp());
        textSPPKPNumber.setText(supplier.getSppkp_number());
        textNamaBank.setText(supplier.getBank_name());
        textPemilikAkun.setText(supplier.getName_of_bank_account());
        textRekBank.setText(supplier.getBank_account());
        textCatatan.setText(supplier.getDescription());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textName = (TextView) findViewById(R.id.textName);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuSupplierInvoice = (TextView) findViewById(R.id.menuSupplierInvoice);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutSupplierInvoice = (ScrollView) findViewById(R.id.layoutSupplierInvoice);
        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);

        textName.setText("#"+ supplier.getSupplier_name());
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
        menuSupplierInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutSupplierInvoice.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutSupplierInvoice.setLayoutParams(params);
                menuSupplierInvoice.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuSupplierInvoice.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        downloadAtachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/supplier/"+ supplier.getSupplier_file_name());
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
        menuSupplierInvoice.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuSupplierInvoice.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutSupplierInvoice.getLayoutParams(); params.height = 0; layoutSupplierInvoice.setLayoutParams(params);
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SUPPLIER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveryDetails.add(new SupplierDetail(jsonArray.getJSONObject(i)));

                            dpp += jsonArray.getJSONObject(i).getDouble("amount");
                            discount += jsonArray.getJSONObject(i).getDouble("discount");
                            ppn += jsonArray.getJSONObject(i).getDouble("ppn");
                            pph += jsonArray.getJSONObject(i).getDouble("pph");
                            stamp += jsonArray.getJSONObject(i).getDouble("stamp");
                            adjustment += jsonArray.getJSONObject(i).getDouble("adjustment_value");
                        }
                        textDPP.setText("Rp. " + formatter.format((long) dpp));
                        textDiscount.setText("Rp. " + formatter.format((long) discount));
                        textPPN.setText("Rp. " + formatter.format((long) ppn));
                        textPPH.setText("Rp. " + formatter.format((long) pph));
                        textStamp.setText("Rp. " + formatter.format((long) stamp));
                        textAdjustment.setText("Rp. " + formatter.format((long) adjustment));
                        toDouble = dpp-discount+ppn-pph+stamp-Math.abs(adjustment);
                        textTotal.setText("Rp. " + formatter.format((long) toDouble));

                        adapter = new MyRecyclerViewAdapter(cashOnDeliveryDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailSupplierActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailSupplierActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailSupplierActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("siId", "" + supplier.getSupplier_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailSupplierActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<SupplierDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<SupplierDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_supplier_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textOrderDate.setText(mValues.get(position).getOrder_date());
            holder.textOrderNumber.setText(mValues.get(position).getOrder_number());
            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. " + formatter.format((long) toDouble));
            holder.textPaymentDate.setText(mValues.get(position).getPayment_date());
            toDouble = Double.valueOf(mValues.get(position).getGrand_total());
            holder.textGrandTotal.setText("Rp. " + formatter.format((long) toDouble));
            holder.textSupplierINV.setText(mValues.get(position).getSupplier_invoice_number());
            holder.textSIDate.setText(mValues.get(position).getSupplier_invoice_date());
            holder.textStatus.setText(mValues.get(position).getSupplier_invoice_status());

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
            public final TextView textOrderDate;
            public final TextView textOrderNumber;
            public final TextView textNilai;
            public final TextView textSIDate;
            public final TextView textSupplierINV;
            public final TextView textGrandTotal;
            public final TextView textStatus;
            public final TextView textPaymentDate;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textOrderDate = (TextView) itemView.findViewById(R.id.textOrderDate);
                textOrderNumber = (TextView) itemView.findViewById(R.id.textOrderNumber);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textSIDate = (TextView) itemView.findViewById(R.id.textSIDate);
                textSupplierINV = (TextView) itemView.findViewById(R.id.textSupplierINV);
                textGrandTotal = (TextView) itemView.findViewById(R.id.textGrandTotal);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);
                textPaymentDate = (TextView) itemView.findViewById(R.id.textPaymentDate);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}