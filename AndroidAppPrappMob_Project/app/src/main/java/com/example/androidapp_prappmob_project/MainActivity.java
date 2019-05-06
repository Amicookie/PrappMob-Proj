package com.example.androidapp_prappmob_project;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String url = "http://192.168.43.218:5000";

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    public static JSONArray workstationArray;
    public static JSONArray sensorArray;
    public static JSONArray sampleArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        (findViewById(R.id.read_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new WebServiceHandler()
                                .execute(url+"/workstations", url+"/sensors", url+"/samples");

                    }
                });

    }

    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader.add("Filter");

        List<String> options = new ArrayList<>();
        options.add("Date from:");
        options.add("Date to:");
        options.add("Workstation:");
        options.add("Sensor:");

        listHash.put(listDataHeader.get(0),options);
    }

    @SuppressLint("StaticFieldLeak")
    private class WebServiceHandler extends AsyncTask<String, Void, List<String>> {

        // okienko dialogowe, które każe użytkownikowi czekać
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
        // mamy w niej dostęp do elementów UI
        @Override
        protected void onPreExecute() {
            // wyświetlamy okienko dialogowe każące czekać
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        // główna operacja, która wykona się w osobnym wątku
        // nie ma w niej dostępu do elementów UI
        @Override
        protected List<String> doInBackground(String... urls) {

            try {

                Log.d(MainActivity.class.getSimpleName(), "DoInBg try block");

                List<String> jsonArrayPrep = new ArrayList<>();

                for(int i=0; i<urls.length; i++){
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

            // chowamy okno dialogowe
            dialog.dismiss();

            try {
                // pobieranie tabelki jsonów z requesta

                // Define static arrays usable in other views with data
                workstationArray = new JSONArray(result.get(0));
                sensorArray = new JSONArray(result.get(1));
                sampleArray = new JSONArray(result.get(2));

                JSONArray json = new JSONArray(result.get(0));

                Log.d("JSONARRAY", json.toString());

                for(int j=0; j<json.length(); j++){
                    System.out.println(json.getJSONObject(j).toString());
                }



                ((TextView) findViewById(R.id.workstation_id)).setText("id: ");
                ((TextView) findViewById(R.id.workstation_name)).setText("name: ");


                for(int j=0; j<json.length(); j++) {
                    // reprezentacja obiektu JSON w Javie
                    JSONObject jsonObject = json.getJSONObject(j);

                    Log.d("JSONOBJECT", jsonObject.toString());

                    //Log.d("JSONOBJECT", jsonObject.optString("workstations"));

                    // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
                    ((TextView) findViewById(R.id.workstation_id)).append(jsonObject.optString("station_id") + " ");
                    ((TextView) findViewById(R.id.workstation_name)).append(jsonObject.optString("station_name") + " ");

                }
                //}





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


}
