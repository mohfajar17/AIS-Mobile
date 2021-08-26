package com.asukacorp.aismobile.Personalia.Karyawan;

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
import com.asukacorp.aismobile.Data.Personalia.Employee.Employee;
import com.asukacorp.aismobile.Data.Personalia.Employee.EmployeeDetKerja;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KerjaKaryawanActivity extends AppCompatActivity {

    private Employee employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeDetKerja> employeeDetFiles;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textKaryawan;
    private TextView menuDetail;
    private TextView menuKerja;
    private TextView menuFile;
    private TextView menuLeave;
    private TextView menuAllowanceDeduction;
    private TextView menuEducationHistory;
    private TextView menuWorkExperience;
    private TextView menuTraining;
    private TextView menuAchievement;
    private TextView menuHistory;

    private TextView textTanggalAwalKerja;
    private TextView textTanggalDiangkat;
    private TextView textTanggalAkhirKontrak;
    private TextView textTanggalKeluar;
    private TextView textAlasanKeluar;
    private TextView textSalary;
    private TextView textCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerja_karyawan);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetFiles = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textTanggalAwalKerja = (TextView) findViewById(R.id.textTanggalAwalKerja);
        textTanggalDiangkat = (TextView) findViewById(R.id.textTanggalDiangkat);
        textTanggalAkhirKontrak = (TextView) findViewById(R.id.textTanggalAkhirKontrak);
        textTanggalKeluar = (TextView) findViewById(R.id.textTanggalKeluar);
        textAlasanKeluar = (TextView) findViewById(R.id.textAlasanKeluar);
        textSalary = (TextView) findViewById(R.id.textSalary);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        textTanggalAwalKerja.setText(employee.getJoin_date());
        textTanggalDiangkat.setText(employee.getEmployment_date());
        textTanggalAkhirKontrak.setText(employee.getTermination_date());
        textTanggalKeluar.setText(employee.getCome_out_date());
        textAlasanKeluar.setText(employee.getTermination_reason());
        textSalary.setText(employee.getIs_active());
        textCatatan.setText(employee.getNotes());

        textKaryawan = (TextView) findViewById(R.id.textKaryawan);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuKerja = (TextView) findViewById(R.id.menuKerja);
        menuFile = (TextView) findViewById(R.id.menuFile);
        menuLeave = (TextView) findViewById(R.id.menuLeave);
        menuAllowanceDeduction = (TextView) findViewById(R.id.menuAllowanceDeduction);
        menuEducationHistory = (TextView) findViewById(R.id.menuEducationHistory);
        menuWorkExperience = (TextView) findViewById(R.id.menuWorkExperience);
        menuTraining = (TextView) findViewById(R.id.menuTraining);
        menuAchievement = (TextView) findViewById(R.id.menuAchievement);
        menuHistory = (TextView) findViewById(R.id.menuHistory);

        textKaryawan.setText("Karyawan : " + employee.getFullname());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KerjaKaryawanActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_KERJA_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetFiles.add(new EmployeeDetKerja(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(employeeDetFiles, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(KerjaKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(KerjaKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(KerjaKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(KerjaKaryawanActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetKerja> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<EmployeeDetKerja> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_kerja_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textDescription.setText(mValues.get(position).getDescription());
            holder.textEmployeeGrade.setText(mValues.get(position).getEmployee_grade_name());
            holder.textIn.setText(mValues.get(position).getStart_date());
            holder.textOut.setText(mValues.get(position).getEnd_date());

            if (position % 2 == 0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textDescription;
            public final TextView textEmployeeGrade;
            public final TextView textIn;
            public final TextView textOut;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textDescription = (TextView) itemView.findViewById(R.id.textDescription);
                textEmployeeGrade = (TextView) itemView.findViewById(R.id.textEmployeeGrade);
                textIn = (TextView) itemView.findViewById(R.id.textIn);
                textOut = (TextView) itemView.findViewById(R.id.textOut);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}