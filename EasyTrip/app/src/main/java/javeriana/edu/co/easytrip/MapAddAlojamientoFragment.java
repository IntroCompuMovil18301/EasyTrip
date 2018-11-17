package javeriana.edu.co.easytrip;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javeriana.edu.co.modelo.Localizacion;

public class MapAddAlojamientoFragment extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private String nombreAlo;
    private Geocoder mGeocoder;
    private ImageButton btnBuscarAloMap;
    private ImageButton btnGuardarAloMap;
    private EditText txtBuscarAloMap;
    private Localizacion localizacion;


    private final static int LOCALIZATION_PERMISSION = 200;

    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude= -78.903968;
    public static final double upperRightLatitude= 11.983639;
    public static final double upperRigthLongitude= -71.869905;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map_add_alojamiento);

        Bundle b = (Bundle) getIntent().getBundleExtra("bundle");
        this.nombreAlo = b.getString("nombreAlo");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationRequest = createLocationRequest();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.mGeocoder = new Geocoder(getBaseContext());

        this.localizacion = new Localizacion();

        this.btnGuardarAloMap = (ImageButton) findViewById(R.id.btnGuardarAloMap);
        this.btnGuardarAloMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarLocalizacion();
            }
        });

        this.txtBuscarAloMap = (EditText) findViewById(R.id.txtBuscarAloMap);
        this.btnBuscarAloMap = (ImageButton) findViewById(R.id.btnBuscarAloMap);
        this.btnBuscarAloMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String addressString = txtBuscarAloMap.getText().toString();

                if (!addressString.isEmpty()) {
                    try {
                        List<Address> addresses = mGeocoder.getFromLocationName( addressString, 2,
                                lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRigthLongitude);

                        //List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address addressResult = addresses.get(0);

                            LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());

                            mMap.clear();
                            /*
                            Marker home = mMap.addMarker(new MarkerOptions()
                                    .position(position).title(nombreAlo)
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            */

                            Marker home = mMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.iconalojamientomap)));

                            home.setVisible(true);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                            //Toast.makeText(MapAddAlojamientoFragment.this,addresses.get(0).getAddressLine(0)+"",Toast.LENGTH_SHORT).show();


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita acceder a los contactos", LOCALIZATION_PERMISSION);


        //En OnCreate()
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location != null) {
                    Toast.makeText(MapAddAlojamientoFragment.this, location.getLatitude()+"", Toast.LENGTH_LONG).show();
                    //latitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
                    //longitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
                    //altitude.setText("Altitude: " + String.valueOf(location.getAltitude()));
                }
            }
        };

        //startLocationUpdates();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng bogota = new LatLng(4.65, -74.05);
        //mMap.addMarker(new MarkerOptions().position(bogota).title(nombreAlo));
        mMap.clear();


        Marker home = mMap.addMarker(new MarkerOptions()
                .position(bogota).title(nombreAlo)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.iconalojamientomap)));


        /*
        Marker home = mMap.addMarker(new MarkerOptions()
                .position(bogota).title(nombreAlo)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        */
        home.setVisible(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                LatLng touched = new LatLng(point.latitude, point.longitude);

                mMap.clear();

                try {



                    List<Address> addresses = null;

                    addresses = mGeocoder.getFromLocation(point.latitude, point.longitude, 3);

                    /*
                    Marker home = mMap.addMarker(new MarkerOptions()
                            .position(touched).title(addresses.get(0).getAddressLine(0))
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    */
                    Marker home = mMap.addMarker(new MarkerOptions()
                            .position(touched)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.iconalojamientomap)));

                    //Toast.makeText(MapAddAlojamientoFragment.this,addresses.get(0).getAddressLine(0)+"",Toast.LENGTH_SHORT).show();

                    home.setVisible(true);

                    localizacion.setLongitud(point.longitude);
                    localizacion.setLatitud(point.latitude);
                    localizacion.setDireccion(addresses.get(0).getAddressLine(0));
                    localizacion.setCiudad(addresses.get(0).getLocality());
                    localizacion.setPais(addresses.get(0).getCountryName());
                    localizacion.setSubLocalidad(addresses.get(0).getSubLocality());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        startLocationUpdates();
    }



    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(5000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private void startLocationUpdates() {
//Verificación de permiso!!
        //Toast.makeText(MapAddAlojamientoFragment.this,"Permisos",Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new
                    OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                LatLng bogota = new LatLng(location.getLatitude(), location.getLongitude());

                                //mMap.addMarker(new MarkerOptions().position(bogota).title(nombreAlo));

                                /*
                                Marker home = mMap.addMarker(new MarkerOptions()
                                        .position(bogota)
                                        .icon(BitmapDescriptorFactory
                                                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                */

                                Marker home = mMap.addMarker(new MarkerOptions()
                                        .position(bogota)
                                        .icon(BitmapDescriptorFactory
                                                .fromResource(R.drawable.iconalojamientomap)));

                                home.setVisible(true);
                                List<Address> addresses = null;

                                try {

                                    addresses = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 3);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //Toast.makeText(MapAddAlojamientoFragment.this,addresses.get(0).getAddressLine(0)+"+++",Toast.LENGTH_SHORT).show();

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
                                mMap.moveCamera(CameraUpdateFactory.zoomTo(18));


                                localizacion.setLongitud(location.getLongitude());
                                localizacion.setLatitud(location.getLatitude());
                                localizacion.setDireccion(addresses.get(0).getAddressLine(0));
                                localizacion.setCiudad(addresses.get(0).getLocality());
                                localizacion.setPais(addresses.get(0).getCountryName());
                                localizacion.setSubLocalidad(addresses.get(0).getSubLocality());
                            }
                        }});

            /*

        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(); //Todas las condiciones para recibir localizaciones
            }
        });

        */
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==200){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(getApplicationContext(),"Permisos",Toast.LENGTH_SHORT).show();
                startLocationUpdates();

            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(MapAddAlojamientoFragment.this);
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

    private void guardarLocalizacion(){

        Intent returnIntent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("localizacion",localizacion);
        //b.putString("localizacion","esta es la prueba");
        returnIntent.putExtra("bundle",b);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


}

