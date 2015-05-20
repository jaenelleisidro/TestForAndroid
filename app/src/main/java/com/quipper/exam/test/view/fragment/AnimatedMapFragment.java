package com.quipper.exam.test.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.view.customview.MapSliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Fragment which houses the View pager.
 */
public class AnimatedMapFragment extends BaseFragment {
    @InjectView(R.id.slAnimatedMap)
    SliderLayout slAnimatedMap;
    @Inject MapManager mapManager;

    public static final String BUNDLEKEY_MAP="BUNDLEKEY_MAP";
    public static final String BUNDLEKEY_MAPS="BUNDLEKEY_MAPS";


    volatile ArrayList<Map> maps;
    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animatedmap, container, false);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        if(maps==null){
            maps=mapManager.generateLatestMaps();
        }

        loadSlider(maps);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        maps=(ArrayList<Map>)savedInstanceState.getSerializable(BUNDLEKEY_MAPS);
    }

    //Here you Save your data
    @Override
    public void onSaveInstanceState2(Bundle outState) {
        outState.putSerializable(BUNDLEKEY_MAPS,maps);
    }
    /**
     * This will load the slider layout with images
     * date will be used for generating the the image links
     *
     * @param maps
     */
    private void loadSlider(List<Map> maps) {
        for (Map map : maps) {
            MapSliderView sliderView = new MapSliderView(getActivity());
            sliderView
                    .description(map.description)
                    .image(map.imageUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderView.getBundle()
                    .putSerializable(BUNDLEKEY_MAP, map);
            slAnimatedMap.addSlider(sliderView);
        }
        slAnimatedMap.setPresetTransformer(SliderLayout.Transformer.Fade);
        slAnimatedMap.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        slAnimatedMap.setCustomAnimation(new DescriptionAnimation());
        slAnimatedMap.setDuration(1000);
    }
    public static AnimatedMapFragment newInstance(){
        return newInstance(null);
    }

    public static AnimatedMapFragment newInstance(ArrayList<Map> maps){
        AnimatedMapFragment fragment =new AnimatedMapFragment();
        fragment.maps=maps;
        return fragment;
    }
}