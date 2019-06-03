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

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;
import com.example.prappmobcorrectviews.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.transparent;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.workstationList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkstationsFragment extends Fragment {


    // temporary list
    //private List<Workstation> workstationsListTemp;

    public WorkstationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.fragment_workstations, container, false);

        final FragmentActivity fragmentActivity = getActivity();

        if(returnView!=null){
//            workstationsListTemp = new ArrayList<>();
//
//            for(int i=0; i<8;i++){
//                workstationsListTemp.add(new Workstation(i, "WS"+i, "WS"+i+"D"));
//            }
//
//            for(int i=0; i<workstationsListTemp.size();i++) Log.d("WorkstationListTemp", workstationsListTemp.get(i).toString());


            // Inflate the layout for this fragment
            addNewWorkstationToTableView(returnView, fragmentActivity);
        }

        return returnView;

    }


    private void addNewWorkstationToTableView(View returnView, FragmentActivity fragmentActivity){


        Log.d("Method", "in!");

        // Get reference to sensorsTable
        TableLayout workstationTable = (TableLayout) returnView.findViewById(R.id.workstationTable);
        // Fill in cells


        // count workstations (0,1,2...)
        int count = 0;

        List<TableRow> tableRows = new ArrayList<>();

        for(Workstation workstation: workstationList) {


            if(count%3 == 0){
//                TableRow tr = new TableRow(getLayoutInflater().inflate(R.layout.table_row_preset, null));
//                tr.setTag("TableRow"+count/3);
//                tableRows.add(tr);

                TableRow tempTableRow = new TableRow(fragmentActivity);
                tempTableRow.setLayoutParams(new TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                tempTableRow.setPadding(5,5,5,5);
                tempTableRow.setTag("TableRow"+count/3);
                tableRows.add(tempTableRow); // if 0, 3, 6... add new tablerow
            }

            Button button = new Button(fragmentActivity, null, android.R.attr.buttonBarButtonStyle);
            button.setTag("WorkstationRow" + count/3 + "Column" + count%3);
            button.setBackgroundResource(transparent);
            button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_workstation, 0 ,0);
            button.setText(workstation.getStation_name());
            button.setLayoutParams(new TableRow.LayoutParams(count%3));

            //if(count%3==0)
            tableRows.get(tableRows.size()-1).addView(button);

            count++;

        }

        for(TableRow tr : tableRows){
            workstationTable.addView(tr);
        }

    }

}
