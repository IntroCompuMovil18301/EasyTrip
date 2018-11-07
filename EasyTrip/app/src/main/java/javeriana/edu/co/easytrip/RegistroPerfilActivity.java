package javeriana.edu.co.easytrip;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//----------------
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;
import javeriana.edu.co.modelo.Usuario;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.FileProvider.getUriForFile;


public class RegistroPerfilActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    private ImageView imageview;

    public static final String PATH_USERS="anfitriones/";

    private ImageView editFotoR;
    private ImageView fotoPerfil;
    private Button btnSiguienteR;
    private EditText txtNombreR;
    private EditText txtEmailR;
    private EditText txtUsuarioR;
    private EditText txtContrasenaR;
    private EditText txtConfirmarR;
    private Spinner idRolR;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    TextView texto;

    Button botonCargar;
    ImageView imagen;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perfil);
        this.fotoPerfil = (ImageView) findViewById(R.id.fotoPerfil);
        this.txtNombreR = (EditText) findViewById(R.id.txtNombreR);
        this.txtNombreR.setText("michael");
        this.txtEmailR = (EditText) findViewById(R.id.txtEmailR);
        this.txtEmailR.setText("michavarg9@gmail.com");
        this.txtUsuarioR = (EditText) findViewById(R.id.txtUsuarioR);
        this.txtUsuarioR.setText("wizchael");
        this.txtContrasenaR = (EditText) findViewById(R.id.txtContrasenaR);
        this.txtContrasenaR.setText("123456");
        this.txtConfirmarR = (EditText) findViewById(R.id.txtConfirmarR);
        this.txtConfirmarR.setText("123456");
        this.idRolR = (Spinner) findViewById(R.id.idRolR);

        this.database= FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        this.btnSiguienteR = (Button) findViewById(R.id.btnSiguienteR);
        this.btnSiguienteR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            if (validateForm()) {

                if (idRolR.getSelectedItem().toString().compareTo("Huesped") == 0) {


                    Huesped h = new Huesped();
                    h.setNombre(txtNombreR.getText().toString());
                    h.setUsuario(txtUsuarioR.getText().toString());
                    h.setEmail(txtEmailR.getText().toString());
                    h.setFechaNacimiento((new Date()).toString());

                    Intent intent = new Intent(view.getContext(), RegistroHuespedActivity.class);
                    Bundle b = new Bundle();

                    intent.putExtra("huesped", h);
                    intent.putExtra("contrasena", txtContrasenaR.getText().toString());

                        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
                        Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();

                        cargarFoto(bitmap, "ImagenesPerfil",h.getNombre());

                    startActivity(intent);

                } else {
                    //--------------------------------------------------------------
                    mAuth.createUserWithEmailAndPassword(txtEmailR.getText().toString(), txtContrasenaR.getText().toString())
                            .addOnCompleteListener(RegistroPerfilActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) { //Update user Info

                                            UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                                            upcrb.setDisplayName("Anfitrion");
                                            //upcrb.setPhotoUri(Uri.parse("gs://easytrip-ffa76.appspot.com/usuarios/"));//fake uri, use Firebase Storage
                                            user.updateProfile(upcrb.build());


                                            //String key = myRef.push().getKey();


                                            Anfitrion a = new Anfitrion();
                                            a.setNombre(txtNombreR.getText().toString());
                                            a.setUsuario(txtUsuarioR.getText().toString());
                                            a.setFechaNacimiento((new Date()).toString());
                                            a.setEmail(txtEmailR.getText().toString());

                                                Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
                                                Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();

                                                cargarFoto(bitmap, "ImagenesPerfil",a.getNombre());
                                                descargarFoto("ImagenesPerfil",a.getNombre());


                                            a.setFoto("");
                                            a.setRol("Anfitrion");
                                            myRef = database.getReference(PATH_USERS + user.getUid());
                                            myRef.setValue(a);

                                            //Toast.makeText(RegistroPerfilActivity.this, user.getUid(),
                                            //      Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(RegistroPerfilActivity.this, HomeAnfitrionActivity.class)); //o en el listener

                                        } else {
                                            Toast.makeText(RegistroPerfilActivity.this, "Error en el Registro",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistroPerfilActivity.this, "Fault",
                                                Toast.LENGTH_SHORT).show();
                                        //Log.e(TAG, task.getException().getMessage());
                                    }
                                }
                            });

                    //--------------------------------------------------------------

                }
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
                /*Intent intent = new Intent(view.getContext(), SubirFotoActivity.class);
                startActivity(intent);*/

                if(validaPermisos()){
                    cargarImagen();
                }else{
                    solicitarPermisosManual();
                }
                //descargarFoto("ImagenesPerfil","michael");

            }
        });

    }

    private boolean validateForm() {


        boolean valid = true;

        String nombre = this.txtNombreR.getText().toString();
        if (TextUtils.isEmpty(nombre)) {
            this.txtNombreR.setError("Requerido");
            valid = false;
        } else {
            this.txtNombreR.setError(null);
        }

        String email = this.txtEmailR.getText().toString();
        if (TextUtils.isEmpty(email)) {
            this.txtEmailR.setError("Requerido");
            valid = false;
        } else {
            if(!this.isEmailValid(email)){
                this.txtEmailR.setError("Email inválido");
            }else{
                this.txtEmailR.setError(null);
            }

        }



        String usuario = this.txtUsuarioR.getText().toString();
        if (TextUtils.isEmpty(usuario)) {
            this.txtUsuarioR.setError("Requerido");
            valid = false;
        } else {
            this.txtUsuarioR.setError(null);
        }

        String contrasena = this.txtContrasenaR.getText().toString();
        if (TextUtils.isEmpty(contrasena)) {
            this.txtContrasenaR.setError("Requerido");
            valid = false;
        } else {
            if(contrasena.length()<6){
                this.txtContrasenaR.setError("Contraseña minimo de 6 caracteres");
            }else{
                this.txtContrasenaR.setError(null);
            }

        }

        String confirmar = this.txtConfirmarR.getText().toString();
        if (TextUtils.isEmpty(confirmar)) {
            this.txtConfirmarR.setError("Requerido");
            valid = false;
        } else {

            this.txtConfirmarR.setError(null);
        }

        if(contrasena.compareTo("")!=0 && confirmar.compareTo("")!=0)
            if(contrasena.compareTo(confirmar) != 0 ){
                this.txtConfirmarR.setError("Los campos no coinciden");
                this.txtContrasenaR.setError("Los campos no coinciden");
                valid = false;
            }


        return valid;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = true;
        if (!email.contains("@") || !email.contains(".") || email.length() < 5)
            isValid = false;
        return isValid;
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
                roundedDrawable.setCornerRadius(bitmap.getHeight());

                fotoPerfil.setImageDrawable(roundedDrawable);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }



    //--------------------------------------------------

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistroPerfilActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(RegistroPerfilActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }


    private void cargarImagen() {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistroPerfilActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    tomarFotografia();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void tomarFotografia() {
  //      Toast.makeText(RegistroPerfilActivity.this, "+++", Toast.LENGTH_SHORT).show();
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);

        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;


        File imagen=new File(path);

        Intent intent=null;
        Toast.makeText(RegistroPerfilActivity.this, path, Toast.LENGTH_SHORT).show();
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";

            Uri imageUri=FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        //startActivityForResult(intent,COD_FOTO);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);

                    break;
            }


        }
    }


}
