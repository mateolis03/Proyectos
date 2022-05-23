package unipiloto.edu.co.appiclaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarContrasena extends AppCompatActivity {
    String email;
    TextView regUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);
         regUsuario = findViewById(R.id.email_address);
    }

    public void recuperarContrasena(View view){
        email= regUsuario.getText().toString();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new  OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OlvidarContrasena.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OlvidarContrasena.this, IngresarAplicacion.class));
                    finish();

                }else{
                    Toast.makeText(OlvidarContrasena.this, "Correo invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(this, IngresarAplicacion.class);
        startActivity(intent);
        finish();
    }
    }