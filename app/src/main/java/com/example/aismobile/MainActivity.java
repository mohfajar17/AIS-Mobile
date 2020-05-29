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

import org.json.JSONException;
import org.json.JSONObject;

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
        menuFinance = (LinearLayout) findViewById(R.id.menuFinance);
        menuInventory = (LinearLayout) findViewById(R.id.menuInventory);
        menuProject = (LinearLayout) findViewById(R.id.menuProject);
        menuMarketing = (LinearLayout) findViewById(R.id.menuMarketing);
        menuCrm = (LinearLayout) findViewById(R.id.menuCrm);
        menuContact = (LinearLayout) findViewById(R.id.menuContact);
        menuSetup = (LinearLayout) findViewById(R.id.menuSetup);
        menuReport = (LinearLayout) findViewById(R.id.menuReport);

        calendarView.setFirstDayOfWeek(2);

        if (sharedPrefManager.getFileName().equals("null")){
            if (Integer.valueOf(sharedPrefManager.getGender())!=1)
                imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_female));
            else imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun_male));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getFileName()).into(imageAkun); //get photo profile

        textViewUserGroup.setText(sharedPrefManager.getUserGroupName());
        textName.setText(sharedPrefManager.getUserDisplayName());

        getDataLeave(sharedPrefManager.getEmployeeId());
        getDataMoneybox(sharedPrefManager.getEmployeeNumber());

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

    private void getDataMoneybox(final String employeeNumber) {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_EMPLOYEE_MONEYBOX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        textViewMoneybox.setText(jsonData.getString("money_box"));
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