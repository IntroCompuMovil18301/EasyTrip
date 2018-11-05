package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Foto;
import javeriana.edu.co.modelo.Localizacion;

public class AddAlojamientoActivity extends AppCompatActivity {

    public static final String PATH_ALOJAMIENTOS="alojamientos/";

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private Alojamiento alo;

    private EditText txtNumeroAddAlo;
    private EditText txtNombreAddAlo;
    private EditText txtValorAddAlo;
    private EditText txtDescripcionAddAlo;
    private TextView txtFotosAddAlo;
    private TextView txtCiudadAddAlo;
    private ImageButton ibtnCamaraAdd;
    private ImageButton ibtnUbicacionAdd;
    private Button btnGuardarAlo;
    private Spinner spnTipoAddAlo;
    private List<Foto> fotos;
    private List<Bitmap> fotosB;
    private Localizacion localizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alojamiento);
        this.alo = new Alojamiento();
        database= FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        this.fotos = new ArrayList<Foto>();
        this.fotosB = new ArrayList<Bitmap>();

        this.localizacion = new Localizacion();

        this.txtNombreAddAlo = (EditText) findViewById(R.id.txtNombreAddAlo);
        this.txtNumeroAddAlo = (EditText) findViewById(R.id.txtNumeroAddAlo);
        this.txtValorAddAlo = (EditText) findViewById(R.id.txtValorAddAlo);
        this.txtDescripcionAddAlo = (EditText) findViewById(R.id.txtDescripcionAddAlo);

        this.txtFotosAddAlo = (TextView) findViewById(R.id.txtFotosAddAlo);
        this.txtFotosAddAlo.setVisibility(View.INVISIBLE);
        this.txtCiudadAddAlo = (TextView) findViewById(R.id.txtCiudadAddAlo);
        this.txtCiudadAddAlo.setVisibility(View.INVISIBLE);


        this.spnTipoAddAlo = (Spinner) findViewById(R.id.spnTipoAddAlo);

        this.ibtnCamaraAdd = (ImageButton) findViewById(R.id.ibtnCamaraAdd);
        this.ibtnCamaraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Foto f = new Foto();
                f.setNombre("Foto1");
                f.setDescripcion("Esta es una descripción muy corta");
                fotos.add(f);

                Drawable originalDrawable = getResources().getDrawable(R.drawable.imagencasa);
                Bitmap fotoBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                fotosB.add(fotoBitmap);

                Foto f2 = new Foto();
                f2.setNombre("Foto2");
                f2.setDescripcion("Esta es una descripción muy corta x2");
                fotos.add(f2);

                Drawable originalDrawable2 = getResources().getDrawable(R.drawable.logo);
                Bitmap fotoBitmap2 = ((BitmapDrawable) originalDrawable2).getBitmap();
                fotosB.add(fotoBitmap2);

                txtFotosAddAlo.setText(fotos.size()+" Fotos");
                txtFotosAddAlo.setVisibility(View.VISIBLE);
            }
        });

        this.ibtnUbicacionAdd = (ImageButton) findViewById(R.id.ibtnUbicacionAdd);
        this.ibtnUbicacionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localizacion.setCiudad("Bogotá");
                localizacion.setDireccion("Carrera 111 Calle 152 D - 65");
                localizacion.setLatitud(4.6758818);
                localizacion.setLongitud(-74.1535071);
                localizacion.setPais("Colombia");

                txtCiudadAddAlo.setText(localizacion.getCiudad());
                txtCiudadAddAlo.setVisibility(View.VISIBLE);
            }
        });

        this.btnGuardarAlo = (Button) findViewById(R.id.btnGuardarAlo);
        //Toast.makeText(AddAlojamientoActivity.this, "aqui33", Toast.LENGTH_SHORT).show();
        this.btnGuardarAlo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(AddAlojamientoActivity.this, "aqui xd", Toast.LENGTH_SHORT).show();


                myRef = database.getReference(PATH_ALOJAMIENTOS);
                String key = myRef.push().getKey();

                myRef = database.getReference(PATH_ALOJAMIENTOS+key);
                myRef.setValue(alo);

                for(int i=0; i < fotos.size();i++){

                    myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/fotos/");
                    String keyF = myRef.push().getKey();
                    myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/fotos/"+keyF);
                    myRef.setValue(fotos.get(i));
                    cargarFoto(fotosB.get(i),keyF,fotos.get(i).getNombre());
                }


                myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/localizacion/");
                String keyL = myRef.push().getKey();
                myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/localizacion/"+keyL);
                myRef.setValue(localizacion);

                finish();
                //Intent intent = new Intent(view.getContext(),HomeAnfitrionActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void cargarFoto(Bitmap bitmap,String destino, String nombre){
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File("alojamientos/"+nombre+".jpg"));
/*
        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap bitmap = ((BitmapDrawable) originalDrawable).getBitmap();
*/

        StorageReference imageRef = storageRef.child("alijamientos/"+destino+"/"+file.getLastPathSegment());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        final byte[] foto = baos.toByteArray();

        imageRef.putBytes(foto);

    }
}
