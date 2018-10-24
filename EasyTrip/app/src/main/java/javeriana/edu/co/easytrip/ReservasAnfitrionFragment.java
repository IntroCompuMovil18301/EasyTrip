package javeriana.edu.co.easytrip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class ReservasAnfitrionFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";


    private ListView listView;
    private AdaptadorReservaPorAceptar adaptador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservas_anfitrion, container, false);



        this.listView = (ListView) view.findViewById(R.id.listReservasAnfi);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerReservaActivity.class);
                startActivity(intent);
            }
        });
        adaptador = new AdaptadorReservaPorAceptar(this.getArrayItems(), view.getContext());
        listView.setAdapter(adaptador);



        return view;
    }


    public ArrayList<EntityReservaPorAceptar> getArrayItems() {
        ArrayList<EntityReservaPorAceptar>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityReservaPorAceptar(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        return arrayItems;
    }


}
