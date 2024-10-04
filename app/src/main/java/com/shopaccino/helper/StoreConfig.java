package com.shopaccino.helper;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class StoreConfig {
    private static String appName = "";
    private static String appToken = "";
    private static String domainUrl = "";
    private static String sessionName = "";
    private static String dbName = "";
    private static String storeId = "";

    private static Drawable loginDrawable = null;
    private static Drawable toolbarDrawable = null;
    private static int notificationDrawable = 0;
    private static int primaryColor = 0;


    public static void setAppName(@NonNull final String value) {
        appName = value;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppToken(@NonNull final String value) {
        appToken = value;
    }

    public static String getAppToken() {
        return appToken;
    }

    public static void setDomainUrl(@NonNull final String value) {
        domainUrl = value;
    }

    public static String getDomainUrl() {
        return domainUrl;
    }

    public static void setSessionName(@NonNull final String value) {
        sessionName = value;
    }

    public static String getSessionName() {
        return sessionName;
    }

    public static void setDbName(@NonNull final String value) {
        dbName = value;
    }

    public static String getDbName() {
        return dbName;
    }

    public static Drawable getLoginDrawable() {
        return loginDrawable;
    }

    public static void setLoginDrawable(Drawable loginDrawable) {
        StoreConfig.loginDrawable = loginDrawable;
    }

    public static Drawable getToolbarDrawable() {
        return toolbarDrawable;
    }

    public static void setToolbarDrawable(Drawable toolbarDrawable) {
        StoreConfig.toolbarDrawable = toolbarDrawable;
    }

    public static int getNotificationDrawable() {
        return notificationDrawable;
    }

    public static void setNotificationDrawable(int toolbarDrawable) {
        StoreConfig.notificationDrawable = toolbarDrawable;
    }

    public static String getStoreId() {
        return storeId;
    }

    public static void setStoreId(String storeId) {
        StoreConfig.storeId = storeId;
    }

    public static int getPrimaryColor() {
        return primaryColor;
    }

    public static void setPrimaryColor(int primaryColor) {
        StoreConfig.primaryColor = primaryColor;
    }
}

