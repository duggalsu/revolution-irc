package io.mrarm.irc.newui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActionMenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.mrarm.irc.DCCActivity;
import io.mrarm.irc.IRCApplication;
import io.mrarm.irc.R;
import io.mrarm.irc.SettingsActivity;

public class MainFragment extends Fragment implements MainActivity.FragmentToolbarCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_newui_main_fragment, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = view.findViewById(R.id.main_container);
        mViewPager.setId(R.id.main_content);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_server_list, menu);
        menu.findItem(R.id.action_dcc_transfers).setOnMenuItemClickListener((i) -> {
            startActivity(new Intent(getContext(), DCCActivity.class));
            return true;
        });
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener((i) -> {
            startActivity(new Intent(getContext(), SettingsActivity.class));
            return true;
        });
        menu.findItem(R.id.action_exit).setOnMenuItemClickListener((i) -> {
            ((IRCApplication) getActivity().getApplication()).requestExit();
            return true;
        });
    }

    @Override
    public View onCreateToolbar(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_newui_main_fragment_toolbar, container, false);

        ActionMenuView actionMenuView = view.findViewById(R.id.action_menu_view);
        onCreateOptionsMenu(actionMenuView.getMenu(), getActivity().getMenuInflater());

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        return view;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ChatListFragment.newInstance();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return getString(R.string.main_tab_chats);
            if (position == 1)
                return getString(R.string.main_tab_servers);
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
