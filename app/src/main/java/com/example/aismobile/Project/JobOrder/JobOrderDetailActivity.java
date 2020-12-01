package com.example.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.Project.JobOrder;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JobOrderDetailActivity extends AppCompatActivity {

    private double toDouble;
    private double toDoubleNew;
    private double totalPengeluaran = 0;

    private TextView menuJoDetail;
    private TextView menuJoMr;
    private TextView menuJoPr;
    private TextView menuJoTr;
    private TextView menuJoMp;
    private TextView menuJoCod;
    private TextView menuJoWo;
    private TextView menuJoPb;
    private TextView menuJoCpr;
    private TextView menuJoExpenses;
    private TextView menuJoInvoice;
    private TextView menuJoMatRet;
    private TextView menuJoNotes;
    private TextView menuJoHistory;

    private ImageView downloadSalesQuotation;
    private ImageView downloadPOClient;
    private TextView jodJobOrder;
    private TextView jodDepartemen;
    private TextView jodKeterangan;
    private TextView jodDay;
    private TextView detailSalesQuotation;
    private TextView detailPOClient;
    private TextView detailSalesOrder;
    private TextView detailSupervisor;
    private TextView detailTglAwal;
    private TextView detailTglAkhir;
    private TextView detailJenisPajak;
    private TextView detailNilai;
    private TextView detailBudgetAmount;
    private TextView detailCatatan;
    private TextView detailNilaiKontrak;
    private TextView detailCustomerInvoice;
    private TextView detailCustomerPayment;
    private TextView detailBalance;
    private TextView detailDibuatOleh;
    private TextView detailTanggalDibuat;
    private TextView detailDiubahOleh;
    private TextView detailDiubahTgl;
    private TextView detailLabaRugiBerjalan;
    private TextView detailSisaBudget;
    private TextView detailLabaRugi;

    private TextView detailMateriB;
    private TextView detailToolB;
    private TextView detailMpcB;
    private TextView detailCodB;
    private TextView detailWoB;
    private TextView detailMrB;
    private TextView detailPbB;
    private TextView detailCprB;
    private TextView detailExpensesB;
    private TextView detailTotalBudgetB;
    private TextView detailMateriP;
    private TextView detailToolP;
    private TextView detailMpcP;
    private TextView detailCodP;
    private TextView detailWoP;
    private TextView detailMrP;
    private TextView detailPbP;
    private TextView detailCprP;
    private TextView detailExpensesP;
    private TextView detailTotalBudgetP;
    private TextView detailMateriPr;
    private TextView detailToolPr;
    private TextView detailMpcPr;
    private TextView detailCodPr;
    private TextView detailWoPr;
    private TextView detailMrPr;
    private TextView detailPbPr;
    private TextView detailCprPr;
    private TextView detailExpensesPr;
    private TextView detailTotalBudgetPr;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail);

        Bundle bundle = getIntent().getExtras();
        jobOrder = bundle.getParcelable("detailJO");

        menuJoDetail = (TextView) findViewById(R.id.menuJoDetail);
        menuJoMr = (TextView) findViewById(R.id.menuJoMr);
        menuJoPr = (TextView) findViewById(R.id.menuJoPr);
        menuJoTr = (TextView) findViewById(R.id.menuJoTr);
        menuJoMp = (TextView) findViewById(R.id.menuJoMp);
        menuJoCod = (TextView) findViewById(R.id.menuJoCod);
        menuJoWo = (TextView) findViewById(R.id.menuJoWo);
        menuJoPb = (TextView) findViewById(R.id.menuJoPb);
        menuJoCpr = (TextView) findViewById(R.id.menuJoCpr);
        menuJoExpenses = (TextView) findViewById(R.id.menuJoExpenses);
        menuJoInvoice = (TextView) findViewById(R.id.menuJoInvoice);
        menuJoMatRet = (TextView) findViewById(R.id.menuJoMatRet);
        menuJoNotes = (TextView) findViewById(R.id.menuJoNotes);
        menuJoHistory = (TextView) findViewById(R.id.menuJoHistory);

        downloadSalesQuotation = (ImageView) findViewById(R.id.downloadSalesQuotation);
        downloadPOClient = (ImageView) findViewById(R.id.downloadPOClient);
        jodJobOrder = (TextView) findViewById(R.id.jodJobOrder);
        jodDepartemen = (TextView) findViewById(R.id.jodDepartemen);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        jodDay = (TextView) findViewById(R.id.jodDay);
        detailSalesQuotation = (TextView) findViewById(R.id.detailSalesQuotation);
        detailPOClient = (TextView) findViewById(R.id.detailPOClient);
        detailSalesOrder = (TextView) findViewById(R.id.detailSalesOrder);
        detailSupervisor = (TextView) findViewById(R.id.detailSupervisor);
        detailTglAwal = (TextView) findViewById(R.id.detailTglAwal);
        detailTglAkhir = (TextView) findViewById(R.id.detailTglAkhir);
        detailJenisPajak = (TextView) findViewById(R.id.detailJenisPajak);
        detailNilai = (TextView) findViewById(R.id.detailNilai);
        detailBudgetAmount = (TextView) findViewById(R.id.detailBudgetAmount);
        detailCatatan = (TextView) findViewById(R.id.detailCatatan);
        detailNilaiKontrak = (TextView) findViewById(R.id.detailNilaiKontrak);
        detailCustomerInvoice = (TextView) findViewById(R.id.detailCustomerInvoice);
        detailCustomerPayment = (TextView) findViewById(R.id.detailCustomerPayment);
        detailBalance = (TextView) findViewById(R.id.detailBalance);
        detailDibuatOleh = (TextView) findViewById(R.id.detailDibuatOleh);
        detailTanggalDibuat = (TextView) findViewById(R.id.detailTanggalDibuat);
        detailDiubahOleh = (TextView) findViewById(R.id.detailDiubahOleh);
        detailDiubahTgl = (TextView) findViewById(R.id.detailDiubahTgl);
        detailLabaRugiBerjalan = (TextView) findViewById(R.id.detailLabaRugiBerjalan);
        detailSisaBudget = (TextView) findViewById(R.id.detailSisaBudget);
        detailLabaRugi = (TextView) findViewById(R.id.detailLabaRugi);

        detailMateriB = (TextView) findViewById(R.id.detailMateriB);
        detailToolB = (TextView) findViewById(R.id.detailToolB);
        detailMpcB = (TextView) findViewById(R.id.detailMpcB);
        detailCodB = (TextView) findViewById(R.id.detailCodB);
        detailWoB = (TextView) findViewById(R.id.detailWoB);
        detailMrB = (TextView) findViewById(R.id.detailMrB);
        detailPbB = (TextView) findViewById(R.id.detailPbB);
        detailCprB = (TextView) findViewById(R.id.detailCprB);
        detailExpensesB = (TextView) findViewById(R.id.detailExpensesB);
        detailTotalBudgetB = (TextView) findViewById(R.id.detailTotalBudgetB);
        detailMateriP = (TextView) findViewById(R.id.detailMateriP);
        detailToolP = (TextView) findViewById(R.id.detailToolP);
        detailMpcP = (TextView) findViewById(R.id.detailMpcP);
        detailCodP = (TextView) findViewById(R.id.detailCodP);
        detailWoP = (TextView) findViewById(R.id.detailWoP);
        detailMrP = (TextView) findViewById(R.id.detailMrP);
        detailPbP = (TextView) findViewById(R.id.detailPbP);
        detailCprP = (TextView) findViewById(R.id.detailCprP);
        detailExpensesP = (TextView) findViewById(R.id.detailExpensesP);
        detailTotalBudgetP = (TextView) findViewById(R.id.detailTotalBudgetP);
        detailMateriPr = (TextView) findViewById(R.id.detailMateriPr);
        detailToolPr = (TextView) findViewById(R.id.detailToolPr);
        detailMpcPr = (TextView) findViewById(R.id.detailMpcPr);
        detailCodPr = (TextView) findViewById(R.id.detailCodPr);
        detailWoPr = (TextView) findViewById(R.id.detailWoPr);
        detailMrPr = (TextView) findViewById(R.id.detailMrPr);
        detailPbPr = (TextView) findViewById(R.id.detailPbPr);
        detailCprPr = (TextView) findViewById(R.id.detailCprPr);
        detailExpensesPr = (TextView) findViewById(R.id.detailExpensesPr);
        detailTotalBudgetPr = (TextView) findViewById(R.id.detailTotalBudgetPr);

        NumberFormat formatter = new DecimalFormat("#,###");
        long total = 0;
        toDouble = Double.valueOf(jobOrder.getMaterial_amount());
        detailMateriB.setText("Rp. " + formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getTools_amount());
        detailToolB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getMan_power_amount());
        detailMpcB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getCod_amount());
        detailCodB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getWo_amount());
        detailWoB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getMaterial_return_amount());
        detailMrB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getPb_amount());
        detailPbB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getCpr_amount());
        detailCprB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        toDouble = Double.valueOf(jobOrder.getExpenses_amount());
        detailExpensesB.setText("Rp. " +  formatter.format((long) toDouble));
        total += (long) toDouble;
        detailTotalBudgetB.setText("Rp. " +  formatter.format(total));

        jodJobOrder.setText(jobOrder.getJob_order_number());
        jodDepartemen.setText("Departemen : " + jobOrder.getDepartment_id());
        jodKeterangan.setText("Keterangan : " + jobOrder.getJob_order_description());

        detailSalesQuotation.setText(jobOrder.getSales_quotation_id());
        detailPOClient.setText(jobOrder.getClient_po_number());
        detailSalesOrder.setText(jobOrder.getSales_order_id());
        detailSupervisor.setText(jobOrder.getSupervisor());
        detailTglAwal.setText(jobOrder.getBegin_date());
        detailTglAkhir.setText(jobOrder.getEnd_date());
        detailJenisPajak.setText(jobOrder.getTax_type_id());
        detailCatatan.setText(jobOrder.getNotes());
        detailDibuatOleh.setText(jobOrder.getCreated_by());
        detailTanggalDibuat.setText(jobOrder.getCreated_date());
        detailDiubahOleh.setText(jobOrder.getModified_by());
        detailDiubahTgl.setText(jobOrder.getModified_date());

        toDouble = Double.valueOf(jobOrder.getAmount());
        detailNilai.setText("Rp. "+ formatter.format((long) toDouble));
        toDouble = Double.valueOf(jobOrder.getBudgeting_amount());
        detailBudgetAmount.setText("Rp. "+ formatter.format((long) toDouble));
        toDouble = Double.valueOf(jobOrder.getAmount());
        detailNilaiKontrak.setText("Rp. "+ formatter.format((long) toDouble));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDateValue = Calendar.getInstance().getTime();
            Date endDateValue = sdf.parse(jobOrder.getEnd_date());
            long diff = endDateValue.getTime() - startDateValue.getTime();
            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<0)
                jodDay.setText("0 Days Remaining");
            else jodDay.setText(""+ (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1) + " Days Remaining");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        downloadSalesQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/salesQuotation/"+jobOrder.getSales_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        downloadPOClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/salesQuotation/"+jobOrder.getClient_po_file_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        menuJoMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailMrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailPrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailTrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailMpActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailCodActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoWo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailWoActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailPbActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailCprActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailExpensesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailInvoiceActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMatRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailMatRetActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailNotesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailActivity.this, JobOrderDetailHistoryActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });

        getData();
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_JOB_ORDER_TOTAL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONObject json = jsonObject.getJSONObject("data");

                        DecimalFormat df = new DecimalFormat("#.#");
                        double doubles;
                        totalPengeluaran = 0;
                        NumberFormat formatter = new DecimalFormat("#,###");
                        doubles = json.getDouble("mr")+json.getDouble("pr"); totalPengeluaran+=doubles;
                        detailMateriP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("tr"); totalPengeluaran+=doubles;
                        detailToolP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("manPower"); totalPengeluaran+=doubles;
                        detailMpcP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("cod"); totalPengeluaran+=doubles;
                        detailCodP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("wo"); totalPengeluaran+=doubles;
                        detailWoP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("matret"); totalPengeluaran+=doubles;
                        detailMrP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("pbHalf"); totalPengeluaran+=doubles;
                        detailPbP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("cpr"); totalPengeluaran+=doubles;
                        detailCprP.setText("Rp. " + formatter.format((long) doubles));
                        doubles = json.getDouble("expenses"); totalPengeluaran+=doubles;
                        detailExpensesP.setText("Rp. " + formatter.format((long) doubles));
                        detailTotalBudgetP.setText("Rp. " + formatter.format((long) totalPengeluaran));

                        if (json.getDouble("mr")+json.getDouble("pr") > Double.valueOf(jobOrder.getMaterial_amount()) && Double.valueOf(jobOrder.getMaterial_amount()) > 0)
                            detailMateriPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailMateriPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getMaterial_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getMaterial_amount()) - (json.getDouble("mr")+json.getDouble("pr"))) / Double.valueOf(jobOrder.getMaterial_amount()) * 100;
                            detailMateriPr.setText(df.format(toDoubleNew) + "%");
                        } else detailMateriPr.setText("0%");

                        if (json.getDouble("tr") > Double.valueOf(jobOrder.getTools_amount()) && Double.valueOf(jobOrder.getTools_amount()) > 0)
                            detailToolPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailToolPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getTools_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getTools_amount()) - json.getDouble("tr")) / Double.valueOf(jobOrder.getTools_amount()) * 100;
                            detailToolPr.setText(df.format(toDoubleNew) + "%");
                        } else detailToolPr.setText("0%");

                        if (json.getDouble("manPower") > Double.valueOf(jobOrder.getMan_power_amount()) && Double.valueOf(jobOrder.getMan_power_amount()) > 0)
                            detailMpcPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailMpcPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getMan_power_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getMan_power_amount()) - json.getDouble("manPower")) / Double.valueOf(jobOrder.getMan_power_amount()) * 100;
                            detailMpcPr.setText(df.format(toDoubleNew) + "%");
                        } else detailMpcPr.setText("0%");

                        if (json.getDouble("cod") > Double.valueOf(jobOrder.getCod_amount()) && Double.valueOf(jobOrder.getCod_amount()) > 0)
                            detailCodPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailCodPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getCod_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getCod_amount()) - json.getDouble("cod")) / Double.valueOf(jobOrder.getCod_amount()) * 100;
                            detailCodPr.setText(df.format(toDoubleNew) + "%");
                        } else detailCodPr.setText("0%");

                        if (json.getDouble("wo") > Double.valueOf(jobOrder.getWo_amount()) && Double.valueOf(jobOrder.getWo_amount()) > 0)
                            detailWoPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailWoPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getWo_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getWo_amount()) - json.getDouble("wo")) / Double.valueOf(jobOrder.getWo_amount()) * 100;
                            detailWoPr.setText(df.format(toDoubleNew) + "%");
                        } else detailWoPr.setText("0%");

                        if (json.getDouble("matret") > Double.valueOf(jobOrder.getMaterial_return_amount()) && Double.valueOf(jobOrder.getMaterial_return_amount()) > 0)
                            detailMrPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailMrPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getMaterial_return_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getMaterial_return_amount()) - json.getDouble("matret")) / Double.valueOf(jobOrder.getMaterial_return_amount()) * 100;
                            detailMrPr.setText(df.format(toDoubleNew) + "%");
                        } else detailMrPr.setText("0%");

                        if (json.getDouble("pbHalf") > Double.valueOf(jobOrder.getPb_amount()) && Double.valueOf(jobOrder.getPb_amount()) > 0)
                            detailPbPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailPbPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getPb_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getPb_amount()) - json.getDouble("pbHalf")) / Double.valueOf(jobOrder.getPb_amount()) * 100;
                            detailPbPr.setText(df.format(toDoubleNew) + "%");
                        } else detailPbPr.setText("0%");

                        if (json.getDouble("cpr") > Double.valueOf(jobOrder.getCpr_amount()) && Double.valueOf(jobOrder.getCpr_amount()) > 0)
                            detailCprPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailCprPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getCpr_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getCpr_amount()) - json.getDouble("cpr")) / Double.valueOf(jobOrder.getCpr_amount()) * 100;
                            detailCprPr.setText(df.format(toDoubleNew) + "%");
                        } else detailCprPr.setText("0%");

                        if (json.getDouble("expenses") > Double.valueOf(jobOrder.getExpenses_amount()) && Double.valueOf(jobOrder.getExpenses_amount()) > 0)
                            detailExpensesPr.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        else detailExpensesPr.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        if (Double.valueOf(jobOrder.getExpenses_amount()) > 0){
                            toDoubleNew = (Double.valueOf(jobOrder.getExpenses_amount()) - json.getDouble("expenses")) / Double.valueOf(jobOrder.getExpenses_amount()) * 100;
                            detailExpensesPr.setText(df.format(toDoubleNew) + "%");
                        } else detailExpensesPr.setText("0%");

                        df = new DecimalFormat("#.##");

                        toDouble = Double.valueOf(jobOrder.getAmount()) - totalPengeluaran;
                        toDoubleNew = (Double.valueOf(jobOrder.getAmount()) - totalPengeluaran) / Double.valueOf(jobOrder.getAmount()) * 100;
                        detailLabaRugi.setText("Rp. " + formatter.format((long) toDouble) + " (" + df.format(toDoubleNew) + "%)");
                        if (toDoubleNew < 0)
                            detailLabaRugi.setTextColor(getResources().getColor(R.color.colorRed));
                        else detailLabaRugi.setTextColor(getResources().getColor(R.color.colorAsukaGreen));

                        toDouble = Double.valueOf(jobOrder.getBudgeting_amount()) - totalPengeluaran;
                        toDoubleNew = (Double.valueOf(jobOrder.getBudgeting_amount()) - totalPengeluaran) / Double.valueOf(jobOrder.getBudgeting_amount()) * 100;
                        detailSisaBudget.setText("Rp. " + formatter.format((long) toDouble) + " (" + df.format(toDoubleNew) + "%)");
                        if (toDoubleNew < 0)
                            detailSisaBudget.setTextColor(getResources().getColor(R.color.colorRed));
                        else detailSisaBudget.setTextColor(getResources().getColor(R.color.colorAsukaGreen));

