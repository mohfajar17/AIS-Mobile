package com.asukacorp.aismobile.Dashboard.TunjanganLokasi;

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
import com.asukacorp.aismobile.Data.Project.TunjanganKaryawan;
import com.asukacorp.aismobile.Project.TunjanganKaryawan.DetailTunjanganKaryawanActivity;
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

public class DashTunjanganLokasiActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<TunjanganKaryawan> tunjanganKaryawans;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private FloatingActionButton fabRefresh;

    public SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_tunjangan_lokasi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        context = getApplicationContext();
        tunjanganKaryawans = new ArrayList<>();
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
                tunjanganKaryawans.clear();
                loadDetail();
            }
        });

//        loadDetail();
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        tunjanganKaryawans.clear();
        loadDetail();

        super.onResume();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_APPROVAL_TUNJANGAN_KARYAWAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjanganKaryawans.add(new TunjanganKaryawan(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(tunjanganKaryawans, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DashTunjanganLokasiActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashTunjanganLokasiActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashTunjanganLokasiActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(DashTunjanganLokasiActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<TunjanganKaryawan> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<TunjanganKaryawan> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_tun_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.ptkTextNomor.setText(""+mValues.get(position).getEmployee_allowance_number());
            holder.ptkTextKaryawan.setText(""+mValues.get(position).getEmployee_id());
            holder.ptkTextType.setText(""+mValues.get(position).getAdditional_allowance_type());
            holder.ptkTextKabupaten.setText(""+mValues.get(position).getKab_id());
            holder.ptkTextJobOrder.setText(""+mValues.get(position).getJob_order_id());
            holder.ptkTextTglAwal.setText(""+mValues.get(position).getBegin_date());
            holder.ptkTextTglAkhir.setText(""+mValues.get(position).getEnd_date());
            holder.ptkTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());
            holder.ptkTextApproval1.setText(""+mValues.get(position).getApproval1_by());
            holder.ptkTextApproval2.setText(""+mValues.get(position).getApproval2_by());
            holder.ptkTextPaid.setText(""+mValues.get(position).getPaid());

            if (position%2==0)
                holder.ptkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.ptkLayoutList.setBackgroundColor(getResources().getColor(R.color.colorLightGray));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadAccess("" + mValues.get(position).getEmployee_allowance_id(), mValues.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView ptkTextNomor;
            public final TextView ptkTextKaryawan;
            public final TextView ptkTextType;
            public final TextView ptkTextKabupaten;
            public final TextView ptkTextJobOrder;
            public final TextView ptkTextTglAwal;
            public final TextView ptkTextTglAkhir;
            public final TextView ptkTextVerifiedBy;
            public final TextView ptkTextApproval1;
            public final TextView ptkTextApproval2;
            public final TextView ptkTextPaid;

            public final LinearLayout ptkLayoutList;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                ptkTextNomor = (TextView) view.findViewById(R.id.ptkTextNomor);
                ptkTextKaryawan = (TextView) view.findViewById(R.id.ptkTextKaryawan);
                ptkTextType = (TextView) view.findViewById(R.id.ptkTextType);
                ptkTextKabupaten = (TextView) view.findViewById(R.id.ptkTextKabupaten);
                ptkTextJobOrder = (TextView) view.findViewById(R.id.ptkTextJobOrder);
                ptkTextTglAwal = (TextView) view.findViewById(R.id.ptkTextTglAwal);
                ptkTextTglAkhir = (TextView) view.findViewById(R.id.ptkTextTglAkhir);
                ptkTextVerifiedBy = (TextView) view.findViewById(R.id.ptkTextVerifiedBy);
                ptkTextApproval1 = (TextView) view.findViewById(R.id.ptkTextApproval1);
                ptkTextApproval2 = (TextView) view.findViewById(R.id.ptkTextApproval2);
                ptkTextPaid = (TextView) view.findViewById(R.id.ptkTextPaid);

                ptkLayoutList = (LinearLayout) view.findViewById(R.id.ptkLayoutList);
            }
        }
    }

    private void loadAccess(final String id, final TunjanganKaryawan tunjanganKaryawan) {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_VIEW_ACCESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        Intent intent = new Intent(DashTunjanganLokasiActivity.this, DetailTunjanganKaryawanActivity.class);
                        intent.putExtra("detail", tunjanganKaryawan);
                        intent.putExtra("code", 1);
                        onPause();
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashTunjanganLokasiActivity.this, "You don't have access", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DashTunjanganLokasiActivity.this, "null", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DashTunjanganLokasiActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", "" + sharedPrefManager.getUserId());
                param.put("feature", "" + "employee-allowance");
                param.put("access", "" + "employee-allowance:view");
                param.put("id", "" + id);
                return param;
            }
        };
        Volley.newRequestQueue(DashTunjanganLokasiActivity.this).add(request);
    }
}