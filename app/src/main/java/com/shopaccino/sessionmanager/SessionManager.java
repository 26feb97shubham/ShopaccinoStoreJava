package com.shopaccino.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private int PRIVATE_MODE = 0;
    private static final String KEY_MOBILE_TOKEN = "app_mobile_token";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String KEY_CURRENCY_ID = "currencyId";

    private static final String KEY_CURRENCY_SYMBOL = "currencySymbol";

    private static final String KEY_CURRENCY_CODE = "currencyCode";

    private static final String KEY_LANGUAGE_ID = "languageId";

    private static final String KEY_CART_SESSION_ID = "cartSessionId";

    private static final String KEY_CART_ORDER_ID = "cartOrderId";
    private static final String keyShowWarehouseSelection = "show_warehouse_selection";
    private static final String KEY_STORE_ADDRESS_ID = "storeAddressId";
    private static final String keyStoreAddressCityId = "store_address_city_id";
    private static final String keyStoreAddressCityName = "store_address_city_name";
    private static final String keyStoreAddressPincode = "store_address_pincode";

    private static final String KEY_ProductColorVariantSettings = "productColorVariantSettings";

    private static final String KEY_IS_LOGIN_WITH_MOBILE_OTP = "isLoginWithMobileOTP";

    private static final String KEY_SHOW_GSTIN = "hideGstin";

    private static final String KEY_CART_COUNT = "cartCount";
    private static final String KEY_WISHLIST_COUNT = "wishlistCount";

    private static final String KEY_IS_STORE_PICKUP = "isStorePickUp";

    private static final String KEY_IS_SERVICEABLE_AREA = "isServiceableArea";
    private static final String KEY_IS_GIFT_VOUCHER_ENABLE = "isGiftVoucherEnable";

    private static final String KEY_MULTI_CURRENCY = "isMultiCurruncy";

    private static final String KEY_HIDE_CURRENCY = "hideCurrency";
    private static final String KEY_FCM_TOKEN = "fcmToken";

    public SessionManager(Context mContext, String prefName) {
        preferences = mContext.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public void setCurrencyId(String currencyId) {
        editor.putString(KEY_CURRENCY_ID, currencyId);
        editor.commit();
    }

    public String getCurrencyId() {
        return preferences.getString(KEY_CURRENCY_ID, "0");
    }

    public void setCurrencySymbol(String currencySymbol) {
        editor.putString(KEY_CURRENCY_SYMBOL, currencySymbol);
        editor.commit();
    }

    public String getCurrencySymbol() {
        return preferences.getString(KEY_CURRENCY_SYMBOL, "");
    }

    public void setCurrencyCode(String currencyCode) {
        editor.putString(KEY_CURRENCY_CODE, currencyCode);
        editor.commit();
    }

    public String getCurrencyCode() {
        return preferences.getString(KEY_CURRENCY_CODE, "");
    }



    public void setLanguageId(String languageId) {
        editor.putString(KEY_LANGUAGE_ID, languageId);
        editor.commit();
    }

    public String getLanguageId() {
        return preferences.getString(KEY_LANGUAGE_ID, "0");
    }

    public void setCartSessionId(String cartSessionId) {
        editor.putString(KEY_CART_SESSION_ID, cartSessionId);
        editor.commit();
    }

    public String getCartSessionId() {
        return preferences.getString(KEY_CART_SESSION_ID, "");
    }

    public void setCartOrderId(String cartOrderId) {
        editor.putString(KEY_CART_ORDER_ID, cartOrderId);
        editor.commit();
    }

    public String getCartOrderId() {
        return preferences.getString(KEY_CART_ORDER_ID, "0");
    }

    public void setWarehouseSelection(final int value) {
        editor.putInt(keyShowWarehouseSelection, value);
        editor.commit();
    }

    public int getWarehouseSelection() {
        // If the address ID is not found, in that case, default returning the value as "0"
        return preferences.getInt(keyShowWarehouseSelection, 0);
    }

    public void setStoreAddressId(final String addressID) {
        editor.putString(KEY_STORE_ADDRESS_ID, addressID);
        editor.commit();
    }

    public String getStoreAddressId() {
        // If the address ID is not found, in that case, default returning the value as "0"
        return preferences.getString(KEY_STORE_ADDRESS_ID, "0");
    }

    public void setStoreAddressCityId(final String addressID) {
        editor.putString(keyStoreAddressCityId, addressID);
        editor.commit();
    }

    public String getStoreAddressCityId() {
        // If the address ID is not found, in that case, default returning the value as "0"
        return preferences.getString(keyStoreAddressCityId, "0");
    }

    public void setStoreAddressCityName(final String addressID) {
        editor.putString(keyStoreAddressCityName, addressID);
        editor.commit();
    }

    public String getStoreAddressCityName() {
        // If the address ID is not found, in that case, default returning the value as "0"
        return preferences.getString(keyStoreAddressCityName, "");
    }

    public void setStoreAddressPincode(final String addressID) {
        editor.putString(keyStoreAddressPincode, addressID);
        editor.commit();
    }

    public String getStoreAddressPincode() {
        // If the address ID is not found, in that case, default returning the value as "0"
        return preferences.getString(keyStoreAddressPincode, "");
    }

    public void setProductColorVariantSettings(boolean productColorVariantSettings) {
        editor.putBoolean(KEY_ProductColorVariantSettings, productColorVariantSettings);
        editor.commit();
    }

    public boolean getProductColorVariantSettings() {
        return preferences.getBoolean(KEY_ProductColorVariantSettings, false);
    }

    public void setAppMobileToken(String currencyId) {
        editor.putString(KEY_MOBILE_TOKEN, currencyId);
        editor.commit();
    }

    public String getAppMobileToken() {
        return preferences.getString(KEY_MOBILE_TOKEN, "");
    }

    public void setLoginWithMobileOtp(boolean isLoginWithMobileOTP) {
        editor.putBoolean(KEY_IS_LOGIN_WITH_MOBILE_OTP, isLoginWithMobileOTP);
        editor.commit();
    }

    public boolean isLoginWithMobileOTP() {
        return preferences.getBoolean(KEY_IS_LOGIN_WITH_MOBILE_OTP, false);
    }

    public void setShowGstin(boolean isShowGstin) {

        editor.putBoolean(KEY_SHOW_GSTIN, isShowGstin);

        // commit changes
        editor.commit();

    }

    public boolean isShowGstin() {
        return preferences.getBoolean(KEY_SHOW_GSTIN, false);
    }

    public void setCartCount(String cartCount) {
        editor.putString(KEY_CART_COUNT, cartCount);
        editor.commit();
    }

    public String getCartCount() {
        return preferences.getString(KEY_CART_COUNT, "0");
    }

    public void setWishlistCount(String cartCount) {
        editor.putString(KEY_WISHLIST_COUNT, cartCount);
        editor.commit();
    }

    public String getWishlistCount() {
        return preferences.getString(KEY_WISHLIST_COUNT, "0");
    }

    public void setStorePickUp(boolean isStorePickUp) {

        editor.putBoolean(KEY_IS_STORE_PICKUP, isStorePickUp);

        // commit changes
        editor.commit();
    }

    public boolean isStorePickUp() {
        return preferences.getBoolean(KEY_IS_STORE_PICKUP, false);
    }

    public void setServiceableArea(boolean isServiceableArea) {
        editor.putBoolean(KEY_IS_SERVICEABLE_AREA, isServiceableArea);
        editor.commit();
    }

    public boolean isServiceableArea() {
        return preferences.getBoolean(KEY_IS_SERVICEABLE_AREA, false);
    }

    public void setGiftVoucherEnable(boolean isGiftVoucherEnable) {
        editor.putBoolean(KEY_IS_GIFT_VOUCHER_ENABLE, isGiftVoucherEnable);
        editor.commit();
    }

    public boolean isGiftVoucherEnable() {
        return preferences.getBoolean(KEY_IS_GIFT_VOUCHER_ENABLE, false);
    }

    public void setMultiCurrency(boolean isMultiCurrency) {

        editor.putBoolean(KEY_MULTI_CURRENCY, isMultiCurrency);

        // commit changes
        editor.commit();

    }

    public boolean isMultiCurrency() {
        return preferences.getBoolean(KEY_MULTI_CURRENCY, false);
    }

    public void setHideCurrency(boolean isHideCurrency) {

        editor.putBoolean(KEY_HIDE_CURRENCY, isHideCurrency);

        // commit changes
        editor.commit();

    }

    public boolean isHideCurrency() {
        return preferences.getBoolean(KEY_HIDE_CURRENCY, true);
    }

    public void setFCMToken(String value) {
        editor.putString(KEY_FCM_TOKEN, value);
        editor.commit();
    }

    public String getFCMToken() {
        return preferences.getString(KEY_FCM_TOKEN, "");
    }

}
