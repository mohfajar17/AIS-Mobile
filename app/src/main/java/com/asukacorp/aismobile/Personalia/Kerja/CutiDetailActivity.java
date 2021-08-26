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
import com.asukacorp.aismobile.Data.Personalia.Employee.EmployeeDetLeave;
import com.asukacorp.aismobile.Data.Personalia.Fullname;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutiDetailActivity extends AppCompatActivity {

    private Fullname employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeDetLeave> employeeDetLeaves;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textKaryawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti_detail);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetLeaves = new ArrayList<>();

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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetLeaves.add(new EmployeeDetLeave(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(employeeDetLeaves, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(CutiDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(CutiDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CutiDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(CutiDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetLeave> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<EmployeeDetLeave> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_leave_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position + 1;
            holder.textNo.setText("" + nomor);
            holder.textTglCuti.setText(mValues.get(position).getDate_leave());
            holder.textTglPengajuan.setText(mValues.get(position).getProposed_date());
            holder.textTglKadarluarsa.setText(mValues.get(position).getDate_extended());
            holder.textStatus.setText(mValues.get(position).getStatus());
            holder.textKategoriCuti.setText(mValues.get(position).getLeave_category_name());

            if (Integer.valueOf(mValues.get(position).getIs_approved()) == 1)
                holder.textDisetujui.setText("Ya");
            else holder.textDisetujui.setText("Belum Disetujui");

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
            public final TextView textNo;
            public final TextView textTglCuti;
            public final TextView textTglPengajuan;
            public final TextView textTglKadarluarsa;
            public final TextView textStatus;
            public final TextView textKategoriCuti;
            public final TextView textDisetujui;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textTglCuti = (TextView) itemView.findViewById(R.id.textTglCuti);
                textTglPengajuan = (TextView) itemView.findViewById(R.id.textTglPengajuan);
                textTglKadarluarsa = (TextView) itemView.findViewById(R.id.textTglKadarluarsa);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);
                textKategoriCuti = (TextView) itemView.findViewById(R.id.textKategoriCuti);
                textDisetujui = (TextView) itemView.findViewById(R.id.textDisetujui);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}