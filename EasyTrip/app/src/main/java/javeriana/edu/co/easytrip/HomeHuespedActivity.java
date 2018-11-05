package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class HomeHuespedActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private HuespedPageAdapter huespedPageAdapter;
    private ViewPager mViewPager;
    private ImageButton toolPerfilPH;
    private Button fabBusquedaPH;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String email;
    private Huesped myUserH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_huesped);

        //this.toolbar = (Toolba    r) findViewById(R.id.toolbarHuesped);
        //this.toolbar.inflateMenu(R.menu.menu);
        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        loadUser(user.getEmail());

        this.fabBusquedaPH = (Button) findViewById(R.id.fabBusquedaPH);
        this.fabBusquedaPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),BuscarAlojamientoActivity.class);

                startActivity(intent);
            }
        });

        this.toolPerfilPH = (ImageButton) findViewById(R.id.toolPerfilPH);
        this.toolPerfilPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PerfilHuespedActivity.class);

                Bundle b = new Bundle();
                b.putSerializable("huesped",myUserH);
                //intent.putExtra("anfitrion",myUserA);
                intent.putExtra("bundle",b);

                startActivity(intent);
            }
        });

        this.huespedPageAdapter = new HuespedPageAdapter(getSupportFragmentManager(),3);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.iconmash);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconlodging);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconreservations);


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.getTabAt(1).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        HuespedPageAdapter adapter = new HuespedPageAdapter(getSupportFragmentManager(),4);
        MashesHuespedFragment mashes = new MashesHuespedFragment();
        adapter.addFragment(new MashesHuespedFragment(),"");
        adapter.addFragment(new AloCercanoHuespedFragment(),"");
        adapter.addFragment(new ReservasHuespedFragment(),"");

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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


    public void loadUser(String em) {
        this.email = em;
        myRef = database.getReference("huespedes/");
        //Toast.makeText(PerfilAnfitrionActivity.this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Huesped h = singleSnapshot.getValue(Huesped.class);
                    if(h.getEmail().compareTo(email) == 0){
                        myUserH = h;
                        //Toast.makeText(HomeHuespedActivity.this, "Aqui2"+h.getNombre(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeHuespedActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
    }
}
