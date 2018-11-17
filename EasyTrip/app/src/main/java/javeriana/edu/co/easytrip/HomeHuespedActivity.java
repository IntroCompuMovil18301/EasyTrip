package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Huesped;

public class HomeHuespedActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private HuespedPageAdapter huespedPageAdapter;
    private ViewPager mViewPager;
    private ImageButton toolPerfilPH;
    private ImageButton fabCalendarioPH;
    private ImageButton fabBusquedaPH;
    private Toolbar toolbar;
    private Huesped usA;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Modelo.Usuario myUser;
    private FirebaseStorage storage;
    private Bitmap fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_huesped);
        this.myUser = (Modelo.Usuario) getIntent().getSerializableExtra("Usuario");

        this.mAuth = FirebaseAuth.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        this.fabCalendarioPH = (ImageButton) findViewById(R.id.fabCalendarioPH);
        this.fabCalendarioPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

        this.fabBusquedaPH = (ImageButton) findViewById(R.id.fabBusquedaPH);
        this.fabBusquedaPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),BuscarAlojamientoActivity.class);
                Bundle b = new Bundle();
                b.putString("nombreUsuario",myUser.getNomUsuario());
                intent.putExtra("bundle",b);
                startActivity(intent);
            }
        });

        this.toolPerfilPH = (ImageButton) findViewById(R.id.toolPerfilPH);
        this.toolPerfilPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modelo.Usuario us = (Modelo.Usuario) getIntent().getSerializableExtra("Usuario");
                myRef = database.getReference();
                myRef.child(FirebaseReference.HUESPESDES).orderByChild("nomUsuario").equalTo(us.getNomUsuario()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            for(DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                                Huesped usH = singlesnapshot.getValue(Huesped.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Usuario",getIntent().getSerializableExtra("Usuario"));
                                bundle.putSerializable("Huesped",usH);
                                Intent intent = new Intent(HomeHuespedActivity.this,PerfilHuespedActivity.class);
                                intent.putExtra("bundle",bundle);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(HomeHuespedActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        this.huespedPageAdapter = new HuespedPageAdapter(getSupportFragmentManager(),3);
        loadUser();
        // Set up the ViewPager with the sections adapter.


    }

    @Override
    protected void onStart() {
        super.onStart();

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

        mashes.setNombreUsuario(myUser.getNomUsuario());
//        mashes.setGenero(usA.getGenero());
        adapter.addFragment(mashes,"");
        //adapter.addFragment(new AloCercanoHuespedFragment(),"");
        AloCercanoHuespedFragment aloC = new AloCercanoHuespedFragment();
        aloC.setNombreUsuario(myUser.getNomUsuario());
        adapter.addFragment(aloC,"");

        ReservasHuespedFragment r = new ReservasHuespedFragment();
        r.setNombreUsuario(myUser.getNomUsuario());
        adapter.addFragment( r,"");

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

    public void loadUser() {
        myRef = database.getReference();
        myRef.child(FirebaseReference.HUESPESDES).orderByChild("nomUsuario").equalTo(myUser.getNomUsuario()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                        usA = singlesnapshot.getValue(Huesped.class);
                        descargarFoto("ImagenesPerfil",myUser.getNomUsuario());
                    }
                }else {
                    Toast.makeText(HomeHuespedActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeHuespedActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
    }



    private void descargarFoto(String origen, String nombre){

        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(origen+"/"+nombre+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                fotoPerfil = bitmap;

                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedDrawable.setCircular(true);
                toolPerfilPH.setImageDrawable(roundedDrawable);

                //Toast.makeText(HomeHuespedActivity.this, "cargada ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
