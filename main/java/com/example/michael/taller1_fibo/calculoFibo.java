package com.example.michael.taller1_fibo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class calculoFibo extends AppCompatActivity {

    List<Integer> fibo;
    LinearLayout ll;
    Button btnfiboWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_fibo);

        this.ll = (LinearLayout) findViewById(R.id.linearLayoutFibo);

        this.fibo = new ArrayList<Integer>();
        this.fibo.add(0);
        this.fibo.add(1);

        btnfiboWeb = (Button) findViewById(R.id.btnfiboWeb);
        btnfiboWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView myWebView = (WebView) findViewById(R.id.FiboWeb);
                myWebView.loadUrl("https://es.wikipedia.org/wiki/Leonardo_de_Pisa");
            }
        });

        Bundle bundle = getIntent().getBundleExtra("bundle");
        calcularFibo(bundle.getInt("n"));

        actualizarScroll(bundle.getInt("n"));
    }

    private void actualizarScroll(int n){

        ll.removeAllViewsInLayout();

        for (int i=0; i < n ; i++) {
            TextView textView = new TextView(this);
            ll.addView(textView);
            textView.setText(fibo.get(i)+"");
            textView.setGravity(Gravity.CENTER);
        }


    }

    private void calcularFibo(int n){

        for(int i=0; i< n;i++){
            int a = this.fibo.get(fibo.size()-1);
            int b = this.fibo.get(fibo.size()-2);
            this.fibo.add(a+b);

        }
    }
}
