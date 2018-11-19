package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Calificacion;
import javeriana.edu.co.modelo.Mash;
import javeriana.edu.co.modelo.Reserva;

import static javeriana.edu.co.easytrip.PrincipalActivity.TAG;

public class CheckOutReservaActivity extends AppCompatActivity implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener {


    private ListView listView;
    private AdaptadorCalificar adaptador;
    private FirebaseDatabase database;
    private ImageView imageView;
    private ImageButton btnCalificar;
    private EditText imageVerAloCalificar2;
    private DatabaseReference myRef;
    private DatabaseReference myRefM;
    private Calificacion caliReserva;
    private List<Mash> mashes;
    private FirebaseStorage storage;
    private String nombreUsuario;
    private String genero;
    private Reserva reserva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_reserva);

        Bundle b = getIntent().getBundleExtra("bundle");
        this.reserva = (Reserva) b.getSerializable("reserva");



        this.storage = FirebaseStorage.getInstance();

        this.imageView = (ImageView) findViewById(R.id.imageVerAloCalificar2);

        this.caliReserva = new Calificacion();

        this.mashes = new ArrayList<Mash>();
        this.database = FirebaseDatabase.getInstance();


        this.btnCalificar = (ImageButton) findViewById(R.id.btnCalificar);
        this.btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caliReserva = new Calificacion();
                
                caliReserva.setIdAlojamiento(reserva.getIdAlojamiento());
                caliReserva.setIdReserva(reserva.getId());


            }
        });

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
        descargarFoto();
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

    private void cargarCalificacion(){


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

    @Override
    public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:

                caliReserva.setPuntaje(1.0);
                break;
            case SmileRating.GOOD:
                //Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
                caliReserva.setPuntaje(2.0);
                break;
            case SmileRating.GREAT:
                caliReserva.setPuntaje(3.0);
                break;
            case SmileRating.OKAY:
                caliReserva.setPuntaje(4.0);
                break;
            case SmileRating.TERRIBLE:
                caliReserva.setPuntaje(6.0);
                break;
            case SmileRating.NONE:
                caliReserva.setPuntaje(7.0);
                break;
        }
    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }


    private void descargarFoto(){
        //Toast.makeText(this, nombre+"...", Toast.LENGTH_SHORT).show();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("alojamientos/"+ reserva.getId()+"/"+"Image0.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedDrawable.setCircular(true);
                imageView.setImageDrawable(roundedDrawable);


                //Toast.makeText(PerfilHuespedActivity.this, "cargada ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
