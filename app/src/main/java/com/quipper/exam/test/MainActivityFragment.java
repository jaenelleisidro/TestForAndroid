package com.quipper.exam.test;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private Button loadButton;
    private ImageView earthImage;
    private TextView dateText;
    private SliderLayout mSlider;
    private static final int CONSTANT_MINUTESDELAYOFMAPAPI=30;
    private static final SimpleDateFormat IMAGE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHH", Locale.US);
    private static final SimpleDateFormat LABEL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.US);
    static {
        TimeZone jst = TimeZone.getTimeZone("GMT+09:00");
        IMAGE_TIME_FORMAT.setTimeZone(jst);
    }
    String imageUrl;
    String imageDate;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loadButton = (Button) view.findViewById(R.id.load_button);
        earthImage = (ImageView) view.findViewById(R.id.earth_image);
        earthImage.setVisibility(View.GONE);
        dateText = (TextView) view.findViewById(R.id.date_text);
        dateText.setVisibility(View.GONE);

        mSlider=(SliderLayout)view.findViewById(R.id.slider);
        loadSlider();
        loadButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Date dateToShow = generateDateForMinutesAgo(CONSTANT_MINUTESDELAYOFMAPAPI);
        imageUrl = generateMapImageUrlFromDate(dateToShow);
        imageDate=LABEL_FORMAT.format(dateToShow);
        loadMap(imageUrl, imageDate);
    }
    /**
     * This will load the map image from url, if it's already downloaded before, it will use the cache.
     * @param imageUrl url of the map image
     * @param imageDate  date of the map image
     */
    private void loadMap(String imageUrl,String imageDate){
        Picasso.with(getActivity())
                .load(imageUrl)
                .into(earthImage);
        dateText.setText(imageDate);
        earthImage.setVisibility(View.VISIBLE);
        dateText.setVisibility(View.VISIBLE);
        earthImage.setOnClickListener(closeEarthClickListener);
    }

    View.OnClickListener closeEarthClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            earthImage.setVisibility(View.GONE);
            dateText.setVisibility(View.GONE);
        }
    };



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey("imageUrl") && savedInstanceState.containsKey("imageDate")){
                imageUrl=savedInstanceState.getString("imageUrl");
                imageDate=savedInstanceState.getString("imageDate");
                loadMap(imageUrl,imageDate);
            }

        }
    }

    /**
     * This will save the url where we need to download the image when orientation changes
     * since picasso has default caching we dont need to worry about downloading the image again, we just need the url and it will fetch the cache for us.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imageUrl",imageUrl);
        outState.putString("imageDate",imageDate);
    }

    private Date generateDateForMinutesAgo(int minutes){
        Date dateToShow = new Date(System.currentTimeMillis() - minutes * 60 * 1000);
        return dateToShow;
    }

    private String generateMapImageUrlFromDate(Date date){
        String imageUrl = String.format("http://www.jma.go.jp/jp/gms/imgs/5/infrared/1/%s00-00.png",IMAGE_TIME_FORMAT.format(date));
        return imageUrl;
    }
    private void loadSlider(){
        loadSlider(5,60);
    }

    private void loadSlider(int numberOfmaps,int minutesInterval){
        List<String> urls=new ArrayList<>();
        for(int index=0;index<numberOfmaps;index++){
            Date date=generateDateForMinutesAgo(CONSTANT_MINUTESDELAYOFMAPAPI+(minutesInterval*index));
            urls.add(generateMapImageUrlFromDate(date));
        }
        loadSlider(urls);
    }


    private void loadSlider(List<String> urls){

        for(String url : urls){
            DefaultSliderView defaultSliderView=new DefaultSliderView(getActivity());
            defaultSliderView.image(url);
            // initialize a SliderLayout

            defaultSliderView
//                    .description(name)
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);
            //add your extra information
            defaultSliderView.getBundle()
                    .putString("url",url);
            mSlider.addSlider(defaultSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(2000);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSlider.refreshDrawableState();

    }
}
