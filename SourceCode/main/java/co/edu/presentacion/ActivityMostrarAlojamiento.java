package co.edu.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ActivityMostrarAlojamiento extends AppCompatActivity {
    Button oferta;
    Button reservar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_alojamiento);
        this.oferta = (Button) findViewById(R.id.btnOferta);
        this.oferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityOfrecerMash.class);
                startActivity(intent);
            }
        });
        this.reservar = (Button) findViewById(R.id.btnReservar);
        this.reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityReservar.class);
                startActivity(intent);
            }
        });
    }
}
