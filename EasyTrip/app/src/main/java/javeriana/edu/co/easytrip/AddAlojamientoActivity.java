package javeriana.edu.co.easytrip;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Foto;
import javeriana.edu.co.modelo.Localizacion;

public class AddAlojamientoActivity extends AppCompatActivity {

    private final static int LOCALIZATION_PERMISSION = 200;

    public static final String PATH_ALOJAMIENTOS="alojamientos/";

    private FusedLocationProviderClient mFusedLocationClient;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private Alojamiento alo;
    private Bitmap foto;
    private Uri imageUri;
    private GoogleMap mapaAlo;

    private EditText txtNumeroAddAlo;
    private EditText txtNombreAddAlo;
    private EditText txtValorAddAlo;
    private EditText txtDescripcionAddAlo;
    private TextView txtNumeroHabiAlo;
    private TextView txtCiudadAddAlo;
    private ImageButton ibtnCamaraAdd;
    private ImageButton ibtnUbicacionAdd;
    private Button btnGuardarAlo;
    private Spinner spnTipoAddAlo;
    private Spinner spnBanoAddAlo;
    private List<Foto> fotos;
    private List<Bitmap> fotosB;
    private Localizacion localizacion;
    private FirebaseAuth mAuth;

    private LinearLayout linearFotosAdd;
    private ViewPager vpMapAddAlo;
    private Geocoder mGeocoder;

    private RadioButton rbtnSerParqueaderoAdd;
    private RadioButton rbtnSerWifiAdd;
    private RadioButton rbtnSerHabiAdd;
    private RadioButton rbtnSerCafeAdd;
    private RadioButton rbtnSerSalaNinosAdd;
    private RadioButton rbtnSerGimnacioAdd;
    private RadioButton rbtnSerPiscinaAdd;
    private RadioButton rbtnSerSPAAdd;


    private RadioButton rbtnRefrigeradorAdd;
    private RadioButton rbtnMicrohondasAdd;
    private RadioButton rbtnJacuzziAdd;
    private RadioButton rbtnTeatroAdd;
    private RadioButton rbtnSonidoAdd;
    private RadioButton rbtnTelevisionAdd;



    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_IMAGE = 100;
    private static final int MAP_LOCATION = 500;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alojamiento);
        this.alo = new Alojamiento();
        this.database= FirebaseDatabase.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        this.rbtnSerParqueaderoAdd = (RadioButton) findViewById(R.id.rbtnSerParqueaderoAdd);
        this.rbtnSerWifiAdd = (RadioButton) findViewById(R.id.rbtnSerWifiAdd);
        this.rbtnSerHabiAdd = (RadioButton) findViewById(R.id.rbtnSerHabiAdd);
        this.rbtnSerCafeAdd = (RadioButton) findViewById(R.id.rbtnSerCafeAdd);
        this.rbtnSerSalaNinosAdd = (RadioButton) findViewById(R.id.rbtnSerSalaNinosAdd);
        this.rbtnSerGimnacioAdd = (RadioButton) findViewById(R.id.rbtnSerGimnasioAdd);
        this.rbtnSerPiscinaAdd = (RadioButton) findViewById(R.id.rbtnSerPiscinaAdd);
        this.rbtnSerSPAAdd = (RadioButton) findViewById(R.id.rbtnSerSPAAdd);

        this.rbtnMicrohondasAdd = (RadioButton) findViewById(R.id.rbtnMicrohondasAdd);
        this.rbtnJacuzziAdd = (RadioButton) findViewById(R.id.rbtnJacuzziAdd);
        this.rbtnTeatroAdd = (RadioButton) findViewById(R.id.rbtnTeatroAdd);
        this.rbtnSonidoAdd = (RadioButton) findViewById(R.id.rbtnSonidoAdd);
        this.rbtnTelevisionAdd = (RadioButton) findViewById(R.id.rbtnTelevisionAdd);
        this.rbtnRefrigeradorAdd = (RadioButton) findViewById(R.id.rbtnRefrigeradorAdd);




        //----------------------------------------
        this.fotos = new ArrayList<Foto>();
        this.fotosB = new ArrayList<Bitmap>();

        this.localizacion = new Localizacion();
        this.mGeocoder = new Geocoder(getBaseContext());

        //this.mapaAlo =(GoogleMap) this.finf(¿R.id.mapAddAloja);           --------------------------------------------------
        this.linearFotosAdd = (LinearLayout) findViewById(R.id.linearFotosAdd);
        this.txtNombreAddAlo = (EditText) findViewById(R.id.txtNombreAddAlo);
        this.txtNumeroAddAlo = (EditText) findViewById(R.id.txtNumeroAddAlo);
        this.txtValorAddAlo = (EditText) findViewById(R.id.txtValorAddAlo);
        this.txtDescripcionAddAlo = (EditText) findViewById(R.id.txtDescripcionAddAlo);
        this.txtNumeroHabiAlo = (EditText) findViewById(R.id.txtNumeroHabiAlo);


        //this.mapaAlo = this.<View>findViewById(R.id.map);
        //this.mapaAlo = new MapAddAlojamientoFragment().;

      //  this.txtFotosAddAlo.setVisibility(View.INVISIBLE);
        this.txtCiudadAddAlo = (TextView) findViewById(R.id.txtCiudadAddAlo);
        this.txtCiudadAddAlo.setVisibility(View.INVISIBLE);


        this.spnTipoAddAlo = (Spinner) findViewById(R.id.spnTipoAddAlo);
        this.spnBanoAddAlo = (Spinner) findViewById(R.id.spnBanoAddAlo);

        this.ibtnCamaraAdd = (ImageButton) findViewById(R.id.ibtnCamaraAdd);
        this.ibtnCamaraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();

                }

        });

        this.ibtnUbicacionAdd = (ImageButton) findViewById(R.id.ibtnUbicacionAdd);
        this.ibtnUbicacionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //localizacion.setCiudad("Bogotá");
               // localizacion.setDireccion("Carrera 111 Calle 152 D - 65");
               // localizacion.setLatitud(4.6758818);
                //localizacion.setLongitud(-74.1535071);
               // localizacion.setPais("Colombia");



                buscarUbicacion();
