

package com.quipper.exam.test.view.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.quipper.exam.test.view.fragment.AnimatedMapFragment;
import com.quipper.exam.test.view.fragment.MainActivityFragment;
import com.quipper.exam.test.view.fragment.MapsFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * Pager adapter
 */
public class CarouselPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    List<FragmentGenerate> list=new ArrayList<FragmentGenerate>();
    public CarouselPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
        list.add(new FragmentGenerate() {
            @Override
            public Fragment newInstance() {
                return MainActivityFragment.newInstance();
            }
            @Override
            public String instanceName() {
                return "Home";
            }
        });
        list.add(new FragmentGenerate() {
            @Override
            public Fragment newInstance() {

                return MapsFragment.newInstance();
            }
            @Override
            public String instanceName() {
                return "Map List";
            }
        });

    }


    private interface FragmentGenerate{
        abstract public Fragment newInstance();
        abstract public String instanceName();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(final int position) {
        return list.get(position).newInstance();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return list.get(position).instanceName();
    }
}
