package com.shopaccino.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shopaccino.databinding.LayoutCouponsItemBinding;
import com.shopaccino.utils.SafeClickListener;

import java.util.Locale;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsAdapterVH> {
    private final OnCouponClickListener onCouponClick;
    private JsonArray couponsJsonArray = new JsonArray();
    private JsonArray filteredCouponsJsonArray = new JsonArray();

    public interface OnCouponClickListener {
        void onCouponClick(JsonObject couponsJsonObject, boolean isTerms);
    }

    public CouponsAdapter(OnCouponClickListener onCouponClick) {
        this.onCouponClick = onCouponClick;
    }

    public static class CouponsAdapterVH extends RecyclerView.ViewHolder {
        private final LayoutCouponsItemBinding binding;

        public CouponsAdapterVH(LayoutCouponsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setCoupons(JsonObject couponsJsonObject, OnCouponClickListener onCouponClick) {
            if (couponsJsonObject.get("coupon_title").getAsString().isBlank()) {
                binding.tvCouponTitle.setText("");
            } else {
                binding.tvCouponTitle.setText(couponsJsonObject.get("coupon_title").getAsString());
            }

            if (couponsJsonObject.get("coupon_code").getAsString().isBlank()) {
                binding.tvCouponCode.setText("");
            } else {
                binding.tvCouponCode.setText(couponsJsonObject.get("coupon_code").getAsString());
            }

//            binding.tvCouponDiscount.setText(coupons.getDiscount());

            binding.btnApply.setOnClickListener(new SafeClickListener() {
                @Override
                public void onSafeClick(View v) {
                    onCouponClick.onCouponClick(couponsJsonObject, false);
                }
            });

            binding.tvTermsNConditions.setOnClickListener(new SafeClickListener() {
                @Override
                public void onSafeClick(View v) {
                    onCouponClick.onCouponClick(couponsJsonObject, true);
                }
            });
        }
    }

    @NonNull
    @Override
    public CouponsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCouponsItemBinding binding = LayoutCouponsItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponsAdapterVH(binding);
    }

    @Override
    public void onBindViewHolder(CouponsAdapterVH holder, int position) {
        holder.setCoupons(filteredCouponsJsonArray.get(position).getAsJsonObject(), onCouponClick);
    }

    @Override
    public int getItemCount() {
        return filteredCouponsJsonArray.size();
    }

    public void setData(JsonArray couponsJsonArray) {
        this.couponsJsonArray = couponsJsonArray;
        notifyDataSetChanged();
    }


    public void filter(String query) {
        filteredCouponsJsonArray = new JsonArray();
        if (query.isBlank()) {
            filteredCouponsJsonArray = couponsJsonArray;
        } else {
            String filterPattern = query.toUpperCase(Locale.getDefault()).trim();
            couponsJsonArray.forEach(item -> {
                JsonObject couponJsonObj = item.getAsJsonObject();
                if (couponJsonObj.get("coupon_code").getAsString().toUpperCase(Locale.getDefault()).contains(filterPattern)) {
                    filteredCouponsJsonArray.add(couponJsonObj);
                }
            });
        }
        notifyDataSetChanged();
    }
}
