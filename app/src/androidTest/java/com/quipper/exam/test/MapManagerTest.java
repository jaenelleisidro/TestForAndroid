package com.quipper.exam.test;

import android.test.AndroidTestCase;

import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.other.Constant;

import junit.framework.Assert;

import java.util.Date;

/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class MapManagerTest extends AndroidTestCase {
    MapManager mapManager;
    long timefrozen=1432069298136l;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mapManager=new MapManager(){
            @Override
            public long getCurrentMillis(){
                return timefrozen;
            }
        };
    }

    public void testGenerateLatestMap() throws Throwable {
        Map map=mapManager.generateLatestMap();
        Date expectedDate=new Date(timefrozen-(Constant.MINUTESDELAYOFMAPAPI *Constant.ONEMINUTEINMILLISECONDS));
        Assert.assertTrue(map.date.equals(expectedDate));
        Assert.assertTrue(map.description.equals("2015-05-19 14:00"));
        Assert.assertTrue(map.imageUrl.equals("http://www.jma.go.jp/jp/gms/imgs/5/infrared/1/201505200500-00.png"));
    }
}
