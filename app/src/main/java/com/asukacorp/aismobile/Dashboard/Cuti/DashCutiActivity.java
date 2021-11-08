package com.asukacorp.aismobile.Dashboard.Cuti;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.Cuti;
import com.asukacorp.aismobile.Project.CutiProject.DetailCutiProjectActivity;
import com.asukacorp.aismobile.Project.CutiProject.EditCutiProjectActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashCutiActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Cuti> spkls;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_cuti);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        spkls = new ArrayList<>();
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
                spkls.clear();
                loadDetail();
            }
        });
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        spkls.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_CUTI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spkls.add(new Cuti(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(spkls, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashCutiActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCutiActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCutiActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashCutiActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<Cuti> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<Cuti> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_cuti_project_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pcTextLeaveNumber.setText(""+mValues.get(position).getEmployee_leave_number());
            holder.pcTextNamaKaryawan.setText(""+mValues.get(position).getEmployee());
            holder.pcTextKategoriCuti.setText(""+mValues.get(position).getLeave_category_name());
            holder.pcTextApprover.setText(""+mValues.get(position).getApprover());
            holder.pcTextProcessed.setText(""+mValues.get(position).getProcessed_by());

            if (position%2==0)
                holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess(mValues.get(position), 1, "employee-leave:view-khusus");
                }
            });

            holder.pcBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.valueOf(mValues.get(position).getIs_approved())<1)
                        loadAccess(mValues.get(position), 2, "employee-leave:update-khusus");
                    else Toast.makeText(DashCutiActivity.this, "Employee Leave has been Approve", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView pcTextLeaveNumber;
            public final TextView pcTextNamaKaryawan;
            public final TextView pcTextKategoriCuti;
            public final TextView pcTextApprover;
            public final TextView pcTextProcessed;

            public final ImageView pcBtnEdit;
            public final LinearLayout pskLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pcTextLeaveNumber = (TextView) view.findViewById(R.id.pcTextLeaveNumber);
                pcTextNamaKaryawan = (TextView) view.findViewById(R.id.pcTextNamaKaryawan);
                pcTextKategoriCuti = (TextView) view.findViewById(R.id.pcTextKategoriCuti);
                pcTextApprover = (TextView) view.findViewById(R.id.pcTextApprover);
                pcTextProcessed = (TextView) view.findViewById(R.id.pcTextProcessed);

                pcBtnEdit = (ImageView) view.findViewById(R.id.pcBtnEdit);
                pskLayoutList = (LinearLayout) view.findViewById(R.id.pcLayoutList);
            }
        }
    }

    private void loadAccess(final Cuti cuti, final int code, final String access) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    int isMobile=jsonObject.getInt("is_mobile");
                    if (Integer.valueOf(jsonData.getString("view"))==1 && isMobile==1 && code==1){
                        Intent intent = new Intent(DashCutiActivity.this, DetailCutiProjectActivity.class);
                        intent.putExtra("detail", cuti);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
//                        DashCutiActivity.this.startActivity(intent);
                    } else if (Integer.valueOf(jsonData.getString("update"))==1 && isMobile==1 && code==2){
                        Intent intent = new Intent(DashCutiActivity.this, EditCutiProjectActivity.class);
                        intent.putExtra("idEmpLeave", cuti.getEmployee_leave_id());
                        onPause();
                        startActivity(intent);
//                        DashCutiActivity.this.startActivity(intent);
                    } else {
                        Toast.makeText(DashCutiActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashCutiActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashCutiActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", sharedPrefManager.getUserId());
                param.put("access", access);
                return param;
            }
        };
        Volley.newRequestQueue(DashCutiActivity.this).add(request);
    }
}