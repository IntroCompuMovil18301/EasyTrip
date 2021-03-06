package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import Modelo.Usuario;
import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class PerfilHuespedActivity extends AppCompatActivity {

    private static final String TAG = "Tab1Fragment";

    private FloatingActionButton fabEditPH;
    private ImageView imageView;
    private TextView txtNombrePH;
    private TextView txtRolPH;
    private TextView txtEdadPH;
    private TextView txtGeneroPH;
    private TextView txtNacionalidadPH;
    private TextView txtInfoPH;


    private Huesped myUserH;
    private Modelo.Usuario myUser;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private Bitmap fotoPerfil;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_huesped);

        storage = FirebaseStorage.getInstance();

        Bundle b =  getIntent().getBundleExtra ("bundle");
        this.myUser = (Usuario) b.getSerializable("Usuario");
        this.myUserH = (Huesped) b.getSerializable("Huesped") ;


        this.fabEditPH = (FloatingActionButton) findViewById(R.id.fabEditPH);
        this.fabEditPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(view.getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });


        this.txtNombrePH = (TextView) findViewById(R.id.txtNombrePH);
        this.txtRolPH = (TextView) findViewById(R.id.txtRolPH);
        this.txtEdadPH = (TextView) findViewById(R.id.txtEdadPH);
        this.txtGeneroPH = (TextView) findViewById(R.id.txtGeneroPH);
        this.txtNacionalidadPH = (TextView) findViewById(R.id.txtNacionalidadPH);
        this.txtInfoPH = (TextView) findViewById(R.id.txtInfoPH);


        this.txtNombrePH.setText(this.myUserH.getNombre());
        this.txtRolPH.setText(this.myUser.getTipo());
        this.txtGeneroPH.setText(this.myUserH.getGenero());
        this.txtNacionalidadPH.setText(this.myUserH.getNacionalidad());
        this.txtInfoPH.setText(this.myUserH.getSobreMi());

        this.mAuth = FirebaseAuth.getInstance();

        DateFormat dateFormat = dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = dateFormat.parse("1997-07-19");
            Calendar cal = Calendar.getInstance();
            Date fechaActual = cal.getTime();
            //Toast.makeText(this, String.valueOf(getEdad(fechaNacimiento, fechaActual)),Toast.LENGTH_SHORT).show();
            this.txtEdadPH.setText(String.valueOf(getEdad(fechaNacimiento, fechaActual))+" Años");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Drawable originalDrawable = getResources().getDrawable(R.drawable.perfirlanonimo);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        //roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        roundedDrawable.setCircular(true);
        imageView = (ImageView) findViewById(R.id.fotoPerfilPH);

        imageView.setImageDrawable(roundedDrawable);

        descargarFoto("ImagenesPerfil",this.myUserH.getNomUsuario());

    }


    private void descargarFoto(String origen, String nombre){

        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(origen+"/"+nombre+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                fotoPerfil = bitmap;

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


    public  int getEdad(Date fechaNacimiento, Date fechaActual) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int dIni = Integer.parseInt(formatter.format(fechaNacimiento));
        int dEnd = Integer.parseInt(formatter.format(fechaActual));
        int age = (dEnd-dIni)/10000;
        return age;
    }

}
