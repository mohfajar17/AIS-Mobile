package com.example.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aismobile.Data.Project.JobOrder;
import com.example.aismobile.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JobOrderDetailActivity extends AppCompatActivity {

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

        NumberFormat formatter = new DecimalFormat("#,###");

        detailMateriB.setText("Rp. " + formatter.format(Long.valueOf(jobOrder.getMaterial_amount())));
        detailToolB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getTools_amount())));
        detailMpcB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getMan_power_amount())));
        detailCodB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getCod_amount())));
        detailWoB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getWo_amount())));
        detailMrB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getMaterial_return_amount())));
        detailPbB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getPb_amount())));
        detailCprB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getCpr_amount())));
        detailExpensesB.setText("Rp. " +  formatter.format(Long.valueOf(jobOrder.getExpenses_amount())));
        int total = (int) Integer.valueOf(jobOrder.getMaterial_amount()) + Integer.valueOf(jobOrder.getTools_amount()) + Integer.valueOf(jobOrder.getMan_power_amount()) + Integer.valueOf(jobOrder.getCod_amount()) + Integer.valueOf(jobOrder.getWo_amount()) + Integer.valueOf(jobOrder.getMaterial_return_amount()) + Integer.valueOf(jobOrder.getPb_amount()) + Integer.valueOf(jobOrder.getCpr_amount()) + Integer.valueOf(jobOrder.getExpenses_amount());
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
        try{
            detailNilai.setText("Rp. "+ formatter.format(Long.valueOf(jobOrder.getAmount())));
            detailBudgetAmount.setText("Rp. "+ formatter.format(Long.valueOf(jobOrder.getBudgeting_amount())));
            detailNilaiKontrak.setText("Rp. "+ formatter.format(Long.valueOf(jobOrder.getAmount())));
            detailCustomerInvoice.setText("Rp. ");
            detailCustomerPayment.setText("Rp. ");
            detailBalance.setText("Rp. ");
            detailLabaRugiBerjalan.setText("Rp. ");
            detailSisaBudget.setText("Rp. ");
            detailLabaRugi.setText("Rp. ");
        } catch (NumberFormatException ex){ // handle your exception
            detailNilai.setText("Rp. "+ jobOrder.getAmount());
            detailBudgetAmount.setText("Rp. "+ jobOrder.getBudgeting_amount());
            detailNilaiKontrak.setText("Rp. "+ jobOrder.getAmount());
            detailCustomerInvoice.setText("Rp. ");
            detailCustomerPayment.setText("Rp. ");
            detailBalance.setText("Rp. ");
            detailLabaRugiBerjalan.setText("Rp. ");
            detailSisaBudget.setText("Rp. ");
            detailLabaRugi.setText("Rp. ");
        }

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
    }
}
