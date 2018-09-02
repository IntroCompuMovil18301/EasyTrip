package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityRevisarReservaUsuario extends AppCompatActivity {

    Button btnVerHospeHuesped;
    Button btnVerCompaHuesped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_reserva_usuario);


        this.btnVerHospeHuesped = (Button) findViewById(R.id.btnVerHospeHuesped);
        this.btnVerHospeHuesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityVerHospedaje.class);

                startActivity(intent);
            }
        });

        this.btnVerCompaHuesped = (Button) findViewById(R.id.btnVerCompaHuesped);
        this.btnVerCompaHuesped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),ActivityCompaneros.class);

                startActivity(intent);
            }
        });
    }
}
