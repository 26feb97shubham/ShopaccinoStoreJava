package com.shopaccino.config;

import static com.shopaccino.sessionmanager.SessionManager.KEY_FCM_TOKEN;

import android.content.Context;

import com.shopaccino.helper.SQLiteHandler;
import com.shopaccino.helper.StoreConfig;
import com.shopaccino.sessionmanager.SessionManager;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class AppConfig {
    public static final String BASE_URL = "https://rome.shopaccino.net/";

    //Home Page Element
    public final static String SLIDESHOWS = "slideshows";
    public final static String FEATURED_CATEGORIES = "featured_categories";
    public final static String IMAGE_WITH_TEXT_OVERLAY = "image_with_text_overlay";
    public final static String GALLERY = "gallery";
    public final static String FEATURED_PRODUCTS = "featured_products";
    public final static String TESTIMONIALS = "testimonials";

    // Product Detail Page Elements
    public final static String PRODUCT_SUMMERY = "product_summary";
    public final static String PRODUCT_SUMMERY_APPEND = "product_summary_append";
    public final static String PRODUCT_DESCRIPTION = "product_description";
    public final static String PRODUCT_DESCRIPTION_APPEND = "product_description_append";
    public final static String PRODUCT_ATTRIBUTES = "product_key_attributes";
    public final static String PRODUCT_MANUFACTURER = "product_manufacturer_packer";
    public final static String PRODUCT_UPSELL = "product_upsell";

    public static String START_URL = "stores/rest_get_start.json";
    public static String GET_STORE_WAREHOUSES = "stores/rest_get_store_warehouses.json";
    public static String GET_STORE_WAREHOUSE_INFO = "stores/rest_get_store_warehouse_info.json";
    public static String HOME_URL = "stores/rest_get_home.json";
    public static String GET_CURRENCY_LIST = "store_currencies/rest_currencies_list.json";
    public static String UPDATE_CURRENCY = "store_currencies/rest_currency.json";
    public static String GET_ALL_CATEGORIES = "stores/rest_get_categories_by_id.json";
    public static String GET_PRODUCTS_CATEGORY_WISE = "store_categories/rest_get_products_category_wise.json";

    public static String GET_PRODUCT_BY_ID = "store_products/rest_get_product_by_id.json";
    public static String GET_PRODUCT_OPTION_VALUES = "store_products/rest_get_product_option_values.json";
    public static String GET_PRODUCT_DESCRIPTION = "store_products/rest_get_product_description.json";
    public static String GET_PRODUCT_SECTION = "store_products/rest_get_product_sections.json";
    public static String GET_RELATED_PRODUCT = "store_products/rest_get_related_products.json";
    public static String GET_RECENT_PRODUCT = "store_products/rest_get_recently_viewed_products.json";
    public static String GET_PRODUCT_REVIEWS = "store_products/rest_get_reviews.json";
    public static String ADD_REVIEW = "store_products/rest_product_review.json";

    public static String CHECK_DELIVERY = "store_orders/rest_check_delivery_cod_available.json";

    public static String LOGIN_URL = "customers/rest_login.json";
    public static String LOGIN_SEND_OTP = "customers/rest_send_otp.json";
    public static String LOGIN_VERIFY_OTP = "customers/rest_verify_otp.json";
    public static String GET_BILLING_COUNTRY_STATE = "stores/rest_get_countries_states_cities.json";
    public static String REGISTER_URL = "customers/rest_register.json";

    public static String RESET_PASSWORD = "customers/rest_forgot_password.json";

    public static String ADD_WISHLIST = "store_products/rest_add_wishlist.json";
    public static String REMOVE_WISHLIST = "store_products/rest_remove_wishlist.json";

    public static String SEARCH_TAG = "store_tags/rest_tags_by_keyword.json";

    public static String SEARCH_RESULT = "store_tags/rest_products_by_tag.json";
    public static String ADD_TO_CART = "store_carts/rest_add_cart.json";
    public static String DELETE_FROM_CART = "store_carts/rest_delete_cart.json";
    public static String GET_CART_DETAILS = "store_carts/rest_cart_detail.json";
    public static String DELIVERY_TYPE = "store_orders/rest_delivery_type.json";
    public static String DELIVERY_ADDRESS = "store_orders/rest_delivery_addresses.json";
    public static String PINCODE_CHECK = "store_orders/rest_check_shipping_pincode.json";
    public static String CHECKOUT_UPDATE_DATA = "store_orders/rest_update.json";
    public static String GET_COUNTRY_STATES = "stores/rest_get_countries_states.json";
    public static String GET_BILLING_CITIES = "stores/rest_get_cities.json";
    public static String GET_SHIPPING_CITIES = "stores/rest_get_shipping_cities.json";
    public static String GET_SERVICEABLE_AREAS = "stores/rest_get_shipping_serviceable_areas.json";
    public static String GET_BILLING_INFO = "customers/rest_customer_default_billing_info.json";
    public static String ADD_ADDRESS = "customers/rest_add_customer_address.json";
    public static String UPDATE_ADDRESS = "customers/rest_update_customer_address.json";
    public static String GET_CUSTOMER_ADDRESS_INFO = "customers/rest_get_customer_address_info.json";
    public static String GET_SHIPPING_OPTIONS = "store_orders/rest_get_shipping_options.json";
    public static String GET_PAYMENT_OPTIONS = "store_orders/rest_get_payment_options.json";
    public static String APPLY_SHIPPING = "store_orders/rest_set_shipping_option.json";
    public static String APPLY_PAYMENT = "store_orders/rest_set_payment_option.json";
    public static String COUPON_CHECK = "store_coupons/rest_check_coupon.json";

    public static String REMOVE_COUPON = "store_coupons/rest_delete_coupon.json";

    public static String APPLY_GIFT = "store_gift_vouchers/rest_check_gift_voucher.json";

    public static String REMOVE_GIFT = "store_gift_vouchers/rest_delete_gift_voucher.json";

    public static String REDEEM_POINTS = "store_reward_points/rest_check_reward_point.json";

    public static String DELETE_POINTS = "store_reward_points/rest_delete_reward_point.json";
    public static String GET_COUPONS = "store_coupons/rest_get_coupons.json";
    public static String PAYMENT_GATEWAY = "store_payment_modes/rest_payment_mode.json";
    public static String CHECKOUT_REVIEW = "store_orders/rest_review.json";
    public static String SEND_OTP = "store_tags/rest_send_otp_code.json";

    public static String VERIFY_OTP = "store_tags/rest_verify_otp_code.json";
    public static String GET_PAGES_POLICIES = "store_pages/rest_get_pages_policies.json";
    public static String PAGE = "store_pages/rest_page.json";
    public static String CUSTOMER_ADDRESSES = "customers/rest_customer_delivery_addresses.json";
    public static String GET_CUSTOMER_PROFILE = "customers/rest_get_customer_info.json";
    public static String UPDATE_CUSTOMER_PROFILE = "customers/rest_update_customer.json";
    public static String UPDATE_CUSTOMER_PASSWORD = "customers/rest_change_password_secure.json";
    public static String TERMINATE_ACCOUNT_REASONS = "customers/rest_customer_termination_reasons.json";
    public static String TERMINATE_ACCOUNT = "customers/rest_termination_request.json";
    public static String ORDER_LIST = "customers/rest_get_customer_orders.json";
    public static String CANCEL_ORDER = "store_orders/rest_update_order_status.json";
    public static String BANK_TRANSFER = "store_payment_modes/rest_bank_transfer.json";
    public static String COD = "store_payment_modes/rest_cod.json";
    public static String ORDER_DETAIL_URL = "store_orders/rest_order_detail.json";
    public static String GET_PRODUCT_RETURN_DATA = "store_orders/rest_get_reasons.json";
    public static String SUBMIT_RETURN = "store_orders/rest_return_request.json";
    public static String PAYMENT_RESPONSE = "store_payment_modes/rest_payment_response.json";
    public static String GET_WISHLIST_ITEMS = "store_products/rest_get_wishlist.json";

    public static String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(160, random).toString(32);
    }


    public static JSONObject getDefaultJsonObject(Context mContext) {
        SessionManager session = new SessionManager(mContext, StoreConfig.getSessionName());
        SQLiteHandler db = new SQLiteHandler(mContext, StoreConfig.getDbName());
        String customerId = "0";
        if (session.isLoggedIn()) {
            HashMap<String, String> user = db.getUserDetails();
            customerId = user.get("customerId");
        }
        if (session.getCartSessionId().isEmpty()) {
            session.setCartSessionId(nextSessionId());
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session_id", session.getCartSessionId());
            jsonObject.put("customer_id", customerId);
            jsonObject.put("store_address_id", session.getStoreAddressId());
            jsonObject.put("store_address_city_id", session.getStoreAddressCityId());
            jsonObject.put("store_address_city_name", session.getStoreAddressCityName());
            jsonObject.put("store_address_pincode", session.getStoreAddressPincode());
            jsonObject.put("currency_id", session.getCurrencyId());
            jsonObject.put("order_id", session.getCartOrderId());
            jsonObject.put("language_id", session.getLanguageId());
            jsonObject.put("google_device_id", session.getFCMToken());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return jsonObject;
    }

}
