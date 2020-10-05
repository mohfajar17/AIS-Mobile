package com.example.aismobile.Personalia.Departemen;

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
import com.example.aismobile.Data.Personalia.Department;
import com.example.aismobile.Data.Personalia.DepartmentDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailDepartemenActivity extends AppCompatActivity {

    private Department department;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<DepartmentDetail> budgetingDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textDepartmentName;
    private TextView textCompany;
    private TextView textKode;
    private TextView textKodeItem;
    private TextView textHeadDepartment;
    private TextView textParent;
    private TextView textNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_departemen);

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

        textDepartmentName = (TextView) findViewById(R.id.textDepartmentName);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textKode = (TextView) findViewById(R.id.textKode);
        textKodeItem = (TextView) findViewById(R.id.textKodeItem);
        textHeadDepartment = (TextView) findViewById(R.id.textHeadDepartment);
        textParent = (TextView) findViewById(R.id.textParent);
        textNotes = (TextView) findViewById(R.id.textNotes);

        textDepartmentName.setText(department.getDepartment_name());
        textCompany.setText(department.getCompany_name());
        textKode.setText(department.getDepartment_code());
        textKodeItem.setText(department.getCode_for_item());
        textHeadDepartment.setText(department.getHead_department());
        textParent.setText(department.getAtasan());
        textNotes.setText(department.getDepartment_notes());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);

        textNumber.setText("Departemen " + department.getDepartment_name());
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DEPARTEMEN_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetingDetails.add(new DepartmentDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailDepartemenActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailDepartemenActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailDepartemenActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("departemenId", "" + department.getDepartment_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailDepartemenActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<DepartmentDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<DepartmentDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_departemen_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textBadge.setText(mValues.get(position).getEmployee_number());
            holder.textFullname.setText(mValues.get(position).getFullname());
            holder.textCompanyWorkbase.setText(mValues.get(position).getCompany_workbase_name());
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
            public final TextView textBadge;
            public final TextView textFullname;
            public final TextView textCompanyWorkbase;
            public final TextView textFullAddress;
            public final TextView textReligion;
            public final TextView textActive;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textBadge = (TextView) itemView.findViewById(R.id.textBadge);
                textFullname = (TextView) itemView.findViewById(R.id.textFullname);
                textCompanyWorkbase = (TextView) itemView.findViewById(R.id.textCompanyWorkbase);
                textFullAddress = (TextView) itemView.findViewById(R.id.textFullAddress);
                textReligion = (TextView) itemView.findViewById(R.id.textReligion);
                textActive = (TextView) itemView.findViewById(R.id.textActive);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}