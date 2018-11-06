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

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class PerfilAnfitrionActivity extends AppCompatActivity {

    private FloatingActionButton fabEditAnfi;
    private ImageView imageView;
    private Anfitrion myUser;
    private TextView txtNombreAnfiVi;
    private TextView txtRolAnfiVi;
    private TextView txtEmailPAVi;
    private TextView txtUsuarioAnfi;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String email;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_anfitrion);

        Bundle b=  getIntent().getBundleExtra ("bundle");
        this.myUser = (Anfitrion) b.getSerializable("anfitrion");
        this.database= FirebaseDatabase.getInstance();

        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        //loadUser(user.getEmail());

        this.txtNombreAnfiVi = (TextView) findViewById(R.id.txtNombreAnfiVi);
        this.txtRolAnfiVi = (TextView) findViewById(R.id.txtRolAnfiVi);
        this.txtEmailPAVi = (TextView) findViewById(R.id.txtEmailPAVi);
        this.txtUsuarioAnfi = (TextView) findViewById(R.id.txtUsuarioAnfiVi);

        //Toast.makeText(PerfilAnfitrionActivity.this, "Aqui--"+myUser.getNombre(), Toast.LENGTH_SHORT).show();

        this.txtNombreAnfiVi.setText(myUser.getNombre().toString());
        this.txtNombreAnfiVi.setText(myUser.getNombre());
        this.txtRolAnfiVi.setText(myUser.getRol());
        this.txtEmailPAVi.setText(myUser.getEmail());
        this.txtUsuarioAnfi.setText(myUser.getUsuario());



        this.fabEditAnfi = (FloatingActionButton) findViewById(R.id.fabEditAnfi);
        this.fabEditAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "TESTING FAB CLICK",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(view.getContext(), EditarPerfilAnfitrionActivity.class);
                //startActivity(intent);
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
        descargarFoto("ImagenesPerfil",this.myUser.getNombre());
    }


    public void loadUser(String em) {
        this.email = em;
        myRef = database.getReference("anfitriones/");
        //Toast.makeText(PerfilAnfitrionActivity.this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(PerfilAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Anfitrion a = singleSnapshot.getValue(Anfitrion.class);
                    if(a.getEmail().compareTo(email) == 0){
                        //txtNombreAnfi.setText(a.getNombre());
                        //txtRolAnfiVi.setText(a.getRol());
                        //txtEmailPAVi.setText(a.getEmail());
                        //txtUsuarioAnfi.setText(a.getUsuario());
                        myUser = a;
                    }
                        //myUser = a;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PerfilAnfitrionActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

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
