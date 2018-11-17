package javeriana.edu.co.easytrip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Reserva;

public class BuscarAlojamientoActivity extends AppCompatActivity {

    private ListView listAloBusqueda;
    private ListView listMashesBusqueda;
    private ImageButton btnBuscarAlojamiento;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefCal;
    private List<Alojamiento> alojamientos;
    private AdaptadorAlojamientosCercanos adaptador;
    private AdaptadorMashBusqueda adapMash;
    private String nombreUsuario;
    private ArrayList<Reserva> reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alojamiento);

        Bundle b = getIntent().getBundleExtra("bundle");
        this.nombreUsuario = b.getString("nombreUsuario");

        this.listMashesBusqueda = (ListView) findViewById(R.id.listMashesBusqueda);

        this.database= FirebaseDatabase.getInstance();
        this.listAloBusqueda = (ListView) findViewById(R.id.listAloBusqueda);

        this.alojamientos = new ArrayList<Alojamiento>();
        this.reservas = new ArrayList<Reserva>();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(BuscarAlojamientoActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                if( position == 0){
                    showQueryDateDialog(BuscarAlojamientoActivity.this);
                }

                if( position == 1){
                    showQueryLocationDialog(BuscarAlojamientoActivity.this);
                }

                if( position == 2){
                    showQueryMoneyDialog(BuscarAlojamientoActivity.this);
                }

                if( position == 3){
                    showQueryTypeDialog();
                }


            }
        });
    }

    private void showQueryLocationDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        //taskEditText.setPadding(70,0,70,0);

        //LayoutParams lp = (RelativeLayout.LayoutParams) taskEditText.getLayoutParams();
        //lp.setMargins(30,0,30,0);
        //taskEditText.setLayoutParams(lp);

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Búsqueda por ubicación")
                .setMessage("Ingrese la dirección o nombre")
                .setView(taskEditText)
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }


    private void showQueryDateDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        //taskEditText.setPadding(20,0,20,0);
        taskEditText.setHint("dd-mm-aaaa");

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Búsqueda por fecha")
                .setMessage("Ingrese la fecha")
                .setView(taskEditText)
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        //DateFormat dateFormat = dateFormat = new SimpleDateFormat("dd-mm-aaaa", Locale.getDefault());

                            //Date fecha=  dateFormat.parse("1997-07-19");
                            //Date fecha =  dateFormat.parse(task);
                            cargarAlojamientosDate(task);



                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }


    private void showQueryMoneyDialog(Context c) {

        final EditText taskEditText = new EditText(c);
        //taskEditText.setPadding(20,0,20,0);
        taskEditText.setHint("#000");

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Búsqueda por rango de dinero")
                .setMessage("La búsqueda se hace con 30000 de desviación")
                .setView(taskEditText)
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        cargarAlojamientosMoney(Double.parseDouble(task));

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }


    private void showQueryTypeDialog() {
        final CharSequence[] opciones={"Apartamento","Cabaña","Casa","Habitación"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(BuscarAlojamientoActivity.this);
        alertOpciones.setTitle("Selección el tipo para la Búsqueda");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Apartamento")){
                        cargarAlojamientosTipo("Apartamento");
                        cargarReservasType("Apartamento");
                }

                if (opciones[i].equals("Cabaña")){
                    cargarAlojamientosTipo("Cabaña");
                    cargarReservasType("Cabaña");
                }

                if (opciones[i].equals("Casa")){
                    cargarAlojamientosTipo("Casa");
                    cargarReservasType("Casa");
                }
                if (opciones[i].equals("Habitación")){
                    cargarAlojamientosTipo("Habitación");
                    cargarReservasType("Habitación");

                }
                else{
                        dialogInterface.dismiss();
                }

            }
        });
        alertOpciones.show();

    }


    public void cargarAlojamientosTipo(final String tipo) {

        alojamientos.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("alojamientos/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Alojamiento a = singleSnapshot.getValue(Alojamiento.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                    a.setId(singleSnapshot.getKey());
                    if(a.getTipo().compareTo(tipo) == 0){
                        boolean esta = false;
                        alojamientos.add(a);
                    }


                }

                ArrayList<Alojamiento> arrayItems = new ArrayList<>();

                for(Alojamiento a : alojamientos){
                    arrayItems.add(a);
                    //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(this,"aqui -"+nombreUsuario , Toast.LENGTH_SHORT).show();
                adaptador = new AdaptadorAlojamientosCercanos(arrayItems, BuscarAlojamientoActivity.this,nombreUsuario);
                listAloBusqueda.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Alojamiento>arrayItems = new ArrayList<>();

        for(Alojamiento a : alojamientos){
            arrayItems.add(a);
            //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
        }
        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());

    }


    public void cargarAlojamientosMoney(final Double money) {

        alojamientos.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("alojamientos/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Alojamiento a = singleSnapshot.getValue(Alojamiento.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                    a.setId(singleSnapshot.getKey());
                    if( (a.getCosto()- 30000.0) <= money || (a.getCosto()+ 30.0) >= money ){
                        boolean esta = false;
                        alojamientos.add(a);
                    }


                }

                ArrayList<Alojamiento> arrayItems = new ArrayList<>();

                for(Alojamiento a : alojamientos){
                    arrayItems.add(a);
                    //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(this,"aqui -"+nombreUsuario , Toast.LENGTH_SHORT).show();
                adaptador = new AdaptadorAlojamientosCercanos(arrayItems, BuscarAlojamientoActivity.this,nombreUsuario);
                listAloBusqueda.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Alojamiento>arrayItems = new ArrayList<>();

        for(Alojamiento a : alojamientos){
            arrayItems.add(a);
            //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
        }
        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());

    }


    public void cargarAlojamientosDate(final String fecha) {

        alojamientos.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("alojamientos/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    final Alojamiento a = singleSnapshot.getValue(Alojamiento.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                    a.setId(singleSnapshot.getKey());

                    /*
                    if(a.getTipo().compareTo(tipo) == 0){
                        boolean esta = false;
                        alojamientos.add(a);
                    }
                    */

                    myRefCal = database.getReference("calendarios/"+a.getId()+"/");
                    myRefCal.addListenerForSingleValueEvent(new ValueEventListener() {



                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                            boolean esta = false;

                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                Date f = singleSnapshot.getValue(Date.class);

                                Calendar calSelected = Calendar.getInstance();
                                calSelected.setTime(f);

                                String date = calSelected.get(Calendar.DAY_OF_MONTH)
                                        + "-" + (calSelected.get(Calendar.MONTH) + 1)
                                        + "-" + calSelected.get(Calendar.YEAR);

                                if(date.compareTo(fecha) == 0){
                                    esta=true;
                                }


                            }


                            if(!esta){
                                alojamientos.add(a);
                                Toast.makeText(BuscarAlojamientoActivity.this, alojamientos.size()+"++" , Toast.LENGTH_SHORT).show();
                            }
                            actualizar();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    }

                ArrayList<Alojamiento> arrayItems = new ArrayList<>();

                for(Alojamiento a : alojamientos){
                    arrayItems.add(a);
                    //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(this,"aqui -"+nombreUsuario , Toast.LENGTH_SHORT).show();
                adaptador = new AdaptadorAlojamientosCercanos(arrayItems, BuscarAlojamientoActivity.this,nombreUsuario);
                listAloBusqueda.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Alojamiento>arrayItems = new ArrayList<>();

        for(Alojamiento a : alojamientos){
            arrayItems.add(a);
            //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
        }
        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());

    }

    public void actualizar(){
        ArrayList<Alojamiento> arrayItems = new ArrayList<>();

        for(Alojamiento a : alojamientos){
            arrayItems.add(a);
            //Toast.makeText(getContext(),a.getNombre() , Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this,"aqui -"+nombreUsuario , Toast.LENGTH_SHORT).show();
        adaptador = new AdaptadorAlojamientosCercanos(arrayItems, BuscarAlojamientoActivity.this,nombreUsuario);
        listAloBusqueda.setAdapter(adaptador);

    }

    public void cargarReservasType(final String tipo) {

        reservas.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("reservas/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Reserva r = singleSnapshot.getValue(Reserva.class);

                    //if(r.getIdUsuario().compareTo(user.getUid()) == 0){
                    r.setId(singleSnapshot.getKey());
                    if(r.getTipoAlo().compareTo(tipo) == 0  && r.getEstado().compareTo("Aceptada") == 0 ){
                        reservas.add(r);
                    }

                    //Toast.makeText(getContext(), r.getNombreAlo(), Toast.LENGTH_SHORT).show();
                    //}
                }

                actualizarReservas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();

        //adaptador = new AdaptadorAlojamientos(getArrayItems(), this.getContext());


    }

    private void actualizarReservas(){
        Toast.makeText(this, nombreUsuario+"!!!", Toast.LENGTH_SHORT).show();
        adapMash= new AdaptadorMashBusqueda(this.reservas, this, nombreUsuario);
        listMashesBusqueda.setAdapter(adapMash);
    }

}
