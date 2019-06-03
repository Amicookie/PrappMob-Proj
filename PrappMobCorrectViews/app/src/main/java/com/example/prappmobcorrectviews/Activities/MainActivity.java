package com.example.prappmobcorrectviews.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sample;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.Filter;
import com.example.prappmobcorrectviews.Fragments.FilteredDataFragment;
import com.example.prappmobcorrectviews.R;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.prappmobcorrectviews.Activities.SplashActivity.sensorList;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.workstationList;

public class MainActivity extends AppCompatActivity {

    public static Filter filter;

    // Picking date from and to
    private Calendar calendar;
    private int year, month, day;
    private TextView dateFrom, dateTo;
    private String whichDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        Button mShowFilterDialog = findViewById(R.id.button_filter_popup);

        mShowFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_filter_pop_up, null);

                filter = new Filter();

                dateFrom =  mView.findViewById(R.id.dateFrom_input);
                dateTo = mView.findViewById(R.id.dateTo_input);
                final Spinner workstation = mView.findViewById(R.id.workstation_input);
                final Spinner sensor = mView.findViewById(R.id.sensor_input);

                List<String> wsTempArray = new ArrayList<>();
                for(Workstation ws : workstationList){
                    wsTempArray.add(ws.getStation_name());
                }

                ArrayAdapter<String> WSadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, wsTempArray);
                WSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                workstation.setAdapter(WSadapter);

                List<String> sensTempArray = new ArrayList<>();
                for(Sensor sens: sensorList){
                    sensTempArray.add(sens.getSensor_name());
                }

                ArrayAdapter<String> sensAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, sensTempArray);
                sensAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sensor.setAdapter(sensAdapter);

                Button calendarButtonDateFrom = mView.findViewById(R.id.insertDateFrom_icon);
                Button calendarButtonDateTo = mView.findViewById(R.id.insertDateTo_icon);

                calendarButtonDateFrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        whichDate = "from";
                        setDate(v);
                        dateFrom.setVisibility(View.VISIBLE);

                    }
                });


                calendarButtonDateTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        whichDate = "to";
                        setDate(v);
                        dateTo.setVisibility(View.VISIBLE);

                    }
                });


                Button filterButton = mView.findViewById(R.id.filterButton);

                filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Zmienić formatowanie

//                        filter.setDateFrom(dateFrom.getText());
//                        filter.setDateTo(dateTo.getText());
//                        filter.setSensor(sensor.getSelectedItem().toString());
//                        filter.setWorkstation(workstation.getSelectedItem().toString());


                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment filteredDataFragment = new FilteredDataFragment();
                        ft.replace(R.id.fragment_visible, filteredDataFragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();


                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });


    }

    // Set Date via pop-up-calendar
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3, whichDate);
                }
            };

    private void showDate(int year, int month, int day, String whichDate) {
        if(whichDate.equals("from")){
            dateFrom.setText(day + "/" + month + "/" + year);
        } else if(whichDate.equals("to")){
            dateTo.setText(day + "/" + month + "/" + year);
        }

    }


    // Add to REST API
    public void sendRESTPostRequest(View view) {



    }
}
