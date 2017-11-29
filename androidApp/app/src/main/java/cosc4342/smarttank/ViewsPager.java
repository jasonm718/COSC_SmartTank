package cosc4342.smarttank;


import android.app.NotificationManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ViewsPager extends AppCompatActivity {
    
    private android.support.v4.view.ViewPager pager;
    private TabLayout tabs;
    private TabsAdapter tabsAdapter;
    private SensorModel sensorModel;
    public static List<Sensor> sensors = new ArrayList<>();
    private static NotificationCompat.Builder mBuilder;
    private static List<String> notifications = new ArrayList<>();
    private static int notification_ids = 0;
    
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
    
        pager = (android.support.v4.view.ViewPager) findViewById(R.id.tabView);
        tabs = (TabLayout) findViewById(R.id.tabs);
        
            
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        pager.setAdapter(tabsAdapter);
        tabs.setupWithViewPager(pager);
        
        sensorModel =  ViewModelProviders.of(this).get(SensorModel.class);
                        
    }
    
    @Override
    public void onStart(){
        super.onStart();
    }
    
    @Override
    public void onResume(){
        super.onResume();
        
    }
    
    
    private class TabsAdapter extends FragmentPagerAdapter{
        private String[] fragments_title = {"Home", "Temp", "PH"};
        private List<Fragment> fragments = new ArrayList<>();
        private Fragment[] fragment;
        public TabsAdapter(FragmentManager manager) {
            super(manager);
            fragments.add(new HomeFragment());
            fragments.add(new TemperatureFragment());
            fragments.add(new PhFragment());
        }
    
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                
                case 0: return fragments.get(0);
                case 1: return fragments.get(1);
                case 2: return fragments.get(2);
                default: return new TemperatureFragment();
            }
        }
    
        @Override
        public int getCount() {
            return fragments.size();
        }
    
        @Override
        public CharSequence getPageTitle(int position){
            return fragments_title[position];
        }
    }
    
    
    public static void sendNotification(String title, String value, String timestamp, Context context, Object notification_service){
        
        if(!notifications.contains(timestamp)) {
            
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.smartanklogo)
                    .setContentTitle("SmartTank Notification")
                    .setContentText("Your " + title+": "+value+ " at "+timestamp);
    
            
            NotificationManager notificationManagerCompat = (NotificationManager) notification_service;
            notificationManagerCompat.notify(notification_ids, mBuilder.build());
            
            notification_ids += 1;
            
            notifications.add(timestamp);
        }
    }
    
    
}
