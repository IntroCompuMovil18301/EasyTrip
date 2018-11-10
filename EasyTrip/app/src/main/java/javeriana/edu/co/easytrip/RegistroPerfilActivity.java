package javeriana.edu.co.easytrip;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;

import Modelo.Usuario;
import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Foto;

public class RegistroPerfilActivity extends AppCompatActivity {

    private ImageView editFotoR;
    private Button btnSiguienteR;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ImageView fotoPerfil;
    private StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap foto;

    EditText nombre, apellidos, email,nom_usuario, contraseña, confContraseña;
    Spinner rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perfil);

        this.fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        rol = (Spinner) findViewById(R.id.idRolR);
        btnSiguienteR = (Button) findViewById(R.id.btnSiguienteR);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        rol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(rol.getSelectedItem().toString().equalsIgnoreCase("Huesped")){
                    btnSiguienteR.setText("Siguiente");
                }else{
                    btnSiguienteR.setText("Registrarse");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }

        });

        this.btnSiguienteR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    crearNuevoUsuario();
                }

            }
        });

        //extraemos el drawable en un bitmap
        Drawable originalDrawable = getResources().getDrawable(R.drawable.perfirlanonimo);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        ImageView imageView = (ImageView) findViewById(R.id.fotoPerfil);

        imageView.setImageDrawable(roundedDrawable);

        this.editFotoR = (ImageView) findViewById(R.id.editFotoR);
        this.editFotoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

    }

    private void crearNuevoUsuario(){
        nom_usuario = (EditText) findViewById(R.id.txtUsuarioR);

        myRef = database.getReference();
        myRef.child(FirebaseReference.USUARIOS).orderByChild("nomUsuario").equalTo(nom_usuario.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for (DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                        Usuario us = singlesnapshot.getValue(Usuario.class);
                        if (nom_usuario.getText().toString().equals(us.getNomUsuario()))
                            Toast.makeText(RegistroPerfilActivity.this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                        else {
                            nuevoUsuario();
                        }
                    }
                }else{
                    nuevoUsuario();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void nuevoUsuario(){
        Usuario usuario = new Usuario(nom_usuario.getText().toString(),contraseña.getText().toString(), email.getText().toString(), rol.getSelectedItem().toString());

        myRef.child(FirebaseReference.USUARIOS).child(usuario.getNomUsuario()).setValue(usuario);

        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        cargarFoto(foto, "ImagenesPerfil",usuario.getNomUsuario());

        mAuth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getContrasena()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                        upcrb.setDisplayName("Anfitrion");
                        user.updateProfile(upcrb.build());
                    } else {
                        Toast.makeText(RegistroPerfilActivity.this, "Error en el Registro",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        if (rol.getSelectedItem().toString().equalsIgnoreCase("Huesped"))
            crearNuevoHuesped(usuario);
        else
            crearNuevoAnfitrion(usuario);
    }

    private Usuario construirUsuario(){

        return new Usuario(nom_usuario.getText().toString(),contraseña.getText().toString(), email.getText().toString(), rol.getSelectedItem().toString());
    }

    private void crearNuevoAnfitrion(Usuario usuario){

        Anfitrion anfitrion = new Anfitrion(usuario.getNomUsuario(), nombre.getText().toString(), "UrlFoto");
        myRef.child(FirebaseReference.ANFITRIONES).child(usuario.getNomUsuario()).setValue(anfitrion);

        Intent intent = new Intent(RegistroPerfilActivity.this, PrincipalActivity.class).putExtra("Usuario",usuario);
        startActivity(intent);
    }

    private  void crearNuevoHuesped(Usuario usuario){

        Bundle bundle = new Bundle();
        bundle.putString("nomUsuario", usuario.getNomUsuario());
        bundle.putString("nombre",nom_usuario.getText().toString());
        bundle.putString("email",email.getText().toString());
        bundle.putSerializable("Usuario",usuario);

        Intent intent = new Intent(RegistroPerfilActivity.this, RegistroHuespedActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private boolean emailValido(String email){
        boolean valid = true;
        if(!email.contains("@") || !email.contains(".")){
            valid = false;
            Toast.makeText(RegistroPerfilActivity.this, "Email incorrecto ejemplo@dominio.com", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
    private boolean validateForm() {

        boolean valid = true;

        nom_usuario = (EditText) findViewById(R.id.txtUsuarioR);
        contraseña = (EditText) findViewById(R.id.txtContrasenaR);
        confContraseña = (EditText) findViewById(R.id.txtConfirmarR);
        email = (EditText) findViewById(R.id.txtEmailR);
        nombre = (EditText) findViewById(R.id.txtNombreR);

        if (TextUtils.isEmpty(nombre.getText().toString())) {
            this.nombre.setError("Requerido");
            valid = false;
        } else {
            this.nombre.setError(null);
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Requerido");
            valid = false;
        } else {
            if(!this.emailValido(email.getText().toString())){
                email.setError("Email inválido");
            }else{
                email.setError(null);
            }

        }


        if (TextUtils.isEmpty(nom_usuario.getText().toString())) {
            nom_usuario.setError("Requerido");
            valid = false;
        } else {
            nom_usuario.setError(null);
        }

        if (TextUtils.isEmpty(contraseña.getText().toString())) {
            this.contraseña.setError("Requerido");
            valid = false;
        } else {
            if(contraseña.getText().toString().length()<6){
                contraseña.setError("Contraseña minimo de 6 caracteres");
            }else{
                contraseña.setError(null);
            }

        }

        if (TextUtils.isEmpty(confContraseña.getText().toString())) {
            confContraseña.setError("Requerido");
            valid = false;
        } else {
            confContraseña.setError(null);
        }

        if(contraseña.getText().toString().compareTo("")!=0 && confContraseña.getText().toString().compareTo("")!=0)
            if(contraseña.getText().toString().compareTo(confContraseña.getText().toString()) != 0 ){
                confContraseña.setError("Los campos no coinciden");
                contraseña.setError("Los campos no coinciden");
                valid = false;
            }
        return valid;
    }

    private void cargarFoto(Bitmap bitmap, String destino, String nombre) {
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File("perfil/" + nombre + ".jpg"));

        StorageReference imageRef = storageRef.child(destino + "/" + file.getLastPathSegment());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] foto = baos.toByteArray();

        imageRef.putBytes(foto);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            RoundedBitmapDrawable roundedDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), photo);

            //asignamos el CornerRadius
            roundedDrawable.setCircular(true);

            foto = photo;
            fotoPerfil.setImageDrawable(roundedDrawable);

        }
    }

}

