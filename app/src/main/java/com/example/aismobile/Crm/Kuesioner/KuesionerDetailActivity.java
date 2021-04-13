package com.example.aismobile.Crm.Kuesioner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.Data.CRM.Kuesioner;
import com.example.aismobile.Data.CRM.KuesionerDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KuesionerDetailActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Kuesioner cashOnDelivery;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<KuesionerDetail> cashOnDeliveryDetails;
    private ProgressDialog progressDialog;

    private ImageView downloadAtachment;
    private ImageView buttonBack;
    private TextView textNumber;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textKuesionerNumber;
    private TextView textContactName;
    private TextView textCompany;
    private TextView textTglKuesioner;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuesioner_detail);

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

        textKuesionerNumber = (TextView) findViewById(R.id.textKuesionerNumber);
        textContactName = (TextView) findViewById(R.id.textContactName);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textTglKuesioner = (TextView) findViewById(R.id.textTglKuesioner);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textKuesionerNumber.setText(cashOnDelivery.getSurvey_number());
        textContactName.setText(cashOnDelivery.getContact_name());
        textCompany.setText(cashOnDelivery.getCompany_id());
        textTglKuesioner.setText(cashOnDelivery.getSurvey_date());
        textCatatan.setText(cashOnDelivery.getNotes());
        textCreatedBy.setText(cashOnDelivery.getCreated_by());
        textCreatedDate.setText(cashOnDelivery.getCreated_date());
        textModifiedBy.setText(cashOnDelivery.getModified_by());
        textModifiedDate.setText(cashOnDelivery.getModified_date());

        downloadAtachment = (ImageView) findViewById(R.id.downloadAtachment);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Kuesioner #" + cashOnDelivery.getSurvey_number());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutDetail.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutDetail.setLayoutParams(params);
                menuDetail.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuDetail.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutCatatan.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutCatatan.setLayoutParams(params);
                menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuCatatan.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        menuHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenLayout();
                params = layoutHistory.getLayoutParams(); params.height = ViewGroup.LayoutParams.MATCH_PARENT; layoutHistory.setLayoutParams(params);
                menuHistory.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                menuHistory.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
        if (!cashOnDelivery.getSurvey_attachment().matches("null")){
            downloadAtachment.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAsukaRed));
            downloadAtachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/survey/"+ cashOnDelivery.getSurvey_attachment());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }

        loadDetail();
    }

    private void hiddenLayout(){
        menuDetail.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuDetail.setTextColor(getResources().getColor(R.color.colorWhite));
        menuCatatan.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuCatatan.setTextColor(getResources().getColor(R.color.colorWhite));
        menuHistory.setBackgroundColor(getResources().getColor(R.color.colorAsukaRed));
        menuHistory.setTextColor(getResources().getColor(R.color.colorWhite));

        params = layoutDetail.getLayoutParams(); params.height = 0; layoutDetail.setLayoutParams(params);
        params = layoutCatatan.getLayoutParams(); params.height = 0; layoutCatatan.setLayoutParams(params);
        params = layoutHistory.getLayoutParams(); params.height = 0; layoutHistory.setLayoutParams(params);
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_KUESIONER_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            cashOnDeliveryDetails.add(new KuesionerDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new MyRecyclerViewAdapter(cashOnDeliveryDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(KuesionerDetailActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(KuesionerDetailActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(KuesionerDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("svId", "" + cashOnDelivery.getSurvey_id());
                return param;
            }
        };
        Volley.newRequestQueue(KuesionerDetailActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<KuesionerDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<KuesionerDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_kuesioner_detail_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textQuestion.setText(mValues.get(position).getQuestion());
            holder.textPoint.setText(mValues.get(position).getValue());
            holder.textComment.setText(mValues.get(position).getComment());

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
            public final TextView textQuestion;
            public final TextView textPoint;
            public final TextView textComment;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textQuestion = (TextView) itemView.findViewById(R.id.textQuestion);
                textPoint = (TextView) itemView.findViewById(R.id.textPoint);
                textComment = (TextView) itemView.findViewById(R.id.textComment);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}