package unipiloto.edu.co.appiclaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class IngresarAplicacion extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TextView regEmail, regPassword;
    private String email;
    private String password;
    private FirebaseAuth firebaseAuth;
    private Switch switchS;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_aplicacion);
        regEmail = findViewById(R.id.email_address);
        regPassword = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        switchS = findViewById(R.id.recordar_sesion);
        sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        switchS.setChecked(sharedPreferences.getBoolean("value", true));
    }

    public void registrarUsuario(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }

    public void home(View view) {
        email = regEmail.getText().toString();
        password = regPassword.getText().toString();
        Validation validation = new Validation();
        boolean pass = true;
        if (validation.isEmpty(email) || !validation.isEmail(email)) {
            Toast.makeText(this, "Ingrese su usuario", Toast.LENGTH_LONG).show();
            pass = false;
        }
        if (validation.isEmpty(password)) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_LONG).show();
            pass = false;
        }
        if (pass) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(IngresarAplicacion.this, "Login correcto", Toast.LENGTH_LONG).show();
                        String id = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference username = myRef.child(id).child("nickname");
                        DatabaseReference typeUser = myRef.child(id).child("TypeUser");

                        username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String nickname = snapshot.getValue().toString();
                                typeUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String typeuser = snapshot.getValue().toString();
                                        if (typeuser.equals("Reciclador")) {
                                            Intent intent = new Intent(IngresarAplicacion.this, Home_reciclador.class);
                                            intent.putExtra("nickname", nickname);
                                            startActivity(intent);

                                        } else if (typeuser.equals("Persona Natural")) {
                                            Intent intent = new Intent(IngresarAplicacion.this, Home_personaNatural.class);
                                            intent.putExtra("nickname", nickname);
                                            startActivity(intent);

                                        }

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else
                        Toast.makeText(IngresarAplicacion.this, "Error en credenciales", Toast.LENGTH_LONG).show();
                }


            });
        }
    }


    public void recuperarContraseña(View view) {
        startActivity(new Intent(this, OlvidarContrasena.class));
        finish();
    }


    public void saveSession(View view) {
        if (switchS.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", true);
            editor.apply();
            switchS.setChecked(true);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", false);
            editor.apply();
            switchS.setChecked(false);
        }
    }
}

