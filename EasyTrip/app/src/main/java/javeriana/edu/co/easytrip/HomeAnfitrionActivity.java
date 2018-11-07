package javeriana.edu.co.easytrip;


import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.Principal;
import java.util.Date;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;
import javeriana.edu.co.modelo.Usuario;

import static android.app.PendingIntent.getActivity;

public class HomeAnfitrionActivity extends AppCompatActivity {

    public static final String PATH_USERS="usuarios/";

    private FirebaseAuth mAuth;
    private AnfitrionPageAdapter anfitrionPageAdapter;
    private ImageButton fabAddAloPA;
    private ViewPager mViewPager;
    private ImageButton toolPerfilPA;
    private Toolbar toolbar;
    private Bitmap fotoPerfil;
    private ImageButton fabCalendarioPA;


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String email;
    private FirebaseStorage storage;
    private Anfitrion myUserA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_anfitrion);

        //this.toolbar = (Toolbar) findViewById(R.id.toolbarAnfitrion);

        //this.toolbar.inflateMenu(R.menu.menu);
        this.myUserA = new Anfitrion();

        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        loadUser(user.getEmail());

        this.fabCalendarioPA = (ImageButton) findViewById(R.id.fabCalendarioPA);
        this.fabCalendarioPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);

            }
        });

        this.fabAddAloPA = (ImageButton) findViewById(R.id.fabAddAloPA);
        this.fabAddAloPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FirebaseUser user = mAuth.getCurrentUser();


                Intent intent = new Intent (view.getContext(),AddAlojamientoActivity.class);
                startActivity(intent);
            }
        });



        this.toolPerfilPA = (ImageButton) findViewById(R.id.toolPerfilPA);
        this.toolPerfilPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mAuth.signOut();
                Intent intent = new Intent(view.getContext(),PerfilAnfitrionActivity.class);

                Bundle b = new Bundle();
                b.putSerializable("anfitrion",myUserA);
                //intent.putExtra("anfitrion",myUserA);
                intent.putExtra("bundle",b);

                startActivity(intent);


            }
        });


        this.anfitrionPageAdapter = new AnfitrionPageAdapter(getSupportFragmentManager(),3);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.menuCerrarSs){
            mAuth.signOut();
            Intent intent = new Intent(HomeAnfitrionActivity.this, PrincipalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void loadUser(String em) {
        this.email = em;
        myRef = database.getReference("anfitriones/");
        //Toast.makeText(PerfilAnfitrionActivity.this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Anfitrion a = singleSnapshot.getValue(Anfitrion.class);
                    if(a.getEmail().compareTo(email) == 0){

                        myUserA = a;

                        //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2"+a.getNombre(), Toast.LENGTH_SHORT).show();
                        descargarFoto("ImagenesPerfil",a.getNombre());
                        //Toast.makeText(HomeAnfitrionActivity.this, a.getNombre(), Toast.LENGTH_SHORT).show();
                    }
                    //myUser = a;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeAnfitrionActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

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
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                fotoPerfil = bitmap;

                RoundedBitmapDrawable roundedDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                //asignamos el CornerRadius
                //roundedDrawable.setCornerRadius(bitmap.getHeight());
                roundedDrawable.setCircular(true);
                toolPerfilPA.setImageDrawable(roundedDrawable);

                Toast.makeText(HomeAnfitrionActivity.this, "cargada ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
