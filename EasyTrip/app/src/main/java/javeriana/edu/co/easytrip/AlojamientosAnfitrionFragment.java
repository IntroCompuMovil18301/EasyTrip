package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AlojamientosAnfitrionFragment extends Fragment{
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST2;
    private ListView listAlojamientosAnfi;
    private final int n=1;
    private FloatingActionButton fabAddAlojamientoAnfi;

    public AlojamientosAnfitrionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alojamientos_anfitrion,container,false);

        this.listAlojamientosAnfi = (ListView) view.findViewById(R.id.listAlojamientosAnfi);
        this.fabAddAlojamientoAnfi = (FloatingActionButton) view.findViewById(R.id.fabAddAlojamientoAnfi);
        this.fabAddAlojamientoAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(),AddAlojamientoActivity.class);
                startActivity(intent);
            }
        });
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ListView listView = this.listAlojamientosAnfi;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1,values);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(android.R.layout.simple_list_item_1, values);

        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);

                startActivity(intent);
        };
        });




        return view;


    }

}
