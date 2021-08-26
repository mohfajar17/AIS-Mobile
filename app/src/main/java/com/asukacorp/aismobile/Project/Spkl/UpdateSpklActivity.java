package com.asukacorp.aismobile.Project.Spkl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.aismobile.Config;
import com.asukacorp.aismobile.Data.Project.Spkl;
import com.asukacorp.aismobile.R;
import com.asukacorp.aismobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateSpklActivity extends AppCompatActivity {

    private EditText editSpklNumber;
    private EditText editSpklDesc;
    private EditText editSpklDate;
    private EditText editSpklJo;
    private EditText editSpklLokasi;
    private EditText editSpklDepartment;
    private EditText editSpklRequested;
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
    private int spklId1, spklId2, spklId3, spklId4, spklId5;
    private int spklEmployeeId1, spklEmployeeId2, spklEmployeeId3, spklEmployeeId4, spklEmployeeId5;
    private EditText spklNamaKaryawan1, spklNamaKaryawan2, spklNamaKaryawan3, spklNamaKaryawan4, spklNamaKaryawan5;
    private EditText spklOvertimeDate1, spklOvertimeDate2, spklOvertimeDate3, spklOvertimeDate4, spklOvertimeDate5;
    private EditText spklStartTime1, spklStartTime2, spklStartTime3, spklStartTime4, spklStartTime5;
    private EditText spklFinishTime1, spklFinishTime2, spklFinishTime3, spklFinishTime4, spklFinishTime5;
    private EditText spklKeterangan1, spklKeterangan2, spklKeterangan3, spklKeterangan4, spklKeterangan5;

    private SharedPrefManager sharedPrefManager;
    private Spkl spkls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spkl);

        Bundle bundle = getIntent().getExtras();
        spkls = bundle.getParcelable("detail");
        sharedPrefManager = new SharedPrefManager(this);
        editSpklNumber = (EditText) findViewById(R.id.editSpklNumber);
        editSpklDesc = (EditText) findViewById(R.id.editSpklDesc);
        editSpklDate = (EditText) findViewById(R.id.editSpklDate);
        editSpklJo = (EditText) findViewById(R.id.editSpklJo);
        editSpklLokasi = (EditText) findViewById(R.id.editSpklLokasi);
        editSpklDepartment = (EditText) findViewById(R.id.editSpklDepartment);
        editSpklRequested = (EditText) findViewById(R.id.editSpklRequested);
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

        spklNamaKaryawan1 = (EditText) findViewById(R.id.spklNamaKaryawan1);
        spklNamaKaryawan2 = (EditText) findViewById(R.id.spklNamaKaryawan2);
        spklNamaKaryawan3 = (EditText) findViewById(R.id.spklNamaKaryawan3);
        spklNamaKaryawan4 = (EditText) findViewById(R.id.spklNamaKaryawan4);
        spklNamaKaryawan5 = (EditText) findViewById(R.id.spklNamaKaryawan5);
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

        editSpklNumber.setText("" + spkls.getOvertime_workorder_number());
        editSpklJo.setText("" + spkls.getJob_order_id());
        editSpklLokasi.setText("" + spkls.getWork_location());
        editSpklDepartment.setText("" + spkls.getDepartment_id());
        editSpklDesc.setText("" + spkls.getWork_description());
        editSpklRequested.setText("" + spkls.getCreated_by());

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        try {
            editSpklDate.setText("" + output.format(input.parse(spkls.getProposed_date())));
        } catch (ParseException e) {
            editSpklDate.setText("" + spkls.getProposed_date());
            e.printStackTrace();
        }

        buttonBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataSpkl();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editSpklDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        spklOvertimeDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateSpklActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                timePicker = new TimePickerDialog(UpdateSpklActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklFinishTime5.setText( selectedHour + ":" + selectedMinute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });

        getDataSpkl();
    }

    private void updateDataSpkl() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_SPKL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        Toast.makeText(UpdateSpklActivity.this, "Success", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(UpdateSpklActivity.this, "Error add data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateSpklActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(UpdateSpklActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("work_description", "" + editSpklDesc.getText().toString());
                param.put("proposed_date", "" + editSpklDate.getText().toString());
                param.put("modified_by", "" + sharedPrefManager.getUserId());
                param.put("overtime_workorder_id", "" + spkls.getOvertime_workorder_id());
                param.put("employeeId1", "" + spklEmployeeId1);
                param.put("overtime_date1", "" + spklOvertimeDate1.getText().toString());
                param.put("description1", "" + spklKeterangan1.getText().toString());
                param.put("start_time1", "" + spklStartTime1.getText().toString());
                param.put("finish_time1", "" + spklFinishTime1.getText().toString());
                param.put("otwo_detail_id1", "" + spklId1);
                param.put("employeeId2", "" + spklEmployeeId2);
                param.put("overtime_date2", "" + spklOvertimeDate2.getText().toString());
                param.put("description2", "" + spklKeterangan2.getText().toString());
                param.put("start_time2", "" + spklStartTime2.getText().toString());
                param.put("finish_time2", "" + spklFinishTime2.getText().toString());
                param.put("otwo_detail_id2", "" + spklId2);
                param.put("employeeId3", "" + spklEmployeeId3);
                param.put("overtime_date3", "" + spklOvertimeDate3.getText().toString());
                param.put("description3", "" + spklKeterangan3.getText().toString());
                param.put("start_time3", "" + spklStartTime3.getText().toString());
                param.put("finish_time3", "" + spklFinishTime3.getText().toString());
                param.put("otwo_detail_id3", "" + spklId3);
                param.put("employeeId4", "" + spklEmployeeId4);
                param.put("overtime_date4", "" + spklOvertimeDate4.getText().toString());
                param.put("description4", "" + spklKeterangan4.getText().toString());
                param.put("start_time4", "" + spklStartTime4.getText().toString());
                param.put("finish_time4", "" + spklFinishTime4.getText().toString());
                param.put("otwo_detail_id4", "" + spklId4);
                param.put("employeeId5", "" + spklEmployeeId5);
                param.put("overtime_date5", "" + spklOvertimeDate5.getText().toString());
                param.put("description5", "" + spklKeterangan5.getText().toString());
                param.put("start_time5", "" + spklStartTime5.getText().toString());
                param.put("finish_time5", "" + spklFinishTime5.getText().toString());
                param.put("otwo_detail_id5", "" + spklId5);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void getDataSpkl() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LIST_DETAIL_SPKL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        spklId1 = jsonArray.getJSONObject(0).getInt("otwo_detail_id");
                        spklEmployeeId1 = jsonArray.getJSONObject(0).getInt("employee_id");
                        spklNamaKaryawan1.setText(jsonArray.getJSONObject(0).getString("fullname") + " - " + jsonArray.getJSONObject(0).getString("job_grade_name"));
                        spklOvertimeDate1.setText(jsonArray.getJSONObject(0).getString("overtime_date"));
                        spklKeterangan1.setText(jsonArray.getJSONObject(0).getString("description"));
                        spklStartTime1.setText(jsonArray.getJSONObject(0).getString("start_time"));
                        spklFinishTime1.setText(jsonArray.getJSONObject(0).getString("finish_time"));
                        spklId2 = jsonArray.getJSONObject(1).getInt("otwo_detail_id");
                        spklEmployeeId2 = jsonArray.getJSONObject(1).getInt("employee_id");
                        spklNamaKaryawan2.setText(jsonArray.getJSONObject(1).getString("fullname") + " - " + jsonArray.getJSONObject(1).getString("job_grade_name"));
                        spklOvertimeDate2.setText(jsonArray.getJSONObject(1).getString("overtime_date"));
                        spklKeterangan2.setText(jsonArray.getJSONObject(1).getString("description"));
                        spklStartTime2.setText(jsonArray.getJSONObject(1).getString("start_time"));
                        spklFinishTime2.setText(jsonArray.getJSONObject(1).getString("finish_time"));
                        spklId3 = jsonArray.getJSONObject(2).getInt("otwo_detail_id");
                        spklEmployeeId3 = jsonArray.getJSONObject(2).getInt("employee_id");
                        spklNamaKaryawan3.setText(jsonArray.getJSONObject(2).getString("fullname") + " - " + jsonArray.getJSONObject(2).getString("job_grade_name"));
                        spklOvertimeDate3.setText(jsonArray.getJSONObject(2).getString("overtime_date"));
                        spklKeterangan3.setText(jsonArray.getJSONObject(2).getString("description"));
                        spklStartTime3.setText(jsonArray.getJSONObject(2).getString("start_time"));
                        spklFinishTime3.setText(jsonArray.getJSONObject(2).getString("finish_time"));
                        spklId4 = jsonArray.getJSONObject(3).getInt("otwo_detail_id");
                        spklEmployeeId4 = jsonArray.getJSONObject(3).getInt("employee_id");
                        spklNamaKaryawan4.setText(jsonArray.getJSONObject(3).getString("fullname") + " - " + jsonArray.getJSONObject(3).getString("job_grade_name"));
                        spklOvertimeDate4.setText(jsonArray.getJSONObject(3).getString("overtime_date"));
                        spklKeterangan4.setText(jsonArray.getJSONObject(3).getString("description"));
                        spklStartTime4.setText(jsonArray.getJSONObject(3).getString("start_time"));
                        spklFinishTime4.setText(jsonArray.getJSONObject(3).getString("finish_time"));
                        spklId5 = jsonArray.getJSONObject(4).getInt("otwo_detail_id");
                        spklEmployeeId5 = jsonArray.getJSONObject(4).getInt("employee_id");
                        spklNamaKaryawan5.setText(jsonArray.getJSONObject(4).getString("fullname") + " - " + jsonArray.getJSONObject(4).getString("job_grade_name"));
                        spklOvertimeDate5.setText(jsonArray.getJSONObject(4).getString("overtime_date"));
                        spklKeterangan5.setText(jsonArray.getJSONObject(4).getString("description"));
                        spklStartTime5.setText(jsonArray.getJSONObject(4).getString("start_time"));
                        spklFinishTime5.setText(jsonArray.getJSONObject(4).getString("finish_time"));
                    } else {
                        Toast.makeText(UpdateSpklActivity.this, "Filed load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(UpdateSpklActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("jobOrder", "" + spkls.getOvertime_workorder_id());
                return param;
            }
        };
        Volley.newRequestQueue(UpdateSpklActivity.this).add(request);
    }
}