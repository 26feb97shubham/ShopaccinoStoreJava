package com.shopaccino.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shopaccino.R;
import com.shopaccino.config.AppConfig;
import com.shopaccino.databinding.ActivitySplashBinding;
import com.shopaccino.helper.StoreConfig;
import com.shopaccino.network.NetworkClass;
import com.shopaccino.sessionmanager.SessionManager;

import org.json.JSONObject;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private Context mContext;
    private SessionManager session;
    private ActivitySplashBinding activitySplashBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mContext = this;
        session = new SessionManager(mContext, StoreConfig.getSessionName());
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());

        setContentView(activitySplashBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getStartData();
    }

    private void getStartData() {
        NetworkClass.createRequest(this, AppConfig.START_URL, AppConfig.getDefaultJsonObject(this), new NetworkClass.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject responseObj = jObj.getJSONObject("response");
                    if (responseObj.getBoolean("success")) {
                        JSONObject dataObj = responseObj.getJSONObject("data");
                        JSONObject storeObj = dataObj.getJSONObject("store_info");
                        session.setCurrencyId(storeObj.getString("currency_id"));
                        session.setCurrencyCode(storeObj.getString("currency_code"));
                        session.setCurrencySymbol(storeObj.getString("currency_symbol"));
                        session.setLanguageId(storeObj.getString("language_id"));
                        // Storing the received pin code and store address Id.
                        if (dataObj.has("store_address_info")) {
                            JSONObject storeAddressJSONObject = dataObj.getJSONObject("store_address_info");

                            if (storeAddressJSONObject.has("id")) {
                                session.setStoreAddressId(storeAddressJSONObject.getString("id"));
                            }

                            if (storeAddressJSONObject.has("pincode")) {
                                session.setStoreAddressPincode(storeAddressJSONObject.getString("pincode"));
                            }

                            if (storeAddressJSONObject.has("city_name")) {
                                session.setStoreAddressCityName(storeAddressJSONObject.getString("city_name"));
                            }

                            if (storeAddressJSONObject.has("city_id")) {
                                session.setStoreAddressCityId(storeAddressJSONObject.getString("city_id"));
                            }
                        }

                        session.setServiceableArea(storeObj.getBoolean("is_servicable_area"));

                        JSONObject tempObj = dataObj.getJSONObject("template_setting_info");
                        StoreConfig.setStoreId(tempObj.getString("store_id"));
                        session.setProductColorVariantSettings(tempObj.getBoolean("product_color_variant_settings"));
                        session.setLoginWithMobileOtp(tempObj.getBoolean("show_mobile_checkout"));
                        String gstin = tempObj.getString("show_company_name_gstin");
                        if (gstin.equals("1")) {
                            session.setShowGstin(true);
                        } else {
                            session.setShowGstin(false);
                        }
                        session.setStorePickUp(tempObj.getBoolean("show_store_pickup"));
                        session.setGiftVoucherEnable(storeObj.getString("gift_card_voucher").equals("1"));

                        session.setWarehouseSelection(tempObj.getInt("show_warehouse_selection"));
                        session.setMultiCurrency(tempObj.getBoolean("show_multi_currency"));
                        String currency = tempObj.getString("hide_multi_currency_dropdown");
                        if (currency.equals("1")) {
                            session.setHideCurrency(true);
                        } else {
                            session.setHideCurrency(false);
                        }

                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    } else {

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new NetworkClass.VolleyErrorCallback() {
            @Override
            public void onError(String error) {
            }
        });
    }
}