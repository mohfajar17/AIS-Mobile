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
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetTraining;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingKaryawanActivity extends AppCompatActivity {

    private Employee employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeDetTraining> employeeDetAchievements;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_karyawan);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetAchievements = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

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
                Intent intent = new Intent(TrainingKaryawanActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingKaryawanActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadDetail();
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KARYAWAN_TRAINING_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            employeeDetAchievements.add(new EmployeeDetTraining(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(employeeDetAchievements, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(TrainingKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(TrainingKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(TrainingKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(TrainingKaryawanActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeDetTraining> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<EmployeeDetTraining> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_training_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textTglAwal.setText(mValues.get(position).getStart_date());
            holder.textTglAkhir.setText(mValues.get(position).getEnd_date());
            holder.textTrainingName.setText(mValues.get(position).getTraining_name());
            holder.textKeterangan.setText(mValues.get(position).getDescription());
            holder.textPlace.setText(mValues.get(position).getPlace());
            holder.textProvider.setText(mValues.get(position).getProvider());
            holder.textDurationDay.setText(mValues.get(position).getDuration_day());
            holder.textEvaluationDate.setText(mValues.get(position).getEvaluation_date());

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
            public final TextView textTglAwal;
            public final TextView textTglAkhir;
            public final TextView textTrainingName;
            public final TextView textKeterangan;
            public final TextView textPlace;
            public final TextView textProvider;
            public final TextView textDurationDay;
            public final TextView textEvaluationDate;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textTglAwal = (TextView) itemView.findViewById(R.id.textTglAwal);
                textTglAkhir = (TextView) itemView.findViewById(R.id.textTglAkhir);
                textTrainingName = (TextView) itemView.findViewById(R.id.textTrainingName);
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textPlace = (TextView) itemView.findViewById(R.id.textPlace);
                textProvider = (TextView) itemView.findViewById(R.id.textProvider);
                textDurationDay = (TextView) itemView.findViewById(R.id.textDurationDay);
                textEvaluationDate = (TextView) itemView.findViewById(R.id.textEvaluationDate);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}