package com.asukacorp.aismobile.Marketing.SalesOrder;

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
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Marketing.SalesOrder;
import com.asukacorp.aismobile.Data.Marketing.SalesOrderDetail;
import com.asukacorp.aismobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailSalesOrderActivity extends AppCompatActivity {

    private SalesOrder salesOrder;
    private Context context;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SalesOrderDetail> joCods;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private TextView textNumber;
    private TextView textSONumber;
    private TextView textSODate;
    private TextView textDueDate;
    private TextView textStatusOrder;
    private TextView textCompany;

    private TextView textMaterialRequests;
    private TextView textProposedBudget;
    private TextView textCashProjectReport;
    private TextView textManPowerTemporary;
    private TextView textManPowerKontrak;
    private TextView textCashOnDeliveries;
    private TextView textWorkOrder;
    private TextView textExpenses;
    private TextView textPengeluaran;
    private TextView textBudgetingAmount;
    private TextView textTotalInvoice;
    private TextView textTotalPayment;
    private TextView textNilai;
    private TextView textLabaRugiBerjalan;
    private TextView textSisaBudget;
    private TextView textLabaRugi;

    private double totalMaterialRequests = 0;
    private double totalProposedBudget = 0;
    private double totalCashProjectReport = 0;
    private double totalManPowerTemporary = 0;
    private double totalManPowerKontrak = 0;
    private double totalCashOnDeliveries = 0;
    private double totalWorkOrder = 0;
    private double totalExpenses = 0;
    private double totalPengeluaran = 0;
    private double totalBudgetingAmount = 0;
    private double totalInvoice = 0;
    private double totalPayment = 0;
    private double totalNilai = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sales_order);

        Bundle bundle = getIntent().getExtras();
        salesOrder = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        joCods = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textNumber = (TextView) findViewById(R.id.textNumber);
        textSONumber = (TextView) findViewById(R.id.textSONumber);
        textSODate = (TextView) findViewById(R.id.textSODate);
        textDueDate = (TextView) findViewById(R.id.textDueDate);
        textStatusOrder = (TextView) findViewById(R.id.textStatusOrder);
        textCompany = (TextView) findViewById(R.id.textCompany);

        textMaterialRequests = (TextView) findViewById(R.id.textMaterialRequests);
        textProposedBudget = (TextView) findViewById(R.id.textProposedBudget);
        textCashProjectReport = (TextView) findViewById(R.id.textCashProjectReport);
        textManPowerTemporary = (TextView) findViewById(R.id.textManPowerTemporary);
        textManPowerKontrak = (TextView) findViewById(R.id.textManPowerKontrak);
        textCashOnDeliveries = (TextView) findViewById(R.id.textCashOnDeliveries);
        textWorkOrder = (TextView) findViewById(R.id.textWorkOrder);
        textExpenses = (TextView) findViewById(R.id.textExpenses);
        textPengeluaran = (TextView) findViewById(R.id.textPengeluaran);
        textBudgetingAmount = (TextView) findViewById(R.id.textBudgetingAmount);
        textTotalInvoice = (TextView) findViewById(R.id.textTotalInvoice);
        textTotalPayment = (TextView) findViewById(R.id.textTotalPayment);
        textNilai = (TextView) findViewById(R.id.textNilai);
        textLabaRugiBerjalan = (TextView) findViewById(R.id.textLabaRugiBerjalan);
        textSisaBudget = (TextView) findViewById(R.id.textSisaBudget);
        textLabaRugi = (TextView) findViewById(R.id.textLabaRugi);

        textNumber.setText("Sales Order #" + salesOrder.getSales_order_number());
        textSONumber.setText("#" + salesOrder.getSales_order_number());
        textSODate.setText(salesOrder.getSales_order_date());
        textDueDate.setText(salesOrder.getDue_date());
        textStatusOrder.setText(salesOrder.getStatus());
        textCompany.setText(salesOrder.getCompany_id());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadJobOrderDetail();
    }

    public void loadJobOrderDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LIST_SALES_ORDER_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            joCods.add(new SalesOrderDetail(jsonArray.getJSONObject(i)));

                            totalMaterialRequests += jsonArray.getJSONObject(i).getDouble("mr")+jsonArray.getJSONObject(i).getDouble("pr");
                            totalProposedBudget += jsonArray.getJSONObject(i).getDouble("pbHalf");
                            totalCashProjectReport += jsonArray.getJSONObject(i).getDouble("cpr");
                            totalManPowerTemporary += jsonArray.getJSONObject(i).getDouble("manPowerTemporary");
                            totalManPowerKontrak += jsonArray.getJSONObject(i).getDouble("manPowerKontrak");
                            totalCashOnDeliveries += jsonArray.getJSONObject(i).getDouble("cod");
                            totalWorkOrder += jsonArray.getJSONObject(i).getDouble("wo");
                            totalExpenses += jsonArray.getJSONObject(i).getDouble("expenses");
                            totalPengeluaran += jsonArray.getJSONObject(i).getDouble("mr")+jsonArray.getJSONObject(i).getDouble("pr")+
                                    jsonArray.getJSONObject(i).getDouble("pbHalf")+jsonArray.getJSONObject(i).getDouble("cpr")+
                                    jsonArray.getJSONObject(i).getDouble("manPowerTemporary")+jsonArray.getJSONObject(i).getDouble("manPowerKontrak")+
                                    jsonArray.getJSONObject(i).getDouble("cod")+jsonArray.getJSONObject(i).getDouble("wo")+
                                    jsonArray.getJSONObject(i).getDouble("expenses");//
                            totalBudgetingAmount += jsonArray.getJSONObject(i).getDouble("budgeting_amount");
                            totalInvoice += jsonArray.getJSONObject(i).getDouble("invoice");
                            totalPayment += jsonArray.getJSONObject(i).getDouble("payment");
                            totalNilai += jsonArray.getJSONObject(i).getDouble("amount");
                        }
                        adapter = new MyRecyclerViewAdapter(joCods, context);
                        recyclerView.setAdapter(adapter);

                        NumberFormat formatter = new DecimalFormat("#,###");
                        textMaterialRequests.setText("Rp. "+ formatter.format((long) totalMaterialRequests));
                        textProposedBudget.setText("Rp. "+ formatter.format((long) totalProposedBudget));
                        textCashProjectReport.setText("Rp. "+ formatter.format((long) totalCashProjectReport));
                        textManPowerTemporary.setText("Rp. "+ formatter.format((long) totalManPowerTemporary));
                        textManPowerKontrak.setText("Rp. "+ formatter.format((long) totalManPowerKontrak));
                        textCashOnDeliveries.setText("Rp. "+ formatter.format((long) totalCashOnDeliveries));
                        textWorkOrder.setText("Rp. "+ formatter.format((long) totalWorkOrder));
                        textExpenses.setText("Rp. "+ formatter.format((long) totalExpenses));
                        textPengeluaran.setText("Rp. "+ formatter.format((long) totalPengeluaran));
                        textBudgetingAmount.setText("Rp. "+ formatter.format((long) totalBudgetingAmount));
                        textTotalInvoice.setText("Rp. "+ formatter.format((long) totalInvoice));
                        textTotalPayment.setText("Rp. "+ formatter.format((long) totalPayment));
                        textNilai.setText("Rp. "+ formatter.format((long) totalNilai));

                        double toDouble = 0;
                        toDouble = totalBudgetingAmount - totalPengeluaran;
                        textSisaBudget.setText("Rp. "+ formatter.format((long) toDouble));
                        toDouble = totalNilai - totalPengeluaran;
                        textLabaRugi.setText("Rp. "+ formatter.format((long) toDouble));
                        toDouble = totalInvoice - totalPengeluaran;
                        textLabaRugiBerjalan.setText("Rp. "+ formatter.format((long) toDouble));
                    } else {
                        Toast.makeText(DetailSalesOrderActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DetailSalesOrderActivity.this, "", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailSalesOrderActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + salesOrder.getSales_order_id());
                return param;
            }
        };
        Volley.newRequestQueue(DetailSalesOrderActivity.this).add(request);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private final List<SalesOrderDetail> mValues;
        private Context context;

        private MyRecyclerViewAdapter(List<SalesOrderDetail> mValues, Context context) {
            this.mValues = mValues;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_sales_order_list, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.textJobOrder.setText(mValues.get(position).getJob_order_number());
            holder.textKeteranganJobOrder.setText(mValues.get(position).getJob_order_description());

            double toDouble;
            NumberFormat formatter = new DecimalFormat("#,###");
            toDouble = Double.valueOf(mValues.get(position).getMr())+Double.valueOf(mValues.get(position).getPr());
            holder.textMaterialRequests.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getPbHalf());
            holder.textProposedBudget.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getCpr());
            holder.textCashProjectReport.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getManPowerTemporary());
            holder.textManPowerTemporary.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getManPowerKontrak());
            holder.textManPowerKontrak.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getCod());
            holder.textCashOnDeliveries.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getWo());
            holder.textWorkOrder.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getExpenses());
            holder.textExpenses.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getMr())+Double.valueOf(mValues.get(position).getPr())+
                    Double.valueOf(mValues.get(position).getPbHalf())+Double.valueOf(mValues.get(position).getCpr())+
                    Double.valueOf(mValues.get(position).getManPowerTemporary())+Double.valueOf(mValues.get(position).getManPowerKontrak())+
                    Double.valueOf(mValues.get(position).getCod())+Double.valueOf(mValues.get(position).getWo())+
                    Double.valueOf(mValues.get(position).getExpenses());//
            holder.textPengeluaran.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getBudgeting_amount());
            holder.textBudgetingAmount.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getInvoice());
            holder.textTotalInvoice.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getPayment());
            holder.textTotalPayment.setText("Rp. "+ formatter.format((long) toDouble));
            toDouble = Double.valueOf(mValues.get(position).getAmount());
            holder.textNilai.setText("Rp. "+ formatter.format((long) toDouble));
            holder.textLabaRugiBerjalan.setText(" %");

            if (position%2==0)
                holder.layout.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            else holder.layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView textJobOrder;
            public final TextView textKeteranganJobOrder;
            public final TextView textMaterialRequests;
            public final TextView textProposedBudget;
            public final TextView textCashProjectReport;
            public final TextView textManPowerTemporary;
            public final TextView textManPowerKontrak;
            public final TextView textCashOnDeliveries;
            public final TextView textWorkOrder;
            public final TextView textExpenses;
            public final TextView textPengeluaran;
            public final TextView textBudgetingAmount;
            public final TextView textTotalInvoice;
            public final TextView textTotalPayment;
            public final TextView textNilai;
            public final TextView textLabaRugiBerjalan;

            public final LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
                textJobOrder = (TextView) itemView.findViewById(R.id.textJobOrder);
                textKeteranganJobOrder = (TextView) itemView.findViewById(R.id.textKeteranganJobOrder);
                textMaterialRequests = (TextView) itemView.findViewById(R.id.textMaterialRequests);
                textProposedBudget = (TextView) itemView.findViewById(R.id.textProposedBudget);
                textCashProjectReport = (TextView) itemView.findViewById(R.id.textCashProjectReport);
                textManPowerTemporary = (TextView) itemView.findViewById(R.id.textManPowerTemporary);
                textManPowerKontrak = (TextView) itemView.findViewById(R.id.textManPowerKontrak);
                textCashOnDeliveries = (TextView) itemView.findViewById(R.id.textCashOnDeliveries);
                textWorkOrder = (TextView) itemView.findViewById(R.id.textWorkOrder);
                textExpenses = (TextView) itemView.findViewById(R.id.textExpenses);
                textPengeluaran = (TextView) itemView.findViewById(R.id.textPengeluaran);
                textBudgetingAmount = (TextView) itemView.findViewById(R.id.textBudgetingAmount);
                textTotalInvoice = (TextView) itemView.findViewById(R.id.textTotalInvoice);
                textTotalPayment = (TextView) itemView.findViewById(R.id.textTotalPayment);
                textNilai = (TextView) itemView.findViewById(R.id.textNilai);
                textLabaRugiBerjalan = (TextView) itemView.findViewById(R.id.textLabaRugiBerjalan);

                layout = (LinearLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}