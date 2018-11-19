package javeriana.edu.co.easytrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Huesped;
import javeriana.edu.co.modelo.Mash;
import javeriana.edu.co.modelo.Reserva;

public class VerReservaActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private ImageButton btnDescartarAnfi;
    private ImageButton btnAceptarAnfi;
    private ImageView imageView;
    private AdaptadorCompaneros adaptador;
    private ListView gridview;
    private Reserva reserva;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Mash> mashes;


    private TextView txtAlojamientoVR;
    private TextView txtNombreHuespedAnfi;
    private TextView txtEdadReservaAnfi;
    private TextView txtCalificacionReservaAnfi;
    private TextView txtFechaInicioReservaAnfi;
    private TextView txtFechaFinReservaAnfi;
    private TextView txtDiasReservaAnfi;
    private TextView txtCostoReservaAnfi;
    private LinearLayout linearButtonsVerReservaAnfi;
    private ImageButton btnCheckout;
    private Button btnSolicitarMash;
    private String origin;
    private String nombreUsuario;


 /*   txtAlojamientoVR
    txtNombreHuespedAnfi
    txtEdadReservaAnfi
    txtCalificacionReservaAnfi
    txtFechaInicioReservaAnfi
    txtFechaFinReservaAnfi
    txtDiasReservaAnfi
    txtCostoReservaAnfi
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reserva);

        //------------------------------------------------------------------------------------------
        Bundle b = getIntent().getBundleExtra("bundle");
        this.reserva = (Reserva) b.getSerializable("reserva");
        this.origin = b.getString("origin");
        this.mashes = new ArrayList<Mash>();
        this.database = FirebaseDatabase.getInstance();
        if(this.origin.compareTo("mash") == 0){
            this.nombreUsuario = b.getString("nombreUsuario");
        }

        this.storage = FirebaseStorage.getInstance();



        this.txtAlojamientoVR = (TextView) findViewById(R.id.txtAlojamientoVR);
        this.txtNombreHuespedAnfi = (TextView) findViewById(R.id.txtNombreHuespedAnfi);
        this.txtEdadReservaAnfi = (TextView) findViewById(R.id.txtEdadReservaAnfi);
        this.txtCalificacionReservaAnfi = (TextView) findViewById(R.id.txtCalificacionReservaAnfi);
        this.txtFechaInicioReservaAnfi = (TextView) findViewById(R.id.txtFechaInicioReservaAnfi);
        this.txtFechaFinReservaAnfi = (TextView) findViewById(R.id.txtFechaFinReservaAnfi);
        this.txtDiasReservaAnfi = (TextView) findViewById(R.id.txtDiasReservaAnfi);
        this.txtCostoReservaAnfi = (TextView) findViewById(R.id.txtCostoReservaAnfi);
        this.linearButtonsVerReservaAnfi = (LinearLayout) findViewById(R.id.linearButtonsVerReservaAnfi);

        txtAlojamientoVR.setText(reserva.getNombreAlo());
        txtNombreHuespedAnfi.setText("");
        txtEdadReservaAnfi.setText("");
        txtFechaInicioReservaAnfi.setText(getFechaString(reserva.getFechaInicio()));
        txtFechaFinReservaAnfi.setText(getFechaString(reserva.getFechaFin()));
        txtDiasReservaAnfi.setText(reserva.getDias()+"");
        txtCostoReservaAnfi.setText(reserva.getCosto()+"00");


        this.gridview = (ListView) findViewById(R.id.listAcompaReserva);
        this.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerHuespedActivity.class);
                startActivity(intent);
            }
        });

        this.btnSolicitarMash = (Button) findViewById(R.id.btnSolicitarMash);
        this.btnSolicitarMash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitarMash();

            }
        });


        if(this.origin.compareTo("mash") == 0){
            this.btnSolicitarMash.setVisibility(View.VISIBLE);

        }else{
            this.btnSolicitarMash.setVisibility(View.INVISIBLE);
        }



        this.adaptador = new AdaptadorCompaneros(getArrayItems(), this);
        gridview.setAdapter(this.adaptador);


        //------------------------------------------------------------------------------------------
        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        imageView = (ImageView) findViewById(R.id.fotoPerfilHuspedAnfi);

        imageView.setImageDrawable(roundedDrawable);
        //------------------------------------------------------------------------------------------

        this.btnDescartarAnfi = (ImageButton) findViewById(R.id.btnDescartarAnfi);
        this.btnDescartarAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference("reservas/"+reserva.getId());
                reserva.setEstado("Rechazada");
                myRef.setValue(reserva);
                finish();
            }
        });
        this.btnDescartarAnfi = (ImageButton) findViewById(R.id.btnDescartarAnfi);

        this.btnAceptarAnfi = (ImageButton) findViewById(R.id.btnAceptarAnfi);
        this.btnAceptarAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference("reservas/"+reserva.getId());
                reserva.setEstado("Aceptada");
                myRef.setValue(reserva);
                finish();
            }
        });

        if(reserva.getEstado().compareTo("Solicitado") == 0){
            linearButtonsVerReservaAnfi.setVisibility(View.VISIBLE);
        }else{
            linearButtonsVerReservaAnfi.setVisibility(View.INVISIBLE);
        }

        this.btnCheckout = (ImageButton) findViewById(R.id.btnCheckout );


        

        loadUser();

    }


    //----------------------------------------------------------------------------------------------

    public ArrayList<EntityCompaneros> getArrayItems() {
        ArrayList<EntityCompaneros>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));

        return arrayItems;
    }

    private String getFechaString(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String fecha = cal.get(Calendar.DAY_OF_MONTH)
                + "/" + (cal.get(Calendar.MONTH) + 1)
                + "/" + cal.get(Calendar.YEAR);

        return fecha;
    }

    private void descargarFoto(String origen, String nombre){
        //Toast.makeText(this, nombre+"...", Toast.LENGTH_SHORT).show();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(origen+"/"+nombre+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


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

    public void loadUser() {
        myRef = database.getReference();
        myRef.child(FirebaseReference.HUESPESDES).orderByChild("nomUsuario").equalTo(reserva.getNombrePrincipal()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                        Huesped h = singlesnapshot.getValue(Huesped.class);
                        descargarFoto("ImagenesPerfil",reserva.getNombrePrincipal());
                        txtNombreHuespedAnfi.setText(h.getNombre());

                        DateFormat dateFormat = dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date fechaNacimiento = null;

                            try {
                                fechaNacimiento = dateFormat.parse("1997-07-19");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar cal = Calendar.getInstance();
                        Date fechaActual = cal.getTime();
                        txtEdadReservaAnfi.setText("("+getEdad(fechaNacimiento, fechaActual)+" Años) - " + h.getNacionalidad());
                        txtCalificacionReservaAnfi.setText("calificacion...");



                    }
                }else {
                    Toast.makeText(VerReservaActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VerReservaActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
    }


    private void solicitarMash(){
        Mash mash = new Mash();
        mash.setFechaInicio(reserva.getFechaInicio());
        mash.setFechaFin(reserva.getFechaFin());
        mash.setPorcentaje(-1.0);
        mash.setValor(-1.0);
        mash.setValoracion(-1.0);
        mash.setNombreUsuario(nombreUsuario);
        mash.setIdAlojamiento(reserva.getIdAlojamiento());
        mash.setNombrePrincipal(reserva.getNombrePrincipal()    );
        mash.setIdPropietario(reserva.getIdUsuario());
        mash.setIdPago("");
        mash.setEstado("Solicitado");

        mashes.add(mash);

        myRef = database.getReference("mashes/"+reserva.getId()+"/");
        reserva.setEstado("Aceptada");
        myRef.setValue(mashes);
        finish();

    }

    public void cargarMashes() {

        mashes.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("mashes/"+reserva.getId()+"/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Mash a = singleSnapshot.getValue(Mash.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                    mashes.add(a);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();
    }



    public  int getEdad(Date fechaNacimiento, Date fechaActual) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int dIni = Integer.parseInt(formatter.format(fechaNacimiento));
        int dEnd = Integer.parseInt(formatter.format(fechaActual));
        int age = (dEnd-dIni)/10000;
        return age;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(origin.compareTo("mash") == 0){
            cargarMashes();
        }
    }
}