//                        toDouble = json.getDouble("invoice") - totalPengeluaran;
//                        toDoubleNew = (json.getDouble("invoice") - totalPengeluaran) / json.getDouble("invoice") * 100;
                        toDouble = json.getDouble("invoice") - totalPengeluaran;
                        if (json.getInt("invoice") != 0)
                            toDoubleNew = (json.getDouble("invoice") - totalPengeluaran) / json.getDouble("invoice") * 100;
                        else toDoubleNew = -100;
                        detailLabaRugiBerjalan.setText( "Rp. " + formatter.format((long) toDouble) + " (" + df.format(toDoubleNew) + "%)");
                        if (toDoubleNew < 0)
                            detailLabaRugiBerjalan.setTextColor(getResources().getColor(R.color.colorRed));
                        else detailLabaRugiBerjalan.setTextColor(getResources().getColor(R.color.colorAsukaGreen));

                        toDouble = json.getDouble("payment");
                        detailCustomerPayment.setText("Rp. " + formatter.format((long) toDouble));
                        toDouble = json.getDouble("invoice");
                        detailCustomerInvoice.setText("Rp. " + formatter.format((long) toDouble));
                        toDouble = json.getDouble("invoice") - json.getDouble("payment");
                        detailBalance.setText("Rp. " + formatter.format((long) toDouble));
                    } else {
                        Toast.makeText(JobOrderDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                onBackPressed();
                Toast.makeText(JobOrderDetailActivity.this, "Your network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
