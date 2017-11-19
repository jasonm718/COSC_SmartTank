package cosc4342.smarttank;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

public class ViewsPager extends AppCompatActivity{
    
    private android.support.v4.view.ViewPager pager;
    private TabLayout tabs;
    private TabsAdapter tabsAdapter;
    
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
    
        pager = (android.support.v4.view.ViewPager) findViewById(R.id.tabView);
        tabs = (TabLayout) findViewById(R.id.tabs);
    
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        pager.setAdapter(tabsAdapter);
        tabs.setupWithViewPager(pager);
    }
    

    private class TabsAdapter extends FragmentPagerAdapter{
        private String[] fragments = {"Home", "Temp", "PH"};
        public TabsAdapter(FragmentManager manager) {
            super(manager);
        }
    
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                
                case 0: return new HomeFragment();
                case 1: return new TemperatureFragment();
                case 2: return new PhFragment();
                default: return new TemperatureFragment();
            }
        }
    
        @Override
        public int getCount() {
            return fragments.length;
        }
    
        @Override
        public CharSequence getPageTitle(int position){
            return fragments[position];
        }
    }
    
}
