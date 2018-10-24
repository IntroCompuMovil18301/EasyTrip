package javeriana.edu.co.easytrip;


import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;

public class HomeAnfitrionActivity extends AppCompatActivity {


    private AnfitrionPageAdapter anfitrionPageAdapter;
    private Button fabBusquedaPA;
    private ViewPager mViewPager;
    private ImageButton toolPerfilPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_anfitrion);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        this.fabBusquedaPA = (Button) findViewById(R.id.fabBusquedaPA);
        this.fabBusquedaPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),BuscarAlojamientoActivity.class);

                startActivity(intent);
            }
        });

        this.anfitrionPageAdapter = new AnfitrionPageAdapter(getSupportFragmentManager(),3);

        this.toolPerfilPA = (ImageButton) findViewById(R.id.toolPerfilPA);
        this.toolPerfilPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PerfilAnfitrionActivity.class);

                startActivity(intent);
            }
        });


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerHomeAnfitrion);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsPA);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.iconmash);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconlodging);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconreservations);


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.getTabAt(1).select();

    }

    private void setupViewPager(ViewPager viewPager) {
        AnfitrionPageAdapter adapter = new AnfitrionPageAdapter(getSupportFragmentManager(),3);
        MashesHuespedFragment mashes = new MashesHuespedFragment();
        adapter.addFragment(new MashesHuespedFragment(),"");
        adapter.addFragment(new AlojamientosAnfitrionFragment(),"");
        adapter.addFragment(new ReservasAnfitrionFragment(),"");

        viewPager.setAdapter(adapter);
        viewPager.arrowScroll(1);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HomeHuespedActivity.PlaceholderFragment newInstance(int sectionNumber) {
            HomeHuespedActivity.PlaceholderFragment fragment = new HomeHuespedActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return HomeHuespedActivity.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


}
