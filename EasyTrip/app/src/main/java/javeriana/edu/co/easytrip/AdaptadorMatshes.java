package javeriana.edu.co.easytrip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorMatshes extends BaseAdapter {

    private ArrayList<EntityMatshes> listItems;
    private Context context;

    public AdaptadorMatshes(ArrayList<EntityMatshes> listItems, Context context) {
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
        EntityMatshes Item = (EntityMatshes) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_matshes, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoMatsh);

        Bitmap bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), Item.getImgFotoMatsh());
        RoundedBitmapDrawable rbmd = RoundedBitmapDrawableFactory.create(view.getContext().getResources(), bitmap);
        rbmd.setCircular(true);
        imgFoto.setImageDrawable(rbmd);

        TextView txtNombreMatch = (TextView) view.findViewById(R.id.txtNombreMatsh);
        TextView fechaInicio = (TextView) view.findViewById(R.id.txtFechaInicioMatsh);
        TextView fechaFin = (TextView) view.findViewById(R.id.txtFechaFinMatsh);
        txtNombreMatch.setText(Item.getNombreMatsh());
        fechaFin.setText(Item.getFechaFin());
        fechaInicio.setText(Item.getFechaInicio());

        return view;
    }
}
