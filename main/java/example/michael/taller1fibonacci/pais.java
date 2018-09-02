package example.michael.taller1fibonacci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michael.taller1_fibo.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class pais extends AppCompatActivity {

    private Bitmap loadedImage;
    Bundle bundle;
    TextView textCapital;
    TextView textPais;
    TextView textPaisINT;
    TextView textSigla;
    Button btnSWPais;
    ImageView img;

    private String imageHttpAddress = "http://jonsegador.com/wp-content/apezz.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);


        this.textCapital = (TextView) findViewById(R.id.textCapital);
        this.textPais = (TextView) findViewById(R.id.textPais);
        this.textPaisINT = (TextView) findViewById(R.id.textPaisINT);
        this.textSigla = (TextView) findViewById(R.id.textSigla);
        this.btnSWPais = (Button) findViewById(R.id.btnSWPais);
        this.img = (ImageView) findViewById(R.id.imageViewPais);

        bundle = getIntent().getBundleExtra("bundle");

        this.textCapital.setText(bundle.getString("capital"));
        this.textPais.setText(bundle.getString("nombre_pais"));
        this.textPaisINT.setText(bundle.getString("nombre_pais_int"));
        this.textSigla.setText(bundle.getString("sigla"));




        this.btnSWPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView myWebView = (WebView) findViewById(R.id.webViewPais);
                myWebView.loadUrl(bundle.getString("sitioWeb"));
            }
        });
    }



}
