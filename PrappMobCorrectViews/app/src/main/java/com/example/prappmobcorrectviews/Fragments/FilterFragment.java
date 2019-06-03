package com.example.prappmobcorrectviews.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.prappmobcorrectviews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends ListFragment {

    String[] filters = new String[]{
            "Date from: ",
            "Date to: ",
            "Workstation: ",
            "Sensor: "
    };


//    public FilterFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        final FragmentActivity fragmentActivity = getActivity();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragmentActivity,
                android.R.layout.simple_list_item_1, filters);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id){
        /*
         DetailsFragment txt = (DetailsFragment)getFragmentManager().findFragmentById(R.id.fragment2);
         txt.change("Name: "+ users[position],"Location : "+ location[position]);
         getListView().setSelector(android.R.color.holo_blue_dark);
         */
    }

}
