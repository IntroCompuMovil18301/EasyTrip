package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityLogin extends AppCompatActivity {
    Button login1;
    Button login2;
    Button registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.login1 = (Button) findViewById(R.id.btnLogin);
        this.login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityPerfilHuesped.class);
                startActivity(intent);
            }
        });

        this.login2 = (Button) findViewById(R.id.btnLogin2);
        this.login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityPerfilAnfitrion.class);
                startActivity(intent);
            }
        });

        this.registrar = (Button) findViewById(R.id.btnRegLogin);
        this.registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityRegistrarse.class);
                startActivity(intent);
            }
        });
    }
}
