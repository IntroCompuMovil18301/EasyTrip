package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPerfilHuesped extends AppCompatActivity {

    Button btnAlojaCercano;
    Button btnReservasHuesped;
    Button btnBuscarAloja;
    Button btnMashHuesped;
    Button btnBusquedaNombre;
    Button btnVerHuspeAnfi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_huesped);

        this.btnAlojaCercano = (Button) findViewById(R.id.btnAlojaCercano);
        this.btnAlojaCercano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityAlojamientosCercanos.class);

                startActivity(intent);
            }
        });

        this.btnReservasHuesped = (Button) findViewById(R.id.btnReservasHuesped);
        this.btnReservasHuesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityReservasDeUsuario.class);

                startActivity(intent);
            }
        });

        this.btnBuscarAloja = (Button) findViewById(R.id.btnBuscarAloja);
        this.btnBuscarAloja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityBuscarAlojamiento.class);

                startActivity(intent);
            }
        });

        this.btnMashHuesped = (Button) findViewById(R.id.btnMashHuesped);
        this.btnMashHuesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityHacerMash.class);

                startActivity(intent);
            }
        });


        this.btnBusquedaNombre = (Button) findViewById(R.id.btnBusquedaNombre);
        this.btnBusquedaNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityBuscarAlojamiento.class);

                startActivity(intent);
            }
        });

        this.btnVerHuspeAnfi = (Button) findViewById(R.id.btnVerHuspeAnfi);
        this.btnVerHuspeAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityRegistrarse.class);

                startActivity(intent);
            }
        });

    }
}
