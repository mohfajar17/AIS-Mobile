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
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetEducation;
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetHistory;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EducationKaryawanActivity extends AppCompatActivity {

    private Employee employee;
    private ProgressDialog progressDialog;
    private Context context;
    private RecyclerView recyclerViewEdu;
    private MyEduRecyclerViewAdapter adapterEdu;
    private RecyclerView.LayoutManager recylerViewLayoutManagerEdu;
    private List<EmployeeDetEducation> employeeDetEducations;
    private RecyclerView recyclerViewHis;
    private MyHisRecyclerViewAdapter adapterHis;
    private RecyclerView.LayoutManager recylerViewLayoutManagerHis;
    private List<EmployeeDetHistory> employeeDetHistorys;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_karyawan);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetEducations = new ArrayList<>();
        employeeDetHistorys = new ArrayList<>();

        recyclerViewEdu = (RecyclerView) findViewById(R.id.recyclerViewPendidikan);
        recylerViewLayoutManagerEdu = new LinearLayoutManager(context);
        recyclerViewEdu.setLayoutManager(recylerViewLayoutManagerEdu);

        recyclerViewHis = (RecyclerView) findViewById(R.id.recyclerViewHistory);
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
                Intent intent = new Intent(EducationKaryawanActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationKaryawanActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadDetailEdu();
        loadDetailHis();
    }

    public void loadDetailEdu(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_EDUCATION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetEducations.add(new EmployeeDetEducation(jsonArray.getJSONObject(i)));
                        }
                        adapterEdu = new MyEduRecyclerViewAdapter(employeeDetEducations, context);
                        recyclerViewEdu.setAdapter(adapterEdu);
                    } else {
                        Toast.makeText(EducationKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(EducationKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EducationKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(EducationKaryawanActivity.this).add(request);
    }

    private class MyEduRecyclerViewAdapter extends RecyclerView.Adapter<MyEduRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetEducation> mValues;
        private Context context;

        private MyEduRecyclerViewAdapter(List<EmployeeDetEducation> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_education_karyawan_edu, parent, false);
            return new MyEduRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyEduRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textPendidikan.setText(mValues.get(position).getEducation_name());
            holder.textNamaSekolah.setText(mValues.get(position).getSchool_name());
            holder.textJurusan.setText(mValues.get(position).getMajor());
            holder.textMasuk.setText(mValues.get(position).getEducation_start());
            holder.textLulus.setText(mValues.get(position).getEducation_end());

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
            public final TextView textPendidikan;
            public final TextView textNamaSekolah;
            public final TextView textJurusan;
            public final TextView textMasuk;
            public final TextView textLulus;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textPendidikan = (TextView) itemView.findViewById(R.id.textPendidikan);
                textNamaSekolah = (TextView) itemView.findViewById(R.id.textNamaSekolah);
                textJurusan = (TextView) itemView.findViewById(R.id.textJurusan);
                textMasuk = (TextView) itemView.findViewById(R.id.textMasuk);
                textLulus = (TextView) itemView.findViewById(R.id.textLulus);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }

    public void loadDetailHis(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_HISTORY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetHistorys.add(new EmployeeDetHistory(jsonArray.getJSONObject(i)));
                        }
                        adapterHis = new MyHisRecyclerViewAdapter(employeeDetHistorys, context);
                        recyclerViewHis.setAdapter(adapterHis);
                    } else {
                        Toast.makeText(EducationKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(EducationKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EducationKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(EducationKaryawanActivity.this).add(request);
    }

    private class MyHisRecyclerViewAdapter extends RecyclerView.Adapter<MyHisRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetHistory> mValues;
        private Context context;

        private MyHisRecyclerViewAdapter(List<EmployeeDetHistory> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_education_karyawan_his, parent, false);
            return new MyHisRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyHisRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textTgl.setText(mValues.get(position).getHistory_date());
            holder.textStatus.setText(mValues.get(position).getStatus_history());
            holder.textJenjangKaryawan.setText(mValues.get(position).getEmployee_grade_name());
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
            public final TextView textNo;
            public final TextView textTgl;
            public final TextView textStatus;
            public final TextView textJenjangKaryawan;
            public final TextView textCatatan;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textTgl = (TextView) itemView.findViewById(R.id.textTgl);
                textStatus = (TextView) itemView.findViewById(R.id.textStatus);
                textJenjangKaryawan = (TextView) itemView.findViewById(R.id.textJenjangKaryawan);
                textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}