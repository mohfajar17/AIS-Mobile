package com.asukacorp.aismobile.Safety.GenbaSafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Safety.GenbaSafety;
import com.asukacorp.aismobile.Data.Safety.GenbaSafetyDetail;
import com.asukacorp.aismobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailGenbaSafetyActivity extends AppCompatActivity {

    private Dialog myDialog;
    private ViewGroup.LayoutParams params;
    private GenbaSafety genbaSafety;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<GenbaSafetyDetail> genbaSafetyDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private ScrollView layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textGenbaDate;
    private TextView textTime;
    private TextView textNamaPembuat;
    private TextView textWorkbase;
    private TextView textChronology;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private ImageView downloadAtachment1;
    private ImageView downloadAtachment2;
    private ImageView downloadAtachment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_genba_safety);

        Bundle bundle = getIntent().getExtras();
        genbaSafety = bundle.getParcelable("detail");
        myDialog = new Dialog(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        genbaSafetyDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textGenbaDate = (TextView) findViewById(R.id.textGenbaDate);
        textTime = (TextView) findViewById(R.id.textTime);
        textNamaPembuat = (TextView) findViewById(R.id.textNamaPembuat);
        textWorkbase = (TextView) findViewById(R.id.textWorkbase);
        textChronology = (TextView) findViewById(R.id.textChronology);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);

        textGenbaDate.setText(genbaSafety.getGenba_date());
        textTime.setText(genbaSafety.getGenba_time());
        textNamaPembuat.setText(genbaSafety.getCreated_by());
        textWorkbase.setText(genbaSafety.getCompany_workbase_id());
        textChronology.setText(genbaSafety.getNotes());
        textCreatedBy.setText(genbaSafety.getCreated_by());
        textCreatedDate.setText(genbaSafety.getCreated_date());
        textModifiedBy.setText(genbaSafety.getModified_by());
        textModifiedDate.setText(genbaSafety.getModified_date());
        downloadAtachment1 = (ImageView) findViewById(R.id.downloadAtachment1);
        downloadAtachment2 = (ImageView) findViewById(R.id.downloadAtachment2);
        downloadAtachment3 = (ImageView) findViewById(R.id.downloadAtachment3);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (ScrollView) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Genba Safety #" + genbaSafety.getGenba_safety_number());
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

        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo1()).into(downloadAtachment1);
        if (downloadAtachment1.getDrawable() == null)
            downloadAtachment1.setImageResource(R.drawable.no_image);
        downloadAtachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo1());
            }
        });
        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo2()).into(downloadAtachment2);
        if (downloadAtachment2.getDrawable() == null)
            downloadAtachment2.setImageResource(R.drawable.no_image);
        downloadAtachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo2());
            }
        });
        Picasso.get().load("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo3()).into(downloadAtachment3);
        if (downloadAtachment3.getDrawable() == null)
            downloadAtachment3.setImageResource(R.drawable.no_image);
        downloadAtachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("https://ais.asukaindonesia.co.id/protected/attachments/genbaSafety/"+genbaSafety.getGenba_photo3());
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_GENBA_SAFETY_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            genbaSafetyDetails.add(new GenbaSafetyDetail(jsonArray.getJSONObject(i)));
                        }

                        adapter = new MyRecyclerViewAdapter(genbaSafetyDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailGenbaSafetyActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailGenbaSafetyActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailGenbaSafetyActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("gs_id", "" + genbaSafety.getGenba_safety_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailGenbaSafetyActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<GenbaSafetyDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<GenbaSafetyDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_genba_safety_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textKaryawan.setText(mValues.get(position).getEmployee_id());
            holder.textKaryawanLuar.setText(mValues.get(position).getEmployee_name1());
            holder.textPosisi.setText(mValues.get(position).getDepartemen());
            holder.textDescription.setText(mValues.get(position).getNotes());

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
            public final TextView textKaryawan;
            public final TextView textKaryawanLuar;
            public final TextView textPosisi;
            public final TextView textDescription;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textKaryawan = (TextView) itemView.findViewById(R.id.textKaryawan);
                textKaryawanLuar = (TextView) itemView.findViewById(R.id.textKaryawanLuar);
                textPosisi = (TextView) itemView.findViewById(R.id.textPosisi);
                textDescription = (TextView) itemView.findViewById(R.id.textDescription);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}