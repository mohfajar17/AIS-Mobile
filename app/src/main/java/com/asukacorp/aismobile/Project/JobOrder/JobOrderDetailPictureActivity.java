package com.asukacorp.aismobile.Project.JobOrder;

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
import com.asukacorp.aismobile.Data.Marketing.JoPicture;
import com.asukacorp.aismobile.Data.Project.JobOrder;
import com.asukacorp.aismobile.Marketing.JoPictures.AddPicturesActivity;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JobOrderDetailPictureActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<JoPicture> joPictures;
    private ProgressDialog progressDialog;
    private Dialog myDialog;
    private FloatingActionButton fabAdd;
    private SharedPrefManager sharedPrefManager;
    private int accessCreatePic = 0;

    private TextView menuJoDetail;
    private TextView menuJoMr;
    private TextView menuJoPr;
    private TextView menuJoTr;
    private TextView menuJoMp;
    private TextView menuJoCod;
    private TextView menuJoWo;
    private TextView menuJoPb;
    private TextView menuJoCpr;
    private TextView menuJoExpenses;
    private TextView menuJoInvoice;
    private TextView menuJoMatRet;
    private TextView menuJoDoc;
    private TextView menuJoNotes;
    private TextView menuJoHistory;

    private TextView jodJobOrder;
    private TextView jodDepartemen;
    private TextView jodKeterangan;
    private TextView jodDay;

    private JobOrder jobOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_detail_picture);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        myDialog = new Dialog(this);
        context = getApplicationContext();
        joPictures = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerJobOrder);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        menuJoDetail = (TextView) findViewById(R.id.menuJoDetail);
        menuJoMr = (TextView) findViewById(R.id.menuJoMr);
        menuJoPr = (TextView) findViewById(R.id.menuJoPr);
        menuJoTr = (TextView) findViewById(R.id.menuJoTr);
        menuJoMp = (TextView) findViewById(R.id.menuJoMp);
        menuJoCod = (TextView) findViewById(R.id.menuJoCod);
        menuJoWo = (TextView) findViewById(R.id.menuJoWo);
        menuJoPb = (TextView) findViewById(R.id.menuJoPb);
        menuJoCpr = (TextView) findViewById(R.id.menuJoCpr);
        menuJoExpenses = (TextView) findViewById(R.id.menuJoExpenses);
        menuJoInvoice = (TextView) findViewById(R.id.menuJoInvoice);
        menuJoMatRet = (TextView) findViewById(R.id.menuJoMatRet);
        menuJoDoc = (TextView) findViewById(R.id.menuJoDoc);
        menuJoNotes = (TextView) findViewById(R.id.menuJoNotes);
        menuJoHistory = (TextView) findViewById(R.id.menuJoHistory);

        jodJobOrder = (TextView) findViewById(R.id.jodJobOrder);
        jodDepartemen = (TextView) findViewById(R.id.jodDepartemen);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        jodDay = (TextView) findViewById(R.id.jodDay);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessCreatePic > 0){
                    Intent intent = new Intent(JobOrderDetailPictureActivity.this, AddPicturesActivity.class);
                    startActivityForResult(intent,1);
                } else Toast.makeText(JobOrderDetailPictureActivity.this, "You don't have access!", Toast.LENGTH_LONG).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        jobOrder = bundle.getParcelable("detailJO");

        jodJobOrder.setText(jobOrder.getJob_order_number());
        jodDepartemen.setText("Departemen : " + jobOrder.getDepartment_id());
        jodKeterangan.setText("Keterangan : " + jobOrder.getJob_order_description());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDateValue = Calendar.getInstance().getTime();
            Date endDateValue = sdf.parse(jobOrder.getEnd_date());
            long diff = endDateValue.getTime() - startDateValue.getTime();
            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<0)
                jodDay.setText("0 Days Remaining");
            else jodDay.setText(""+ (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1) +" Days Remaining");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        menuJoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailMrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailPrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailTrActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailMpActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailCodActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoWo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailWoActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailPbActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailCprActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailExpensesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailInvoiceActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoMatRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailMatRetActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailNotesActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });
        menuJoHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobOrderDetailPictureActivity.this, JobOrderDetailHistoryActivity.class);
                intent.putExtra("detailJO", jobOrder);
                startActivityForResult(intent,1);
                finish();
            }
        });

        loadJobOrderDetail();
    }

    private void loadJobOrderDetail() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DETAIL_JO_PICTURE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length()>0){
                            for(int i=0;i<jsonArray.length();i++){
                                joPictures.add(new JoPicture(jsonArray.getJSONObject(i)));
                            }
                            adapter = new MyRecyclerViewAdapter(joPictures, context);
                            recyclerView.setAdapter(adapter);
                        } else Toast.makeText(JobOrderDetailPictureActivity.this, "No pictures data in this job order", Toast.LENGTH_LONG).show();

                        accessCreatePic = jsonObject.getInt("access");
                    } else {
                        Toast.makeText(JobOrderDetailPictureActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(JobOrderDetailPictureActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(JobOrderDetailPictureActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + jobOrder.getJob_order_id());
                param.put("userId", "" + sharedPrefManager.getUserId());
                return param;
            }
        };
        Volley.newRequestQueue(JobOrderDetailPictureActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<JoPicture> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<JoPicture> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_job_order_detail_picture_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {

            Picasso.get().load(Config.DATA_URL_JO_PICTURE+mValues.get(position).getFile_name()).into(holder.jopImageFile);
            holder.jopTextFile.setText(mValues.get(position).getFile_name());
            holder.jopTextDesc.setText(mValues.get(position).getDescription());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowPopup(mValues.get(position).getFile_name());
                }
            });

            if (position%2==0)
                holder.layoutJoPicture.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            else holder.layoutJoPicture.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView jopImageFile;
            public final TextView jopTextFile;
            public final TextView jopTextDesc;

            public final LinearLayout layoutJoPicture;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                jopImageFile = (ImageView) itemView.findViewById(R.id.jopImageFile);
                jopTextFile = (TextView) itemView.findViewById(R.id.jopTextFile);
                jopTextDesc = (TextView) itemView.findViewById(R.id.jopTextDesc);

                layoutJoPicture = (LinearLayout) itemView.findViewById(R.id.layoutJoPicture);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode == JobOrderDetailPictureActivity.RESULT_OK ){
            loadJobOrderDetail();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ShowPopup(String url) {
        ImageView imageNo;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageNo = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(Config.DATA_URL_JO_PICTURE+url).into(imageNo);
        if (imageNo.getDrawable() == null)
            imageNo.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}