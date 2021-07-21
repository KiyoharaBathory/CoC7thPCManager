package cn.kiyohara.cocplayercharactermanager.adapter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class DetailPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] titles = {"基本信息", "技能信息", "其他信息"};
    Context mContext;

    public DetailPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mContext = context;
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
