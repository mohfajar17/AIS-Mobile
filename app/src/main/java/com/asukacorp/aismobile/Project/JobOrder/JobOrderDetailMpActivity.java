package com.asukacorp.aismobile.Project.JobOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.DetailJobOrder.JoMppk;
import com.asukacorp.aismobile.Data.Project.DetailJobOrder.JoMptm;
import com.asukacorp.aismobile.Data.Project.JobOrder;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JobOrderDetailMpActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerViewTemp;
    private RecyclerView recyclerViewPermanen;
    private MyRecyclerViewAdapterTemp adapterTemp;
    private MyRecyclerViewAdapterPermanen adapterPermanen;
    private RecyclerView.LayoutManager recylerViewLayoutManagerTemp;
    private RecyclerView.LayoutManager recylerViewLayoutManagerPermanen;
    private List<JoMppk> joMppks;
    private List<JoMptm> joMptms;
    private ProgressDialog progressDialog;
    private long totalPricePermanen = 0;
    private long totalPriceTemp = 0;
    private long totalPriceAll = 0;

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
    private TextView menuJoDoc;
    private TextView menuJoNotes;
    private TextView menuJoHistory;

    private TextView jodJobOrder;
    private TextView jodDepartemen;
    private TextView jodKeterangan;
    private TextView jodDay;
    private TextView totalJobOrderTemp;
    private TextView totalJobOrderPermanen;
    private TextView totalKeseluruhanMp;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail_mp);

        recyclerViewTemp = (RecyclerView) findViewById(R.id.recyclerViewTemp);
        recyclerViewPermanen = (RecyclerView) findViewById(R.id.recyclerViewPermanen);
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
        menuJoDoc = (TextView) findViewById(R.id.menuJoDoc);
        menuJoNotes = (TextView) findViewById(R.id.menuJoNotes);
        menuJoHistory = (TextView) findViewById(R.id.menuJoHistory);

        jodJobOrder = (TextView) findViewById(R.id.jodJobOrder);
        jodDepartemen = (TextView) findViewById(R.id.jodDepartemen);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        jodDay = (TextView) findViewById(R.id.jodDay);
        totalJobOrderTemp = (TextView) findViewById(R.id.totalJobOrderTemp);
        totalJobOrderPermanen = (TextView) findViewById(R.id.totalJobOrderPermanen);
        totalKeseluruhanMp = (TextView) findViewById(R.id.totalKeseluruhanMp);

        recylerViewLayoutManagerTemp = new LinearLayoutManager(context);
        recyclerViewTemp.setLayoutManager(recylerViewLayoutManagerTemp);
        recylerViewLayoutManagerPermanen = new LinearLayoutManager(context);
        recyclerViewPermanen.setLayoutManager(recylerViewLayoutManagerPermanen);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        joMppks = new ArrayList<>();
        joMptms = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        jobOrder = bundle.getParcelable("detailJO");

        jodJobOrder.setText(jobOrder.getJob_order_number());
        jodDepartemen.setText("Departemen : " + jobOrder.getDepartment_id());
        jodKeterangan.setText("Keterangan : " + jobOrder.getJob_order_description());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDateValue = Calendar.getInstance().getTime();
            Date endDateValue = sdf.parse(jobOrder.getEnd_date());
            long diff = endDateValue.getTime() - startDateValue.getTime();
            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<0)
                jodDay.setText("0 Days Remaining");
            else jodDay.setText(""+ (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1) +" Days Remaining");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        menuJoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailMrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailPrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailTrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailCodActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoWo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailWoActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailPbActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailCprActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailExpensesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailInvoiceActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMatRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailMatRetActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailPictureActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailNotesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailMpActivity.this, JobOrderDetailHistoryActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadJobOrderDetailTemp();
        loadJobOrderDetailPermanen();
    }

    public void loadJobOrderDetailTemp(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_MP_TEMP_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joMptms.add(new JoMptm(jsonArray.getJSONObject(i)));

                            double one = jsonArray.getJSONObject(i).getDouble("GP");
                            double two = jsonArray.getJSONObject(i).getDouble("GL");
                            double three = jsonArray.getJSONObject(i).getDouble("Tunjangan");
                            double four = jsonArray.getJSONObject(i).getDouble("project_location");

                            double subTotal = one+two+three+four;
                            totalPriceTemp += (long) subTotal;
                        }
                        totalPriceAll += totalPriceTemp;
                        adapterTemp = new MyRecyclerViewAdapterTemp(joMptms, context);
                        recyclerViewTemp.setAdapter(adapterTemp);
                    } else {
                        Toast.makeText(JobOrderDetailMpActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(JobOrderDetailMpActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(JobOrderDetailMpActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(JobOrderDetailMpActivity.this).add(request);
    }

    public void loadJobOrderDetailPermanen(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_MP_PERMANEN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joMppks.add(new JoMppk(jsonArray.getJSONObject(i)));

                            double one = jsonArray.getJSONObject(i).getDouble("GP");
                            double two = jsonArray.getJSONObject(i).getDouble("GL");
                            double three = jsonArray.getJSONObject(i).getDouble("Tunjangan");
                            double four = jsonArray.getJSONObject(i).getDouble("project_location");
                            double five = jsonArray.getJSONObject(i).getDouble("AB");

                            double subTotal = one+two+three+four-five;
                            totalPricePermanen += (long) subTotal;
                        }
                        totalPriceAll += totalPricePermanen;
                        adapterPermanen = new MyRecyclerViewAdapterPermanen(joMppks, context);
                        recyclerViewPermanen.setAdapter(adapterPermanen);
                    } else {
                        Toast.makeText(JobOrderDetailMpActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(JobOrderDetailMpActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(JobOrderDetailMpActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(JobOrderDetailMpActivity.this).add(request);
    }

    private class MyRecyclerViewAdapterPermanen extends RecyclerView.Adapter<MyRecyclerViewAdapterPermanen.ViewHolder> {

        private final List<JoMppk> mValues;
        private Context context;

        private MyRecyclerViewAdapterPermanen(List<JoMppk> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_mppk_list, parent, false);
            return new MyRecyclerViewAdapterPermanen.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapterPermanen.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.joTextNo.setText("" + nomor);
            holder.joTextBulan.setText(mValues.get(position).getYear());
            holder.joTextJumlahKaryawan.setText(mValues.get(position).getJumlah()+" Orang");

            double one = Double.valueOf(mValues.get(position).getGP());
            double two = Double.valueOf(mValues.get(position).getGL());
            double three = Double.valueOf(mValues.get(position).getTunjangan());
            double four = Double.valueOf(mValues.get(position).getProject_location());
            double five = Double.valueOf(mValues.get(position).getAB());

            double subTotal = one+two+three+four-five;

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextSubTotal.setText("Rp. "+ formatter.format(Long.valueOf((int) subTotal)));
            holder.joTextGajiPokok.setText("Rp. "+ formatter.format(Long.valueOf((int) one)));
            holder.joTextGajiLembur.setText("Rp. "+ formatter.format(Long.valueOf((int) two)));
            holder.joTextTunjangan.setText("Rp. "+ formatter.format(Long.valueOf((int) three)));
            holder.joTextTunjanganLokasi.setText("Rp. "+ formatter.format(Long.valueOf((int) four)));
            holder.joTextPotonganAbsent.setText("Rp. "+ formatter.format(Long.valueOf((int) five)));
            if (position == joMppks.size()-1) {
                totalJobOrderPermanen.setText("Rp. " + formatter.format(Long.valueOf(totalPricePermanen)) + " ");
                totalKeseluruhanMp.setText("Rp. " + formatter.format(Long.valueOf(totalPriceAll)) + " ");
            }
            if (position%2==0)
                holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextNo;
            public final TextView joTextBulan;
            public final TextView joTextGajiPokok;
            public final TextView joTextGajiLembur;
            public final TextView joTextTunjangan;
            public final TextView joTextTunjanganLokasi;
            public final TextView joTextPotonganAbsent;
            public final TextView joTextSubTotal;
            public final TextView joTextJumlahKaryawan;

            public final LinearLayout layoutJo;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextNo = (TextView) itemView.findViewById(R.id.joTextNo);
                joTextBulan = (TextView) itemView.findViewById(R.id.joTextBulan);
                joTextGajiPokok = (TextView) itemView.findViewById(R.id.joTextGajiPokok);
                joTextGajiLembur = (TextView) itemView.findViewById(R.id.joTextGajiLembur);
                joTextTunjangan = (TextView) itemView.findViewById(R.id.joTextTunjangan);
                joTextTunjanganLokasi = (TextView) itemView.findViewById(R.id.joTextTunjanganLokasi);
                joTextPotonganAbsent = (TextView) itemView.findViewById(R.id.joTextPotonganAbsent);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);
                joTextJumlahKaryawan = (TextView) itemView.findViewById(R.id.joTextJumlahKaryawan);

                layoutJo = (LinearLayout) itemView.findViewById(R.id.layoutJo);
            }
        }
    }

    private class MyRecyclerViewAdapterTemp extends RecyclerView.Adapter<MyRecyclerViewAdapterTemp.ViewHolder> {

        private final List<JoMptm> mValues;
        private Context context;

        private MyRecyclerViewAdapterTemp(List<JoMptm> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_mptemp_list, parent, false);
            return new MyRecyclerViewAdapterTemp.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapterTemp.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.joTextNo.setText("" + nomor);
            holder.joTextBulan.setText(mValues.get(position).getYear());
            holder.joTextJumlahKaryawan.setText(mValues.get(position).getJumlah() + " Orang");

            double one = Double.valueOf(mValues.get(position).getGP());
            double two = Double.valueOf(mValues.get(position).getGL());
            double three = Double.valueOf(mValues.get(position).getTunjangan());
            double four = Double.valueOf(mValues.get(position).getProject_location());

            double subTotal = one+two+three+four;

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextSubTotal.setText("Rp. "+ formatter.format(Long.valueOf((int) subTotal)));
            holder.joTextGajiPokok.setText("Rp. "+ formatter.format(Long.valueOf(mValues.get(position).getGP())));
            holder.joTextGajiLembur.setText("Rp. "+ formatter.format(Long.valueOf(mValues.get(position).getGL())));
            holder.joTextTunjangan.setText("Rp. "+ formatter.format(Long.valueOf(mValues.get(position).getTunjangan())));

            if (position == joMptms.size()-1) {
                totalJobOrderTemp.setText("Rp. " + formatter.format(Long.valueOf(totalPriceTemp)) + " ");
                totalKeseluruhanMp.setText("Rp. " + formatter.format(Long.valueOf(totalPriceAll)) + " ");
            }

            if (position%2==0)
                holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextNo;
            public final TextView joTextBulan;
            public final TextView joTextGajiPokok;
            public final TextView joTextGajiLembur;
            public final TextView joTextTunjangan;
            public final TextView joTextSubTotal;
            public final TextView joTextJumlahKaryawan;

            public final LinearLayout layoutJo;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextNo = (TextView) itemView.findViewById(R.id.joTextNo);
                joTextBulan = (TextView) itemView.findViewById(R.id.joTextBulan);
                joTextGajiPokok = (TextView) itemView.findViewById(R.id.joTextGajiPokok);
                joTextGajiLembur = (TextView) itemView.findViewById(R.id.joTextGajiLembur);
                joTextTunjangan = (TextView) itemView.findViewById(R.id.joTextTunjangan);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);
                joTextJumlahKaryawan = (TextView) itemView.findViewById(R.id.joTextJumlahKaryawan);

                layoutJo = (LinearLayout) itemView.findViewById(R.id.layoutJo);
            }
        }
    }
}