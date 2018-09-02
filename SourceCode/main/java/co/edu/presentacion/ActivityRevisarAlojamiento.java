package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityRevisarAlojamiento extends AppCompatActivity {

    Button btnCalenAnfi;
    Button btnEditarAloAnfi;
    Button btnVerReservaAnfi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_alojamiento);


        this.btnVerReservaAnfi = (Button) findViewById(R.id.btnVerReservaAnfi);
        this.btnVerReservaAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityVerReserva.class);
                startActivity(intent);
            }
        });

        this.btnEditarAloAnfi = (Button) findViewById(R.id.btnEditarAloAnfi);
        this.btnEditarAloAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityEditarAlojamiento.class);
                startActivity(intent);
            }
        });

        this.btnCalenAnfi = (Button) findViewById(R.id.btnCalenAnfi);
        this.btnCalenAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityCalendario.class);
                startActivity(intent);
            }
        });
    }
}
