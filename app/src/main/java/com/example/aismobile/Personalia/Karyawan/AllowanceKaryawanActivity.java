package com.example.aismobile.Personalia.Karyawan;

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
import com.example.aismobile.Data.Personalia.Employee.Employee;
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetPotongan;
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetTunjangan;
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

public class AllowanceKaryawanActivity extends AppCompatActivity {

    private Employee employee;
    private ProgressDialog progressDialog;
    private Context context;
    private RecyclerView recyclerViewEdu;
    private MyEduRecyclerViewAdapter adapterEdu;
    private RecyclerView.LayoutManager recylerViewLayoutManagerEdu;
    private List<EmployeeDetTunjangan> employeeDetEducations;
    private RecyclerView recyclerViewHis;
    private MyHisRecyclerViewAdapter adapterHis;
    private RecyclerView.LayoutManager recylerViewLayoutManagerHis;
    private List<EmployeeDetPotongan> employeeDetHistorys;
    private NumberFormat formatter;

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
    private TextView textTotalTunjangan;
    private TextView textTotalPotongan;

    private double totalTunjangan;
    private double totalPotongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowance_karyawan);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetEducations = new ArrayList<>();
        employeeDetHistorys = new ArrayList<>();

        recyclerViewEdu = (RecyclerView) findViewById(R.id.recyclerViewTun);
        recylerViewLayoutManagerEdu = new LinearLayoutManager(context);
        recyclerViewEdu.setLayoutManager(recylerViewLayoutManagerEdu);

        recyclerViewHis = (RecyclerView) findViewById(R.id.recyclerViewPot);
        recylerViewLayoutManagerHis = new LinearLayoutManager(context);
        recyclerViewHis.setLayoutManager(recylerViewLayoutManagerHis);

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
        textTotalTunjangan = (TextView) findViewById(R.id.textTotalTunjangan);
        textTotalPotongan = (TextView) findViewById(R.id.textTotalPotongan);

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
                Intent intent = new Intent(AllowanceKaryawanActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllowanceKaryawanActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadDetailEdu();
        loadDetailHis();
    }

    public void loadDetailEdu(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_TUNJANGAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        totalTunjangan = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetEducations.add(new EmployeeDetTunjangan(jsonArray.getJSONObject(i)));
                            totalTunjangan += jsonArray.getJSONObject(i).getDouble("value");
                        }
                        textTotalTunjangan.setText("Total Tunjangan = Rp. " + formatter.format(totalTunjangan));
                        adapterEdu = new MyEduRecyclerViewAdapter(employeeDetEducations, context);
                        recyclerViewEdu.setAdapter(adapterEdu);
                    } else {
                        Toast.makeText(AllowanceKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AllowanceKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AllowanceKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(AllowanceKaryawanActivity.this).add(request);
    }

    private class MyEduRecyclerViewAdapter extends RecyclerView.Adapter<MyEduRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetTunjangan> mValues;
        private Context context;

        private MyEduRecyclerViewAdapter(List<EmployeeDetTunjangan> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_allowance_karyawan_tun, parent, false);
            return new MyEduRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyEduRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textNama.setText(mValues.get(position).getEmployee_grade_allowance_name());
            holder.textNilai.setText(mValues.get(position).getValue());
            holder.textHitungThr.setText(mValues.get(position).getCount_as_religious_holiday_allowance());
            holder.textAktif.setText(mValues.get(position).getIs_active());

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
            public final TextView textNilai;
            public final TextView textHitungThr;
            public final TextView textAktif;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textNama = (TextView) itemView.findViewById(R.id.textNama);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textHitungThr = (TextView) itemView.findViewById(R.id.textHitungThr);
                textAktif = (TextView) itemView.findViewById(R.id.textAktif);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }

    public void loadDetailHis(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_POTONGAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        totalPotongan = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetHistorys.add(new EmployeeDetPotongan(jsonArray.getJSONObject(i)));
                            totalPotongan += jsonArray.getJSONObject(i).getDouble("value");
                        }
                        textTotalPotongan.setText("Total Potongan = Rp. " + formatter.format(totalPotongan));
                        adapterHis = new MyHisRecyclerViewAdapter(employeeDetHistorys, context);
                        recyclerViewHis.setAdapter(adapterHis);
                    } else {
                        Toast.makeText(AllowanceKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AllowanceKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AllowanceKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(AllowanceKaryawanActivity.this).add(request);
    }

    private class MyHisRecyclerViewAdapter extends RecyclerView.Adapter<MyHisRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetPotongan> mValues;
        private Context context;

        private MyHisRecyclerViewAdapter(List<EmployeeDetPotongan> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_allowance_karyawan_pot, parent, false);
            return new MyHisRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyHisRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textPotongan.setText(mValues.get(position).getDeduction_name());
            holder.textNilai.setText(mValues.get(position).getValue());
            holder.textAktif.setText(mValues.get(position).getIs_active());

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
            public final TextView textPotongan;
            public final TextView textNilai;
            public final TextView textAktif;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textPotongan = (TextView) itemView.findViewById(R.id.textPotongan);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textAktif = (TextView) itemView.findViewById(R.id.textAktif);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}