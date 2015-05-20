package com.quipper.exam.test.view.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quipper.exam.test.R;
import com.quipper.exam.test.view.adapter.CarouselPagerAdapter;

import butterknife.InjectView;

/**
 * Fragment which houses the View pager.
 */
public class CarouselFragment extends BaseFragment {

    @InjectView(R.id.tpi_header)
    protected com.rey.material.widget.TabPageIndicator indicator;

    @InjectView(R.id.vp_pages)
    protected ViewPager pager;

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        pager.setAdapter(new CarouselPagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        pager.setCurrentItem(currentItem);

    }

    int currentItem=0;
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        currentItem=savedInstanceState.getInt("currentItem");
    }
    @Override
    public void onSaveInstanceState2(Bundle outState) {
        currentItem=pager.getCurrentItem();
        outState.putInt("currentItem", pager.getCurrentItem());
    }

    public static CarouselFragment newInstance(){
        return new CarouselFragment();
    }

}