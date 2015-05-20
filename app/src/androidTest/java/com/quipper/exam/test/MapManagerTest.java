package com.quipper.exam.test;

import android.test.AndroidTestCase;

import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.other.Constant;

import junit.framework.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class MapManagerTest extends AndroidTestCase {
    MapManager mapManager;
    long timefrozen=1432069298136l;
    Date expectedDate;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mapManager=new MapManager(){
            @Override
            public long getCurrentMillis(){
                return timefrozen;
            }
        };
        expectedDate=new Date(timefrozen-(Constant.MINUTESDELAYOFMAPAPI *Constant.ONEMINUTEINMILLISECONDS));
    }

    private void testIfLatestMap(Map map){
        Assert.assertTrue(map.date.equals(expectedDate));
        Assert.assertTrue(map.description.equals("2015-05-19 14:00"));
        Assert.assertTrue(map.imageUrl.equals("http://www.jma.go.jp/jp/gms/imgs/5/infrared/1/201505200500-00.png"));
    }

    public void testGenerateMapForMinutesAgo() throws Throwable {
        Map map=mapManager.generateMapForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI);
        testIfLatestMap(map);
    }

    public void testGenerateLatestMap() throws Throwable {
        Map map=mapManager.generateLatestMap();
        testIfLatestMap(map);
    }


    public void testGenerateLatestMaps() throws Throwable {
        List<Map> maps=mapManager.generateLatestMaps();
        testIfLatestMap(maps.get(0));
        for(int index=0;index<maps.size();index++){
            Map map=maps.get(index);
            Map map2=mapManager.generateMapForMinutesAgo(Constant.MINUTESDELAYOFMAPAPI+(Constant.SLIDERMAP_MINUTESINTERVAL*index));
            Assert.assertTrue(map.equals(map2));
        }
    }


}
