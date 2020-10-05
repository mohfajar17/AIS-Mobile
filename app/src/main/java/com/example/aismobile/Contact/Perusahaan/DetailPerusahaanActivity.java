package com.example.aismobile.Contact.Perusahaan;

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
import com.example.aismobile.Data.Contact.Company;
import com.example.aismobile.Data.Contact.CompanyDetContact;
import com.example.aismobile.Data.Contact.CompanyDetInvoice;
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

public class DetailPerusahaanActivity extends AppCompatActivity {

    private double toDouble;
    private NumberFormat formatter;
    private ViewGroup.LayoutParams params;
    private Company cashOnDelivery;
    private ProgressDialog progressDialog;
    private Context context;
    private RecyclerView recyclerViewContact; //
    private MyContactRecyclerViewAdapter adapterContact; //
    private List<CompanyDetContact> companyDetContacts; //
    private RecyclerView recyclerViewInvoice; //
    private MyInvoiceRecyclerViewAdapter adapterInvoice; //
    private List<CompanyDetInvoice> companyDetInvoices; //
    private RecyclerView.LayoutManager recylerViewLayoutManager;

    private TextView textName;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuSupplierInvoice;
    private TextView menuCountInvoice;
    private TextView menuCustomer;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutSupplierInvoice;
    private LinearLayout layoutCustomer;
    private LinearLayout layoutCountInvoice;

    private TextView textNamaPerusahaan;
    private TextView textCode;
    private TextView textNPWP;
    private TextView textAlamat;
    private TextView textAddressSPPKP;
    private TextView textKota;
    private TextView textPropinsi;
    private TextView textNegara;
    private TextView textKodepos;
    private TextView textTelepon1;
    private TextView textTelepon2;
    private TextView textFax;
    private TextView textEMail;
    private TextView textWebsite;
    private TextView textCatatan;

    private TextView textAmountNew;
    private TextView textAmountDelivered;
    private TextView textAmountPaid;
    private TextView textAmountCancel;

