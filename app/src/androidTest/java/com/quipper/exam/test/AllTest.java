package com.quipper.exam.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Created by jaenelleisidro on 5/20/15.
 */
public class AllTest extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(AllTest.class).includeAllPackagesUnderHere().build();
    }
}