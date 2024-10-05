package com.shopaccino.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopaccino.R;
import com.shopaccino.adapter.CouponsAdapter;
import com.shopaccino.config.AppConfig;
import com.shopaccino.databinding.ActivityCouponsBinding;
import com.shopaccino.databinding.CouponDetailsLayoutBinding;
import com.shopaccino.databinding.CouponDialogLayoutBinding;
import com.shopaccino.network.NetworkClass;
import com.shopaccino.utils.SafeClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;

public class CouponsActivity extends AppCompatActivity {
    private ActivityCouponsBinding couponsBinding;
    private JsonArray couponsJsonArray = new JsonArray();

    private CouponsAdapter couponsAdapter;

    EmitterConfig emitterConfig = new Emitter(100, TimeUnit.MILLISECONDS).max(100);
    Party party =
            new PartyFactory(emitterConfig)
                    .setSpeed(0f)
                    .setSpeedBetween(0f, 30f)
                    .spread(360)
                    .setDamping(0.9f)
                    .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                    .position(new Position.Relative(0.5f, 0.3f))
                    .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        couponsBinding = ActivityCouponsBinding.inflate(getLayoutInflater());
        setContentView(couponsBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setAdapter();
    }

    private void setAdapter() {
        couponsAdapter = new CouponsAdapter(new CouponsAdapter.OnCouponClickListener() {
            @Override
            public void onCouponClick(JsonObject couponsJsonObject, boolean isTerms) {
                if (isTerms) {
                    showTermsAndConditionsDialog(couponsJsonObject);
                } else {
                    couponsBinding.konfettiView.start(party = party);
                    showDialog(couponsJsonObject);
                }
            }
        });
        couponsAdapter.setData(couponsJsonArray);
        couponsBinding.rvCoupons.setAdapter(couponsAdapter);
    }

    private void showTermsAndConditionsDialog(JsonObject couponsJsonObject) {
        Dialog dialog = new Dialog(this);
        CouponDetailsLayoutBinding dialogBinding = CouponDetailsLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        // Setting coupon details
        dialogBinding.tvCouponTitle.setText(couponsJsonObject.get("coupon_title").getAsString());
        dialogBinding.tvCouponCode.setText(couponsJsonObject.get("coupon_code").getAsString().toUpperCase());


        // Use Html.fromHtml to convert HTML to Spanned
        CharSequence spanned = Html.fromHtml(couponsJsonObject.get("short_description").getAsString(), Html.FROM_HTML_MODE_LEGACY);
        dialogBinding.tvCouponDesc.setText(spanned);

        dialogBinding.tvValidTill.setText("Valid till " + couponsJsonObject.get("expiry_date").getAsString());

        // Set click listener for apply button
        dialogBinding.btnApply.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                dialog.dismiss();
                couponsBinding.konfettiView.start(party);  // Assuming `party` is defined elsewhere
                showDialog(couponsJsonObject);
            }
        });

        // Set layout params
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;  // or a specific size
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;   // or a specific size
        dialog.getWindow().setAttributes(layoutParams);

        // Make dialog non-cancelable
        dialog.setCancelable(false);
        dialog.show();

        // Set dialog background if available
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.terms_and_conditions_dialog_bg);
        }
    }

    private void showDialog(JsonObject couponsJsonObject) {
        Dialog dialog = new Dialog(this);
        CouponDialogLayoutBinding dialogBinding = CouponDialogLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.tvCouponCode.setText(couponsJsonObject.get("coupon_code").getAsString().toUpperCase() + " applied");
        dialogBinding.tvCouponDescription.setText("₹ " + "" + " savings with this coupon");

        dialogBinding.viewClose.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                dialog.dismiss();

            }
        });

        dialogBinding.tvYay.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View v) {
                dialog.dismiss();
            }
        });

        // Set layout params
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;  // or a specific size
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // or a specific size
        dialog.getWindow().setAttributes(layoutParams);

        dialog.setCancelable(false);
        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callCouponsApi();
    }

    private void callCouponsApi() {
        JSONObject params = AppConfig.getDefaultJsonObject(this);
        NetworkClass.createRequest(this, AppConfig.GET_COUPONS, params, new NetworkClass.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                if (jsonObject.has("response")) {
                    JsonObject responseJsonObject = jsonObject.getAsJsonObject("response");
                    boolean success = responseJsonObject.get("success").getAsBoolean();
                    if (success) {
                        if (responseJsonObject.has("data")) {
                            if (responseJsonObject.get("data").isJsonObject()) {
                                JsonObject myJsonObject = responseJsonObject.getAsJsonObject("data");
                                if (myJsonObject.has("store_coupons")) {
                                    JsonArray couponsArray = myJsonObject.getAsJsonArray("store_coupons");
                                    if (!couponsArray.isEmpty()) {
                                        couponsArray.forEach(item -> {
                                            JsonObject jsonObj1 = item.getAsJsonObject();
                                            JsonObject couponJsonObject = new JsonObject();
                                            couponJsonObject.addProperty(
                                                    "coupon_code",
                                                    jsonObj1.get("coupon_code").getAsString()
                                            );
                                            couponJsonObject.addProperty(
                                                    "expiry_date",
                                                    jsonObj1.get("expiry_date").getAsString()
                                            );
                                            couponJsonObject.addProperty(
                                                    "coupon_title",
                                                    jsonObj1.get("coupon_title").getAsString()
                                            );
                                            couponJsonObject.addProperty(
                                                    "short_description",
                                                    jsonObj1.get("short_description").getAsString()
                                            );
                                            couponJsonObject.addProperty(
                                                    "terms_and_conditions",
                                                    jsonObj1.get("terms_and_conditions").getAsString()
                                            );
                                            couponsJsonArray.add(couponJsonObject);
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, new NetworkClass.VolleyErrorCallback() {
            @Override
            public void onError(String error) {
            }
        });
    }
}