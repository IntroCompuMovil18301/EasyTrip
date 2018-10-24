package javeriana.edu.co.easytrip;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class VerMashActivity extends AppCompatActivity {

    private ImageView fotoPerfilMash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mash);


        Drawable originalDrawable = getResources().getDrawable(R.drawable.fotoperfil);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();


        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        this.fotoPerfilMash = (ImageView) findViewById(R.id.fotoPerfilMash);

        this.fotoPerfilMash.setImageDrawable(roundedDrawable);
    }
}
