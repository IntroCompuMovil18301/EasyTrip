package javeriana.edu.co.easytrip;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Localizacion;

public class MapAloCercanos extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Geocoder mGeocoder;
    private ImageButton btnBuscarAloMap;
    private ImageButton btnGuardarAloMap;
    private EditText txtBuscarAloMap;
    private Localizacion localizacion;
    private LatLng puntoActu;
    private Marker markerActu;
    private List<Alojamiento> alojamientos;
    public static final int ID_PERMISSION_LOCATION = 1;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefCal;
    private Location location;
    protected static final int RADIUS_OF_EARTH_KM = 6371;
    private JSONObject jsonObject;


    private final static int LOCALIZATION_PERMISSION = 200;

    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude= -78.903968;
    public static final double upperRightLatitude= 11.983639;
    public static final double upperRigthLongitude= -71.869905;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map_add_alojamiento);

        alojamientos = new ArrayList<Alojamiento>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeocoder = new Geocoder(getBaseContext());

        mLocationRequest = createLocationRequest();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        database= FirebaseDatabase.getInstance();

        mLocationCallback = new LocationCallback(){
            @Override
            public void  onLocationResult(LocationResult locationResult){
                location = locationResult.getLastLocation();
                if(location != null){
                    puntoActual(location.getLatitude(),location.getLongitude());
                }
            }
        };

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

                        if (addresses != null && !addresses.isEmpty()) {
                            Address addressResult = addresses.get(0);

                            LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());

                            mMap.clear();

                            Marker home = mMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.iconalojamientomap)));

                            home.setVisible(true);

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita acceder a los contactos", LOCALIZATION_PERMISSION);
        permisoConsedido();
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
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        alojamientosDosKilometors();
/*
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String url = "http://maps.googleapis.com/maps/api/directions/json?origin="+location.getLatitude()+","+location.getLongitude()+"&destination="+marker.getPosition().latitude+","+marker.getPosition().longitude;

                RequestQueue queue = Volley.newRequestQueue(MapAloCercanos.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            Log.i("jsonTuta",""+response);
                            trazarRuta(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(stringRequest);

                return false;
            }
        });
        */
    }
/*
    private void trazarRuta(JSONObject jsonObject) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try{
            jRoutes = jsonObject.getJSONArray("routes");
            for(int i=0; i<jRoutes.length();i++){
                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
                for(int j=0; j<jLegs.length();j++){
                    jSteps = ((JSONObject)(jLegs.get(j))).getJSONArray("steps");
                    for(int k=0; k<jSteps.length();k++){

                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.RED).width(5));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(5000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
        switch (requestCode){
            case ID_PERMISSION_LOCATION : {
                if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Ya hay permiso para acceder a la localizacion", Toast.LENGTH_LONG).show();
                    requestLocation();
                }else{
                    Toast.makeText(this, "No hay permiso", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(this);
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
        returnIntent.putExtra("bundle",b);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

    public void puntoActual(double lat, double lng){

        if(markerActu != null){
            markerActu.remove();
        }

        puntoActu = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(puntoActu));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        markerActu = mMap.addMarker(new MarkerOptions().position(puntoActu).title("Marcador en Bogotá"));

    }

    private void requestLocation(){
        if((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        puntoActual(location.getLatitude(),location.getLongitude());
                    }
                }
            });
        }
    }

    private void startLocationUpdates(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS : {
                if (resultCode == RESULT_OK) {
                    startLocationUpdates(); //Se encendió la localización!!!
                } else {
                    Toast.makeText(this,"Sin acceso a localización, hardware deshabilitado!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void permisoConsedido(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                int statusCode = ((ApiException)e).getStatusCode();
                switch (statusCode){
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(MapAloCercanos.this, REQUEST_CHECK_SETTINGS);
                        }catch (IntentSender.SendIntentException sendEx){

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        })  ;
    }

    private  boolean radioDosKilometros(double lat1, double long1, double lat2, double long2){
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        if(result < 2){
            return true;
        }else{
            return false;
        }
    }

    private void alojamientosDosKilometors(){
        myRef = database.getReference("alojamientos/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    final Alojamiento a = singleSnapshot.getValue(Alojamiento.class);
                    a.setId(singleSnapshot.getKey());
                    myRefCal = database.getReference("alojamientos/" + a.getId() + "/localizacion/");
                    myRefCal.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                Localizacion l = singleSnapshot.getValue(Localizacion.class);
                                if (radioDosKilometros(l.getLatitud(), l.getLongitud(), location.getLatitude(), location.getLongitude())) {
                                    a.setLocaliza(l);
                                    alojamientos.add(a);
                                }
                            }
                            ponerMarcas();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void ponerMarcas(){
        for(Alojamiento a : alojamientos){
            LatLng latLng = new LatLng(a.getLocaliza().getLatitud(),a.getLocaliza().getLongitud());
            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.iconalojamientomap)));
            m.setTitle(a.getNombre());
        }
    }
}