package com.asukacorp.aismobile.Project.WorkCompletion;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.WorkCompletion;
import com.asukacorp.aismobile.Data.Project.WorkCompletionDetail;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailWorkCompletionActivity extends AppCompatActivity {

    private WorkCompletion workCompletion;
    private Context context;
    private RecyclerView wcRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<WorkCompletionDetail> workCompletionDetails;
    private ProgressDialog progressDialog;
    private int totalNilai = 0;

    private ImageView wcButtonBack;
    private TextView wcNumber;
    private TextView wcMenuDetail;
    private TextView wcMenuCatatan;
    private TextView wcMenuHistory;
    private ScrollView wcLayoutDetail;
    private LinearLayout wcLayoutCatatan;
    private LinearLayout wcLayoutHistory;

    private TextView wcPreparedBy;
    private TextView wcJobOrder;
    private TextView wcProgress;
    private TextView wcKeterangan;
    private TextView wcSqNumber;
    private TextView wcPerusahaan;
    private TextView wcTerminPembayaran;
    private TextView wcClientPONumber;
    private TextView wcStartWork;
    private TextView wcEndWork;
    private TextView wcDiterima1;
    private TextView wcDiterima2;
    private TextView wcDiterima3;
    private TextView wcCatatanCatatan;
    private TextView wcCatatanKeterangan;
    private TextView wcCreatedBy;
    private TextView wcCreatedDate;
    private TextView wcModifiedBy;
    private TextView wcModifiedDate;
    private TextView wcGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_completion);

        Bundle bundle = getIntent().getExtras();
        workCompletion = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        workCompletionDetails = new ArrayList<>();

        wcRecyclerView = (RecyclerView) findViewById(R.id.wcRecyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        wcRecyclerView.setLayoutManager(recylerViewLayoutManager);

        wcButtonBack = (ImageView) findViewById(R.id.wcButtonBack);
        wcNumber = (TextView) findViewById(R.id.wcNumber);
        wcMenuDetail = (TextView) findViewById(R.id.wcMenuDetail);
        wcMenuCatatan = (TextView) findViewById(R.id.wcMenuCatatan);
        wcMenuHistory = (TextView) findViewById(R.id.wcMenuHistory);
        wcLayoutDetail = (ScrollView) findViewById(R.id.wcLayoutDetail);
        wcLayoutCatatan = (LinearLayout) findViewById(R.id.wcLayoutCatatan);
        wcLayoutHistory = (LinearLayout) findViewById(R.id.wcLayoutHistory);

        wcPreparedBy = (TextView) findViewById(R.id.wcPreparedBy);
        wcJobOrder = (TextView) findViewById(R.id.wcJobOrder);
        wcProgress = (TextView) findViewById(R.id.wcProgress);
        wcKeterangan = (TextView) findViewById(R.id.wcKeterangan);
        wcSqNumber = (TextView) findViewById(R.id.wcSqNumber);
        wcPerusahaan = (TextView) findViewById(R.id.wcPerusahaan);
        wcTerminPembayaran = (TextView) findViewById(R.id.wcTerminPembayaran);
        wcClientPONumber = (TextView) findViewById(R.id.wcClientPONumber);
        wcStartWork = (TextView) findViewById(R.id.wcStartWork);
        wcEndWork = (TextView) findViewById(R.id.wcEndWork);
        wcDiterima1 = (TextView) findViewById(R.id.wcDiterima1);
        wcDiterima2 = (TextView) findViewById(R.id.wcDiterima2);
        wcDiterima3 = (TextView) findViewById(R.id.wcDiterima3);
        wcCatatanCatatan = (TextView) findViewById(R.id.wcCatatanCatatan);
        wcCatatanKeterangan = (TextView) findViewById(R.id.wcCatatanKeterangan);
        wcCreatedBy = (TextView) findViewById(R.id.wcCreatedBy);
        wcCreatedDate = (TextView) findViewById(R.id.wcCreatedDate);
        wcModifiedBy = (TextView) findViewById(R.id.wcModifiedBy);
        wcModifiedDate = (TextView) findViewById(R.id.wcModifiedDate);
        wcGrandTotal = (TextView) findViewById(R.id.wcGrandTotal);

        wcNumber.setText(workCompletion.getJob_progress_report_number());
        wcPreparedBy.setText(workCompletion.getPrepared_by());
        wcJobOrder.setText(workCompletion.getJob_order_id());
        wcProgress.setText(workCompletion.getProgress_percentage());
        wcKeterangan.setText(workCompletion.getJob_order_description());
        wcSqNumber.setText(workCompletion.getSales_quotation_id());
        wcPerusahaan.setText(workCompletion.getCompany_id());
        wcTerminPembayaran.setText(workCompletion.getPayment_term_id());
        wcClientPONumber.setText(workCompletion.getClient_po_number());
        wcStartWork.setText(workCompletion.getStart_work());
        wcEndWork.setText(workCompletion.getEnd_work());
        wcDiterima1.setText(workCompletion.getAccepted_by());
        wcDiterima2.setText(workCompletion.getAccepted_by2());
        wcDiterima3.setText(workCompletion.getAccepted_by3());
        wcCatatanCatatan.setText(workCompletion.getNotes());
        wcCatatanCatatan.setText(workCompletion.getNotes());
        wcCatatanKeterangan.setText(workCompletion.getDescription());
        wcCreatedBy.setText(workCompletion.getCreated_by());
        wcCreatedDate.setText(workCompletion.getCreated_date());
        wcModifiedBy.setText(workCompletion.getModified_by());
        wcModifiedDate.setText(workCompletion.getModified_date());

        wcButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        wcMenuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params;
                params = wcLayoutCatatan.getLayoutParams();
                params.height = 0;
                wcLayoutCatatan.setLayoutParams(params);
                params = wcLayoutHistory.getLayoutParams();
                params.height = 0;
                wcLayoutHistory.setLayoutParams(params);
                params = wcLayoutDetail.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                wcLayoutDetail.setLayoutParams(params);
                wcMenuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuHistory.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                wcMenuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        wcMenuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params;
                params = wcLayoutDetail.getLayoutParams();
                params.height = 0;
                wcLayoutDetail.setLayoutParams(params);
                params = wcLayoutHistory.getLayoutParams();
                params.height = 0;
                wcLayoutHistory.setLayoutParams(params);
                params = wcLayoutCatatan.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                wcLayoutCatatan.setLayoutParams(params);
                wcMenuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuHistory.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                wcMenuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        wcMenuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params;
                params = wcLayoutCatatan.getLayoutParams();
                params.height = 0;
                wcLayoutCatatan.setLayoutParams(params);
                params = wcLayoutDetail.getLayoutParams();
                params.height = 0;
                wcLayoutDetail.setLayoutParams(params);
                params = wcLayoutHistory.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                wcLayoutHistory.setLayoutParams(params);
                wcMenuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                wcMenuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
                wcMenuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                wcMenuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_WORK_COMPLETION_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            workCompletionDetails.add(new WorkCompletionDetail(jsonArray.getJSONObject(i)));

                            double toDouble = jsonArray.getJSONObject(i).getDouble("amount");
                            totalNilai += (int) toDouble;
                        }
                        adapter = new MyRecyclerViewAdapter(workCompletionDetails, context);
                        wcRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailWorkCompletionActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailWorkCompletionActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailWorkCompletionActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobId", "" + workCompletion.getJob_progress_report_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailWorkCompletionActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<WorkCompletionDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<WorkCompletionDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_work_completion_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.wcTextNo.setText("" + nomor);
            holder.wcTextDetail.setText(mValues.get(position).getDescription());
            holder.wcTextQty.setText(mValues.get(position).getQuantity());
            holder.wcTextSatuan.setText(mValues.get(position).getUnit_abbr());
            holder.wcTextStatus.setText(mValues.get(position).getStatus());

            NumberFormat formatter = new DecimalFormat("#,###");
            double toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.wcTextNilai.setText("Rp. " + formatter.format((long) toDouble));

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            if (position+1 == mValues.size())
                wcGrandTotal.setText("Rp. " + formatter.format((long) totalNilai));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView wcTextNo;
            public final TextView wcTextDetail;
            public final TextView wcTextQty;
            public final TextView wcTextSatuan;
            public final TextView wcTextNilai;
            public final TextView wcTextStatus;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                wcTextNo = (TextView) itemView.findViewById(R.id.wcTextNo);
                wcTextDetail = (TextView) itemView.findViewById(R.id.wcTextDetail);
                wcTextQty = (TextView) itemView.findViewById(R.id.wcTextQty);
                wcTextSatuan = (TextView) itemView.findViewById(R.id.wcTextSatuan);
                wcTextNilai = (TextView) itemView.findViewById(R.id.wcTextNilai);
                wcTextStatus = (TextView) itemView.findViewById(R.id.wcTextStatus);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}