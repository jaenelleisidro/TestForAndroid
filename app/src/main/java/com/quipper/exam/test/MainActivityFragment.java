package com.quipper.exam.test;

import android.support.v4.app.Fragment;
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
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.nineoldandroids.animation.Animator;
import com.quipper.exam.test.view.MapSliderView;
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
    private TextView tvCreatedByJay;
    private SliderLayout mSlider;
    private static final int CONSTANT_ONESECONDINMILLISECONDS=1000;
    private static final int CONSTANT_ONEMINUTEINMILLISECONDS=CONSTANT_ONESECONDINMILLISECONDS*60;

    private static final int CONSTANT_MINUTESDELAYOFMAPAPI=30;
    private static final int SLIDERMAP_NUMBEROFMAPS=5;
    private static final int SLIDERMAP_MINUTESINTERVAL=60;

    private static final String CONSTANT_MAPAPIWEBSITE ="http://www.jma.go.jp";
    private static final String CONSTANT_MAPAPIIMAGE =CONSTANT_MAPAPIWEBSITE+"/jp/gms/imgs/5/infrared/1/%s00-00.png";
    public static final String BUNDLEKEY_IMAGEURL="imageUrl";
    public static final String BUNDLEKEY_IMAGEDATE="imageDate";

    private static final SimpleDateFormat IMAGE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHH", Locale.US);
    private static final SimpleDateFormat LABEL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.US);
    String imageUrl;
    String imageDate;
    static {
        TimeZone jst = TimeZone.getTimeZone("GMT+09:00");
        IMAGE_TIME_FORMAT.setTimeZone(jst);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loadButton = (Button) view.findViewById(R.id.load_button);
        earthImage = (ImageView) view.findViewById(R.id.earth_image);
        dateText = (TextView) view.findViewById(R.id.date_text);
        tvCreatedByJay=(TextView) view.findViewById(R.id.tvCreatedByJay);

        mSlider=(SliderLayout)view.findViewById(R.id.slider);
        loadSlider();
        loadButton.setOnClickListener(this);

        tvCreatedByJay.setOnClickListener(tvCreatedByJayOnClick);
        tvCreatedByJay.callOnClick();
        return view;
    }
    View.OnClickListener tvCreatedByJayOnClick=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            YoYo.with(Techniques.Wobble).duration(3000).playOn(tvCreatedByJay);
        }
    };


    @Override
    public void onClick(View v) {
        Date dateToShow = generateDateForMinutesAgo(CONSTANT_MINUTESDELAYOFMAPAPI);
        imageUrl = generateMapImageUrlFromDate(dateToShow);
        imageDate=LABEL_FORMAT.format(dateToShow);
        loadMap(imageUrl, imageDate);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(BUNDLEKEY_IMAGEURL) && savedInstanceState.containsKey(BUNDLEKEY_IMAGEDATE)){
                imageUrl=savedInstanceState.getString(BUNDLEKEY_IMAGEURL);
                imageDate=savedInstanceState.getString(BUNDLEKEY_IMAGEDATE);
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
        if(imageUrl!=null) {
            outState.putString(BUNDLEKEY_IMAGEURL, imageUrl);
        }
        if(imageDate!=null) {
            outState.putString(BUNDLEKEY_IMAGEDATE,imageDate);
        }

    }

    /**
     * Generate a date object from current time minus minutes ago
     * @param minutes
     * @return
     */
    private Date generateDateForMinutesAgo(int minutes){
        Date dateToShow = new Date(System.currentTimeMillis() - minutes * CONSTANT_ONEMINUTEINMILLISECONDS);
        return dateToShow;
    }

    /**
     * generate a valid map image url based on api
     * @param date the date of the map image
     * @return
     */
    private String generateMapImageUrlFromDate(Date date){
        String imageUrl = String.format(CONSTANT_MAPAPIIMAGE,IMAGE_TIME_FORMAT.format(date));
        return imageUrl;
    }

    /**
     * starts the backgroud map that animates
     */
    private void loadSlider(){
        loadSlider(SLIDERMAP_NUMBEROFMAPS,SLIDERMAP_MINUTESINTERVAL);
    }

    private void loadSlider(int numberOfmaps,int minutesInterval){
        List<Date> dates=new ArrayList<Date>();
        for(int index=0;index<numberOfmaps;index++){
            Date date=generateDateForMinutesAgo(CONSTANT_MINUTESDELAYOFMAPAPI+(minutesInterval*index));
            dates.add(date);
        }
        loadSlider(dates);
    }


    /**
     * This will load the slider layout with images
     * date will be used for generating the the image links
     * @param dates of images
     */
    private void loadSlider(List<Date> dates){


        for(Date date : dates){
            MapSliderView sliderView = new MapSliderView(getActivity());

            //DefaultSliderView sliderView=new DefaultSliderView(getActivity());
            String url=generateMapImageUrlFromDate(date);
            sliderView
                    .description(LABEL_FORMAT.format(date))
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderView.getBundle()
                    .putString("url",url);
            mSlider.addSlider(sliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(1000);
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
        earthImage.setOnClickListener(closeEarthClickListener);
        final ViewGroup parent=(ViewGroup) earthImage.getParent().getParent();
        YoYo.with(Techniques.SlideInDown)
                .duration(1000)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(parent);
        parent.setVisibility(View.VISIBLE);
    }

    //a listener to close loaded map
    View.OnClickListener closeEarthClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //delete the following variable to avoid loading this data on orientation change
            imageUrl=null;
            imageDate=null;

            YoYo.with(Techniques.SlideOutUp)
                    .duration(1000)
                    .playOn((ViewGroup) earthImage.getParent().getParent());
        }
    };

}
