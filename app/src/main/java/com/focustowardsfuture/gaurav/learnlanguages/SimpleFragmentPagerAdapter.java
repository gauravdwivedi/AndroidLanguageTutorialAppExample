package com.focustowardsfuture.gaurav.learnlanguages;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Gaurav Dwivedi on 07-05-2019.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] =new String[]{"Numbers","Family","Colors","Phrases"};
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new NumbersFragment();
        } else if (i == 1) {
            return new FamilyFragment();
        } else if (i == 2) {
            return new ColorsFragment();
        } else  {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      //  return super.getPageTitle(position);
        return tabTitles[position];
    }
}
