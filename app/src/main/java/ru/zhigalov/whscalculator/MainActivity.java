package ru.zhigalov.whscalculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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


import ru.zhigalov.whscalculator.ui.main.SectionsPagerAdapter;
import ru.zhigalov.whscalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getLifecycle(), getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, binding.viewPager,
                (tab, position) -> tab.setText("O" + position)).attach();
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