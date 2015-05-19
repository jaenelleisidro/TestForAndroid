package com.quipper.exam.test.view.fragment;

import android.os.Bundle;
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
import com.quipper.exam.test.view.customview.MapSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    public static final String BUNDLEKEY_MAP = "BUNDLEKEY_MAP";
    @Inject
    AndroidUtils androidUtils;
    @Inject
    MapManager mapManager;
    @InjectView(R.id.load_button)
    Button loadButton;
    @InjectView(R.id.earth_image)
    ImageView earthImage;
    @InjectView(R.id.date_text)
    TextView dateText;
    @InjectView(R.id.tvCreatedByJay)
    TextView tvCreatedByJay;
    View.OnClickListener tvCreatedByJayOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YoYo.with(Techniques.Wobble).duration(3000).playOn(tvCreatedByJay);
        }
    };
    @InjectView(R.id.slider)
    SliderLayout mSlider;
    volatile Map map;
    //a listener to close loaded map
    View.OnClickListener closeEarthClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //delete the variable to avoid loading this data on orientation change
            map = null;
            YoYo.with(Techniques.SlideOutUp)
                    .duration(1000)
                    .playOn((ViewGroup) earthImage.getParent().getParent());
        }
    };
    View.OnClickListener loadButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadMap();
        }
    };

    @Override
    public View onCreateView2(LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        loadSlider();
        loadButton.setOnClickListener(loadButtonOnClick);
        tvCreatedByJay.setOnClickListener(tvCreatedByJayOnClick);
        tvCreatedByJayOnClick.onClick(tvCreatedByJay);
        if (map != null) {
            loadMap(map);
        }
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
     * starts the backgroud map that animates
     */
    private void loadSlider() {
        List<Map> maps = mapManager.generateLatestMaps();
        loadSlider(maps);
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
            mSlider.addSlider(sliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(1000);
    }

    /**
     * This will load the latest map image, if it's already downloaded before, it will use the cache.
     */

    private void loadMap() {
        map = mapManager.generateLatestMap();
        loadMap(map);
    }

    private void loadMap(Map map) {
        Picasso.with(getActivity())
                .load(map.imageUrl)
                .into(earthImage);
        dateText.setText(map.description);
        earthImage.setOnClickListener(closeEarthClickListener);
        final ViewGroup parent = (ViewGroup) earthImage.getParent().getParent();
        YoYo.with(Techniques.SlideInDown)
                .duration(1000)
                .playOn(parent);
        parent.setVisibility(View.VISIBLE);
    }

}
