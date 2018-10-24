package javeriana.edu.co.easytrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class VerAlojamientoActivity extends AppCompatActivity {

    private ImageButton btnCalendarioReservar;
    private Button btnReservarVerAlo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamiento);

        this.btnCalendarioReservar = (ImageButton) findViewById(R.id.btnCalendarioReservar);
        this.btnCalendarioReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

        this.btnReservarVerAlo = (Button) findViewById(R.id.btnReservarVerAlo);
        this.btnReservarVerAlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

    }
}
