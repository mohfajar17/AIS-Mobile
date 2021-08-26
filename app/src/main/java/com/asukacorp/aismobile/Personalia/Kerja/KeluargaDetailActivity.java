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
import com.asukacorp.aismobile.Data.Personalia.Employee.EmployeeDetFamily;
import com.asukacorp.aismobile.Data.Personalia.Fullname;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeluargaDetailActivity extends AppCompatActivity {

    private Fullname employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeDetFamily> employeeDetFamilies;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textKaryawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluarga_detail);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetFamilies = new ArrayList<>();

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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetFamilies.add(new EmployeeDetFamily(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(employeeDetFamilies, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(KeluargaDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(KeluargaDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(KeluargaDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(KeluargaDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetFamily> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<EmployeeDetFamily> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textNama.setText(mValues.get(position).getFamily_name());
            holder.textTanggalLahir.setText(mValues.get(position).getBirthday());
            holder.textHubunganKeluarga.setText(mValues.get(position).getFamily_type_name());
            holder.textJenisKelamin.setText(mValues.get(position).getGender());
            holder.textPendidikanTerakhir.setText(mValues.get(position).getLast_education());
            holder.textPekerjaan.setText(mValues.get(position).getJob());

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
            public final TextView textNama;
            public final TextView textTanggalLahir;
            public final TextView textHubunganKeluarga;
            public final TextView textJenisKelamin;
            public final TextView textPendidikanTerakhir;
            public final TextView textPekerjaan;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textNama = (TextView) itemView.findViewById(R.id.textNama);
                textTanggalLahir = (TextView) itemView.findViewById(R.id.textTanggalLahir);
                textHubunganKeluarga = (TextView) itemView.findViewById(R.id.textHubunganKeluarga);
                textJenisKelamin = (TextView) itemView.findViewById(R.id.textJenisKelamin);
                textPendidikanTerakhir = (TextView) itemView.findViewById(R.id.textPendidikanTerakhir);
                textPekerjaan = (TextView) itemView.findViewById(R.id.textPekerjaan);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}