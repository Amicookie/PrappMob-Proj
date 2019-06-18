package com.example.prappmobcorrectviews.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sample;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;
import com.example.prappmobcorrectviews.Fragments.FilteredDataFragment;
import com.example.prappmobcorrectviews.Fragments.NoDataFragment;
import com.example.prappmobcorrectviews.Fragments.SampleFragment;
import com.example.prappmobcorrectviews.Fragments.SensorsFragment;
import com.example.prappmobcorrectviews.Notification.NewSampleAddedNotification;
import com.example.prappmobcorrectviews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.prappmobcorrectviews.Activities.SplashActivity.isDataDownloadedFromServer;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sampleList;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sensorList;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.stringToDate;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.url;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.workstationList;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    // Picking date from and to
    private Calendar calendar;
    private int year, month, day;
    private TextView dateFrom, dateTo;
    private String whichDate;


    public AlertDialog.Builder mBuilder;
    public AlertDialog dialog;

    public static List<Sample> filteredSamplesList; // Filtered samples list --> List of samples we've filtered
    public static List<Sample> samplesListForSensor; // List of samples per picked sensor
    public static List<Sensor> sensorsListForWorkstation; // List of sensors per picked workstation


    public static int selectedWorkstationId = -1, selectedSensorId = -1;


    // Sockets

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(url);
            Log.d("URI socket connection","OPEN");

        } catch (URISyntaxException e) {
            Log.d("URI socket connection","FAILED");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();
        mSocket.on("sampleSaved", onSampleSaved);



        if (!isDataDownloadedFromServer) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            NoDataFragment noDataFragment = new NoDataFragment();
            ft.replace(R.id.fragment_visible, noDataFragment, "NO_DATA");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        setContentView(R.layout.activity_main);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        Button mShowFilterDialog = findViewById(R.id.button_filter_popup);

        mShowFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_filter_pop_up, null);

                dateFrom = mView.findViewById(R.id.dateFrom_input);
                dateTo = mView.findViewById(R.id.dateTo_input);
                final Spinner workstation = mView.findViewById(R.id.workstation_input);
                final Spinner sensor = mView.findViewById(R.id.sensor_input);


                ArrayAdapter<Workstation> WSadapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_custom, workstationList);
                WSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                workstation.setAdapter(WSadapter);


                ArrayAdapter<Sensor> sensAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_custom, sensorList);
                sensAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sensor.setAdapter(sensAdapter);


                final Button calendarButtonDateFrom = mView.findViewById(R.id.insertDateFrom_icon);
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

                        Sensor selectedSensor = (Sensor) sensor.getSelectedItem();
                        Workstation selectedWS = (Workstation) workstation.getSelectedItem();

                        new FilterDataHttpRequestTask().execute(dateFrom.getText().toString(),
                                dateTo.getText().toString(),
                                String.valueOf(selectedSensor.getSensor_id()),
                                String.valueOf(selectedWS.getStation_id()));


                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("sampleSaved", onSampleSaved);
    }

    private Emitter.Listener onSampleSaved = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("onSampleSaved Socket","Received");

                    JSONObject data = (JSONObject) args[0];
                    String sensor_id;


                    try {
                        Log.d("onSampleSaved Socket","sensor_id"+data.getString("sensor_id"));

                        sensor_id = data.getString("sensor_id");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    Toast.makeText(getBaseContext(), "New sample added on sensor " + sensor_id + "!", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

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
                    showDate(arg1, arg2 + 1, arg3, whichDate);
                }
            };

    private void showDate(int year, int month, int day, String whichDate) {
        String mm = "", dd = "";
        if (month < 10) {
            mm = "0" + month;
        }
        if (day < 10) {
            dd = "0" + day;
        }
        if (whichDate.equals("from")) {

            dateFrom.setText(year + "-" + mm + "-" + dd + "T00:00");
        } else if (whichDate.equals("to")) {
            dateTo.setText(year + "-" + mm + "-" + dd + "T00:00");
        }

    }


    // Add Sample to REST API
    public void sendRESTPostRequest(View view) {

        final EditText enteredValue = findViewById(R.id.SaveSample_Number);
        final EditText enteredTimestampValue = findViewById(R.id.SaveSample_Timestamp);
        final TextView alertValue = findViewById(R.id.alertValue);
        final TextView alertTimestamp = findViewById(R.id.alertTimestamp);

        if (selectedSensorId != -1 && enteredTimestampValue != null && (enteredValue != null && !enteredValue.getText().toString().equals(""))) {
            if (alertValue.getVisibility() == View.VISIBLE) {
                alertValue.setVisibility(View.GONE);
            }

            if (alertTimestamp.getVisibility() == View.VISIBLE) {
                alertTimestamp.setVisibility(View.GONE);
            }

            new AddSampleHttpRequestTask().execute(enteredValue.getText().toString(),
                    enteredTimestampValue.getText().toString(),
                    String.valueOf(selectedSensorId));
        } else if (enteredValue != null && enteredValue.getText().toString().equals("")) {
            // pop up window that says "enter value before submitting" :)
            alertValue.setVisibility(View.VISIBLE);
            alertValue.setText(getString(R.string.alertValue));

        } else if (alertTimestamp != null && alertTimestamp.getText().toString().equals("")) {
            alertValue.setVisibility(View.VISIBLE);
            alertValue.setText(getString(R.string.alertTimestamp));
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class AddSampleHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {


            try {
                JSONObject request = new JSONObject();

                request.put("value", params[0]);
                request.put("timestamp", params[1]);
                request.put("sensor_id", params[2]);
                Log.d("Sample Added Map", request.toString());

                return sendHTTPData(SplashActivity.url + "/samples", request);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("Czy sie wykonalo", "TAK");
            Log.d("Odpowiedz serwera", response);

            try {

                JSONObject jsonSampleAdded = new JSONObject(response);

                Log.d("JSONARRAY", jsonSampleAdded.toString());
                sampleList.add(new Sample(jsonSampleAdded.optInt("sample_id"),
                        (Double) jsonSampleAdded.opt("value"),
                        stringToDate(jsonSampleAdded.optString("timestamp")),
                        jsonSampleAdded.optInt("sensor_id")));


                // TODO: Refreshing page after sample added ;)


//                if(selectedSensorId==sampleList.get(sampleList.size()-1).getSensor_id()){
//                    // odśwież widok :)
//                    getSupportFragmentManager().popBackStack();
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    //ft.remove(getSupportFragmentManager().findFragmentByTag("SENS_FRAG"));
//                    SampleFragment sampleFragment = new SampleFragment();
//                    ft.replace(R.id.fragment_visible, sampleFragment, "SAMPLE_FRAG");
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft.addToBackStack(null);
//                    ft.commit();
//
//                }

                //Log.d("SampleList Updated:", sampleList.toString());

                NewSampleAddedNotification.showNotify(MainActivity.this,
                        String.valueOf(sampleList.get(sampleList.size() - 1).getSample_id()),
                        String.valueOf(sampleList.get(sampleList.size() - 1).getSensor_id()),
                        69);

                sendSaveSampleSocket(jsonSampleAdded.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public String sendHTTPData(String urlpath, JSONObject json) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(json.toString());
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("test", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("test", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class FilterDataHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {

                JSONObject request = new JSONObject();
                request.put("dateFrom", params[0]);
                request.put("dateTo", params[1]);
                request.put("sensor_id", params[2]);
                request.put("workstation_id", params[3]);
                Log.d("Sample Filtered Map", request.toString());

                return sendHTTPData(SplashActivity.url + "/filter", request);

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Czy sie wykonalo", "TAK");
            Log.d("Odpowiedz serwera", result);

            try {

                filteredSamplesList = new ArrayList<>();
                JSONArray jsonSample = new JSONArray(result);

                for (int j = 0; j < jsonSample.length(); j++) {
                    System.out.println(jsonSample.getJSONObject(j).toString());
                    filteredSamplesList.add(new Sample(jsonSample.getJSONObject(j).optInt("sample_id"),
                            (Double) jsonSample.getJSONObject(j).opt("value"),
                            stringToDate(jsonSample.getJSONObject(j).optString("timestamp")),
                            jsonSample.getJSONObject(j).optInt("sensor_id")));
                }


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                //ft.remove(getSupportFragmentManager().findFragmentByTag("SENS_FRAG"));

                FilteredDataFragment filteredDataFragment = new FilteredDataFragment();
                ft.replace(R.id.fragment_visible, filteredDataFragment, "FILTERED_FRAG");
                ft.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left);
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("FILTERED_FRAG");
                ft.commit();

                dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void handleBackButton(View view) {
        getSupportFragmentManager().popBackStack();
    }


    // Socket.io emitter
    private void sendSaveSampleSocket(String jsonObject){
        // jsonObject - {"sensor_id": sensor_id}
        mSocket.emit("sampleSaved", jsonObject);
        Log.d("sampleSaved Socket", "emitted");
    }



}
