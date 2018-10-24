package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class VerReservaActivity extends AppCompatActivity {

    private Button btnDescartarAnfi;
    private Button btnAceptarAnfi;
    private ImageView imageView;
    private AdaptadorCompaneros adaptador;
    private ListView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reserva);
        super.setTitle("Reserva Casa Quinta");
        //------------------------------------------------------------------------------------------

        this.gridview = (ListView) findViewById(R.id.listAcompaReserva);
        this.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), VerHuespedActivity.class);
                startActivity(intent);
            }
        });

        this.adaptador = new AdaptadorCompaneros(getArrayItems(), this);
        gridview.setAdapter(this.adaptador);


        //------------------------------------------------------------------------------------------
        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        imageView = (ImageView) findViewById(R.id.fotoPerfilHuspedAnfi);

        imageView.setImageDrawable(roundedDrawable);
        //------------------------------------------------------------------------------------------

        this.btnDescartarAnfi = (Button) findViewById(R.id.btnDescartarAnfi);
        this.btnDescartarAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

        this.btnAceptarAnfi = (Button) findViewById(R.id.btnAceptarAnfi);
        this.btnAceptarAnfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CalendarioReservarActivity.class);

                startActivity(intent);
            }
        });

    }


    //----------------------------------------------------------------------------------------------

    public ArrayList<EntityCompaneros> getArrayItems() {
        ArrayList<EntityCompaneros>arrayItems = new ArrayList<>();
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));
        arrayItems.add(new EntityCompaneros(R.drawable.imgcompaneros, "pepe", "5"));

        return arrayItems;
    }

}
