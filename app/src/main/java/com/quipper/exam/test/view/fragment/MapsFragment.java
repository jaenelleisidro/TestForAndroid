package com.quipper.exam.test.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.view.activity.MapActivity;
import com.quipper.exam.test.view.adapter.MapsAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

public class MapsFragment extends BaseFragment {

    @Inject
    Context appContext;
    @Inject
    protected MapManager mapManager;

    @InjectView(R.id.lvListHolder)
    protected ListView lvMovies;

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simplelist, container, false);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        MapsAdapter adapter=new MapsAdapter(appContext,mapManager);
        lvMovies.setAdapter(adapter);
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map map=(Map)adapterView.getItemAtPosition(i);
                MapActivity.start(getActivity(),map);
            }
        });
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){}

    //Here you Save your data
    @Override
    public void onSaveInstanceState2(Bundle outState) {}


    public static MapsFragment newInstance(){
        return new MapsFragment();
    }
}