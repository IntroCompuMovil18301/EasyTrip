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

import Modelo.Usuario;
import javeriana.edu.co.modelo.FirebaseReference;

public class PrincipalActivity extends AppCompatActivity {
    public static final String TAG = "USER";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnRegistrarseP, btnLogin;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    EditText mUser, mPassword;
    private String tipoUsuario, nomUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        database = FirebaseDatabase.getInstance();

        mUser = (EditText) findViewById(R.id.txtUsuarioL);
        mPassword = (EditText) findViewById(R.id.txtContrasenaL);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!= null){
                    rolUsuario(user.getEmail());
                }else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        btnLogin = (Button) findViewById(R.id.btnLoginL);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

        this.btnRegistrarseP = (Button) findViewById(R.id.btnRegistrarseP);
        this.btnRegistrarseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegistroPerfilActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private boolean valideForm(){
        boolean valid = true;
        String email = mUser.getText().toString();
        if(TextUtils.isEmpty(email)){
            mUser.setError("Requerido");
            valid = false;
        }else{
            mUser.setError(null);
        }
        String password = mPassword.getText().toString();
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Requerido");
            valid = false;
        }else{
            mPassword.setError(null);
        }
        return valid;
    }
    protected void signInUser(){
        if(valideForm()){
            myRef = database.getReference();
            myRef.child(FirebaseReference.USUARIOS).orderByChild("nomUsuario").equalTo(mUser.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()) {
                        for (DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                            Usuario us = singlesnapshot.getValue(Usuario.class);
                            tipoUsuario = us.getTipo();
                            nomUsuario = us.getNomUsuario();
                            ingresar(us.getEmail(),us.getContrasena());
                        }
                    }else {
                        Toast.makeText(PrincipalActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    private void ingresar(String nomUsuario, String password){
        mAuth.signInWithEmailAndPassword(nomUsuario,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(PrincipalActivity.this, "Fallo al iniciar la sesion", Toast.LENGTH_SHORT).show();
                    mUser.setText("");
                    mPassword.setText("");
                }
            }
        });
    }
    private void rolUsuario(String email){
        myRef = database.getReference();
        myRef.child(FirebaseReference.USUARIOS).orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot singlesnapshot : dataSnapshot.getChildren()) {
                        Usuario us = singlesnapshot.getValue(Usuario.class);
                        if(us.getTipo().equals("Huesped")) {
                            startActivity(new Intent(PrincipalActivity.this, HomeHuespedActivity.class).putExtra("nomusuario",nomUsuario));
                        }else{
                            startActivity(new Intent(PrincipalActivity.this, HomeAnfitrionActivity.class).putExtra("nomusuario",nomUsuario));
                        }
                    }
                }else {
                    Toast.makeText(PrincipalActivity.this,"No encontre nada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
