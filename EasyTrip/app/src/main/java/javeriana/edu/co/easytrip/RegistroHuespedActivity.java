package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroHuespedActivity extends AppCompatActivity {

    private Button btnRegistrarH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_huesped);

        this.btnRegistrarH = (Button) findViewById(R.id.btnRegistrarH);
        this.btnRegistrarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),HomeHuespedActivity.class);

                startActivity(intent);
            }
        });
    }
}