//                Intent intent = new Intent(view.getContext(),MapAddAlojamientoFragment.class);
                //              startActivity(intent);


            }
        });

        this.btnGuardarAlo = (Button) findViewById(R.id.btnGuardarAlo);
        this.btnGuardarAlo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                alo.setNombre(txtNombreAddAlo.getText().toString());
                alo.setCosto(Double.parseDouble(txtValorAddAlo.getText().toString()));
                alo.setDescripcion(txtDescripcionAddAlo.getText().toString());
                alo.setMaxHuespedes(Integer.parseInt(txtNumeroAddAlo.getText().toString().trim()));
                alo.setTipo(spnTipoAddAlo.getSelectedItem().toString());
                alo.setValoracion(0.0);
                alo.setIdUsuario(user.getUid());
                alo.setNumHabitaciones(Integer.parseInt(txtNumeroHabiAlo.getText().toString()));
                alo.setBano(spnBanoAddAlo.getSelectedItem().toString());

                if(rbtnSerParqueaderoAdd.isChecked()){  alo.setSerParqueadero(true);} else { alo.setSerParqueadero(false);}
                if(rbtnSerWifiAdd.isChecked()){  alo.setWifi(true);} else { alo.setWifi(false);}
                if(rbtnSerHabiAdd.isChecked()){  alo.setSerHabitacion(true);} else { alo.setSerHabitacion(false);}
                if(rbtnSerCafeAdd.isChecked()){  alo.setCafeBar(true);} else { alo.setCafeBar(false);}
                if(rbtnSerSalaNinosAdd.isChecked()){  alo.setSalaNinos(true);} else { alo.setSalaNinos(false);}
                if(rbtnSerGimnacioAdd.isChecked()){  alo.setGimnasio(true);} else { alo.setGimnasio(false);}
                if(rbtnSerPiscinaAdd.isChecked()){  alo.setPiscina(true);} else { alo.setPiscina(false);}
                if(rbtnSerSPAAdd.isChecked()){  alo.setSpa(true);} else { alo.setSpa(false);}


                if(rbtnRefrigeradorAdd.isChecked()){  alo.setRefrigerador(true);} else { alo.setRefrigerador(false);}
                if(rbtnMicrohondasAdd.isChecked()){  alo.setMicrohondas(true);} else { alo.setMicrohondas(false);}
                if(rbtnJacuzziAdd.isChecked()){  alo.setJacuzzi(true);} else { alo.setJacuzzi(false);}
                if(rbtnTeatroAdd.isChecked()){  alo.setTeatro(true);} else { alo.setTeatro(false);}
                if(rbtnSonidoAdd.isChecked()){  alo.setEquipoSonido(true);} else { alo.setEquipoSonido(false);}
                if(rbtnTelevisionAdd.isChecked()){  alo.setTelevision(true);} else { alo.setTelevision(false);}


                myRef = database.getReference(PATH_ALOJAMIENTOS);
                String key = myRef.push().getKey();

                myRef = database.getReference(PATH_ALOJAMIENTOS+key);
