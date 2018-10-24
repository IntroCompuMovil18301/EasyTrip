package javeriana.edu.co.easytrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class BuscarAlojamientoActivity extends AppCompatActivity {

    private ListView listAlojamientosBusqueda;
    private ImageButton btnBuscarAlojamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alojamiento);

        this.listAlojamientosBusqueda = (ListView) findViewById(R.id.listAlojamientosBusqueda);

        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ListView listView = this.listAlojamientosBusqueda;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,values);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(android.R.layout.simple_list_item_1, values);

        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);

                startActivity(intent);
            };
        });


        this.btnBuscarAlojamiento = (ImageButton) findViewById(R.id.btnBuscarAlojamiento);
        this.btnBuscarAlojamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);

                startActivity(intent);
            }
        });

    }

}
