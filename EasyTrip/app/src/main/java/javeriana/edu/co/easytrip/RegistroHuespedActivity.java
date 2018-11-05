package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class RegistroHuespedActivity extends AppCompatActivity {

    public static final String PATH_USERS="huespedes/";

    private Button btnRegistrarH;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;

    private RadioGroup groupSexo;
    private RadioButton rbtnFemeninoR;
    private RadioButton rbtnMasculinoR;
    private RadioButton rbtnSiFumaR;
    private RadioButton rbtnNoFumaR;
    private Spinner spnNacionalidadR;
    private EditText txtSobretiR;
    private Huesped huesped;
    private String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_huesped);
        storage = FirebaseStorage.getInstance();

        this.database= FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();


        this.huesped = (Huesped) getIntent().getSerializableExtra("huesped");
        this.c = getIntent().getStringExtra("contrasena");
        this.spnNacionalidadR = (Spinner) findViewById(R.id.spnNacionalidadR);
        this.rbtnFemeninoR = (RadioButton) findViewById(R.id.rbtnFemeninoR);
        this.rbtnMasculinoR = (RadioButton) findViewById(R.id.rbtnMasculinoR);
        this.rbtnSiFumaR = (RadioButton) findViewById(R.id.rbtnSiFumaR);
        this.rbtnNoFumaR = (RadioButton) findViewById(R.id.rbtnNoFumaR);
        this.txtSobretiR = (EditText) findViewById(R.id.txtSobretiR);
        this.groupSexo = (RadioGroup) findViewById(R.id.groupSexo);
        this.btnRegistrarH = (Button) findViewById(R.id.btnRegistrarH);
        this.btnRegistrarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent (view.getContext(),HomeHuespedActivity.class);

                //startActivity(intent);

       if (validateForm()) {

            /*
                Huesped h = new Huesped();
                h.setNombre(txtNombreR.getText().toString());
                h.setUsuario(txtUsuarioR.getText().toString());
                h.setEmail(txtEmailR.getText().toString());
                h.setFechaNacimiento((new Date()).toString());

                Intent intent = new Intent(view.getContext(), RegistroHuespedActivity.class);
                Bundle b = new Bundle();

                intent.putExtra("huesped", h);
                intent.putExtra("contrasena", txtContrasenaR.getText().toString());

                startActivity(intent);
            */
                //--------------------------------------------------------------
                mAuth.createUserWithEmailAndPassword(huesped.getEmail(), c )
                    .addOnCompleteListener(RegistroHuespedActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) { //Update user Info

                                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                                upcrb.setDisplayName("Huesped");
                                //upcrb.setPhotoUri(Uri.parse("gs://easytrip-ffa76.appspot.com/usuarios/"));//fake uri, use Firebase Storage
                                user.updateProfile(upcrb.build());

                                //String key = myRef.push().getKey();

                                if(rbtnFemeninoR.isChecked()){
                                    huesped.setGenero("Femenino");
                                }
                                if(rbtnMasculinoR.isChecked()){
                                    huesped.setGenero("Masculino");
                                }


                                if(rbtnSiFumaR.isChecked()){
                                    huesped.setFumador(true);
                                } else{
                                    huesped.setFumador(false);
                                }

                                huesped.setNacionalidad(spnNacionalidadR.getSelectedItem().toString());
                                huesped.setSobreTi(txtSobretiR.getText().toString());
                                huesped.setFoto("");
                                huesped.setRol("Huesped");

                                    Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
                                    Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();

                                cargarFoto(bitmap, "ImagenesPerfil",huesped.getNombre());

                                myRef = database.getReference(PATH_USERS + user.getUid());
                                myRef.setValue(huesped);

                                upcrb.setPhotoUri(Uri.parse("gs://easytrip-ffa76.appspot.com/ImagenesPerfil/"+huesped.getNombre()+".jpg"));//fake uri, use Firebase Storage
                                user.updateProfile(upcrb.build());

                                //Toast.makeText(RegistroPerfilActivity.this, user.getUid(),
                                //      Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegistroHuespedActivity.this, HomeHuespedActivity.class)); //o en el listener

                            } else {
                                Toast.makeText(RegistroHuespedActivity.this, "Error en el Registro",
                                     Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistroHuespedActivity.this, "Fault",
                                    Toast.LENGTH_SHORT).show();
                            //Log.e(TAG, task.getException().getMessage());
                        }
                        }
                    });

                //--------------------------------------------------------------


       }
            }
        });
    }


    private boolean validateForm() {


        boolean valid = true;

        String nombre = this.txtSobretiR.getText().toString();
        if (TextUtils.isEmpty(nombre)) {
            this.txtSobretiR.setError("");
            valid = true;
        } else {
            this.txtSobretiR.setError(null);
        }


        return valid;
    }

    private void cargarFoto(Bitmap bitmap,String destino, String nombre){
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File("perfil/"+nombre+".jpg"));
/*
        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();
*/

        StorageReference imageRef = storageRef.child(destino+"/"+file.getLastPathSegment());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        final byte[] foto = baos.toByteArray();

        imageRef.putBytes(foto);

    }

}
