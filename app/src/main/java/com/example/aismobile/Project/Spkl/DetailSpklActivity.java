package com.example.aismobile.Project.Spkl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.aismobile.Data.Project.Spkl;
import com.example.aismobile.Data.Project.SpklDetail;
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

public class DetailSpklActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SpklDetail> jomrs;
    private ProgressDialog progressDialog;

    private TextView menuSpklDetail;
    private TextView menuSpklHistory;
    private TextView textSpklNumber;
    private TextView jodKeterangan;
    private TextView detailSpklDepartment;
    private TextView detailSpklLocation;
    private TextView detailSpklJobCode;
    private TextView detailSpklProposedDate;
    private TextView detailSpklReq;
    private TextView detailSpklReqDate;
    private TextView detailSpklApproval1;
    private TextView detailSpklApproval2;
    private TextView detailSpklVerifiedBy;
    private TextView historySpklCreatedBy;
    private TextView historySpklCreatedDate;
    private TextView historySpklModifiedBy;
    private TextView historySpklModifiedDate;

    private LinearLayout layoutHistory;
    private ScrollView layoutDetail;

    private Spkl spkls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spkl);

        Bundle bundle = getIntent().getExtras();
        spkls = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        jomrs = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSpkl);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        menuSpklDetail = (TextView) findViewById(R.id.menuSpklDetail);
        menuSpklHistory = (TextView) findViewById(R.id.menuSpklHistory);
        textSpklNumber = (TextView) findViewById(R.id.textSpklNumber);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        detailSpklDepartment = (TextView) findViewById(R.id.detailSpklDepartment);
        detailSpklLocation = (TextView) findViewById(R.id.detailSpklLocation);
        detailSpklJobCode = (TextView) findViewById(R.id.detailSpklJobCode);
        detailSpklProposedDate = (TextView) findViewById(R.id.detailSpklProposedDate);
        detailSpklReq = (TextView) findViewById(R.id.detailSpklReq);
        detailSpklReqDate = (TextView) findViewById(R.id.detailSpklReqDate);
        detailSpklApproval1 = (TextView) findViewById(R.id.detailSpklApproval1);
        detailSpklApproval2 = (TextView) findViewById(R.id.detailSpklApproval2);
        detailSpklVerifiedBy = (TextView) findViewById(R.id.detailSpklVerifiedBy);
        historySpklCreatedBy = (TextView) findViewById(R.id.historySpklCreatedBy);
        historySpklCreatedDate = (TextView) findViewById(R.id.historySpklCreatedDate);
        historySpklModifiedBy = (TextView) findViewById(R.id.historySpklModifiedBy);
        historySpklModifiedDate = (TextView) findViewById(R.id.historySpklModifiedDate);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);

        textSpklNumber.setText(spkls.getOvertime_workorder_number());
        jodKeterangan.setText("Keterangan : " + spkls.getWork_description());
        detailSpklDepartment.setText(spkls.getDepartment_id());
        detailSpklLocation.setText(spkls.getWork_location());
        detailSpklJobCode.setText(spkls.getJob_order_id());
        detailSpklProposedDate.setText(spkls.getProposed_date());
        detailSpklReq.setText(spkls.getRequested_id());
        detailSpklReqDate.setText(spkls.getRequest_date());
        detailSpklApproval1.setText(spkls.getApproval1_by() + "\n" + spkls.getApproval1_date());
        detailSpklApproval2.setText(spkls.getApproval2_by() + "\n" + spkls.getApproval2_date());
        detailSpklVerifiedBy.setText(spkls.getVerified_by() + "\n" + spkls.getVerified_date());
        historySpklCreatedBy.setText(spkls.getCreated_by());
        historySpklCreatedDate.setText(spkls.getCreated_date());
        historySpklModifiedBy.setText(spkls.getModified_by());
        historySpklModifiedDate.setText(spkls.getModified_date());

        menuSpklDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutHistory.getLayoutParams();
                params.height = 0;
                layoutHistory.setLayoutParams(params);
                params = layoutDetail.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutDetail.setLayoutParams(params);
                menuSpklHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                menuSpklHistory.setTextColor(getResources().getColor(R.color.colorWhite));
                menuSpklDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuSpklDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuSpklHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutDetail.getLayoutParams();
                params.height = 0;
                layoutDetail.setLayoutParams(params);
                params = layoutHistory.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutHistory.setLayoutParams(params);
                menuSpklDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
                menuSpklDetail.setTextColor(getResources().getColor(R.color.colorWhite));
                menuSpklHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuSpklHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });

        loadSpklDetail();
    }

    public void loadSpklDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_SPKL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            jomrs.add(new SpklDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(jomrs, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailSpklActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailSpklActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + spkls.getOvertime_workorder_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailSpklActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<SpklDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<SpklDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_spkl_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.spklTextNo.setText("" + nomor);
            holder.spklTextKaryawan.setText(mValues.get(position).getFullname());
            holder.spklTextPosisi.setText(mValues.get(position).getJob_grade_name());
            holder.spklTextTglLembur.setText(mValues.get(position).getOvertime_date());
            holder.spklTextStartTime.setText(mValues.get(position).getStart_time());
            holder.spklTextFinishTime.setText(mValues.get(position).getFinish_time());
            holder.spklTextDescription.setText(mValues.get(position).getDescription());
            holder.spklTextApproval1.setText(mValues.get(position).getApproval_status1());
            holder.spklTextApproval2.setText(mValues.get(position).getApproval_status2());

            if (position%2==0)
                holder.layoutSpkl.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layoutSpkl.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView spklTextNo;
            public final TextView spklTextKaryawan;
            public final TextView spklTextPosisi;
            public final TextView spklTextTglLembur;
            public final TextView spklTextStartTime;
            public final TextView spklTextFinishTime;
            public final TextView spklTextDescription;
            public final TextView spklTextApproval1;
            public final TextView spklTextApproval2;

            public final LinearLayout layoutSpkl;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                spklTextNo = (TextView) itemView.findViewById(R.id.spklTextNo);
                spklTextKaryawan = (TextView) itemView.findViewById(R.id.spklTextKaryawan);
                spklTextPosisi = (TextView) itemView.findViewById(R.id.spklTextPosisi);
                spklTextTglLembur = (TextView) itemView.findViewById(R.id.spklTextTglLembur);
                spklTextStartTime = (TextView) itemView.findViewById(R.id.spklTextStartTime);
                spklTextFinishTime = (TextView) itemView.findViewById(R.id.spklTextFinishTime);
                spklTextDescription = (TextView) itemView.findViewById(R.id.spklTextDescription);
                spklTextApproval1 = (TextView) itemView.findViewById(R.id.spklTextApproval1);
                spklTextApproval2 = (TextView) itemView.findViewById(R.id.spklTextApproval2);

                layoutSpkl = (LinearLayout) itemView.findViewById(R.id.layoutSpkl);
            }
        }
    }
}