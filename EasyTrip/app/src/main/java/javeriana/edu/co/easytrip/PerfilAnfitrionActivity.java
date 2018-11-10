package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import Modelo.Usuario;
import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class PerfilAnfitrionActivity extends AppCompatActivity {

    private FloatingActionButton fabEditAnfi;
    private ImageView imageView;
    private Anfitrion myUserA;
    private Modelo.Usuario myUser;
    private TextView txtNombreAnfiVi;
    private TextView txtRolAnfiVi;
    private TextView txtEmailPAVi;
    private TextView txtUsuarioAnfi;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;

    private FloatingActionButton cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_anfitrion);

        Bundle bundle = getIntent().getBundleExtra("bundle");

        this.myUserA =(Anfitrion) bundle.getSerializable("Anfitrion");
        this.myUser = (Usuario) bundle.getSerializable("Usuario");

        this.database= FirebaseDatabase.getInstance();

        this.mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        this.txtNombreAnfiVi = (TextView) findViewById(R.id.txtNombreAnfiVi);
        this.txtRolAnfiVi = (TextView) findViewById(R.id.txtRolAnfiVi);
        this.txtEmailPAVi = (TextView) findViewById(R.id.txtEmailPAVi);
        this.txtUsuarioAnfi = (TextView) findViewById(R.id.txtUsuarioAnfiVi);

        this.txtUsuarioAnfi.setText(myUser.getNomUsuario());
        this.txtRolAnfiVi.setText(myUser.getTipo());
        this.txtEmailPAVi.setText(myUser.getEmail());
        this.txtNombreAnfiVi.setText(myUserA.getNombre());



        this.fabEditAnfi = (FloatingActionButton) findViewById(R.id.fabEditAnfi);
        this.fabEditAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "TESTING FAB CLICK",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(view.getContext(), EditarPerfilAnfitrionActivity.class);
                //startActivity(intent);
                mAuth.signOut();
                Intent intent = new Intent(view.getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });


        Drawable originalDrawable = getResources().getDrawable(R.drawable.perfirlanonimo);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        //roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        roundedDrawable.setCircular(true);

        imageView = (ImageView) findViewById(R.id.fotoPerfilAnfi);

        imageView.setImageDrawable(roundedDrawable);
        descargarFoto("ImagenesPerfil",this.myUserA.getNombre());
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

                RoundedBitmapDrawable roundedDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                //asignamos el CornerRadius
                //roundedDrawable.setCornerRadius(bitmap.getHeight());
                roundedDrawable.setCircular(true);

                imageView.setImageDrawable(roundedDrawable);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