    private double amountNew;
    private double amountDelivered;
    private double amountPaid;
    private double amountCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perusahaan);

        Bundle bundle = getIntent().getExtras();
        cashOnDelivery = bundle.getParcelable("detail");
        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        companyDetContacts = new ArrayList<>();
        companyDetInvoices = new ArrayList<>();

        recyclerViewContact = (RecyclerView) findViewById(R.id.recyclerViewCustomer);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerViewContact.setLayoutManager(recylerViewLayoutManager);

        recyclerViewInvoice = (RecyclerView) findViewById(R.id.recyclerViewInvoice);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerViewInvoice.setLayoutManager(recylerViewLayoutManager);

        textAmountNew = (TextView) findViewById(R.id.textAmountNew);
        textAmountDelivered = (TextView) findViewById(R.id.textAmountDelivered);
        textAmountPaid = (TextView) findViewById(R.id.textAmountPaid);
        textAmountCancel = (TextView) findViewById(R.id.textAmountCancel);

        textNamaPerusahaan = (TextView) findViewById(R.id.textNamaPerusahaan);
        textCode = (TextView) findViewById(R.id.textCode);
        textNPWP = (TextView) findViewById(R.id.textNPWP);
        textAlamat = (TextView) findViewById(R.id.textAlamat);
        textAddressSPPKP = (TextView) findViewById(R.id.textAddressSPPKP);
        textKota = (TextView) findViewById(R.id.textKota);
        textPropinsi = (TextView) findViewById(R.id.textPropinsi);
        textNegara = (TextView) findViewById(R.id.textNegara);
        textKodepos = (TextView) findViewById(R.id.textKodepos);
        textTelepon1 = (TextView) findViewById(R.id.textTelepon1);
        textTelepon2 = (TextView) findViewById(R.id.textTelepon2);
        textFax = (TextView) findViewById(R.id.textFax);
        textEMail = (TextView) findViewById(R.id.textEMail);
        textWebsite = (TextView) findViewById(R.id.textWebsite);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textNamaPerusahaan.setText(cashOnDelivery.getCompany_name());
        textCode.setText(cashOnDelivery.getCompany_code());
        textNPWP.setText(cashOnDelivery.getNpwp());
        textAlamat.setText(cashOnDelivery.getCompany_address());
        textAddressSPPKP.setText(cashOnDelivery.getCompany_address_sppkp());
        textKota.setText(cashOnDelivery.getCompany_city());
        textPropinsi.setText(cashOnDelivery.getCompany_state());
        textNegara.setText(cashOnDelivery.getCompany_country());
        textKodepos.setText(cashOnDelivery.getCompany_zipcode());
        textTelepon1.setText(cashOnDelivery.getCompany_phone1());
        textTelepon2.setText(cashOnDelivery.getCompany_phone2());
        textFax.setText(cashOnDelivery.getCompany_fax());
        textEMail.setText(cashOnDelivery.getCompany_email());
        textWebsite.setText(cashOnDelivery.getCompany_www());
        textCatatan.setText(cashOnDelivery.getCompany_notes());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textName = (TextView) findViewById(R.id.textName);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuSupplierInvoice = (TextView) findViewById(R.id.menuSupplierInvoice);
        menuCountInvoice = (TextView) findViewById(R.id.menuCountInvoice);
        menuCustomer = (TextView) findViewById(R.id.menuCustomer);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutSupplierInvoice = (LinearLayout) findViewById(R.id.layoutSupplierInvoice);
        layoutCustomer = (LinearLayout) findViewById(R.id.layoutCustomer);
        layoutCountInvoice = (LinearLayout) findViewById(R.id.layoutCountInvoice);

        textName.setText("#"+ cashOnDelivery.getCompany_name());
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
        menuCountInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCountInvoice.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCountInvoice.setLayoutParams(params);
                menuCountInvoice.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCountInvoice.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCustomer.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCustomer.setLayoutParams(params);
                menuCustomer.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCustomer.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });

        loadDetailContact();
        loadDetailInvoice();
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuSupplierInvoice.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuSupplierInvoice.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCountInvoice.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCountInvoice.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCustomer.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCustomer.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutSupplierInvoice.getLayoutParams(); params.height = 0; layoutSupplierInvoice.setLayoutParams(params);
        params = layoutCustomer.getLayoutParams(); params.height = 0; layoutCustomer.setLayoutParams(params);
        params = layoutCountInvoice.getLayoutParams(); params.height = 0; layoutCountInvoice.setLayoutParams(params);
    }

    public void loadDetailContact(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_COMPANY_CONTACT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++)
                            companyDetContacts.add(new CompanyDetContact(jsonArray.getJSONObject(i)));

                        adapterContact = new MyContactRecyclerViewAdapter(companyDetContacts, context);
                        recyclerViewContact.setAdapter(adapterContact);
                    } else {
                        Toast.makeText(DetailPerusahaanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DetailPerusahaanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPerusahaanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("cpnId", "" + cashOnDelivery.getCompany_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPerusahaanActivity.this).add(request);
    }

    private class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

        private final List<CompanyDetContact> mValues;
        private Context context;

        private MyContactRecyclerViewAdapter(List<CompanyDetContact> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_perusahaan_contact, parent, false);
            return new MyContactRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyContactRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textContactName.setText(mValues.get(position).getContact_name());
            holder.textKategoriKontak.setText(mValues.get(position).getContact_catid());
            holder.textPangkat.setText(mValues.get(position).getContact_jobtitle());
            holder.textEmail.setText(mValues.get(position).getContact_email());
            holder.textTelepon.setText(mValues.get(position).getContact_phone1());

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
            public final TextView textContactName;
            public final TextView textKategoriKontak;
            public final TextView textPangkat;
            public final TextView textEmail;
            public final TextView textTelepon;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textContactName = (TextView) itemView.findViewById(R.id.textContactName);
                textKategoriKontak = (TextView) itemView.findViewById(R.id.textKategoriKontak);
                textPangkat = (TextView) itemView.findViewById(R.id.textPangkat);
                textEmail = (TextView) itemView.findViewById(R.id.textEmail);
                textTelepon = (TextView) itemView.findViewById(R.id.textTelepon);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }

    public void loadDetailInvoice(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_COMPANY_INVOICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    amountNew = 0;
                    amountDelivered = 0;
                    amountPaid = 0;
                    amountCancel = 0;

                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            companyDetInvoices.add(new CompanyDetInvoice(jsonArray.getJSONObject(i)));

                            if (jsonArray.getJSONObject(i).getString("sales_order_invoice_status").toLowerCase().contains("New".toLowerCase()))
                                amountNew += jsonArray.getJSONObject(i).getDouble("service_amount")+jsonArray.getJSONObject(i).getDouble("service_ppn")-jsonArray.getJSONObject(i).getDouble("pph")-Math.abs(jsonArray.getJSONObject(i).getDouble("adjustment_value"));
                            else if (jsonArray.getJSONObject(i).getString("sales_order_invoice_status").toLowerCase().contains("Delivered".toLowerCase()))
                                amountDelivered += jsonArray.getJSONObject(i).getDouble("service_amount")+jsonArray.getJSONObject(i).getDouble("service_ppn")-jsonArray.getJSONObject(i).getDouble("pph")-Math.abs(jsonArray.getJSONObject(i).getDouble("adjustment_value"));
                            else if (jsonArray.getJSONObject(i).getString("sales_order_invoice_status").toLowerCase().contains("Paid".toLowerCase()))
                                amountPaid += jsonArray.getJSONObject(i).getDouble("service_amount")+jsonArray.getJSONObject(i).getDouble("service_ppn")-jsonArray.getJSONObject(i).getDouble("pph")-Math.abs(jsonArray.getJSONObject(i).getDouble("adjustment_value"));
                            else if (jsonArray.getJSONObject(i).getString("sales_order_invoice_status").toLowerCase().contains("Cancel".toLowerCase()))
                                amountCancel += jsonArray.getJSONObject(i).getDouble("service_amount")+jsonArray.getJSONObject(i).getDouble("service_ppn")-jsonArray.getJSONObject(i).getDouble("pph")-Math.abs(jsonArray.getJSONObject(i).getDouble("adjustment_value"));
                        }

                        textAmountNew.setText("Rp. " + formatter.format((long) amountNew));
                        textAmountDelivered.setText("Rp. " + formatter.format((long) amountDelivered));
                        textAmountPaid.setText("Rp. " + formatter.format((long) amountPaid));
                        textAmountCancel.setText("Rp. " + formatter.format((long) amountCancel));

                        adapterInvoice = new MyInvoiceRecyclerViewAdapter(companyDetInvoices, context);
                        recyclerViewInvoice.setAdapter(adapterInvoice);
                    } else {
                        Toast.makeText(DetailPerusahaanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DetailPerusahaanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailPerusahaanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("cpnId", "" + cashOnDelivery.getCompany_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailPerusahaanActivity.this).add(request);
    }

    private class MyInvoiceRecyclerViewAdapter extends RecyclerView.Adapter<MyInvoiceRecyclerViewAdapter.ViewHolder> {

        private final List<CompanyDetInvoice> mValues;
        private Context context;

        private MyInvoiceRecyclerViewAdapter(List<CompanyDetInvoice> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_perusahaan_invoice, parent, false);
            return new MyInvoiceRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyInvoiceRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textINVDate.setText(mValues.get(position).getInvoice_date());
            holder.textINVNumber.setText(mValues.get(position).getSales_order_invoice_number());
            holder.textDueDate.setText(mValues.get(position).getDue_date());
            toDouble = Double.valueOf(mValues.get(position).getService_amount());
            holder.textTotal.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getService_ppn());
            holder.textVat10.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getPph());
            holder.textPph.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getAdjustment_value());
            holder.textAdjusment.setText("" + formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getService_amount()) + Double.valueOf(mValues.get(position).getService_ppn()) - Double.valueOf(mValues.get(position).getPph())-Double.valueOf(mValues.get(position).getAdjustment_value());
            holder.textGrandTotal.setText("" + formatter.format((long) toDouble));
            holder.textStatus.setText(mValues.get(position).getSales_order_invoice_status());

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
            public final TextView textINVDate;
            public final TextView textINVNumber;
            public final TextView textDueDate;
            public final TextView textTotal;
            public final TextView textVat10;
            public final TextView textPph;
            public final TextView textAdjusment;
            public final TextView textGrandTotal;
            public final TextView textStatus;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textINVDate = (TextView) itemView.findViewById(R.id.textINVDate);
                textINVNumber = (TextView) itemView.findViewById(R.id.textINVNumber);
                textDueDate = (TextView) itemView.findViewById(R.id.textDueDate);
                textTotal = (TextView) itemView.findViewById(R.id.textTotal);
                textVat10 = (TextView) itemView.findViewById(R.id.textVat10);
                textPph = (TextView) itemView.findViewById(R.id.textPph);
                textAdjusment = (TextView) itemView.findViewById(R.id.textAdjusment);
                textGrandTotal = (TextView) itemView.findViewById(R.id.textGrandTotal);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}