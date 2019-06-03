package com.example.prappmobcorrectviews.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sample;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.transparent;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sampleList;
import static com.example.prappmobcorrectviews.Activities.SplashActivity.sensorList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SampleFragment extends Fragment {


    public SampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View returnView = inflater.inflate(R.layout.fragment_sample, container, false);

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
            addNewSampleToTableView(returnView, fragmentActivity);
        }

        return returnView;

    }

    private void addNewSampleToTableView(View returnView, FragmentActivity fragmentActivity){

        // Get reference to sensorsTable
        TableLayout samplesTable = (TableLayout) returnView.findViewById(R.id.sampleTable);

        // Fill in cells
        // count sensors (0,1,2...)
        int count = 0;

        List<TableRow> tableRows = new ArrayList<>();

        for(Sample sample: sampleList) {

            if(count%3 == 0){
                TableRow tempTableRow = new TableRow(fragmentActivity);
                tempTableRow.setLayoutParams(new TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                tempTableRow.setPadding(5,5,5,5);
                tempTableRow.setTag("TableRow"+count/3);
                tableRows.add(tempTableRow); // if 0, 3, 6... add new tablerow
            }

            Button button = new Button(fragmentActivity, null, android.R.attr.buttonBarButtonStyle);
            button.setTag("SampleRow" + count/3 + "Column" + count%3);
            button.setBackgroundResource(transparent);
            button.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sample, 0 ,0);
            button.setText("Sample " + sample.getSample_id());
            button.setLayoutParams(new TableRow.LayoutParams(count%3));

            //if(count%3==0)
            tableRows.get(tableRows.size()-1).addView(button);

            count++;

        }

        for(TableRow tr : tableRows){
            samplesTable.addView(tr);
        }

    }


}
