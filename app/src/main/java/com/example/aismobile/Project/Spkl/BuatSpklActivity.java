package com.example.aismobile.Project.Spkl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aismobile.Config;
import com.example.aismobile.R;
import com.example.aismobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuatSpklActivity extends AppCompatActivity {

    private String spklNumber;
    private ArrayAdapter<String> adapter;
    private String[] spklJobCodeId;
    private String[] spklLokasiId;
    private String[] spklDepartmenId;
    private String[] spklDepartmenCode;
    private String[] spklRequestedId;
    private String[] spklKaryawan1;
    private String[] spklKaryawan2;
    private String[] spklKaryawan3;
    private String[] spklKaryawan4;
    private String[] spklKaryawan5;

    private EditText editSpklNumber;
    private EditText editSpklDesc;
    private EditText editSpklDate;
    private Spinner editSpklJo;
    private Spinner editSpklLokasi;
    private Spinner editSpklDepartment;
    private Spinner editSpklRequested;
    private Button buttonBuat;
    private ImageView buttonBack;

    private TextView buttonSpkl1;
    private TextView buttonSpkl2;
    private TextView buttonSpkl3;
    private TextView buttonSpkl4;
    private TextView buttonSpkl5;
    private LinearLayout layoutspkl1;
    private LinearLayout layoutspkl2;
    private LinearLayout layoutspkl3;
    private LinearLayout layoutspkl4;
    private LinearLayout layoutspkl5;
    private Spinner spklNamaKaryawan1, spklNamaKaryawan2, spklNamaKaryawan3, spklNamaKaryawan4, spklNamaKaryawan5;
    private EditText spklOvertimeDate1, spklOvertimeDate2, spklOvertimeDate3, spklOvertimeDate4, spklOvertimeDate5;
    private EditText spklStartTime1, spklStartTime2, spklStartTime3, spklStartTime4, spklStartTime5;
    private EditText spklFinishTime1, spklFinishTime2, spklFinishTime3, spklFinishTime4, spklFinishTime5;
    private EditText spklKeterangan1, spklKeterangan2, spklKeterangan3, spklKeterangan4, spklKeterangan5;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_spkl);

        sharedPrefManager = new SharedPrefManager(this);
        editSpklNumber = (EditText) findViewById(R.id.editSpklNumber);
        editSpklDesc = (EditText) findViewById(R.id.editSpklDesc);
        editSpklDate = (EditText) findViewById(R.id.editSpklDate);
        editSpklJo = (Spinner) findViewById(R.id.editSpklJo);
        editSpklLokasi = (Spinner) findViewById(R.id.editSpklLokasi);
        editSpklDepartment = (Spinner) findViewById(R.id.editSpklDepartment);
        editSpklRequested = (Spinner) findViewById(R.id.editSpklRequested);
        buttonBuat = (Button) findViewById(R.id.buttonBuat);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        buttonSpkl1 = (TextView) findViewById(R.id.buttonSpkl1);
        buttonSpkl2 = (TextView) findViewById(R.id.buttonSpkl2);
        buttonSpkl3 = (TextView) findViewById(R.id.buttonSpkl3);
        buttonSpkl4 = (TextView) findViewById(R.id.buttonSpkl4);
        buttonSpkl5 = (TextView) findViewById(R.id.buttonSpkl5);
        layoutspkl1 = (LinearLayout) findViewById(R.id.layoutspkl1);
        layoutspkl2 = (LinearLayout) findViewById(R.id.layoutspkl2);
        layoutspkl3 = (LinearLayout) findViewById(R.id.layoutspkl3);
        layoutspkl4 = (LinearLayout) findViewById(R.id.layoutspkl4);
        layoutspkl5 = (LinearLayout) findViewById(R.id.layoutspkl5);

        spklNamaKaryawan1 = (Spinner) findViewById(R.id.spklNamaKaryawan1);
        spklNamaKaryawan2 = (Spinner) findViewById(R.id.spklNamaKaryawan2);
        spklNamaKaryawan3 = (Spinner) findViewById(R.id.spklNamaKaryawan3);
        spklNamaKaryawan4 = (Spinner) findViewById(R.id.spklNamaKaryawan4);
        spklNamaKaryawan5 = (Spinner) findViewById(R.id.spklNamaKaryawan5);
        spklOvertimeDate1 = (EditText) findViewById(R.id.spklOvertimeDate1);
        spklOvertimeDate2 = (EditText) findViewById(R.id.spklOvertimeDate2);
        spklOvertimeDate3 = (EditText) findViewById(R.id.spklOvertimeDate3);
        spklOvertimeDate4 = (EditText) findViewById(R.id.spklOvertimeDate4);
        spklOvertimeDate5 = (EditText) findViewById(R.id.spklOvertimeDate5);
        spklStartTime1 = (EditText) findViewById(R.id.spklStartTime1);
        spklStartTime2 = (EditText) findViewById(R.id.spklStartTime2);
        spklStartTime3 = (EditText) findViewById(R.id.spklStartTime3);
        spklStartTime4 = (EditText) findViewById(R.id.spklStartTime4);
        spklStartTime5 = (EditText) findViewById(R.id.spklStartTime5);
        spklFinishTime1 = (EditText) findViewById(R.id.spklFinishTime1);
        spklFinishTime2 = (EditText) findViewById(R.id.spklFinishTime2);
        spklFinishTime3 = (EditText) findViewById(R.id.spklFinishTime3);
        spklFinishTime4 = (EditText) findViewById(R.id.spklFinishTime4);
        spklFinishTime5 = (EditText) findViewById(R.id.spklFinishTime5);
        spklKeterangan1 = (EditText) findViewById(R.id.spklKeterangan1);
        spklKeterangan2 = (EditText) findViewById(R.id.spklKeterangan2);
        spklKeterangan3 = (EditText) findViewById(R.id.spklKeterangan3);
        spklKeterangan4 = (EditText) findViewById(R.id.spklKeterangan4);
        spklKeterangan5 = (EditText) findViewById(R.id.spklKeterangan5);

        buttonBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdSpkl();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSpkl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl1.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl1.setLayoutParams(params);
            }
        });
        buttonSpkl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl2.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl2.setLayoutParams(params);
            }
        });
        buttonSpkl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl3.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl3.setLayoutParams(params);
            }
        });
        buttonSpkl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl4.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl4.setLayoutParams(params);
            }
        });
        buttonSpkl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl5.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl5.setLayoutParams(params);
            }
        });

        editSpklDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editSpklDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editSpklDate.setText("" + dateFormat.format(date));
        spklOvertimeDate1.setText("" + dateFormat.format(date));
        spklOvertimeDate2.setText("" + dateFormat.format(date));
        spklOvertimeDate3.setText("" + dateFormat.format(date));
        spklOvertimeDate4.setText("" + dateFormat.format(date));
        spklOvertimeDate5.setText("" + dateFormat.format(date));

        spklOvertimeDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate1.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate3.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate4.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuatSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate5.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setTitle("Select Overtime Date");
                datePickerDialog.show();
            }
        });
        spklStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime3.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime4.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime5.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime3.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime4.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(BuatSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime5.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });

        getListJobCode();
        getListWorkbase();
        getListDepartmen();
        getListRequested();
        getSpklNumber();
    }

    public void getListJobCode() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_JOB_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] spklJobCode = new String[jsonArray.length()+1];
                                spklJobCodeId = new String[jsonArray.length()+1];
                                spklJobCode[0] = "-- Pilih Job Code --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklJobCode[i + 1] = jsonArray.getJSONObject(i).getString("job_order_number") + " | " + jsonArray.getJSONObject(i).getString("job_order_description");
                                    spklJobCodeId[i + 1] = jsonArray.getJSONObject(i).getString("job_order_id");
                                }
                                adapter = new ArrayAdapter<String>(BuatSpklActivity.this, android.R.layout.simple_spinner_dropdown_item, spklJobCode);
                                editSpklJo.setAdapter(adapter);
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
        Volley.newRequestQueue(BuatSpklActivity.this).add(request);
    }

    public void getListWorkbase() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_WORKBASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] spklLokasi = new String[jsonArray.length()+1];
                                spklLokasiId = new String[jsonArray.length()+1];
                                spklLokasi[0] = "-- Pilih Lokasi Kerja --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklLokasi[i + 1] = jsonArray.getJSONObject(i).getString("company_workbase_name");
                                    spklLokasiId[i + 1] = jsonArray.getJSONObject(i).getString("company_workbase_id");
                                }
                                adapter = new ArrayAdapter<String>(BuatSpklActivity.this, android.R.layout.simple_spinner_dropdown_item, spklLokasi);
                                editSpklLokasi.setAdapter(adapter);
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
        Volley.newRequestQueue(BuatSpklActivity.this).add(request);
    }

    public void getListDepartmen() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_DEPARTMEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] spklDepartmen = new String[jsonArray.length()+1];
                                spklDepartmenId = new String[jsonArray.length()+1];
                                spklDepartmenCode = new String[jsonArray.length()+1];
                                spklDepartmen[0] = "-- Pilih Departemen --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklDepartmen[i + 1] = jsonArray.getJSONObject(i).getString("department_name");
                                    spklDepartmenId[i + 1] = jsonArray.getJSONObject(i).getString("department_id");
                                    spklDepartmenCode[i + 1] = jsonArray.getJSONObject(i).getString("department_code");
                                }
                                adapter = new ArrayAdapter<String>(BuatSpklActivity.this, android.R.layout.simple_spinner_dropdown_item, spklDepartmen);
                                editSpklDepartment.setAdapter(adapter);
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
        Volley.newRequestQueue(BuatSpklActivity.this).add(request);
    }

    public void getListRequested() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_LIST_EMPLOYEE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String[] SpklRequested = new String[jsonArray.length()+1];
                                spklRequestedId = new String[jsonArray.length()+1];
                                spklKaryawan1 = new String[jsonArray.length()+1];
                                spklKaryawan2 = new String[jsonArray.length()+1];
                                spklKaryawan3 = new String[jsonArray.length()+1];
                                spklKaryawan4 = new String[jsonArray.length()+1];
                                spklKaryawan5 = new String[jsonArray.length()+1];
                                SpklRequested[0] = "-- Pilih Karyawan --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SpklRequested[i + 1] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    spklRequestedId[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklKaryawan1[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklKaryawan2[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklKaryawan3[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklKaryawan4[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklKaryawan5[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                }
                                adapter = new ArrayAdapter<String>(BuatSpklActivity.this, android.R.layout.simple_spinner_dropdown_item, SpklRequested);
                                editSpklRequested.setAdapter(adapter);

                                spklNamaKaryawan1.setAdapter(adapter);
                                spklNamaKaryawan2.setAdapter(adapter);
                                spklNamaKaryawan3.setAdapter(adapter);
                                spklNamaKaryawan4.setAdapter(adapter);
                                spklNamaKaryawan5.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BuatSpklActivity.this, "Boom", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(BuatSpklActivity.this).add(request);
    }

    public void getSpklNumber(){
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_SPKL_NUMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            spklNumber = jsonObject.getString("data");
                            editSpklNumber.setText("SPKL-XXX-" + spklNumber);
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
        Volley.newRequestQueue(BuatSpklActivity.this).add(request);
    }

    public void createdSpkl(){
        if (editSpklJo.getSelectedItemPosition() == 0 || editSpklLokasi.getSelectedItemPosition() == 0 ||
                editSpklDepartment.getSelectedItemPosition() == 0 || editSpklDesc.getText().toString().matches("") ||
                editSpklDate.getText().toString().matches("") || editSpklRequested.getSelectedItemPosition() == 0 ||
                spklNamaKaryawan1.getSelectedItemPosition() == 0 || spklOvertimeDate1.getText().toString().matches("") ||
                spklStartTime1.getText().toString().matches("") || spklFinishTime1.getText().toString().matches("")) {
            Toast.makeText(BuatSpklActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else {
            final String spklJobCode = spklJobCodeId[editSpklRequested.getSelectedItemPosition()];
            final String spklWorkLocation = spklLokasiId[editSpklLokasi.getSelectedItemPosition()];
            final String spklDepartment = spklDepartmenId[editSpklDepartment.getSelectedItemPosition()];
            final String spklWorkDescription = String.valueOf(editSpklDesc.getText());
            final String spklProposedDate = String.valueOf(editSpklDate.getText());
            final String spklRequested = spklRequestedId[editSpklRequested.getSelectedItemPosition()];

            final String employeeId1 = spklKaryawan1[spklNamaKaryawan1.getSelectedItemPosition()];
            final String employeeId2 = spklKaryawan2[spklNamaKaryawan2.getSelectedItemPosition()];
            final String employeeId3 = spklKaryawan3[spklNamaKaryawan3.getSelectedItemPosition()];
            final String employeeId4 = spklKaryawan4[spklNamaKaryawan4.getSelectedItemPosition()];
            final String employeeId5 = spklKaryawan5[spklNamaKaryawan5.getSelectedItemPosition()];

            final String spklNumberFinal = "SPKL-" + spklDepartmenCode[editSpklDepartment.getSelectedItemPosition()] + "-" + spklNumber;

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CREATE_SPKL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(BuatSpklActivity.this, "Success", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(BuatSpklActivity.this, "", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BuatSpklActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(BuatSpklActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("number", "" + spklNumberFinal);
                    param.put("proposedDate", "" + spklProposedDate);
                    param.put("workDescription", "" + spklWorkDescription);
                    param.put("workLocation", "" + spklWorkLocation);
                    param.put("jobOrder", "" + spklJobCode);
                    param.put("departmentId", "" + spklDepartment);
                    param.put("requestedId", "" + spklRequested);
                    param.put("createdBy", "" + sharedPrefManager.getUserId());
                    param.put("employeeId1", "" + employeeId1);
                    param.put("overtimeDate1", "" + spklOvertimeDate1.getText().toString());
                    param.put("description1", "" + spklKeterangan1.getText().toString());
                    param.put("startTime1", "" + spklStartTime1.getText().toString());
                    param.put("finishTime1", "" + spklFinishTime1.getText().toString());
                    param.put("employeeId2", "" + employeeId2);
                    param.put("overtimeDate2", "" + spklOvertimeDate2.getText().toString());
                    param.put("description2", "" + spklKeterangan2.getText().toString());
                    param.put("startTime2", "" + spklStartTime2.getText().toString());
                    param.put("finishTime2", "" + spklFinishTime2.getText().toString());
                    param.put("employeeId3", "" + employeeId3);
                    param.put("overtimeDate3", "" + spklOvertimeDate3.getText().toString());
                    param.put("description3", "" + spklKeterangan3.getText().toString());
                    param.put("startTime3", "" + spklStartTime3.getText().toString());
                    param.put("finishTime3", "" + spklFinishTime3.getText().toString());
                    param.put("employeeId4", "" + employeeId4);
                    param.put("overtimeDate4", "" + spklOvertimeDate4.getText().toString());
                    param.put("description4", "" + spklKeterangan4.getText().toString());
                    param.put("startTime4", "" + spklStartTime4.getText().toString());
                    param.put("finishTime4", "" + spklFinishTime4.getText().toString());
                    param.put("employeeId5", "" + employeeId5);
                    param.put("overtimeDate5", "" + spklOvertimeDate5.getText().toString());
                    param.put("description5", "" + spklKeterangan5.getText().toString());
                    param.put("startTime5", "" + spklStartTime5.getText().toString());
                    param.put("finishTime5", "" + spklFinishTime5.getText().toString());
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }
}