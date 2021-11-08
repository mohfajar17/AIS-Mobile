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
import com.asukacorp.aismobile.Data.Project.DetailJobOrder.JoPr;
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

public class JobOrderDetailPrActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<JoPr> joprs;
    private ProgressDialog progressDialog;
    private double totalPrice = 0;

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
    private TextView totalJobOrder;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail_pr);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        joprs = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerJobOrder);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

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
        totalJobOrder = (TextView) findViewById(R.id.totalJobOrder);

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
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailMrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailTrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailMpActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailCodActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoWo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailWoActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailPbActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailCprActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailExpensesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailInvoiceActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMatRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailMatRetActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailPictureActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailNotesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPrActivity.this, JobOrderDetailHistoryActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadJobOrderDetail();
    }

    public void loadJobOrderDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_PR_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joprs.add(new JoPr(jsonArray.getJSONObject(i)));

                            double quantity = 0;
                            if (jsonArray.getJSONObject(i).getString("quantity_taked").equals("null")) {
                                quantity = 0;
                            } else {
                                quantity = jsonArray.getJSONObject(i).getDouble("quantity_taked");
                            }

                            double price = jsonArray.getJSONObject(i).getDouble("unit_price");
                            double diskon = jsonArray.getJSONObject(i).getDouble("discount");
                            double qty = quantity * price;
                            if ((int) qty > 0) {
                                qty -= diskon;
                            }
                            totalPrice += qty;
                        }
                        adapter = new MyRecyclerViewAdapter(joprs, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(JobOrderDetailPrActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(JobOrderDetailPrActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(JobOrderDetailPrActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(JobOrderDetailPrActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<JoPr> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<JoPr> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_pr_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            double quantity = 0;
            if (String.valueOf(mValues.get(position).getQuantity_taked()).equals("null")) {
                quantity = 0;
                holder.joTextQty.setText("" + (int) quantity);
            } else {
                quantity = Double.valueOf(mValues.get(position).getQuantity_taked());
                holder.joTextQty.setText("" + (int) quantity + " " + mValues.get(position).getUnit_abbr());
            }

            double price = Double.valueOf(mValues.get(position).getUnit_price());
            double diskon = Double.valueOf(mValues.get(position).getDiscount());
            double qty = quantity * price;
            if (qty > 0) {
                qty -= diskon;
            }

            holder.joTextNo.setText("" + nomor);
            holder.joTextItem.setText(mValues.get(position).getItem_name());
            holder.joTextSpesifikasi.setText(mValues.get(position).getItem_specification());
            holder.joTextMrNumber.setText(mValues.get(position).getMaterial_request_number());
            holder.joTextPoNumber.setText(mValues.get(position).getPurchase_order_number());
            holder.joTextPickupNumber.setText(mValues.get(position).getPickup_number());
            holder.joTextStatus.setText(mValues.get(position).getStatus());

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextUnitPrice.setText("Rp. "+ formatter.format((long) price));
            holder.joTextDiscount.setText("Rp. "+ formatter.format((long) diskon));
            holder.joTextSubTotal.setText("Rp. "+ formatter.format((long) qty));
            if (position == joprs.size()-1)
                totalJobOrder.setText("Rp. "+formatter.format((long) totalPrice) + " ");

            if (position%2==0)
                holder.layoutJoMr.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutJoMr.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextNo;
            public final TextView joTextItem;
            public final TextView joTextSpesifikasi;
            public final TextView joTextMrNumber;
            public final TextView joTextPoNumber;
            public final TextView joTextPickupNumber;
            public final TextView joTextQty;
            public final TextView joTextStatus;
            public final TextView joTextUnitPrice;
            public final TextView joTextDiscount;
            public final TextView joTextSubTotal;

            public final LinearLayout layoutJoMr;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextNo = (TextView) itemView.findViewById(R.id.joTextNo);
                joTextItem = (TextView) itemView.findViewById(R.id.joTextItem);
                joTextSpesifikasi = (TextView) itemView.findViewById(R.id.joTextSpesifikasi);
                joTextMrNumber = (TextView) itemView.findViewById(R.id.joTextMrNumber);
                joTextPoNumber = (TextView) itemView.findViewById(R.id.joTextPoNumber);
                joTextPickupNumber = (TextView) itemView.findViewById(R.id.joTextPickupNumber);
                joTextQty = (TextView) itemView.findViewById(R.id.joTextQty);
                joTextStatus = (TextView) itemView.findViewById(R.id.joTextStatus);
                joTextUnitPrice = (TextView) itemView.findViewById(R.id.joTextUnitPrice);
                joTextDiscount = (TextView) itemView.findViewById(R.id.joTextDiscount);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);

                layoutJoMr = (LinearLayout) itemView.findViewById(R.id.layoutJoMr);
            }
        }
    }
}