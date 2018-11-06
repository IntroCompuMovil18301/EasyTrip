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

    private ImageButton btnCalendarioReservar;
    private Button btnReservarVerAlo;
    private Alojamiento alojamiento;

    private TextView txtNombreVerAlo;
    private TextView txtValorVerAlo;
    private TextView txtCapacidadNumVerAlo;
    private TextView txtDescripcionVerAlo;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamiento);
        this.selected =0;
        this.storage = FirebaseStorage.getInstance();
        this.database= FirebaseDatabase.getInstance();

        this.fotos = new ArrayList<>();

        this.alojamiento = (Alojamiento) getIntent().getSerializableExtra("alojamiento");
        //Toast.makeText(this,this.alojamiento.getNombre(), Toast.LENGTH_SHORT).show();

        this.btnCalendarioReservar = (ImageButton) findViewById(R.id.btnCalendarioReservar);
        this.btnCalendarioReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

        this.btnReservarVerAlo = (Button) findViewById(R.id.btnReservarVerAlo);
        this.btnReservarVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);



                startActivity(intent);
            }
        });

        this.txtNombreVerAlo = (TextView) findViewById(R.id.txtNombreVerAlo);
        this.txtValorVerAlo = (TextView) findViewById(R.id.txtValorVerAlo);
        this.txtCapacidadNumVerAlo = (TextView) findViewById(R.id.txtCapacidadNumVerAlo);
        this.txtDescripcionVerAlo = (TextView) findViewById(R.id.txtDescripcionVerAlo);

        this.imageVerAlo = (ImageView) findViewById(R.id.imageVerAlo);
        this.btnBackFotoVerAlo = (ImageButton) findViewById(R.id.btnBackFotoVerAlo);
        this.btnBackFotoVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(VerAlojamientoActivity.this, selected, Toast.LENGTH_SHORT).show();

                if(selected == 1){
                    selected = fotosB.size();
                }else{
                    selected--;
                }

                imageVerAlo.setImageBitmap(fotosB.get(selected-1));

            }
        });
        this.btnNextFotoVerAlo = (ImageButton) findViewById(R.id.btnNextFotoVerAlo);
        this.btnNextFotoVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(VerAlojamientoActivity.this, selected, Toast.LENGTH_SHORT).show();
                if(selected == fotosB.size()){
                    selected = 1;
                }else{
                    selected++;
                }

                imageVerAlo.setImageBitmap(fotosB.get(selected-1));

            }
        });

        this.txtNombreVerAlo.setText(alojamiento.getNombre());
        this.txtValorVerAlo.setText("Valor noche:\n"+"$"+alojamiento.getCosto().toString()+"00");
        this.txtCapacidadNumVerAlo.setText(alojamiento.getMaxHuespedes()+" Personas");
        this.txtDescripcionVerAlo.setText(alojamiento.getDescripcion());


    }

    private void cargarFotos(){
        fotosB = new ArrayList<>();

        myRef = database.getReference("alojamientos/"+alojamiento.getId()+"/fotos/");
        //Toast.makeText(this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(VerAlojamientoActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(VerAlojamientoActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                    foto = singleSnapshot.getValue(Foto.class);

                    StorageReference storageRef = storage.getReference();
                    StorageReference islandRef = storageRef.child("alojamientos/"+singleSnapshot.getKey()+"/"+foto.getNombre()+".jpg");
                    Toast.makeText(VerAlojamientoActivity.this, islandRef.getPath(), Toast.LENGTH_SHORT).show();

                    final long ONE_MEGABYTE = 1024 * 1024;

                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Data for "images/island.jpg" is returns, use this as needed
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            //foto.setBitmap(bitmap);
                            fotos.add(foto);
                            fotosB.add(bitmap);
                            imageVerAlo.setImageBitmap(fotosB.get(0));
                            selected++;
                 //           Toast.makeText(VerAlojamientoActivity.this, "hecho", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });


                }
                //Toast.makeText(VerAlojamientoActivity.this, fotos.size(), Toast.LENGTH_SHORT).show();
                //asignarFotos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.class, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void asignarFotos(){
        Toast.makeText(VerAlojamientoActivity.this, fotos.size(), Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        cargarFotos();
    }
}
