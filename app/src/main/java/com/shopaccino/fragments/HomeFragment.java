package com.shopaccino.fragments;

import static com.shopaccino.utils.Utility.getWidth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopaccino.R;
import com.shopaccino.config.AppConfig;
import com.shopaccino.databinding.FragmentHomeBinding;
import com.shopaccino.network.NetworkClass;

import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding fragmentHomeBinding;

    private final JsonArray homePageJsonArray = new JsonArray();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater);
        return fragmentHomeBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }

    private void callApi() {
        JSONObject params = AppConfig.getDefaultJsonObject(requireContext());
        try {
            params.put("slideshows", "true");
            params.put("featured_categories", "true");
            params.put("image_with_text_overlay", "true");
            params.put("gallery", "true");
            params.put("featured_products", "true");
            params.put("testimonials", "true");

            NetworkClass.createRequest(getContext(), AppConfig.HOME_URL, params, new NetworkClass.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                    if (jsonObject.has("response")) {
                        JsonObject responseJsonObject = jsonObject.getAsJsonObject("response");
                        if (responseJsonObject.has("data")) {
                            if (responseJsonObject.get("data").isJsonObject()) {
                                JsonObject myJsonObject = responseJsonObject.getAsJsonObject("data");
                                if (myJsonObject.has("home_elements")) {
                                    JsonArray homeElements = myJsonObject.getAsJsonArray("home_elements");
                                    if (!homeElements.isEmpty()) {
                                        homeElements.forEach(item -> {
                                            JsonObject jsonObj1 = item.getAsJsonObject();
                                            JsonObject homeJsonObject = new JsonObject();
                                            homeJsonObject.addProperty(
                                                    "file_name",
                                                    jsonObj1.get("file_name").getAsString()
                                            );

                                            switch (jsonObj1.get("file_name").getAsString()) {
                                                case "slideshows": {
                                                    JsonArray homeItemJsonArray = new JsonArray();
                                                    JsonArray itemJsonArray = jsonObj1.getAsJsonArray("items");

                                                    if (!itemJsonArray.isEmpty()) {
                                                        for (int i = 0; i < itemJsonArray.size(); i++) {
                                                            JsonElement itemJsonElement = itemJsonArray.get(i);
                                                            JsonObject itemObj = itemJsonElement.getAsJsonObject();
                                                            JsonObject homeItemJsonObject = new JsonObject();

                                                            homeItemJsonObject.addProperty(
                                                                    "medium_image_url",
                                                                    itemObj.get("medium_image_url").getAsString()
                                                            );
                                                            homeItemJsonObject.addProperty(
                                                                    "video_code",
                                                                    itemObj.get("video_code").getAsString()
                                                            );
                                                            homeItemJsonObject.addProperty(
                                                                    "video_type",
                                                                    itemObj.get("video_type").getAsString()
                                                            );
                                                            homeItemJsonObject.addProperty(
                                                                    "is_autoplay_video",
                                                                    itemObj.get("is_autoplay_video").getAsBoolean()
                                                            );

                                                            homeItemJsonArray.add(homeItemJsonObject);
                                                        }
                                                        homeJsonObject.add("items", homeItemJsonArray);
                                                    }
                                                    break;
                                                }

                                                case "featured_categories": {
                                                    // Get the itemConfig JSON object
                                                    JsonObject itemConfig = jsonObj1.getAsJsonObject("item_config");

                                                    // Add properties to homeJsonObject
                                                    homeJsonObject.addProperty("image_style", itemConfig.get("image_style").getAsInt());

                                                    // Check if style_type is "grid_rows"
                                                    boolean isVertical = itemConfig.get("style_type").getAsString().equals("grid_rows");
                                                    homeJsonObject.addProperty("is_vertical", isVertical);

                                                    homeJsonObject.addProperty("elements_in_row", itemConfig.get("elements_in_row").getAsString());

                                                    // Calculate itemWidth
                                                    int elementsInRow = Integer.parseInt(itemConfig.get("elements_in_row").getAsString());
                                                    int itemWidth = (getWidth(requireContext()) - (int) (getWidth(requireContext()) * 0.1)) / elementsInRow;
                                                    itemWidth -= 8;
                                                    homeJsonObject.addProperty("item_width", itemWidth);

                                                    // Get the itemJsonArray from jsonObj1
                                                    JsonArray itemJsonArray = jsonObj1.getAsJsonArray("items");

                                                    // Check if itemJsonArray is not empty
                                                    if (!itemJsonArray.isEmpty()) {
                                                        JsonArray featuredCategoriesJsonArray = new JsonArray();

                                                        // Iterate through the itemJsonArray
                                                        for (int index = 0; index < itemJsonArray.size(); index++) {
                                                            JsonElement jsonElement1 = itemJsonArray.get(index);
                                                            JsonObject itemJsonObject = jsonElement1.getAsJsonObject();
                                                            JsonObject featuredCategoriesJsonObject = new JsonObject();

                                                            // Add properties to featuredCategoriesJsonObject
                                                            featuredCategoriesJsonObject.addProperty("category_name", itemJsonObject.get("category_name").getAsString());
                                                            featuredCategoriesJsonObject.addProperty("category_id", itemJsonObject.get("category_id").getAsString());
                                                            featuredCategoriesJsonObject.addProperty("thumb_image_path", itemJsonObject.get("thumb_image_path").getAsString());

                                                            // Add to featuredCategoriesJsonArray
                                                            featuredCategoriesJsonArray.add(featuredCategoriesJsonObject);
                                                        }

                                                        // Add the featuredCategoriesJsonArray to homeJsonObject
                                                        homeJsonObject.add("items", featuredCategoriesJsonArray);
                                                    }
                                                    break;
                                                }

                                                case "image_with_text_overlay": {
                                                    // Get the itemConfig JSON object
                                                    JsonObject itemConfig = jsonObj1.getAsJsonObject("item_config");

                                                    // Add properties to homeJsonObject
                                                    homeJsonObject.addProperty("overlay_color", itemConfig.get("overlay_color").getAsString());
                                                    homeJsonObject.addProperty("text_color", itemConfig.get("text_color").getAsString());
                                                    homeJsonObject.addProperty("overlay_opacity", itemConfig.get("overlay_opacity").getAsString());

                                                    JsonArray imageWithTextOverlayJsonArray = new JsonArray();
                                                    JsonArray itemsArray = jsonObj1.getAsJsonArray("items");

                                                    // Check if itemsArray is not empty
                                                    if (!itemsArray.isEmpty()) {
                                                        // Iterate through itemsArray
                                                        for (JsonElement itemJsonElement : itemsArray) {
                                                            JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();
                                                            JsonObject imageWithTextOverlayJsonObject = new JsonObject();

                                                            // Add properties to imageWithTextOverlayJsonObject
                                                            imageWithTextOverlayJsonObject.addProperty("title", itemJsonObject.get("title").getAsString());
                                                            imageWithTextOverlayJsonObject.addProperty("medium_image_url", itemJsonObject.get("medium_image_url").getAsString());

                                                            // Add to imageWithTextOverlayJsonArray
                                                            imageWithTextOverlayJsonArray.add(imageWithTextOverlayJsonObject);
                                                        }

                                                        // Add the imageWithTextOverlayJsonArray to homeJsonObject
                                                        homeJsonObject.add("items", imageWithTextOverlayJsonArray);
                                                    }
                                                    break;
                                                }

                                                case "gallery": {
                                                    // Get the items array from jsonObj1
                                                    JsonArray itemsJsonArray = jsonObj1.getAsJsonArray("items");
                                                    JsonArray galleryJsonArray = new JsonArray();

                                                    // Check if itemsJsonArray is not empty
                                                    if (!itemsJsonArray.isEmpty()) {
                                                        // Iterate through itemsJsonArray
                                                        for (int index = 0; index < itemsJsonArray.size(); index++) {
                                                            JsonElement itemJsonElement = itemsJsonArray.get(index);
                                                            JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();
                                                            JsonObject galleryJsonObject = new JsonObject();

                                                            // Add properties to galleryJsonObject
                                                            galleryJsonObject.addProperty("medium_image_url", itemJsonObject.get("medium_image_url").getAsString());
                                                            galleryJsonObject.addProperty("video_code", itemJsonObject.get("video_code").getAsString());
                                                            galleryJsonObject.addProperty("video_type", itemJsonObject.get("video_type").getAsString());
                                                            galleryJsonObject.addProperty("is_autoplay_video", itemJsonObject.get("is_autoplay_video").getAsBoolean());

                                                            // Add to galleryJsonArray
                                                            galleryJsonArray.add(galleryJsonObject);
                                                        }

                                                        // Add the galleryJsonArray to homeJsonObject
                                                        homeJsonObject.add("items", galleryJsonArray);
                                                    }
                                                    break;
                                                }

                                                case "featured_products": {
                                                    JsonArray itemsJsonArray = jsonObj1.getAsJsonArray("items");
                                                    JsonObject itemConfigJsonObject = jsonObj1.getAsJsonObject("item_config");

                                                    // Determine if the layout is vertical
                                                    boolean isVertical = itemConfigJsonObject.get("style_type").getAsString().equals("grid_rows");
                                                    homeJsonObject.addProperty("is_vertical", isVertical);

                                                    // Check if itemsJsonArray is not empty
                                                    if (!itemsJsonArray.isEmpty()) {
                                                        JsonArray featuredProductsJsonArray = new JsonArray();

                                                        // Iterate through itemsJsonArray
                                                        for (JsonElement itemJsonElement : itemsJsonArray) {
                                                            JsonObject itemObject = itemJsonElement.getAsJsonObject();
                                                            JsonObject featuredProductsJsonObject = new JsonObject();

                                                            // Add properties to featuredProductsJsonObject
                                                            featuredProductsJsonObject.addProperty("id", itemObject.get("id").getAsString());
                                                            featuredProductsJsonObject.addProperty("product_variant_id", itemObject.get("product_variant_id").getAsString());
                                                            featuredProductsJsonObject.addProperty("product_name", itemObject.get("product_name").getAsString());
                                                            featuredProductsJsonObject.addProperty("medium_image_url", itemObject.get("medium_image_url").getAsString());
                                                            featuredProductsJsonObject.addProperty("variant_count_text", itemObject.get("variant_count_text").getAsString());
                                                            featuredProductsJsonObject.addProperty("product_price", itemObject.get("product_price").getAsString());
                                                            featuredProductsJsonObject.addProperty("net_price", itemObject.get("net_price").getAsString());
                                                            featuredProductsJsonObject.addProperty("minimum_order_quantity", itemObject.get("minimum_order_quantity").getAsInt());

                                                            // Add label based on item status
                                                            if (itemObject.get("is_out_of_stock").getAsInt() == 1) {
                                                                featuredProductsJsonObject.addProperty("label", "Sold Out");
                                                            } else if (itemObject.get("is_popular").getAsInt() == 1) {
                                                                featuredProductsJsonObject.addProperty("label", "Popular");
                                                            } else if (itemObject.get("is_new").getAsInt() == 1) {
                                                                featuredProductsJsonObject.addProperty("label", "New");
                                                            } else if (!itemObject.get("custom_tag").getAsString().isEmpty()) {
                                                                featuredProductsJsonObject.addProperty("label", itemObject.get("custom_tag").getAsString());
                                                            } else {
                                                                featuredProductsJsonObject.addProperty("label", "");
                                                            }

                                                            // Add remaining properties
                                                            featuredProductsJsonObject.addProperty("discount_percent", itemObject.get("discount_percent").getAsString());
                                                            featuredProductsJsonObject.addProperty("option_name", itemObject.get("option_name").getAsString());
                                                            featuredProductsJsonObject.addProperty("is_wishlist", itemObject.get("is_wishlist").getAsBoolean());
                                                            featuredProductsJsonObject.addProperty("is_gift_card", itemObject.get("is_gift_card").getAsBoolean());
                                                            featuredProductsJsonObject.addProperty("hide_price", itemObject.get("hide_price").getAsBoolean());
                                                            featuredProductsJsonObject.addProperty("add_cart_visibility", itemObject.get("add_cart_visibility").getAsBoolean());

                                                            // Add average rating
                                                            int averageRating = itemObject.get("average_rating").getAsInt();
                                                            if (averageRating > 0) {
                                                                featuredProductsJsonObject.addProperty("average_rating", Integer.toString(averageRating));
                                                            } else {
                                                                featuredProductsJsonObject.addProperty("average_rating", "");
                                                            }

                                                            // Add total reviews
                                                            int totalReviews = itemObject.get("total_reviews").getAsInt();
                                                            if (totalReviews > 0) {
                                                                featuredProductsJsonObject.addProperty("total_reviews", Integer.toString(totalReviews));
                                                            } else {
                                                                featuredProductsJsonObject.addProperty("total_reviews", "");
                                                            }

                                                            // Handle product option values
                                                            JsonArray variantsJsonArray = new JsonArray();
                                                            JsonArray itemsProductOptionValuesArray = itemObject.getAsJsonArray("product_option_values");
                                                            if (!itemsProductOptionValuesArray.isEmpty()) {
                                                                for (JsonElement optionValueElement : itemsProductOptionValuesArray) {
                                                                    JsonObject optionValuesJsonObject = optionValueElement.getAsJsonObject();
                                                                    JsonObject variantsJsonObject = new JsonObject();
                                                                    variantsJsonObject.addProperty("id", optionValuesJsonObject.get("id").getAsString());
                                                                    variantsJsonObject.addProperty("name", optionValuesJsonObject.get("name").getAsString());
                                                                    variantsJsonObject.addProperty("product_price", optionValuesJsonObject.get("product_price").getAsString());
                                                                    variantsJsonObject.addProperty("discount_percent", optionValuesJsonObject.get("discount_percent").getAsString());
                                                                    variantsJsonObject.addProperty("net_price", optionValuesJsonObject.get("net_price").getAsInt());
                                                                    variantsJsonObject.addProperty("show_as_main", optionValuesJsonObject.get("show_as_main").getAsInt());
                                                                    variantsJsonObject.addProperty("selected_variant_name", optionValuesJsonObject.get("name").getAsString());
                                                                    variantsJsonObject.addProperty("selected_variant_id", optionValuesJsonObject.get("id").getAsString());

                                                                    // Add to variantsJsonArray
                                                                    variantsJsonArray.add(variantsJsonObject);
                                                                }

                                                                // Add the variantsJsonArray to featuredProductsJsonObject
                                                                featuredProductsJsonObject.add("product_option_values", variantsJsonArray);
                                                            }

                                                            // Add featuredProductsJsonObject to featuredProductsJsonArray
                                                            featuredProductsJsonArray.add(featuredProductsJsonObject);
                                                        }

                                                        // Add the featuredProductsJsonArray to homeJsonObject
                                                        homeJsonObject.add("items", featuredProductsJsonArray);
                                                    }
                                                    break;
                                                }

                                                default: {
                                                    // Get the items array from jsonObj1
                                                    JsonArray itemsJsonArray = jsonObj1.getAsJsonArray("items");
                                                    JsonArray testimonialJsonArray = new JsonArray();
                                                    if (!itemsJsonArray.isEmpty()) {
                                                        for (int index = 0; index < itemsJsonArray.size(); index++) {
                                                            JsonElement itemJsonElement = itemsJsonArray.get(index);
                                                            JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();
                                                            JsonObject testimonialJsonObject = new JsonObject();

                                                            // Add properties to galleryJsonObject
                                                            testimonialJsonObject.addProperty("customer_name", itemJsonObject.get("customer_name").getAsString());
                                                            testimonialJsonObject.addProperty("comments", itemJsonObject.get("comments").getAsString());
                                                            testimonialJsonObject.addProperty("thumb_image_url", itemJsonObject.get("thumb_image_url").getAsString());

                                                            // Add to testimonialJsonArray
                                                            testimonialJsonArray.add(testimonialJsonObject);
                                                        }
                                                        // Add the testimonialJsonArray to homeJsonObject
                                                        homeJsonObject.add("items", testimonialJsonArray);
                                                    }
                                                    break;
                                                }
                                            }

                                            homePageJsonArray.add(homeJsonObject);
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }, new NetworkClass.VolleyErrorCallback() {
                @Override
                public void onError(String error) {
                    Log.e("@#@#@#", error);
                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}