package unipiloto.edu.co.appiclaje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PublicarSolicitud extends AppCompatActivity {
    private Button regBtn;
    private DatabaseReference database;
    private TextView regTipo,regPeso,regAddress;
    private Validation validation;
    public FirebaseAuth firebaseAuth;
    private String tipo, peso, address = "";
    private String id="";
    private String nickname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_publicar_solicitud);
        regAddress = findViewById(R.id.addressSoli);
        regTipo = findViewById(R.id.tipo);
        regPeso = findViewById(R.id.peso);
        nickname = intent.getStringExtra("nickname");
    }

    public void sendMail(){
         String mail="pruebaweb231@gmail.com";
         String subject="Se ha publicado tu solicitud!";
         String mensaje="Oye " + nickname+"\n"+
         "Has publicado la solicitud la solicitud:"+"\n"+
         "Id: "+id+"\n"+"Tipo: "+tipo+"\n"+"Peso: "+peso+"\n"+"Dirección: " +address;
        JavaMailAPI javaMailAPI= new JavaMailAPI(this,mail,subject,mensaje);
        javaMailAPI.execute();
    }

    public void publicar(View view) {
        validation = new Validation();
        boolean cont = true;
        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        address = regAddress.getText().toString();
        tipo = regTipo.getText().toString();
        peso = regPeso.getText().toString();
        if (validation.isEmpty(address) ) {
            Toast.makeText(this, "Ingrese un dirección válida", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(tipo)) {
            Toast.makeText(this, "Ingrese un tipo válido", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (validation.isEmpty(peso) || !validation.isNumeric(peso)) {
            Toast.makeText(this, "Ingrese un peso válido", Toast.LENGTH_LONG).show();
            cont = false;
        }
        if (cont) {
            publicarSolicitud();
        }
    }

    public void publicarSolicitud() {
        Map<String, Object> map = new HashMap<>();
        int max=1;
        int min=9999999;
        int random=(int)Math.floor(Math.random()*(max-min+1)+min);
        id= String.valueOf(random);
        map.put("nickname",nickname);
        map.put("address", address);
        map.put("tipo", tipo);
        map.put("peso",peso);
        map.put("estado","Pendiente");
        map.put("asingado"," ");
       database.child("solicitudes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sendMail();
                    Toast.makeText(PublicarSolicitud.this, "Solicitud publicada", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(PublicarSolicitud.this,Home_personaNatural.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
        finish();
    }
}