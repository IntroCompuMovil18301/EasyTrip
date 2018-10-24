package javeriana.edu.co.easytrip;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AdaptadorReservaPorAceptar extends BaseAdapter {

    private ArrayList<EntityReservaPorAceptar> listItems;
    private Context context;

    public AdaptadorReservaPorAceptar(ArrayList<EntityReservaPorAceptar> listItems, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        EntityReservaPorAceptar Item = (EntityReservaPorAceptar) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_reservas_por_aceptar, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoReserva);

        Bitmap bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), Item.getImgFotoReserva());
        RoundedBitmapDrawable rbmd = RoundedBitmapDrawableFactory.create(view.getContext().getResources(), bitmap);
        rbmd.setCircular(true);
        imgFoto.setImageDrawable(rbmd);

        TextView txtNombreReserva = (TextView) view.findViewById(R.id.txtNombreRePoAC);
        TextView fechaInicio = (TextView) view.findViewById(R.id.txtFechaInicioRePoAc);
        TextView fechaFin = (TextView) view.findViewById(R.id.txtFechaFinRePoAC);
        txtNombreReserva.setText(Item.getNombreReserva());
        fechaFin.setText(Item.getFechaFin());
        fechaInicio.setText(Item.getFechaInicio());

        Button button = (Button) view.findViewById(R.id.btnVerReservaPorAceptarAnfi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VerReservaActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
