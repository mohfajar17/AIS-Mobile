package com.example.aismobile.Personalia.Kerja;

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
import com.example.aismobile.Data.Personalia.Employee.EmployeeDetEducation;
import com.example.aismobile.Data.Personalia.Fullname;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendidikanDetailActivity extends AppCompatActivity {

    private Fullname employee;
    private ProgressDialog progressDialog;
    private Context context;
    private RecyclerView recyclerViewEdu;
    private MyEduRecyclerViewAdapter adapterEdu;
    private RecyclerView.LayoutManager recylerViewLayoutManagerEdu;
    private List<EmployeeDetEducation> employeeDetEducations;

    private ImageView buttonBack;
    private TextView textKaryawan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendidikan_detail);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        employeeDetEducations = new ArrayList<>();

        recyclerViewEdu = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManagerEdu = new LinearLayoutManager(context);
        recyclerViewEdu.setLayoutManager(recylerViewLayoutManagerEdu);

        textKaryawan = (TextView) findViewById(R.id.textKaryawan);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textKaryawan.setText("#" + employee.getFullname());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadDetailEdu();
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
                        Toast.makeText(PendidikanDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PendidikanDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PendidikanDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", "" + employee.getEmployee_id());
                return param;
            }
        };
        Volley.newRequestQueue(PendidikanDetailActivity.this).add(request);
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
}