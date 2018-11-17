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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import javeriana.edu.co.modelo.Alojamiento;
import javeriana.edu.co.modelo.Foto;

public class AdaptadorAlojamientosCercanos extends BaseAdapter {

    private ArrayList<Alojamiento> listItems;
    private Context context;
    private FirebaseStorage storage;
    private ImageView imgFotoAloja;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Foto> fotos;
    private ImageView btnVerRutaH;
    private Alojamiento item;
    private LinearLayout linearInfoAloCercanoH;
    private String nombreUsuario;

    public AdaptadorAlojamientosCercanos(ArrayList<Alojamiento> listItems, Context context, String nombreUsuario) {
        this.listItems = listItems;
        this.context = context;
        this.nombreUsuario = nombreUsuario;
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
        view = LayoutInflater.from(context).inflate(R.layout.item_alojamiento_cercano, null);
        item = (Alojamiento) getItem(i);

        this.storage = FirebaseStorage.getInstance();
        this.database= FirebaseDatabase.getInstance();
        //--------------------------------

        this.fotos = new ArrayList<Foto>();
        Drawable originalDrawable = null;

        if(item.getTipo().compareTo("Habitación")==0)
            originalDrawable = view.getResources().getDrawable(R.drawable.imagehabitacion);

        if(item.getTipo().compareTo("Apartamento")==0)
            originalDrawable = view.getResources().getDrawable(R.drawable.imageapartamento);

        if(item.getTipo().compareTo("Cabaña")==0)
            originalDrawable = view.getResources().getDrawable(R.drawable.imagecabana);

        if(originalDrawable == null)
            originalDrawable = view.getResources().getDrawable(R.drawable.imagecasa);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(view.getResources(), originalBitmap);

        //roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        roundedDrawable.setCircular(true);
        imgFotoAloja = (ImageView) view.findViewById(R.id.imgFotoAlojaC);

        imgFotoAloja.setImageDrawable(roundedDrawable);


        //--------------------------------
        this.btnVerRutaH = (ImageView) view.findViewById(R.id.btnVerRutaH);
        this.btnVerRutaH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("alojamiento",(Alojamiento)getItem(i));
                b.putString("rol","");
                //intent.putExtra("alojamiento",item);
                intent.putExtra("bundle",b);
                //Toast.makeText(view.getContext(),"Ver", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);

            }
        });

        TextView txtINombreAloja = (TextView) view.findViewById(R.id.txtINombreAloja);
        TextView txtICostoAloja = (TextView) view.findViewById(R.id.txtICostoAloja);
        //TextView txtINumeroAloja = (TextView) view.findViewById(R.id.txtINumeroAloja);

        txtINombreAloja.setText(item.getNombre());
        txtICostoAloja.setText("Costo: "+item.getCosto().toString()+"\n"+"Num. Personas: "+item.getMaxHuespedes());

        this.linearInfoAloCercanoH = (LinearLayout) view.findViewById(R.id.linearInfoAloCercanoH);
        this.linearInfoAloCercanoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VerAlojamientoActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("alojamiento",(Alojamiento)getItem(i));
                b.putString("rol","");
                b.putString("nombreUsuario",nombreUsuario);
                //intent.putExtra("alojamiento",item);
                intent.putExtra("bundle",b);
                //Toast.makeText(view.getContext(),"Ver", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);

            }
        });
        return view;
    }

    private void descargarFoto(String origen, final View v){
        //-------------------------------------------------------------------

        myRef = database.getReference("alojamientos/"+origen+"/fotos/");
        //Toast.makeText(v.getContext(), "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(HomeAnfitrionActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Foto f = singleSnapshot.getValue(Foto.class);
                    fotos.add(f);
                    //descargarFoto();

                    //Toast.makeText(v.getContext(), "Aqui2", Toast.LENGTH_SHORT).show();
                    //descargarFoto("ImagenesPerfil",a.getNombre());
                    //Toast.makeText(HomeAnfitrionActivity.this, a.getNombre(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(v.getContext(), "Aqui2", Toast.LENGTH_SHORT).show();
/*                    //----------------
                    StorageReference storageRef = storage.getReference();
                    StorageReference islandRef = storageRef.child("alijamientos/"+singleSnapshot.getKey()+"/"+f.getNombre()+".jpg");

                    final long ONE_MEGABYTE = 1024 * 1024;
                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Data for "images/island.jpg" is returns, use this as needed
                            //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            //RoundedBitmapDrawable roundedDrawable =
                            //      RoundedBitmapDrawableFactory.create(v.getResources(), bitmap);
                            //asignamos el CornerRadius
                            //roundedDrawable.setCornerRadius(bitmap.getHeight());
                            //roundedDrawable.setCircular(true);

                            //imgFotoAloja.setImageDrawable(roundedDrawable);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });*/
                    //-----------------
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(AlojamientosAnfitrionFragment.class, "Error en consulta", Toast.LENGTH_SHORT).show();

            }
        });

        //-------------------------------------------------------------------
        //updateImage();
//Toast.makeText(v.getContext(), "Aqui", Toast.LENGTH_SHORT).show();
        //Toast.makeText(v.getContext(), fotos.size(), Toast.LENGTH_SHORT).show();
    }

    private void updateImage(){
        //Toast.makeText(get, fotos.size(), Toast.LENGTH_SHORT).show();
        //Uri file = Uri.fromFile(new File("alojamientos/"+fotos.get(0).toString()+".jpg"));

        /*StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("alojamientos/"+origen+"/"+file.getLastPathSegment());

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                //RoundedBitmapDrawable roundedDrawable =
                  //      RoundedBitmapDrawableFactory.create(v.getResources(), bitmap);
                //asignamos el CornerRadius
                //roundedDrawable.setCornerRadius(bitmap.getHeight());
                //roundedDrawable.setCircular(true);

                //imgFotoAloja.setImageDrawable(roundedDrawable);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/
    }
}
