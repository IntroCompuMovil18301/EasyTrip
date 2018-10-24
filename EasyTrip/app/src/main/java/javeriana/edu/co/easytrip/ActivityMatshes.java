package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class ActivityMatshes extends AppCompatActivity{
    private ListView listView;
    private AdaptadorMatshes adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matshes);
        listView = (ListView) findViewById(R.id.lvMatshes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerMashActivity.class);
                startActivity(intent);
            }
        });
        adaptador = new AdaptadorMatshes(getArrayItems(), this);
        listView.setAdapter(adaptador);
    }
    public ArrayList<EntityMatshes> getArrayItems() {
        ArrayList<EntityMatshes>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityMatshes(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityMatshes(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        return arrayItems;
    }
}
