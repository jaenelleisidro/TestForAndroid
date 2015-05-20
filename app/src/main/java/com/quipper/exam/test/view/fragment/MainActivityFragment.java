package com.quipper.exam.test.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.other.helper.AndroidUtils;
import com.quipper.exam.test.view.activity.AnimatedMapActivity;
import com.quipper.exam.test.view.activity.MapListActivity;
import com.quipper.exam.test.view.customview.MapSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment implements View.OnClickListener{

    public static final String BUNDLEKEY_MAP = "BUNDLEKEY_MAP";
    @Inject
    AndroidUtils androidUtils;
    @Inject
    MapManager mapManager;
    @Inject
    Context appContext;
    @InjectView(R.id.load_button)
    Button loadButton;
    @InjectView(R.id.earth_image)
    ImageView earthImage;
    @InjectView(R.id.date_text)
    TextView dateText;
    @InjectView(R.id.tvCreatedByJay)
    TextView tvCreatedByJay;
    @InjectView(R.id.tvPlayAnimatedMap)
    com.rey.material.widget.Button tvPlayAnimatedMap;
    @InjectView(R.id.tvViewList)
    com.rey.material.widget.Button tvViewList;

    volatile Map map;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvPlayAnimatedMap :
                AnimatedMapActivity.start(v.getContext());
                break;
            case R.id.tvViewList :
                MapListActivity.start(v.getContext());
                break;
            case R.id.tvCreatedByJay :
                YoYo.with(Techniques.Wobble).duration(3000).playOn(v);
                break;
            case R.id.load_button :
                loadMap();
                break;
            case R.id.earth_image :
                //close loaded map
                //delete the variable to avoid loading this data on orientation change
                map = null;
                YoYo.with(Techniques.SlideOutUp)
                        .duration(1000)
                        .playOn((ViewGroup) v.getParent().getParent());
                break;

        }
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        loadButton.setOnClickListener(this);
        tvCreatedByJay.setOnClickListener(this);
        tvViewList.setOnClickListener(this);
        tvPlayAnimatedMap.setOnClickListener(this);
        if (map != null) {
            loadMap(map,false);
        }
        //androidUtils.loadFragment(this,R.id.animatedMapHolder, AnimatedMapFragment.newInstance());
    }

    //this will be called when the fragment was recovered after destroyed. this will give us a chance to recover previous datas.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        map = (Map) savedInstanceState.getSerializable(BUNDLEKEY_MAP);
    }

    /**
     * This will save the url where we need to download the image when orientation changes
     * since picasso has default caching we dont need to worry about downloading the image again, we just need the url and it will fetch the cache for us.
     */

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
        final ViewGroup parent = (ViewGroup) earthImage.getParent().getParent();
        Picasso.with(appContext)
                .load(map.imageUrl)
                .resize(600, 300)
                .into(earthImage);
        dateText.setText(map.description);
        earthImage.setOnClickListener(this);
        parent.setVisibility(View.VISIBLE);
        if(isAnimated) {
            YoYo.with(Techniques.SlideInDown)
                    .duration(1000)
                    .playOn(parent);
        }
    }


    public static Fragment newInstance(){
        return new MainActivityFragment();
    }
}
