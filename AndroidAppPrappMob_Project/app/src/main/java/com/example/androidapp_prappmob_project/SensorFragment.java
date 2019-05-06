package com.example.androidapp_prappmob_project;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {


    public SensorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }
//
//
//    public class SensorHttpRequestTask extends AsyncTask<Void, Void, Sensor[]> {
//        @Override
//        protected Sensor[] doInBackground(Void... params) {
//            try {
//
//
//
//
//
//                return value;
////                String url = "http://192.168.6.92:8080/quest/getQuests";
////
////                RestTemplate restTemplate = new RestTemplate();
////                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
////                Quest[] value = restTemplate.getForObject(url, Quest[].class);
////                return value;
//
//            } catch (Exception e) {
//                Log.e("MapsActivity", e.getMessage(), e);
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Sensor[] response) {
//
////            String password = response[0].getPassword();
////            Log.d("CorrectPW:",password);
////            correctPassword = password;
//
//        }
//    }

}
