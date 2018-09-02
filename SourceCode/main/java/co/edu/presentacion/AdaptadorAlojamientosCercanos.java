package co.edu.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorAlojamientosCercanos extends BaseAdapter {

    private ArrayList <ListEntityAlojamientosCercanos> listItems;
    private Context context;

    public AdaptadorAlojamientosCercanos(ArrayList<ListEntityAlojamientosCercanos> listItems, Context context) {
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
        ListEntityAlojamientosCercanos Item = (ListEntityAlojamientosCercanos) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_alojamiento_cercano, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoAlojamiento);
        TextView txtNombre = (TextView) view.findViewById(R.id.txtNombreAlojamiento);
        TextView txtCalificacion = (TextView) view.findViewById(R.id.txtCalificacionAlojamiento);
        imgFoto.setImageResource(Item.getImgFoto());
        txtNombre.setText(Item.getNombreAlojamiento());
        txtCalificacion.setText(Item.getCalificacion());
        return view;
    }
}
