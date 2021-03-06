package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;

public class AloCercanoHuespedFragment extends Fragment{
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST2;
    private ListView listAlojamientoHuesped;
    private final int n=1;
    private ImageButton btnMapHuesped;
    private AdaptadorAlojamientosCercanos adaptador;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<Object> values;
    private String nombreUsuario;


    private List<Alojamiento> alojamientos;



    public AloCercanoHuespedFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //alojamientos.clear();
        //updateList();
        cargarAlojamientos();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alo_cercano_huesped,container,false);

        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();
        this.alojamientos = new ArrayList<Alojamiento>();


        this.listAlojamientoHuesped = (ListView) view.findViewById(R.id.listAlojamientoHuesped);

        this.btnMapHuesped = (ImageButton) view.findViewById(R.id.btnMapHuesped);
        this.btnMapHuesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),MapAloCercanos.class);
                startActivity(intent);
            }
        });


        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ListView listView = this.listAlojamientoHuesped;
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1,values);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(android.R.layout.simple_list_item_1, values);

        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);


        adaptador = new AdaptadorAlojamientos(getArrayItems(), view.getContext());
        listView.setAdapter(adaptador);
*/
        //updateList();
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);

                startActivity(intent);
        };
        });*/
//        cargarAlojamientos();

        return view;


    }
/*
    public void updateList(){
        //alojamientos.clear();
        adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());
        listAlojamientosAnfi.setAdapter(adaptador);
    }

    public ArrayList<Alojamiento> getArrayItems() {
        ArrayList<Alojamiento>arrayItems = new ArrayList<>();
        //List<Alojamiento> alojas = cargarAlojamientos();

        for(Alojamiento a : alojas){
            arrayItems.add(a);
        }
            /*
            Alojamiento a = new Alojamiento();
            a.setNombre("Alojamiento 1");
            arrayItems.add(a);

            a = new Alojamiento();
            a.setNombre("Alojamiento 2");
            arrayItems.add(a);

            a = new Alojamiento();
            a.setNombre("Alojamiento 3");
            arrayItems.add(a);


        return arrayItems;
    }
*/
    public void cargarAlojamientos() {

        alojamientos.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("alojamientos/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            FirebaseUser user = mAuth.getCurrentUser();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Alojamiento a = singleSnapshot.getValue(Alojamiento.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                        a.setId(singleSnapshot.getKey());
                        boolean esta = false;
                        alojamientos.add(a);
                  //  }

                    //Toast.makeText(getContext(),"aqui -"+a.getNombre() , Toast.LENGTH_SHORT).show();
                    /*for(Alojamiento al : alojamientos){
                        if(al.getId().compareTo(singleSnapshot.getKey()) ==0)
                            esta= true;
                    }*/
                    //values = (List<Object>) td.values();
                    //if(!esta)

                    //Toast.makeText(getContext(),alojamientos.size() , Toast.LENGTH_SHORT).show();
                        //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2"+a.getNombre(), Toast.LENGTH_SHORT).show();
                        //descargarFoto("ImagenesPerfil",a.getNombre());
                        //Toast.makeText(AlojamientosAnfitrionFragment.this, a.getNombre(), Toast.LENGTH_SHORT).show();
                    //}
                    //myUser = a;

                }

                ArrayList<Alojamiento>arrayItems = new ArrayList<>();

                for(Alojamiento a : alojamientos){
                    arrayItems.add(a);
                    //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getContext(),"aqui -"+nombreUsuario , Toast.LENGTH_SHORT).show();
                adaptador = new AdaptadorAlojamientosCercanos(arrayItems, getContext(),nombreUsuario);
                listAlojamientoHuesped.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Alojamiento>arrayItems = new ArrayList<>();

        for(Alojamiento a : alojamientos){
            arrayItems.add(a);
            //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
        }
        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());


    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }




}
