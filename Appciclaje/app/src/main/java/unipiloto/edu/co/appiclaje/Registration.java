package unipiloto.edu.co.appiclaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {
    private Button regBtn;
    private DatabaseReference database;
    private DatabaseReference myRef;
    private TextView regName, regEmail, regUser, regPassword, regTelephone, regAddress;
    private Validation validation;
    public FirebaseAuth firebaseAuth;
    private String name = "";
    private String email = "";
    private String nickname = "";
    private String password = "";
    private String telephone = "";
    private String address = "";
    private String typeUser = "";
    private ArrayList<String> users = new ArrayList<>();
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        regName = findViewById(R.id.nombre);
        regEmail = findViewById(R.id.email_address);
        regUser = findViewById(R.id.user);
        regPassword = findViewById(R.id.password);
        regTelephone = findViewById(R.id.number);
        regAddress = findViewById(R.id.address);
        regBtn = findViewById(R.id.registrarButton);
        spinner = (Spinner) findViewById(R.id.selector);
        users.add("Reciclador");
        users.add("Persona Natural");
        ArrayAdapter adp = new ArrayAdapter(Registration.this, android.R.layout.simple_spinner_dropdown_item, users);
        spinner.setAdapter(adp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeUser = (String) spinner.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void register(View view) {
        validation = new Validation();
        boolean cont = true;
        database = FirebaseDatabase.getInstance().getReference();
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        nickname = regUser.getText().toString();
        password = regPassword.getText().toString();
        telephone = regTelephone.getText().toString();
        address = regAddress.getText().toString();
        if (validation.isEmpty(name) || validation.isNumeric(name)) {
            Toast.makeText(this, "Ingrese un nombre valido", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(email) || !validation.isEmail(email)) {
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(nickname)) {
            Toast.makeText(this, "Ingrese una usuario", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.containSpace(nickname)) {
            Toast.makeText(this, "El usuario no puede tener espacios", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(password)) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isTeleTelephone(telephone)) {
            Toast.makeText(this, "Ingrese una número de telefono valido", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(address)) {
            Toast.makeText(this, "Ingrese una dirección valida", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (cont) {
            registerUser(email, password);
        }
    }

    public void registerUser(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("nickname", nickname);
                    map.put("telephone", telephone);
                    map.put("address", address);
                    map.put("TypeUser", typeUser);
                    String id = firebaseAuth.getCurrentUser().getUid();
                    database.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(Registration.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Registration.this, IngresarAplicacion.class));
                                finish();
                            } else {
                                if (task2.getException().toString().contains("email"))
                                    Toast.makeText(Registration.this, "Este correo ya está registrado", Toast.LENGTH_LONG).show();
                                else {
                                    Toast.makeText(Registration.this, "La contraseña debe tener 6 caracteres ", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    });
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