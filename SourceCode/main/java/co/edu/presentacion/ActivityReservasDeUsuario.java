package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ActivityReservasDeUsuario extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_de_usuario);

        lv = (ListView) findViewById(R.id.listReservasUsuario);

        String[] values = new String[]{"Ironman","Capitan America","Hulk","Thor","Black Widow","Ant man","Spider man"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);


        //ArrayAdapter<LinearLayout> layouts = new ArrayAdapter<LinearLayout>(this,android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(view.getContext(),ActivityRevisarReservaUsuario.class);

                startActivity(intent);

            }

        });
    }
}
