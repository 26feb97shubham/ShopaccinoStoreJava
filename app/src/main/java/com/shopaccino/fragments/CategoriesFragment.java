package com.shopaccino.fragments;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
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
import com.shopaccino.databinding.FragmentCategoriesBinding;
import com.shopaccino.helper.GridSpacingItemDecoration;
import com.shopaccino.helper.SQLiteHandler;
import com.shopaccino.network.NetworkClass;
import com.shopaccino.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding fragmentCategoriesBinding;
    private SQLiteHandler db;
    private boolean isLinear = true, isTextOnly = true;
    private int elementsInRow = 1, itemHeight = 0, itemWidth = 0;
    private double imageRatio = 1;
    private JsonArray categoriesJsonArray = new JsonArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCategoriesBinding = FragmentCategoriesBinding.inflate(inflater);
        return fragmentCategoriesBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCategories();
    }

    private void getCategories() {
        JSONObject params = AppConfig.getDefaultJsonObject(requireContext());
        try {
            params.put("category_id", "0");
            NetworkClass.createRequest(requireContext(), AppConfig.GET_ALL_CATEGORIES, params, new NetworkClass.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                        if (jsonObject.has("response")) {
                            JsonObject responseJsonObject = jsonObject.getAsJsonObject("response");
                            if (responseJsonObject.has("data")) {
                                if (responseJsonObject.get("data").isJsonObject()) {
                                    JsonObject myJsonObject = responseJsonObject.getAsJsonObject("data");
                                    JsonObject configObj = myJsonObject.getAsJsonObject("item_config");
                                    isLinear = configObj.get("device_is_category_linear").getAsInt() == 1;
                                    if (isLinear) {
                                        isTextOnly = configObj.get("show_category_type").getAsInt() == 1;
                                    } else {
                                        isTextOnly = false;
                                        elementsInRow = configObj.get("device_category_item_per_row").getAsInt();
                                    }
                                    imageRatio = Double.parseDouble(configObj.get("image_ratio").getAsString());

                                    JsonArray categoriesArray = myJsonObject.get("categories").getAsJsonArray();
                                    if (!categoriesArray.isEmpty()) {
                                        for (int i = 0; i < categoriesArray.size(); i++) {
                                            JsonElement itemJsonElement = categoriesArray.get(i);
                                            JsonObject itemObj = itemJsonElement.getAsJsonObject();
                                            JsonObject categoryJsonObject = new JsonObject();
                                            categoryJsonObject.addProperty("category_name", itemObj.get("category_name").getAsString());
                                            categoryJsonObject.addProperty("category_id", itemObj.get("category_id").getAsString());
                                            categoryJsonObject.addProperty("mobile_menu_image_path", itemObj.get("mobile_menu_image_path").getAsString());
                                            categoriesJsonArray.add(categoryJsonObject);
                                        }
                                    }

                                    DisplayMetrics displaymetrics = new DisplayMetrics();
                                    if (isLinear) {
                                        if (isTextOnly) {
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                            fragmentCategoriesBinding.rvCategories.setLayoutManager(mLayoutManager);
                                            fragmentCategoriesBinding.rvCategories.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
                                        } else {
                                            elementsInRow = 1;
                                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(requireContext(), 1);
                                            fragmentCategoriesBinding.rvCategories.setLayoutManager(mLayoutManager);
                                            int spacing = (int) Utility.dpToPx(requireContext(), 0);
                                            fragmentCategoriesBinding.rvCategories.addItemDecoration(new GridSpacingItemDecoration(1, spacing, false));
                                            ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                                            itemWidth = (displaymetrics.widthPixels) / elementsInRow;
                                            itemHeight = (int) (itemWidth * imageRatio);                                        }
                                    } else {
                                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(requireContext(), elementsInRow);
                                        fragmentCategoriesBinding.rvCategories.setLayoutManager(mLayoutManager);
                                        int spacing = (int) Utility.dpToPx(requireContext(), 0);
                                        fragmentCategoriesBinding.rvCategories.addItemDecoration(new GridSpacingItemDecoration(elementsInRow, spacing, true));

                                        ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                                        int itemSpacing = (int)Utility.dpToPx(requireContext(),10) * (elementsInRow + 1);
                                        itemWidth = (displaymetrics.widthPixels - itemSpacing) / elementsInRow;
                                        itemHeight = (int) (itemWidth * imageRatio);
                                    }
                                }
                                fragmentCategoriesBinding.rvCategories.setItemAnimator(new DefaultItemAnimator());
                            }
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
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}