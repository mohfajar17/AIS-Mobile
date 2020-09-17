package com.example.aismobile.Project.JobOrder;

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
import com.example.aismobile.Config;
import com.example.aismobile.Data.Project.DetailJobOrder.JoPb;
import com.example.aismobile.Data.Project.DetailJobOrder.JoPbRest;
import com.example.aismobile.Data.Project.JobOrder;
import com.example.aismobile.R;

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

public class JobOrderDetailPbActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<JoPb> joPbs;
    private List<JoPbRest> joPbRests;
    private ProgressDialog progressDialog;
    private MyNewRecyclerViewAdapter newAdapter;

    private Context contextHalf;
    private RecyclerView recyclerViewHalf;
    private MyHalfRecyclerViewAdapter adapterHalf;
    private RecyclerView.LayoutManager recylerViewLayoutManagerHalf;
    private List<JoPb> joPbHalfs;

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

    private TextView jodJobOrder;
    private TextView jodDepartemen;
    private TextView jodKeterangan;
    private TextView jodDay;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail_pb);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        joPbs = new ArrayList<>();
        joPbRests = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAll);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        contextHalf = getApplicationContext();
        joPbHalfs = new ArrayList<>();

        recyclerViewHalf = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManagerHalf = new LinearLayoutManager(contextHalf);
        recyclerViewHalf.setLayoutManager(recylerViewLayoutManagerHalf);

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

        jodJobOrder = (TextView) findViewById(R.id.jodJobOrder);
        jodDepartemen = (TextView) findViewById(R.id.jodDepartemen);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        jodDay = (TextView) findViewById(R.id.jodDay);

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
            else jodDay.setText(""+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" Days Remaining");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        menuJoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailMrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailPrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailTrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailMpActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailCodActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoWo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailWoActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailCprActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailExpensesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailInvoiceActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMatRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailMatRetActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailNotesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPbActivity.this, JobOrderDetailHistoryActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadJobOrderDetail();
        loadHalfData();
    }

    private void loadHalfData() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_PB_HALF_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joPbHalfs.add(new JoPb(jsonArray.getJSONObject(i)));
                        }
                        adapterHalf = new MyHalfRecyclerViewAdapter(joPbHalfs, contextHalf);
                        recyclerViewHalf.setAdapter(adapterHalf);
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
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(JobOrderDetailPbActivity.this).add(request);
    }

    public void loadJobOrderDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_PB_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joPbs.add(new JoPb(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(joPbs, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(JobOrderDetailPbActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(JobOrderDetailPbActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(JobOrderDetailPbActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(JobOrderDetailPbActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<JoPb> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<JoPb> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_pb_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            if (position % 2 == 0)
                holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            int numb = position + 1;
            holder.joTextNo.setText(""+numb);
            holder.joTextPbNumber.setText(mValues.get(position).getCash_advance_number());
            holder.joTextItem.setText(mValues.get(position).getRest_from());

            double restValue = Double.valueOf(mValues.get(position).getRest_value());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextSubTotal.setText("Rp. "+ formatter.format(Long.valueOf((int) restValue)));

            recylerViewLayoutManager = new LinearLayoutManager(context);
            holder.recyclerViewList.setLayoutManager(recylerViewLayoutManager);

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_PB_REST_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status=jsonObject.getInt("status");
                        if(status==1){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                joPbRests.add(new JoPbRest(jsonArray.getJSONObject(i)));
                            }
                            newAdapter = new MyNewRecyclerViewAdapter(joPbRests, context);
                            holder.recyclerViewList.setAdapter(newAdapter);
                        } else {
                            Toast.makeText(JobOrderDetailPbActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(JobOrderDetailPbActivity.this, "", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(JobOrderDetailPbActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("jobOrder", "" + mValues.get(position).getCash_advance_id());
                    return param;
                }
            };
            Volley.newRequestQueue(JobOrderDetailPbActivity.this).add(request);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextNo;
            public final TextView joTextPbNumber;
            public final TextView joTextItem;
            public final TextView joTextSubTotal;
            public final RecyclerView recyclerViewList;

            public final LinearLayout layoutJo;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextNo = (TextView) itemView.findViewById(R.id.joTextNo);
                joTextPbNumber = (TextView) itemView.findViewById(R.id.joTextPbNumber);
                joTextItem = (TextView) itemView.findViewById(R.id.joTextItem);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);
                recyclerViewList = (RecyclerView) itemView.findViewById(R.id.recyclerViewList);

                layoutJo = (LinearLayout) itemView.findViewById(R.id.layoutJo);
            }
        }
    }

    private class MyNewRecyclerViewAdapter extends RecyclerView.Adapter<MyNewRecyclerViewAdapter.ViewHolder> {

        private final List<JoPbRest> mValues;
        private Context context;

        private MyNewRecyclerViewAdapter(List<JoPbRest> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_pb_list_other, parent, false);
            return new MyNewRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyNewRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.joTextItem.setText(mValues.get(position).getItem_name());
            holder.joTextQty.setText(mValues.get(position).getQuantity() + " " + mValues.get(position).getUnit_abbr());

            double quantity = Double.valueOf(mValues.get(position).getQuantity());
            double price = Double.valueOf(mValues.get(position).getUnit_price());
            int subTotal = (int) quantity * (int) price;

            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextUnitPrice.setText("Rp. "+ formatter.format(Long.valueOf((int) price)));
            holder.joTextSubTotal.setText("Rp. "+ formatter.format(Long.valueOf(subTotal)));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextItem;
            public final TextView joTextQty;
            public final TextView joTextUnitPrice;
            public final TextView joTextSubTotal;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextItem = (TextView) itemView.findViewById(R.id.joTextItem);
                joTextQty = (TextView) itemView.findViewById(R.id.joTextQty);
                joTextUnitPrice = (TextView) itemView.findViewById(R.id.joTextUnitPrice);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);
            }
        }
    }

    private class MyHalfRecyclerViewAdapter extends RecyclerView.Adapter<MyHalfRecyclerViewAdapter.ViewHolder> {

        private final List<JoPb> mValues;
        private Context context;

        private MyHalfRecyclerViewAdapter(List<JoPb> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_pb_list, parent, false);
            return new MyHalfRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyHalfRecyclerViewAdapter.ViewHolder holder, final int position) {
            if (position % 2 == 0)
                holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layoutJo.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            int numb = position + 1;
            holder.joTextNo.setText(""+numb);
            holder.joTextPbNumber.setText(mValues.get(position).getCash_advance_number());
            holder.joTextItem.setText(mValues.get(position).getRest_from());

            double restValue = Double.valueOf(mValues.get(position).getRest_value());
            NumberFormat formatter = new DecimalFormat("#,###");
            holder.joTextSubTotal.setText("Rp. "+ formatter.format(Long.valueOf((int) restValue)));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView joTextNo;
            public final TextView joTextPbNumber;
            public final TextView joTextItem;
            public final TextView joTextSubTotal;

            public final LinearLayout layoutJo;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                joTextNo = (TextView) itemView.findViewById(R.id.joTextNo);
                joTextPbNumber = (TextView) itemView.findViewById(R.id.joTextPbNumber);
                joTextItem = (TextView) itemView.findViewById(R.id.joTextItem);
                joTextSubTotal = (TextView) itemView.findViewById(R.id.joTextSubTotal);

                layoutJo = (LinearLayout) itemView.findViewById(R.id.layoutJo);
            }
        }
    }
}