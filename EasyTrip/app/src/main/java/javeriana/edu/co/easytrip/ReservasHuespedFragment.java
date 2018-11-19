package javeriana.edu.co.easytrip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Reserva;

public class ReservasHuespedFragment extends Fragment{
    private static final String TAG = "Tab1Fragment";

    private TextView txtNombreReservaPH;
    private TextView txtEstadoReservaPH;
    private TextView txtFechaInicioReservaPH;
    private TextView txtFechaFinReservaPH;
    private TextView txtDiasReservaPH;
    private TextView txtCostoReservaPH;
    private ImageButton btnCheckout;

    private ImageButton btnBackReservaPH;
    private ImageButton imageAloReservaPH;
    private ImageButton  btnNextReservaPH;


    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference myRef;
    private DatabaseReference myRefA;
    private DatabaseReference myRefL;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private int selected;

    private LinearLayout linearFechaInicioPH;
    private LinearLayout linearFechaFinPH;
    private LinearLayout linearDiasPH;
    private LinearLayout linearCostoReservaPH;
    private LinearLayout linearAcompanantesReservaPH;


    private List<Reserva> reservas;
    private Alojamiento alojamiento;
    private String nombreUsuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas_huesped,container,false);

        this.mAuth = FirebaseAuth.getInstance();
        this.database= FirebaseDatabase.getInstance();
        this.reservas = new ArrayList<Reserva>();

        this.user = mAuth.getCurrentUser();
        this.storage = FirebaseStorage.getInstance();

        this.btnCheckout = (ImageButton) view.findViewById(R.id.btnCheckout);
        this.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
            }
        });

        this.txtNombreReservaPH =(TextView) view.findViewById(R.id.txtNombreReservaPH );
        this.txtEstadoReservaPH =(TextView) view.findViewById(R.id.txtEstadoReservaPH );
        this.txtFechaInicioReservaPH =(TextView) view.findViewById(R.id.txtFechaInicioReservaPH );
        this.txtFechaFinReservaPH =(TextView) view.findViewById(R.id.txtFechaFinReservaPH );
        this.txtDiasReservaPH =(TextView) view.findViewById(R.id.txtDiasReservaPH );
        this.txtCostoReservaPH =(TextView) view.findViewById(R.id.txtCostoReservaPH );

        this.linearFechaInicioPH = (LinearLayout) view.findViewById(R.id.linearFechaInicioPH);
        this.linearFechaFinPH = (LinearLayout) view.findViewById(R.id.linearFechaFinPH);
        this.linearDiasPH = (LinearLayout) view.findViewById(R.id.linearDiasPH);
        this.linearCostoReservaPH = (LinearLayout) view.findViewById(R.id.linearCostoReservaPH);
        this.linearAcompanantesReservaPH = (LinearLayout) view.findViewById(R.id.linearAcompanantesReservaPH);


        this.imageAloReservaPH = (ImageButton) view.findViewById(R.id.imageAloReservaPH);

        this.btnNextReservaPH = (ImageButton) view.findViewById(R.id.btnNextReservaPH);
        this.btnNextReservaPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextReserve();
            }
        });

        this.btnBackReservaPH = (ImageButton) view.findViewById(R.id.btnBackReservaPH);
        this.btnBackReservaPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backReserve();
            }
        });

        actualizarReservas();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cargarReservas();
    }

    public void cargarReservas() {

        reservas.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("reservas/");

        myRefL = database.getReference("alojamientos/"+user.getUid()+"/localizacion/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Reserva r = singleSnapshot.getValue(Reserva.class);

                    if(r.getIdUsuario().compareTo(user.getUid()) == 0){
                        r.setId(singleSnapshot.getKey());
                        reservas.add(r);
                    }
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


    public void actualizarReservas(){
        this.selected =0;
        if(reservas.size() > 0){
            this.linearFechaInicioPH.setVisibility(View.VISIBLE);
            this.linearFechaFinPH.setVisibility(View.VISIBLE);
            this.linearDiasPH.setVisibility(View.VISIBLE);
            this.linearCostoReservaPH.setVisibility(View.VISIBLE);
            this.linearAcompanantesReservaPH.setVisibility(View.VISIBLE);


            this.txtNombreReservaPH.setText(reservas.get(0).getNombreAlo());
            this.txtEstadoReservaPH.setText(reservas.get(0).getEstado());
            this.txtEstadoReservaPH.setTextColor(getColor(reservas.get(0).getEstado()));
            //this.txtDireccionReservaPH.setText(reservas.get(0).getAlojamiento().getLocalizacion().getDireccion());
            this.txtFechaInicioReservaPH.setText(getFechaString(reservas.get(0).getFechaInicio()));
            this.txtFechaFinReservaPH.setText(getFechaString(reservas.get(0).getFechaFin()));
            this.txtDiasReservaPH.setText(reservas.get(0).getDias()+"");
            this.txtCostoReservaPH.setText(reservas.get(0).getCosto()+"00");

            //-----------------------------------------
            Drawable originalDrawable = null;

            descargarFoto("Alojamientos/"+reservas.get(0).getIdAlojamiento(),reservas.get(0).getFoto());
            //-----------------------------------------

        }else{
            this.txtNombreReservaPH.setText("");
            this.txtEstadoReservaPH.setText("No hay Reservas");
            this.txtFechaInicioReservaPH.setText("");
            this.txtFechaFinReservaPH.setText("");
            this.txtDiasReservaPH.setText("");
            this.txtCostoReservaPH.setText("");

            this.linearFechaInicioPH.setVisibility(View.INVISIBLE);
            this.linearFechaFinPH.setVisibility(View.INVISIBLE);
            this.linearDiasPH.setVisibility(View.INVISIBLE);
            this.linearCostoReservaPH.setVisibility(View.INVISIBLE);
            this.linearAcompanantesReservaPH.setVisibility(View.INVISIBLE);

        }



    }

    private String getFechaString(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String fecha = cal.get(Calendar.DAY_OF_MONTH)
                + "/" + (cal.get(Calendar.MONTH) + 1)
                + "/" + cal.get(Calendar.YEAR);

        return fecha;
    }

    private int getColor(String estado){

        if(estado.compareTo("Solicitado")== 0)
            return R.color.solicitado;

        if(estado.compareTo("Aceptada")== 0)
            return R.color.aceptada;

        if(estado.compareTo("Rechazada")== 0)
            return R.color.rechazada;

        if(estado.compareTo("Caducada")== 0)
            return R.color.caducada;

        return R.color.h1;
    }


    private void descargarFoto(String origen, String nombre){

        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(origen+"/"+nombre+".jpg");
        //Toast.makeText(getContext(), origen+"/"+nombre, Toast.LENGTH_SHORT).show();

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                //RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                //roundedDrawable.setCircular(true);
                //imageAloReservaPH.setImageDrawable(roundedDrawable);
                imageAloReservaPH.setImageBitmap(bitmap);

                //Toast.makeText(HomeAnfitrionActivity.this, "cargada ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void backReserve(){
        if(this.selected>0 ){
            selected--;
            this.txtNombreReservaPH.setText(reservas.get(selected).getNombreAlo());
            this.txtEstadoReservaPH.setText(reservas.get(selected).getEstado());
            this.txtEstadoReservaPH.setTextColor(getColor(reservas.get(selected).getEstado()));
            //this.txtDireccionReservaPH.setText(reservas.get(0).getAlojamiento().getLocalizacion().getDireccion());
            this.txtFechaInicioReservaPH.setText(getFechaString(reservas.get(selected).getFechaInicio()));
            this.txtFechaFinReservaPH.setText(getFechaString(reservas.get(selected).getFechaFin()));
            this.txtDiasReservaPH.setText(reservas.get(selected).getDias()+"");
            this.txtCostoReservaPH.setText(reservas.get(selected).getCosto()+"00");

            //-----------------------------------------
            Drawable originalDrawable = null;

            descargarFoto("Alojamientos/"+reservas.get(selected).getIdAlojamiento(),reservas.get(0).getFoto());
            //-----------------------------------------


        }

    }


    private void nextReserve(){

        if((this.selected+1) <= (reservas.size()-1) ){
            selected++;
            this.txtNombreReservaPH.setText(reservas.get(selected).getNombreAlo());
            this.txtEstadoReservaPH.setText(reservas.get(selected).getEstado());
            this.txtEstadoReservaPH.setTextColor(getColor(reservas.get(selected).getEstado()));
            //this.txtDireccionReservaPH.setText(reservas.get(0).getAlojamiento().getLocalizacion().getDireccion());
            this.txtFechaInicioReservaPH.setText(getFechaString(reservas.get(selected).getFechaInicio()));
            this.txtFechaFinReservaPH.setText(getFechaString(reservas.get(selected).getFechaFin()));
            this.txtDiasReservaPH.setText(reservas.get(selected).getDias()+"");
            this.txtCostoReservaPH.setText(reservas.get(selected).getCosto()+"00");

            //-----------------------------------------
            Drawable originalDrawable = null;

            descargarFoto("Alojamientos/"+reservas.get(selected).getIdAlojamiento(),reservas.get(0).getFoto());
            //-----------------------------------------


        }
    }

    private void checkout() {
        final CharSequence[] opciones={"Pagar","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("CheckOut");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Pagar")){
                    Intent intent = new Intent(getContext(), CheckOutReservaActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable("reserva",reservas.get(selected));
                        intent.putExtra("bundle",b);
                    startActivity(intent);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();

    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
