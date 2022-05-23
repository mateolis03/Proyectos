package unipiloto.edu.co.appiclaje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home_reciclador extends AppCompatActivity {

    private FirebaseAuth firbaseAuth;
    private String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_reciclador);
        firbaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        TextView messageView = findViewById(R.id.userhome);
       nickname= intent.getStringExtra("nickname");
        messageView.setText(nickname);

    }



    public void logout(View view) {
        startActivity(new Intent(this, IngresarAplicacion.class));
        finish();
    }

    public void aceptarSolicitud(View view) {
        Intent intent = new Intent(this, AceptarSolicitud.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);

    }

    public void consultarSolicitud(View view) {
        Intent intent = new Intent(this, TipoConsulta.class);
        intent.putExtra("nickname",nickname);
        intent.putExtra("tipo","Reciclador");
        startActivity(intent);

    }


    public void finalizarSolicitud(View view) {
        Intent intent = new Intent(this, FinalizarSolicitud.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);

    }

    public void recorrido(View view) {
        Intent intent = new Intent(this, Mapa.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }
}