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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Mash;
import javeriana.edu.co.modelo.Reserva;

public class MashesHuespedFragment extends Fragment{
    private static final String TAG = "Tab1Fragment";

    private ListView listView;
    private AdaptadorMatshes adaptador;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefM;
    private List<Mash> mashes;
    private String nombreUsuario;
    private String genero;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mashes_huesped,container,false);

        this.mashes = new ArrayList<Mash>();
        this.database = FirebaseDatabase.getInstance();

        listView = (ListView) view.findViewById(R.id.listMashesPH);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerMashActivity.class);
                startActivity(intent);
            }
        });
        adaptador = new AdaptadorMatshes((ArrayList<Mash>) mashes, view.getContext());
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

    public void cargarMashes() {

        mashes.clear();
        //FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference("reservas/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    final Reserva r = singleSnapshot.getValue(Reserva.class);
                    r.setId(singleSnapshot.getKey());

                    if(r.getNombrePrincipal().compareTo((nombreUsuario)) == 0){

                        myRefM = database.getReference("mashes/"+r.getId()+"/");

                        myRefM.addListenerForSingleValueEvent(new ValueEventListener() {


                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int i = 0;
                                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                    Mash m = singleSnapshot.getValue(Mash.class);
                                    m.setId(singleSnapshot.getKey());
                                    m.setIdReserva(r.getId());
                                    mashes.add(m);
                                }
                                actualizarMashes();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.this, "Error en consulta", Toast.LENGTH_SHORT).show();

            }


        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        //Toast.makeText( this.getContext() , this.alojamientos.size(), Toast.LENGTH_SHORT).show();

    }

    private void actualizarMashes(){
        adaptador = new AdaptadorMatshes((ArrayList<Mash>) mashes, getContext());
        listView.setAdapter(adaptador);

    }

    @Override
    public void onStart() {
        super.onStart();
        cargarMashes();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
