package co.edu.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityAlojamientosCercanos extends AppCompatActivity {
    private ListView listView;
    private AdaptadorAlojamientosCercanos adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamientos_cercanos);

        listView = (ListView) findViewById(R.id.lvAlojCercanos);
        adaptador = new AdaptadorAlojamientosCercanos(getArrayItems(), this);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(view.getContext(),ActivityMostrarAlojamiento.class);

                startActivity(intent);

            }

        });
    }

    public ArrayList<ListEntityAlojamientosCercanos> getArrayItems() {
        ArrayList<ListEntityAlojamientosCercanos>arrayItems = new ArrayList<>();
        arrayItems.add(new ListEntityAlojamientosCercanos(R.drawable.imagencasa, "casa", "5"));
        return arrayItems;
    }
}
