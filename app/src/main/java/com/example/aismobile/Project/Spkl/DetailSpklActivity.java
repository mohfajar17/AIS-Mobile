package com.example.aismobile.Project.Spkl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.example.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailSpklActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Spkl spkls;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SpklDetail> spklDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
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

    private int code = 0, approval = 0, akses1 = 0, akses2 = 0, approve1 = 0, approve2 = 0;
    private ArrayAdapter<String> adapterApproval;
    private LinearLayout layoutApproval;
    private TextView btnApprove1;
    private TextView btnApprove2;
    private TextView btnApprove3;
    private TextView btnSaveApprove;
    private FloatingActionButton fabRefresh;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spkl);

        Bundle bundle = getIntent().getExtras();
        spkls = bundle.getParcelable("detail");
        code = bundle.getInt("code");
        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        spklDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSpkl);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        layoutApproval = (LinearLayout) findViewById(R.id.layoutApproval);
        if (code > 0){
            params = layoutApproval.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutApproval.setLayoutParams(params);
            loadAccess();
            loadAccessApproval();
        }
        btnApprove1 = (TextView) findViewById(R.id.btnApprove1);
        btnApprove2 = (TextView) findViewById(R.id.btnApprove2);
        btnApprove3 = (TextView) findViewById(R.id.btnApprove3);
        btnSaveApprove = (TextView) findViewById(R.id.btnSaveApprove);
        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        btnApprove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 1){
                    btnApprove1.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 1;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(spklDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 2){
                    btnApprove2.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 2;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(spklDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnApprove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                if (approval != 3){
                    btnApprove3.setTextColor(getResources().getColor(R.color.colorBlack));
                    approval = 3;
                    recyclerView.setAdapter(null);
                    adapter = new MyRecyclerViewAdapter(spklDetails, context);
                    recyclerView.setAdapter(adapter);
                } else approval = 0;
            }
        });
        btnSaveApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (approval == 1 && akses1 > 0 && approve1 > 0){
                    for (int i = 0; i<spklDetails.size(); i++)
                        updateApproval(String.valueOf(spklDetails.get(i).getOtwo_detail_id()),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval1)).getSelectedItem().toString(),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.spklTextApproval2)).getText().toString());
                    updateApprovalId();
                } else if (approval == 2 && akses2 > 0 && approve2 > 0){
                    for (int i = 0; i<spklDetails.size(); i++)
                        updateApproval(String.valueOf(spklDetails.get(i).getOtwo_detail_id()),
                                ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.spklTextApproval1)).getText().toString(),
                                ((Spinner) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.editApproval2)).getSelectedItem().toString());
                    updateApprovalId();
                } else if (approval == 3){
                    updateApprovalId();
                } else Toast.makeText(DetailSpklActivity.this, "You don't have access to approve", Toast.LENGTH_LONG).show();
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approval = 0;
                changeColor();
                loadSpklDetail();
            }
        });

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
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
        detailSpklReq.setText(spkls.getCreated_by());
        detailSpklReqDate.setText("");//spkls.getRequest_date());
        detailSpklApproval1.setText(spkls.getApproval1_by() + "\n" + spkls.getApproval1_date());
        detailSpklApproval2.setText(spkls.getApproval2_by() + "\n" + spkls.getApproval2_date());
        detailSpklVerifiedBy.setText(spkls.getVerified_by() + "\n" + spkls.getVerified_date());
        historySpklCreatedBy.setText(spkls.getCreated_by());
        historySpklCreatedDate.setText(spkls.getCreated_date());
        historySpklModifiedBy.setText(spkls.getModified_by());
        historySpklModifiedDate.setText(spkls.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                changeColor();
                approval = 0;
            }
        });

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

    private void loadAccessApproval() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    approve1 = jsonObject.getInt("access1");
                    approve2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("id", "" + spkls.getOvertime_workorder_id());
                param.put("code", "1");
                return param;
            }
        };
        Volley.newRequestQueue(DetailSpklActivity.this).add(request);
    }

    private void loadAccess() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_APPROVAL_ALLOW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    akses1 = jsonObject.getInt("access1");
                    akses2 = jsonObject.getInt("access2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "3");
                return param;
            }
        };
        Volley.newRequestQueue(DetailSpklActivity.this).add(request);
    }

    public void changeColor(){
        btnApprove1.setTextColor(getResources().getColor(R.color.colorWhite));
        btnApprove2.setTextColor(getResources().getColor(R.color.colorWhite));
        btnApprove3.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void updateApproval(final String id, final String approve1, final String approve2){
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_APPROVAL_SPKL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                    } else {
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
                param.put("id", id);
                param.put("approve1", approve1);
                param.put("approve2", approve2);
                return param;
            }
        };
        Volley.newRequestQueue(DetailSpklActivity.this).add(request);
    }

    public void updateApprovalId(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_UPDATE_ID_APPROVAL_SPKL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(DetailSpklActivity.this, "Success update data", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailSpklActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(DetailSpklActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
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
                param.put("id", "" + spkls.getOvertime_workorder_id());
                param.put("user", sharedPrefManager.getUserId());
                param.put("code", "" + approval);
                return param;
            }
        };
        Volley.newRequestQueue(DetailSpklActivity.this).add(request);
    }

    public void loadSpklDetail(){
        progressDialog.show();
        recyclerView.setAdapter(null);
        spklDetails.clear();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_SPKL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spklDetails.add(new SpklDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(spklDetails, context);
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

            if (approval == 1 && code == 1){
                params =  holder.editApproval1.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval1.setLayoutParams(params);
                params =  holder.spklTextApproval1.getLayoutParams();
                params.height = 0;
                holder.spklTextApproval1.setLayoutParams(params);
            } else if (approval == 2 && code == 1) {
                params =  holder.editApproval2.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.editApproval2.setLayoutParams(params);
                params =  holder.spklTextApproval2.getLayoutParams();
                params.height = 0;
                holder.spklTextApproval2.setLayoutParams(params);
            }

            String[] approve = {"Approved", "Reject", "-"};
            adapterApproval = new ArrayAdapter<String>(DetailSpklActivity.this, android.R.layout.simple_spinner_dropdown_item, approve);
            holder.editApproval1.setAdapter(adapterApproval);
            holder.editApproval2.setAdapter(adapterApproval);
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
            public final Spinner editApproval1;
            public final Spinner editApproval2;

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
                editApproval1 = (Spinner) itemView.findViewById(R.id.editApproval1);
                editApproval2 = (Spinner) itemView.findViewById(R.id.editApproval2);

                layoutSpkl = (LinearLayout) itemView.findViewById(R.id.layoutSpkl);
            }
        }
    }
}