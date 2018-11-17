package javeriana.edu.co.easytrip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javeriana.edu.co.modelo.Mash;
import javeriana.edu.co.modelo.Reserva;

public class AdaptadorMatshes extends BaseAdapter {

    private ArrayList<Mash> listItems;
    private Context context;
    private Button button;

    public AdaptadorMatshes(ArrayList<Mash> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Mash item = (Mash) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_matshes, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoHuespedMash);

        button = (Button) view.findViewById(R.id.btnConfirmarMash);

        Drawable originalDrawable = null;

        originalDrawable = view.getResources().getDrawable(R.drawable.fotoperfil);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable rbmd =
        RoundedBitmapDrawableFactory.create(view.getResources(), originalBitmap);
        rbmd.setCircular(true);
        imgFoto.setImageDrawable(rbmd);

        TextView txtNombreHuespedMah = (TextView) view.findViewById(R.id.txtNombreHuespedMah);
        TextView txtGeneroHuespedMah = (TextView) view.findViewById(R.id.txtGeneroHuespedMah);
        TextView txtEdadHuespedMah = (TextView) view.findViewById(R.id.txtEdadHuespedMah);
        txtNombreHuespedMah.setText(item.getNombreUsuario());
        txtGeneroHuespedMah.setText(item.getValoracion()+"");
        txtEdadHuespedMah.setText(item.getEdad()+" Años");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),VerMashActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("mash",listItems.get(i));
                //b.putString("origin","anfitrion");
                intent.putExtra("bundle",b);
                view.getContext().startActivity(intent);
            }
        });
        button.setBackgroundColor(getColor(item.getEstado()));
        button.setText(item.getEstado());

        return view;
    }

    private String getFechaString(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String fecha = cal.get(Calendar.DAY_OF_MONTH)
                + "/" + (cal.get(Calendar.MONTH) + 1)
                + "/" + cal.get(Calendar.YEAR);

        return fecha;
    }

    private int getColor(String estado){

        if(estado.compareTo("Solicitado")== 0)
            return R.color.solicitado;

        if(estado.compareTo("Aceptada")== 0)
            return R.color.aceptada;

        if(estado.compareTo("Rechazada")== 0)
            return R.color.rechazada;

        if(estado.compareTo("Caducada")== 0)
            return R.color.caducada;

        return R.color.h1;
    }

}
