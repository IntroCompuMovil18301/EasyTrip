package co.edu.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView txtNombreReserva = (TextView) view.findViewById(R.id.txtNombreReservaPorAceptar);
        TextView txtNombreCliente = (TextView) view.findViewById(R.id.txtNombreCliente);
        TextView txtCalificacion = (TextView) view.findViewById(R.id.txtCalificacionCliente);
        imgFoto.setImageResource(Item.getImgFotoReserva());
        txtNombreReserva.setText(Item.getNombreReserva());
        txtNombreCliente.setText(Item.getNombreCliente());
        txtCalificacion.setText(Item.getCalificacionCliente());
        return view;
    }
}
