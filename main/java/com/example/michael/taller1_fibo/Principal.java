package com.example.michael.taller1_fibo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import example.michael.taller1fibonacci.paises;


public class Principal extends AppCompatActivity {


    List<Integer> fibo;
    Button btnCalPos;
    Button btnCalS;
    Button btnFibo;
    Button btnPaises;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        this.fibo = new ArrayList<Integer>();
        this.fibo.add(0);
        this.actualizarTextView();
        this.fibo.add(1);
        this.actualizarTextView();

        this.btnCalPos = (Button) findViewById(R.id.btnCalPos);
        this.btnCalPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), posicionesFibo.class);

                bundle = new Bundle();

                intent.putExtra("bundle", bundle);

                startActivity(intent);

            }
        });

        this.btnCalS = (Button) findViewById(R.id.btnCalcularS);
        this.btnCalS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularFibo();
                actualizarTextView();

            }
        });

        this.btnFibo = (Button) findViewById(R.id.btnFibo);
        this.btnFibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebView myWebView = (WebView) findViewById(R.id.webview);
                myWebView.loadUrl("https://es.wikipedia.org/wiki/Leonardo_de_Pisa");
            }
        });

        this.btnPaises =(Button) findViewById(R.id.btnPaises);
        this.btnPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), paises.class);
                bundle = new Bundle();
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

    }


    private void actualizarTextView(){
        String text = "";
        LinearLayout ll = (LinearLayout) findViewById(R.id.llSigF);

        TextView textView = new TextView(this);
        ll.addView(textView);
        textView.setText(fibo.get(fibo.size()-1)+"");
        textView.setGravity(Gravity.CENTER);

    }


    private void calcularFibo(){
        int a = this.fibo.get(fibo.size()-1);
        int b = this.fibo.get(fibo.size()-2);
        this.fibo.add(a+b);
    }
}
