package com.example.prappmobcorrectviews.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sample;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    public static List<Workstation> workstationList;
    public static List<Sensor> sensorList;
    public static List<Sample> sampleList;

    public static final String url = "http://192.168.43.160:5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoadViewTask().execute();
    }

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(SplashActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading application View, please wait...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
            //        (findViewById(R.id.read_button))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
            new WebServiceHandler().execute(url+"/workstations", url+"/sensors", url+"/samples");
//
//                    }
//                });
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try
            {
                //Get the current thread's token
                synchronized (this)
                {

                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 4)
                    {
                        //Wait 850 milliseconds
                        this.wait(1000);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter*25);
                    }

                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Handle REST connection

    @SuppressLint("StaticFieldLeak")
    private class WebServiceHandler extends AsyncTask<String, Void, List<String>> {

//        // okienko dialogowe, które każe użytkownikowi czekać
//        private ProgressDialog dialog = new ProgressDialog(SplashActivity.this);

        // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
        // mamy w niej dostęp do elementów UI
        @Override
        protected void onPreExecute() {
//            // wyświetlamy okienko dialogowe każące czekać
//            dialog.setMessage("Please wait...");
//            dialog.show();
        }

        // główna operacja, która wykona się w osobnym wątku
        // nie ma w niej dostępu do elementów UI
        @Override
        protected List<String> doInBackground(String... urls) {

            try {

                Log.d(MainActivity.class.getSimpleName(), "DoInBg try block");

                List<String> jsonArrayPrep = new ArrayList<>();

                for (int i = 0; i < urls.length; i++) {
                    // 1st url = workstations
                    URL url = new URL(urls[i]);
                    URLConnection connection = url.openConnection();

                    // pobranie danych do InputStream
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    // konwersja InputStream na String
                    // wynik będzie przekazany do metody onPostExecute()

                    jsonArrayPrep.add(streamToString(in));
                }


//                // 1st url = workstations
//                URL url = new URL(urls[0]);
//                URLConnection connection = url.openConnection();
//
//                // pobranie danych do InputStream
//                InputStream in = new BufferedInputStream(connection.getInputStream());
//
//                // konwersja InputStream na String
//                // wynik będzie przekazany do metody onPostExecute()
//
//

                return jsonArrayPrep;

            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
                Log.d(MainActivity.class.getSimpleName(), "wyjatek doInBackground");
                return null;
            }

        }

        // metoda wykonuje się po zakończeniu metody głównej,
        // której wynik będzie przekazany;
        // w tej metodzie mamy dostęp do UI
        @Override
        protected void onPostExecute(List<String> result) {

//            // chowamy okno dialogowe
//            dialog.dismiss();

            try {
                // pobieranie tabelki jsonów z requesta

                // Define static arrays usable in other views with data
                workstationList = new ArrayList<>();
                sensorList = new ArrayList<>();
                sampleList = new ArrayList<>();

                JSONArray jsonWS = new JSONArray(result.get(0));
                JSONArray jsonSensor = new JSONArray(result.get(1));
                JSONArray jsonSample = new JSONArray(result.get(2));


                Log.d("JSONARRAY", jsonWS.toString());

                for (int j = 0; j < jsonWS.length(); j++) {
                    System.out.println(jsonWS.getJSONObject(j).toString());
                    workstationList.add(new Workstation(jsonWS.getJSONObject(j).optInt("station_id"),
                            jsonWS.getJSONObject(j).optString("station_name"),
                            jsonWS.getJSONObject(j).optString("station_description")));
                }

                for (int j = 0; j < jsonSensor.length(); j++) {
                    System.out.println(jsonSensor.getJSONObject(j).toString());
                    sensorList.add(new Sensor(jsonSensor.getJSONObject(j).optInt("sensor_id"),
                            jsonSensor.getJSONObject(j).optString("sensor_name"),
                            jsonSensor.getJSONObject(j).optString("sensor_description"),
                            jsonSensor.getJSONObject(j).optInt("station_id")));
                }

                for (int j = 0; j < jsonSample.length(); j++) {
                    System.out.println(jsonSample.getJSONObject(j).toString());
                    sampleList.add(new Sample(jsonSample.getJSONObject(j).optInt("sample_id"),
                            (Double) jsonSample.getJSONObject(j).opt("value"),
                            stringToDate(jsonSample.getJSONObject(j).optString("timestamp"),"E',' dd M yyyy HH:mm:ss z"),
                            jsonSample.getJSONObject(j).optInt("sample_id")));

                   // Log.d("timestamp", sampleList.get(0).getTimestamp().toString());
                }


            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
                Log.d(MainActivity.class.getSimpleName(), "wyjatek onPostExec");

            }
        }
    }


    // konwersja z InputStream do String
    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            Log.d(MainActivity.class.getSimpleName(), stringBuilder.toString());

            reader.close();

        } catch (IOException e) {
            // obsłż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
            Log.d(MainActivity.class.getSimpleName(), "wyjatek metoda");
        }

        return stringBuilder.toString();
    }


    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
