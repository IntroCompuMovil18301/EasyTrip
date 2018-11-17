package javeriana.edu.co.easytrip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import javeriana.edu.co.modelo.Reserva;


public class ReservasAnfitrionFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";


    private ListView listView;
    private AdaptadorReservaPorAceptar adaptador;
    private String nombreUsuario;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Alojamiento alojamiento;
    private TextView txtAloHistorialReservas;

    private ArrayList<Reserva> reservas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservas_anfitrion, container, false);



        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();
        this.user = mAuth.getCurrentUser();

        this.reservas = new ArrayList<Reserva>();

        this.listView = (ListView) view.findViewById(R.id.listReservasAnfi);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerReservaActivity.class);
                startActivity(intent);
            }
        });

        //adaptador = new AdaptadorReservaPorAceptar(this.getArrayItems(), view.getContext());
        //listView.setAdapter(adaptador);



        return view;
    }


    public ArrayList<EntityReservaPorAceptar> getArrayItems() {
        ArrayList<EntityReservaPorAceptar>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        return arrayItems;
    }

    private int getColor(String estado){

        if(estado.compareTo("Solicitado")== 0)
            return R.color.solicitado;

        if(estado.compareTo("Aceptada")== 0)
            return R.color.aceptada;

        if(estado.compareTo("Rechazada")== 0)
            return R.color.rechazada;

        if(estado.compareTo("Caducada")== 0)
            return R.color.caducada;

        return R.color.h1;
    }

    @Override
    public void onStart() {
        super.onStart();
        cargarReservas();
    }

    public void cargarReservas() {

        reservas.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("reservas/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Reserva r = singleSnapshot.getValue(Reserva.class);

                    //if(r.getIdUsuario().compareTo(user.getUid()) == 0){
                        r.setId(singleSnapshot.getKey());
                        reservas.add(r);
                    //Toast.makeText(getContext(), r.getNombreAlo(), Toast.LENGTH_SHORT).show();
                    //}
                }
                actualizarReservas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();

        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());


    }

    private void actualizarReservas(){
        //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        adaptador = new AdaptadorReservaPorAceptar(this.reservas, getContext());
        listView.setAdapter(adaptador);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
