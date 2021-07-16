package com.syte.ai.android_sdk.util;

import android.util.Log;

public class SyteLogger {

    public enum LogLevel {
        VERBOSE, INFO, DEBUG, WARN, ERROR;
    }

    private static LogLevel mLogLevel = LogLevel.VERBOSE;

    private static String appendTag(String TAG) {
        return ":SYTE:" + TAG;
    }

    public static void setLogLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
    }

    public static LogLevel getLogLevel() {
        return mLogLevel;
    }

    public static void v(String TAG, String message) {
        if (mLogLevel.ordinal() > LogLevel.VERBOSE.ordinal()) {
            return;
        }
        Log.i(appendTag(TAG), message);
    }

    public static void i(String TAG, String message) {
        if (mLogLevel.ordinal() > LogLevel.INFO.ordinal()) {
            return;
        }
        Log.i(appendTag(TAG), message);
    }

    public static void d(String TAG, String message) {
        if (mLogLevel.ordinal() > LogLevel.DEBUG.ordinal()) {
            return;
        }
        Log.i(appendTag(TAG), message);
    }

    public static void w(String TAG, String message) {
        if (mLogLevel.ordinal() > LogLevel.WARN.ordinal()) {
            return;
        }
        Log.i(appendTag(TAG), message);
    }

    public static void e(String TAG, String message) {
        Log.i(appendTag(TAG), message);
    }

}
