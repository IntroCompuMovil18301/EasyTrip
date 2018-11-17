package javeriana.edu.co.easytrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Reserva;

public class HistorialReservaActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reserva);

        Bundle b = getIntent().getBundleExtra("bundle");
        this.alojamiento = (Alojamiento) b.getSerializable("alojamiento");

        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();
        this.user = mAuth.getCurrentUser();

        this.reservas = new ArrayList<Reserva>();

        this.listView = (ListView) findViewById(R.id.listHistorialReservasAnfi);

        this.txtAloHistorialReservas = (TextView) findViewById(R.id.txtAloHistorialReservas);

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

                    Toast.makeText(HistorialReservaActivity.this, alojamiento.getId(), Toast.LENGTH_SHORT).show();

                    if(r.getIdAlojamiento().compareTo(alojamiento.getId()) == 0){
                        reservas.add(r);
                    }

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
        adaptador = new AdaptadorReservaPorAceptar(this.reservas, this);
        listView.setAdapter(adaptador);
        this.txtAloHistorialReservas.setText(alojamiento.getNombre());
    }
}
