package com.asukacorp.aismobile.Personalia.Kerja;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Personalia.Fullname;
import com.asukacorp.aismobile.Data.Personalia.RiwayatDetail;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiwayatDetailActivity extends AppCompatActivity {

    private Fullname employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<RiwayatDetail> employeeDet;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textKaryawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_detail);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDet = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textKaryawan = (TextView) findViewById(R.id.textKaryawan);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textKaryawan.setText("#" + employee.getFullname());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_HISTORY_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDet.add(new RiwayatDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(employeeDet, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(RiwayatDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(RiwayatDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RiwayatDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(RiwayatDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<RiwayatDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<RiwayatDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_riwayat_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textTgl.setText(mValues.get(position).getHistory_date());
            holder.textBedge.setText(mValues.get(position).getEmployee_number());
            holder.textNamaLengkap.setText(mValues.get(position).getFullname());
            holder.textNamaJenjang.setText(mValues.get(position).getEmployee_grade_name());
            holder.textNamaStatus.setText(mValues.get(position).getMarital_status_name());
            holder.textNamaLokasi.setText(mValues.get(position).getCompany_workbase_name());
            holder.textCatatan.setText(mValues.get(position).getNotes());

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textTgl;
            public final TextView textBedge;
            public final TextView textNamaLengkap;
            public final TextView textNamaJenjang;
            public final TextView textNamaStatus;
            public final TextView textNamaLokasi;
            public final TextView textCatatan;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textTgl = (TextView) itemView.findViewById(R.id.textTgl);
                textBedge = (TextView) itemView.findViewById(R.id.textBedge);
                textNamaLengkap = (TextView) itemView.findViewById(R.id.textNamaLengkap);
                textNamaJenjang = (TextView) itemView.findViewById(R.id.textNamaJenjang);
                textNamaStatus = (TextView) itemView.findViewById(R.id.textNamaStatus);
                textNamaLokasi = (TextView) itemView.findViewById(R.id.textNamaLokasi);
                textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}