package com.congthiep.musictablayout;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.congthiep.musictablayout.fragment.InformationFragment;
import com.congthiep.musictablayout.fragment.ListFragment;
import com.congthiep.musictablayout.fragment.SearchAndStatisticsFragment;

public class AdapterPaper  extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public AdapterPaper(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ListFragment listFragment = new ListFragment();
                return listFragment;
            case 1:
                InformationFragment informationFragment = new InformationFragment();
                return informationFragment;
            case 2:
                SearchAndStatisticsFragment searchAndStatisticsFragment = new SearchAndStatisticsFragment();
                return searchAndStatisticsFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}