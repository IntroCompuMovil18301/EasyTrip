package com.example.michael.taller1_fibo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class calculoFacto extends AppCompatActivity {

    List<Integer> facto;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_facto);

        this.ll = (LinearLayout) findViewById(R.id.linearLayoutFacto);

        this.facto = new ArrayList<Integer>();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        calcularFacto(bundle.getInt("n"));

        actualizarScroll(bundle.getInt("n"));
    }

    @SuppressLint("ResourceAsColor")
    private void actualizarScroll(int n){

        ll.removeAllViewsInLayout();

        TextView textView = new TextView(this);


        String text=" Multiplicación: ";
        for (int i=0; i < n ; i++) {

            text = text + (i+1);
                if((i+1)<n)
                    text = text +"x";
        }

        textView.setText(text);


        TextView textViewR = new TextView(this);


        textViewR.setText(" Resultado: "+ facto.get(facto.size()-1));

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(R.color.Amarillo);
        textView.setTextSize(16);
        textViewR.setGravity(Gravity.CENTER);
        textViewR.setTextColor(R.color.Amarillo);
        textViewR.setTextSize(16);

        ll.addView(textView);
        ll.addView(textViewR);
    }

    private void calcularFacto(int n){

        this.facto.clear();

        int f=1;

        for(int i=1; i<= n;i++){

            this.facto.add(i*f);
            f=i*f;
        }
        System.out.println(this.facto.toString());
    }
}
