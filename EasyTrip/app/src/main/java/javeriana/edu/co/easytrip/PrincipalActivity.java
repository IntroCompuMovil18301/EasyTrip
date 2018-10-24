package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity {


    private Button btnRegistrarseP;
    private Button btnLoginL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        this.btnLoginL = (Button) findViewById(R.id.btnLoginL);
        this.btnLoginL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(view.getContext(),VerReservaActivity.class);
                Intent intent = new Intent(view.getContext(),HomeAnfitrionActivity.class);
                //Intent intent = new Intent(view.getContext(),ActivityReservasPorAcepatar.class);
                startActivity(intent);
            }
        });

    //--------------------------------------------------------------------------------------------------


        this.btnRegistrarseP = (Button) findViewById(R.id.btnRegistrarseP);
        this.btnRegistrarseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegistroPerfilActivity.class);
                //Intent intent = new Intent(view.getContext(),ActivityCompaneros.class);
                //Intent intent = new Intent(view.getContext(),VerReservaActivity.class);
                startActivity(intent);
            }
        });


    }
}
