package com.example.task3benchmarks;

import static com.example.task3benchmarks.util.AppConstants.EXTRA_KEY;
import static com.example.task3benchmarks.util.AppConstants.REQUEST_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.task3benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;
    private ViewPagerAdapter viewPagerAdapter; //TODO: DI!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabLayout = binding.tabLayout;
        ViewPager2 viewPager = binding.viewPager;

        tabLayout.addTab(tabLayout.newTab().setText("Collections"));
        tabLayout.addTab(tabLayout.newTab().setText("Maps"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        fragmentManager.setFragmentResultListener(REQUEST_KEY, this, ((requestKey, bundle) -> {
            int result = bundle.getInt(EXTRA_KEY);
            Log.d("RESULT", ""+result);
            String stringResult = String.valueOf(result);
            viewPager.setVisibility(View.VISIBLE);
        }));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_left);
                } else {
                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_right);
                }
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}