package co.edu.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorCompaneros extends BaseAdapter {

    private ArrayList<EntityCompaneros> listItems;
    private Context context;

    public AdaptadorCompaneros(ArrayList<EntityCompaneros> listItems, Context context) {
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
        EntityCompaneros Item = (EntityCompaneros) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_companeros, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoCompanero);
        TextView txtNombre = (TextView) view.findViewById(R.id.txtNombreCompanero);
        TextView txtCalificacion = (TextView) view.findViewById(R.id.txtCalificacionCompanero);
        imgFoto.setImageResource(Item.getImgFoto());
        txtNombre.setText(Item.getNombreCompanero());
        txtCalificacion.setText(Item.getCalificacion());
        return view;
    }
}
