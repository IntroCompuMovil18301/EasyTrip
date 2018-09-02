package example.michael.taller1fibonacci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.michael.taller1_fibo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class paises extends AppCompatActivity {

    ListView lv;
    String[] arreglo;
    JSONArray paisesJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        this.lv = (ListView) findViewById(R.id.listView);

        try {
            initArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arreglo);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                        JSONObject jsonObject = paisesJsonArray.getJSONObject(position);

                        Intent intent = new Intent(getBaseContext(), pais.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("capital",jsonObject.getString("capital"));
                        bundle.putString("nombre_pais",jsonObject.getString("nombre_pais"));
                        bundle.putString("nombre_pais_int",jsonObject.getString("nombre_pais_int"));
                        bundle.putString("sigla",jsonObject.getString("sigla"));
                        bundle.putString("sitioWeb",jsonObject.getString("sitioWeb"));

                        intent.putExtra("bundle",bundle);

                        startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    private void initArray() throws JSONException {
        JSONObject json = new JSONObject(loadJSONFromAsset());
        this.paisesJsonArray = json.getJSONArray("paises");

        ArrayList<String> list= new ArrayList<String>();

        for(int i=0;i<paisesJsonArray.length();i++)
        {
            JSONObject jsonObject = paisesJsonArray.getJSONObject(i);
            list.add(jsonObject.getString("capital") + " (" +jsonObject.getString("nombre_pais")+")");
        }
        this.arreglo = new String[list.size()];

        for (int i = 0; i < arreglo.length; i++)
            arreglo[i] = list.get(i);
    }


    public String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = this.getAssets().open("paises.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
