package com.example.michael.taller1_fibo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.*;

public class posicionesFibo extends AppCompatActivity {

    List<Integer> fibo;
    EditText posFibo;
    TextView resFibo;
    Button btnCalcularFibo;
    Button btnPosFibo;
    Button btnPosFacto;
    Bundle bundleAnt;
    int numFibo;
    int numFacto;
    ImageView imgFacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posiciones_fibo);

        this.fibo = new ArrayList<Integer>();
        this.fibo.add(0);
        this.fibo.add(1);

        this.numFacto=0;
        this.numFibo=0;

        bundleAnt = getIntent().getBundleExtra("bundle");

        this.posFibo = (EditText) findViewById(R.id.posFibo);
        this.resFibo = (TextView) findViewById(R.id.resFibo);

        this.btnCalcularFibo = (Button) findViewById(R.id.btnCalcularFibo);
        this.btnCalcularFibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                String time = calendar.getTime().toString();

                calcularFibo(Integer.parseInt(posFibo.getText().toString()));
                resFibo.setText(fibo.get(Integer.parseInt(posFibo.getText().toString())-1)+"");

                bundleAnt.putString("dateFibo", Calendar.getInstance().getTime().toString());
            }
        });


        this.btnPosFibo = (Button) findViewById(R.id.btnPosFibo);
        this.btnPosFibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), calculoFibo.class);
                Bundle bundle = new Bundle();

                bundle.putInt("n",Integer.parseInt(posFibo.getText().toString()));

                intent.putExtra("bundle",bundle);

                numFibo++;

                TextView mofi = (TextView) findViewById(R.id.modFibo);
                mofi.setText("Último: "+Calendar.getInstance().getTime().toString());

                startActivity(intent);
            }
        });

        this.imgFacto = (ImageView) findViewById(R.id.imgFacto);
        this.imgFacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),calculoFacto.class);

                Bundle bundle = new Bundle();

                bundle.putInt("n", Integer.parseInt(posFibo.getText().toString()));

                intent.putExtra("bundle",bundle);

                numFacto++;

                TextView mofa = (TextView) findViewById(R.id.modiFacto);
                mofa.setText("Último: "+Calendar.getInstance().getTime().toString());

                startActivity(intent);
            }
        });




    }


    public void onResume()
    {
        super.onResume();


        TextView fa = (TextView) findViewById(R.id.numFacto);
        fa.setText(numFacto+"");



        TextView fi = (TextView) findViewById(R.id.numFibo);
        fi.setText(numFibo+"");

    }

    private void calcularFibo(int n){

        for(int i=0; i< n;i++){
            int a = this.fibo.get(fibo.size()-1);
            int b = this.fibo.get(fibo.size()-2);
            this.fibo.add(a+b);
        }
    }


}
