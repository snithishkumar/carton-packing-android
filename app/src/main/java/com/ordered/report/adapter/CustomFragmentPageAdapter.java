package com.ordered.report.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ordered.report.fragment.DeliveredFragment;
import com.ordered.report.fragment.PackingFragment;
import com.ordered.report.fragment.OrderedFragment;

public class CustomFragmentPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 3;

    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OrderedFragment();
            case 1:
                return new PackingFragment();
            case 2:
                return new DeliveredFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ORDERED";
            case 1:
                return "PACKING";
            case 2:
                return "DELIVERED";
        }
        return null;
    }
}
