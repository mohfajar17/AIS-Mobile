package com.example.aismobile.Personalia.JenjangKaryawan;

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
import com.example.aismobile.Config;
import com.example.aismobile.Data.Personalia.EmployeeGrade;
import com.example.aismobile.Data.Personalia.EmployeeGradeDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailJenjangKaryawanActivity extends AppCompatActivity {

    private EmployeeGrade department;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<EmployeeGradeDetail> budgetingDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textNamaJenjang;
    private TextView textKodeLaporan;
    private TextView textJabatan;
    private TextView textPangkat;
    private TextView textGolonganGaji;
    private TextView textOvertimeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jenjang_karyawan);

        Bundle bundle = getIntent().getExtras();
        department = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        budgetingDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textNamaJenjang = (TextView) findViewById(R.id.textNamaJenjang);
        textKodeLaporan = (TextView) findViewById(R.id.textKodeLaporan);
        textJabatan = (TextView) findViewById(R.id.textJabatan);
        textPangkat = (TextView) findViewById(R.id.textPangkat);
        textGolonganGaji = (TextView) findViewById(R.id.textGolonganGaji);
        textOvertimeLimit = (TextView) findViewById(R.id.textOvertimeLimit);

        textNamaJenjang.setText(department.getEmployee_grade_name());
        textKodeLaporan.setText(department.getReport_code());
        textJabatan.setText(department.getJobtitle_name());
        textPangkat.setText(department.getJob_grade_name());
        textGolonganGaji.setText(department.getSalary_grade_name());
        textOvertimeLimit.setText(department.getOvertime_limit());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_GRADE_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetingDetails.add(new EmployeeGradeDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailJenjangKaryawanActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailJenjangKaryawanActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailJenjangKaryawanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jenjangId", "" + department.getEmployee_grade_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailJenjangKaryawanActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<EmployeeGradeDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<EmployeeGradeDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_jenjang_karyawan_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textDepartment.setText(mValues.get(position).getDepartment_name());
            holder.textFullname.setText(mValues.get(position).getFullname());
            holder.textEmploymentDate.setText(mValues.get(position).getEmployment_date());
            holder.textTerminationDate.setText(mValues.get(position).getTermination_date());
            holder.textFullAddress.setText(mValues.get(position).getAddress());
            holder.textReligion.setText(mValues.get(position).getReligion_name());
            holder.textActive.setText(mValues.get(position).getIs_active());

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
            public final TextView textDepartment;
            public final TextView textFullname;
            public final TextView textEmploymentDate;
            public final TextView textTerminationDate;
            public final TextView textFullAddress;
            public final TextView textReligion;
            public final TextView textActive;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textDepartment = (TextView) itemView.findViewById(R.id.textDepartment);
                textFullname = (TextView) itemView.findViewById(R.id.textFullname);
                textEmploymentDate = (TextView) itemView.findViewById(R.id.textEmploymentDate);
                textTerminationDate = (TextView) itemView.findViewById(R.id.textTerminationDate);
                textFullAddress = (TextView) itemView.findViewById(R.id.textFullAddress);
                textReligion = (TextView) itemView.findViewById(R.id.textReligion);
                textActive = (TextView) itemView.findViewById(R.id.textActive);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}