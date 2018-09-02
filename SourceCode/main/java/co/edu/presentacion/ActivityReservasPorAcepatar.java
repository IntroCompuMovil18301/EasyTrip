package co.edu.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityReservasPorAcepatar extends AppCompatActivity{
    private ListView listView;
    private AdaptadorReservaPorAceptar adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_por_aceptar);
        listView = (ListView) findViewById(R.id.lvReservasXaceptar);
       // adaptador = new AdaptadorReservaPorAceptar(getArrayItems(), this);
        //listView.setAdapter(adaptador);


        String[] values = new String[]{"Ironman","Capitan America","Hulk","Thor","Black Widow","Ant man","Spider man"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(view.getContext(),ActivityVerReserva.class);

                startActivity(intent);

            }

        });
    }
    public ArrayList<EntityReservaPorAceptar> getArrayItems() {
        ArrayList<EntityReservaPorAceptar>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "pepe", "5"));
        return arrayItems;
    }
}
