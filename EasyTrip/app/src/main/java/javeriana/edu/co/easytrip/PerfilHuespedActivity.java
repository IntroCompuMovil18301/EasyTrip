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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

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


    private Huesped myUser;
    private FirebaseStorage storage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_huesped);
        //Toast.makeText(PerfilHuespedActivity.this, "consulta", Toast.LENGTH_SHORT).show();

        storage = FirebaseStorage.getInstance();

        Bundle b =  getIntent().getBundleExtra ("bundle");
        this.myUser = (Huesped) b.getSerializable("huesped");


        this.fabEditPH = (FloatingActionButton) findViewById(R.id.fabEditPH);
        this.fabEditPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "TESTING FAB CLICK",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EditarPerfilHuespedActivity.class);
                startActivity(intent);
            }
        });

        this.txtNombrePH = (TextView) findViewById(R.id.txtNombrePH);
        this.txtRolPH = (TextView) findViewById(R.id.txtRolPH);
        this.txtEdadPH = (TextView) findViewById(R.id.txtEdadPH);
        this.txtGeneroPH = (TextView) findViewById(R.id.txtGeneroPH);
        this.txtNacionalidadPH = (TextView) findViewById(R.id.txtNacionalidadPH);
        this.txtInfoPH = (TextView) findViewById(R.id.txtInfoPH);


        this.txtNombrePH.setText(this.myUser.getNombre());
        this.txtRolPH.setText(this.myUser.getRol());
        this.txtGeneroPH.setText(this.myUser.getGenero());
        this.txtNacionalidadPH.setText(this.myUser.getNacionalidad());
        this.txtInfoPH.setText(this.myUser.getSobreTi());


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

        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        imageView = (ImageView) findViewById(R.id.fotoPerfilP);

        imageView.setImageDrawable(roundedDrawable);

        descargarFoto("ImagenesPerfil",this.myUser.getNombre());




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

                imageView.setImageDrawable(roundedDrawable);
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
