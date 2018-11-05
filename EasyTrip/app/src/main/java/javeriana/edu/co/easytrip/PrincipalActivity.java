package javeriana.edu.co.easytrip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javeriana.edu.co.modelo.Anfitrion;
import javeriana.edu.co.modelo.Huesped;

public class PrincipalActivity extends AppCompatActivity {

    //firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Button btnRegistrarseP;
    private Button btnLoginL;
    private EditText txtUsuarioL;
    private EditText txtContrasenaL;
    private Anfitrion myUser;
    private Huesped myUserH;

    private String email;
    private String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.rol ="";
        this.database= FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();

        this.txtUsuarioL = (EditText) findViewById(R.id.txtUsuarioL);
        this.txtUsuarioL.setText("michavarg9@gmail.com");
        this.txtContrasenaL = (EditText) findViewById(R.id.txtContrasenaL);
        this.txtContrasenaL.setText("123456");

        this.btnLoginL = (Button) findViewById(R.id.btnLoginL);
        this.btnLoginL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateForm()) {

                mAuth.signInWithEmailAndPassword(txtUsuarioL.getText().toString(), txtContrasenaL.getText().toString())
                        .addOnCompleteListener(PrincipalActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                FirebaseUser user = mAuth.getCurrentUser();
                                if (!task.isSuccessful()) {
                                    //Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(PrincipalActivity.this, "Usuario o contraseña Incorrectos",
                                            Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    //startActivity(new Intent(PrincipalActivity.this,HomeAnfitrionActivity.class));

                                    String rolAux="";
                                    //while(rol.compareTo("") == 0){
                                        //rolAux = loadUser(user.getEmail());
                                    //}
                                    //rolAux = loadUser(user.getEmail());


                                    //-----------------------------------------------------------------------------------
                                    //Toast.makeText(PrincipalActivity.this, "+++", Toast.LENGTH_SHORT).show();

                                    if(user.getDisplayName().compareTo("Huesped") == 0){
                                        //Intent intent = new Intent( PrincipalActivity.this,HomeHuespedActivity.class);
                                        //Toast.makeText(PrincipalActivity.this, user.getEmail()+"--"+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PrincipalActivity.this, HomeHuespedActivity.class));
                                    }
                                    if(user.getDisplayName().compareTo("Anfitrion") == 0){
                                        startActivity(new Intent(PrincipalActivity.this, HomeAnfitrionActivity.class));
                                    }

                                    //startActivity(new Intent(PrincipalActivity.this, HomeAnfitrionActivity.class));

                                }
                            }
                        });


                // Intent intent = new Intent(view.getContext(),VerReservaActivity.class);
                //Intent intent = new Intent(view.getContext(),HomeAnfitrionActivity.class);
                //Intent intent = new Intent(view.getContext(),ActivityReservasPorAcepatar.class);
                //startActivity(intent);

            }
            }
        });

    //--------------------------------------------------------------------------------------------------


        this.btnRegistrarseP = (Button) findViewById(R.id.btnRegistrarseP);
        this.btnRegistrarseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegistroPerfilActivity.class);
                //Intent intent = new Intent(view.getContext(),HomeAnfitrionActivity.class);
                //Intent intent = new Intent(view.getContext(),ActivityCompaneros.class);
                //Intent intent = new Intent(view.getContext(),VerReservaActivity.class);
                startActivity(intent);
            }
        });


    //----------------------------------------------------------------------------------------------------

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    boolean hecho = false;
                    String rolAux = "";
                    //while (!hecho) {
                    Toast.makeText(PrincipalActivity.this, "aqui", Toast.LENGTH_SHORT).show();
                    rolAux = loadUser(user.getEmail());

                    Toast.makeText(PrincipalActivity.this, rolAux +"--", Toast.LENGTH_SHORT).show();


                    if (rolAux.compareTo("Huesped") == 0) {
                        startActivity(new Intent(PrincipalActivity.this, HomeHuespedActivity.class));
                        hecho = true;
                    }
                    if (rolAux.compareTo("Anfitrion") == 0) {
                        startActivity(new Intent(PrincipalActivity.this, HomeAnfitrionActivity.class));
                        hecho = true;
                    }
                //}
                        Toast.makeText(PrincipalActivity.this, rolAux, Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out

                    Toast.makeText(PrincipalActivity.this, "No Logueado", Toast.LENGTH_SHORT).show();
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        //this.database= FirebaseDatabase.getInstance();
        //this.mAuth = FirebaseAuth.getInstance();
        //mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private boolean validateForm() {
        boolean valid = true;
        String email = this.txtUsuarioL.getText().toString();
        if (TextUtils.isEmpty(email)) {
            this.txtUsuarioL.setError("Requerido");
            valid = false;
        } else {
            this.txtUsuarioL.setError(null);
        }
        String password = this.txtContrasenaL.getText().toString();
        if (TextUtils.isEmpty(password)) {
            this.txtContrasenaL.setError("Requerido");
            valid = false;
        } else {
            this.txtContrasenaL.setError(null);
        }
        return valid;
    }

    public String loadUser(String em) {
        this.email = em;

        //Toast.makeText(PrincipalActivity.this, em+"++", Toast.LENGTH_SHORT).show();;
        myRef = database.getReference("huespedes/");
        //Toast.makeText(PrincipalActivity.this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    myUserH = singleSnapshot.getValue(Huesped.class);

                    if(myUserH.getEmail().compareTo(email) == 0){
                        Toast.makeText(PrincipalActivity.this, myUserH.getRol()+"++", Toast.LENGTH_SHORT).show();;
                        rol =  myUserH.getRol();
                    }
                    //String p = myUserH.getRol();
                    //Toast.makeText(PrincipalActivity.this, p+"--", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PrincipalActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();
            }
        });


        //------------------------------------------------------------------
        myRef = database.getReference("anfitriones/");
        //Toast.makeText(PrincipalActivity.this, "Aqui", Toast.LENGTH_SHORT).show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    myUser = singleSnapshot.getValue(Anfitrion.class);
                    if(myUser.getEmail().compareTo(email) == 0){
                        Toast.makeText(PrincipalActivity.this, "Aqui2", Toast.LENGTH_SHORT).show();
                        rol =  myUser.getRol();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PrincipalActivity.this, "Error en consulta", Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(PrincipalActivity.this, rol, Toast.LENGTH_SHORT).show();
        return rol;
    }




}
