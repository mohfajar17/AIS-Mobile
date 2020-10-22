package com.example.aismobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Contact.ContactMenuActivity;
import com.example.aismobile.Dashboard.DashboardActivity;
import com.example.aismobile.Inventory.InventoryMenuActivity;
import com.example.aismobile.Finance.FinanceMenuActivity;
import com.example.aismobile.Kalender.HomeCollection;
import com.example.aismobile.Kalender.HwAdapter;
import com.example.aismobile.News.NewsActivity;
import com.example.aismobile.Personalia.PersonaliaMenuActivity;
import com.example.aismobile.Profile.ProfileActivity;
import com.example.aismobile.Project.ProjectMenuActivity;
import com.example.aismobile.Marketing.MarketingMenuActivity;
import com.example.aismobile.Crm.CrmMenuActivity;
import com.example.aismobile.Purchasing.PurchasingMenuActivity;
import com.example.aismobile.Report.ReportActivity;
import com.example.aismobile.Safety.SafetyMenuActivity;
import com.example.aismobile.Setup.SetupActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private SharedPrefManager sharedPrefManager;
    private boolean doubleBackToExitPressedOnce = false;

    private Dialog myDialog;
    private CircleImageView imageAkun;
    private LinearLayout buttonDetailInfo;
    private LinearLayout layoutDetailInfo;
    public TextView textViewCuti;
    private TextView textViewMoneybox;
    private TextView textName;
    private TextView textViewUserGroup;
    private TextView textViewCustomerInvoice;
    private TextView textViewSupplierInvoice;
    private TextView textViewBankAccount;
    private TextView textViewSalesQuotation;
    private TextView textViewInventoryPrice;

    private LinearLayout menuDashboard;
    private LinearLayout menuFinance;
    private LinearLayout menuInventory;
    private LinearLayout menuProject;
    private LinearLayout menuMarketing;
    private LinearLayout menuCrm;
    private LinearLayout menuContact;
    private LinearLayout menuSetup;
    private LinearLayout menuReport;
    private LinearLayout menuPersonalia;
    private LinearLayout menuPurchasing;
    private LinearLayout menuSafety;
    private LinearLayout menuNews;

    private LinearLayout layoutDashbord;
    private LinearLayout layoutDashbordChart;

    private Button buttonChart;
    private Button buttonDetailChart;
    private BarChart barChart;

    private long ci;
    private long si;
    private long ba;
    private long sq;
    private long ip;
    private int count=0;

    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        stopService(new Intent(getBaseContext(), AppService.class));
        startService(new Intent(getBaseContext(), AppService.class));

        sharedPrefManager = SharedPrefManager.getInstance(this);

        imageAkun = (CircleImageView) findViewById(R.id.imageAkun);
        buttonDetailInfo = (LinearLayout) findViewById(R.id.buttonDetailInfo);
        layoutDetailInfo = (LinearLayout) findViewById(R.id.layoutDetailInfo);
        textViewUserGroup = (TextView) findViewById(R.id.textViewUserGroup);
        textName = (TextView) findViewById(R.id.textViewNameDisplay);
        textViewCuti = (TextView) findViewById(R.id.textViewCuti);
        textViewMoneybox = (TextView) findViewById(R.id.textViewMoneybox);
        textViewCustomerInvoice = (TextView) findViewById(R.id.textViewCustomerInvoice);
        textViewSupplierInvoice = (TextView) findViewById(R.id.textViewSupplierInvoice);
        textViewBankAccount = (TextView) findViewById(R.id.textViewBankAccount);
        textViewSalesQuotation = (TextView) findViewById(R.id.textViewSalesQuotation);
        textViewInventoryPrice = (TextView) findViewById(R.id.textViewInventoryPrice);
        menuDashboard = (LinearLayout) findViewById(R.id.menuDashboard);
        menuFinance = (LinearLayout) findViewById(R.id.menuFinance);
        menuInventory = (LinearLayout) findViewById(R.id.menuInventory);
        menuProject = (LinearLayout) findViewById(R.id.menuProject);
        menuMarketing = (LinearLayout) findViewById(R.id.menuMarketing);
        menuCrm = (LinearLayout) findViewById(R.id.menuCrm);
        menuContact = (LinearLayout) findViewById(R.id.menuContact);
        menuSetup = (LinearLayout) findViewById(R.id.menuSetup);
        menuReport = (LinearLayout) findViewById(R.id.menuReport);
        menuPersonalia = (LinearLayout) findViewById(R.id.menuPersonalia);
        menuPurchasing = (LinearLayout) findViewById(R.id.menuPurchasing);
        menuSafety = (LinearLayout) findViewById(R.id.menuSafety);
        menuNews = (LinearLayout) findViewById(R.id.menuNews);
        barChart = (BarChart) findViewById(R.id.barChart);
        buttonChart = (Button) findViewById(R.id.buttonChart);
        buttonDetailChart = (Button) findViewById(R.id.buttonDetailChart);
        layoutDashbord = (LinearLayout) findViewById(R.id.layoutDashbord);
        layoutDashbordChart = (LinearLayout) findViewById(R.id.layoutDashbordChart);

        if (sharedPrefManager.getFileName().equals("")){
            if (sharedPrefManager.getGender().equals("1"))
                imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_male));
            else imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_female));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getFileName()).into(imageAkun); //get photo profile

        textViewUserGroup.setText(sharedPrefManager.getUserGroupName());
        textName.setText(sharedPrefManager.getUserDisplayName());

        getDataLeave(sharedPrefManager.getEmployeeId());
        getDataMoneybox(sharedPrefManager.getEmployeeId());
        getDataCustomerInvoice();
        getDataSupplierInvoice();
        getDataBank();
        getDataSalesQuotation();
        getDataInventoryPrice();

        imageAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaActivity = new Intent(MainActivity.this, ProfileActivity.class);
                startActivityForResult(bukaActivity,1);
                finish();
            }
        });

        buttonDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutDetailInfo.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutDetailInfo.setLayoutParams(params);
            }
        });

        myDialog = new Dialog(MainActivity.this);
        menuDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaActivity = new Intent(MainActivity.this, DashboardActivity.class);
                startActivityForResult(bukaActivity, 1);
            }
        });
        menuFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("finance".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, FinanceMenuActivity.class);
                    startActivityForResult(bukaMenuActivity, 1);
                } else ShowPopup("Finance Accounting");
            }
        });

        menuInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("inventory".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, InventoryMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Inventory");
            }
        });

        menuProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("project".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, ProjectMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Project");
            }
        });

        menuMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("marketing".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, MarketingMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Marketing");
            }
        });
        menuCrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("crm".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, CrmMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("CRM");
            }
        });

        menuContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("contact".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, ContactMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Contact");
            }
        });

        menuSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, SetupActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, ReportActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuPersonalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("hrga".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, PersonaliaMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Personalia");
            }
        });

        menuPurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("purchasing".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, PurchasingMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Purchasing");
            }
        });

        menuSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefManager.getAccessModul().toLowerCase().contains("hse".toLowerCase())) {
                    Intent bukaMenuActivity = new Intent(MainActivity.this, SafetyMenuActivity.class);
                    startActivityForResult(bukaMenuActivity,1);
                } else ShowPopup("Safety");
            }
        });

        menuNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, NewsActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        if (sharedPrefManager.getAccessModul().toLowerCase().contains("dashboard".toLowerCase())){
            ViewGroup.LayoutParams params;
            params = layoutDashbord.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutDashbord.setLayoutParams(params);
        }

        buttonChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params;
                params = layoutDashbordChart.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutDashbordChart.setLayoutParams(params);
                params = layoutDashbord.getLayoutParams();
                params.height = 0;
                layoutDashbord.setLayoutParams(params);
            }
        });

        buttonDetailChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params;
                params = layoutDashbord.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutDashbord.setLayoutParams(params);
                params = layoutDashbordChart.getLayoutParams();
                params.height = 0;
                layoutDashbordChart.setLayoutParams(params);
            }
        });

        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();
        getHoliday();
    }

    private void setCalendar(){
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month,HomeCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setPreviousMonth();
                    refreshCalendar();
                }
            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2018) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, MainActivity.this);
            }
        });
    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

    private void getHoliday() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_CALENDAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    HomeCollection.date_collection_arr.add( new HomeCollection(jsonArray.getJSONObject(i).getString("holiday_date"),jsonArray.getJSONObject(i).getString("holiday_date"),jsonArray.getJSONObject(i).getString("holiday_name"),jsonArray.getJSONObject(i).getString("description")));
                                }
                                setCalendar();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataInventoryPrice() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_INVENTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                ip = jsonData.getLong("item");
                                textViewInventoryPrice.setText("Rp. "+formatMoney(jsonData.getLong("item")));
                                count++;
                                if (count==5)
                                    setChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataSalesQuotation() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_SALES_QUOTATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                sq = jsonData.getLong("amount")-jsonData.getLong("wo_amount");
                                textViewSalesQuotation.setText("Rp. "+formatMoney(sq));
                                count++;
                                if (count==5)
                                    setChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataBank() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_BANK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                ba = jsonData.getLong("ending_reconcile_balance");
                                textViewBankAccount.setText("Rp. "+formatMoney(ba));
                                count++;
                                if (count==5)
                                    setChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataSupplierInvoice() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_SUPPLIER_INVOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                si = jsonData.getLong("amount")-jsonData.getLong("discount")+jsonData.getLong("ppn")+jsonData.getLong("adjustment_value");
                                textViewSupplierInvoice.setText("Rp. "+formatMoney(si));
                                count++;
                                if (count==5)
                                    setChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataCustomerInvoice() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_CUSTOMER_INVOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                ci = jsonData.getLong("grand_total");// jsonData.getLong("SOI_amount")+jsonData.getLong("SOI_service_amount")+jsonData.getLong("SOI_ppn")+jsonData.getLong("SOI_service_ppn");
                                textViewCustomerInvoice.setText("Rp. "+formatMoney(ci));
                                count++;
                                if (count==5)
                                    setChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(this).add(request);
    }

    private String formatMoney(long money){
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(money);
    }

    private void getDataMoneybox(final String employeeNumber) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_MONEYBOX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        textViewMoneybox.setText(formatMoney(jsonData.getLong("money_box")));
                    } else textViewMoneybox.setText("0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("employee_number", employeeNumber);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataLeave(final String employeeId) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        textViewCuti.setText(jsonData.getString("leave"));
                    } else textViewCuti.setText("0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("employee_id", employeeId);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }

    private void setChart(){
        barChart.setOnChartValueSelectedListener(this);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(barChart); // For bounds control
//        barChart.setMarker(mv); // Set the marker to the chart

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
//        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = barChart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        barChart.getAxisRight().setEnabled(false);

        setData();
    }

    private void setData(){
        float groupSpace = 0.08f;
        float barSpace = 0.034f; // x4 DataSet
        float barWidth = 0.15f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount = 1;
        int startYear = 2020;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();
        ArrayList<BarEntry> values5 = new ArrayList<>();

        values1.add(new BarEntry(startYear, (float) ci));
        values2.add(new BarEntry(startYear, (float) si));
        values3.add(new BarEntry(startYear, (float) ba));
        values4.add(new BarEntry(startYear, (float) sq));
        values5.add(new BarEntry(startYear, (float) ip));

        BarDataSet set1, set2, set3, set4, set5;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) barChart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) barChart.getData().getDataSetByIndex(3);
            set5 = (BarDataSet) barChart.getData().getDataSetByIndex(4);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            set5.setValues(values5);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Customer Invoice");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Supplier Invoice");
            set2.setColor(Color.rgb(255, 102, 0));
            set3 = new BarDataSet(values3, "Bank Account");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Sales Quotation");
            set4.setColor(Color.rgb(164, 228, 251));
            set5 = new BarDataSet(values5, "Inventory Price");
            set5.setColor(Color.rgb(246, 162, 246));

            BarData data = new BarData(set1, set2, set3, set4, set5);
            data.setValueFormatter(new LargeValueFormatter());
//            data.setValueTypeface(tfLight);

            barChart.setData(data);
        }

        // specify the width each bar should have
        barChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(startYear + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(startYear, groupSpace, barSpace);
        barChart.invalidate();
    }

    public void ShowPopup(String massage) {
        TextView textViewWarning;
        TextView closeDialog;
        myDialog.setContentView(R.layout.custom_popup);
        textViewWarning = (TextView) myDialog.findViewById(R.id.textViewWarning);
        closeDialog = (TextView) myDialog.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        textViewWarning.setText("You can't access modul " + massage);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}