package javeriana.edu.co.easytrip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Huesped;
import javeriana.edu.co.modelo.Mash;

public class VerMashActivity extends AppCompatActivity {

    private ImageView fotoPerfilMash;
    private ImageButton btnDescartarMash;
    private ImageButton btnAceptarMash;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Mash mash;
    private String nombreUsuario;
    private LinearLayout linearButtonsMash;
    private FirebaseAuth mAuth;
    private Modelo.Usuario myUser;
    private FirebaseStorage storage;


    private TextView txtNombreMash;
    private TextView txtEdadMash;
    private TextView txtGeneroMash;
    private TextView txtFumaMash;
    private TextView txtNacionalidadMash;
    private TextView txtDescripcionMash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mash);

        Bundle b = getIntent().getBundleExtra("bundle");

        this.mAuth = FirebaseAuth.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.database = FirebaseDatabase.getInstance();

        this.mash = (Mash) b.getSerializable("mash");
        this.nombreUsuario = mash.getNombreUsuario();

        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();


        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        this.database = FirebaseDatabase.getInstance();

        this.fotoPerfilMash = (ImageView) findViewById(R.id.fotoPerfilMash);

        this.fotoPerfilMash.setImageDrawable(roundedDrawable);

        this.btnAceptarMash = (ImageButton) findViewById(R.id.btnAceptarMash);
        this.btnAceptarMash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef = database.getReference("mashes/"+mash.getIdReserva()+"/"+mash.getId()+"/");
                mash.setEstado("Aceptada");
                myRef.setValue(mash);
                finish();
            }
        });

        this.btnDescartarMash = (ImageButton) findViewById(R.id.btnDescartarMash);
        this.btnDescartarMash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef = database.getReference("mashes/"+mash.getIdReserva()+"/"+mash.getId()+"/");
                mash.setEstado("Rechazada");
                myRef.setValue(mash);
                finish();

            }
        });

        this.linearButtonsMash = (LinearLayout) findViewById(R.id.linearButtonsMash);
        if(mash.getEstado().compareTo("Solicitado") == 0){
            linearButtonsMash.setVisibility(View.VISIBLE);
        }else{
            linearButtonsMash.setVisibility(View.INVISIBLE);
        }

        this.txtNombreMash = (TextView) findViewById(R.id.txtNombreMash);
        this.txtEdadMash = (TextView) findViewById(R.id.txtEdadMash);
        this.txtGeneroMash = (TextView) findViewById(R.id.txtGeneroMash);
        this.txtFumaMash = (TextView) findViewById(R.id.txtFumaMash);
        this.txtNacionalidadMash = (TextView) findViewById(R.id.txtNacionalidadMash);
        this.txtDescripcionMash = (TextView) findViewById(R.id.txtDescripcionMash);

    }


    public void loadUser() {
        myRef = database.getReference();
        myRef.child(FirebaseReference.HUESPESDES).orderByChild("nomUsuario").equalTo(nombreUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                        Huesped h = singlesnapshot.getValue(Huesped.class);
                        descargarFoto("ImagenesPerfil",nombreUsuario);

                        txtNombreMash.setText(mash.getNombreUsuario());
                        txtEdadMash.setText("21 Años");
                        txtGeneroMash.setText(h.getGenero());
                        txtFumaMash.setText("Fuma: "+h.isFumador());
                        txtNacionalidadMash.setText(h.getNacionalidad());
                        txtDescripcionMash.setText(h.getSobreMi());

                    }
                }else {
                    Toast.makeText(VerMashActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VerMashActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

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



                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedDrawable.setCircular(true);
                fotoPerfilMash.setImageDrawable(roundedDrawable);

                //Toast.makeText(HomeHuespedActivity.this, "cargada ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUser();
    }
}
