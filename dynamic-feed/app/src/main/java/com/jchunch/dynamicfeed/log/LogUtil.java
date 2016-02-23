package com.jchunch.dynamicfeed.log;

import android.util.Log;

import com.jchunch.dynamicfeed.BuildConfig;

/**
 * Created by jchunch on 2/22/16.
 */
public class LogUtil {

    public static void d (String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e (String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void v (String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void w (String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
        }
    }
}
