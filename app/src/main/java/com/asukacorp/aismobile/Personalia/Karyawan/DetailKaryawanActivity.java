package com.asukacorp.aismobile.Personalia.Karyawan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.asukacorp.aismobile.Data.Personalia.Employee.EmployeeDetFamily;
import com.asukacorp.aismobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailKaryawanActivity extends AppCompatActivity {

    private Employee employee;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeDetFamily> employeeDetFamilies;
    private ProgressDialog progressDialog;
    private Dialog myDialog;

    private ImageView buttonBack;
    private ImageView imagePhoto;
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

    private TextView detailNamaLengkap;
    private TextView detailNamaPanggilan;
    private TextView detailJenisKelamin;
    private TextView detailTglLahir;
    private TextView detailAgama;
    private TextView detailAlamat;
    private TextView detailTeleponRumah;
    private TextView detailNoHP;
    private TextView detailEmail;
    private TextView detailNomorRek;
    private TextView detailKTP;
    private TextView detailKTPBerlaku;
    private TextView detailWargaNegara;
    private TextView detailKota;
    private TextView detailPropinsi;
    private TextView detailNegara;
    private TextView detailAsalKotaKTP;
    private TextView detailStatusKawin;
    private TextView detailWorkingStatus;
    private TextView detailSalary;
    private TextView detailBadge;
    private TextView detailIDMesinKaryawan;
    private TextView detailLokasiKerja;
    private TextView detailDepartemen;
    private TextView detailJenjangKaryawan;
    private TextView detailJabatan;
    private TextView detailStatusKerja;
    private TextView detailGajiPokokSekarang;
    private TextView detailBPJSKerja;
    private TextView detailBPJSKesehatan;
    private TextView detailNPWP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");
        myDialog = new Dialog(this);

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
        detailNamaLengkap = (TextView) findViewById(R.id.detailNamaLengkap);
        detailNamaPanggilan = (TextView) findViewById(R.id.detailNamaPanggilan);
        detailJenisKelamin = (TextView) findViewById(R.id.detailJenisKelamin);
        detailTglLahir = (TextView) findViewById(R.id.detailTglLahir);
        detailAgama = (TextView) findViewById(R.id.detailAgama);
        detailAlamat = (TextView) findViewById(R.id.detailAlamat);
        detailTeleponRumah = (TextView) findViewById(R.id.detailTeleponRumah);
        detailNoHP = (TextView) findViewById(R.id.detailNoHP);
        detailEmail = (TextView) findViewById(R.id.detailEmail);
        detailNomorRek = (TextView) findViewById(R.id.detailNomorRek);
        detailKTP = (TextView) findViewById(R.id.detailKTP);
        detailKTPBerlaku = (TextView) findViewById(R.id.detailKTPBerlaku);
        detailWargaNegara = (TextView) findViewById(R.id.detailWargaNegara);
        detailKota = (TextView) findViewById(R.id.detailKota);
        detailPropinsi = (TextView) findViewById(R.id.detailPropinsi);
        detailNegara = (TextView) findViewById(R.id.detailNegara);
        detailAsalKotaKTP = (TextView) findViewById(R.id.detailAsalKotaKTP);
        detailStatusKawin = (TextView) findViewById(R.id.detailStatusKawin);
        detailWorkingStatus = (TextView) findViewById(R.id.detailWorkingStatus);
        detailSalary = (TextView) findViewById(R.id.detailSalary);
        detailBadge = (TextView) findViewById(R.id.detailBadge);
        detailIDMesinKaryawan = (TextView) findViewById(R.id.detailIDMesinKaryawan);
        detailLokasiKerja = (TextView) findViewById(R.id.detailLokasiKerja);
        detailDepartemen = (TextView) findViewById(R.id.detailDepartemen);
        detailJenjangKaryawan = (TextView) findViewById(R.id.detailJenjangKaryawan);
        detailJabatan = (TextView) findViewById(R.id.detailJabatan);
        detailStatusKerja = (TextView) findViewById(R.id.detailStatusKerja);
        detailGajiPokokSekarang = (TextView) findViewById(R.id.detailGajiPokokSekarang);
        detailBPJSKerja = (TextView) findViewById(R.id.detailBPJSKerja);
        detailBPJSKesehatan = (TextView) findViewById(R.id.detailBPJSKesehatan);
        detailNPWP = (TextView) findViewById(R.id.detailNPWP);

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
        detailNamaLengkap.setText(employee.getFullname());
        detailNamaPanggilan.setText(employee.getNickname());
        detailJenisKelamin.setText(employee.getGender());
        detailTglLahir.setText(employee.getPlace_birthday() + ", " + employee.getBirthday());
        detailAgama.setText(employee.getReligion_name());
        detailAlamat.setText(employee.getAddress());
        detailTeleponRumah.setText(employee.getHome_phone());
        detailNoHP.setText(employee.getMobile_phone());
        detailEmail.setText(employee.getEmail1());
        detailNomorRek.setText(employee.getNo_rek());
        detailKTP.setText(employee.getSin_num());
        detailKTPBerlaku.setText(employee.getSin_expiry_date());
        detailWargaNegara.setText(employee.getCitizenship());
        detailKota.setText(employee.getCity());
        detailPropinsi.setText(employee.getState());
        detailNegara.setText(employee.getCountry());
        detailAsalKotaKTP.setText(employee.getOrigin_city_ktp());
        detailStatusKawin.setText(employee.getMarital_status_name());
        detailWorkingStatus.setText(employee.getWorking_status());
        detailSalary.setText(employee.getIs_active());
        detailBadge.setText(employee.getEmployee_number());
        detailIDMesinKaryawan.setText(employee.getEmployee_check_id());
        detailLokasiKerja.setText(employee.getCompany_workbase_name());
        detailDepartemen.setText(employee.getDepartment_name());
        detailJenjangKaryawan.setText(employee.getEmployee_grade_name());
        detailJabatan.setText(employee.getJobtitle_name());
        detailStatusKerja.setText(employee.getEmployee_status());
        detailGajiPokokSekarang.setText(employee.getCurrent_basic_salary());
        detailBPJSKerja.setText(employee.getSocial_security_number());
        detailBPJSKesehatan.setText(employee.getBpjs_health_number());
        detailNPWP.setText(employee.getNpwp());

        imagePhoto = (ImageView) findViewById(R.id.imagePhoto);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, DetailKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, KerjaKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, FileKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, LeaveKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAllowanceDeduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, AllowanceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuEducationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, EducationKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuWorkExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, ExperienceKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, TrainingKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, AchievementKaryawanActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailKaryawanActivity.this, HistoryActivity.class);
                intent.putExtra("detail", employee);
                startActivityForResult(intent,1);
                finish();
            }
        });

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/"+employee.getEmployee_file_name()).into(imagePhoto);
        if (imagePhoto.getDrawable() == null)
            imagePhoto.setImageResource(R.drawable.no_image);
        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/"+employee.getEmployee_file_name());
            }
        });

        loadDetail();
    }

    public void showPopup(String url) {
        ImageView imageView;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageView = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(url).into(imageView);
        if (imageView.getDrawable() == null)
            imageView.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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
                        Toast.makeText(DetailKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(DetailKaryawanActivity.this).add(request);
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