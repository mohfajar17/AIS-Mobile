package com.example.aismobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
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
import com.example.aismobile.Contact.ContactMenuActivity;
import com.example.aismobile.Inventory.InventoryMenuActivity;
import com.example.aismobile.Finance.FinanceMenuActivity;
import com.example.aismobile.Profile.ProfileActivity;
import com.example.aismobile.Project.ProjectMenuActivity;
import com.example.aismobile.Marketing.MarketingMenuActivity;
import com.example.aismobile.Crm.CrmMenuActivity;
import com.example.aismobile.Report.ReportActivity;
import com.example.aismobile.Setup.SetupActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private boolean doubleBackToExitPressedOnce = false;

    private CircleImageView imageAkun;
    private LinearLayout buttonDetailInfo;
    private LinearLayout layoutDetailInfo;
    private CalendarView calendarView;
    private TextView textViewCuti;
    private TextView textViewMoneybox;
    private TextView textName;
    private TextView textViewUserGroup;
    private TextView textViewCustomerInvoice;
    private TextView textViewSupplierInvoice;
    private TextView textViewBankAccount;
    private TextView textViewSalesQuotation;
    private TextView textViewInventoryPrice;

    private LinearLayout menuFinance;
    private LinearLayout menuInventory;
    private LinearLayout menuProject;
    private LinearLayout menuMarketing;
    private LinearLayout menuCrm;
    private LinearLayout menuContact;
    private LinearLayout menuSetup;
    private LinearLayout menuReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        imageAkun = (CircleImageView) findViewById(R.id.imageAkun);
        buttonDetailInfo = (LinearLayout) findViewById(R.id.buttonDetailInfo);
        layoutDetailInfo = (LinearLayout) findViewById(R.id.layoutDetailInfo);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        textViewUserGroup = (TextView) findViewById(R.id.textViewUserGroup);
        textName = (TextView) findViewById(R.id.textViewNameDisplay);
        textViewCuti = (TextView) findViewById(R.id.textViewCuti);
        textViewMoneybox = (TextView) findViewById(R.id.textViewMoneybox);
        textViewCustomerInvoice = (TextView) findViewById(R.id.textViewCustomerInvoice);
        textViewSupplierInvoice = (TextView) findViewById(R.id.textViewSupplierInvoice);
        textViewBankAccount = (TextView) findViewById(R.id.textViewBankAccount);
        textViewSalesQuotation = (TextView) findViewById(R.id.textViewSalesQuotation);
        textViewInventoryPrice = (TextView) findViewById(R.id.textViewInventoryPrice);
        menuFinance = (LinearLayout) findViewById(R.id.menuFinance);
        menuInventory = (LinearLayout) findViewById(R.id.menuInventory);
        menuProject = (LinearLayout) findViewById(R.id.menuProject);
        menuMarketing = (LinearLayout) findViewById(R.id.menuMarketing);
        menuCrm = (LinearLayout) findViewById(R.id.menuCrm);
        menuContact = (LinearLayout) findViewById(R.id.menuContact);
        menuSetup = (LinearLayout) findViewById(R.id.menuSetup);
        menuReport = (LinearLayout) findViewById(R.id.menuReport);

        calendarView.setFirstDayOfWeek(2);

        if (sharedPrefManager.getFileName().equals("")){
            if (sharedPrefManager.getGender().equals("1"))
                imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_male));
            else imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_female));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getFileName()).into(imageAkun); //get photo profile

        textViewUserGroup.setText(sharedPrefManager.getUserGroupName());
        textName.setText(sharedPrefManager.getUserDisplayName());

        getDataLeave(sharedPrefManager.getEmployeeId());
        getDataMoneybox(sharedPrefManager.getEmployeeNumber());
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

        menuFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, FinanceMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, InventoryMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, ProjectMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, MarketingMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });
        menuCrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, CrmMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
            }
        });

        menuContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaMenuActivity = new Intent(MainActivity.this, ContactMenuActivity.class);
                startActivityForResult(bukaMenuActivity,1);
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
                                textViewInventoryPrice.setText("Rp. "+formatMoney(jsonData.getLong("item")));
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
                                long result = jsonData.getLong("amount")-jsonData.getLong("wo_amount");
                                textViewSalesQuotation.setText("Rp. "+formatMoney(result));
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
                                textViewBankAccount.setText("Rp. "+formatMoney(jsonData.getLong("ending_reconcile_balance")));
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
                                long result = jsonData.getLong("amount")-jsonData.getLong("discount")+jsonData.getLong("ppn")+jsonData.getLong("adjustment_value");
                                textViewSupplierInvoice.setText("Rp. "+formatMoney(result));
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
                                long result = jsonData.getLong("SOI_amount")+jsonData.getLong("SOI_service_amount")+jsonData.getLong("SOI_ppn")+jsonData.getLong("SOI_service_ppn");
                                textViewCustomerInvoice.setText("Rp. "+formatMoney(result));
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}