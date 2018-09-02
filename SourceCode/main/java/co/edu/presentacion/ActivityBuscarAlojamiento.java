package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityBuscarAlojamiento extends AppCompatActivity {

    ListView listView;
    private AdaptadorAlojamientosCercanos adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alojamiento);

        listView = (ListView) findViewById(R.id.listBuscarAloja);
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
