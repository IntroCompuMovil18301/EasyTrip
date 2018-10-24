package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class PerfilHuespedActivity extends AppCompatActivity {

    private static final String TAG = "Tab1Fragment";

    private FloatingActionButton fabEditPH;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_huesped);


        this.fabEditPH = (FloatingActionButton) findViewById(R.id.fabEditPH);
        this.fabEditPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "TESTING FAB CLICK",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EditarPerfilHuespedActivity.class);
                startActivity(intent);
            }
        });



        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        imageView = (ImageView) findViewById(R.id.fotoPerfilP);

        imageView.setImageDrawable(roundedDrawable);

    }

}
