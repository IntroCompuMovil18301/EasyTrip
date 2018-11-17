package javeriana.edu.co.easytrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Reserva;

public class CalendarioReservarActivity extends AppCompatActivity {

    public static final String PATH_RESERVAS="reservas/";
    public static final String PATH_CALENDARIOS="calendarios/";


    private TextView txtFechaInicioCalReser;
    private TextView txtFechaFinCalReser;
    private TextView txtDiasCalReser;
    private TextView txtValorCalReser;
    private Button btnReservarCal;
    private String nombreUsuario;

    private CalendarPickerView datePicker;
    private List<Date> fechas;
    private List<Date> calendario;
    private Date fechaInicio;
    private Date fechaFin;
    private Alojamiento alojamiento;
    private String foto;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_reservar);

        this.database= FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();


        Bundle b = getIntent().getBundleExtra("bundle");
        this.alojamiento = (Alojamiento) b.getSerializable("alojamiento");
        this.nombreUsuario = b.getString("nombreUsuario");
        this.foto = "Image0";
        this.fechas = new ArrayList<Date>();
        this.calendario = new ArrayList<Date>();
        //this.alojamiento = (Alojamiento) getIntent().getSerializableExtra("alojamiento");

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        this.txtFechaInicioCalReser = (TextView) findViewById(R.id.txtFechaInicioCalReser);
        this.txtFechaFinCalReser = (TextView) findViewById(R.id.txtFechaFinCalReser);
        this.txtDiasCalReser = (TextView) findViewById(R.id.txtDiasCalReser);
        this.txtValorCalReser = (TextView) findViewById(R.id.txtValorCalReser);

        datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);



        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);


                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = calSelected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (calSelected.get(Calendar.MONTH) + 1)
                        + "/" + calSelected.get(Calendar.YEAR);

                //Toast.makeText(CalendarioReservarActivity.this, datePicker.getSelectedDates().toString(), Toast.LENGTH_SHORT).show();
                fechas = datePicker.getSelectedDates();
                actualizarFechas();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        this.btnReservarCal = (Button) findViewById(R.id.btnReservarCal);
        this.btnReservarCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();

                if(validateForm()){

                    Reserva reserva = new Reserva();
                    reserva.setIdAlojamiento(alojamiento.getId());
                    reserva.setFoto(foto);
                    reserva.setNombreAlo(alojamiento.getNombre());
                    reserva.setTipoAlo(alojamiento.getTipo());
                    reserva.setIdUsuario(mAuth.getUid());
                    reserva.setFechaInicio(fechaInicio);
                    reserva.setFechaFin(fechaFin);
                    reserva.setNombrePrincipal(nombreUsuario);
                    reserva.setDias(Integer.parseInt(txtDiasCalReser.getText().toString()));
                    reserva.setCosto(Double.parseDouble(txtValorCalReser.getText().toString()));
                    reserva.setEstado("Solicitado");

                    myRef = database.getReference(PATH_RESERVAS);
                    String key = myRef.push().getKey();

                    myRef = database.getReference(PATH_RESERVAS+key);

                    myRef.setValue(reserva);

                    actualizarCalendario();
                    //Toast.makeText(CalendarioReservarActivity.this, "hecho", Toast.LENGTH_SHORT).show();


                }

            }
        });

        actualizarFechas();

    }

    private void actualizarFechas(){
        if(fechas.size()>0){
            this.fechaInicio = fechas.get(0);
            this.fechaFin = fechas.get(fechas.size()-1);
            //Toast.makeText(CalendarioReservarActivity.this, fechaFin.toString(), Toast.LENGTH_SHORT).show();
            Calendar calSelected1 = Calendar.getInstance();
            calSelected1.setTime(fechaInicio);

            String inicio = calSelected1.get(Calendar.DAY_OF_MONTH)
                    + "/" + (calSelected1.get(Calendar.MONTH) + 1)
                    + "/" + calSelected1.get(Calendar.YEAR);

            Calendar calSelected2 = Calendar.getInstance();
            calSelected2.setTime(fechaFin);

            String fin = calSelected2.get(Calendar.DAY_OF_MONTH)
                    + "/" + (calSelected2.get(Calendar.MONTH) + 1)
                    + "/" + calSelected2.get(Calendar.YEAR);

            this.txtFechaInicioCalReser.setText(inicio);
            this.txtFechaFinCalReser.setText(fin);
            this.txtDiasCalReser.setText(fechas.size()+"");
            this.txtValorCalReser.setText((fechas.size()*alojamiento.getCosto()) + "00");

        }else{
            this.txtFechaInicioCalReser.setText("");
            this.txtFechaFinCalReser.setText("");
            this.txtDiasCalReser.setText("");
            this.txtValorCalReser.setText("");
        }


    }

    private boolean validateForm() {

        boolean valid = true;


        if (TextUtils.isEmpty(txtFechaInicioCalReser.getText().toString())) {
            this.txtFechaInicioCalReser.setError("Requerido");
            valid = false;
        } else {
            this.txtFechaInicioCalReser.setError(null);
        }

        if (TextUtils.isEmpty(txtFechaFinCalReser.getText().toString())) {
            this.txtFechaFinCalReser.setError("Requerido");
            valid = false;
        } else {
            this.txtFechaFinCalReser.setError(null);
        }

        if (TextUtils.isEmpty(txtDiasCalReser.getText().toString())) {
            this.txtDiasCalReser.setError("Requerido");
            valid = false;
        } else {
            this.txtDiasCalReser.setError(null);
        }

        if (TextUtils.isEmpty(txtValorCalReser.getText().toString())) {
            this.txtValorCalReser.setError("Requerido");
            valid = false;
        } else {
            this.txtValorCalReser.setError(null);
        }

        if(!valid)
            Toast.makeText(CalendarioReservarActivity.this, "Seleccione las fechas de las Reservas", Toast.LENGTH_SHORT).show();


        return valid;
    }



    private void actualizarCalendario(){
        for(Date f: fechas){
            calendario.add(f);
        }

        myRef = database.getReference(PATH_CALENDARIOS);
        //String keyF = myRef.push().getKey();
        myRef = database.getReference(PATH_CALENDARIOS+alojamiento.getId());

        myRef.setValue(calendario);

        finish();
    }



    public void cargarCalendario() {

        calendario.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("calendarios/"+alojamiento.getId()+"/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Date a = singleSnapshot.getValue(Date.class);
                    //if(a.getEmail().compareTo(email) == 0){
                    //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                    //    if(a.getIdUsuario().compareTo(user.getUid()) == 0){
                    calendario.add(a);

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

    @Override
    protected void onStart() {
        super.onStart();
        cargarCalendario();
    }
}

