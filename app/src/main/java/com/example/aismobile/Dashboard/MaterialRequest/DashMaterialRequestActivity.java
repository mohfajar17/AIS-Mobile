package com.example.aismobile.Dashboard.MaterialRequest;

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
import android.widget.ImageView;
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
import com.example.aismobile.Data.Project.MaterialRequest;
import com.example.aismobile.Project.MaterialRequest.DetailMaterialRequestActivity;
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

public class DashMaterialRequestActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<MaterialRequest> materialRequests;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_material_request);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        materialRequests = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(null);
                materialRequests.clear();
                loadDetail();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_MATERIAL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            materialRequests.add(new MaterialRequest(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(materialRequests, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashMaterialRequestActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashMaterialRequestActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashMaterialRequestActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<MaterialRequest> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<MaterialRequest> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_material_req_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pmrTextNomorMaterial.setText(""+mValues.get(position).getMaterial_request_number());
            holder.pmrTextJobCode.setText(""+mValues.get(position).getJob_order_id());
            holder.pmrTextTglPermintaan.setText(""+mValues.get(position).getRequisition_date());
            holder.pmrStatus.setText(""+mValues.get(position).getMaterial_request_status());
            holder.pmrTextDibuat.setText(""+mValues.get(position).getCreated_by());
            holder.pmrTextApproval1.setText(""+mValues.get(position).getApproval1());
            holder.pmrTextApproval2.setText(""+mValues.get(position).getApproval2());
            holder.pmrTextApproval3.setText(""+mValues.get(position).getApproval3());

            if (Integer.valueOf(mValues.get(position).getPriority())==4)
                holder.pmrTextPriority.setText("Urgent");
            else if (Integer.valueOf(mValues.get(position).getPriority())==3)
                holder.pmrTextPriority.setText("High");
            else if (Integer.valueOf(mValues.get(position).getPriority())==2)
                holder.pmrTextPriority.setText("Medium");
            else holder.pmrTextPriority.setText("Normal");

            if (position%2==0)
                holder.pmrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pmrLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getMaterial_request_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView pmrTextNomorMaterial;
            public final TextView pmrTextJobCode;
            public final TextView pmrTextTglPermintaan;
            public final TextView pmrStatus;
            public final TextView pmrTextDibuat;
            public final TextView pmrTextApproval1;
            public final TextView pmrTextApproval2;
            public final TextView pmrTextApproval3;
            public final TextView pmrTextPriority;

            public final LinearLayout pmrLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pmrTextNomorMaterial = (TextView) view.findViewById(R.id.pmrTextNomorMaterial);
                pmrTextJobCode = (TextView) view.findViewById(R.id.pmrTextJobCode);
                pmrTextTglPermintaan = (TextView) view.findViewById(R.id.pmrTextTglPermintaan);
                pmrStatus = (TextView) view.findViewById(R.id.pmrStatus);
                pmrTextDibuat = (TextView) view.findViewById(R.id.pmrTextDibuat);
                pmrTextApproval1 = (TextView) view.findViewById(R.id.pmrTextApproval1);
                pmrTextApproval2 = (TextView) view.findViewById(R.id.pmrTextApproval2);
                pmrTextApproval3 = (TextView) view.findViewById(R.id.pmrTextApproval3);
                pmrTextPriority = (TextView) view.findViewById(R.id.pmrTextPriority);

                pmrLayoutList = (LinearLayout) view.findViewById(R.id.pmrLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final MaterialRequest materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashMaterialRequestActivity.this, DetailMaterialRequestActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashMaterialRequestActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashMaterialRequestActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashMaterialRequestActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "material-request");
                param.put("access", "" + "material-request:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashMaterialRequestActivity.this).add(request);
    }
}