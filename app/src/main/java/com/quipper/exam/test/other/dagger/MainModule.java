package com.quipper.exam.test.other.dagger;

import android.content.Context;

import com.quipper.exam.test.model.businesslayer.MapManager;
import com.quipper.exam.test.other.MainApplication;
import com.quipper.exam.test.other.helper.AndroidUtils;
import com.quipper.exam.test.view.activity.BaseActivity;
import com.quipper.exam.test.view.activity.MainActivity;
import com.quipper.exam.test.view.fragment.BaseFragment;
import com.quipper.exam.test.view.fragment.MainActivityFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
        complete = false,

        injects = {
                MainApplication.class
                , BaseActivity.class
                , BaseFragment.class
                , MainActivity.class
                , MainActivityFragment.class
        }
)
public class MainModule {


    @Provides
    @Singleton
    AndroidUtils provideAndroidUtils(Context context) {
        return new AndroidUtils(context);
    }

    @Provides
    @Singleton
    MapManager provideMapManager() {
        return new MapManager();
    }


}
