package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityCompaneros extends AppCompatActivity {
    private ListView listView;
    private AdaptadorCompaneros adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companeros);
        listView = (ListView) findViewById(R.id.lvCompaneros);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerHuespedActivity.class);
                startActivity(intent);
            }
        });

        adaptador = new AdaptadorCompaneros(getArrayItems(), this);

        listView.setAdapter(adaptador);
    }


    public ArrayList<EntityCompaneros> getArrayItems() {
        ArrayList<EntityCompaneros>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));


        return arrayItems;
    }
}