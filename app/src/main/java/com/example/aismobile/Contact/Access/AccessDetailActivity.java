package com.example.aismobile.Contact.Access;

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
import com.example.aismobile.Data.Contact.AccessRequest;
import com.example.aismobile.Data.Contact.AccessRequestDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessDetailActivity extends AppCompatActivity {

    private AccessRequest cashOnDelivery;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<AccessRequestDetail> cashOnDeliveryDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;

    private TextView textArNumber;
    private TextView textEmployee;
    private TextView textRequestDate;
    private TextView textApproval;
    private TextView textApprovalDate;
    private TextView textComment;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_detail);

        Bundle bundle = getIntent().getExtras();
        cashOnDelivery = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        cashOnDeliveryDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textArNumber = (TextView) findViewById(R.id.textArNumber);
        textEmployee = (TextView) findViewById(R.id.textEmployee);
        textRequestDate = (TextView) findViewById(R.id.textRequestDate);
        textApproval = (TextView) findViewById(R.id.textApproval);
        textApprovalDate = (TextView) findViewById(R.id.textApprovalDate);
        textComment = (TextView) findViewById(R.id.textComment);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textArNumber.setText(cashOnDelivery.getAccess_request_number());
        textEmployee.setText(cashOnDelivery.getEmployee_request());
        textRequestDate.setText(cashOnDelivery.getRequest_date());
        textApproval.setText(cashOnDelivery.getApproval1());
        textApprovalDate.setText(cashOnDelivery.getApproval_date());
        textComment.setText(cashOnDelivery.getApproval_comment1());
        textCatatan.setText(cashOnDelivery.getNotes());
        textCreatedBy.setText(cashOnDelivery.getCreated_by());
        textCreatedDate.setText(cashOnDelivery.getCreated_date());
        textModifiedBy.setText(cashOnDelivery.getModified_by());
        textModifiedDate.setText(cashOnDelivery.getModified_date());

        textNumber = (TextView) findViewById(R.id.textNumber);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textNumber.setText(cashOnDelivery.getAccess_request_number());
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_ACCESS_REQUEST_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++)
                            cashOnDeliveryDetails.add(new AccessRequestDetail(jsonArray.getJSONObject(i)));

                        adapter = new MyRecyclerViewAdapter(cashOnDeliveryDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(AccessDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(AccessDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AccessDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("arId", "" + cashOnDelivery.getAccess_request_id());
                return param;
            }
        };
        Volley.newRequestQueue(AccessDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<AccessRequestDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<AccessRequestDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_access_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textNamaAkses.setText(mValues.get(position).getAccess_name());
            holder.textCatatan.setText(mValues.get(position).getNotes());
            holder.textApproval.setText(mValues.get(position).getApproval1());

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
            public final TextView textNamaAkses;
            public final TextView textCatatan;
            public final TextView textApproval;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textNamaAkses = (TextView) itemView.findViewById(R.id.textNamaAkses);
                textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);
                textApproval = (TextView) itemView.findViewById(R.id.textApproval);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}