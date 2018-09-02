package co.edu.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;




public class ActivityHacerMash extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_mash);

        lv = (ListView) findViewById(R.id.listHacerMash);

        String[] values = new String[]{"Ironman","Capitan America","Hulk","Thor","Black Widow","Ant man","Spider man"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //ArrayAdapter<LinearLayout> layouts = new ArrayAdapter<LinearLayout>(this,android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)lv.getItemAtPosition(position);
                System.out.println("itemval");

                Intent intent = new Intent(view.getContext(),ActivityinfoUsuariosMash.class);

                startActivity(intent);

            }

        });

    }
}
