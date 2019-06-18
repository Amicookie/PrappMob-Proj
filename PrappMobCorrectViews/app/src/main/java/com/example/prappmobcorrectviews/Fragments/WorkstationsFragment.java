package com.example.prappmobcorrectviews.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;
import com.example.prappmobcorrectviews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.color.transparent;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.prappmobcorrectviews.Activities.MainActivity.selectedWorkstationId;
import static com.example.prappmobcorrectviews.Activities.MainActivity.sensorsListForWorkstation;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sensorList;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.workstationList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkstationsFragment extends Fragment {


    public WorkstationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.fragment_workstations, container, false);

        final FragmentActivity fragmentActivity = getActivity();

        if(returnView!=null){
            addNewWorkstationToTableView(returnView, fragmentActivity);
        }

        return returnView;

    }


    private void addNewWorkstationToTableView(View returnView, final FragmentActivity fragmentActivity){


        Log.d("Method", "in!");

        // Get reference to workstationTable
        final TableLayout workstationTable = (TableLayout) returnView.findViewById(R.id.workstationTable);
        // Fill in cells


        // count workstations (0,1,2...)
        int count = 0;

        final List<TableRow> tableRows = new ArrayList<>();

        for(final Workstation workstation: workstationList) {

            if(count%3 == 0){
                TableRow tempTableRow = new TableRow(fragmentActivity);
                tempTableRow.setLayoutParams(new TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                tempTableRow.setPadding(5,5,5,5);
                tempTableRow.setTag("TableRow"+count/3);
                tableRows.add(tempTableRow); // if 0, 3, 6... add new tablerow
            }

            final Button button = new Button(fragmentActivity, null, android.R.attr.buttonBarButtonStyle);
            button.setTag("WorkstationRow" + count/3 + "Column" + count%3);
            button.setBackgroundResource(transparent);
            button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_workstation, 0 ,0);
            button.setText(workstation.getStation_name());
            button.setLayoutParams(new TableRow.LayoutParams(count%3));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("OnClickListener", "IN!");
                    sensorsListForWorkstation = new ArrayList<>();

                    for(Sensor sensor: sensorList){
                        if(sensor.getStation_id() == workstation.getStation_id()) {
                            sensorsListForWorkstation.add(sensor);
                        }
                    }

                    Log.d("SensorListForWorkstation", sensorsListForWorkstation.toString());

                    selectedWorkstationId = workstation.getStation_id();

                    FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    SensorsFragment sensorsFragment = new SensorsFragment();
                    ft.remove(fragmentActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_visible));
                    ft.replace(R.id.fragment_visible, sensorsFragment, "SENS_FRAG");
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();


                }
            });

            tableRows.get(tableRows.size()-1).addView(button);

            count++;

        }

        for(TableRow tr : tableRows){
            workstationTable.addView(tr);
        }

    }

}
