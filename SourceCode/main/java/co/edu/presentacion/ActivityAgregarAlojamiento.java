package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class ActivityAgregarAlojamiento extends AppCompatActivity {

    Button btnCFotosRA;
    Button btnUbicacionRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alojamiento);

        btnCFotosRA =(Button) findViewById(R.id.btnCFotosA);
        btnCFotosRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ActivityEditarAlojamiento.class);

                startActivity(intent);
            }
        });



        btnUbicacionRA = (Button) findViewById(R.id.btnUbicacionA);
        btnUbicacionRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ActivityEditarAlojamiento.class);

                startActivity(intent);
            }
        });

    }
}
