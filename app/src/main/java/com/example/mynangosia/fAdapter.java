package com.example.mynangosia;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class fAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public fAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                f1 tab1 = new f1();
                return tab1;
            case 1:
                f2 tab2 = new f2();
                return  tab2;
            case 2:
                f3 tab3 = new f3();
                return  tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
