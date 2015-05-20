package com.quipper.exam.test.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.view.activity.AnimatedMapActivity;
import com.quipper.exam.test.view.activity.MapListActivity;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Fragment which houses the View pager.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    @Inject
    Context appContext;
    @Inject
    MapManager mapManager;
    @InjectView(R.id.load_button)
    Button loadButton;
    @InjectView(R.id.play_button)
    Button playButton;
    @InjectView(R.id.list_button)
    Button listButton;
    @InjectView(R.id.imgMapToBeLoaded)
    ImageView imgMapToBeLoaded;
    public static final String BUNDLEKEY_MAP = "BUNDLEKEY_MAP";

    private volatile Map map;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.load_button:
                loadMap();
                break;
            case R.id.play_button:
                AnimatedMapActivity.start(getActivity());
                break;
            case R.id.list_button:
                MapListActivity.start(getActivity());
                break;
        }
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        if (map != null) {
            loadMap(map,false);
        }
        loadButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        map = (Map) savedInstanceState.getSerializable(BUNDLEKEY_MAP);
    }

    //Here you Save your data
    @Override
    public void onSaveInstanceState2(Bundle outState) {
        if (map != null) {
            outState.putSerializable(BUNDLEKEY_MAP, map);
        }
    }
    /**
     * This will load the latest map image, if it's already downloaded before, it will use the cache.
     */

    private void loadMap() {
        map = mapManager.generateLatestMap();
        loadMap(map,true);
    }

    private void loadMap(Map map,boolean isAnimated) {
        Picasso.with(appContext)
                .load(map.imageUrl)
                .resize(600, 300)
                .into(imgMapToBeLoaded);
//        dateText.setText(map.description);
        if(isAnimated) {
            YoYo.with(Techniques.SlideInDown)
                    .duration(1000)
                    .playOn(imgMapToBeLoaded);
        }
    }


    public static Fragment newInstance(int layoutId){
        return new HomeFragment();
    }
}