/*
                Drawable originalDrawable = getResources().getDrawable(R.drawable.imagencasa);
                Bitmap fotoBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

                Drawable originalDrawable2 = getResources().getDrawable(R.drawable.personauno);
                Bitmap fotoBitmap2 = ((BitmapDrawable) originalDrawable).getBitmap();
*/
                myRef.setValue(alo);
                int i=0;
                for(Bitmap f: fotosB){
                Foto foto = new Foto();
                foto.setNombre("Image"+i);

                    myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/fotos/");
                    String keyF = myRef.push().getKey();
                    myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/fotos/"+keyF);

                    myRef.setValue(foto);

                    //if(i%2 == 0){
                        //Drawable originalDrawable = getResources().getDrawable(R.drawable.imagencasa);
                        //Bitmap fotoBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                        cargarFoto(f, key,foto.getNombre());
                    //}
                    /*else{
                        //Drawable originalDrawable = getResources().getDrawable(R.drawable.personauno);
                        //Bitmap fotoBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                        cargarFoto(f, key,foto.getNombre());

                    }*/
                    i++;

                }


                myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/localizacion/");
                String keyL = myRef.push().getKey();
                myRef = database.getReference(PATH_ALOJAMIENTOS+key+"/localizacion/"+keyL);
                myRef.setValue(localizacion);

                finish();
            }
        });
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita acceder a los contactos", LOCALIZATION_PERMISSION);
    }

    private void cargarFoto(Bitmap bitmap,String destino, String nombre){
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File("alojamiento/"+nombre+".jpg"));

        StorageReference imageRef = storageRef.child("Alojamientos/"+destino+"/"+file.getLastPathSegment());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        final byte[] foto = baos.toByteArray();

        imageRef.putBytes(foto);

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void openCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            ImageView image = new ImageView(AddAlojamientoActivity.this);
            //image.getLayoutParams().height = 150;
            //image.getLayoutParams().width = 150;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,LinearLayout.LayoutParams.MATCH_PARENT);
            //lp.setMargins(0, 0, 10, 0);
            image.setLayoutParams(lp);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setImageBitmap(photo);
            linearFotosAdd.addView(image);
            fotosB.add(photo);


        }

        if(resultCode ==Activity.RESULT_OK && requestCode == PICK_IMAGE){

            try {
                imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //fotoPerfil.setImageURI(imageUri);


                ImageView image = new ImageView(AddAlojamientoActivity.this);
                //image.getLayoutParams().height = 150;
                //image.getLayoutParams().width = 150;

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,LinearLayout.LayoutParams.MATCH_PARENT);
                //lp.setMargins(0, 0, 10, 0);
                image.setLayoutParams(lp);
                image.setImageBitmap(bitmap);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                linearFotosAdd.addView(image);
                fotosB.add(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(resultCode ==Activity.RESULT_OK && requestCode == MAP_LOCATION){
            Bundle b = data.getExtras().getBundle("bundle");
            localizacion = (Localizacion) b.getSerializable("localizacion");
            txtCiudadAddAlo.setVisibility(View.VISIBLE);
            txtCiudadAddAlo.setText(localizacion.getCiudad()+" - "+localizacion.getSubLocalidad());
        }


    }

    private void cargarImagen() {
        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddAlojamientoActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    openCamera();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        openGallery();
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void buscarUbicacion() {
        final CharSequence[] opciones={"Ubicación actual","Buscar en mapa"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddAlojamientoActivity.this);
        alertOpciones.setTitle("Ingrese la ubicación del Alojamiento");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Ubicación actual")){
                    cargarLocaActual();
                }else{
                    if (opciones[i].equals("Buscar en mapa")){
                        Intent intent = new Intent(AddAlojamientoActivity.this,MapAddAlojamientoFragment.class);
                        Bundle b = new Bundle();
                        b.putString("nombreAlo", txtNombreAddAlo.getText().toString());
                        intent.putExtra("bundle",b);
                        //startActivity(intent);
                        startActivityForResult(intent,MAP_LOCATION);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void cargarLocaActual(){
        this.localizacion = new Localizacion();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new
                    OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                //LatLng bogota = new LatLng(location.getLatitude(), location.getLongitude());
                                //String addressString = location.getLatitude()+"  "    + location.getLongitude()+"";
                                List<Address> addresses = null;
                                try {
                                    // localizacion.setLatitud(4.6758818);
                                    //localizacion.setLongitud(-74.1535071);
                                    //addresses = mGeocoder.getFromLocation(4.6758818,  -74.1535071, 3);
                                    addresses = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 3);
                                    //String a="campi";
                                    //addresses = mGeocoder.getFromLocationName(a, 3);
                                    String address = "";
                                    if (addresses.size() > 0) {

                                        localizacion.setLongitud(location.getLongitude());
                                        localizacion.setLatitud(location.getLatitude());
                                        localizacion.setDireccion(addresses.get(0).getAddressLine(0));
                                        localizacion.setCiudad(addresses.get(0).getLocality());
                                        localizacion.setPais(addresses.get(0).getCountryName());
                                        localizacion.setSubLocalidad(addresses.get(0).getSubLocality());

                                        txtCiudadAddAlo.setVisibility(View.VISIBLE);
                                        txtCiudadAddAlo.setText(localizacion.getCiudad()+" - "+addresses.get(0).getSubLocality());

                                        //Toast.makeText(AddAlojamientoActivity.this, addresses.get(0).getLocality()+"", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(AddAlojamientoActivity.this, addresses.get(0).getCountryName()+"", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(AddAlojamientoActivity.this, addresses.get(0).getSubLocality()+"", Toast.LENGTH_SHORT).show();
                                    }
                                    //Toast.makeText(AddAlojamientoActivity.this, address, Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


  /*
                                if (!addressString.isEmpty()) {
                                    try {
                                        List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                                        if (addresses != null && !addresses.isEmpty()) {
                                            Address addressResult = addresses.get(0);

                                            LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                                            Toast.makeText(AddAlojamientoActivity.this, addressResult.getCountryName(), Toast.LENGTH_SHORT).show();
                                        } else {Toast.makeText(AddAlojamientoActivity.this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {Toast.makeText(AddAlojamientoActivity.this, "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
*/


                            }
                        }});

        }else{
            requestPermission(AddAlojamientoActivity.this,Manifest.permission.ACCESS_FINE_LOCATION,"",LOCALIZATION_PERMISSION);
        }
    }


    //-----------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==200){
            if(grantResults.length==2 && grantResults[0]== PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){

                cargarLocaActual();

            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddAlojamientoActivity.this);
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


    private void requestPermission(Activity context, String permission, String explanation, int requestId ){
        if (ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?Â  Â
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }
    }

/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapaAlo = googleMap;
        LatLng bogota = new LatLng(4.65, -74.05);
        mapaAlo.addMarker(new MarkerOptions().position(bogota).title("Marcador en Bogotá"));



        Marker bogotaHome = mMap.addMarker(new MarkerOptions()
                .position(bogota)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.iconmash)));



        mMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);


        Marker home = mapaAlo.addMarker(new MarkerOptions()
                .position(bogota)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        home.setVisible(true);
        mapaAlo.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        mapaAlo.moveCamera(CameraUpdateFactory.zoomTo(25));

        mapaAlo.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                LatLng touched = new LatLng(point.latitude, point.longitude);
                Toast.makeText(AddAlojamientoActivity.this, point.latitude+"--"+point.longitude, Toast.LENGTH_LONG).show();
                mapaAlo.clear();
                Marker home = mapaAlo.addMarker(new MarkerOptions()
                        .position(touched)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                home.setVisible(true);

            }
        });
    }*/
}