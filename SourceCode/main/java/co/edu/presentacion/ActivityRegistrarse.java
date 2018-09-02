package co.edu.presentacion;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;



public class ActivityRegistrarse extends AppCompatActivity {

    LinearLayout llRegHuesped;
    Switch swHuesped;
    EditText ediNacimientoReg;
    Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        llRegHuesped = (LinearLayout) findViewById(R.id.llRegHuesped);
        llRegHuesped.setVisibility(View.GONE);
        swHuesped = (Switch) findViewById(R.id.swHuesped);
        swHuesped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    llRegHuesped.setVisibility(View.VISIBLE);
                } else {
                    llRegHuesped.setVisibility(View.GONE);
                }

            }
        });

        ediNacimientoReg = (EditText) findViewById(R.id.verEdadCom);
        ediNacimientoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnRegistrarse =(Button) findViewById(R.id.btnRegistrarReg);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ActivityAgregarAlojamiento.class);

                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                ediNacimientoReg.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }
}