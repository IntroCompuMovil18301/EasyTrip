package javeriana.edu.co.easytrip;

import android.content.Context;
import android.graphics.Typeface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javeriana.edu.co.modelo.Mash;

import static javeriana.edu.co.easytrip.PrincipalActivity.TAG;

public class AdaptadorCalificar extends BaseAdapter implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener {

    private ArrayList<Mash> listItems;
    private Context context;
    private Button button;

    public AdaptadorCalificar(ArrayList<Mash> listItems, Context context) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_calificar_companero, null);
        ImageView imgFoto = (ImageView) view.findViewById(R.id.imgFotoClificar);

        button = (Button) view.findViewById(R.id.btnConfirmarMash);

        Drawable originalDrawable = null;

        originalDrawable = view.getResources().getDrawable(R.drawable.fotoperfil);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable rbmd =
        RoundedBitmapDrawableFactory.create(view.getResources(), originalBitmap);
        rbmd.setCircular(true);
        imgFoto.setImageDrawable(rbmd);

        TextView txtNombreHuespedMah = (TextView) view.findViewById(R.id.txtNombreCalificar);

        txtNombreHuespedMah.setText(item.getNombreUsuario());

        SmileRating mSmileRating = (SmileRating) view.findViewById(R.id.ratingCalificar);

        mSmileRating.setOnSmileySelectionListener(this);
        mSmileRating.setOnRatingSelectedListener(this);
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "MetalMacabre.ttf");
        //mSmileRating.setTypeface(typeface);

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

    @Override
    public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Log.i(TAG, "Bad");
                break;
            case SmileRating.GOOD:
                Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Good");
                break;
            case SmileRating.GREAT:
                Log.i(TAG, "Great");
                break;
            case SmileRating.OKAY:
                Log.i(TAG, "Okay");
                break;
            case SmileRating.TERRIBLE:
                Log.i(TAG, "Terrible");
                break;
            case SmileRating.NONE:
                Log.i(TAG, "None");
                break;
        }
    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }
}
