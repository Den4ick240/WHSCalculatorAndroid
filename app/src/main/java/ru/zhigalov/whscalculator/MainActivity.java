package ru.zhigalov.whscalculator;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.ActivityMainBinding;
import ru.zhigalov.whscalculator.ui.main.SectionsPagerAdapter;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public static final String NOTIFY_HANDICAP_INDEX_CHANGED_ID = "notify-handicap-index-changed-id";
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
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                setupTopAppBar();
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, binding.viewPager,
                (tab, position) -> tab.setText(getTabText(position))).attach();
        setSupportActionBar(binding.appBar);
        setTitle("Your scores"); //TODO: redo
    }

    private Fragment getCurrentViewPagerFragment() {
        String currentViewPagerFragmentTag = "f" + binding.viewPager.getCurrentItem();
        return getSupportFragmentManager().findFragmentByTag(currentViewPagerFragmentTag);
    }

    private AppBarConfiguration appBarConfiguration;
    private void setupTopAppBar() {
        Fragment currentViewPagerFragment =getCurrentViewPagerFragment();
        if (currentViewPagerFragment == null) return;
        NavController navController = NavHostFragment.findNavController(currentViewPagerFragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    private String getTabText(int position) {
        return getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = NavHostFragment.findNavController(getCurrentViewPagerFragment());
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Fragment currentViewPagerFragment = getCurrentViewPagerFragment();
        if (currentViewPagerFragment == null) {
            super.onBackPressed();
            return;
        }
        NavController controller = NavHostFragment.findNavController(currentViewPagerFragment);
        if (controller.navigateUp())
            return;
        super.onBackPressed();
    }
}