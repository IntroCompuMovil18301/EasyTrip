package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javeriana.edu.co.modelo.FirebaseReference;
import javeriana.edu.co.modelo.Huesped;

public class RegistroHuespedActivity extends AppCompatActivity {

    private Button btnRegistrarH;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    String nombre, apellidos, nomUsuario;
    TextView sobreMi;
    RadioGroup radioGenero;
    Spinner rol, nacionalidad, tipo_Viaje;
    RadioButton genero, fumador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_huesped);

        database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.btnRegistrarH = (Button) findViewById(R.id.btnRegistrarH);

        this.btnRegistrarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getIntent().getBundleExtra("bundle");
                nombre = bundle.getString("nombre");
                apellidos = bundle.getString("apellidos");
                nomUsuario = bundle.getString("nomUsuario");

                Huesped huesped = crearNuevoHuesped();

                myRef = database.getReference();

                if(huesped != null)
                    myRef.child(FirebaseReference.HUESPESDES).child(nomUsuario).setValue(huesped);

                Intent intent = new Intent (view.getContext(),HomeHuespedActivity.class);
                startActivity(intent);
            }
        });
    }

    private Huesped crearNuevoHuesped(){

        radioGenero = (RadioGroup) findViewById(R.id.groupSexo);
        genero = (RadioButton) findViewById(radioGenero.getCheckedRadioButtonId());
        tipo_Viaje = (Spinner) findViewById(R.id.spnTipoViajeR);
        nacionalidad = (Spinner) findViewById(R.id.spnNacionalidadR);
        fumador = (RadioButton) findViewById(R.id.rbtnSiFumaR);
        boolean fuma = (fumador.isSelected())? true : false;
        sobreMi = (EditText) findViewById(R.id.txtSobretiR);
        FirebaseUser user = mAuth.getCurrentUser();
        Huesped huesped = new Huesped(nomUsuario, nombre,"URL_Foto",genero.getText().toString(),nacionalidad.getSelectedItem().toString(),tipo_Viaje.getSelectedItem().toString(),fuma,sobreMi.getText().toString(),user.getEmail());

        return huesped;
    }

}
