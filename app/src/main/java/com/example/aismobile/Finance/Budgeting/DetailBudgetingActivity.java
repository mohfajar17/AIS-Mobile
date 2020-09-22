package com.example.aismobile.Finance.Budgeting;

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
import com.example.aismobile.Data.FinanceAccounting.Budgeting;
import com.example.aismobile.Data.FinanceAccounting.BudgetingDetail;
import com.example.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailBudgetingActivity extends AppCompatActivity {

    private ViewGroup.LayoutParams params;
    private Budgeting budgeting;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<BudgetingDetail> budgetingDetails;
    private ProgressDialog progressDialog;

    private TextView textNumber;
    private ImageView buttonBack;
    private TextView menuDetail;
    private TextView menuCatatan;
    private TextView menuHistory;
    private ScrollView layoutDetail;
    private LinearLayout layoutCatatan;
    private LinearLayout layoutHistory;

    private TextView textBudgetNumber;
    private TextView textStartDate;
    private TextView textCheckedBy;
    private TextView textCheckedDate;
    private TextView textApproval1;
    private TextView textApproval1Date;
    private TextView textComment1;
    private TextView textApproval2;
    private TextView textApproval2Date;
    private TextView textComment2;
    private TextView textApproval3;
    private TextView textApproval3Date;
    private TextView textComment3;
    private TextView textCatatan;
    private TextView textCreatedBy;
    private TextView textCreatedDate;
    private TextView textModifiedBy;
    private TextView textModifiedDate;
    private TextView textGrandTotal;

    private double toDouble;
    private double grandTotal;
    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_budgeting);

        Bundle bundle = getIntent().getExtras();
        budgeting = bundle.getParcelable("detail");

        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        budgetingDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textBudgetNumber = (TextView) findViewById(R.id.textBudgetNumber);
        textStartDate = (TextView) findViewById(R.id.textStartDate);
        textCheckedBy = (TextView) findViewById(R.id.textCheckedBy);
        textCheckedDate = (TextView) findViewById(R.id.textCheckedDate);
        textApproval1 = (TextView) findViewById(R.id.textApproval1);
        textApproval1Date = (TextView) findViewById(R.id.textApproval1Date);
        textComment1 = (TextView) findViewById(R.id.textComment1);
        textApproval2 = (TextView) findViewById(R.id.textApproval2);
        textApproval2Date = (TextView) findViewById(R.id.textApproval2Date);
        textComment2 = (TextView) findViewById(R.id.textComment2);
        textApproval3 = (TextView) findViewById(R.id.textApproval3);
        textApproval3Date = (TextView) findViewById(R.id.textApproval3Date);
        textComment3 = (TextView) findViewById(R.id.textComment3);
        textCatatan = (TextView) findViewById(R.id.textCatatan);
        textCreatedBy = (TextView) findViewById(R.id.textCreatedBy);
        textCreatedDate = (TextView) findViewById(R.id.textCreatedDate);
        textModifiedBy = (TextView) findViewById(R.id.textModifiedBy);
        textModifiedDate = (TextView) findViewById(R.id.textModifiedDate);
        textGrandTotal = (TextView) findViewById(R.id.textGrandTotal);

        textBudgetNumber.setText(budgeting.getBudget_number());
        textStartDate.setText(budgeting.getStart_date());
        textCheckedBy.setText(budgeting.getChecked_by());
        textCheckedDate.setText(budgeting.getChecked_date());
        textApproval1.setText(budgeting.getApproval1());
        textApproval1Date.setText(budgeting.getApproval_date1());
        textComment1.setText(budgeting.getApproval_comment1());
        textApproval2.setText(budgeting.getApproval2());
        textApproval2Date.setText(budgeting.getApproval_date2());
        textComment2.setText(budgeting.getApproval_comment2());
        textApproval3.setText(budgeting.getApproval3());
        textApproval3Date.setText(budgeting.getApproval_date3());
        textComment3.setText(budgeting.getApproval_comment3());
        textCatatan.setText(budgeting.getNotes());
        textCreatedBy.setText(budgeting.getCreated_by());
        textCreatedDate.setText(budgeting.getCreated_date());
        textModifiedBy.setText(budgeting.getModified_by());
        textModifiedDate.setText(budgeting.getModified_date());

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        menuDetail = (TextView) findViewById(R.id.menuDetail);
        menuCatatan = (TextView) findViewById(R.id.menuCatatan);
        menuHistory = (TextView) findViewById(R.id.menuHistory);
        layoutDetail = (ScrollView) findViewById(R.id.layoutDetail);
        layoutCatatan = (LinearLayout) findViewById(R.id.layoutCatatan);
        layoutHistory = (LinearLayout) findViewById(R.id.layoutHistory);

        textNumber.setText("Budgeting #" + budgeting.getBudget_number());
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

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_BUDGETING_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        grandTotal = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            budgetingDetails.add(new BudgetingDetail(jsonArray.getJSONObject(i)));
                            grandTotal += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        textGrandTotal.setText("Rp. " + formatter.format((long) grandTotal));
                        adapter = new MyRecyclerViewAdapter(budgetingDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DetailBudgetingActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailBudgetingActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailBudgetingActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("budgetId", "" + budgeting.getBudget_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailBudgetingActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<BudgetingDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<BudgetingDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_budgeting_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            int nomor = position+1;
            holder.textNo.setText("" + nomor);
            holder.textKeterangan.setText(mValues.get(position).getDescription());
            holder.textDepartment.setText(mValues.get(position).getDepartment_id());
            holder.textProjectLocation.setText(mValues.get(position).getCompany_workbase_id());
            holder.textToDepartment.setText(mValues.get(position).getTo_department());
            holder.textApproval1.setText(mValues.get(position).getBudget_app1());
            holder.textApproval2.setText(mValues.get(position).getBudget_app2());
            holder.textApproval3.setText(mValues.get(position).getBudget_app3());

            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. " + formatter.format((long) toDouble));

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
            public final TextView textKeterangan;
            public final TextView textDepartment;
            public final TextView textProjectLocation;
            public final TextView textToDepartment;
            public final TextView textNilai;
            public final TextView textApproval1;
            public final TextView textApproval2;
            public final TextView textApproval3;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textNo = (TextView) itemView.findViewById(R.id.textNo);
                textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
                textDepartment = (TextView) itemView.findViewById(R.id.textDepartment);
                textProjectLocation = (TextView) itemView.findViewById(R.id.textProjectLocation);
                textToDepartment = (TextView) itemView.findViewById(R.id.textToDepartment);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textApproval1 = (TextView) itemView.findViewById(R.id.textApproval1);
                textApproval2 = (TextView) itemView.findViewById(R.id.textApproval2);
                textApproval3 = (TextView) itemView.findViewById(R.id.textApproval3);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}