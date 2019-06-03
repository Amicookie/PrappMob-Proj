package com.example.prappmobcorrectviews.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.transparent;
import static android.widget.TableRow.LayoutParams.*;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sensorList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorsFragment extends Fragment {

    // temporary list
    //private List<Sensor> sensorsListTemp;


    public SensorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.fragment_sensors, container, false);

        final FragmentActivity fragmentActivity = getActivity();

        if(returnView!=null){
//            sensorsListTemp = new ArrayList<>();
//
//            for(int i=0; i<7;i++){
//                sensorsListTemp.add(new Sensor(i, "S"+i, "S"+i+"D", i));
//            }
//
//            for(int i=0; i<sensorsListTemp.size();i++) Log.d("SensorListTemp", sensorsListTemp.get(i).toString());


            // Inflate the layout for this fragment
            addNewSensorToTableView(returnView, fragmentActivity);
        }

        return returnView;
    }



    private void addNewSensorToTableView(View returnView, FragmentActivity fragmentActivity){

        // Get reference to sensorsTable
        TableLayout sensorsTable = (TableLayout) returnView.findViewById(R.id.sensorsTable);

        // Fill in cells
        // count sensors (0,1,2...)
        int count = 0;

        List<TableRow> tableRows = new ArrayList<>();

        for(Sensor sensor : sensorList) {

            if(count%3 == 0){
                TableRow tempTableRow = new TableRow(fragmentActivity);
                tempTableRow.setLayoutParams(new TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                tempTableRow.setPadding(5,5,5,5);
                tempTableRow.setTag("TableRow"+count/3);
                tableRows.add(tempTableRow); // if 0, 3, 6... add new tablerow
            }

            Button button = new Button(fragmentActivity, null, android.R.attr.buttonBarButtonStyle);
            button.setTag("SensorRow" + count/3 + "Column" + count%3);
            button.setBackgroundResource(transparent);
            button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sensor, 0 ,0);
            button.setText(sensor.getSensor_name());
            button.setLayoutParams(new LayoutParams(count%3));

            //if(count%3==0)
            tableRows.get(tableRows.size()-1).addView(button);

            count++;

        }

        for(TableRow tr : tableRows){
            sensorsTable.addView(tr);
        }

    }

}
