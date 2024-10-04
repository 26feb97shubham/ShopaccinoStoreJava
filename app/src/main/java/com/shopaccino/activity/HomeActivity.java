package com.shopaccino.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shopaccino.R;
import com.shopaccino.databinding.ActivityHomeBinding;
import com.shopaccino.fragments.AccountsFragment;
import com.shopaccino.fragments.CategoriesFragment;
import com.shopaccino.fragments.HomeFragment;
import com.shopaccino.fragments.MenuFragment;
import com.shopaccino.fragments.OrderFragment;
import com.shopaccino.helper.SQLiteHandler;
import com.shopaccino.sessionmanager.SessionManager;
import com.shopaccino.utils.SafeClickListener;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private Context mContext;
    private SessionManager session;
    private ActivityHomeBinding activityHomeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadFragment(new HomeFragment());
        clickListeners();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void clickListeners() {
        activityHomeBinding.ivHomePage.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                loadFragment(new HomeFragment());
            }
        });

        activityHomeBinding.ivCategories.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                loadFragment(new CategoriesFragment());
            }
        });

        activityHomeBinding.ivOrder.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                loadFragment(new OrderFragment());
            }
        });

        activityHomeBinding.ivAccount.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                loadFragment(new AccountsFragment());
            }
        });

        activityHomeBinding.ivMenu.setOnClickListener(new SafeClickListener() {
            @Override
            public void onSafeClick(View view) {
                loadFragment(new MenuFragment());
            }
        });

    }
}