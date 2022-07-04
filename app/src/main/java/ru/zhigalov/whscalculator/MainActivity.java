package ru.zhigalov.whscalculator;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dagger.hilt.android.AndroidEntryPoint;
import ru.zhigalov.whscalculator.databinding.ActivityMainBinding;
import ru.zhigalov.whscalculator.ui.main.SectionsPagerAdapter;

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