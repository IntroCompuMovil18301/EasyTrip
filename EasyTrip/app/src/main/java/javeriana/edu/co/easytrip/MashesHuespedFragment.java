package javeriana.edu.co.easytrip;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MashesHuespedFragment extends Fragment{
    private static final String TAG = "Tab1Fragment";

    private ListView listView;
    private AdaptadorMatshes adaptador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mashes_huesped,container,false);

        listView = (ListView) view.findViewById(R.id.listMashesPH);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerMashActivity.class);
                startActivity(intent);
            }
        });
        adaptador = new AdaptadorMatshes(getArrayItems(), view.getContext());
        listView.setAdapter(adaptador);

        return view;
    }


    public ArrayList<EntityMatshes> getArrayItems() {
        ArrayList<EntityMatshes>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityMatshes(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityMatshes(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityMatshes(R.drawable.imgcompaneros, "casa", "20/07/18", "20/07/18"));
        arrayItems.add(new EntityMatshes(R.drawable.personauno, "casa", "20/07/18", "20/07/18"));

        return arrayItems;
    }


}
