package javeriana.edu.co.easytrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.ArrayList;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Foto;
import javeriana.edu.co.modelo.Huesped;

public class VerAlojamientoActivity extends AppCompatActivity {


    private Button btnReservarVerAlo;
    private Alojamiento alojamiento;

    private TextView txtHistorialVerAlo;
    private TextView txtNombreVerAlo;
    private TextView txtValorVerAlo;
    private TextView txtCapacidadNumVerAlo;
    private TextView txtDescripcionVerAlo;
    private TextView txtHabitaNumVerAlo;
    private TextView txtCocinaVerAlo;
    private TextView txtBanoVerAlo;
    private TextView txtServiciosVerAlo;
    private TextView txtElectroVerAlo;
    private TextView txtTipoVerAlo;


    private ImageView imageVerAlo;
    private ImageButton btnBackFotoVerAlo;
    private ImageButton  btnNextFotoVerAlo;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private int selected;
    private ArrayList<Foto> fotos;
    private Foto foto;
    private ArrayList<Bitmap> fotosB;
    private String rol;
    private String nombreUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamiento);
        this.selected =0;
        this.storage = FirebaseStorage.getInstance();
        this.database= FirebaseDatabase.getInstance();

        this.fotos = new ArrayList<>();

        Bundle b = getIntent().getBundleExtra("bundle");

        this.alojamiento = (Alojamiento) b.getSerializable("alojamiento");
        this.rol = b.getString("rol");
        this.nombreUsuario = b.getString("nombreUsuario");



        this.btnReservarVerAlo = (Button) findViewById(R.id.btnReservarVerAlo);
        this.btnReservarVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("alojamiento",alojamiento);
                b.putString("foto","Image0");
                b.putString("nombreUsuario",nombreUsuario);
                intent.putExtra("bundle",b);

                startActivity(intent);
            }
        });

        //Toast.makeText(this, rol+"--", Toast.LENGTH_SHORT).show();

        if(rol.compareTo("anfitrion")==0){
            this.btnReservarVerAlo.setVisibility(View.INVISIBLE);
        }else{
            this.btnReservarVerAlo.setVisibility(View.VISIBLE);
        }

        this.txtNombreVerAlo = (TextView) findViewById(R.id.txtNombreVerAlo);
        this.txtValorVerAlo = (TextView) findViewById(R.id.txtValorVerAlo);
        this.txtCapacidadNumVerAlo = (TextView) findViewById(R.id.txtCapacidadNumVerAlo);
        this.txtDescripcionVerAlo = (TextView) findViewById(R.id.txtDescripcionVerAlo);

        this.txtHabitaNumVerAlo = (TextView) findViewById(R.id.txtHabitaNumVerAlo);
        this.txtCocinaVerAlo = (TextView) findViewById(R.id.txtCocinaVerAlo);
        this.txtBanoVerAlo = (TextView) findViewById(R.id.txtBanoVerAlo);
        this.txtServiciosVerAlo = (TextView) findViewById(R.id.txtServiciosVerAlo);
        this.txtElectroVerAlo = (TextView) findViewById(R.id.txtElectroVerAlo);
        this.txtTipoVerAlo = (TextView) findViewById(R.id.txtTipoVerAlo);

        this.txtHistorialVerAlo = (TextView) findViewById(R.id.txtHistorialVerAlo);
        this.txtHistorialVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerAlojamientoActivity.this,HistorialReservaActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("alojamiento",alojamiento);
                intent.putExtra("bundle",b);

                startActivity(intent);
            }
        });


        this.imageVerAlo = (ImageView) findViewById(R.id.imageVerAlo);
        this.btnBackFotoVerAlo = (ImageButton) findViewById(R.id.btnBackFotoVerAlo);
        this.btnBackFotoVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!fotosB.isEmpty()){
                    if(selected == 1){
                        selected = fotosB.size();
                    }else{
                        selected--;
                    }

                    imageVerAlo.setImageBitmap(fotosB.get(selected-1));
                }

            }
        });
        this.btnNextFotoVerAlo = (ImageButton) findViewById(R.id.btnNextFotoVerAlo);
        this.btnNextFotoVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fotosB.isEmpty()){
                    if(selected == fotosB.size()){
                        selected = 1;
                    }else{
                        selected++;
                    }

                    imageVerAlo.setImageBitmap(fotosB.get(selected-1));
                }


            }
        });

        this.txtNombreVerAlo.setText(alojamiento.getNombre());
        this.txtValorVerAlo.setText("Valor noche:\n"+"$"+alojamiento.getCosto().toString()+"00");
        this.txtCapacidadNumVerAlo.setText(alojamiento.getMaxHuespedes()+"");
        this.txtDescripcionVerAlo.setText(alojamiento.getDescripcion());

        this.txtHabitaNumVerAlo.setText(alojamiento.getNumHabitaciones()+"");

        if(alojamiento.isCocina()) {this.txtCocinaVerAlo.setText("Si");}
        else{ this.txtCocinaVerAlo.setText("No"); }

        this.txtBanoVerAlo.setText(alojamiento.getBano());
        this.txtServiciosVerAlo.setText(alojamiento.getServicios());
        this.txtElectroVerAlo.setText(alojamiento.getElectrodomesticos());
        this.txtTipoVerAlo.setText(alojamiento.getTipo());


    }

    private void cargarFotos(){
        fotosB = new ArrayList<>();

        myRef = database.getReference("alojamientos/"+alojamiento.getId()+"/fotos/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    foto = singleSnapshot.getValue(Foto.class);

                    StorageReference storageRef = storage.getReference();
                    StorageReference islandRef = storageRef.child("Alojamientos/"+alojamiento.getId()+"/"+foto.getNombre()+".jpg");

                    final long ONE_MEGABYTE = 1024 * 1024;

                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            fotos.add(foto);
                            fotosB.add(bitmap);
                            imageVerAlo.setImageBitmap(fotosB.get(0));
                            selected++;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarFotos();
    }
}