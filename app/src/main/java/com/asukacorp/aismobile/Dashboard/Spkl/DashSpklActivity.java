package com.asukacorp.aismobile.Dashboard.Spkl;

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
import com.asukacorp.aismobile.Data.Project.Spkl;
import com.asukacorp.aismobile.Project.Spkl.DetailSpklActivity;
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

public class DashSpklActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Spkl> spkls;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_spkl);

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

//        loadDetail();
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_SPKL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spkls.add(new Spkl(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(spkls, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashSpklActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashSpklActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashSpklActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<Spkl> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<Spkl> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_spkl_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.pskTextSpklNumber.setText(""+mValues.get(position).getOvertime_workorder_number());
            holder.pskTextDate.setText(""+mValues.get(position).getProposed_date());
            holder.pskTextDescription.setText(""+mValues.get(position).getWork_description());
            holder.pskTextLocation.setText(""+mValues.get(position).getWork_location());
            holder.pskTextJobCode.setText(""+mValues.get(position).getJob_order_id());
            holder.pskTextFile.setText(""+mValues.get(position).getOvertime_file_name());
            holder.pskTextDepartment.setText(""+mValues.get(position).getDepartment_id());
            holder.pskTextApproval1.setText(""+mValues.get(position).getApproval2_by());
            holder.pskTextApproval2.setText(""+mValues.get(position).getApproval2_by());
            holder.pskTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());

            if (position%2==0)
                holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.pskLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            ViewGroup.LayoutParams params = holder.pskBtnEdit.getLayoutParams();
            params.height = 0;
            holder.pskBtnEdit.setLayoutParams(params);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getOvertime_workorder_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView pskTextSpklNumber;
            public final TextView pskTextDate;
            public final TextView pskTextDescription;
            public final TextView pskTextLocation;
            public final TextView pskTextJobCode;
            public final TextView pskTextFile;
            public final TextView pskTextDepartment;
            public final TextView pskTextApproval1;
            public final TextView pskTextApproval2;
            public final TextView pskTextVerifiedBy;

            public final ImageView pskBtnEdit;
            public final LinearLayout pskLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                pskTextSpklNumber = (TextView) view.findViewById(R.id.pskTextSpklNumber);
                pskTextDate = (TextView) view.findViewById(R.id.pskTextDate);
                pskTextDescription = (TextView) view.findViewById(R.id.pskTextDescription);
                pskTextLocation = (TextView) view.findViewById(R.id.pskTextLocation);
                pskTextJobCode = (TextView) view.findViewById(R.id.pskTextJobCode);
                pskTextFile = (TextView) view.findViewById(R.id.pskTextFile);
                pskTextDepartment = (TextView) view.findViewById(R.id.pskTextDepartment);
                pskTextApproval1 = (TextView) view.findViewById(R.id.pskTextApproval1);
                pskTextApproval2 = (TextView) view.findViewById(R.id.pskTextApproval2);
                pskTextVerifiedBy = (TextView) view.findViewById(R.id.pskTextVerifiedBy);

                pskBtnEdit = (ImageView) view.findViewById(R.id.pskBtnEdit);
                pskLayoutList = (LinearLayout) view.findViewById(R.id.pskLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final Spkl materialRequest) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashSpklActivity.this, DetailSpklActivity.class);
                        intent.putExtra("detail", materialRequest);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashSpklActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashSpklActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "overtime-workorder");
                param.put("access", "" + "overtime-workorder:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashSpklActivity.this).add(request);
    }
}