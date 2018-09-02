package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPerfilAnfitrion extends AppCompatActivity {


    Button btnAddAnfi;
    Button btnLugaresAnfi;
    Button btnReservasAnfi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_anfitrion);

        this.btnAddAnfi = (Button) findViewById(R.id.btnMashHuesped);
        this.btnAddAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityAgregarAlojamiento.class);

                startActivity(intent);
            }
        });

        this.btnLugaresAnfi = (Button) findViewById(R.id.btnBuscarAloja);
        this.btnLugaresAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityVerAlojamientosAnfrition.class);

                startActivity(intent);
            }
        });

        this.btnReservasAnfi = (Button) findViewById(R.id.btnReservasHuesped);
        this.btnReservasAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityReservasPorAcepatar.class);

                startActivity(intent);
            }
        });

    }
}
