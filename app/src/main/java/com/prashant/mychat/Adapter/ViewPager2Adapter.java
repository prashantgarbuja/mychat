package com.prashant.mychat.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPager2Adapter(FragmentManager fragmentManager, Lifecycle Lifecycle) {
        super(fragmentManager, Lifecycle);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add( fragment );
        titles.add( title );
    }
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles.get( position );
//    }
}
