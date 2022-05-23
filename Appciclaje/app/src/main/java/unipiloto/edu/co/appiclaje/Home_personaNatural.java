package unipiloto.edu.co.appiclaje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home_personaNatural extends AppCompatActivity {
    private FirebaseAuth firbaseAuth;
    public String nickname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_persona_natural);
        firbaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String id = firbaseAuth.getCurrentUser().getUid();
        TextView messageView = findViewById(R.id.userhome);
        nickname = intent.getStringExtra("nickname");
        messageView.setText(nickname);

    }
    public void publicarSolicitud(View view) {
        Intent intent = new Intent(this, PublicarSolicitud.class);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    public void consultarSolicitud(View view) {
        Intent intent = new Intent(this, TipoConsulta.class);
        intent.putExtra("nickname",nickname);
        intent.putExtra("tipo","Natural");
        startActivity(intent);
    }


    public void logout(View view) {
        startActivity(new Intent(this, IngresarAplicacion.class));
        finish();
    }
}