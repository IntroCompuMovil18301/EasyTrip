package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Mash;
import javeriana.edu.co.modelo.Reserva;

public class CheckOutReservaActivity extends AppCompatActivity {


    private ListView listView;
    private AdaptadorCalificar adaptador;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefM;
    private List<Mash> mashes;
    private String nombreUsuario;
    private String genero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_reserva);

        this.mashes = new ArrayList<Mash>();
        this.database = FirebaseDatabase.getInstance();

        listView = (ListView) findViewById(R.id.listCompanerosCalificar);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerMashActivity.class);
                startActivity(intent);
            }
        });
        adaptador = new AdaptadorCalificar((ArrayList<Mash>) mashes, this);
        listView.setAdapter(adaptador);

    }


    public void cargarMashes() {

        mashes.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("reservas/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    final Reserva r = singleSnapshot.getValue(Reserva.class);
                    r.setId(singleSnapshot.getKey());



                        myRefM = database.getReference("mashes/"+r.getId()+"/");

                        myRefM.addListenerForSingleValueEvent(new ValueEventListener() {


                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int i = 0;
                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                    Mash m = singleSnapshot.getValue(Mash.class);
                                    m.setId(singleSnapshot.getKey());
                                    m.setIdReserva(r.getId());
                                    mashes.add(m);
                                }
                                actualizarMashes();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

                            }
                        });


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }


        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();

    }

    private void actualizarMashes(){
        adaptador = new AdaptadorCalificar((ArrayList<Mash>) mashes, this);
        listView.setAdapter(adaptador);

    }

    @Override
    public void onStart() {
        super.onStart();
        cargarMashes();
    }
}
