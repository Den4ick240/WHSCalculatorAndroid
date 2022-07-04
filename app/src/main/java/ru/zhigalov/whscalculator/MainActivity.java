package ru.zhigalov.whscalculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.ui.main.SectionsPagerAdapter;
import ru.zhigalov.whscalculator.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getLifecycle(), getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, binding.viewPager,
                (tab, position) -> tab.setText(getTabText(position))).attach();
    }

    private String getTabText(int position) {
        return getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public void onBackPressed() {
        String currentViewPagerFragmentTag = "f" + binding.viewPager.getCurrentItem();
        Fragment currentViewPagerFragment = getSupportFragmentManager().findFragmentByTag(currentViewPagerFragmentTag);
        NavController controller = NavHostFragment.findNavController(currentViewPagerFragment);
        if (controller.navigateUp())
            return;
        super.onBackPressed();
    }
}